package com.gettoere.service;

import java.util.List;

import com.gettoere.vo.AdminBoardVO;
import com.gettoere.vo.AttachVO;
import com.gettoere.vo.BoardVO;
import com.gettoere.vo.Criteria;

public interface BoardService {
	public int regist(BoardVO board) throws Exception;
	
	public void registAttach(AttachVO vo) throws Exception;
	
	public void modifyAttach(AttachVO aVo) throws Exception;
	
	public BoardVO read(int bNo) throws Exception;
	
	public int modify(BoardVO board)throws Exception;
	
	public void remove(int bNo)throws Exception;
	
//	public List<BoardVO> listAll() throws Exception;
	
	//게시판 목록및 검색처리 kyo 17-11-10
	public List<BoardVO> listCriteria(Criteria cri)throws Exception;
	
	//게시판 페이징처리  kyo 17-11-10
	public int listCountCriteria(Criteria cri)throws Exception;

	//게시판 제목 검색. ssh. 171113
	public List<BoardVO> searchBoardName(BoardVO vo)throws Exception;
	
	//첨부파일 읽기. lsy. 17-11-14
	public AttachVO readAttach(int bNo)throws Exception;

	// 메인화면 인기글 목록 ktg 17.11.13
	public List<BoardVO> bestPosts() throws Exception;

	// 메인화면 인기게시판 목록 ktg 17.11.14
	public List<BoardVO> bestBoard() throws Exception;

	// 메인화면 죽어가는 게시판 목록 ktg 17.11.14
	public List<BoardVO> dyingBoard() throws Exception;
	
	// 헤더 부분 새로운 게시판 슬라이더 ktg 17.11.15
	public List<BoardVO> newBoardSlider(BoardVO vo) throws Exception;
	
	// 게시판 정보. ssh. 171120
	public BoardVO getListBoard(Criteria cri) throws Exception;

	public BoardVO upDownRecommend(BoardVO vo) throws Exception;
	
	//게시판 공지글 kyo 17.11.22
	public List<BoardVO> noticeList(Criteria cri)throws Exception;
	
	//리스트 게시판 목록 및 검색처리 kyo 17.11.22
	public List<AdminBoardVO> listBoardCriteria(Criteria cri)throws Exception;
	
	//리스트게시판 페이징처리  kyo 17-11-10
	public int listBoardCountCriteria(Criteria cri)throws Exception;

	//내가 쓴글 목록 및 검색처리. ssh. 171201
	public List<BoardVO> myList(Criteria cri) throws Exception;

	//내가 쓴글 목록 및 검색처리 관련 글수. ssh. 171201
	public int myListCount(Criteria cri) throws Exception;

	//내가 쓴 댓글 목록 및 검색처리. ssh. 171201
	public List<BoardVO> myReply(Criteria cri) throws Exception;

	//내가 쓴 댓글 목록 및 검색처리 관련 글수. ssh. 171201
	public int myReplyCount(Criteria cri) throws Exception;

	//최신 글 목록 및 검색처리. ssh. 171204
	public List<BoardVO> recentList(Criteria cri);

	//최신 글 목록 및 검색처리 관련 글수. ssh. 171204
	public int recentListCount(Criteria cri);
}
