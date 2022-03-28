package com.gettoere.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gettoere.service.AdminService;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.LoginVO;
import com.gettoere.vo.ManagerVO;
import com.gettoere.vo.PageMaker;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Inject
	private AdminService service;

	// 최고관리자 회원리스트 페이징. jun. 17.11.13
	// 최고관리자만 접근 권한. jun. 17.11.29
	@RequestMapping(value="/memberList", method = RequestMethod.GET)
	public void memberList(@ModelAttribute("cri") Criteria cri, HttpServletRequest request, Model model)throws Exception{
		logger.info("show admin memberlist paging . . . . .");
		
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("LoginVo") == null) {
			model.addAttribute("checkMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session.getAttribute("LoginVo");
			
			if(lVo.getGrade() == 3) {
				model.addAttribute("memberList", service.memberList(cri));
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(service.memberCountPaging(cri));
				model.addAttribute("pageMaker",pageMaker);
			} else {
				model.addAttribute("checkMsg", "accessFail");
			}
		}
	}
	
	// 최고관리자 게시판테이블 페이징. jun. 17.11.13
	// 최고관리자만 접근 권한. jun. 17.11.29
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public void boardList(@ModelAttribute("cri") Criteria cri, HttpServletRequest request, Model model) throws Exception{
		logger.info("show admin boardList paging .....");
		
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("LoginVo") == null) {
			model.addAttribute("checkMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session.getAttribute("LoginVo");
			
			if(lVo.getGrade() == 3) {
				model.addAttribute("boardList", service.boardList(cri));
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(service.boardCountPaging(cri));
				model.addAttribute("pageMaker",pageMaker);
			} else {
				model.addAttribute("checkMsg", "accessFail");
			}
		}
	}

	// 최고관리자 블랙리스트 페이징. jun. 17.11.13
	// 최고관리자만 접근 권한. jun. 17.11.29
	@RequestMapping(value = "/blackList", method = RequestMethod.GET)
	public void blackList(@ModelAttribute("cri") Criteria cri, HttpServletRequest request, Model model) throws Exception{
		logger.info("show admin blackList paging.....");
		
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("LoginVo") == null) {
			model.addAttribute("checkMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session.getAttribute("LoginVo");
			
			if(lVo.getGrade() == 3) {
				model.addAttribute("blackList", service.blackList(cri));
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(service.blackCountPaging(cri));
				model.addAttribute("pageMaker",pageMaker);
			} else {
				model.addAttribute("checkMsg", "accessFail");
			}
		}
	}
	
	// 최고관리자 금지어리스트 페이징. jun. 17.11.13
	// 최고관리자만 접근 권한. jun. 17.11.29
	@RequestMapping(value = "/tabooWordList", method = RequestMethod.GET)
	public void tabooWordList(@ModelAttribute("cri") Criteria cri, HttpServletRequest request, Model model) throws Exception{
		logger.info("show admin tabooWordList paging.....");
		
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("LoginVo") == null) {
			model.addAttribute("checkMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session.getAttribute("LoginVo");
			
			if(lVo.getGrade() == 3) {
				model.addAttribute("tabooWordList", service.tabooWordList(cri));
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(service.tabooWordCountPaging(cri));
				model.addAttribute("pageMaker",pageMaker);
			} else {
				model.addAttribute("checkMsg", "accessFail");
			}
		}
	}
	
	// 최고관리자 금지어삭제. jun. 17.11.15
	@RequestMapping(value = "/tabooWordDelete", method = RequestMethod.POST)
	public @ResponseBody String tabooWordDelete (@RequestBody Map<String, Object> tNo) throws Exception{
		logger.info("show admin tabooWordList Check Delete.....");
		//logger.info(tNo.toString());
		
//		String[] key = tNo.get("tNo").toString().split(",");
//		service.tabooWordCheck(key);

		String key="";
		for (String i : tNo.keySet()){
			key += tNo.get(i);
	    }
		//key=key.replaceAll("\\[", "'").replaceAll("\\]", "'").replaceAll(", ", "','");
		logger.info(key);
		service.tabooWordDelete(key);
		
		return "SUCCESS";
	}
	
	// 최고관리자 금지어생성. jun. 17.11.16
	@RequestMapping(value = "/tabooWordInsert", method = RequestMethod.POST)
	public @ResponseBody String tabooWordInsert (@RequestBody String input) throws Exception{
		logger.info("show admin tabooWordList Check Insert.....");

		logger.info(input);
		service.tabooWordInsert(input);
		
		return "SUCCESS";
	}
	
	// 최고관리자 게시판 정지 해제시 이름 비교. jun. 17.12.05
	@RequestMapping(value = "/chkBoardName", method = RequestMethod.POST)
	public @ResponseBody int chkBoardName(@RequestBody Map<String, Object> lNo) throws Exception{
		String key="";
		for (String i : lNo.keySet()){
			if(i.equals("lNo"))
				key += lNo.get(i);
	    }
		String[] lNo1 = key.split(",");
		for(int i = 0; i<lNo1.length; i++) {
			logger.info(lNo1[i]);
		}
		
		int chkBoard = 2;
		
		chkBoard = service.chkBoardName(lNo1);
		
		return chkBoard;
	}
	
	// 최고관리자 게시판  정지 해제. jun. 17.11.23
	@RequestMapping(value = "/boardStartStop", method = RequestMethod.POST)
	public @ResponseBody String boardStop (@RequestBody Map<String, Object> lNo) throws Exception{
		logger.info("show admin boardList Check Start and Stop.....");

		String key="";
		String msg="";
		for (String i : lNo.keySet()){
			if(i.equals("lNo"))
				key += lNo.get(i);
			else
				msg +=lNo.get(i);
	    }
		logger.info(key);
		logger.info(msg);
		service.boardStartStop(key, msg);
		
		return "SUCCESS";
	}
	
	// 게시판리스트 메시지 추가. jun. 17.11.23
	@RequestMapping(value = "/boardMsg", method = RequestMethod.POST)
	public @ResponseBody String boardMsg (@RequestBody Map<String, Object> lNo) throws Exception{
		logger.info("show admin boardList Check Msg.....");

		String key="";
		String msg="";
		for (String i : lNo.keySet()){
			if(i.equals("lNo"))
				key += lNo.get(i);
			else
				msg +=lNo.get(i);
	    }
		logger.info(key);
		logger.info(msg);
		service.boardMsg(key, msg);
		
		return "SUCCESS";
	}
	
	// 블랙리스트 면제기능. jun. 17.11.16
	@Transactional
	@RequestMapping(value = "/memberFree", method = RequestMethod.POST)
	public @ResponseBody String memberFree (@RequestBody Map<String, Object> mNo) throws Exception{
		logger.info("show admin memberList Check Free.....");

		String key="";
		String lNo="";
		for (String i : mNo.keySet()){
			if(i.equals("mNo"))
				key += mNo.get(i);
			else
				lNo += mNo.get(i);
	    }
		logger.info(key);
		logger.info(lNo);
		
		service.memberFree(key, lNo);
		
		return "SUCCESS";
	}
	
	// 회원 블랙리스트 추가기능. jun. 17.11.16
	// 트랜잭션 추가. jun. 17.11.20
	// 관리자 블랙리스트 추가. jun, 17.11.24
	@Transactional
	@RequestMapping(value = "/blackListAdd", method = RequestMethod.POST)
	public @ResponseBody String blackListAdd (@RequestBody Map<String, Object> mNo) throws Exception{
		logger.info("show admin memberList Check Free.....");

		String key="";
		String input="";
		String lNo="";
		for (String i : mNo.keySet()){
			if(i.equals("mNo"))
				key += mNo.get(i);
			else if(i.equals("reason"))
				input +=mNo.get(i);
			else
				lNo += mNo.get(i);
	    }
		logger.info(lNo);
		String[] keyWord = key.split(",");
		for(int i = 0; i<keyWord.length; i++) {
			logger.info(keyWord[i]);
		}
		logger.info(input);
		service.blackListAdd(keyWord, input, lNo);
		
		return "SUCCESS";
	}
}
