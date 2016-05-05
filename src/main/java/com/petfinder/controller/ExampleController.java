package com.petfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	UserService userservice;
	
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public String example(@PathVariable(value="name") String name, Model model) {
		model.addAttribute("name", name);

		return "example";
	}
}
