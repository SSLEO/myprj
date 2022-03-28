package com.gettoere.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gettoere.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	@Inject
	private MemberService service;
	
	// 회원 정보 조회. ssh. 17.11.03
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public void read(@RequestParam("userid") String userid, Model model) throws Exception{
		logger.info("show member .....");
		
		model.addAttribute("user", service.read(userid));
	}
	
}
