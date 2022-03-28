package com.gettoere.vo;

import java.util.Date;

public class AdminBlackVO {
	private int seq; // 행번호
	private int mNo;
	private int oNo;
	private String id;
	private String name;
	private String nickname;
	private String reason;
	private Date bCreateDate;
	private Date mCreateDate;
	@Override
	public String toString() {
		return "AdminBlackVO [seq=" + seq + ", mNo=" + mNo + ", oNo=" + oNo + ", id=" + id + ", name=" + name
				+ ", nickname=" + nickname + ", reason=" + reason + ", bCreateDate=" + bCreateDate + ", mCreateDate="
				+ mCreateDate + "]";
	}
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
	public int getoNo() {
		return oNo;
	}
	public void setoNo(int oNo) {
		this.oNo = oNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getbCreateDate() {
		return bCreateDate;
	}
	public void setbCreateDate(Date bCreateDate) {
		this.bCreateDate = bCreateDate;
	}
	public Date getmCreateDate() {
		return mCreateDate;
	}
	public void setmCreateDate(Date mCreateDate) {
		this.mCreateDate = mCreateDate;
	}
}
