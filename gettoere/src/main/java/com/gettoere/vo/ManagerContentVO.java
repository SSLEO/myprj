package com.gettoere.vo;

import java.util.Date;

public class ManagerContentVO {
	private int seq; // 행번호
	private int bNo;
	private String title;
	private String nickname;
	private Date createDate;
	private int viewCnt;
	private int bestCnt;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getbNo() {
		return bNo;
	}
	public void setbNo(int bNo) {
		this.bNo = bNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public int getBestCnt() {
		return bestCnt;
	}
	public void setBestCnt(int bestCnt) {
		this.bestCnt = bestCnt;
	}
	
	@Override
	public String toString() {
		return "ManagerContentVO [seq=" + seq + ", bNo=" + bNo + ", title=" + title + ", nickname=" + nickname
				+ ", createDate=" + createDate + ", viewCnt=" + viewCnt + ", bestCnt=" + bestCnt + "]";
	}
}
