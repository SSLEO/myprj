package com.gettoere.dao;

import com.gettoere.vo.BoardVO;
import com.gettoere.vo.JoinVO;

public interface JoinDAO {

	public void create(JoinVO vo) throws Exception;

	public int idCheck(JoinVO vo) throws Exception;

	public int nicknameCheck(JoinVO vo) throws Exception;

	public int emailCheck(JoinVO vo) throws Exception;

	public String idFind(JoinVO vo) throws Exception;

	public String pwFind(JoinVO vo) throws Exception;

	// 비밀번호 찾기 이메일 인증 ktg 17.11.27
	public int pwFindCheck(JoinVO vo) throws Exception;

	// 새 비밀번호 설정 userCheck ktg 17.11.27
	public int userCheck(JoinVO vo) throws Exception;

	// 새 비밀번호 Update ktg 17.11.27
	public void newPwUp(JoinVO vo) throws Exception;

	// 랜덤값 temp에 저장 ktg 17.11.27
	public void tempUp(JoinVO vo) throws Exception;
	
	// 새 비밀번호 Update 후 temp에 저장 ktg 17.11.27
	public void newTempUp(JoinVO vo) throws Exception;
	// 개인정보 변경 kyo 17.11.30
	public void Update(JoinVO vo) throws Exception;

	// 핸드폰 중복 체크 ktg 17.11.30
	public int phoneCheck(JoinVO vo) throws Exception;
	//개인정보 수정 폰중복체크 kyo 17.12.1
	public int phoneUpCheck(JoinVO vo) throws Exception;
	//개인정보 수정 닉네임중복체크 kyo 17.12.1
	public int NicknameUpCheck(JoinVO vo) throws Exception;
	//개인정보 수정 이메일중복체크 kyo 17.12.1
	public int EmailUpCheck(JoinVO vo) throws Exception;
	
}
