package com.petfinder.rest.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petfinder.domain.ExampleData;

@RestController
@RequestMapping("/api")
public class ExampleRestController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value="example", method=RequestMethod.GET)
	public ExampleData example(@RequestParam(value="name", defaultValue="Foo") String name) {
		return new ExampleData(counter.incrementAndGet(), name);
	}
}
