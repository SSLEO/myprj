package com.gettoere.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gettoere.vo.AdminBlackVO;
import com.gettoere.vo.AdminMemberVO;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.LoginVO;
import com.gettoere.vo.ManagerBoardVO;
import com.gettoere.vo.ManagerContentVO;
import com.gettoere.vo.ManagerListVO;
import com.gettoere.vo.ManagerVO;


@Repository
public class ManagerDAOImpl implements ManagerDAO {
	
	@Inject
	private SqlSession session;
	private static String namespace = "com.gettoere.mapper.ManagerMapper";

	//게시판 관리. lsj. 171117
	@Override
	@Transactional
	public void createBoard(ManagerVO vo) throws Exception {
		int check = session.insert(namespace + ".createBoard", vo);
		// 게시판 새로 개설시 해당 멤버의 등급은 게시판 관리자 등급(grade = 2) 으로 변경되고
		// 본인의 게시판 번호를 업뎃하는 기능을 추가함. ssh. 171122
		if(check > 0) {
			session.update(namespace + ".updateMember", vo);
		}
	}

	@Override
	public void updateBoard(ManagerVO vo) throws Exception {
		session.update(namespace + ".updateBoard", vo);
		
	}

	@Override
	public void delBoard(Integer lNo) throws Exception {
		session.update(namespace + ".delBoard", lNo);
		//게시판을 삭제하면 관련 멤버들의 grade와 lNoManager를 0으로 변경함. lsj. 171127
		session.update(namespace + ".deleteMemBoard", lNo);
		
	}
	
	@Override
	public ManagerVO readBoard(Integer lNo) throws Exception{
		return session.selectOne(namespace + ".readBoard", lNo);
	}
	
	@Override
	public int chkBoardName(ManagerVO vo) throws Exception {
		return session.selectOne(namespace + ".chkBoardName", vo);
	}
	
	//Manager
	@Override
	public List<ManagerVO> listManager(int lNo) throws Exception {
		return session.selectList(namespace + ".listManager", lNo);
	}
	
	@Override
	public int downManager(LoginVO lVo) throws Exception {
		// TODO Auto-generated method stub
		return session.update(namespace + ".downManager", lVo);
	}
	@Override
	public int upManager(ManagerVO mVo) throws Exception {
		// TODO Auto-generated method stub
		// listBoard의 mNo 수정. jun. 17.12.05
		int l = session.update(namespace + ".upManagerListBoard", mVo);
		int m =session.update(namespace + ".upManager", mVo);
		if(l == m) {
			return l;
		} else {
			return 0;
		}
	}
	@Override
	public int delManager(String mNos) throws Exception {
		return session.update(namespace + ".delManager", mNos);
	}
	
	@Override
	public List<ManagerVO> findMember(ManagerVO mVo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".findMember", mVo);
	}
	@Override
	public int addManager(ManagerVO vo) throws Exception {
		return session.insert(namespace + ".addManager", vo);
	}
	
	//Outlist
	@Override
	public void addOutlist(ManagerListVO vo) throws Exception {
		session.insert(namespace + ".addOutlist", vo);
		
	}

	@Override
	public void delOutlist(Integer mno) throws Exception {
		session.delete(namespace + ".delOutlist", mno);
		
	}

	@Override
	public List<ManagerListVO> listOutlist(int mNo) throws Exception {
		return session.selectList(namespace + ".listOutlist", mNo);
	}
	
	//List Member
	@Override
	public List<ManagerListVO> listMember() throws Exception {
		return session.selectList(namespace + ".listMember");
	}

	// 매니저 게시판관리 테이블 페이징. jun. 17.11.22
	@Override
	public List<ManagerVO> contentMan(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".contentMan", cri);
	}
	@Override
	public int contentCountPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".contentCountPaging", cri);
	}

	// 게시판리스트 삭제기능. jun. 17.11.16
	@Override
	public void contentDelete(String key) throws Exception {
		// TODO Auto-generated method stub
		session.selectOne(namespace+".contentDelete", key);
	}

	// 매니저 블랙리스트 테이블 페이징. jun. 17.11.23
	@Override
	public List<AdminBlackVO> outlistMan(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".outlistMan", cri);
	}
	@Override
	public int outlistCountPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".outlistCountPaging", cri);
	}

	// 회원 검색. jun. 17.11.23
	@Override
	public List<AdminMemberVO> searchMember(AdminMemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".searchMember", vo);
	}
	
	@Override
	public int checkBan(ManagerVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".checkBan", vo);
	}



}
