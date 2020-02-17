<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
  request.setCharacterEncoding("UTF-8");

  String member_id = (String)session.getAttribute("member_id");
  
%>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%	if(member_id == null) { %>
		<script>
			location.href="${contextPath}/member/login_go.do";
		</script>
<%	} %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MyOrder</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
	<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
	<meta name="author" content="FREEHTML5.CO" />

	<!-- Animate.css -->
	<link rel="stylesheet" href="../css/animate.css">
	<!-- Icomoon Icon Fonts-->
	<link rel="stylesheet" href="../css/icomoon.css">
	<!-- Bootstrap  -->
	<link rel="stylesheet" href="../css/bootstrap.css">
	<!-- Owl Carousel -->
	<link rel="stylesheet" href="../css/owl.carousel.min.css">
	<link rel="stylesheet" href="../css/owl.theme.default.min.css">

	<link rel="stylesheet" href="../css/style.css">

  
  <script type="text/javascript">
  	function fn_new(oorder_num) {
  		window.open("./transnum_go.do?oorder_num=" + oorder_num, "", "width=400,height=250");
  	}
  	
  	function fn_status(oorder_num, oorder_status) {
  		location.href="${contextPath}/oorder/status.do?oorder_num=" + oorder_num + "&oorder_status=" + oorder_status;
  	}
  </script>

</head>
<body>


<!--header top-->
<div class="header-top hder-hgt">
<%if(member_id == null) { %>
				  <a href="${contextPath}/member/login_go.do">로그인</a> |
                  <a href="${contextPath}/member/join_go.do">회원가입</a>
				  
<%} else { 
	if(member_id.equals("admin")) {%>
				  <div class="dropdown">
				   <a class="mypage_btn">마이페이지</a> | <a href="${contextPath}/member/logout.do">로그아웃</a>
				   	<div class="mypage_content">
				  		<a href="${contextPath}/oorder/admin.do">주문처리</a>
                  		<a href="${contextPath}/member/admin.do">회원관리</a>
                 	</div>
                 </div>
<%}else{ %>         
				<div class="dropdown">
				 <a class="mypage_btn">마이페이지</a> | <a href="${contextPath}/member/logout.do" class="logout_mypage">로그아웃</a>
                 	<div class="mypage_content">
                		<a href="${contextPath}/member/modify_go.do">정보수정</a>
                 		<a href="${contextPath}/cart/mycart.do">장바구니</a>
                 		<a href="${contextPath}/oorder/myorder.do">주문조회</a>
                 	</div>
                 </div>
<% }
	   } %> 
</div>
<!--header top-->

<!--Main Header-->
	<header>
		<div class="container text-center">
			<div class="fh5co-navbar-brand">
				<a href="${contextPath}/main/index.do">
                              <img src="../img/bijouLogo.gif" alt="" width="130">
                </a>
			</div>
			<nav id="fh5co-main-nav" role="navigation">
				<ul class="ahover">
					<li class="active">
                              <a href="${contextPath}/main/index.do">Home</a>
                        </li>
                        <li>
                              <a href="${contextPath}/product/list.do">Earring</a>
                        </li>
                        <li>
                              <a href="${contextPath}/notice/list.do">Notice</a>
                        </li>
                        <li>
                              <a href="${contextPath}/qna/list.do">Q&A</a>
                        </li>
				</ul>
			</nav>
		</div>
	</header>


		 <h1 align="center">주문조회</h1>
		 
		<table align="center" style="margin: 20 0 0 0%" style="width:100%">
		 <select id="oorder_status" title="oorder_status" name="oorder_status" onchange="location.href=this.value">
			<option>주문상태</option>
			<option value="${contextPath}/oorder/admin.do">all</option>
   			<option value="${contextPath}/oorder/hold.do" >hold</option>
   			<option value="${contextPath}/oorder/preparing.do" >preparing</option>
   			<option value="${contextPath}/oorder/shipping.do" >shipping</option>
   			<option value="${contextPath}/oorder/completed.do" >completed</option>	
   			<option value="${contextPath}/oorder/completedR.do" >completedR</option>	
			<option value="${contextPath}/oorder/cancle.do" >cancle</option>	
   		</select>
   		
				<tr align="center">
					<td width="5%">주문번호</td>
					<td width="5%">상품번호</td>
					<td width="5%">회원번호</td>
					<td width="5%">이름</td>
					<td width="8%">전화번호</td>
					<td width="5%">주문수량</td>
					<td width="8%">주문가격</td>
					<td width="8%">우편번호</td>
					<td width="15%">주소</td>
					<td width="8%">주문날짜</td>
					<td width="8%">주문상태</td>	
					<td width="8%">운송장번호</td>
					<td width="10%">결제방식</td>
					<td width="5%">주문취소</td>
				</tr>
			<c:choose>
				<c:when test="${list == null}">
					<tr height="10">
						<td colspan="4">
							<p align="center">
								<b><span style="font-size: 9pt">주문내역이 없습니다.</span></b>
							</p>
						</td>
					</tr>	
				</c:when>	
				<c:when test="${list != null}">
					<c:forEach var="list" items="${list}" begin="0" end="${fn:length(list)}">
						<tr height="10" align="center">
							<td width="8%">${list.oorder_num}</td>
							<td width="5%">
								<a href="${contextPath}/product/content.do?product_num=${list.product_num}">${list.product_num}</a>
							</td>
							<td width="8%">${list.member_num}</td>
							<td width="5%">${list.oorder_name}</td>
							<td width="8%">${list.oorder_phone}</td>
							<td width="5%">${list.oorder_count}</td>
							<td width="8%">${list.oorder_price}</td>
							<td width="8%">${list.oorder_post}</td>
							<td width="30%">${list.oorder_address1}, ${list.oorder_address2}, ${list.oorder_address3}</td>
							<td width="8%">${list.oorder_date}</td>
							<td width="8%">
								<select id="status_change" title="status_change" name="status_change" onchange="fn_status(${list.oorder_num}, this.value);">
					    			<option value="hold" <c:if test="${list.oorder_status eq 'hold'}">selected</c:if>>hold</option>
					    			<option value="preparing" <c:if test="${list.oorder_status eq 'preparing'}">selected</c:if>>preparing</option>
					    			<option value="shipping" <c:if test="${list.oorder_status eq 'shipping'}">selected</c:if>>shipping</option>
					    			<option value="completed" <c:if test="${list.oorder_status eq 'completed'}">selected</c:if>>completed</option>
					    			<option value="completedR" <c:if test="${list.oorder_status eq 'completedR'}">selected</c:if>>completedR</option>	
					    			<option value="cancle" <c:if test="${list.oorder_status eq 'cancle'}">selected</c:if>>cancle</option>	
					    		</select>
							</td>
							<td width="8%"><input type="button" style="width:80pt;" value="${list.oorder_transnum}" onclick="fn_new(${list.oorder_num});" /></td>
							<td width="8%">${list.oorder_pay}</td>
							<td width="8%">
							<input type="button" value="주문삭제" onclick="location.href='${contextPath}/oorder/delete.do?oorder_num=${list.oorder_num}'"/>
							</td>
						</tr>	
					</c:forEach>
				</c:when>
			</c:choose>	
		</table>
	
	

<!--footer-main-->
<footer>
		<div id="footer" class="fh5co-border-line">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-md-offset-2 text-center">
						<p>Copyright 2020. All Rights Reserved by <a href="#">Bijou</a><br>Made with <i class="icon-heart3 love"></i> by <a href="http://freehtml5.co/" target="_blank">Bijou</a></p>
						<p class="fh5co-social-icons">
							<a href="#"><i class="icon-twitter-with-circle"></i></a>
							<a href="#"><i class="icon-facebook-with-circle"></i></a>
							<a href="#"><i class="icon-instagram-with-circle"></i></a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</footer>

	<!-- jQuery -->
	<script src="../js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="../js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="../js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="../js/jquery.waypoints.min.js"></script>
	<!-- Owl carousel -->
	<script src="../js/owl.carousel.min.js"></script>
	<!-- Stellar -->
	<script src="../js/jquery.stellar.min.js"></script>

	<!-- Main JS (Do not remove) -->
	<script src="../js/main.js"></script>

	</body>
</html>

