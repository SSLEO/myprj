package com.gettoere.service;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.gettoere.dao.LoginDAO;
import com.gettoere.vo.LoginVO;

// 로그인 서비스 - ktg 17.11.13
@Service
public class LoginServiceImpl implements LoginService {
	
	@Inject
	private LoginDAO dao;

	@Override
	public boolean loginCheck(LoginVO loginVO) {
		// TODO Auto-generated method stub
		return dao.loginCheck(loginVO);
	}
	
	@Override
	public LoginVO login(String id) throws Exception {
		return dao.login(id);
	}
	
	
    @Override
    public boolean loginCheck2(LoginVO vo) {
        boolean result = dao.loginCheck(vo);
        
        return result;
    }
    
    @Override
    public LoginVO viewMember(LoginVO vo) {
        return dao.viewMember(vo);
    }

}
