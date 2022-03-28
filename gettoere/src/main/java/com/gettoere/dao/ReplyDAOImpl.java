package com.gettoere.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gettoere.vo.Criteria;
import com.gettoere.vo.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "com.gettoere.mapper.ReplyMapper";
	
	@Override
	public List<ReplyVO> list(int bNo) throws Exception {
		return session.selectList(namespace + ".list", bNo);
	}

	@Override
	@Transactional
	public void create(ReplyVO vo) throws Exception {
		
		int inCnt = session.insert(namespace + ".create", vo);
		if(inCnt > 0) {
			session.update(namespace+".updReplyCnt",vo);
		}
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		session.update(namespace + ".update", vo);
	}

	@Override
	@Transactional
	public void delete(int rNo, int bNo) throws Exception {
		int delCnt = session.delete(namespace + ".delete", rNo);
		
		if(delCnt > 0) {
			session.update(namespace+".updReplyMinusCnt", bNo);
		}
	}

	@Override
	public List<ReplyVO> listPage(int bNo, Criteria cri) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("bNo", bNo);
		paramMap.put("cri", cri);
		
		return session.selectList(namespace + ".listPage", paramMap);
	}

	@Override
	public int count(int bNo) throws Exception {
		return session.selectOne(namespace + ".count", bNo);
	}

}
