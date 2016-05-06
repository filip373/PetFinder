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

	private String status = "";
	
	@Autowired
	UserService userservice;
	
	@RequestMapping(value="/register",  method=RequestMethod.GET)
	public String registerView(Model model) {
		return "register";
	}
	
	 @RequestMapping(value="/register", method=RequestMethod.POST)
	    public String userAdd(@RequestParam(required=false) String login,
	        @RequestParam(required=false) String password,
	        @RequestParam(required=false) String repeatPassword,
	        @RequestParam(required=false) String email,
	        Model model) {
		 	       
	        if (login.contains("") || password.contains("") || repeatPassword.contains("") || email.contains("")) {
	            model.addAttribute("status", "Fields cannot remain empty.");
	            return "register";
	        }
	        
	        try {
	            userservice.register(login, password, repeatPassword, email);
	        } catch (LoginExistsException e) {
	            model.addAttribute("status", e.getMessage());
	        } catch (EmailExistsException e) {
	            model.addAttribute("status", e.getMessage());
	        } catch (InvalidEmailException e) {
	            model.addAttribute("status", e.getMessage());
	        } catch (PasswordsDoesNotMatchException e) {
	            model.addAttribute("status", e.getMessage());
	        } finally {
	            return "register";
	        }
	    }
	
}
