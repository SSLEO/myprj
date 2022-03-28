package com.gettoere.service;

import java.util.List;

import com.gettoere.vo.Criteria;
import com.gettoere.vo.ReplyVO;

public interface ReplyService {
	
	public void addReply(ReplyVO vo) throws Exception;
	
	public List<ReplyVO> listReply(int bNo)throws Exception;
	
	public void modifyReply(ReplyVO vo)throws Exception;
	
	public void removeReply(int rNo, int bNo)throws Exception;
	
	public List<ReplyVO> listReplyPage(int bNo, Criteria cri) throws Exception;
	
	public int count(int bNo) throws Exception;

}
