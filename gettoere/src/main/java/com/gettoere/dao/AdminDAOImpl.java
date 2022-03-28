package com.gettoere.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gettoere.vo.AdminBlackVO;
import com.gettoere.vo.AdminBoardVO;
import com.gettoere.vo.AdminMemberVO;
import com.gettoere.vo.Criteria;

@Repository
public class AdminDAOImpl implements AdminDAO{
	@Inject
	private SqlSession session;
	private static String namespace = "com.gettoere.mapper.AdminMapper";
	
	// 최고관리자 회원리스트. jun. 17.11.09
//	@Override
//	public List<AdminMemberVO> memberList() throws Exception {
//		// TODO Auto-generated method stub
//		return session.selectList(namespace+".memberList");
//	}

	// 최고관리자 게시판리스트. jun. 17.11.10
//	@Override
//	public List<AdminBoardVO> boardList() throws Exception {
//		// TODO Auto-generated method stub
//		return session.selectList(namespace+".boardList");
//	}
	
	// 최고관리자 회원리스트 페이징. jun. 17.11.13
	@Override
	public List<AdminMemberVO> memberList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".memberList", cri);
	}
	@Override
	public int memberCountPaging(Criteria cri) throws Exception {
		return session.selectOne(namespace+".memberCountPaging", cri);
	}

	// 최고관리자 게시판테이블 페이징. jun. 17.11.13
	@Override
	public List<AdminBoardVO> boardList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".boardList", cri);
	}
	@Override
	public int boardCountPaging(Criteria cri) throws Exception {
		return session.selectOne(namespace+".boardCountPaging", cri);
	}
	
	// 최고관리자 블랙리스트 페이징. jun. 17.11.13
	@Override
	public List<AdminBlackVO> blackList(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".blackList", cri);
	}
	@Override
	public int blackCountPaging(Criteria cri) throws Exception{
		return session.selectOne(namespace+".blackCountPaging", cri);
	}
	
	// 최고관리자 금지어리스트 페이징. jun. 17.11.13
	@Override
	public List<AdminBlackVO> tabooWordList(Criteria cri) throws Exception{
		return session.selectList(namespace+".tabooWordList", cri);
	}
	@Override
	public int tabooWordCountPaging(Criteria cri) throws Exception{
		return session.selectOne(namespace+".tabooWordCountPaging", cri);
	}
	
	// 최고관리자 금지어삭제. jun. 17.11.15
	@Override
	public String tabooWordDelete(String key) throws Exception {
		// TODO Auto-generated method stub
//		for(int i=0; i< key.length; i++) {
//			System.out.println(key[i]);
//			session.selectOne(namespace+".tabooWordCheck", key[i]);
//		}
		System.out.println(key);
		return session.selectOne(namespace+".tabooWordDelete", key);
	}
	
	// 최고관리자 금지어생성. jun. 17.11.16
	@Override
	public String tabooWordInsert(String input) throws Exception {
		return session.selectOne(namespace+".tabooWordInsert", input);
	}
	
	// 최고관리자 게시판 정지 해제시 이름 비교. jun. 17.12.05
	@Override
	public int chkBoardName(String[] lNo1) throws Exception {
		// TODO Auto-generated method stub
		boolean chk = false;
		for(int i=0; i<lNo1.length; i++) {
			int result = session.selectOne(namespace+".chkBoardName",lNo1[i]);
			if(result == 1) {
				chk = true;
			}
		}
		if(chk)
			return 1;
		else
			return 0;
	}

	// 최고관리자 게시판 정지 해제. jun. 17.11.23
	@Override
	public void boardStartStop(String key, String msg) throws Exception {
		// TODO Auto-generated method stub
		if(msg.equals("stop")) {
			//정지
			session.selectOne(namespace+".boardStop", key);
		} else {
			//해제
			session.selectOne(namespace+".boardStart", key);
		}
	}
	
	// 블랙리스트 면제기능. jun. 17.11.16
	@Transactional
	@Override
	public void memberFree(String key, String lNo) throws Exception {
		// TODO Auto-generated method stub
		if(lNo==null) {
			lNo = "0";
			session.selectOne(namespace+".memberFree", key);
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("key", key);
		map.put("lNo", lNo);
		session.selectOne(namespace+".memberFreePickoutlist", map);
	}
	
	// 회원 블랙리스트 추가기능. jun. 17.11.16
	// 트랜잭션 추가. jun. 17.11.20
	// 관리자 블랙리스트 추가. jun, 17.11.24
	@Transactional
	@Override
	public void blackListAdd(String[] keyWord, String input, String lNo) throws Exception {
		// TODO Auto-generated method stub
		boolean isIndex = true;
		HashMap<String, String> map = new HashMap<>();
		for(int i=0; i<keyWord.length; i++) {
			if(lNo==null) {
				lNo = "0";
				session.selectOne(namespace+".blackListMemberUpdate", keyWord[i]);
			}
			if(isIndex) {
				session.selectOne(namespace+".outlistInsert", input);
				isIndex = false;
			}
			map.put("keyWord", keyWord[i]);
			map.put("lNo", lNo);
			session.selectOne(namespace+".blackListPickoutlistUpdate", map);
		}
	}

	// 게시판리스트 메시지 추가. jun. 17.11.23
	@Override
	public void boardMsg(String key, String msg) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> a = new HashMap<>();
		a.put("key", key);
		a.put("msg", msg);
		session.selectOne(namespace+".boardMsg", a);
	}
	
}
