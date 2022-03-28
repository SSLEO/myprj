package com.gettoere.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gettoere.vo.LoginVO;

//17.11.08 로그인 DAO ktg

@Repository
public class LoginDAOImpl implements LoginDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "com.gettoere.mapper.LoginMapper";

	@Override
	public boolean loginCheck(LoginVO loginVO) {
		// TODO Auto-generated method stub
		int count = Integer.parseInt(sqlSession.selectOne(namespace + ".loginCheck", loginVO).toString());
		int totalCount = sqlSession.selectOne(namespace + ".totalAccount");
		if (totalCount > 0) {
			if(count > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public LoginVO login(String id) throws Exception {
		// TODO Auto-generated method stub
		
		return sqlSession.selectOne(namespace + ".loginSuccess", id);
	}
	
    @Override
    public boolean loginCheck2(LoginVO vo) {
        String name = sqlSession.selectOne(namespace + ".loginCheck2", vo);
        return (name == null) ? false : true;
    }
    
    @Override
    public LoginVO viewMember(LoginVO vo) {
        return sqlSession.selectOne(namespace + ".viewMember", vo);
    }
    
}
