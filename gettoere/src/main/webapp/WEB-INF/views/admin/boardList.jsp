<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="../include/header.jsp"%>

<!-- Main content -->
<section class="content">
	<div class="box">
		<div class="boardListset">
			<!-- 최고관리자 게시판리스트. jun. 17.11.09 -->
			<div class="box-header with-border">
				<h3 class="box-title">게토리 리스트</h3>
			</div>
			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px"><input type="checkbox" class="checkAll" name="checkAll"></th>
						<th>게토리 목록</th>
						<th>메시지</th>
						<th>관리자</th>
						<th>상태</th>
						<th>생성일</th>
					</tr>
					<c:forEach items="${boardList}" var="AdminBoardVO">
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="${AdminBoardVO.lNo }" />
							</td>
							<td>
								<a href='/board/list?lNo=${AdminBoardVO.lNo }'>${AdminBoardVO.name } <small class ="badge bg-red">${AdminBoardVO.cnt }</small></a>
							</td>
							<td>${AdminBoardVO.message }</td>
							<td>${AdminBoardVO.nickname }</td>
							<c:set var="status" value="${AdminBoardVO.status}" />
	
							<c:if test="${status eq '0'}">
								<td></td>
							</c:if>
							<c:if test="${status eq '1'}">
								<td>중단</td>
							</c:if>
							<c:if test="${status eq '2'}">
								<td>정지</td>
							</c:if>
							<td>
								<fmt:formatDate pattern="yy. MM. dd" value="${AdminBoardVO.createDate }" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!-- /.box-body -->
	
			<!-- 게시판 정지 해제 기능버튼. jun 17.11.23 -->
			<button id='stopBtn'>정지</button>
			<button id='startBtn'>해제</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 관리자에게 메시지 기능. jun. 17.11.23 -->
			<input type="text" name='keyword' id="msgInput">
			<button id='msgBtn'>입 력</button>
	
			<!-- 최고관리자 게시판리스트 페이징. jun 17.11.13 -->
			<div class="box-footer">
	
				<div class="text-center">
					<ul class="pagination">
						<c:if test="${pageMaker.isprev}">
							<li><a href="boardList?page=${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
						</c:if>
	
						<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
							<li <c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>><a href="${pageMaker.makeSearch(idx)}">${idx}</a></li>
						</c:forEach>
	
						<c:if test="${pageMaker.isnext && pageMaker.endPage > 0}">
							<li><a href="${pageMaker.makeSearch(pageMaker.endPage + 1) }">&raquo;</a></li>
						</c:if>
					</ul>
				</div>
	
			</div>
			<!-- /.box-footer-->
	
			<!-- 게시판리스트 검색기능. jun. 17.11.14 -->
			<input type="text" name='keyword' id="keywordInput" value='${cri.keyword }'>
			<button id='searchBtn'>검 색</button>
		</div>
	</div>
</section>
<!-- /.content -->

<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("게시판 관리");

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
	
	
	/* 게시판리스트 검색기능. jun. 17.11.14 */
	$('#searchBtn').on("click", function(event) {
						self.location = "boardList"
						+ '${pageMaker.makeQuery(1)}'
						+ "&keyword=" + $('#keywordInput').val();
	});
	
	/* 게시판리스트 정지기능. jun. 17.11.23 */
	$('#stopBtn').on("click", function(event){
		var temp = "";
		$(".check:checked").each(function() {
			temp += "," + $(this).val();
		});

		if (temp.length == 0) {
			alert("정지 할 게시판을 체크해 주세요");
			return;
		} else {
			var conf = confirm("정지하시겠습니까?");
			if(conf == true){
				temp = temp.substring(1);
				$.ajax({
					url : '/admin/boardStartStop',
					type : 'POST',
					data : JSON.stringify({
						msg : "stop",
						lNo : temp
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
	})
	
	/* 게시판리스트 해제기능. jun. 17.11.23 */
	$('#startBtn').on("click", function(event){
		var temp = "";
		$(".check:checked").each(function() {
			temp += "," + $(this).val();
		});

		if (temp.length == 0) {
			alert("해제 할 게시판을 체크해 주세요");
			return;
		} else {
			var conf = confirm("해제하시겠습니까?");
			if(conf == true){
				temp = temp.substring(1);
				/* 해제시 이름 비교. jun. 17.12.05 */
				$.ajax({
	        		url : '/admin/chkBoardName',
	        		headers : {
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "POST"
					},
	        		type : 'post',
	        		data : JSON.stringify({
	        			lNo : temp
	        		}),
	        		dataType : 'text',
	        		success : function(result){
	        			if(result == 0){
	        				$.ajax({
	        					url : '/admin/boardStartStop',
	        					type : 'POST',
	        					data : JSON.stringify({
	        						msg : "start",
	        						lNo : temp
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
	        			}else if(result == 1){
	        				alert("이미 존재하는 게시판이 있습니다.");
	        			}else{
	        				alert("error");
	        			}
	        		},
	        		error:function(request,status,error){
	        		    console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	      		    }
	        	});
			}
			else if(conf == false){
				alert("취소를 누르셨군요.");
			}
		}
	})

	/* 게시판리스트 메시지 추가. jun. 17.11.23 */
	$('#msgBtn').on("click", function(event){
		var temp = "";
		$(".check:checked").each(function() {
			temp += "," + $(this).val();
		});

		if (temp.length == 0) {
			alert("메시지 입력 할 게시판을 체크해 주세요");
			return;
		} else {
			var conf = confirm("입력하시겠습니까?");
			if(conf == true){
				temp = temp.substring(1);
				$.ajax({
					url : '/admin/boardMsg',
					type : 'POST',
					data : JSON.stringify({
						msg : $('#msgInput').val(),
						lNo : temp
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
	})
});
</script>

<%@include file="../include/footer.jsp"%>