<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@ include file="../include/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<style>
	.fileDrop{
		width: 100%;
		height:150px;
		border: 1px dotted gray;
	}
	
	.fileDrop img{
		width: 100%;
		height: 100%;
	}
	small{
		margin-left: 3px;
		font-weight: bold;
		color: gray;
	}
	#btns{
		float: left;
		width:100%;
	}
</style>
<script type="text/javascript">
	//상단 제목 변경. ssh. 171129
	$(".content-header h1").html("게시판 설정");

    function PreviewImage() {
        var oFReader = new FileReader();
        oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);

        oFReader.onload = function (oFREvent) {
            document.getElementById("uploadPreview").src = oFREvent.target.result;
        }
    }
    
    function funcDelete(){
    	var r = confirm("게시판을 삭제하시겠습니까?\n게시판을 삭제하면 되돌릴 수 없습니다.");
    	if(r == true){
    		location.href = "/manager/deleteBoard";
    	}else{
    		
    	}
    }
    
    function funcSubmit(){
    	var name = $('#boardName').val();
    	var mNo = $('#mNo').val();
    	
    	if(checkNullStr(name)){
    		$.ajax({
        		url : 'chkBoardName',
        		headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
        		type : 'post',
        		data : JSON.stringify({
        			name : name,
        			mNo : mNo
        		}),
        		dataType : 'text',
        		success : function(result){
        			if(result == 0){
        				$("#frmBoardMan").submit();
        			}else if(result == 1){
        				alert("이미 존재하는 게시판입니다.");
        				$('#boardName').focus();
        			}else{
        				alert("error");
        				
        			}
        		},
        		error:function(request,status,error){
        		    console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        		   
      		    }
        	});
    	}else{
    		alert("게시판 이름을 입력해주세요");
    		$('#boardName').focus();
    	}
    }
    
    window.onload = function(){
    	var bManMsg = '${bManMsg}';
    	console.log(bManMsg);
    	if(bManMsg == 'loginFail'){
    		alert("로그인 후 이용 가능합니다.");
    		
    		location.href = '/';
    	}else if(bManMsg == 'setSuccess'){
    		alert("적용되었습니다.");
    	}else if(bManMsg == 'boardDeleted'){
    		alert("게시판이 삭제되었습니다.");	
    	}
    	
    	var bManMsg2 = '${bManMsg2}';
    	if (bManMsg2 == 'memGrade0') {
            document.getElementById("btnSubmit").innerHTML = "게시판 생성";
    	}else if (bManMsg2 == 'memGrade1') {
    		document.getElementById("btnSubmit").innerHTML = "적용";
    	}
    }
</script>
<input type="hidden" id="mNo" value="${ManagerVO.mNo}">
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-body">
					<div class="boardMan">
						<form action="boardMan" id="frmBoardMan" method="POST" enctype="multipart/form-data">
			            	<div class="logoset">
				                <h4>게시판 로고 등록</h4>
				                <div class="filepath">
				                    <!-- 파일 업로드 form -->
				                    <div class="fileDrop">
					                	<c:choose>
											<c:when test="${ManagerVO.imgUrl != null}">
												<img id="uploadPreview" src="/resources/logoImg/${ManagerVO.imgUrl }" />
											</c:when>
											<c:otherwise>
												<img src="/resources/img/defaultLogo.jpg" />
											</c:otherwise>
										</c:choose>
									</div>
									<input id="uploadImage" type="file" name="imgUrl" onchange="PreviewImage();" />
				                </div>
				            </div>
					        <div class="titleset">
								<h4>게시판 제목 설정</h4>
								<p class="boardtitle">
								    <input type="text" id="boardName" placeholder="게시판 제목" name="name" value="${ManagerVO.name }" maxlength="10">
								</p>
					        </div>
						</form>
				        <div id="btns">
				        	<button class="btn" id="btnSubmit" onclick="funcSubmit();">적용</button>
				        </div>
			    	</div>
					<div id="btns">
						<button class="btn" disabled style="visibility: hidden">자리채우기</button>
			        	<button class="btn btn-danger" id="delBoard" onclick="funcDelete()" style="position: absolute; right: 10px;">게시판 삭제</button>
			        </div>
				</div>
			</div>
		</div>
	</div>
</section>
<%@include file="../include/footer.jsp" %>