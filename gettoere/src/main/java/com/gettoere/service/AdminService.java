package com.gettoere.service;

import java.util.List;

import com.gettoere.vo.AdminBlackVO;
import com.gettoere.vo.AdminBoardVO;
import com.gettoere.vo.AdminMemberVO;
import com.gettoere.vo.Criteria;

public interface AdminService {
	// 최고관리자 회원리스트. jun. 17.11.09
//	public List<AdminMemberVO> memberList() throws Exception;
	// 최고관리자 게시판리스트. jun. 17.11.10
//	public List<AdminBoardVO> boardList() throws Exception;
	
	// 최고관리자 회원리스트 페이징. jun. 17.11.13
	public List<AdminMemberVO> memberList(Criteria cri) throws Exception;
	public int memberCountPaging(Criteria cri) throws Exception;
	
	// 최고관리자 게시판테이블 페이징. jun. 17.11.13
	public List<AdminBoardVO> boardList(Criteria cri) throws Exception;
	public int boardCountPaging(Criteria cri) throws Exception;
	
	// 최고관리자 블랙리스트 페이징. jun. 17.11.13
	public List<AdminBlackVO> blackList(Criteria cri) throws Exception;
	public int blackCountPaging(Criteria cri) throws Exception;
	
	// 최고관리자 금지어리스트 페이징. jun. 17.11.13
	public List<AdminBlackVO> tabooWordList(Criteria cri) throws Exception;
	public int tabooWordCountPaging(Criteria cri) throws Exception;
	
	// 최고관리자 금지어삭제. jun. 17.11.15
	public String tabooWordDelete(String key) throws Exception;
	
	// 최고관리자 금지어생성. jun. 17.11.16
	public String tabooWordInsert(String input) throws Exception;
	
	// 최고관리자 게시판  정지 해제. jun. 17.11.23
	public void boardStartStop(String key, String msg) throws Exception;

	// 최고관리자 게시판 정지 해제시 이름 비교. jun. 17.12.05
	public int chkBoardName(String[] lNo1) throws Exception;
	
	// 블랙리스트 면제기능. jun. 17.11.16
	public void memberFree(String key, String lNo) throws Exception;
	
	// 회원 블랙리스트 추가기능. jun. 17.11.16
	public void blackListAdd(String[] keyWord, String input, String lNo) throws Exception;
	
	// 게시판리스트 메시지 추가. jun. 17.11.23
	public void boardMsg(String key, String msg) throws Exception;
}
