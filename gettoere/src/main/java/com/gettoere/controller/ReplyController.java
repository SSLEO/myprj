package com.gettoere.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gettoere.service.ReplyService;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.PageMaker;
import com.gettoere.vo.ReplyVO;
import com.gettoere.vo.LoginVO;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Inject
	private ReplyService service;
	
	// 댓글 등록을 위한 기능. lsy. 17-11-09 
    @RequestMapping(value="", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String>register(@RequestBody ReplyVO vo, HttpServletRequest request){
       
       ResponseEntity<String> entity = null;
       LoginVO loginVO = null;
       try {
          HttpSession session = request.getSession();
          loginVO = (LoginVO) session.getAttribute("LoginVo");
          System.out.println("로그인 사용자 정보 : " + loginVO.getmNo());
          
          //   로그인 사용자 닉네임을 ReplyVO에 설정. lsy. 171117
          vo.setmNo(loginVO.getmNo());
          vo.setNickname(loginVO.getNickname());
          System.out.println("Controller : "+vo.getNickname());
          service.addReply(vo);
          entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
       }catch(Exception e) {
          e.printStackTrace();
          entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
       return entity;
    }
	
	/*// 특정 게시물의 전체 댓글 목록 표시. lsy. 17-11-10
	@RequestMapping(value = "/all/{bNo}", method = RequestMethod.GET)
	  public ResponseEntity<List<ReplyVO>> list(
			  @PathVariable("bNo") int bNo) {

	    ResponseEntity<List<ReplyVO>> entity = null;
	    try {
	      entity = new ResponseEntity<>(service.listReply(bNo), HttpStatus.OK);

	    } catch (Exception e) {
	      e.printStackTrace();
	      entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    return entity;
	  }*/
	
	// 댓글의 수정 처리. lsy. 17-11-10
	@RequestMapping(value = "/{rNo}", 
			method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(
			@PathVariable("rNo") int rNo,
			@RequestBody ReplyVO vo) {
		
		ResponseEntity<String> entity = null;
		try {
			logger.info("댓글수정");
			vo.setrNo(rNo);
			service.modifyReply(vo);
			
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(
					e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 댓글 삭제 처리. lsy. 17-11-10
	@RequestMapping(value = "/{rNo}", method = RequestMethod.DELETE)
	  public ResponseEntity<String> remove(@PathVariable("rNo") int rNo, @RequestBody ReplyVO vo) {

	    ResponseEntity<String> entity = null;
	    try {
	      service.removeReply(rNo, vo.getbNo());
	      logger.info("댓글삭제");
	      entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	    } catch (Exception e) {
	      e.printStackTrace();
	      entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	    return entity;
	  }
	
	// 댓글 페이징 처리. lsy. 17-11-13
	@RequestMapping(value = "/{bNo}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(
			@PathVariable("bNo") int bNo,
			@PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<ReplyVO> list = service.listReplyPage(bNo, cri);
			
			map.put("list", list);
			
			int replyCount = service.count(bNo);
			pageMaker.setTotalCount(replyCount);
			
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
