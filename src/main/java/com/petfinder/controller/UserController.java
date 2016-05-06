package com.petfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.petfinder.exception.EmailExistsException;
import com.petfinder.exception.InvalidEmailException;
import com.petfinder.exception.LoginExistsException;
import com.petfinder.exception.PasswordsDoesNotMatchException;
import com.petfinder.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userservice;
	
	@RequestMapping(value="/register",  method=RequestMethod.GET)
	public String registerView(Model model) {
        model.addAttribute("register", "JTwig");
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String userAdd(@RequestParam String login, @RequestParam String password, @RequestParam String repeatPassword,
			@RequestParam String email, Model model) {
		
		try {
			userservice.register(login, password, repeatPassword, email);
		} catch (LoginExistsException e) {
			return "loginexists";
		} catch (EmailExistsException e) {
			return "emailexists";
		} catch (InvalidEmailException e) {
			return "invalidemail";
		} catch (PasswordsDoesNotMatchException e) {
			return "passwordsdoesnotmatch";
		}
     
		//model.addAttribute("register", "JTwig");
		return "register";
	}
}
