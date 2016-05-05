package com.petfinder.rest.controller;

import com.petfinder.exception.EmailExistsException;
import com.petfinder.exception.InvalidEmailException;
import com.petfinder.exception.LoginExistsException;
import com.petfinder.exception.PasswordsDoesNotMatchException;
import com.petfinder.rest.domain.RestResponse;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> register(
            @RequestBody UserRegistrationForm data) {
        try {
            userService.register(data.getLogin(), data.getPassword(),
                    data.getRepeatPassword(), data.getEmail());
            return new ResponseEntity<>(new RestResponse("registered", data),
                    HttpStatus.CREATED);
        } catch (InvalidEmailException e) {
            return new ResponseEntity<>(
                    new RestResponse(e.getMessage(), 1, "invalid email", data),
                    HttpStatus.BAD_REQUEST);
        } catch (LoginExistsException e) {
            return new ResponseEntity<>(
                    new RestResponse(e.getMessage(), 2, "login exists", data),
                    HttpStatus.BAD_REQUEST);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>(
                    new RestResponse(e.getMessage(), 3, "email exists", data),
                    HttpStatus.BAD_REQUEST);
        } catch (PasswordsDoesNotMatchException e) {
            return new ResponseEntity<>(
                    new RestResponse(e.getMessage(), 4,
                            "passwords does not match", data),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
