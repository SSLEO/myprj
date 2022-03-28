<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@include file="../include/header.jsp" %>


<section class="content">
	<div class='box'>
		<div class="box-header with-border">
				<h3 class="box-title">최신 글 목록</h3>
		</div>
	
		<div class='box-body'>
			<select name="searchType">
				<option value="ntcw" <c:out value="${cri.searchType eq 'ntcw'?'selected':''}"/>>전체</option>
				<option value="n" <c:out value="${cri.searchType eq 'n'?'selected':''}"/>>게시판이름</option>
				<option value="t" <c:out value="${cri.searchType eq 't'?'selected':''}"/>>제목</option>
				<option value="c" <c:out value="${cri.searchType eq 'c'?'selected':''}"/>>내용</option>
				<option value="w" <c:out value="${cri.searchType eq 'w'?'selected':''}"/>>작성자</option>
				<option value="tc" <c:out value="${cri.searchType eq 'tc'?'selected':''}"/>>제목+내용</option>
				<option value="cw" <c:out value="${cri.searchType eq 'cw'?'selected':''}"/>>내용+작성자</option>
			</select> <input type="text" name='keyword' id="keywordInput" value='${cri.keyword }' onkeypress="if(event.keyCode==13) {$('#searchBtn').trigger('click'); return false;}">
			<button id='searchBtn' class ="btn btn-primary">검색</button>
		</div>
	
	
		<!-- 리스트게시판 목록처리  ssh 171204 -->
		<table class = "table table-bordered">
			<tr>
				<th style = "width: 50px">번호</th>
				<th>게시판(상태)</th>
				<th>제목</th>
				<th>작성자</th>
				<th>등록날짜</th>
				<th>추천수</th>
				<th>조회수</th>
			</tr>
			
			<c:forEach items="${list}" var ="listVO">
				<tr>
					<td>${listVO.seq}</td>
					<td>
						<c:choose>
							<c:when test="${listVO.status == 0}"><a href='/board/list?lNo=${listVO.lNo}'>${listVO.name} <small class ="badge bg-green">정상</small></a></c:when>
							<c:when test="${listVO.status == 1}">${listVO.name} <small class ="badge bg-red">중단</small></c:when>
							<c:when test="${listVO.status == 2}">${listVO.name} <small class ="badge bg-red">정지</small></c:when>
						</c:choose>
					</td>
					<td>
						<a href='/board/read?lNo=${listVO.lNo}&bNo=${listVO.bNo}'>
							${listVO.title} <small class ="badge bg-red">${listVO.replyCnt }</small>
						</a>
					</td>
					<td>${listVO.nickname}</td>
					<td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss" value="${listVO.createDate}"/></td>
					<td><span class ="badge bg-blue">${listVO.bestCnt}</span></td>
					<td><span class ="badge bg-green">${listVO.viewCnt}</span></td>
				</tr>
			</c:forEach> 
		</table>
		
		<!-- 페이징처리. ssh. 171204 -->
		<div class = "text-center">
			<ul class = "pagination">
				<c:if test="${pageMaker.isprev}">
					<li><a href = "recentList${pageMaker.listMakeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
				</c:if>
			
				<c:forEach begin = "${pageMaker.startPage}" end="${pageMaker.endPage}" var = "idx">
			
					<li <c:out value="${pageMaker.cri.page == idx ? 'class = active':''}"/>>
						<a href ="recentList${pageMaker.listMakeSearch(idx)}">${idx}</a>
					</li>
				</c:forEach>
				<c:if test="${ pageMaker.isnext && pageMaker.endPage >0}">
					<li><a href = "recentList${pageMaker.listMakeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</section>
<!-- 검색기능. ssh. 171204 -->
<script>
	//상단 제목 변경. ssh. 171129
	$(".content-header h1").html("최신 글 목록");

	$(document).ready(
			function() {
				$('#searchBtn').on(
						"click",
						function(event) {

							self.location = "recentList"
 									+ '${pageMaker.listMakeQuery(1)}'
									+ "&searchType="
									+ $("select option:selected").val()
									+ "&keyword=" + $('#keywordInput').val();
				});
				 
			});
	
</script>

<%@include file="../include/footer.jsp" %>
