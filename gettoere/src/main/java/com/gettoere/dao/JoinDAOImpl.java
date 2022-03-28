package com.gettoere.dao;



import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gettoere.vo.JoinVO;

@Repository
public class JoinDAOImpl implements JoinDAO{

	@Inject
	private SqlSession session;
	private static String namespace = "com.gettoere.mapper.JoinMapper";
	@Override
	public void create(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".create",vo);
	}
	@Override
	public int idCheck(JoinVO vo) throws Exception {
//		System.out.println("DAO:"+vo.getId());
		return session.selectOne(namespace+".idCheck",vo);
		
		
	}
	@Override
	public int nicknameCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("DAO:"+vo.getNickname());
		return session.selectOne(namespace+".nicknameCheck",vo);
	}
	@Override
	public int emailCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DAO:"+vo.getEmail());
		return session.selectOne(namespace+".emailCheck",vo);
	}
	@Override
	public String idFind(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DAO"+vo.getName()+"ee"+vo.getEmail());
		return session.selectOne(namespace+".idFind",vo);
		
	}
	@Override
	public String pwFind(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".pwFind",vo);
	}
	
	// 비밀번호 찾기 이메일 인증 ktg 17.11.27
	@Override
	public int pwFindCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".pwFindCheck", vo);
	}
	// 랜덤값 temp에 저장 ktg 17.11.27
	@Override
	public void tempUp(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".tempUp", vo);
	}
	// 새 비밀번호 설정 userCheck ktg 17.11.27
	@Override
	public int userCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".userCheck", vo);
	}
	// 새 비밀번호 Update ktg 17.11.27
	@Override
	public void newPwUp(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".newPwUp", vo);
	}
	// 새 비밀번호 Update 후 temp에 저장 ktg 17.11.27
	@Override
	public void newTempUp(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".newTempUp", vo);
	}
	//개인정보 수정 kyo 17.11.30
	@Override
	public void Update(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".Update", vo);	
	}
	// 핸드폰 중복 체크 ktg 17.11.30
	@Override
	public int phoneCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".phoneCheck",vo);
	}
	@Override
	public int phoneUpCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".phoneUpCheck",vo);
	}
	@Override
	public int NicknameUpCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".nicknameUpCheck",vo);
	}
	@Override
	public int EmailUpCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".emailUpCheck",vo);
	}
}
