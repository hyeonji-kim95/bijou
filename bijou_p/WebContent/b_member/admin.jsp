<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix = "fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
  request.setCharacterEncoding("UTF-8");

  String member_id = (String)session.getAttribute("member_id");

%>

<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%	if(member_id == null) { %>
		<script>
			location.href="${contextPath}/member/login_go.do";
		</script>
<%	} %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Admin</title>
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


		<h1 align="center">회원 관리</h1>
		
	<table align="left" class="admin_td">
		<tr align="center">
			<td width="5%">ID</td>
			<td width="5%">PW</td>
			<td width="5%">Name</td>
			<td width="10%">Phone</td>
			<td width="10%">Post</td>
			<td width="30%">Address</td>
			<td width="5%">Rating</td>
			<td width="5%">Point</td>
			<td width="10%">Save</td>
			<td width="10%">Delete</td>
		</tr>
		
		<c:choose>
	<c:when test="${memberList == null}">
		<tr height="10">
			<td colspan="4">
				<p align="center">
					<b><span style="font-size: 9pt">등록된 회원이 없습니다.</span></b>
				</p>
			</td>
		</tr>	
	</c:when>
	<c:when test="${memberList != null}">
	<c:forEach var="memberList" items="${memberList}" begin="0" end="${fn:length(memberList)}"	>
	<form action="${contextPath}/member/admin_update.do" method="post" id="h1">
		<tr>
			<td><input type="text" size="6" value="${memberList.member_id}" name="member_id" class="input_box"/></td>
			<td><input type="text" size="6" value="${memberList.member_pw}" name="member_pw" class="input_box"/></td>
			<td><input type="text" size="6" value="${memberList.member_name}" name="member_name" class="input_box"/></td>
			<td><input type="text" size="11" value="${memberList.member_phone}" name="member_phone" class="input_box"/></td>
			<td><input type="text" size="8" value="${memberList.member_post}" name="member_post" class="input_box"/></td>
			<td>
				<input type="text" size="17" value="${memberList.member_address1}" name="member_address1" class="input_box"/>
				 <input type="text" size="17" value="${memberList.member_address2}" name="member_address2" class="input_box"/>
				 <input type="text" size="17" value="${memberList.member_address3}" name="member_address3" class="input_box"/>
			</td>
			<td>
				<select id="rating" title="rating" name="member_rating">
	    			<option value="S" <c:if test="${memberList.member_rating eq 'S'}">selected</c:if>>S</option>
	    			<option value="G" <c:if test="${memberList.member_rating eq 'G'}">selected</c:if>>G</option>
	    			<option value="V" <c:if test="${memberList.member_rating eq 'V'}">selected</c:if>>V</option>
				</select>
			</td>
			<td><input type="text" size="7" value="${memberList.member_point}" name="member_point" class="input_box"/></td>
			<td width="10%"><input type="submit" value="Save"></td>
			<td width="10%"><c:if test="${memberList.member_id ne 'admin'}"><input type="button" value="Delete" onClick="location.href='${contextPath}/member/admin_delete.do?member_id=${memberList.member_id}'"></c:if></td>
		</tr>
	</form>
		</c:forEach>
	</c:when>
</c:choose>
			
		</table>
	

<!-- footer-main -->
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



