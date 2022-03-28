package com.gettoere.vo;

import java.util.Date;

public class ReplyVO {
	
	private int rNo;
	private int bNo;
	private int rrNo;
	private int mNo;
	private String contents;
	private int bestCnt;
	private Date createDate;
	private String nickname;
	
	private int lNo;

	public int getlNo() {
		return lNo;
	}

	public void setlNo(int lNo) {
		this.lNo = lNo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getrNo() {
		return rNo;
	}

	public void setrNo(int rNo) {
		this.rNo = rNo;
	}

	public int getbNo() {
		return bNo;
	}

	public void setbNo(int bNo) {
		this.bNo = bNo;
	}

	public int getRrNo() {
		return rrNo;
	}

	public void setRrNo(int rrNo) {
		this.rrNo = rrNo;
	}

	public int getmNo() {
		return mNo;
	}

	public void setmNo(int mNo) {
		this.mNo = mNo;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getBestCnt() {
		return bestCnt;
	}

	public void setBestCnt(int bestCnt) {
		this.bestCnt = bestCnt;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "ReplyVO [rNo=" + rNo + ", bNo=" + bNo + ", rrNo=" + rrNo + ", mNo=" + mNo + ", contents=" + contents
				+ ", bestCnt=" + bestCnt + ", createDate=" + createDate + ", nickname=" + nickname + ", lNo=" + lNo
				+ "]";
	}
}
