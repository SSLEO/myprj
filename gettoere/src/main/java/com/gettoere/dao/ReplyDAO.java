package com.gettoere.dao;

import java.util.List;

import com.gettoere.vo.Criteria;
import com.gettoere.vo.ReplyVO;

public interface ReplyDAO {
	
	public List<ReplyVO> list(int bNo)throws Exception;
	
	public void create(ReplyVO vo)throws Exception;
	
	public void update(ReplyVO vo)throws Exception;
	
	public void delete(int rNo, int bNo)throws Exception;
	
	public List<ReplyVO> listPage(int bNo, Criteria cri) throws Exception;
	
	public int count(int bNo) throws Exception;
	

}
