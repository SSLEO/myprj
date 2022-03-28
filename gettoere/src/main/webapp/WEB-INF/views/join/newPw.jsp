<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@include file="../include/header.jsp"%>
<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("새 비밀번호 설정");

function passwordCheckfuncion(){
	var pw = $('#pw').val();
	var pwCk = $('#pwCk').val();
	
	if(pw !=pwCk){
		$('#passwordCheckMessage').html("비밀번호가 서로 일치하지 않습니다.");
		$('#passwordCheckMessage').css("color","red");
		
	}else{
		$('#passwordCheckMessage').html("");
	}
}
function checkValue(){
	var pw = $('#pw').val();
	var pwCk = $('#pwCk').val();
	
	if (!checkNullStr(pw)){
		alert( 'pw를 입력하세요');
		return false;
	}
	if(pw!=pwCk){
		alert("비밀번호가 서로 일치하지않습니다.");
		return false;
	}
	if (pw.length<8){
		alert("8~15자이내로 비밀번호를 입력해주세요.");
		return false;
	}
	if (!checkNullStr(pwCk)){
		alert( 'pw확인을 입력하세요');
		return false;
	}
}

function newPwfunction(){
	var id = "${param.id}";
	var temp = "${param.temp}";
	var pw = $('#pw').val();
	var pwCk = $('#pwCk').val();
	
	
	if (pw == pwCk) {
			$.ajax({
				url : 'usercheck',
				type : 'post',
				data : {
					temp : temp,
					id : id,
					pw : pw
				},
				dataType : 'text',
				success : function(result) {
					// 새 비밀번호 설정  ktg 17.11.27
					if (result == 0) {
						$('#pwcheckMessage').html(
								"이미 설정 하셨거나 정보를 가져오는데 실패하였습니다.");
						$('#pwcheckMessage').css("color", "red");
					} else {
						$('#pwcheckMessage').html("새 비밀번호 설정 완료.");
						$('#pwcheckMessage').css("color", "blue");
						alert("비밀번호 설정이 완료 되었습니다.\n메인 페이지로 이동합니다.")
						goFirstForm();
					}
				}
			});
		} else {
			alert ('실패!');
		}
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
						<img src="/resources/img/logo.png" alt="gettoere" width=50
							height=50 style="float: left">
						<h1>
							&nbsp새 비밀번호 설정 <small>gettoere</small>
						</h1>
					</div>
					<div class="col-md-6 col-md-offset-3">
						<form role="form" method="post" name="userInfo"
							onsubmit="return checkValue()">

							<div class="form-group">
								<label for="InputPw1">비밀번호</label> <input type="password"
									class="form-control" name="pw" id="pw"
									onkeyup="passwordCheckfuncion()" maxlength="15"
									placeholder="8~15자, 영어+숫자 가능">
							</div>
							<div class="form-group">
								<label for="InputPw2">비밀번호 확인</label> <input type="password"
									class="form-control" name="pwCk" id="pwCk"
									onkeyup="passwordCheckfuncion()" maxlength="15"
									placeholder="8~15자, 영어+숫자 가능">
								<p class="help-block" id="passwordCheckMessage"></p>
							</div>
							<p class="help-block" id ="pwcheckMessage"> </p>
							<div class="form-group text-center">
								<button type="button" class="btn btn-info"
									onclick="newPwfunction()">
									설정<i class="fa fa-check spaceLeft"></i>
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<%@include file="../include/footer.jsp"%>