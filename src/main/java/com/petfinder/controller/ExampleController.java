package com.petfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.petfinder.exception.EmailExistsException;
import com.petfinder.exception.InvalidEmailException;
import com.petfinder.exception.LoginExistsException;
import com.petfinder.service.UserService;

@Controller
@RequestMapping("/example")
public class ExampleController {
		
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public String example(@PathVariable(value="name") String name, Model model) {
		model.addAttribute("name", name);
		
		UserService userservice = new UserService();
		try {
			userservice.register("damian", "kr", "kr", "ww@o2.pl");
		} catch (LoginExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "example";
	}
}
