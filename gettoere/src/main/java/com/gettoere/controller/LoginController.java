package com.gettoere.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gettoere.service.BoardService;
import com.gettoere.service.LoginService;
import com.gettoere.vo.LoginVO;

// 17.11.08 메인 페이지 로그인 ktg
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Inject
	LoginService service;
	
	@Inject
	BoardService bService;
	
	// 17.11.13 메인페이지 ktg
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		// 인기글 목록
		model.addAttribute("bestPosts", bService.bestPosts());

		// 인기게시판 목록
		model.addAttribute("bestBoard", bService.bestBoard());

		// 죽어가는 게시판 목록
		model.addAttribute("dyingBoard", bService.dyingBoard());
		
		return "home";
	}
	
	// 17.11.13 로그인 했을 때 ktg
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody LoginVO loginCheck(@RequestBody LoginVO vo, HttpServletRequest request, RedirectAttributes rttr) {
		
		HttpSession session = request.getSession();
		boolean result = service.loginCheck2(vo);
		LoginVO vo2 = null;
		if (result == true) { // 로그인 성공

			System.out.println("로그인 성공");
			
			vo2 = service.viewMember(vo);
			vo2.setMsg("SUCCESS");
			// VO2를 세션에 저장
        	session.setAttribute("LoginVo", vo2);
			
		} else { // 로그인 실패
			vo2 = new LoginVO();
			vo2.setMsg("FAIL");
		}
		return vo2;
	}
	
	// 17.11.13 로그아웃 했을 때 ktg
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public ResponseEntity<String> logout(HttpSession session) {
		ResponseEntity<String> entity = null;
        try {
            // 세션 정보 초기화
        	session.invalidate();
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
        return entity;
    }
	
	//최고관리자로부터 밴된 사람 접근 불가. ssh. 17.11.28
	@RequestMapping(value = "/allBan", method = RequestMethod.GET)
	public String allBan(Locale locale, Model model) throws Exception {
		
		return "error/allBan";
	}
	
	//최고관리자로부터 밴된 사람 접근 불가. ssh. 17.11.28
	@RequestMapping(value = "/boardBan", method = RequestMethod.GET)
	public String boardBan(Locale locale, Model model) throws Exception {
		
		return "error/boardBan";
	}
	
	//로그인을 안해서 접근 불가. ssh. 17.11.28
	@RequestMapping(value = "/noLogin", method = RequestMethod.GET)
	public String noLogin(Locale locale, Model model) throws Exception {
		
		return "error/noLogin";
	}
	
	//게시판 정지되어 접근 불가. ssh. 17.11.28
	@RequestMapping(value = "/noBoard", method = RequestMethod.GET)
	public String noBoard(Locale locale, Model model) throws Exception {
		
		return "error/noBoard";
	}
}
