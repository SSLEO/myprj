<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<!-- Main content -->
		<section class="content">
			<div class="box">
				<div class="outlistManset">
					<!-- 관리자 블랙리스트. jun. 17.11.23 -->
					<div class="box-header with-border">
						<h3 class="box-title">블랙리스트 관리</h3>
					</div>
					<div class="box-body">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th style="width: 10px">#</th>
									<th style="width: 10px"><input type="checkbox" class="checkAll" name="checkAll"></th>
									<th>닉네임</th>
									<th>사유</th>
									<th>블랙일</th>
								</tr>
							</thead>
							<tbody id="bList">
								<c:forEach items="${outlistMan}" var="AdminBlackVO">
									<tr>
										<td>${AdminBlackVO.seq }</td>
										<td>
											<input type="checkbox" class="outListCheck" name="outListCheck" value="${AdminBlackVO.mNo }"/>
										</td>
										<td>${AdminBlackVO.nickname}</td>
										<td>${AdminBlackVO.reason}</td>
										<td><fmt:formatDate pattern="yy. MM. dd" value="${AdminBlackVO.bCreateDate }"/></td>
									</tr>
						
								</c:forEach>
							</tbody>
					
						</table>
					</div>
					<button id="outlistRemove">면 제</button>
					
					<div class="box-footer">
			
						<div class="text-center">
							<ul class="pagination">
								<c:if test="${pageMaker.isprev}">
									<li><a
										href="outlistMan?page=${pageMaker.startPage-1}">&laquo;</a></li>
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
					<button data-toggle="modal" data-target="#myModal">블랙리스트 추가</button>
					
					<!-- Modal -->
					<div class="modal fade" id="myModal" role="dialog">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-header">
									<h4 class="modal-title">회원 검색</h4>
									<p>* 게시판 관리자는 블랙리스트로 추가할 수 없습니다.</p>
									<div class="form-inline">
						                <input class="form-control" id="myInput" type="text" placeholder="닉네임 검색" onkeypress="if(event.keyCode==13) {$('#outlistSearch').trigger('click'); return false;}">
						                <button type="button" class="btn btn-default" id="outlistSearch">검색</button>
					                </div>
								</div>
								<div class="modal-body" id="dvSrc" style="height: 300px; overflow-y: auto;">
									<table class="table table-bordered">
										<thead>
											<tr>
												<th style="width: 10px"><input type="checkbox" class="checkAll" name="checkAll"></th>
												<th align="center">닉네임</th>
											</tr>
										</thead>
										<tbody id="mList">
											<tr>
												<td colspan="2" align="center">검색한 데이터가 없습니다.</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="modal-footer">
									<input type="text" name='keyword' id="keywordBlackInput">
									<button type="button" class="btn btn-default" id="outlistAdd">추가하기</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- /.content -->
		
		<script>
			//상단 제목 변경. ssh. 171129
			$(".content-header h1").html("블랙리스트 관리");
		
			/* 주관리자, 부관리자 접근 권한. jun. 17.11.29 */
			var managerManMsg = '${managerManMsg}';
			if(managerManMsg == 'loginFail'){
				alert("로그인 후 이용 가능 합니다.");
				location.href = "/";
			}else if(managerManMsg == 'accessFail'){
				alert("접근 권한이 없습니다.");
				location.href = "/";
			} else {
				$(document).ready(function () {
					/* 블랙리스트 면제기능. jun. 17.11.16 */
					$('#outlistRemove').on("click", function(event) {
						var temp = "";
						var lNo = <%=vo.getlNoManager()%>;
						$(".outListCheck:checked").each(function() {
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
										mNo : temp,
										lNo : lNo
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
					
					/* 블랙리스트 추가 버튼. jun, 17.11.24 */
					$("#outlistAdd").on("click", function() {
						var temp = "";
						var reason = $('#keywordBlackInput').val();
						var lNo = <%=vo.getlNoManager()%>;
						$(".check:checked").each(function() {
							temp += "," + $(this).val();
						});
						
						if (temp.length == 0) {
							alert("블랙리스트로 추가 할 회원을 체크해 주세요");
							return;
						} else if (reason.length == 0){
							alert("블랙리스트로 추가 할 회원의 사유를 적어주세요");
							return;
						} else {
							var conf = confirm("추가하시겠습니까?");
							if(conf == true){
								temp = temp.substring(1);
								$.ajax({
									url : '/admin/blackListAdd',
									type : 'POST',
									data : JSON.stringify({
										mNo : temp,
										reason : reason,
										lNo : lNo
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
					
					/* 회원 검색. jun. 17.11.23 */
					$('#outlistSearch').on("click", function(event) {
						var nickname = $("#myInput").val();
						if(checkNullStr(nickname)){
							$.ajax({
								type : 'post',
								url : '/manager/searchMember',
								headers : {
									"Content-Type" : "application/json",
									"X-HTTP-Method-Override" : "POST"
								},
								dataType : 'json',
								data : JSON.stringify({
									nickname : nickname
								}),
								success : function(data) {
									$("#mList").html("");
									var text = "";
									if(data.length>0){
										for(var i=0; i<data.length; i++){
											var chkText ='';
											var lNo = <%=vo.getlNoManager()%>;
											if((data[i].grade == 2) && (data[i].lNoManager == lNo)){
												
											} else {
												chkText = '<input type="checkbox" class="check" value="'+data[i].mNo+'"/>';
											}
											text += '<tr>';
											text += '<td>'+ chkText +'</td>';
											text += '<td>'+ data[i].nickname +'</td>';
											text += '</tr>';
										}
									} else {
										text='<tr><td colspan="2" align="center">검색한 데이터가 없습니다.</td></tr>';
									}
									$("#mList").html(text);
									$("#dvSrc").animate({scrollTop:0}, 500);
								},
								error:function(data){
									console.log(data);
								}
							});
						} else {
							alert("닉네임을 잘못 입력하셨습니다.");
							$("#myInput").focus();
						}
					});
				})
			}
		</script>
<%
	}
%>

<%@include file="../include/footer.jsp"%>