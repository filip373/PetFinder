package com.petfinder.rest.controller;

import com.petfinder.exception.EmailExistsException;
import com.petfinder.exception.InvalidEmailException;
import com.petfinder.exception.LoginExistsException;
import com.petfinder.exception.PasswordsDoesNotMatchException;
import com.petfinder.rest.domain.Status;
import com.petfinder.rest.domain.UserRegistrationForm;
import com.petfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<Status> register(
            @RequestBody UserRegistrationForm data) {
        try {
            userService.register(data.getLogin(), data.getPassword(),
                    data.getRepeatPassword(), data.getEmail());
            return new ResponseEntity<>(new Status("registered"),
                    HttpStatus.CREATED);
        } catch (InvalidEmailException e) {
            return new ResponseEntity<>(new Status("invalid email"),
                    HttpStatus.BAD_REQUEST);
        } catch (LoginExistsException e) {
            return new ResponseEntity<>(new Status("login exists"),
                    HttpStatus.BAD_REQUEST);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>(new Status("email exists"),
                    HttpStatus.BAD_REQUEST);
        } catch (PasswordsDoesNotMatchException e) {
            return new ResponseEntity<>(new Status("passwords does not match"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
