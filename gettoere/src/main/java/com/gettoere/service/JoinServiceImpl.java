package com.gettoere.service;



import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gettoere.dao.JoinDAO;
import com.gettoere.vo.JoinVO;

@Service
public class JoinServiceImpl implements JoinService{

	
	@Inject
	private JoinDAO dao;

	@Override
	public void regist(JoinVO vo) throws Exception {
		dao.create(vo);
		
	}

	@Override
	public int idCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("service:"+vo.getId());
		return dao.idCheck(vo);
		
	}

	@Override
	public int nicknameCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("service:"+vo.getNickname());
		return dao.nicknameCheck(vo);
	}

	@Override
	public int emailCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("service:"+vo.getEmail());
		return dao.emailCheck(vo);
	}

	@Override
	public String idFind(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("Service"+vo.getName()+"ee"+vo.getEmail());
		return dao.idFind(vo);
	}

	@Override
	public String pwFind(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.pwFind(vo);
	}

	// 비밀번호 찾기 이메일 인증 ktg 17.11.27
	@Override
	public int pwFindCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.pwFindCheck(vo);
	}

	// 랜덤값 temp에 저장 ktg 17.11.27
	@Override
	public void tempUp(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.tempUp(vo);
	}
	
	// 새 비밀번호 설정 userCheck ktg 17.11.27
	@Override
	public int userCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.userCheck(vo);
	}

	// 새 비밀번호 Update ktg 17.11.27
	@Override
	public void newPwUp(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.newPwUp(vo);
	}

	// 새 비밀번호 Update 후 temp에 저장 ktg 17.11.27
	@Override
	public void newTempUp(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.newTempUp(vo);
	}

	//개인정보 수정 kyo 17.11.30
	public void Update(JoinVO vo) throws Exception{
		dao.Update(vo);
		
	}

	// 핸드폰 중복 체크 ktg 17.11.30
	@Override
	public int phoneCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.phoneCheck(vo);
	}

	@Override
	public int phoneUpCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.phoneUpCheck(vo);
	}

	@Override
	public int NicknameUpCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.NicknameUpCheck(vo);
	}

	@Override
	public int EmailUpCheck(JoinVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.EmailUpCheck(vo);
	}
}
