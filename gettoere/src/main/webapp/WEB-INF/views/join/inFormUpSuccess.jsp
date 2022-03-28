<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@include file="../include/header.jsp" %>
<script>
//상단 제목 변경. ssh. 171129
$(".content-header h1").html("");

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
						<h1><small>gettoere</small>&nbsp회원정보 수정이 완료되었습니다.</h1>
					</div>
					<div class="col-md-6 col-md-offset-3">
						<div class="form-group text-center">
							<button type="button" class="btn btn-info" onclick="goFirstForm()">
								확인<i class="fa fa-check spaceLeft"></i>
							</button>
						</div>
					</div>
				</div>
			
			</div>
		</div>
	</div>
</section>
<%@include file="../include/footer.jsp" %>


