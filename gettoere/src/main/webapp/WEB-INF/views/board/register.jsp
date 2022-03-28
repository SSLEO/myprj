<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp" %>
<script type="text/javascript" src="/resources/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/resources/se2/photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"></script>
<script type="text/javascript">
$(".content-header h1").html("${lVo.name}"+" - 게시글 등록");

var oEditors = [];
//textArea에 이미지 첨부
function pasteHTML(filepath){
    var sHTML = '<img src="<%=request.getContextPath()%>/resources/upload/'+filepath+'">';
    oEditors.getById["contents"].exec("PASTE_HTML", [sHTML]);
}

window.onload = function () {
	
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "contents",
	    sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	    fCreator: "createSEditor2"
	});
}

function funcSubmit(){
	var elClickedObj = $("#form1");

	//msg리셋
	$("#msg1").html("");
	$("#msg2").html("");
	
	//id가 smarteditor인 textarea에 에디터에서 대입
	oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);

	//validation작업
	if($("#title").val().trim()==""){
		
		$("#msg1").html("<b style='color:red'>제목은 필수 입력항목입니다.</b>");
		$("#title").focus();
		return;
	}
	
    var contents = $("#contents").val();
    if( contents.length < 5)  {
    	alert("내용은 필수 입력항목입니다.(5글자 이상)");
		$("#msg2").html("<b style='color:red'>내용은 필수 입력항목입니다.(5글자 이상)</b>");
        oEditors.getById["contents"].exec("FOCUS"); //포커싱
        return;
    }

    try {
        elClickedObj.submit();
    } catch(e) {}
}
</script>

<!-- 로고이미지 추가  ssh 17.11.22-->
<div class="box-header with-border">
	<c:choose>
		<c:when test="${lVo.imgUrl != null && fn:length(lVo.imgUrl) > 0}">
			<img src="/resources/logoImg/${lVo.imgUrl}" alt ="logoImage" width=100% height=150px style="float:left">
		</c:when>
		<c:otherwise>
			<img src="/resources/img/defaultLogo.jpg" alt ="logoImage" width=100% height=150px style="float:left">
		</c:otherwise>
	</c:choose>
</div>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<form id="form1" role="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="lNo" value="${param.lNo}"/>
					<div class="box-body">
						<div class="form-group">
							<label for="exampleInputEmail1">제목</label>
							<input type="text" id="title" name="title" class="form-control" placeholder="Enter Title">
							<span id="msg1"></span>
						</div>
						<%
						int lNo = Integer.parseInt(request.getParameter("lNo").toString());

						if(vo!=null && vo.getlNoManager() == lNo && (vo.getGrade() == 3 || vo.getGrade() == 2 || vo.getGrade() == 1)){
						%>
						<div class="form-group">
							<label for="exampleInputEmail1">분류</label>
							<input type="radio" name="category" value="0" checked="checked">일반글
							<input type="radio" name="category" value="1">공지글
						</div>
						<%
						}else{
						%>
						<input type="hidden" name="category" value="0">
						<%
						}
						%>
						<div class="form-group">
							<label for="exampleInputPassword1">내용</label>
							<textarea class="form-control" id="contents" name="contents" rows="20"></textarea>
							<span id="msg2"></span>
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">첨부파일</label>
							<input type="file" name="file">
						</div>
					</div>
				</form>
				<div class="box-footer">
					<button id="submit" class="btn btn-primary" onclick="funcSubmit()">저장</button>
					<button id="cancel" class="btn btn-warning" onclick="window.history.back()">취소</button>
				</div>
			
			</div>
		</div>
	</div>
</section>

	
<%@include file="../include/footer.jsp" %>