package com.hypertask.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hypertask.app.controller.TopController;
import com.hypertask.app.service.MemberService;

@Controller
public class TopController {
	
	//ログ
	private static final Logger logger = LoggerFactory.getLogger(TopController.class);
	
	@Autowired
	MemberService memberService;

	@RequestMapping("/")
	public String init(Model model){
		
		logger.info("-----------------  APP START !!! ---------------------");
		
		model.addAttribute("memberInfo", memberService.getMemberInfo("MEN0000000001"));
		
		return "top";
	}
}
