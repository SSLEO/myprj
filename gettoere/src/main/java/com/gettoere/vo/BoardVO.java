package com.gettoere.vo;

import java.util.Date;

public class BoardVO {
	private int seq;
	private int bNo;
	private int lNo;
	private int mNo;
	private int category;
	private String title;
	private String contents;
	private Date createDate;
	private int viewCnt;
	private int bestCnt;
	private int worstCnt;
	private boolean isBest;

	//변수 추가(정렬을 위한 번호)  jun. 171130
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	//변수 추가  kyo. 171108
	private String nickname; 
	
	//게시판 이름 변수 추가. 171113
	private String name;
	
	//댓글 수 변수추가. lsy. 171116
	private int replyCnt;
	
	//listBoard 용 추가. ssh. 171120
	private Date lastDate;
	private String message;
	private String imgUrl;
	private int status;
	private int level;
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getWorstCnt() {
		return worstCnt;
	}
	public void setWorstCnt(int worstCnt) {
		this.worstCnt = worstCnt;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getReplyCnt() {
		return replyCnt;
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getbNo() {
		return bNo;
	}
	public void setbNo(int bNo) {
		this.bNo = bNo;
	}
	public int getlNo() {
		return lNo;
	}
	public void setlNo(int lNo) {
		this.lNo = lNo;
	}
	public int getmNo() {
		return mNo;
	}
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getBestCnt() {
		return bestCnt;
	}
	public void setBestCnt(int bestCnt) {
		this.bestCnt = bestCnt;
	}
	public boolean isBest() {
		return isBest;
	}
	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}
	@Override
	public String toString() {
		return "BoardVO [seq=" + seq + ", bNo=" + bNo + ", lNo=" + lNo + ", mNo=" + mNo + ", category=" + category
				+ ", title=" + title + ", contents=" + contents + ", createDate=" + createDate + ", viewCnt=" + viewCnt
				+ ", bestCnt=" + bestCnt + ", worstCnt=" + worstCnt + ", isBest=" + isBest + ", nickname=" + nickname
				+ ", name=" + name + ", replyCnt=" + replyCnt + ", lastDate=" + lastDate + ", message=" + message
				+ ", imgUrl=" + imgUrl + ", status=" + status + ", level=" + level + "]";
	}
	
	
}
