package com.gettoere.service;

import com.gettoere.vo.MemberVO;

public interface MemberService {
	public void regist(MemberVO vo) throws Exception;
	
	public MemberVO read(String userid) throws Exception;
}
