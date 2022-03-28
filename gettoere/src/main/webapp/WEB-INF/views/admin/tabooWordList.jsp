<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../include/header.jsp" %>

<!-- Main content -->
<section class="content">
	<div class="box">
		<div class="tabooWordListset">
			<!-- 최고관리자 금지어리스트. jun. 17.11.13 -->
			<div class="box-header with-border">
				<h3 class="box-title">금지어 리스트</h3>
			</div>
			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px">#</th>
						<th style="width: 10px"><input type="checkbox" class="checkAll" name="checkAll"></th>
						<th>단어</th>
					</tr>
					<c:forEach items="${tabooWordList}" var="AdminTabooWordVO">
						<tr>
							<td>${AdminTabooWordVO.seq }</td>
							<td><input type="checkbox" class="check" name="check" value="${AdminTabooWordVO.tNo }"/></td>
							<td>${AdminTabooWordVO.word }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!-- /.box-body -->
			<!-- 최고관리자 금지어리스트 페이징. jun. 17.11.13 -->
			<div class="box-footer">
	
				<div class="text-center">
					<ul class="pagination">
						<c:if test="${pageMaker.isprev}">
							<li><a
								href="tabooWordList?page=${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
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
			<!-- 금지어리스트 검색기능. jun. 17.11.14 -->
			<button id='deleteBtn'>삭 제</button><br>
			<button id='insertBtn'>추 가</button>
			<input type="text" name='keyword' id="keywordInput" value='${cri.keyword }'>
			<button id='searchBtn'>검 색</button>
		</div>
	</div>
</section>
<!-- /.content -->

<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("금지어 관리");

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
	/* 최고관리자 금지어리스트. jun. 17.11.13 */

	
	
	$('.checkAll').on('click', function () {
	    $(this).closest('table').find('tbody :checkbox')
	      .prop('checked', this.checked)
	      .closest('tr').toggleClass('selected', this.checked);
	});

	$('tbody :checkbox').on('click', function () {
	    $(this).closest('tr').toggleClass('selected', this.checked);

	    $(this).closest('table').find('.checkAll').prop('checked', ($(this).closest('table').find('tbody :checkbox:checked').length == $(this).closest('table').find('tbody :checkbox').length));
	});

	/* 금지어리스트 검색기능. jun. 17.11.14 */
	$('#searchBtn').on("click", function(event) {
						self.location = "tabooWordList"
						+ '${pageMaker.makeQuery(1)}'
						+ "&keyword=" + $('#keywordInput').val();
	});


	/* 금지어리스트 등록기능. jun. 17.11.14 */
	$('#insertBtn').on("click", function(event) {
		var input = $('#keywordInput').val();
		$.ajax({
			url : '/admin/tabooWordInsert',
			type : 'POST',
			data : input,
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
	});

	/* 금지어리스트 삭제기능. jun. 17.11.15 */
	$('#deleteBtn').on("click", function(event) {

		/* var tNo = []; */

		var temp = "";

		$(".check:checked").each(function() {
			temp += "," + $(this).val();
			/* tNo.push($(this).val()); */
		});
		/* for(var i=0; i<tNo.length; i++){
			alert(tNo[i]);
		} 
		if(!tNo[0]){
			   alert("삭제할 내용을 체크해 주세요");
			   return;
		}*/

		if (temp.length == 0) {
			alert("삭제할 내용을 체크해 주세요");
			return;
		} else {
			temp = temp.substring(1);
			var conf = confirm("삭제하시겠습니까?");
			if(conf == true){
				$.ajax({
					url : '/admin/tabooWordDelete',
					type : 'POST',
					data : JSON.stringify({
						tNo : temp
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