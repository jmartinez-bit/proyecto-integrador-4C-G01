package com.pcperu.web.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
}
