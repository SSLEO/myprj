package com.gettoere.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gettoere.service.BoardService;
import com.gettoere.service.ManagerService;
import com.gettoere.vo.AdminMemberVO;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.LoginVO;
import com.gettoere.vo.ManagerBoardVO;
import com.gettoere.vo.ManagerListVO;
import com.gettoere.vo.ManagerVO;
import com.gettoere.vo.PageMaker;

@Controller
@RequestMapping("/manager/*")
public class ManagerController {


	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	@Inject
	private ManagerService service;
	
	@Inject
	private BoardService bService;

	
	@RequestMapping(value="/boardMan", method = RequestMethod.GET)
	public void boardGET(HttpServletRequest request, RedirectAttributes rttr, Model model) throws Exception{
		//게시판 이름이랑 로고이미지 띄워줘야함
		HttpSession session2 = request.getSession();
		ManagerVO mVo = null;
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			model.addAttribute("bManMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			int lNo = lVo.getlNoManager();

			mVo = service.readBoard(lNo);
			
			if(lVo.getGrade() == 0) {
				model.addAttribute("bManMsg2", "memGrade0");
			}else {
				model.addAttribute("bManMsg2", "memGrade1");
			}
			model.addAttribute("ManagerVO", mVo);
			
		}
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/boardMan", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String boardPOST(@RequestParam("name") String name, @RequestParam("imgUrl") MultipartFile file, HttpServletRequest request, RedirectAttributes rttr, Model model)throws Exception{
		
		ServletContext context = request.getSession().getServletContext();
		String uploadPath = context.getRealPath("/resources/logoImg/");
		
		HttpSession session2 = request.getSession();
		
		logger.info("originalName: " + file.getOriginalFilename());
		logger.info("size: " + file.getSize());
		logger.info("contentType: " + file.getContentType());
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + file.getOriginalFilename();
		
		File target = new File(uploadPath, savedName);
		
		FileCopyUtils.copy(file.getBytes(), target);
		
		model.addAttribute("savedName", savedName);
		
		ManagerVO vo = new ManagerVO();
		int mNo = 0;
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			rttr.addFlashAttribute("bManMsg", "loginFail");
			
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			mNo = lVo.getmNo();
			
			vo.setmNo(mNo);
			vo.setName(name);
			
			if(lVo.getGrade() < 2) {
				if(file.getSize() > 0) {
					vo.setImgUrl(savedName);
					service.createBoard(vo);
				}else {
					vo.setImgUrl(null);
					service.createBoard(vo);
				}
			}else {
				vo.setlNo(lVo.getlNoManager());
				if(file.getSize() > 0) {
					vo.setImgUrl(savedName);
					service.updateBoard(vo);
				}else {
					ManagerVO mVo = service.readBoard(lVo.getlNoManager());
					vo.setImgUrl(mVo.getImgUrl());
					service.updateBoard(vo);
				}
			}
			
			session2.removeAttribute("LoginVo");
			
			lVo.setGrade(2);
			lVo.setlNoManager(vo.getlNo());
			
        	session2.setAttribute("LoginVo", lVo);
			
			rttr.addFlashAttribute("bManMsg", "setSuccess");
		}
		
		return "redirect:/manager/boardMan";
		
		//return new ResponseEntity<>(savedName, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/chkBoardName", method = RequestMethod.POST)
	public @ResponseBody int chkBoardName(@RequestBody ManagerVO mVo) throws Exception{
		
		int chkBoard = 2;
		
		chkBoard = service.chkBoardName(mVo);
		
		return chkBoard;
	}
	
	
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String deleteBoard(HttpServletRequest request, RedirectAttributes rttr,  Model model) throws Exception {
		HttpSession session2 = request.getSession();
		int lNo = 0;
		ManagerBoardVO bVo = null;
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			rttr.addFlashAttribute("bManMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			lNo = lVo.getlNoManager();
			//게시판 삭제
			service.delBoard(lNo);
			
			session2.removeAttribute("LoginVo");
			
			lVo.setGrade(0);
			lVo.setlNoManager(0);
			
        	session2.setAttribute("LoginVo", lVo);
			
			rttr.addFlashAttribute("bManMsg", "boardDeleted");

		}
		
		//redirect
		return "redirect:/manager/boardMan";
	}
	
	@RequestMapping(value = "/managerMan", method = RequestMethod.GET)
	public void managerGET(HttpServletRequest request, Model model)throws Exception{
		logger.info("manager manager get ........");
		HttpSession session2 = request.getSession();
		int lNo = 0;
		List<ManagerVO> voList = null;
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			model.addAttribute("managerManMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			
			if(lVo.getGrade() == 1 || lVo.getGrade() == 0) {
				model.addAttribute("managerManMsg", "accessFail");
			}else {
				lNo = lVo.getlNoManager();
				voList = service.listManager(lNo);
			}
		}
		
		model.addAttribute("manList", voList);
	}
	
	@Transactional
	@RequestMapping(value = "/managerUp", method = RequestMethod.POST)
	public @ResponseBody String managerUp(@RequestBody ManagerVO mVo, HttpServletRequest request) throws Exception{
		HttpSession session2 = request.getSession();
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			return "loginFail";
		}else {
			LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
			
			//현재 게시판의 관리자를 모두 부매니저 등급으로 변경해둠. ssh. 171124
			int check1 = service.downManager(lVo);
			
			//선택된 사용자는 주관리자로 등급을 변경. ssh. 171124
			int check2 = 0;
			if(check1 > 0) {
				check2 = service.upManager(mVo);
			}
			
			if(check2 > 0) {
				return "success";
			}else {
				return "fail";
			}
		}
	}
	
	@RequestMapping(value = "/managerOut", method = RequestMethod.POST)
	public @ResponseBody String managerOut(@RequestBody ManagerVO mVo, HttpServletRequest request) throws Exception{
		HttpSession session2 = request.getSession();
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			return "loginFail";
		}else {
			//선택된 사용자는 주관리자로 등급을 변경. ssh. 171124
			int check = service.delManager(mVo.getmNos());
			
			if(check > 0) {
				return "success";
			}else {
				return "fail";
			}
		}
	}
	
	@RequestMapping(value = "/findMember", method = RequestMethod.POST)
	public @ResponseBody List<ManagerVO> findMember(@RequestBody ManagerVO mVo, HttpServletRequest request) throws Exception{
		HttpSession session2 = request.getSession();
		
		List<ManagerVO> rstVo = null;
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			
		}else {
			//선택된 사용자는 주관리자로 등급을 변경. ssh. 171124
			rstVo = service.findMember(mVo);
		}
		
		return rstVo;
	}
	
	@RequestMapping(value = "/addManager", method = RequestMethod.POST)
	public @ResponseBody String addManager(@RequestBody ManagerVO mVo, HttpServletRequest request) throws Exception{
		HttpSession session2 = request.getSession();
		
		int check=0;
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			return "loginFail";
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			
			mVo.setlNo(lVo.getlNoManager());
			
			//선택된 사용자는 부 매니저로 등급을 변경. ssh. 171124
			check = service.addManager(mVo);
		}
		
		return "success";
	}
	
	
	// 매니저 블랙리스트 테이블 페이징. jun. 17.11.23
	// 주관리자, 부관리자 접근 권한. jun. 17.11.29
	@RequestMapping(value = "/outlistMan", method = RequestMethod.GET)
	public void outListMan(@ModelAttribute("cri") Criteria cri, HttpServletRequest request, Model model)throws Exception{
		logger.info("show manager outlistMan paging ........");
		
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("LoginVo") == null) {
			model.addAttribute("managerManMsg", "loginFail");
		} else {
			LoginVO lVo = (LoginVO)session.getAttribute("LoginVo");
			if(lVo.getGrade() == 2 || lVo.getGrade() == 1) {
				cri.setlNo(lVo.getlNoManager());
				model.addAttribute("outlistMan", service.outlistMan(cri));
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(service.outlistCountPaging(cri));
				model.addAttribute("pageMaker",pageMaker);
			} else {
				model.addAttribute("managerManMsg", "accessFail");
			}
		}
	}

	// 매니저 게시판관리 테이블 페이징. jun. 17.11.22
	// 주관리자, 부관리자 접근 권한. jun. 17.11.29
	@RequestMapping(value = "/contentMan", method = { RequestMethod.POST,RequestMethod.GET })
	public void contentManGET(HttpServletRequest request, @ModelAttribute("cri") Criteria cri, Model model)throws Exception{
		logger.info("show manager contentMan paging .....");
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("LoginVo") == null) {
			model.addAttribute("managerManMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session.getAttribute("LoginVo");
			if(lVo.getGrade() == 2 || lVo.getGrade() == 1) {
				cri.setlNo(lVo.getlNoManager());
				model.addAttribute("contentMan", service.contentMan(cri));
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(service.contentCountPaging(cri));
				model.addAttribute("pageMaker",pageMaker);
			} else {
				model.addAttribute("managerManMsg", "accessFail");
			}
		}
	}
	
	// 게시판리스트 삭제기능. jun. 17.11.16
	@RequestMapping(value = "/contentDelete", method = RequestMethod.POST)
	public @ResponseBody String contentDelete (@RequestBody Map<String, Object> bNo) throws Exception{
		logger.info("show manager contentMan Check Delete.....");

		String key="";
		for (String i : bNo.keySet()){
			key += bNo.get(i);
	    }
		service.contentDelete(key);
		
		return "SUCCESS";
	}
	
	// 회원 검색. jun. 17.11.23
	@RequestMapping(value="/searchMember", method = RequestMethod.POST)
	public @ResponseBody List<AdminMemberVO> searchMember (@RequestBody AdminMemberVO vo1) throws Exception{
		logger.info("show manager searchMember . . . . .");
		List<AdminMemberVO> listVO;
		listVO = service.searchMember(vo1);
		logger.info(listVO.toString());
		return listVO;
	}
	
	// 부 매니저 탈퇴. ssh. 171128
	@RequestMapping(value="/outManager", method = RequestMethod.POST)
	public @ResponseBody String outManager (HttpServletRequest request) throws Exception{
		logger.info("manager out. . . . .");
		
		HttpSession session2 = request.getSession();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			return "loginFail";
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			
			int check = service.delManager( Integer.toString(lVo.getmNo()) );
			
			if(check > 0) {
			
				session2.removeAttribute("LoginVo");
				
				lVo.setGrade(0);
				lVo.setlNoManager(0);
				
				session2.setAttribute("LoginVo", lVo);
			
				return "success";
			}else {
				return "fail";
			}
		}
		
	}
}
