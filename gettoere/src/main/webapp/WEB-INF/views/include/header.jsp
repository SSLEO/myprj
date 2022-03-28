<%@page import="com.gettoere.vo.LoginVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>게토리 - 자유로운 커뮤니티 사이트</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- Bootstrap 3.3.4 -->
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="/resources/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- AdminLTE Skins. Choose a skin from the css/skins 
         folder instead of downloading all of them to reduce the load. -->
    <link href="/resources/dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
  </head>
      <!-- jQuery 2.1.4 -->
    <script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>

	<!-- FlexSlider 추가 ktg 17.11.14 -->
    <!-- FlexSlider -->
	<link rel="stylesheet" href="/resources/flexslider/flexslider.css" type="text/css" media="screen" />
	<script defer src="/resources/flexslider/jquery.flexslider.js"></script>
	
<%
	HttpSession s = request.getSession();
	LoginVO vo = (LoginVO)s.getAttribute("LoginVo");
%>

<style type="text/css">
	#confirmTmp{
		display: none;
	}
	.loginPop {
		display:none;
		width:200px;
		height:220px;
		background: #FFFFFF;
		color: #000;
		position: absolute;
		text-align: center;
		border: 2px solid #000;
		z-index:1050;
	}
	
	.close div {
		float: left;
		text-align: right;
	}
	
	.close {
	    padding-right: 20px;
		height: 50px;
	    line-height: 50px;
	    font-size: 20px;
	    width: auto;
	}
	.col-5 {
    padding-left: 2.5px !important;
    padding-right: 2.5px !important;
	}
	div {
	    display: block;
	}
	.row-5 {
	    margin-left: -2.5px !important;
	    margin-right: -2.5px !important;
	}
	.main-head {
	    border-bottom: 1px solid #ddd;
	    margin: 0px 0px 12px;
	    font-weight: bold;
	    padding-bottom: 3px;
	}
	.wrapper, .content-wrapper > .content {
	    max-width: 1250px;
	}
	.wrapper {
	    margin: 0 auto;
	    min-height: 100%;
	    box-shadow: 0 0 8px rgba(0, 0, 0, 0.5);
	    position: relative;
    }
    a.top {
		text-align: center;
		font-size: 20px;
		line-height: 10px;
		position: fixed;
		bottom: 50px;
		display: none;
		z-index: 1060;
		right: 0px;
		text-shadow: 0 0 1px #fff;
		-moz-text-shadow: 0 0 1px #fff;
		-webkit-text-shadow: 0 0 1px #fff;
		font-weight: bolder;
	}
	
	table a:visited {
		color : darkgray;
	}
	</style>
    <script src="/resources/js/com.js"></script>
	<script type="text/javascript">
//회원가입 버튼 누르면 회원가입 페이지로 이동  kyo 17.11.16
	function signUpFunction(){
		self.location = "/join/signUp"
	}
		// 헤더부분 새로운 게시판 목록 슬라이더 ktg 17.11.15
		function getNewBoard() {
			$.ajax({
				type : 'post',
				url : '/board/newBoard',
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : 'json',
				success : function(data) {
					if(data.length > 0){
						var text ="";
	
						for(var i=0; i < data.length; i++){
							text+= "<li><a href='/board/list?lNo="+data[i].lNo+"'>"+data[i].name+"</a></li>";
						}
						$(".newBoard").html(text);
						
						// flexSlider ktg 17.11.14
						$('.flexslider').flexslider({
							animation: "slide",
							direction: "vertical",
							animationSpeed: 600,
							slideshowSpeed: 5000,
							controlNav: false,
							directionNav: false
					    });
					}else{
						console.log("새로운 게시판 목록을 가져오지 못했습니다.");						
					}
				},
				error:function(data){
					console.log(data);
				}
			});
		}
		function closePop() {
			$('#pop').hide();
		}
		
		$( document ).ready(function() {
			// 헤더부분 새로운 게시판 목록 슬라이더 ktg 17.11.15
			getNewBoard();
			
			//로그인 안된 상태 메시지 출력. ssh. 171114
			var msg = '${msg}';
			if(msg == 'loginFail'){
				alert("로그인 후 이용 가능합니다.");
				$('#pop').show();
				// 팝업창 인터넷 크기 창에 맞게 가운데 정렬
				funcPosiCenter("loginPop"); 
			}
			
			//창크기 변화 감지
			$( window ).resize(function() {
				funcPosiCenter("loginPop"); 
			});

			
			$('.loginbtn').click(function() {
				$('#pop').show();
				// 팝업창 인터넷 크기 창에 맞게 가운데 정렬
				funcPosiCenter("loginPop"); 
			});
			
			$("#userlogin").on("click", function() {
				var idObj = $("#userid");
				var pwbj = $("#userpw");
				var id = idObj.val();
				var pw = pwbj.val();
				$.ajax({
					type : 'post',
					url : '/login',
					headers : {
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "POST"
					},
					dataType : 'json',
					data : JSON.stringify({
						id : id,
						pw : pw
					}),
					success : function(data) {
						if(data.msg == "SUCCESS"){
							window.location.reload();
						}else{
							alert("잘못된 아이디 또는 비밀번호를 입력하셨습니다.");						
						}
					},
					error:function(data){
						console.log(data);
					}
				});
			});
			
			//로그아웃
			$(".logout").on("click", function() {
				$.ajax({
					type : 'post',
					url : '/logout',
					headers : {
						"Content-Type" : "application/text",
						"X-HTTP-Method-Override" : "POST"
					},
					dataType : 'text',
					success : function(result) {
						if(result == 'SUCCESS'){
							alert("로그아웃 되었습니다.");
							location.href = "/";
						}
					}
				});
			});
			
			// 게시판 제목 검색 버튼. ssh. 171113
			$("#sideSearchBoardBtn").on("click", function(){
				var title = $("#sideSearchBoard").val();
				if(checkNullStr(title)){
					
					$.ajax({
						type : 'post',
						url : '/board/searchBoard',
						headers : {
							"Content-Type" : "application/json",
							"X-HTTP-Method-Override" : "POST"
						},
						dataType : 'json',
						data : JSON.stringify({
							title : title,
						}),
						success : function(data) {
							$("#listBoardNames").html("");
							var text = "";
							if(data.length > 0){
								for(var i=0; i<data.length; i++){
									text+= "<li><a href='/board/list?lNo="+data[i].lNo+"'>"+data[i].name+"</a></li>";
								}
							}else{
								text="<li><a href='#'>검색한 게시판이 없습니다.</a></li>";
							}
							$("#listBoardNames").html(text);
							$("#divListBoard").show("blind");
						},
						error:function(data){
							console.log(data);
						}
					});
				}else{
					alert("검색어를 잘못 입력하셨습니다.");
					$("#sideSearchBoard").focus();
				}
				
			});
			$('.flexslider2').flexslider({
				animation: "slide",
				controlNav: false,
				directionNav: false
			});
			
			// 죽어가는 게시판 영정사진 깜빡이는 애니메이션 ktg 17.11.17
			setInterval(function(){
                $(".dyingImg").animate({
				opacity:0.5},1000).animate({opacity:1},1000);
            },2000);
			
			// Top 버튼 tkg 17.11.30
			$( window ).scroll( function() {
				if ( $( this ).scrollTop() > 200 ) {
					$( '.top' ).fadeIn();
				} else {
		            $( '.top' ).fadeOut();
				}
			});
			
			$( '.top' ).click( function() {
				$( 'html, body' ).animate( { scrollTop : 0 }, 400 );
				return false;
			} );
		});
		
		function funcOutManager(){
			
			if(confirm("부 매니저에서 탈퇴를 하겠습니까?")){
				$.ajax({
					type : 'post',
					url : '/manager/outManager',
					headers : {
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "POST"
					},
					dataType : 'text',
					success : function(rst) {
						if(rst == "success"){
							alert("탈퇴가 완료되었습니다.");
							location.reload();
						}else if(rst == "loginFail"){
							alert("로그인 후 이용가능합니다.");
						}else if(rst == "fail"){
							alert("탈퇴 실패하였습니다.");
						}
					},
					error:function(data){
						console.log(data);
					}
				});
			}
		}
	</script>
	<body class="skin-blue sidebar-mini">
  	<!-- 팝업 -->
	<div id="pop" class="loginPop">
		<div>
			<!-- 로그인 -->
			<div class="navbar-brand" href="">로그인 팝업</div>
			<div class="close">
				<a href="javascript:closePop();">X</a>
			</div>
			<div>
				<div class ="form-group">
					<input type="text" name="id" placeholder="id" id="userid" onkeypress="if(event.keyCode==13) {$('#userpw').focus(); return false;}">
				</div>
				<div class ="form-group">
					<input type="password" name="pw" placeholder="password" id="userpw" onkeypress="if(event.keyCode==13) {$('#userlogin').trigger('click'); return false;}">
				</div>
				<div class ="form-group">
					<button type="button" class="btn btn-primary" id="userlogin">로그인</button>
					<button type="button" class="btn btn-warning" onclick="closePop()">닫기</button>
				</div>
				<div class ="form-group">
					<a href="/join/idFind">아이디 찾기</a> / <a href="/join/pwFind">비밀번호 찾기</a>
				</div>
			</div>
		</div>
	</div>
  
    <div class="wrapper">
      
      <header class="main-header">
        <!-- Logo -->
        <a href="/" class="logo">
          <span class="logo-mini"><b>게</b>토리</span>
          <span class="logo-lg"><b>게</b>시판 팩<b>토리</b></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              
              <!-- 상단 로그인 상태 화면 -->
              <%
              if(vo==null){
              %>
              <li class="btnHide">
              	<a class="loginbtn" style="cursor:pointer">로그인</a>
              </li>
              <li class="btnHide">
              	<a onclick ="signUpFunction()" style="cursor:pointer">회원가입</a>
              </li>
              <%
              }else{
              %>
              <li class="dropdown user user-menu" class="loginSuccess">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <!-- <img src="/resources/dist/img/user2-160x160.jpg" class="user-image" alt="User Image"/> -->
                  <span class='nickName'><%=vo.getNickname()%></span>
                  <span>님 환영합니다.</span>
                </a>
                
                <!-- 상단 닉네임 클릭 했을 때 -->
                <ul class="dropdown-menu">
                  <!-- User image -->
                  <li class="user-header">
                    <img src="/resources/img/logo.png" class="img-circle" alt="User Image" />
                    <p class="nickName">
                      <%=vo.getNickname()%>
                    </p>
                  </li>
                  <!-- Menu Body -->
                  <!-- <li class="user-body">
                    <div class="col-xs-4 text-center">
                      <a href="#">Followers</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Sales</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Friends</a>
                    </div>
                  </li> -->
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="/join/inFormUpdate" class="btn btn-default btn-flat">개인정보 수정</a>
                    </div>
                    <div class="pull-right">
                      <a href="#" class="btn btn-default btn-flat logout">로그아웃</a>
                    </div>
                  </li>
                </ul>
              </li>
              <%
              }
              %>
              <!-- Control Sidebar Toggle Button -->
              <!-- <li>
                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
              </li> -->
            </ul>
          </div>
        </nav>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <!-- Sidebar user panel -->
          <div class="user-panel">
            <div class="pull-left image">
              <!-- <img src="/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" /> -->
              <img src="/resources/img/logo.png" class="img-circle" alt="Logo Image" />
            </div>
            <%
            if(vo==null){
            %>
            <!-- 메뉴 로그아웃 화면 -->
            <div class="pull-left info">
            	<button class="btn btn-primary loginbtn">로그인</button>
            	<!-- 회원가입 누르면 회원가입 페이지로 이동 kyo 17.11.16 -->
            	<button class="btn btn-warning" onclick ="signUpFunction()">회원가입</button>
            </div>
            <%
            }else{
            %>
            <!-- 메뉴 로그인 상태화면 -->
            <div class="loginSuccess">
	            <div class="pull-left info">
	              <p class="nickName" style="color: #b8c7ce;"><%=vo.getNickname()%></p>
	
	              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
	            </div>
            </div>
            <%
            }
            %>
          </div>
          <!-- search form -->
		  <div class="sidebar-form">
			<div class="input-group">
	            <input type="text" id="sideSearchBoard" class="form-control" placeholder="게시판 제목검색" onkeypress="if(event.keyCode==13) {$('#sideSearchBoardBtn').trigger('click'); return false;}"/>
	            <span class="input-group-btn">
	              <button id='sideSearchBoardBtn' class="btn btn-flat"><i class="fa fa-search"></i></button>
	            </span>
          	</div>
          </div>
          <div class="sidebar-form" id="divListBoard" style="display:none; width: 210px; height: 150px; margin: 10px; overflow-y: auto;">
	          <div class="box-body no-padding">
		          <div class="form-group">
					<ul class="nav nav-pills nav-stacked" id="listBoardNames">
						
					</ul>
		          </div>
	          </div>
          </div>
          <!-- /.search form -->
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-book"></i> <span>공지</span> <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="/board/list?lNo=1"><i class="fa fa-circle-o"></i> 목록</a></li>
                <%
                if(vo!=null && vo.getGrade() == 3){
                %>
	            <li><a href="/board/register?lNo=1"><i class="fa fa-circle-o"></i> 등록</a></li>
				<%	         
                }
                %>
              </ul>
            </li>
            <li class="treeview">
				<a href="#">
					<i class="fa fa-laptop"></i>
					<span>게시판</span>
					<i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="/board/listBoard"><i class="fa fa-circle-o"></i>게시판 목록</a></li>
					<li><a href="/board/recentList"><i class="fa fa-circle-o"></i>최신 글 목록</a></li>
					<%
	                if(vo!=null && vo.getlNoManager() > 0){
	                %>
					<li><a href="/board/list?lNo=<%=vo.getlNoManager()%>"><i class="fa fa-circle-o"></i>나의 게시판</a></li>
					<%
	                }
					%>
					<%
	                if(vo!=null){
	                %>
					<li><a href="/board/myList"><i class="fa fa-circle-o"></i>내가 쓴 글</a></li>
					<li><a href="/board/myReply"><i class="fa fa-circle-o"></i>내가 쓴 댓글</a></li>
					<%
	                }
					%>
				</ul>
            </li>
            <%
            if(vo!=null && vo.getGrade() == 3){
            %>
            <li class="treeview">
				<a href="#">
					<i class="fa fa-laptop"></i>
					<span>최고 관리자 전용</span>
					<i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="/admin/boardList"><i class="fa fa-circle-o"></i> 게시판 관리</a></li>
					<li><a href="/admin/memberList"><i class="fa fa-circle-o"></i> 회원 관리</a></li>
					<li><a href="/admin/blackList"><i class="fa fa-circle-o"></i> 블랙리스트</a></li>
					<!-- <li><a href="/admin/tabooWordList"><i class="fa fa-circle-o"></i> 금지어 관리</a></li> -->
				</ul>
            </li>
            <%
            }
            %>
            
            <%
            if(vo!=null && vo.getGrade() == 2){
            %>
            <li class="treeview">
				<a href="../widgets.html">
					<i class="fa fa-th"></i> 
					<span>주 관리자 전용</span> 
					<i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="/manager/boardMan"><i class="fa fa-circle-o"></i> 게시판 설정</a></li>
					<li><a href="/manager/contentMan"><i class="fa fa-circle-o"></i> 게시글 관리</a></li>
					<li><a href="/manager/managerMan"><i class="fa fa-circle-o"></i> 매니저 관리 </a></li>
					<li><a href="/manager/outlistMan"><i class="fa fa-circle-o"></i> 블랙리스트 관리</a></li>
	            </ul>
            </li>
            <%
            }
            %>
            
            <%
            if(vo!=null && vo.getGrade() == 1){
            %>
            <li class="treeview">
				<a href="#">
					<i class="fa fa-edit"></i>
					<span>부 매니저 전용</span>
					<i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="/manager/contentMan"><i class="fa fa-circle-o"></i> 게시글 관리</a></li>
					<li><a href="/manager/outlistMan"><i class="fa fa-circle-o"></i> 블랙리스트 관리</a></li>
					<li><a href="javascript:funcOutManager();"><i class="fa fa-circle-o"></i> 부 매니저 탈퇴</a></li>
				</ul>
            </li>
            <%
            }
            %>
            
            <%
            if(vo!=null && vo.getGrade() == 0){
            %>
            <li class="treeview">
				<a href="#">
					<i class="fa fa-edit"></i>
					<span>일반 회원 전용</span>
					<i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="/manager/boardMan"><i class="fa fa-circle-o"></i> 게시판 설정</a></li>
				</ul>
            </li>
            <%
            }
            %>
            <!-- 
            <li><a href="/resources/documentation/index.html"><i class="fa fa-book"></i> <span>Documentation</span></a></li>
            <li class="header">LABELS</li>
            <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
            <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
            <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li> -->
          </ul>
          
          	<!-- 자유 채팅창 추가. ssh. 171205 -->
			<%
			if(vo!=null){
			%>
          	<div class="sidebar-form" style="width: 210px; height: 505px; margin: 10px;">
				<div class="box-body no-padding">
					<div class="form-group">
						<iframe src="http://192.168.0.56:52273/?nickname=<%=vo.getNickname()%>" width="210px" height="505px" scrolling="no"></iframe>
					</div>
				</div>
			</div>
			<%
			}else{
			%>
			<div class="sidebar-form" style="width: 210px; height: 400px; margin: 10px;">
				<div class="box-body no-padding">
					<div class="form-group">
						<iframe src="http://192.168.0.56:52273/?nickname=" width="210px" height="400px" scrolling="no"></iframe>
					</div>
				</div>
			</div>
			<%
			}
			%>
		
        </section>
        <!-- /.sidebar -->
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <!-- 헤더 부분 높이 조절, 새로만들어진 게시판 슬라이더 추가시킴 ktg 17.11.14 -->
        <section class="content-header" style="height: 120px;">
			<h1>
				Board Management
				<small>Preview</small>
			</h1>
    		
			<ol class="breadcrumb">
				<li style="width: 150px;"><i class="fa fa-paper-plane-o"></i> 신규 게시판 목록 </li>
			</ol>
			<div class="flexslider" style="width: 150px; float: right; z-index:100; margin-top: 9px; border-style: solid; border-width: 1px; border-color: #333;">
				<ol class="slides newBoard">
				</ol>
			</div>
			<!-- <ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
				<li><a href="#">Forms</a></li>
				<li class="active">General Elements</li>
			</ol> -->
        </section>
		<div class="a">
			<a href="#" class="top"><i class="fa fa-angle-double-up" aria-hidden="true"></i> Top</a>
		</div>