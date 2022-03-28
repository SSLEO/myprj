package com.gettoere.vo;

//게시판 목록,페이징처리  kyo 17-11-10
public class Criteria {
	
	private int lNo;
	private int page;
	private int perPageNum;
	private String searchType;
	private String keyword;
	
	//내가 쓴 글용으로 파라미터 추가함. ssh. 171201
	private int mNo;
	
	public int getmNo() {
		return mNo;
	}

	public void setmNo(int mNo) {
		this.mNo = mNo;
	}

	public int getlNo() {
		return lNo;
	}

	public void setlNo(int lNo) {
		this.lNo = lNo;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public void setPage(int page) {
		if(page<=0) {
			this.page =1;
			return;
			
		}
		this.page=page;
	}
	
	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum >100) {
			this.perPageNum =10;
			return;
		}
		this.perPageNum = perPageNum;
	}

	public int getPage() {
		
		return page;
	}

	public int getPageStart() {
		return (this.page-1) * perPageNum;
	}
	
	
	public int getPerPageNum() {
		return perPageNum;
	}
	


}
