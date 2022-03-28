package com.gettoere.dao;

import java.util.List;

import com.gettoere.vo.AdminBlackVO;
import com.gettoere.vo.AdminMemberVO;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.LoginVO;
import com.gettoere.vo.ManagerBoardVO;
import com.gettoere.vo.ManagerContentVO;
import com.gettoere.vo.ManagerListVO;
import com.gettoere.vo.ManagerVO;

public interface ManagerDAO {
	
	//Board
	public void createBoard(ManagerVO vo) throws Exception;
	
	public void updateBoard(ManagerVO vo) throws Exception;
	
	public void delBoard(Integer lNo) throws Exception;
	
	public ManagerVO readBoard(Integer lNo) throws Exception;
	
	public int chkBoardName(ManagerVO vo) throws Exception;


	//Manager
	public List<ManagerVO> listManager(int lNo) throws Exception;
	
	public int downManager(LoginVO lVo) throws Exception;
	public int upManager(ManagerVO mVo) throws Exception;

	public int delManager(String mNos)throws Exception;
	
	public List<ManagerVO> findMember(ManagerVO mVo) throws Exception;
	public int addManager(ManagerVO vo) throws Exception;
	
	
	
	//Outlist
	public void addOutlist(ManagerListVO vo) throws Exception;
	
	public void delOutlist(Integer mno)throws Exception;
	
	public List<ManagerListVO> listOutlist(int mNo) throws Exception;
	
	
	//List Member
	public List<ManagerListVO> listMember() throws Exception;

	// 매니저 게시판관리 테이블 페이징. jun. 17.11.22
	public List<ManagerVO> contentMan(Criteria cri) throws Exception;
	public int contentCountPaging(Criteria cri) throws Exception;

	// 게시판리스트 삭제기능. jun. 17.11.16
	public void contentDelete(String key) throws Exception;

	// 매니저 블랙리스트 테이블 페이징. jun. 17.11.23
	public List<AdminBlackVO> outlistMan(Criteria cri) throws Exception;
	public int outlistCountPaging(Criteria cri) throws Exception;

	// 회원 검색. jun. 17.11.23
	public List<AdminMemberVO> searchMember(AdminMemberVO vo) throws Exception;

	public int checkBan(ManagerVO vo) throws Exception;

}
