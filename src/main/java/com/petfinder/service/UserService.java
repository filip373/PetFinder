package com.petfinder.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	public void register(String login, String password, String repeatpassword, String email)
			throws LoginExistsException, EmailExistsException, InvalidEmailException {
		if (isEmailAddressValid(email)) {
			if (verifyLogin(login) && verifyEmail(email)) {
				if (ifPasswordsMatch(password, repeatpassword)) {
					String passwordhash = hashPassword(password);
					User user = new User(login, email, passwordhash);
					userRepository.save(user);
				}
			}
		}
	}

	private boolean ifPasswordsMatch(String password, String repeatpassword) {
		if (password.equals(repeatpassword)) {
			return true;
		}
		return false;
	}

	private String hashPassword(String password) {
		String salt = BCrypt.gensalt(31);
		String pw_hash = BCrypt.hashpw(password, salt);
		return pw_hash;
	}

	private boolean verifyLogin(String login) throws LoginExistsException {
		try {
			userRepository.findOneByLogin(login);
		} catch (Exception e) {
			return true;
		}
		throw new LoginExistsException();
	}

	private boolean verifyEmail(String email) throws EmailExistsException {
		try {
			userRepository.findOneByEmail(email);
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
