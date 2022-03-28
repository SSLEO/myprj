<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<%@include file="../include/header.jsp" %>
<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("비밀번호 찾기");

function pwFindfunction(){
	var email = $('#email').val();
	var id = $('#id').val();
	var tempstr = "0123456789abcdefghijklmnopqrstuvwxyz_-@.";
	var str1cnt = 0;
	var str2cnt = 0;
	if (!checkNullStr(id)){
		alert( 'id를 입력하세요');
		return false;
	}
	if (!checkNullStr(email)){
		alert( 'email을 입력하세요');
		return false;
	}
	   		//이메일 유효성 검사 
	for(i=0;i<email.length;i++){ 
		if(tempstr.indexOf(email.charAt(i)) == -1){
			alert('이메일양식에 맞게 넣어주세요.')
			return false;
		}
		if(email.charAt(i) == '@'){
			str1cnt += 1;
		}
		if(email.charAt(i) == '.'){
			str2cnt += 1;
		}
	} 
	if (str1cnt != 1 || str2cnt < 1 || str2cnt > 2)
	{
	alert('이메일양식에 맞게 넣어주세요.')
	return false;
	} 	 		
	$.ajax({
		url:'pwFindcheck',
		type:'post',
		data:{email:email,id:id},
		dataType:'text',
		success: function(result){
			// 비밀번호 찾기 이메일 인증 ktg 17.11.27
			if(result==0){
				$('#pwcheckMessage').html("회원정보가 일치하지않습니다.");
				$('#pwcheckMessage').css("color","red");
			}
			else{
				$('#pwcheckMessage').html("입력하신 정보를 이메일로 전송했습니다.");
				$('#pwcheckMessage').css("color","blue");
			}
		}
	});
}
function goFirstForm(){
self.location = "/"
}
</script>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
         
				 <div class="box-body">
				    <div class="page-header">
				   		<img src="/resources/img/logo.png" alt ="gettoere" width=50 height=50 style="float:left">
				        <h1>&nbsp비밀번호 찾기 <small>gettoere</small></h1>
				    </div> 
				    <div class="col-md-6 col-md-offset-3">
						<form role ="form" method ="post" name="userInfo" onsubmit="return checkValue()">
				        
				            <div class="form-group">
				              <label for="InputId">아이디</label>
								<input type="text" name = "id" id = "id" class="form-control" onkeyup="" maxlength = "15" placeholder="5~15자, 영어+숫자 가능">
								
				            </div>
				            <div class="form-group">
				              <label for="InputEmail">이메일</label>
												<input type="email"  class="form-control" name = "email" id = "email"  
													onkeyup="" maxlength = "25" placeholder="이메일 입력">
							  <p class="help-block" id ="pwcheckMessage"> </p>
				            </div>                     
				            <div class="form-group text-center">
				              <button type="button" class="btn btn-info" onclick ="pwFindfunction()">찾기<i class="fa fa-check spaceLeft"></i></button>
				              <button type="button" class="btn btn-warning" onclick="goFirstForm()">취소<i class="fa fa-times spaceLeft"></i></button>
				            </div>
				            
						</form>
					</div>
				</div>
         
			</div>
		</div>
	</div>
</section>

<%@include file="../include/footer.jsp" %>