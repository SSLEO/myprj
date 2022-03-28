package com.gettoere.vo;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

// 게시판페이징 처리 kyo 17-11-10
public class PageMaker{

	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean isprev;
	private boolean isnext;
	
	private int displayPageNum = 10;
	
	private Criteria cri;
	


	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}

	private void calcData() {
		// TODO Auto-generated method stub
		
		endPage = (int) (Math.ceil(cri.getPage() / (double)displayPageNum ) * displayPageNum);
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		
		if(endPage > tempEndPage){
			endPage = tempEndPage;
		}
		
		isprev = startPage ==1 ? false : true;
		
		isnext = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isIsprev() {
		return isprev;
	}

	public void setIsprev(boolean isprev) {
		this.isprev = isprev;
	}

	public boolean isIsnext() {
		return isnext;
	}

	public void setIsnext(boolean isnext) {
		this.isnext = isnext;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public String makeQuery(int page){
		
		UriComponents uriComponents =
	            UriComponentsBuilder.newInstance()
	            .queryParam("lNo", cri.getlNo())
	            .queryParam("page", page)
	            .queryParam("perPageNum", cri.getPerPageNum())
	            .build();	            
		
		return uriComponents.toUriString();
	}

	public String makeSearch(int page){
		
		UriComponents uriComponents =
	            UriComponentsBuilder.newInstance()
	            .queryParam("lNo", cri.getlNo())
	            .queryParam("page", page)
	            .queryParam("perPageNum", cri.getPerPageNum())
	            .queryParam("searchType",cri.getSearchType())
	            .queryParam("keyword", cri.getKeyword())
	            .build();	            
		
		return uriComponents.toUriString();
	}	
	
	//리스트게시판 kyo 17.11.22
	public String listMakeQuery(int page){
		
		UriComponents uriComponents =
	            UriComponentsBuilder.newInstance()
	            .queryParam("page", page)
	            .queryParam("perPageNum", cri.getPerPageNum())
	            .build();	            
		
		return uriComponents.toUriString();
	}
	
	//리스트게시판 kyo 17.11.22
	public String listMakeSearch(int page){
		
		UriComponents uriComponents =
	            UriComponentsBuilder.newInstance()
	            .queryParam("page", page)
	            .queryParam("perPageNum", cri.getPerPageNum())
	            .queryParam("searchType",cri.getSearchType())
	            .queryParam("keyword", cri.getKeyword())
	            .build();	            
		
		return uriComponents.toUriString();
	}	
	

	
}
