<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@ include file="../include/header.jsp" %>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
			
				<div class="box-body">
				    <div class="managerset">
				        <h4>매니저 관리</h4>
				        <table class = "table table-bordered">
				            <tr>
				                <th style="width: 30px"><input type="checkbox" onchange="checkAll(this)" name="chk[]"></th>
				                <th>닉네임</th>
				                <th>등급</th>
				                <th style="width: 180px">관리자 변경</th>
				            </tr>
				            <c:forEach items="${manList}"  var="manVO">
				            	<tr>
				            		<td>
				            			<input type="checkbox" class="check" name="check" value="${manVO.mNo}"/>
				            			<input type="hidden" class="gradeVal" id="grade${manVO.mNo}" value="${manVO.grade}"/>
				            		</td>
				            		<td>${manVO.nickname}</td>
				            		<td class="gradeTd" id="td${manVO.mNo}">
				            			<c:choose>
				            				<c:when test="${manVO.grade == 2}">주 관리자</c:when>
				            				<c:when test="${manVO.grade == 1}">부 매니저</c:when>
				            			</c:choose>
				            		</td>
				            		<td><button class="btn btn-primary btnToMain" onclick="funcToMainMan(${manVO.mNo}, '${manVO.nickname}', this)" <c:if test="${manVO.grade == 2}">disabled='true'</c:if> >주 관리자로 임명</button></td>
				            	</tr>
				            </c:forEach>
				        </table>
				    </div>
					<button class="btn btn-primary" id="manRemove">선택 삭제</button>
					<button class="btn btn-primary" data-toggle="modal" data-target="#myModal">매니저 추가</button>
				</div>
			</div>
		</div>
	</div>
</section>
			
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="border-radius: .3rem;">
            <div class="modal-header">
                <h3 class="modal-title">회원 검색</h4>
                <p>* 게시판에 소유/소속된 회원은 매니저로 추가할 수 없습니다.</p>
                <div class="form-inline">
	                <input class="form-control" id="searchCon" type="text" placeholder="닉네임 검색" onkeypress="if(event.keyCode==13) {$('#searchBtn').trigger('click'); return false;}">
	                <button class="btn btn-default" id="searchBtn">검색</button>
                </div>
            </div>
            <div class="modal-body" id="dvSrc" style="height: 300px; overflow-y: auto;">
       			<table class="table table-bordered" id="searchContent">
       				<tr>
       					<th style="width: 45px;">선택</th>
		                <th>닉네임</th>
		                <th>등급</th>
		                <th>관리하는 게시판</th>
		            </tr>
		            <tr>
		            	<td colspan="4" align="center">검색한 데이터가 없습니다.</td>
		            </tr>
       			</table>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" id="manAdd">추가하기</button>
                <button class="btn btn-default" data-dismiss="modal">취소</button>
            </div>
        </div>
    </div>
</div>
    
<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("매니저 관리");

var managerManMsg = '${managerManMsg}';
if(managerManMsg == 'loginFail'){
	alert("로그인 후 이용 가능 합니다.");
	location.href = "/";
}else if(managerManMsg == 'accessFail'){
	alert("접근 권한이 없습니다.");
	location.href = "/";
}

function checkAll(ele) {
    var checkboxes = document.getElementsByName('check');
    if (ele.checked) {
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].type == 'checkbox') {
                checkboxes[i].checked = true;
            }
        }
    } else {
        for (var i = 0; i < checkboxes.length; i++) {
            console.log(i)
            if (checkboxes[i].type == 'checkbox') {
                checkboxes[i].checked = false;
            }
        }
    }
}

function funcToMainMan(mNo, nick, btn){
	if (confirm(nick+" 회원이 주 관리자로 변경되고,\n현재 주관리자는 부매니저로 변경됩니다.\n\n게시판을 양도하시겠습니까?")) {
		$.ajax({
            type:'post',
            url:'/manager/managerUp',
            headers: { 
                  "Content-Type": "application/json",
                  "X-HTTP-Method-Override": "POST" },
            dataType:'text',
            data: JSON.stringify({mNo:mNo}),
            success:function(result){
               console.log("result: " + result);
               if(result == 'success'){
					alert("변경 되었습니다.");
					$(".btnToMain").attr("disabled", false);	// 비활성화 해제
					$(".gradeVal").val("1");					// 부 매니저로 등급 초기화 시킴
					$(".gradeTd").text("부 매니저");				// 부 매니저로 초기화 시킴
					
					//주관리자로 설정
					$(btn).attr("disabled", true);	// 주 관리자는 버튼 비활성화 설정
					$("#td"+mNo).text("주 관리자");	// 주 관리자는 표기
					$("#grade"+mNo).val("2");		// 주 관리자 등급 표기
               }else{
					alert("변경 실패하였습니다.");
               }
         	}
		});
	}
}

$(document).ready(function(){
	//전체 체크 박스
	$("#myInput").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#mySelect option").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
	
	//매니저 선택 삭제버튼
	$("#manRemove").on("click", function(){
		
		$(".check:checked").each(function(e){
			var grade = $("#grade"+$(this).val()).val();
			if(grade == 2){
				$(this).prop('checked', false);
				alert("선택한 회원 중 주 관리자는 제외됩니다.");
			}
		});
		
		if($(".check:checked").length == 0){
			alert("선택한 회원이 없습니다.");
		}else{
			if(confirm("선택한 회원을 매니저 목록에서 삭제하시겠습니까?")){
				var mNos = "";
				$(".check:checked").each(function(e){
					var grade = $("#grade"+$(this).val()).val();
					if(grade == 1){
						mNos += ","+$(this).val();
					}
				});
				mNos = mNos.substring(1);
				
				$.ajax({
					type:'post',
					url:'/manager/managerOut',
					headers: { 
						"Content-Type": "application/json",
						"X-HTTP-Method-Override": "POST" },
					dataType:'text',
					data: JSON.stringify({mNos:mNos}),
					success:function(result){
						console.log("result: " + result);
						if(result == 'success'){
							alert("매니저로부터 제외 되었습니다.");
							location.reload();
						}else{
							alert("매니저 제외를 실패했습니다.");
						}
					}
				});
			}
		}
	})
	//회원 검색 버튼
	$("#searchBtn").on("click", function(){
		
		var src = $("#searchCon").val();
		if(!checkNullStr(src)){
			alert("검색할 닉네임을 입력해주세요.");
			return false;
		}
		
		$.ajax({
			type:'post',
			url:'/manager/findMember',
			headers: { 
				"Content-Type": "application/json",
				"X-HTTP-Method-Override": "POST" },
			dataType:'json',
			data: JSON.stringify({nickname:src}),
			success:function(data){
				var html = '<tr><th style="width: 45px;">선택</th><th>닉네임</th><th>등급</th><th>관리하는 게시판</th></tr>';
				if(data.length == 0){
					html += '<tr><td colspan="4" align="center">검색한 데이터가 없습니다.</td></tr>';
				}else{
					for(var i=0; i<data.length; i++){
						var chkText = '';
						var grdText = '';
						if(data[i].grade == 0){
							chkText = '<input type="checkbox" class="checkM" value="'+data[i].mNo+'"/>';
							grdText = '일반회원';
						}else if(data[i].grade == 1){
							grdText = '부 매니저';
						}else if(data[i].grade == 2){
							grdText = '주 관리자';
						}else if(data[i].grade == 3){
							grdText = '최고 관리자';
						}
						html += '<tr>';
						html += '<td>'+chkText+'</td>';
						html += '<td>'+data[i].nickname+'</td>';
						html += '<td>'+grdText+'</td>';
						html += '<td>'+data[i].name+'</td>';
						html += '</tr>';
					}
				}
				$("#searchContent").html(html);
				
				$("#dvSrc").animate({scrollTop:0}, 500);
			},
			error:function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				$("#searchContent").html('<td colspan="4" align="center">검색한 데이터가 없습니다.</td>');
			}
			
		});
	});
	
	//매니저 추가 버튼
	$("#manAdd").on("click", function(){
		
		if($(".checkM:checked").length == 0){
			alert("선택한 회원이 없습니다.");
			return false;
		}
		
		var mNos = "";
		$(".checkM:checked").each(function(e){
			mNos += ","+$(this).val();
		});
		mNos = mNos.substring(1);
		
		$.ajax({
			type:'post',
			url:'/manager/addManager',
			headers: { 
				"Content-Type": "application/json",
				"X-HTTP-Method-Override": "POST" },
			dataType:'text',
			data: JSON.stringify({mNos:mNos}),
			success:function(result){
				console.log("result: " + result);
				if(result == 'success'){
					alert("매니저 추가 되었습니다.");
					location.reload();
				}else{
					alert("매니저 추가를 실패했습니다.");
				}
			}
		});
	});
});
</script>

<%@include file="../include/footer.jsp" %>
