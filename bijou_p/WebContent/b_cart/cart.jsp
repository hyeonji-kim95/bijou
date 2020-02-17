<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cart</title>
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

  
  <% if(member_id == null) { %>
  	<script type="text/javascript">
 	 	location.href="${contextPath}/member/login_go.do";
 	 </script>
  <% } %>
  
  <script type="text/javascript">
		function onlyNumber(event){
		    event = event || window.event;
		    var keyID = (event.which) ? event.which : event.keyCode;
		    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		        return;
		    else
		        return false;
		}
		 
		function removeChar(event) {
		    event = event || window.event;
		    var keyID = (event.which) ? event.which : event.keyCode;
		    if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		        return;
		    else
		        event.target.value = event.target.value.replace(/[^0-9]/g, "");
		}
		
		function fn_change(obj) {
			var cart_count = document.getElementById("cart_count").value;
	 		var product_count = document.getElementById("product_count").value;

	 		<% if(member_id != null) {%>
		 		if(order_count <= 0){
	 				alert("1 이상 입력해주세요");
	 			} else {
		 			document.form.action="${contextPath}/cart/modify.do";
		 			document.form.submit();
	 			}
		 	<% } else { %>
		 		location.href="${contextPath}/member/login_go.do";
		 	<% } %>
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

	 <h1 align="center">장바구니</h1>
	 <table>
		 <c:choose>
			<c:when test="${listCart == null }">
				<tr height="10">
					<td colspan="4">
						<p align="center">
							<b><span style="font-size: 9pt">장바구니에 담긴 상품 없습니다.</span></b>
						</p>
					</td>
				</tr>		
			</c:when>
			<c:when test="${listCart != null}">
			<c:set var="total" value="0" />
			<c:forEach var="listCart" items="${listCart}" begin="0" end="${fn:length(listCart)}" >
				<form action="${contextPath}/cart/modify.do" method="post" name="mm">
				<input type="hidden" value="${listCart.cart_num}" name="cart_num" varstatus="status">
	 			<tr>
					<c:forEach var="productCart" items="${productCart}" begin="0" end="${fn:length(productCart)}">
						<c:if test="${listCart.product_num eq productCart.product_num}">
							<input type="hidden" value="${productCart.product_count}" id="product_count" name="product_count"/>
							<th><a href="${contextPath}/product/content.do?product_num=${listCart.product_num}">${productCart.product_name}</a></th>
							<th><a href="${contextPath}/product/content.do?product_num=${listCart.product_num}">
								<img src="${contextPath}/download_p.do?product_num=${productCart.product_num}&product_img=${productCart.product_img}" alt="" width="200px"/></a>
							</th>
						</c:if>
					</c:forEach>
					<th><input type="text" value="${listCart.cart_count}" name="cart_count" id="cart_count" size="2" class="input_box" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' onblur="fn_change(this.form);")/>
					</th>
					<th><input type="submit" value="수량변경" class="input_box"></th>
					<th><input type="text" value="${listCart.cart_price}" name="cart_price" id="cart_price" readonly class="input_box"/></th>
					<th><input type="button" value="삭제" onclick="location.href='${contextPath}/cart/delete.do?cart_num=${listCart.cart_num}'" ></th>
						<c:set var="total" value="${total+listCart.cart_price}" />
				</tr>
				</form>
			</c:forEach>
					<th colspan="6" style="text-align: right;">총 결제 금액 :
						<c:out value="${total}" /> 원</th>
			</c:when>
		</c:choose>
		<c:if test="${total != 0}">
	 			<tr>
	 				<td width="400" colspan="6" style="text-align: center;">				
	 				<input type="button" value="주문하기" onclick="location.href='${contextPath}/oorder/cart_go.do?total=${total}'"></td>
	 			</tr> 	
	 	</c:if>			 		 
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




