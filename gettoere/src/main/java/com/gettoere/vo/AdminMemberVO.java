package com.gettoere.vo;

import java.util.Date;

public class AdminMemberVO {
	private int seq; // 행번호
	private int mNo;
	private String id;
	private String mname;
	private String nickname;
	private String lname;
	private int status;
	private Date createDate;
	private int grade;
	private int lNoManager;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getmNo() {
		return mNo;
	}
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getlNoManager() {
		return lNoManager;
	}
	public void setlNoManager(int lNoManager) {
		this.lNoManager = lNoManager;
	}
	@Override
	public String toString() {
		return "AdminMemberVO [seq=" + seq + ", mNo=" + mNo + ", id=" + id + ", mname=" + mname + ", nickname="
				+ nickname + ", lname=" + lname + ", status=" + status + ", createDate=" + createDate + ", grade="
				+ grade + ", lNoManager=" + lNoManager + "]";
	}
	
}
