<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<%@include file="../include/header.jsp" %>
<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("아이디 찾기");

function idFindfunction(){
	var email = $('#email').val();
	var name = $('#name').val();
	var tempstr = "0123456789abcdefghijklmnopqrstuvwxyz_-@.";
	var str1cnt = 0;
	var str2cnt = 0;	
	
	if (!checkNullStr(email)){
		alert( 'email을 입력하세요');
		return false;
	}
	//이메일 유효성 검사 
	for(i=0;i<email.length;i++) { 
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
	if (str1cnt != 1 || str2cnt < 1 || str2cnt > 2){
		alert('이메일양식에 맞게 넣어주세요.')
		return false;
	}
	if (name ==''||name== null) {
		alert( '이름을 입력하세요');
		return false;
	}	
	$.ajax({
		url:'idFindfunction',
		data:{email:email,name:name},
		dataType:'text',
		success: function(result){
		/* alert(result); */
			if(result==""){
				$('#idcheckMessage').html("아이디가존재하지않습니다.");
				$('#idcheckMessage').css("color","red");
			}
			else{
				$('#idcheckMessage').html("아이디는 :"+result+"입니다.");
				$('#idcheckMessage').css("color","blue");
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
				<div class="container">
					<div class="page-header">
						<img src="/resources/img/logo.png" alt ="gettoere" width=50 height=50 style="float:left">
						<h1>&nbsp아이디 찾기 <small>gettoere</small></h1>
					</div> 
					<div class="col-md-6 col-md-offset-3">
						<form role ="form" method ="post" name="userInfo" onsubmit="return checkValue()">      
							<div class="form-group">
								<label for="InputEmail">이메일</label>
								<input type="email"  class="form-control" name = "email" id = "email"  onkeyup="" maxlength = "25" placeholder="이메일 입력">
							</div>
							<div class="form-group">
								<label for="InputName">이름</label>
								<input type="text" name = "name" id = "name" class="form-control" onkeyup="" maxlength = "15" placeholder="이름 입력">
								<p class="help-block" id ="idcheckMessage"> </p>
							</div>
							<div class="form-group text-center">
								<button type="button" class="btn btn-info" onclick ="idFindfunction()">찾기<i class="fa fa-check spaceLeft"></i></button>
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