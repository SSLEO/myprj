package com.gettoere.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gettoere.dao.AdminDAO;
import com.gettoere.vo.AdminBlackVO;
import com.gettoere.vo.AdminBoardVO;
import com.gettoere.vo.AdminMemberVO;
import com.gettoere.vo.Criteria;


@Service
public class AdminServiceImpl implements AdminService {
	@Inject
	private AdminDAO dao;

	// 최고관리자 회원리스트. jun. 17.11.09
//	@Override
//	public List<AdminMemberVO> memberList() throws Exception {
//		// TODO Auto-generated method stub
//		return dao.memberList();
//	}

	// 최고관리자 게시판리스트. jun. 17.11.10
//	@Override
//	public List<AdminBoardVO> boardList() throws Exception {
//		// TODO Auto-generated method stub
//		return dao.boardList();
//	}

	// 최고관리자 회원리스트 페이징. jun. 17.11.13
	@Override
	public List<AdminMemberVO> memberList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.memberList(cri);
	}
	@Override
	public int memberCountPaging(Criteria cri) throws Exception{
		return dao.memberCountPaging(cri);
	}
	
	// 최고관리자 게시판테이블 페이징. jun. 17.11.13
	@Override
	public List<AdminBoardVO> boardList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.boardList(cri);
	}
	@Override
	public int boardCountPaging(Criteria cri) throws Exception{
		return dao.boardCountPaging(cri);
	}
	
	// 최고관리자 블랙리스트 페이징. jun. 17.11.13
	@Override
	public List<AdminBlackVO> blackList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.blackList(cri);
	}
	@Override
	public int blackCountPaging(Criteria cri) throws Exception{
		return dao.blackCountPaging(cri);
	}
	
	// 최고관리자 금지어리스트 페이징. jun. 17.11.13
	public List<AdminBlackVO> tabooWordList(Criteria cri) throws Exception{
		return dao.tabooWordList(cri);
	}
	public int tabooWordCountPaging(Criteria cri) throws Exception{
		return dao.tabooWordCountPaging(cri);
	}
	
	// 최고관리자 금지어삭제. jun. 17.11.15
	@Override
	public String tabooWordDelete(String key) throws Exception {
		// TODO Auto-generated method stub
		return dao.tabooWordDelete(key);
	}
	
	// 최고관리자 금지어생성. jun. 17.11.16
	@Override
	public String tabooWordInsert(String input) throws Exception{
		// TODO Auto-generated method stub
		return dao.tabooWordInsert(input);
	}
	
	// 최고관리자 게시판 정지 해제시 이름 비교. jun. 17.12.05
	@Override
	public int chkBoardName(String[] lNo1) throws Exception {
		// TODO Auto-generated method stub
		return dao.chkBoardName(lNo1);
	}
	
	// 최고관리자 게시판  정지 해제. jun. 17.11.23
	@Override
	public void boardStartStop(String key, String msg) throws Exception {
		// TODO Auto-generated method stub
		dao.boardStartStop(key, msg);
	}
	
	// 블랙리스트 면제기능. jun. 17.11.16
	@Override
	public void memberFree(String key, String lNo) throws Exception {
		// TODO Auto-generated method stub
		dao.memberFree(key, lNo);
	}
	
	// 회원 블랙리스트 추가기능. jun. 17.11.16
	@Override
	public void blackListAdd(String[] keyWord, String input, String lNo) throws Exception {
		// TODO Auto-generated method stub
		dao.blackListAdd(keyWord, input, lNo);
	}
	
	// 게시판리스트 메시지 추가. jun. 17.11.23
	@Override
	public void boardMsg(String key, String msg) throws Exception {
		// TODO Auto-generated method stub
		dao.boardMsg(key, msg);
	}
}
