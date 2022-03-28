package com.gettoere.vo;

import java.util.Date;

public class ManagerVO {
	private int lNo;
	private String name;
	private String imgUrl;
	private int grade;
	private String message;
	private Date createDate;
	private Date lastDate;
	private int status;
	private int mNo;
	private String mNos;
	private int seq; // 행번호
	private int bNo;
	private String title;
	private String nickname;
	private int viewCnt;
	private int bestCnt;
	private int lNoManager;
	private String reason;
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public int getlNo() {
		return lNo;
	}
	public void setlNo(int lNo) {
		this.lNo = lNo;
	}
	public String getmNos() {
		return mNos;
	}
	public void setmNos(String mNos) {
		this.mNos = mNos;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getmNo() {
		return mNo;
	}
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}
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
	public int getlNoManager() {
		return lNoManager;
	}
	public void setlNoManager(int lNoManager) {
		this.lNoManager = lNoManager;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
