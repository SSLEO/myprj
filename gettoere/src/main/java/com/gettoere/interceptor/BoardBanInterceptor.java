package com.gettoere.interceptor;

import java.util.Enumeration;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gettoere.service.BoardService;
import com.gettoere.service.ManagerService;
import com.gettoere.vo.BoardVO;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.LoginVO;
import com.gettoere.vo.ManagerVO;

public class BoardBanInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(BoardBanInterceptor.class);
	
	@Inject
	private ManagerService mService;
	
	@Inject
	private BoardService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("Board Ban interceptor pre ..........");
		
		HttpSession session2 = request.getSession();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			//response.sendRedirect("/noLogin");
		}else {
			
			LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
			
			
			if(request.getParameter("lNo") != null) {
				
				int lNo = Integer.parseInt(request.getParameter("lNo").toString());
				
				ManagerVO mVo = new ManagerVO();
				mVo.setmNo(lVo.getmNo());
				mVo.setlNo(lNo);
				int check = mService.checkBan(mVo);
				
				if(check > 0) {
					//게시판 관리자로부터 밴 된 사람은 페이지 이동을 막음. ssh. 171128
					response.sendRedirect("/boardBan");
				}
			}
		}
		
		if(request.getParameter("lNo") != null) {
			int lNo = Integer.parseInt(request.getParameter("lNo").toString());
			
			Criteria cri = new Criteria();
			cri.setlNo(lNo);
			BoardVO bVo = service.getListBoard(cri);
			if ( bVo==null || bVo.getStatus() != 0 ) {
				//정지된 게시판은 페이지 이동을 막음. ssh. 171128
				response.sendRedirect("/noBoard");
			}
		}

		return true;
	}
}
