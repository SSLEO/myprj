package com.gettoere.dao;


import com.gettoere.vo.LoginVO;

//17.11.08 로그인 DAO ktg
public interface LoginDAO {
	public boolean loginCheck(LoginVO loginVO);
	
	public LoginVO login(String id) throws Exception;
	
    public LoginVO viewMember(LoginVO vo);

	boolean loginCheck2(LoginVO vo);
	
}
