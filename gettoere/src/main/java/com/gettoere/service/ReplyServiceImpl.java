package com.gettoere.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gettoere.dao.ReplyDAO;
import com.gettoere.vo.Criteria;
import com.gettoere.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	private ReplyDAO dao;
	
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		dao.create(vo);
	}

	@Override
	public List<ReplyVO> listReply(int bNo) throws Exception {
		return dao.list(bNo);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		dao.update(vo);
	}

	@Override
	public void removeReply(int rNo, int bNo) throws Exception {
		dao.delete(rNo, bNo);
	}

	@Override
	public List<ReplyVO> listReplyPage(int bNo, Criteria cri) throws Exception {
		
		return dao.listPage(bNo, cri);
	}

	@Override
	public int count(int bNo) throws Exception {

		return dao.count(bNo);
	}

}
