package com.gettoere.service;

import com.gettoere.vo.LoginVO;

//로그인 서비스 - ktg 17.11.13
public interface LoginService {
	
	public boolean loginCheck(LoginVO loginVO);
	
	public LoginVO login(String id) throws Exception;
	
    public boolean loginCheck2(LoginVO vo);

	LoginVO viewMember(LoginVO vo);
	
	
}
