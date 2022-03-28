<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@include file="../include/header.jsp" %>
<!-- 로고이미지 추가  kyo 17.11.16-->
<div class="box-header with-border">
	<c:choose>
		<c:when test="${lVo.imgUrl != null && fn:length(lVo.imgUrl) > 0}">
			<img src="/resources/logoImg/${lVo.imgUrl}" alt ="logoImage" width=100% height=150px style="float:left">
		</c:when>
		<c:otherwise>
			<img src="/resources/img/defaultLogo.jpg" alt ="logoImage" width=100% height=150px style="float:left">
		</c:otherwise>
	</c:choose>
</div>
<div>
	<c:choose>
		<c:when test ="${lVo.message != null}">
			${lVo.message}
		</c:when>
	</c:choose>
</div>

<!-- 게시판 검색 기능 kyo 17-11-10 -->
<div class='box'>
	<div class='box-body'>
		<select name="searchType">
			<option value="n"
				<c:out value="${cri.searchType == null?'selected':''}"/>>
				전체</option>
			<option value="t"
				<c:out value="${cri.searchType eq 't'?'selected':''}"/>>
				제목</option>
			<option value="c"
				<c:out value="${cri.searchType eq 'c'?'selected':''}"/>>
				글내용</option>
			<option value="w"
				<c:out value="${cri.searchType eq 'w'?'selected':''}"/>>
				작성자</option>
			<option value="tc"
				<c:out value="${cri.searchType eq 'tc'?'selected':''}"/>>
				제목+글내용</option>
			<option value="cw"
				<c:out value="${cri.searchType eq 'cw'?'selected':''}"/>>
				글내용+작성자</option>
			<option value="tcw"
				<c:out value="${cri.searchType eq 'tcw'?'selected':''}"/>>
					작성자+글내용+제목</option>
		</select> 
		<input type="text" name='keyword' id="keywordInput"value='${cri.keyword }'>
		<button id='searchBtn' class ="btn btn-primary">검색</button>
		<%if(vo!=null){ %>
		<c:set var="grade" value="<%=vo.getGrade()%>" />
		<c:if test="${lVo.level != 9 || grade == 3}">	<!-- 게시판 레벨이 관리자 급(9) 이면 관리자만 글쓰기 가능 -->
			<button id='newBtn' class ="btn btn-danger" style ="left: '600px'">글쓰기</button>
		</c:if>
		<%}%>
		<%-- <a href='/board/register${pageMaker.makeSearch(pageMaker.cri.page) }'>글쓰기</a> --%>
	</div>
</div>

<!-- 게시판 목록처리  kyo 17-11-10 -->
<table class = "table table-bordered">
	<tr>
		<th style = "width: 50px">#</th>
		<th>제목</th>
		<th>작성자</th>
		<th>날짜</th>
		<th style ="width : 50px">추천</th>
		<th style ="width: 50px">조회</th>
	</tr>
	<!-- 게시판 공지글 kyo 17.11.22 -->
	<c:forEach items="${noticeList}" var ="BoardVO2">
		<tr>
		<td><b class ="badge bg-black">공지</b></td>
		<!-- href 경로 추가 kyo 17-11-10 -->
		<td><a href='/board/read${pageMaker.makeSearch(pageMaker.cri.page) }&bNo=${BoardVO2.bNo}'>&nbsp&nbsp&nbsp<b>${BoardVO2.title}</b> <small class ="badge bg-red">${BoardVO2.replyCnt}</small></a></td>
		<td>${BoardVO2.nickname}</td>
		<td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value="${BoardVO2.createDate}"/></td>
		<td><span class ="badge bg-blue">${BoardVO2.bestCnt}</span></td>
		<td><span class ="badge bg-green">${BoardVO2.viewCnt}</span></td>
		</tr>
	</c:forEach>
	
	 <c:forEach items="${list}" var ="BoardVO">
		<tr>
		<td>${BoardVO.seq}</td>
		<!-- href 경로 추가 kyo 17-11-10 -->
		<td><a href='/board/read${pageMaker.makeSearch(pageMaker.cri.page) }&bNo=${BoardVO.bNo}'>${BoardVO.title} <small class ="badge bg-red">${BoardVO.replyCnt}</small></a></td>
		<td>${BoardVO.nickname}</td>
		<td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value="${BoardVO.createDate}"/></td>
		<td><span class ="badge bg-blue">${BoardVO.bestCnt}</span></td>
		<td><span class ="badge bg-green">${BoardVO.viewCnt}</span></td>
		</tr>
	</c:forEach> 
</table>

<!-- 게시판 페이징처리 kyo 17-11-10 -->
<div class = "text-center">
	<ul class = "pagination">
		<c:if test="${pageMaker.isprev}">
			<li><a href = "list${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
		</c:if>
	
		<c:forEach begin = "${pageMaker.startPage}" end="${pageMaker.endPage}" var = "idx">
	
			<li <c:out value="${pageMaker.cri.page == idx ? 'class = active':''}"/>>
				<a href ="list${pageMaker.makeSearch(idx)}">${idx}</a>
			</li>
		</c:forEach>
		<c:if test="${ pageMaker.isnext && pageMaker.endPage >0}">
			<li><a href = "list${pageMaker.makeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
		</c:if>
	</ul>
</div> 

<!-- 게시판 검색기능  kyo 17-11-10 -->
<!-- 게시판 등록으로 넘어가기 추가 하드코딩함 kyo 17-11-10 -->
<script>
	$(".content-header h1").html("${lVo.name}");

	$(document).ready(
			function() {
				$('#searchBtn').on(
						"click",
						function(event) {

							self.location = "list"
									+ '${pageMaker.makeQuery(1)}'
									+ "&searchType="
									+ $("select option:selected").val()
									+ "&keyword=" + $('#keywordInput').val();
				});
				
	 			$('#newBtn').on("click", function(evt) {
					self.location = "register?lNo=${pageMaker.cri.lNo}";
				}); 
			});
	
</script>

<%@include file="../include/footer.jsp" %>
