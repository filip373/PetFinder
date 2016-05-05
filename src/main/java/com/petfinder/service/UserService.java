package com.petfinder.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.petfinder.exception.PasswordsDoesNotMatchException;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petfinder.dao.UserRepository;
import com.petfinder.domain.User;
import com.petfinder.exception.EmailExistsException;
import com.petfinder.exception.InvalidEmailException;
import com.petfinder.exception.LoginExistsException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void register(String login, String password, String repeatPassword,
                         String email)
            throws LoginExistsException, EmailExistsException,
            InvalidEmailException, PasswordsDoesNotMatchException {
		if (isEmailAddressValid(email)) {
			if (verifyLogin(login) && verifyEmail(email)) {
				if (password.equals(repeatPassword)) {
					String passwordHash = hashPassword(password);
					User user = new User(login, email, passwordHash);
					userRepository.save(user);
				} else {
                    throw new PasswordsDoesNotMatchException();
                }
			}
		}
	}

	private String hashPassword(String password) {
		String salt = BCrypt.gensalt(31);
        return BCrypt.hashpw(password, salt);
	}

	private boolean verifyLogin(String login) throws LoginExistsException {
		try {
			User user = userRepository.findOneByLogin(login);
			if (user == null) {
                return true;
            }
		} catch (Exception e) {
			return true;
		}
		throw new LoginExistsException();
	}

	private boolean verifyEmail(String email) throws EmailExistsException {
		try {
			User user = userRepository.findOneByEmail(email);
            if (user == null) {
                return true;
            }
		} catch (Exception e) {
			return true;
		}
		throw new EmailExistsException();
	}

	private boolean isEmailAddressValid(String email) throws InvalidEmailException {
		boolean valid = EmailValidator.getInstance().isValid(email);
		if (valid) {
			return true;
		} else {
			throw new InvalidEmailException();
		}
	}
}
