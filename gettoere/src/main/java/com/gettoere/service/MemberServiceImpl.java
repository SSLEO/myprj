package com.gettoere.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gettoere.dao.MemberDAO;
import com.gettoere.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	private MemberDAO dao;

	@Override
	public void regist(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemberVO read(String userid) throws Exception {
		// TODO Auto-generated method stub
		return dao.readMember(userid);
	}

}
