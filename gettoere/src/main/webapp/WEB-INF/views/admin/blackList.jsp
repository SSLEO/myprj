<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../include/header.jsp" %>

<!-- Main content -->
<section class="content">
	<div class="box">
		<div class="blackListset">
			<!-- 최고관리자 블랙리스트. jun. 17.11.10 -->
			<div class="box-header with-border">
				<h3 class="box-title">블랙 리스트</h3>
			</div>
			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px">#</th>
						<th style="width: 10px"><input type="checkbox" class="checkAll" name="checkAll"></th>
						<th>아이디</th>
						<th>이름</th>
						<th>닉네임</th>
						<th>블랙사유</th>
						<th>블랙일</th>
						<th>가입일</th>
					</tr>
					<c:forEach items="${blackList}" var="AdminBlackVO">
						<tr>
							<td>${AdminBlackVO.seq }</td>
							<td><input type="checkbox" class="check" name="check" value="${AdminBlackVO.mNo }"/></td>
							<td>${AdminBlackVO.id }</td>
							<td>${AdminBlackVO.name }</td>
							<td>${AdminBlackVO.nickname }</td>
							<td>${AdminBlackVO.reason }</td>
							<td><fmt:formatDate pattern="yy. MM. dd" value="${AdminBlackVO.bCreateDate }"/></td>
							<td><fmt:formatDate pattern="yy. MM. dd" value="${AdminBlackVO.mCreateDate }"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!-- /.box-body -->
			<!-- 최고관리자 블랙리스트 페이징. jun. 17.11.13 -->
			<div class="box-footer">
	
				<div class="text-center">
					<ul class="pagination">
						<c:if test="${pageMaker.isprev}">
							<li><a
								href="blackList?page=${pageMaker.startPage-1}">&laquo;</a></li>
						</c:if>
	
						<c:forEach begin="${pageMaker.startPage }"
									 end="${pageMaker.endPage }" var="idx">
							<li
								<c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
								<a href="${pageMaker.makeSearch(idx)}">${idx}</a>
							</li>
						</c:forEach>
	
						<c:if test="${pageMaker.isnext && pageMaker.endPage > 0}">
							<li><a
								href="${pageMaker.makeSearch(pageMaker.endPage + 1) }">&raquo;</a></li>
						</c:if>
					</ul>
				</div>
	
			</div>
			<!-- /.box-footer-->
			<!-- 블랙리스트 검색기능. jun. 17.11.14 -->
			<button id='freeBtn'>블랙리스트 면제</button><br>
			<select name="searchType">
				<option value="i"
					<c:out value="${cri.searchType eq 'i'?'selected':''}"/>>아이디</option>
				<option value="n"
					<c:out value="${cri.searchType eq 'n'?'selected':''}"/>>이름</option>
				<option value="nn"
					<c:out value="${cri.searchType eq 'nn'?'selected':''}"/>>닉네임</option>
			</select>
			<input type="text" name='keyword' id="keywordInput" value='${cri.keyword }'>
			<button id='searchBtn'>검 색</button>
		</div>
	</div>
</section>
<!-- /.content -->

<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("블랙리스트 관리");

/* 최고관리자만 접근 권한. jun. 17.11.29 */
var checkMsg = '${checkMsg}';
if(checkMsg == 'loginFail'){
	alert("로그인 후 이용 가능 합니다.");
	location.href = "/";
}else if(checkMsg == 'accessFail'){
	alert("접근 권한이 없습니다.");
	location.href = "/";
}

$(document).ready(function () {
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

	/* 블랙리스트 검색기능. jun. 17.11.14 */
	$('#searchBtn').on("click", function(event) {
		self.location = "blackList"
						+ '${pageMaker.makeQuery(1)}'
						+ "&searchType="
						+ $("select option:selected").val()
						+ "&keyword=" + $('#keywordInput').val();
	});

	/* 블랙리스트 면제기능. jun. 17.11.16 */
	$('#freeBtn').on("click", function(event) {
		var temp = "";
		$(".check:checked").each(function() {
			temp += "," + $(this).val();
		});

		if (temp.length == 0) {
			alert("면제할 회원을 체크해 주세요");
			return;
		} else {
			var conf = confirm("면제하시겠습니까?");
			if(conf == true){
				temp = temp.substring(1);
				$.ajax({
					url : '/admin/memberFree',
					type : 'POST',
					data : JSON.stringify({
						mNo : temp
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

<%@include file="../include/footer.jsp" %>