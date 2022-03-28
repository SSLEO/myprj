package com.gettoere.controller;


import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gettoere.controller.BoardController;

import com.gettoere.service.BoardService;
import com.gettoere.vo.AttachVO;
import com.gettoere.vo.BoardVO;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.LoginVO;
import com.gettoere.vo.PageMaker;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	//게시판  검색 및 목록 페이징처리  kyo 17-11-10	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listAll(Criteria cri,Model model,HttpServletRequest request)throws Exception{
		logger.info("show list Page with criteria.............." );
		
		//게시판  등록으로 이동할때 lno 받기위해서 코딩했음 kyo 17-11-10
//		BoardVO vo = boardList.get(2);
		
		//게시판 정보. ssh. 171120
		model.addAttribute("lVo",service.getListBoard(cri));
		//게시판 공지글 kyo 17.11.22
		model.addAttribute("noticeList",service.noticeList(cri));
		
		model.addAttribute("list",service.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria(cri));
		model.addAttribute("cri",cri);
		model.addAttribute("pageMaker",pageMaker);

	}
	//리스트 게시판 검색 및 목록 페이징 처리 kyo 17.11.22 __ 수정(). jun. 17.12.05
	@RequestMapping(value = "/listBoard", method = RequestMethod.GET)
	public void listBoard(Criteria cri,Model model,HttpServletRequest request)throws Exception{
		logger.info("show listBoard Page with criteria.............." );						
		model.addAttribute("list",service.listBoardCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listBoardCountCriteria(cri));
		model.addAttribute("cri",cri);
		model.addAttribute("pageMaker",pageMaker);

	}
	
	//내가 쓴 글 목록 페이지. ssh. 171201	
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public void myList(Criteria cri,Model model,HttpServletRequest request)throws Exception{
		logger.info("show my list Page with criteria.............." );
		
		HttpSession session2 = request.getSession();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			model.addAttribute("msg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
			
			cri.setmNo(lVo.getmNo());
			
			model.addAttribute("list",service.myList(cri));
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.myListCount(cri));
			model.addAttribute("cri",cri);
			model.addAttribute("pageMaker",pageMaker);
		}
	}
	
	//내가 쓴 댓글 목록 페이지. ssh. 171201	
	@RequestMapping(value = "/myReply", method = RequestMethod.GET)
	public void myReply(Criteria cri,Model model,HttpServletRequest request)throws Exception{
		logger.info("show my Reply Page with criteria.............." );
		
		HttpSession session2 = request.getSession();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			model.addAttribute("msg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
			
			cri.setmNo(lVo.getmNo());
			
			model.addAttribute("list",service.myReply(cri));
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.myReplyCount(cri));
			model.addAttribute("cri",cri);
			model.addAttribute("pageMaker",pageMaker);
		}
	}
	
	//최신 글 목록 페이지. ssh. 171204
	@RequestMapping(value = "/recentList", method = RequestMethod.GET)
	public void recentList(Criteria cri,Model model)throws Exception{
		logger.info("show recentList Page with criteria.............." );
		
		model.addAttribute("list",service.recentList(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.recentListCount(cri));
		model.addAttribute("cri",cri);
		model.addAttribute("pageMaker",pageMaker);
	}
	
	//게시판 이름 검색. ssh. 171116
	@RequestMapping(value="/searchBoard", method = RequestMethod.POST)
	public @ResponseBody List<BoardVO> loginCheck(@RequestBody BoardVO vo) throws Exception{
		
		List<BoardVO> listVO;
		listVO = service.searchBoardName(vo);
			
		return listVO;
	}
	
	@RequestMapping("/download")
	public ModelAndView download(@RequestParam("bNo") int bNo, HttpServletRequest request)throws Exception{
		
		ServletContext context = request.getSession().getServletContext();
		String uploadPath = context.getRealPath("/resources/attach/");
		
		AttachVO aVo = service.readAttach(bNo);
		
		File file = new File(uploadPath + aVo.getPath());
		
		return new ModelAndView("fileDownload","downloadFile",file);
	}
	
	/** 
	 * 등록화면. ssh. 171108 
	 * */
	// 등록화면이동 . ssh. 171108
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public void registerGET(@ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		//게시판 정보. ssh. 171122
		model.addAttribute("lVo",service.getListBoard(cri));
		model.addAttribute("lNo", cri.getlNo());
		logger.info("register get ..........");
	}
	
	// 등록처리. ssh. 171108
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registerPOST(
			@RequestParam("lNo") int lNo,
			@RequestParam("title") String title, 
			@RequestParam("category") int category, 
			@RequestParam("contents") String contents, 
			@RequestParam("file") MultipartFile file, 
			HttpServletRequest request, 
			Criteria cri,
			RedirectAttributes rttr) throws Exception{
		
		HttpSession session2 = request.getSession();
		
		BoardVO vo = new BoardVO();
		AttachVO aVo = new AttachVO();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			String referer = request.getHeader("Referer");
			rttr.addFlashAttribute("msg", "loginFail");
			return "redirect:"+ referer;
		}else {
			vo.setlNo(lNo);
			vo.setTitle(title);
			vo.setCategory(category);
			vo.setContents(contents);
			
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			
			// 작성자가 현재 게시판과 관련없는 일반회원이면 무조건 일반글만 작성되게 함.(편법 막기) ssh. 171130
			if(lNo != lVo.getlNoManager()) {
				vo.setCategory(0);
			}
			
			int mNo = lVo.getmNo();
			
			vo.setmNo(mNo);
			
			//글 DB에 등록. ssh. 171113
			int bNo = service.regist(vo);
			
			if(file.getSize() > 0) {
				ServletContext context = request.getSession().getServletContext();
				
				String uploadPath = context.getRealPath("/resources/attach/");
				
				//파일 저장 및 저장 경로 리턴받기. ssh. 171113
				String path = uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				
				aVo.setbNo(bNo);
				aVo.setFileName(file.getOriginalFilename());
				aVo.setExtension(file.getContentType());
				aVo.setPath(path);
				aVo.setSize( Long.toString(file.getSize()) );
				
				//첨부파일 DB에 등록. ssh. 171113
				service.registAttach(aVo);
			}
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			rttr.addFlashAttribute("msg", "success");
			
			return "redirect:/board/list"+pageMaker.makeSearch(cri.getPage());
		}
	}
	
	// 파일명 (중복안되게) 변경 및 저장. ssh. 171113
	private String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		String path = calcPathMakeDir(uploadPath);
		
		UUID uid = UUID.randomUUID();
		
		String savedName = uid.toString() + "_" + originalName;
		
		File target = new File(uploadPath + path, savedName);
		
		FileCopyUtils.copy(fileData, target);
		
		return path + File.separator + savedName;
	}
	
	// 날짜별로 폴더 경로 설정. ssh. 171113
	private String calcPathMakeDir(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		
		String yPath = File.separator + cal.get(Calendar.YEAR);
		
		String mPath = yPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		
		String dPath = mPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
	
		makeDir(uploadPath, yPath, mPath, dPath);
		
		//logger.info(dPath);
		
		return dPath;
	}
	
	// 폴더 생성. ssh. 171113
	private void makeDir(String uploadPath, String... paths) {
		if(new File(uploadPath + paths[paths.length-1]).exists()) {
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			if(!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	
	// 수정화면 이동. ssh. 171108
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(@ModelAttribute("bNo") int bNo, @ModelAttribute("cri") Criteria cri, HttpServletRequest request, Model model) throws Exception {
		
		//글 내용 가져오기. ssh. 171115
		BoardVO bVo = service.read(bNo);
		
		HttpSession session2 = request.getSession();
		
		int checkMNo = 0;
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			model.addAttribute("modifyMsg", "loginFail");
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			checkMNo = lVo.getmNo();
		}
		
		//접근한 사용자와 글작성한 사용자 번호가 불일치 할경우 체크함. ssh. 171115
		if(checkMNo > 0 && bVo.getmNo() != checkMNo) {
			model.addAttribute("modifyMsg", "accessFail");
		}
		
		
		AttachVO aVo = service.readAttach(bNo);
		
		if(aVo == null) {
			aVo = new AttachVO();
			aVo.setaNo(0);
			aVo.setFileName("없음");
		}
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		model.addAttribute("lVo",service.getListBoard(cri));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardVO", bVo);
		model.addAttribute("attachVO", aVo);
	}
	
	// 수정처리. ssh. 171108
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modifyPOST(
		@RequestParam("lNo") int lNo,
		@RequestParam("bNo") int bNo,
		@RequestParam("title") String title, 
		@RequestParam("category") int category, 
		@RequestParam("contents") String contents, 
		@RequestParam("aNo") int aNo,
		@RequestParam("file") MultipartFile file, 
		HttpServletRequest request, 
		Criteria cri,
		RedirectAttributes rttr) throws Exception{
		
		HttpSession session2 = request.getSession();
		
		BoardVO vo = new BoardVO();
		AttachVO aVo = new AttachVO();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			String referer = request.getHeader("Referer");
			rttr.addFlashAttribute("modifyMsg", "loginFail");
			return "redirect:"+ referer;
		}else {
			vo.setlNo(lNo);
			vo.setbNo(bNo);
			vo.setTitle(title);
			vo.setCategory(category);
			vo.setContents(contents);
			
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			
			// 작성자가 현재 게시판과 관련없는 일반회원이면 무조건 일반글만 작성되게 함.(편법 막기) ssh. 171130
			if(lNo != lVo.getlNoManager()) {
				vo.setCategory(0);
			}
			
			int mNo = lVo.getmNo();
			
			vo.setmNo(mNo);
			int check = service.modify(vo);
			
			// 글 업데이트에 문제가 생기면 바로 돌아감. ssh. 171115
			if(check <= 0) {
				String referer = request.getHeader("Referer");
				rttr.addFlashAttribute("modifyMsg", "wrongUpdate");
				return "redirect:"+ referer;
			}
			
			if(file.getSize() > 0) {
				ServletContext context = request.getSession().getServletContext();
				
				String uploadPath = context.getRealPath("/resources/attach/");
				
				//기존 파일 삭제. ssh. 171115
				if(aNo > 0)
				{
					AttachVO aVo2 = service.readAttach(bNo);
					deleteFile(uploadPath, aVo2.getPath());
				}
				
				//파일 저장 및 저장 경로 리턴받기. ssh. 171113
				String path = uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				
				aVo.setaNo(aNo);
				aVo.setbNo(bNo);
				aVo.setFileName(file.getOriginalFilename());
				aVo.setExtension(file.getContentType());
				aVo.setPath(path);
				aVo.setSize( Long.toString(file.getSize()) );
				
				if(aNo > 0) {
					//첨부파일 수정. ssh. 171115
					service.modifyAttach(aVo);
				}else {
					//첨부파일 DB에 등록. ssh. 171115
					service.registAttach(aVo);
				}
			}
			
			rttr.addFlashAttribute("msg", "success");
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			return "redirect:/board/read"+pageMaker.makeSearch(cri.getPage())+"&bNo="+bNo;
		}
	}
	
	private void deleteFile(String uploadPath, String filePath) {
		
		File nowFile = new File(uploadPath + filePath);
		
		if(nowFile.exists()) {
			nowFile.delete();
		}
	}
	
	//읽기페이지로이동. lsy. 17-11-10
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public void read(@ModelAttribute("bNo") int bNo, Model model, Criteria cri,
			HttpServletRequest request) throws Exception{
		logger.info("show list Page with criteria.............." );
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		//게시판 정보. ssh. 171120
		model.addAttribute("lVo",service.getListBoard(cri));
		
		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardVO", service.read(bNo));
		model.addAttribute("attachVO", service.readAttach(bNo));
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public void read(@RequestParam("bNo") int bNo,
			@ModelAttribute("cri") Criteria cri, Model model)throws Exception{
		model.addAttribute(service.read(bNo));
	}
	
	//삭제처리. lsy. 17-11-13
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	public String remove(@RequestParam("bNo") int bNo, HttpServletRequest request, Criteria cri, RedirectAttributes rttr) throws Exception{
		
		BoardVO bVo = service.read(bNo);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		HttpSession session2 = request.getSession();
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			rttr.addFlashAttribute("removeMsg", "loginFail");
			return "redirect:/board/list"+pageMaker.makeSearch(cri.getPage());
		}else {
			LoginVO lVo = (LoginVO)session2.getAttribute("LoginVo");
			if(bVo.getmNo() == lVo.getmNo()) {
				service.remove(bNo);
				
				rttr.addFlashAttribute("removeMsg","SUCCESS");
				return "redirect:/board/list"+pageMaker.makeSearch(cri.getPage());
			}else {
				rttr.addFlashAttribute("removeMsg", "incorrect");
				return "redirect:/board/list"+pageMaker.makeSearch(cri.getPage());
			}
		}
	}
	
	//수정페이지로. lsy. 17-11-13
	@RequestMapping(value = "/modifyPage", method=RequestMethod.GET)
	public void modifyPagingGET(@RequestParam("bNo") int bNo, @ModelAttribute("cri") Criteria cri,
			Model model)throws Exception{
		
		model.addAttribute(service.read(bNo));
	}
	
	// 메인페이지 새로운 게시판 목록 슬라이더 ktg 17.11.15
	@RequestMapping(value = "/newBoard", method = RequestMethod.POST)
	public @ResponseBody List<BoardVO> newBoardSlider(BoardVO vo) throws Exception {
		List<BoardVO> listVO;
		listVO = service.newBoardSlider(vo);

		return listVO;
	}
	
	@RequestMapping(value="/upDown", method = RequestMethod.POST)
	public @ResponseBody BoardVO upDown(@RequestBody BoardVO vo, HttpServletRequest request) throws Exception{
		HttpSession session2 = request.getSession();
		
		BoardVO bVo = new BoardVO();
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			bVo.setMessage("loginFail");
		}else {
			LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
			int mNo = lVo.getmNo();
			vo.setmNo(mNo);
			bVo = service.upDownRecommend(vo);
		}
		return bVo;
	}
}
