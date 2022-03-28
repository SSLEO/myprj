package com.gettoere.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gettoere.vo.AdminBoardVO;
import com.gettoere.vo.AttachVO;
import com.gettoere.vo.BoardVO;
import com.gettoere.vo.Criteria;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@Inject
	private SqlSession session;
	private static String namespace = "com.gettoere.mapper.BoardMapper";
	
	@Override
	public int create(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		int inCnt = session.insert(namespace+".create",vo);
		int rst=0;
		if(inCnt > 0) {	//insert 된 갯수를 체크
			rst = vo.getbNo();	//insert가 되면 vo에 bNo를 저장된 것을 가져옴(mybatis 참고). ssh. 171113
		}
		return rst;
	}
	
	@Override
	public void createAttach(AttachVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".createAttach",vo);
	}
	
	@Override
	public void updateAttach(AttachVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".updateAttach",vo);
	}

	@Override
	@Transactional
	public BoardVO read(int bNo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".read",bNo);
	}

	@Override
	public int update(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.update(namespace+".update",vo);
		
	}

	@Override
	public void delete(int bNo) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace+".delete",bNo);
		
	}

// ��ü ����Ʈ ���� ����¡ó���¾������Ѱ�kyo 17-11-10
/*	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("BoardDAOImpl");
		return session.selectList(namespace+".listAll");
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		// TODO Auto-generated method stub
		if(page<=0) {
			page =1;
		}
		
		page = (page-1) * 10;
		
		return session.selectList(namespace+".listPage",page);
	}
*/
	
	//게시판 목록및 검색처리 kyo 17-11-10
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".listCriteria",cri);
	}

	//게시판 페이징처리  kyo 17-11-10
	@Override
	public int countPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".countPaging",cri);
	}

	@Override
	public List<BoardVO> searchBoardName(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".searchBoardName", vo);
	}

	@Override
	public AttachVO readAttach(int bNo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".readAttach", bNo);
	}

	// 메인화면 인기글 목록 ktg 17.11.13
	@Override
	public List<BoardVO> bestPosts() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".bestPosts");
	}

	// 메인화면 인기게시판 목록 ktg 17.11.14
	@Override
	public List<BoardVO> bestBoard() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".bestBoards");
	}

	// 메인화면 죽어가는 게시판 목록 ktg 17.11.14
	@Override
	public List<BoardVO> dyingBoard() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".dyingBoards");
	}

	// 헤더부분 새로운 게시판 목록 슬라이더 ktg 17.11.15
	@Override
	public List<BoardVO> newBoardSlider(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".newBoardSlider");
	}

	@Override
	public BoardVO getListBoard(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".readListBoard", cri);
	}
	
	@Override
	public void updateViewCnt(int bNo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace +".updateViewCnt", bNo);
	}

	@Override
	@Transactional
	public BoardVO upDownRecommend(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		int rst = session.selectOne(namespace+".upDownCheck", vo);
		if(rst > 0) {
			BoardVO bVo = new BoardVO();
			bVo.setMessage("fail");
			return bVo;
		}else {
			if (vo.getBestCnt() > 0) {
				session.update(namespace+".upBestCnt",vo);
				session.insert(namespace+".bestWhoBest",vo);
			}else if(vo.getWorstCnt() > 0) {
				session.update(namespace+".upWorstCnt",vo);
				session.insert(namespace+".worstWhoBest",vo);
			}
			return session.selectOne(namespace+".read",vo.getbNo());
		}
	}

	//게시판 공지글 kyo 17.11.22
	@Override
	public List<BoardVO> noticeList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".noticeList",cri);
	}

	@Override
	public List<AdminBoardVO> listBoardCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".listBoardCriteria",cri);
	}

	@Override
	public int listCountPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".listCountPaging",cri);
	}

	@Override
	public List<BoardVO> myList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".myList",cri);
	}

	@Override
	public int myListCount(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".myListCount",cri);
	}

	@Override
	public List<BoardVO> myReply(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".myReply",cri);
	}

	@Override
	public int myReplyCount(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".myReplyCount",cri);
	}

	@Override
	public List<BoardVO> recentList(Criteria cri) {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".recentList",cri);
	}

	@Override
	public int recentListCount(Criteria cri) {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".recentListCount",cri);
	}
}
