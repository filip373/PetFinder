package com.petfinder.service;

import com.petfinder.dao.UserRepository;
import com.petfinder.domain.User;
import com.petfinder.exception.UserStateNotChangedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanningService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public void banUser(String userLogin) throws UserStateNotChangedException {
        User loggedUser = userService.getLoggedUser();
        if(!loggedUser.getRole().equals("MODERATOR")) {
            throw new SecurityException("User is not a moderator");
        }
        User userToBan = userRepository.findOneByLogin(userLogin);
        if(userToBan.isBanned()) {
            throw new UserStateNotChangedException("User already banned");
        }
        userToBan.setBanned(true);
        userRepository.save(userToBan);
    }

    public void unbanUser(String userLogin) throws UserStateNotChangedException {
        User loggedUser = userService.getLoggedUser();
        if(!loggedUser.getRole().equals("MODERATOR")) {
            throw new SecurityException("User is not a moderator");
        }
        User userToBan = userRepository.findOneByLogin(userLogin);
        if(!userToBan.isBanned()) {
            throw new UserStateNotChangedException("User is already unbanned");
        }
        userToBan.setBanned(false);
        userRepository.save(userToBan);
    }

}
