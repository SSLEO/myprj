package com.gettoere.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.gettoere.dao.BoardDAO;
import com.gettoere.vo.AdminBoardVO;
import com.gettoere.vo.AttachVO;
import com.gettoere.vo.BoardVO;
import com.gettoere.vo.Criteria;

@Service
public class BoardServiceImpl implements BoardService{

	@Inject
	private BoardDAO dao;

	@Override
	public int regist(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		return (int)dao.create(board);
	}
	
	@Override
	public void registAttach(AttachVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.createAttach(vo);
	}
	
	@Override
	public void modifyAttach(AttachVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.updateAttach(vo);
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public BoardVO read(int bNo) throws Exception {
		// TODO Auto-generated method stub
		dao.updateViewCnt(bNo);
		return dao.read(bNo);
	}

	@Override
	public int modify(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		return dao.update(board);
	}

	@Override
	public void remove(int bNo) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(bNo);
	}

/*	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("BoardServiceImpl");
		return dao.listAll();
	}*/

	//게시판 목록및 검색처리 kyo 17-11-10
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		
		return dao.listCriteria(cri);
	}

	//게시판 페이징처리  kyo 17-11-10
	@Override
	public int listCountCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.countPaging(cri);
	}

	@Override
	public List<BoardVO> searchBoardName(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchBoardName(vo);
	}

	@Override
	public AttachVO readAttach(int bNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.readAttach(bNo);
	}
	
	// 메인화면 인기글 목록 ktg 17.11.13
	@Override
	public List<BoardVO> bestPosts() throws Exception {
		// TODO Auto-generated method stub
		return dao.bestPosts();
	}
	
	// 메인화면 인기게시판 목록 ktg 17.11.14
	@Override
	public List<BoardVO> bestBoard() throws Exception {
		// TODO Auto-generated method stub
		return dao.bestBoard();
	}

	// 메인화면 죽어가는 게시판 목록 ktg 17.11.14
	@Override
	public List<BoardVO> dyingBoard() throws Exception {
		// TODO Auto-generated method stub
		return dao.dyingBoard();
	}

	// 헤더부분 새로운 게시판 목록 슬라이더 ktg 17.11.15
	@Override
	public List<BoardVO> newBoardSlider(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.newBoardSlider(vo);
	}
	
	@Override
	public BoardVO getListBoard(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.getListBoard(cri);
	}

	@Override
	public BoardVO upDownRecommend(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.upDownRecommend(vo);
	}

	//게시판 공지글 kyo 17.11.22
	@Override
	public List<BoardVO> noticeList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.noticeList(cri);
	}

	@Override
	public List<AdminBoardVO> listBoardCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listBoardCriteria(cri);
	}

	@Override
	public int listBoardCountCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listCountPaging(cri);
	}

	@Override
	public List<BoardVO> myList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.myList(cri);
	}

	@Override
	public int myListCount(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.myListCount(cri);
	}

	@Override
	public List<BoardVO> myReply(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.myReply(cri);
	}

	@Override
	public int myReplyCount(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.myReplyCount(cri);
	}

	@Override
	public List<BoardVO> recentList(Criteria cri) {
		// TODO Auto-generated method stub
		return dao.recentList(cri);
	}

	@Override
	public int recentListCount(Criteria cri) {
		// TODO Auto-generated method stub
		return dao.recentListCount(cri);
	}

}
