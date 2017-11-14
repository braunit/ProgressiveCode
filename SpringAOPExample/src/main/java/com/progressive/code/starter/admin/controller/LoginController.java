package com.progressive.code.starter.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model) {
		
		LOGGER.info("Login request");
		
		return "login";
		
	}
}