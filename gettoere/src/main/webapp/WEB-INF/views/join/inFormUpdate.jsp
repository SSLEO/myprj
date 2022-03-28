<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<%@include file="../include/header.jsp" %>
 <script type="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("개인정보수정");

var inFormMsg = '${inFormMsg}';
if(inFormMsg == 'loginFail'){
	alert("로그인 후 이용 가능합니다.");
	location.href = "/";
}

//회원가입 중복 체크 ktg 17.11.30
$(document).ready(function() {
	
	//비밀번호 체크 kyo 17.11.14
	$("#pwCk").blur(function() {
		var pw = $('#pw').val();
		var pwCk = $('#pwCk').val();
		if(pw !=pwCk){
			$('#passwordCheckMessage').html("비밀번호가 서로 일치하지 않습니다.");
			$('#passwordCheckMessage').css("color","red");
			
		}else{
			$('#passwordCheckMessage').html("");
		}
	});
	
	//닉네임 체크  kyo 17.11.14
	$('#nickname').blur(function() {
		var nicknameid = $('#nickname').val();
		$.ajax({
			url:'NicknameUpCheck',
			data:{nickname:nicknameid},
			success: function(data){
				if(data=='0'||nicknameid==""){					
					$('#nicknamecheckMessage').html("");
					$('#nicknamecheckMessage').css("color","blue");
				}
				else{
					$('#nicknamecheckMessage').html("사용할수 없는 닉네임입니다.");
					$('#nicknamecheckMessage').css("color","red");
				}
			}
		});
	});
	
	//폰번호 체크  ktg 17.11.30
	$('#phone').blur(function() {
		var phoneid = $('#phone').val();
		$.ajax({
			url:'PhoneUpCheck',
			data:{phone:phoneid},
			success: function(data){
				if(data=='0'||phoneid==""){					
					$('#phonecheckMessage').html("");
					$('#phonecheckMessage').css("color","blue");
				}
				else{
					$('#phonecheckMessage').html("이미 입력된 핸드폰 번호 입니다.");
					$('#phonecheckMessage').css("color","red");
				}
			}
		});
	});
	
	//이메일 체크 kyo 17.11.14	
	$('#email').blur(function() {
		var emailid = $('#email').val();
		$.ajax({
			url:'EmailUpCheck',
			data:{email:emailid},
			dataType:'text',
			success: function(data){
				/* alert(data); */
				if(data=='0' || emailid == ""){	
					$('#emailcheckMessage').html("");
					$('#confirmcheckMessage').html("");
					$('#emailcheckMessage').css("color","blue");
					document.getElementById('confirmTmp').value = "&!@#%(@#%&)@#$";
				}
				else{
					$('#emailcheckMessage').html("사용할수 없거나 이미 사용된 이메일입니다.");
					$('#emailcheckMessage').css("color","red");
					
				}
			}
		});
	});
});

// 이메일 발송 버튼 ktg 17.11.24
function emailSendBtn() {
	var emailid = $('#email').val();
	
	// 메일 입력된 값이 없는 경우
	if (emailid == ""){
		$('#emailcheckMessage').html("이메일 발송을 실패했습니다.");
		$('#emailcheckMessage').css("color","red");
	} else {
		
		// 잘못 된 형식이나 특수문자가 들어간 경우
		if (email_check(emailid) == false){
			$('#emailcheckMessage').html("잘못 된 이메일 주소입니다.");
			$('#emailcheckMessage').css("color","red");
		} else {
			
			// 메일 형식이 잘못 된 경우 ( @, . )
			if (IsMailStr(emailid) == false){
				$('#emailcheckMessage').html("이메일 형식이 잘 못되었습니다.");
				$('#emailcheckMessage').css("color","red");
			} else {
				$('#confirmcheckMessage').css("color","blue");
				var confirmNum = $('#temp').val();
				$.ajax({
					type : 'post',
					url:'EmailConfirm',
					data:{
						temp:confirmNum,
						email:emailid
						},
					dataType:'text',
					success: function(data){
						$('#confirmTmp').val(data);
						console.log("이메일 인증 키 : " + data);
					}
				});
				alert(emailid + "로 전 송이 되었습니다. 인증번호를 확인해 주세요.");
				$('#temp').show();
			}
		}
	}
}
// 인증번호 버튼 ktg 17.11.24
function confirmBtn() {	
	
	$('#confirmcheckMessage').css("color","blue");	
	var confirmNum = $('#temp').val();	// 인증 번호 값
	var confirmTmp = $('#confirmTmp').val();	// 임시 인증 번호 값
	var btn = document.getElementById('joinBtn');	// 회원가입 버튼
	
	// 인증번호가 입력이 안 됐을 경우
	if (confirmNum == ""){
		$('#confirmcheckMessage').html("이메일 인증을 해주세요.");
		$('#confirmcheckMessage').css("color","red");
	} else {
		// 인증 번호가 같을 경우
		if (confirmNum == confirmTmp){
			var btn = document.getElementById('hideBtn');
			$('#confirmcheckMessage').html("인증 되었습니다.");
			$('#confirmcheckMessage').css("color","blue");
			document.getElementById("joinBtn").disabled = false;
		}
		// 인증 번호가 같지 않을 경우
		else {
			$('#confirmcheckMessage').html("입력하신 인증 번호가 아닙니다.");
			$('#confirmcheckMessage').css("color","red");
			btn.disabled = 'disabled';
		}
	}
}

// 개인정보 수정 화면의 입력값들을 검사한다.
function checkValue(){
	var id = $('#id').val();
	var pw = $('#pw').val();
	var pwCk = $('#pwCk').val();
	var nickname = $('#nickname').val();
	var phone = $('#phone').val();
	var email = $('#email').val();
	var name = $('#name').val();
	
	// 이메일 인증 번호 ktg 17.11.27
	var mailConfirm = $('#temp').val();
	var confirmTmp = $('#confirmTmp').val();
	
	//핸드폰 '-'검사 변수
	var phonestr = "0123456789";
	//이메일 중복체크 변수
	var tempstr = "0123456789abcdefghijklmnopqrstuvwxyz_-@.";
	var str1cnt = 0;
	var str2cnt = 0;

	if (!checkNullStr(id)){
		alert( 'ID를 입력하세요');
		return false;
	}
	//id 중복체크 검사 
	if ($('#idcheckMessage').html()=="사용할수 없는 아이디입니다."){
		alert('사용할수 없는 아이디입니다.');
		return false;
	} 

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
	if (!checkNullStr(nickname)){
		alert( 'nickname을 입력하세요');
		return false;
	}
	//닉네임 중복체크 검사 
	if ($('#nicknamecheckMessage').html()=="사용할수 없는 닉네임입니다."){
		alert('사용할수 없는 닉네임입니다.');
		return false;
	} 
	if (!checkNullStr(name)){
		alert( 'name을 입력하세요');
		return false;
	}
	if (!checkNullStr(phone)){
		alert( 'phone을 입력하세요');
		return false;
	}
 	//핸드폰 유효성 검사 
   	for(var i=0;i<phone.length;i++){
   		if(phonestr.indexOf(phone.charAt(i)) == -1){
   			alert("핸드폰번호에 숫자만 기입해주세요");
   			return false;
   		}
   	}
    //핸드폰 유효성 검사 
    if(phone.length != 11){
		alert("핸드폰번호에 11자리 맞게 기입해주세요");
		return false;
   	} 
	if (!checkNullStr(email)){
		alert( 'email을 입력하세요');
		return false;
	}
	if (!checkNullStr(mailConfirm)){
		alert('이메일 인증을 해주세요');
		return false;
	}
	if (confirmTmp != mailConfirm){
		alert('이메일 인증을 해주세요');
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
	if (str1cnt != 1 || str2cnt < 1 || str2cnt > 2){
		alert('이메일양식에 맞게 넣어주세요.')
		return false;
	}
	if ($('#emailcheckMessage').html()=="사용할수 없는 이메일입니다."){
		alert('사용할수 없는 이메일입니다.');
		return false;
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
			
				<form role="form" method="post" name="userInfo" onsubmit="return checkValue()">
					<div class="box-body">
						<div class="page-header">
							<img src="/resources/img/logo.png" alt ="gettoere" width=50 height=50 style="float:left">
							<h1>&nbsp개인정보수정 <small>gettoere</small></h1>
						</div>
						<div class="col-md-6 col-md-offset-3">
							<div class="form-group">
								<label for="InputId">아이디</label>
								<input type="text" class="form-control" name="id" id="id" value = "<%=vo.getId()%>" readonly maxlength="15" placeholder="5~15자, 영어+숫자 가능">
								<p class="help-block" id="idcheckMessage"></p>
							</div>
							<div class="form-group">
								<label for="InputPw1">비밀번호</label>
								<input type="password" class="form-control" name="pw" id="pw" maxlength="15" placeholder="8~15자, 영어+숫자 가능">
							</div>
							<div class="form-group">
								<label for="InputPw2">비밀번호 확인</label>
								<input type="password" class="form-control" name="pwCk" id="pwCk" maxlength="15" placeholder="8~15자, 영어+숫자 가능">
								<p class="help-block" id="passwordCheckMessage"></p>
							</div>
							<div class="form-group">
								<label for="InputNickname">닉네임</label>
								<input type="text" class="form-control" name="nickname" id="nickname" value = "<%=vo.getNickname()%>" maxlength="15" placeholder="닉네임을 입력">
								<p class="help-block" id="nicknamecheckMessage"></p>
							</div>
							<div class="form-group">
								<label for="InputName">이름</label>
								<input type="text" readonly class="form-control" id="name" name="name" maxlength="15" value = "<%=vo.getName()%>" placeholder="이름을 입력">
							</div>
							<label for="InputPhone">휴대폰</label>
							<div class="form-group">
								<input type="tel" class="form-control" id="phone" name="phone" maxlength="11" placeholder="'-'을 제외해서 입력" value="<%=vo.getPhone()%>">
								<p class="help-block" id="phonecheckMessage"></p>
							</div>
							<div class="form-group">
								<label for="InputEmail">이메일</label>
								<input type="text" class="form-control" id="email" name="email" maxlength="30" placeholder="이메일을 입력" value = "<%=vo.getEmail()%>">
								<p class="help-block" id="emailcheckMessage"></p>
							</div>
							<!-- 이메일 인증 ktg 17.11.24 -->
							<div class="form-group text-center">
								<input type="text" class="form-control confirmBox" id="temp" name="temp" maxlength="4" placeholder="인증번호 입력" style="width: 200px; float: left;">
								<button type="button" class="btn btn-info btn-confirm" id='hideBtn' onclick="confirmBtn()" style="background-color: #3c8dbc; border-color: #367fa9; float: left; margin: 0 10px;">
									인증<i class="fa fa-check spaceLeft"></i>
								</button>
								<button type="button" class="btn btn-info emailSend" onclick="emailSendBtn()">
									메일발송<i class="fa fa-check spaceLeft"></i>
								</button>
								<p class="help-block" id="confirmcheckMessage"></p>
							</div>
							<div class="form-group">
								 <input type="text" id="confirmTmp">
							</div>
							
						</div>
					</div>
					<div class="box-footer">
						<div class="form-group text-center">
							<button type="submit" class="btn btn-info" id="joinBtn" disabled="disabled">
								수정<i class="fa fa-check spaceLeft"></i>
							</button>
							<button type="button" class="btn btn-warning"
								onclick="goFirstForm()">
								취소<i class="fa fa-times spaceLeft"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>


<%@include file="../include/footer.jsp" %>


