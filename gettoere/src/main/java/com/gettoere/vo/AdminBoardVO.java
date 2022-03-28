package com.gettoere.vo;

import java.util.Date;

public class AdminBoardVO {
	private int seq; // 행번호
	private int lNo;
	private String name;
	private int cnt;
	private String nickname;
	private Date createDate;
	private int status;
	private String message;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getlNo() {
		return lNo;
	}
	public void setlNo(int lNo) {
		this.lNo = lNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
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
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "AdminBoardVO [seq=" + seq + ", lNo=" + lNo + ", name=" + name + ", cnt=" + cnt + ", nickname="
				+ nickname + ", createDate=" + createDate + ", status=" + status + ", message=" + message + "]";
	}
}
