package com.hypertask.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/signUp")
public class SignUpController {
	
	//ログ
	private static final Logger logger = LoggerFactory.getLogger(TopController.class);

	@RequestMapping(value = "/init")
	public String init(){
		
		logger.info("-----------------  sign-up START !!! ---------------------");
		
		return "SignUp";
	}
}
