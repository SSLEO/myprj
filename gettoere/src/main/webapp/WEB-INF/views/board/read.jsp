<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
.non-pre{
	background-color: #fff;
	border: 0 solid #fff;
	border-radius: 0;
}
</style>
<div class="box-header with-border">
	<c:choose>
		<c:when test="${lVo.imgUrl != null && fn:length(lVo.imgUrl) > 0}">
			<img src="/resources/logoImg/${lVo.imgUrl}" alt ="logoImage" width=100% height=150px style="float:left">
		</c:when>
		<c:otherwise>
			<img src="/resources/img/defaultLogo.jpg" alt ="gettoere" width=100% height=150px style="float:left">
		</c:otherwise>
	</c:choose>
</div>
<form role="form" method="post">
	<input type="hidden" name="bNo" value="${boardVO.bNo}">
</form>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">

				<div class="box-body" style="background:#D8D8D8;">
					<div class="form-group">
						<b class ="badge bg-grey">제  목</b><label>${boardVO.title }</label> |
						<b class ="badge bg-green">조회수</b> <label id="view">${boardVO.viewCnt }</label> |
						<b class ="badge bg-grey">날짜	</b><label id="date"><fmt:formatDate value="${boardVO.createDate }" pattern="yyyy-MM-dd hh:mm:ss"/></label>							 
					</div>
					<div class="form-group">
						<b class ="badge bg-grey">작성자</b>
						<label for="exampleInputEmail1">${boardVO.nickname}</label>
					</div>
				</div>
				<div class="box-body">
					<div class="form-group">
						<div name="contents" style="min-height:250px;">${boardVO.contents}</div>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1"> 첨부파일 : <a
							href='/board/download?bNo=${boardVO.bNo}'>${attachVO.fileName}</a>
						</label><br>
					</div>
					<div class="form-group" id="upCnt" style="text-align:center;">
						<button class="btn btn-primary" type="button" id="recommend" style="">추천+${boardVO.bestCnt}</button>
						<button class="btn btn-danger" type="button" id="notfun">노잼+${boardVO.worstCnt}</button>
					</div>
				</div>

				<div class="box-footer" id="userviewBtn">
					<%
						if (vo != null) {
					%>
					<c:set var="name1" value="${boardVO.nickname}" />
					<c:set var="name2" value="<%=vo.getNickname()%>" />
					<c:if test="${name1 eq name2}">
						<button type="submit" class="btn btn-warning" id="modifyBtn">수정</button>
						<button type="submit" class="btn btn-danger" id="deleteBtn">삭제</button>
					</c:if>
					<%
						}
					%>
					<button type="submit" class="btn btn-primary" id="listBtn">목록</button>
				</div>

			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">

			<div class="box box-success">
				<%
					if (vo != null) {
				%>
				<c:if test="${empty vo }">
				<div class="box-header">
					<h3 class="box-title">새로운 댓글 추가</h3>
					<small> 글자수 <span id="inputReplyCnt">0</span> / 200자 제한</small>
				</div>
					<div class="box-body">
						<!-- <input class="form-control" type="text" placeholder="contents"id="newcontents"> -->
						<textarea class="form-control" id="newContents" placeholder="댓글 입력.." rows="3" cols="40" style="resize: none;"></textarea>
					</div>
					<!-- /.box body -->
					<div class="box-footer">
						<button class="btn btn-warning" type="button" id="replyAddBtn">댓글 추가</button>
					</div>
				</c:if>
				<%
					}
				%>
			</div>
		</div>
	</div>

	<!-- The time line -->
	<ul class="timeline">
		<li class="time-label" id="repliesDiv"><span class="bg-green">댓글
			<small id='replycntSmall'>[ ${boardVO.replyCnt } ]</small>
		</span></li>
	</ul>
	<div class="text-center">
		<ul id="pagination" class="pagination pagination-sm no-margin">
		</ul>
	</div>
</section>
<!-- /.box body -->



<!-- Modal -->
<div id="modifyModal" class="modal modal-primary fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">댓글 수정</h4>
				<input type="hidden" id="modalReplyNo"/>
			</div>
			<div class="modal-body" data-rno>
				<p>
					<textarea class="form-control" id="contents" placeholder="댓글 수정.." rows="3" cols="40" style="resize: none;"></textarea>
				</p>
			</div>
			<div class="modal-footer">
				<small>글자수 <span id="changeReplyCnt">0</span> / 200자 제한 </small>
				<button type="button" class="btn btn-info" id="replyModBtn">수정</button>
				<button type="button" class="btn btn-danger" id="replyDelBtn">삭제</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>

<script id="template" type="text/x-handlebars-template">

{{#each.}}
<li class="replyLi" data-rno={{rNo}}>
	<i class="fa fa-comments bg-blue"></i>
	<div class="timeline-item">
		<span class="time">
			<i class="fa fa-clock-o"></i>{{prettifyDate createDate}}
	 	</span>
	 	<h3 class="timeline-header"><strong>{{nickname}}</strong></h3>
	 	<pre class="timeline-body non-pre">{{contents}}</pre>
		<div class="timeline-footer">
			{{#eqNickname nickname}}
			<a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#modifyModal">수정</a>
			{{/eqNickname}}
		</div>
	</div>
</li>
{{/each}} 
</script>

<script>
$(".content-header h1").html("${lVo.name}"+" - 게시글");

Handlebars.registerHelper("prettifyDate", function(timeValue){
	var dateObj = new Date(timeValue);
    var year = dateObj.getFullYear();
    var month = dateObj.getMonth() + 1;
    var date = dateObj.getDate();
    var hour = dateObj.getHours();
    var min = dateObj.getMinutes();
    var sec = dateObj.getSeconds();
    
    return year+"/"+month+"/"+date+" "+hour+":"+min+":"+sec;
});

<%
	String nick = "";
	if(vo != null && vo.getNickname() != null){
		nick = vo.getNickname();
	}
%>

//닉네임 일치 여부에 따라 수정버튼 보이기.lsy.171122
Handlebars.registerHelper("eqNickname",function(nickname,block){
	var accum='';
	
	if(nickname == '<%=nick%>'){
		accum += block.fn();
	}
	return accum;
});
var printData = function(replyArr, target, templateObject){
	
	var template = Handlebars.compile(templateObject.html());
	
	var html = template(replyArr);
	$(".replyLi").remove();
	target.after(html);
}

var bNo = ${boardVO.bNo};
var replyPage = 1;

function getPage(pageInfo){
	
	$.getJSON(pageInfo, function(data){
		printData(data.list, $("#repliesDiv"), $('#template'));
		printPaging(data.pageMaker, $(".pagination"));
		
		$("#modifyModal").modal("hide");
		$("#replycntSmall").html("["+data.pageMaker.totalCount+"]");
	});
}

var printPaging = function(pageMaker, target){
	
	var str = "";
	
	if(pageMaker.prev){
		str += "<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
	}
	for(var i=pageMaker.startPage, len=pageMaker.endPage; i<=len; i++){
		var strClass = pageMaker.cri.page == i?'class=active':'';
		str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
	}
	if(pageMaker.next){
		str += "<li><a href='"+(pageMaker.endPage +1)+"'> >> </a></li>";
	}
	target.html(str);
};

$(document).ready(function(){
	//댓글 목록 펼치기. lsy. 171115
	getPage("/replies/"+bNo+"/1" );
	
	//댓글 페이징 처리. lsy. 171115
	$(".pagination").on("click", "li a", function(event){
		event.preventDefault();
		replyPage = $(this).attr("href");
		getPage("/replies/"+bNo+"/"+replyPage);
	});
	
	//댓글 등록. lsy. 171115
	$("#replyAddBtn").on("click",function(){
		var contents = $("#newContents").val();
		
		if(contents.trim() == ""){
			alert("댓글을 입력하세요");
			$("#newContents").focus();
			$("#newContents").val("");
			return false;
		}

        $.ajax({
            type:'post',
            url:'/replies/',
            headers: { 
                  "Content-Type": "application/json",
                  "X-HTTP-Method-Override": "POST" },
            dataType:'text',
            data: JSON.stringify({bNo:bNo, contents:contents}),
            success:function(result){
               console.log("result: " + result);
               if(result == 'SUCCESS'){
                  alert("등록 되었습니다.");
                  replyPage = 1;
                  getPage("/replies/"+bNo+"/"+replyPage );
                  $("#newContents").val("");
                  $("#inputReplyCnt").html("0");
               }
         	}
		});
   });
	
	// 댓글 입력 글자수 제한. ssh. 171201
	$('#newContents').on('keyup', function() {
        if($(this).val().length > 200) {
        	alert("댓글 입력하신 글자 수가 초과하였습니다.");
            $(this).val($(this).val().substring(0, 200));
        }
        $("#inputReplyCnt").html($(this).val().length);
        
        var rows = $(this).val().split('\n').length;
        var maxRows = 10;
        if( rows > maxRows){
            alert('10줄 까지만 가능합니다');
            modifiedText = $(this).val().split("\n").slice(0, maxRows);
            $(this).val(modifiedText.join("\n"));
        }
    });
	
	// 댓글 입력 글자수 제한. ssh. 171201
	$('#contents').on('keyup', function() {
        if($(this).val().length > 200) {
        	alert("댓글 입력하신 글자 수가 초과하였습니다.");
            $(this).val($(this).val().substring(0, 200));
        }
        $("#changeReplyCnt").html($(this).val().length);
        
        var rows = $(this).val().split('\n').length;
        var maxRows = 10;
        if( rows > maxRows){
            alert('10줄 까지만 가능합니다');
            modifiedText = $(this).val().split("\n").slice(0, maxRows);
            $(this).val(modifiedText.join("\n"));
        }
        
    });
	
	$(".timeline").on("click", ".replyLi", function(event){
		 var reply = $(this);
		 
		 $("#contents").val(reply.find(".timeline-body").text());
		 //$(".modal-title").html(reply.attr("data-rno"));
		 $("#modalReplyNo").val(reply.attr("data-rno"));
		 
		 $("#changeReplyCnt").html(reply.find(".timeline-body").text().length);
	});
	
	//Modal창에서 댓글 수정. lsy. 171115
	$("#replyModBtn").on("click",function(){
		var rNo = $("#modalReplyNo").val();
		var contents = $("#contents").val();
		
		if(contents.trim() == ""){
			alert("댓글을 입력하세요");
			$("#newContents").focus();
			$("#newContents").val("");
			return false;
		}

		$.ajax({
			type: 'put',
			url: '/replies/'+rNo,
			headers: {
				"Content-Type": "application/json",
				"X-HTTP-Method-Override": "PUT"},
			data:JSON.stringify({contents:contents}),
			dataType:'text',
			success:function(result){
				console.log("result:" + result);
				if(result == "SUCCESS"){
					alert("수정 되었습니다.");
					getPage("/replies/"+bNo+"/"+replyPage);
				}
			}
		});
	});
	
	//Modal창에서 댓글 삭제. lsy. 171115
	$("#replyDelBtn").on("click",function(){
		if(confirm("댓글을 삭제하시겠습니까?")){
			var rNo = $("#modalReplyNo").val();
			var contents = $("#contents").val();
			
			$.ajax({
				type: 'delete',
				url: '/replies/'+rNo,
				headers: {
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": "DELETE"},
				data: JSON.stringify({bNo:bNo}),
				dataType:'text',
				success:function(result){
					console.log("result:" + result);
					if(result == "SUCCESS"){
						alert("삭제 되었습니다.");
						getPage("/replies/"+bNo+"/"+replyPage);
					}
				}
			});
		}
	});


	//수정화면 이동. lsy. 171115
	$("#modifyBtn").on("click", function(){
		//alert("/board/list${pageMaker.makeSearch(pageMaker.cri.page)}&bNo="+bNo);
		location.href = "/board/modify${pageMaker.makeSearch(pageMaker.cri.page)}&bNo="+bNo;
	});
	
	
	var formObj = $("form[role='form']");
	
	console.log(formObj);
	
	//게시글 삭제. lsy. 171116
	$("#deleteBtn").on("click", function(){
		if(confirm("정말 삭제하시겠습니까?")==true){
			location.href = "/board/remove${pageMaker.makeSearch(pageMaker.cri.page)}&bNo="+bNo
			alert("삭제되었습니다.")
		}else
			alert("취소 되었습니다.")
			return;
	});
	
	//게시글 목록으로. lsy. 171115
	$("#listBtn").on("click", function(){
		self.location = "/board/list${pageMaker.makeSearch(pageMaker.cri.page)}"
	});
	
	//추천버튼. lsy. 171120
	$("#recommend").on("click", function(){
		$.ajax({
            type:'post',
            url:'/board/upDown/',
            headers: { 
                  "Content-Type": "application/json",
                  "X-HTTP-Method-Override": "POST" },
            dataType:'json',
            data: JSON.stringify({
            	bNo:bNo,
          		bestCnt:1
            }),
            success:function(vo){
               console.log("result: " + vo);
               if(vo.message == 'loginFail'){
					alert("로그인 후 이용가능합니다.");
					$(".loginbtn").trigger("click");
               }else if(vo.message == 'fail'){
					alert("이미 처리된 내역이 있습니다.");
               }else{
            	   alert("추천 되었습니다.");
            	   $("#recommend").text("추천["+vo.bestCnt+"]");
               }
         	}
		});
	});
	
	//비추천버튼. lsy. 171120
	$("#notfun").on("click", function(){
		$.ajax({
            type:'post',
            url:'/board/upDown/',
            headers: { 
                  "Content-Type": "application/json",
                  "X-HTTP-Method-Override": "POST" },
            dataType:'json',
            data: JSON.stringify({
            	bNo:bNo,
          		worstCnt:1
            }),
            success:function(vo){
               console.log("result: " + vo);
               if(vo.message == 'loginFail'){
					alert("로그인 후 이용가능합니다.");
					$(".loginbtn").trigger("click");
               }else if(vo.message == 'fail'){
            	   alert("이미 처리된 내역이 있습니다.");
               }else{
            	   alert("처리되었습니다.");
            	   $("#notfun").text("노잼["+vo.worstCnt+"]");
               }
         	}
		});
	});
	
});
	
</script>
<%@ include file="../include/footer.jsp"  %>