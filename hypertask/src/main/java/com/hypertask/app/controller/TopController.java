package com.hypertask.app.controller;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.AsyncRestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hypertask.app.controller.TopController;
import com.hypertask.app.service.MemberService;
import com.hypertask.app.vo.FacebookUserVo;

@Controller
public class TopController {
	
	//ログ
	private static final Logger logger = LoggerFactory.getLogger(TopController.class);
	// app_id
	private static final String APP_ID = "1312805038747012";
	// redirect_uri
	private static final String PRE_REDIRECT_URI = "http://localhost:8080/hypertask/";
	// 
	private static final String APP_SECRET = "411873c10383cbb07d3a12984bb8797f";
	
	@Autowired
	MemberService memberService;

	@RequestMapping("/")
	public String init(Model model){
		
		logger.info("-----------------  APP START !!! ---------------------");
		
		model.addAttribute("memberInfo", memberService.getMemberInfo("MEN0000000001"));
		
		return "top";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest request, String snsType){
		StringBuilder reqUri = new StringBuilder("https://www.facebook.com/dialog/oauth?")
				.append("client_id=" + APP_ID)
				.append("&redirect_uri=" + PRE_REDIRECT_URI + "facebooklogin")
				.append("&state=" + getRandomString());
		
//		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
//		ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.getForEntity(reqUri.toString(), String.class);
//		
//		String json = future.get().getBody();
		
		return reqUri.toString();
	}
	
	@RequestMapping("/facebooklogin")
	public String facebookLogin(String code, Model model){
		FacebookUserVo facebookUserVo = null;
		
		// create uri to get access token
		StringBuilder reqUri = new StringBuilder("https://graph.facebook.com/oauth/access_token?")
				.append("client_id=" + APP_ID)
				.append("&redirect_uri=" + PRE_REDIRECT_URI + "facebooklogin")
				.append("&client_secret=" + APP_SECRET)
				.append("&code=" + code);
		
		// アクセストークン取得
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
		ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.getForEntity(reqUri.toString(), String.class);
		
		String accessToken = null;

		try {
			String res = future.get().getBody();

			String[] paramArray = res.split("&");

			for (String string : paramArray) {
				String[] p = string.split("=");

				if (p.length == 2 && p[0].equals("access_token")) {
					accessToken = p[1];
				}
			}

			facebookUserVo = getFbUser(accessToken);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			return "error!!!";
		}
		
		model.addAttribute("userVo", facebookUserVo);
		
		return "user_page";
	}
	
	private String getRandomString(){
		return RandomStringUtils.randomAlphanumeric(15);
	}
	
	private FacebookUserVo getFbUser(String accessToken) throws InterruptedException, ExecutionException, Exception {
		StringBuilder reqUri = new StringBuilder("https://graph.facebook.com/me?access_token=" + accessToken);
		
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
		ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.getForEntity(reqUri.toString(), String.class);
		
		String res = future.get().getBody();

		ObjectMapper mapper = new ObjectMapper();
		FacebookUserVo user = mapper.readValue(res, FacebookUserVo.class);
		return user;
	}
}
