<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
.mainCenter {
    margin-bottom: 5px;
	padding: 10px;
}
.boardBox{
	height:440px;
	padding: 5px;
	background-color:#3c8dbc;
	border-radius: 5px;
}
.dying{
	height:440px;
	border-style: solid;
    border-width: 20px;
	background-color: #fff;
}
h4{
    color: #fff;
}
.dyingImg {
	width: 100%;
	height: auto;
}
.portImg {
	width: 100%;
}
</style>
<script>
	//상단 제목 변경. ssh. 171129
	$(".content-header h1").html("자유로운 커뮤니티 사이트 - 게토리");
</script>
<!-- Main content -->
<section class="content">
	<div class="at-content">
		<div class="row row-5">
			<div class="col-sm-6 col-5 mainCenter">
				<div class="boardBox">
					<div class="main-head">
						<h4>인기게시판</h4>
					</div>
					<div>
						<table class="table table-bordered"
							style="background-color: #E2F0F0;">
							<c:forEach items="${bestBoard}" var="BoardVO">
								<tr>
									<td><a href='/board/list?lNo=${BoardVO.lNo}'>${BoardVO.name}</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-5 mainCenter">
				<div class="boardBox">
					<div class="main-head">
						<h4>인기 글</h4>
					</div>
					<div>
						<table class="table table-bordered"
							style="background-color: #E2F0F0;">
							<c:forEach items="${bestPosts}" var="BoardVO">
								<tr>
									<td><a href='/board/read?lNo=${BoardVO.lNo}&bNo=${BoardVO.bNo}'>${BoardVO.title}</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-5 mainCenter">
				<div class="boardBox">
					<div class="main-head">
						<h4>광고</h4>
					</div>
					<!-- Place somewhere in the <body> of your page -->
					<div class="flexslider2">
						<ul class="slides">
							<li><a href="http://bug.dothome.co.kr/Portfolio/" target="_blank"><img class="portImg" src="/resources/img/port01.jpg"/></a></li>
							<li><a href="http://ssleo.dothome.co.kr/portfolio/" target="_blank"><img class="portImg" src="/resources/img/port02.jpg"/></a></li>
							<li><a href="http://kyo.dothome.co.kr/portfolio" target="_blank"><img class="portImg" src="/resources/img/port03.jpg"/></a></li>
							<li><a href="http://bernicek.dothome.co.kr/portfolio/" target="_blank"><img class="portImg" src="/resources/img/port04.jpg"/></a></li>
							<li><a href="http://todo.dothome.co.kr/portfolio/" target="_blank"><img class="portImg" src="/resources/img/port05.jpg"/></a></li>
							<li><a href="http://waterdragon.dothome.co.kr/kingkong" target="_blank"><img class="portImg" src="/resources/img/port06.jpg"/></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-5 mainCenter">
				<div class="dying" style="text-align: center;">
					<img class="dyingImg" src="/resources/img/dyingImg.png"/>
					<div class="main-head">
					</div>
					<div>
						<table class="table table-bordered">
							<c:forEach items="${dyingBoard}" var="BoardVO">
								<tr>
									<td>
										<a href='/board/list?lNo=${BoardVO.lNo}'>
										<img src="/resources/img/flower.png"/>
										${BoardVO.name}
										<img src="/resources/img/flower.png"/>
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- /.content -->