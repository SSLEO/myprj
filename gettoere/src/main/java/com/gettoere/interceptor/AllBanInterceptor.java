package com.gettoere.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gettoere.vo.LoginVO;

// 최고관리자로부터 밴 된 사람은 페이지 이동을 막음. ssh. 171128
public class AllBanInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(AllBanInterceptor.class);
			
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("All Ban interceptor pre ..........");
		
		HttpSession session2 = request.getSession();
		
		if(session2 == null || session2.getAttribute("LoginVo") == null) {
			logger.info("All Ban interceptor pre : 로그아웃 상태");
			//response.sendRedirect("/noLogin");
		}else {
			
			LoginVO lVo = (LoginVO) session2.getAttribute("LoginVo");
			
			if(lVo.getStatus() == 1) {
				response.sendRedirect("/allBan");
			}
		}

		return true;
	}
}
