<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@ include file="../include/header.jsp"%>

<%
	if(vo==null){
%>
	<script>
	/* 로그인 정보가 없을시 메인 페이지로 */
		alert("로그인 후 이용 가능 합니다.");
		location.href = "/";
	</script>
<% 
	} else {
%>
		<div class="contentset">
			<div class='box'>
				<div class='contentManset'>
					<div class="box-header with-border">
						<h3 class="box-title">게시글 관리</h3>
					</div>
					<div class="box-body">
						<table class="table table-bordered">
							<tr>
								<th style="width: 10px">#</th>
								<th style="width: 10px"><input type="checkbox" class="checkAll" name="checkAll"></th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>날짜</th>
								<th style="width: 50px">조회</th>
								<th style="width: 50px">추천</th>
							</tr>
							<c:forEach items="${contentMan}"  var="ManagerContentVO">
								<tr>
									<td>${ManagerContentVO.seq }</td>
									<td><input type="checkbox" id="check" name="check" value="${ManagerContentVO.bNo }"/></td>
									<!-- href 경로 추가. jun. 17.11.22 -->
									<td><a
										href='/board/read?lNo=<%=vo.getlNoManager()%>&bNo=${ManagerContentVO.bNo}'>${ManagerContentVO.title}</a></td>
									<td>${ManagerContentVO.nickname}</td>
									<td><fmt:formatDate pattern="yy-MM-dd HH:mm" value="${ManagerContentVO.createDate}" /></td>
									<td><span class="badge bg-red">${ManagerContentVO.viewCnt}</span></td>
									<td><span class="badge bg-red">${ManagerContentVO.bestCnt}</span></td>
								</tr>
							</c:forEach>
						</table>
						<div width="100%" style="text-align: left">
							<button class="btn btn-primary" id="delSelect">선택 삭제</button>
						</div>
						<!-- 게시판 페이징처리. jun. 17.11.22 -->
						<div class="text-center">
							<ul class="pagination">
								<c:if test="${pageMaker.isprev}">
									<li><a href="contentMan?page=${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
								</c:if>
						
								<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}"
									var="idx">
						
									<li
										<c:out value="${pageMaker.cri.page == idx ? 'class = active':''}"/>>
										<a href="${pageMaker.makeSearch(idx)}">${idx}</a>
									</li>
								</c:forEach>
								<c:if test="${ pageMaker.isnext && pageMaker.endPage >0}">
									<li><a href="${pageMaker.makeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
								</c:if>
							</ul>
						</div>
						<!-- 게시판 검색 기능. jun. 17.11.22 -->
						<div class='box-body'>
							<select name="searchType">
								<option value="t"
									<c:out value="${cri.searchType eq 't'?'selected':''}"/>>제목</option>
								<option value="n"
									<c:out value="${cri.searchType eq 'n'?'selected':''}"/>>닉네임</option>
							</select> <input type="text" name='keyword' id="keywordInput" value='${cri.keyword }'>
							<button id='searchBtn'>Search</button>
						</div>
						<div>
							<div width="100%" style="text-align: right">
								<button class="btn btn-primary" id="catChange">공지 등록</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script>
			//상단 제목 변경. ssh. 171129
			$(".content-header h1").html("게시글 관리");
		
			/* 주관리자, 부관리자 접근 권한. jun. 17.11.29 */
			var managerManMsg = '${managerManMsg}';
			if(managerManMsg == 'loginFail'){
				alert("로그인 후 이용 가능 합니다.");
				location.href = "/";
			}else if(managerManMsg == 'accessFail'){
				alert("접근 권한이 없습니다.");
				location.href = "/";
			}
			   
			$(document).ready(function() {
				/* 체크박스 전체선택 기능. jun. 17.11.10 */
				$('.checkAll').on('click', function () {
				    $(this).closest('table').find('tbody :checkbox')
				      .prop('checked', this.checked)
				      .closest('tr').toggleClass('selected', this.checked);
			  	});
			
			  	$('tbody :checkbox').on('click', function () {
				    $(this).closest('tr').toggleClass('selected', this.checked);
			
				    $(this).closest('table').find('.checkAll').prop('checked', ($(this).closest('table').find('tbody :checkbox:checked').length == $(this).closest('table').find('tbody :checkbox').length));
				});
			  	
				/* 게시글 검색 기능. jun. 17.11.22 */
				$('#searchBtn').on("click", function(event) {
					self.location = "contentMan"
									+ '${pageMaker.makeQuery(1)}'
									+ "&searchType="
									+ $("select option:selected").val()
									+ "&keyword=" + $('#keywordInput').val();
				});
				
				/* 공지글 등록 버튼. jun. 17.11.22 */
				$('#catChange').on("click", function(event){
					self.location = "/board/register?lNo="+<%=vo.getlNoManager()%>;
				});
			
				/* 게시글 삭제 기능. jun. 17.11.22 */
				$('#delSelect').on("click", function(event){
					var temp = "";
					$("#check:checked").each(function() {
						temp += "," + $(this).val();
					});
			
					if (temp.length == 0) {
						alert("삭제할 내용을 체크해 주세요");
						return;
					} else {
						var conf = confirm("삭제하시겠습니까?");
						if(conf == true){
							temp = temp.substring(1);
							$.ajax({
								url : '/manager/contentDelete',
								type : 'POST',
								data : JSON.stringify({
									bNo : temp
								}),
								headers : {
									"Content-Type" : "application/json",
									"X-HTTP-Method-Override" : "POST"
								},
								dataType : 'text',
								success : function(rst) {
									console.log(rst);
									window.location.reload();
								},
								error : function() {
									console.log('error');
								}
							});
						}
						else if(conf == false){
							alert("취소를 누르셨군요.");
						}
					}
				});
			});
		
		</script>
<%
	}
%>


<%@include file="../include/footer.jsp"%>