<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
  request.setCharacterEncoding("UTF-8");

  String member_id = (String)session.getAttribute("member_id");
  
  if(member_id == null) {}
  
%>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product</title>
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

	
	 <h1 align="center">상품목록</h1>
	 	
	 	<div align="right" class="btn_write">
			<% if(member_id != null && member_id.equals("admin")) { %>
				<input type="button" value="상품등록" onclick="location.href='${contextPath}/product/write_go.do'">
	 		<% } %>
		</div>
		
	 	<c:choose>
		<c:when test="${listProduct == null }">
			<b><span style="font-size: 9pt">등록된 상품이 없습니다.</span></b>
		</c:when>
		<c:when test="${listProduct != null}">
			<c:forEach var="listProduct" items="${listProduct}" begin="0" end="${fn:length(listProduct)}"	>
			<div class="prod-img">
				<form action="${contextPath}/product/list.do" method="post" id="product" style="width:350px;margin-left: 0;">
					
					<img src="${contextPath}/download_p.do?product_num=${listProduct.product_num}&product_img=${listProduct.product_img}"
							 id="preview" name="product_img"  alt="img" width="350px" height="350px"
							 onclick="location.href='${contextPath}/product/content.do?product_num=${listProduct.product_num}'" /><br>
					<a href="${contextPath}/product/content.do?product_num=${listProduct.product_num}">${listProduct.product_name}</a><br>
					${listProduct.product_price}원
					
		</form>
		</div>
		
			</c:forEach>
		</c:when>
	</c:choose>	
	 
	 
	
	
	
<!--footer-main-->
<footer style="clear:both;">
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


