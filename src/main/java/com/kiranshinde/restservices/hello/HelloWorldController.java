package com.kiranshinde.restservices.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	//@RequestMapping(method= RequestMethod.GET, path="/helloWorld")
	@GetMapping("/helloWorld")
	public String helloWorld() {
		return "Hello World kiran";
	}
	
	@GetMapping("/helloWorld-bean")
	public UserDetails helloWorldBean() {
		return new UserDetails("Kiran", "Shinde", "Nashik");
	}
}
