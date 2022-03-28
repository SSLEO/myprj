package com.gettoere.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gettoere.dao.ManagerDAO;
import com.gettoere.vo.AdminBlackVO;
import com.gettoere.vo.AdminMemberVO;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.LoginVO;
import com.gettoere.vo.ManagerBoardVO;
import com.gettoere.vo.ManagerContentVO;
import com.gettoere.vo.ManagerListVO;
import com.gettoere.vo.ManagerVO;

@Service
public class ManagerServiceImpl implements ManagerService{
	
	
	@Inject
	private ManagerDAO dao;

	//Board
	@Override
	public void createBoard(ManagerVO vo) throws Exception {
		dao.createBoard(vo);
		
	}

	@Override
	public void updateBoard(ManagerVO vo) throws Exception {
		dao.updateBoard(vo);
		
	}

	@Override
	public void delBoard(Integer lNo) throws Exception {
		dao.delBoard(lNo);
		
	}
	
	@Override
	public ManagerVO readBoard(Integer lNo) throws Exception{
		return dao.readBoard(lNo);
	}
	
	@Override
	public int chkBoardName(ManagerVO vo) throws Exception {
		return dao.chkBoardName(vo);
	}

	//Contents
	
	
	//Manager. ssh. 171124
	@Override
	public List<ManagerVO> listManager(int lNo) throws Exception {
		return dao.listManager(lNo);
	}
	@Override
	public int downManager(LoginVO lVo) throws Exception {
		// TODO Auto-generated method stub
		return dao.downManager(lVo);
	}
	@Override
	public int upManager(ManagerVO mVo) throws Exception {
		// TODO Auto-generated method stub
		return dao.upManager(mVo);
	}
	@Override
	public int delManager(String mNos) throws Exception {
		return dao.delManager(mNos);
	}
	@Override
	public List<ManagerVO> findMember(ManagerVO mVo) throws Exception {
		// TODO Auto-generated method stub
		return dao.findMember(mVo);
	}
	@Override
	public int addManager(ManagerVO vo) throws Exception {
		return dao.addManager(vo);
		
	}

	
	//Outlist
	@Override
	public void addOutlist(ManagerListVO vo) throws Exception {
		dao.addOutlist(vo);
		
	}

	@Override
	public void delOutlist(Integer mno) throws Exception {
		dao.delOutlist(mno);
		
	}

	@Override
	public List<ManagerListVO> listOutlist(int mNo) throws Exception {
		return dao.listOutlist(mNo);
	}

	
	//List Member
	@Override
	public List<ManagerListVO> listMember() throws Exception {
		return dao.listMember();
	}

	// 매니저 게시판관리 테이블 페이징. jun. 17.11.22
	@Override
	public List<ManagerVO> contentMan(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.contentMan(cri);
	}
	@Override
	public int contentCountPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.contentCountPaging(cri);
	}

	// 게시판리스트 삭제기능. jun. 17.11.16
	@Override
	public void contentDelete(String key) throws Exception {
		// TODO Auto-generated method stub
		dao.contentDelete(key);
	}

	// 매니저 블랙리스트 테이블 페이징. jun. 17.11.23
	@Override
	public List<AdminBlackVO> outlistMan(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.outlistMan(cri);
	}
	@Override
	public int outlistCountPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.outlistCountPaging(cri);
	}

	// 회원 검색. jun. 17.11.23
	@Override
	public List<AdminMemberVO> searchMember(AdminMemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchMember(vo);
	}
	
	@Override
	public int checkBan(ManagerVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.checkBan(vo);
	}


	
}
