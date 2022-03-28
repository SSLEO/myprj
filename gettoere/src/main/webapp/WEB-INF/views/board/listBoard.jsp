<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@include file="../include/header.jsp" %>


<!-- 전제적인 수정 (글 수, 정지and중단 제거). jun. 17.11.29 -->
<section class="content">
	<!-- 리스트게시판 검색 기능 kyo 17-11-22 -->
	<div class='box'>
		<div class="box-header with-border">
				<h3 class="box-title">게시판 리스트</h3>
		</div>
	
		<div class='box-body'>
			<select name="searchType">
				<option value="n"
					<c:out value="${cri.searchType == null?'selected':''}"/>>전체</option>
				<option value="t"
					<c:out value="${cri.searchType eq 't'?'selected':''}"/>>게시판이름</option>
				<option value="w"
					<c:out value="${cri.searchType eq 'w'?'selected':''}"/>>관리자</option>
			</select> <input type="text" name='keyword' id="keywordInput" value='${cri.keyword }'>
			<button id='searchBtn' class ="btn btn-primary">검색</button>
		</div>
	
	
		<!-- 리스트게시판 목록처리  kyo 17-11-22 -->
		<table class = "table table-bordered">
			<tr>
				<th style = "width: 50px">번호</th>
				<th>게시판이름</th>
				<th>관리자</th>
				<th>날짜</th>
			</tr>
			
			 <c:forEach items="${list}" var ="AdminBoardVO">
				<tr>
				<td>${AdminBoardVO.seq}</td>
				<!-- href 경로 추가 kyo 17-11-10 -->
				<td>
					<a href='/board/list?lNo=${AdminBoardVO.lNo}'>
						${AdminBoardVO.name} <small class ="badge bg-red">${AdminBoardVO.cnt }</small>
					</a>
				</td>
				<td>${AdminBoardVO.nickname}</td>
				<td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value="${AdminBoardVO.createDate}"/></td>
				</tr>
			</c:forEach> 
		</table>
		
		<!-- 리스트게시판 페이징처리 kyo 17-11-22 -->
		<div class = "text-center">
			<ul class = "pagination">
				<c:if test="${pageMaker.isprev}">
					<li><a href = "listBoard${pageMaker.listMakeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
				</c:if>
			
				<c:forEach begin = "${pageMaker.startPage}" end="${pageMaker.endPage}" var = "idx">
			
					<li <c:out value="${pageMaker.cri.page == idx ? 'class = active':''}"/>>
						<a href ="listBoard${pageMaker.listMakeSearch(idx)}">${idx}</a>
					</li>
				</c:forEach>
				<c:if test="${ pageMaker.isnext && pageMaker.endPage >0}">
					<li><a href = "listBoard${pageMaker.listMakeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</section>
<!-- 리스트게시판 검색기능  kyo 17-11-22 -->
<script>
	//상단 제목 변경. ssh. 171129
	$(".content-header h1").html("게시판 목록");

	$(document).ready(
			function() {
				$('#searchBtn').on(
						"click",
						function(event) {

							self.location = "listBoard"
 									+ '${pageMaker.listMakeQuery(1)}'
									+ "&searchType="
									+ $("select option:selected").val()
									+ "&keyword=" + $('#keywordInput').val();
				});
				 
			});
	
</script>

<%@include file="../include/footer.jsp" %>
