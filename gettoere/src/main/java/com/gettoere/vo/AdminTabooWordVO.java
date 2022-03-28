package com.gettoere.vo;

public class AdminTabooWordVO {
	private int seq; // 행번호
	private int tNo;
	private String word;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int gettNo() {
		return tNo;
	}
	public void settNo(int tNo) {
		this.tNo = tNo;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Override
	public String toString() {
		return "AdminTabooWordVO [seq=" + seq + ", tNo=" + tNo + ", word=" + word + "]";
	}
}
