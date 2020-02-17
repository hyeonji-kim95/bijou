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

 <script type="text/javascript">
 	function fn_product_del(obj){
	  
	  obj.action="${contextPath}/product/delete.do";
	  obj.submit();
	  
  }
 	
 	function fn_order(obj) {
 		var order_count = document.getElementById("order_count").value;
 		var product_count = document.getElementById("product_count").value;
 		
 		<% if(member_id != null) {%>
	 			if(order_count <= 0){
	 				alert("1 이상 입력해주세요");
	 			} else {
			 		if(order_count > product_count){
			 			alert("수량이 부족합니다.");
			 		} else {
			 			oorder.action="${contextPath}/oorder/product_go.do";
			 			oorder.submit();
			 		}
	 			}
	 	<% } else { %>
	 		location.href="${contextPath}/member/login_go.do";
	 	<% } %>
	}
 	
  </script>
  
  <script type="text/javascript">
	  function fn_cart(obj) {
			var order_count = document.getElementById("order_count").value;
			var product_count = document.getElementById("product_count").value;

			<% if(member_id != null) {%>
		 		if(order_count > product_count){
		 			alert("수량이 부족합니다.");
		 		}else if(order_count <= 0){
		 			alert("1 이상 입력해주세요");
		 		}else {
		 			oorder.action="${contextPath}/cart/cart.do";
		 			oorder.submit();
		 		}
		 	<% } else { %>
		 		location.href="${contextPath}/member/login_go.do";
		 	<% } %>
		}
  </script>
  
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
	
<h1 align="center">${vo.product_name}</h1>

		<!-- 일반사용자 일때 -->
		<c:if test="${member_id ne 'admin'}">
			<form action="${contextPath}/cart/cart.do" name="oorder">
				<div id="product" style="padding-top:10px;" align="center" id="product">
				<input type="hidden" value="${vo.product_num}" name="product_num">
				<input type="hidden" value="${vo.product_price}" name="product_price">
				</div>
        		<div class="" style="padding-top:10px;" align="center">
					<img src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_img=${vo.product_img}" id="preview" width="300px" height="350px"/><br>
				</div>
				<div class="" style="padding-top:10px;"  align="center">
					<label>${vo.product_name}</label>
				</div>
		        <div class="" style="padding-top:10px;"  align="center">
					<label>${vo.product_price}</label>원
				</div>
				<div class="" style="padding-top:10px;"  align="center">
					<label><input type="text" size="2" class="input_box" id="order_count" value="1"  name="oorder_count" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)'/></label>개
				</div>
				<c:choose>
					<c:when test="${vo.product_count > 0}">
						<div align="center">
							<input type="button" value="cart" onclick="fn_cart(this.form);" />
							<input type="button" value="order" onclick="fn_order(this.form);" />
							<input type="hidden" value="${vo.product_count}" id="product_count"/>
							<input type="button" value="list" onclick="location.href='${contextPath}/product/list.do'"/>
						</div>
					</c:when>
					<c:when test="${vo.product_count == 0}">
						<p>품절된 상품입니다.</p>
					</c:when>
				</c:choose>
				<div class="prd_content">
					<a href="#product">product</a>
					<a href="#qna">Q&A</a>
					<a href="#Review">Review</a>
				</div>
				<div align="center">
					<td>${vo.product_content}</td>
				</div>
				<div style="width:60%; margin-left: 20%">
					<c:if test="${vo.product_cimg1 ne null}">
						<img alt="" width="100%" height="90%" align="center" style="max-height:100%;max-width:100%;" src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_cimg1=${vo.product_cimg1}" />
					</c:if>
					<c:if test="${vo.product_cimg2 ne null}">
						<img alt="" width="100%" height="90%" style="max-height:100%;max-width:100%;" src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_cimg2=${vo.product_cimg2}" />
					</c:if>
					<c:if test="${vo.product_cimg3 ne null}">
						<img alt="" width="100%" height="90%" style="max-height:100%;max-width:100%;" src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_cimg3=${vo.product_cimg3}" />
					</c:if>
				</div>
				<div class="prd_content">
					<a href="#product">product</a>
					<a href="#qna">Q&A</a>
					<a href="#Review">Review</a>
				</div>
				<div id="qna">
				<h3 class="prd_content">Q&A</h3>
					<table class="table_1">
						<c:choose>
							<c:when test="${qnaList == null }">
								<tr height="10">
									<td colspan="4">
										<p align="center">
											<b><span style="font-size: 9pt">등록된 글이 없습니다.</span></b>
										</p>
									</td>
								</tr>	
							</c:when>
							<c:when test="${qnaList != null }">
					    	<c:forEach var="qnaList" items="${qnaList}" end="${fn:length(qnaList)}">
							    <tr align="center">
									<td>${qnaList.qna_num}</td>
									<c:if test="${qnaList.product_num == null}">
										<td> </td>
									</c:if>
									<c:if test="${qnaList.product_num != null}">
										<td>${qnaList.product_num}</td>
									</c:if>
									<% if(member_id != null && member_id.equals("admin")) { %>
										<td><a href="${contextPath}/qna/content.do?qna_num=${qnaList.qna_num}">${qnaList.qna_title}</a></td>
									<% } else { %>
										<td><a href="${contextPath}/qna/content_s_go.do?qna_num=${qnaList.qna_num}">${qnaList.qna_title}</a></td>
									<% } %>
									<c:if test="${qnaList.qna_re == null }">
									<td></td>
									</c:if>	
									<c:if test="${qnaList.qna_re != null }">
									<td>√</td>
									</c:if>
									<c:forEach var="memberList" items="${memberList}" end="${fn:length(memberList)}">
										<c:if test="${qnaList.member_num == memberList.member_num}">
											<td>${memberList.member_name}</td>
										</c:if>
									</c:forEach>
									  <td  width="10%"><fmt:formatDate value="${qnaList.qna_date}" /></td> 
								</tr>
						    </c:forEach>
					     </c:when>
					</c:choose>	
					<td align="center"><input type="button" value="글쓰기" onclick="location.href='${contextPath}/qna/write_go_product.do?product_num=${vo.product_num}'"/></td>
					</table>
				</div>
				<div class="prd_content">
					<a href="#product">product</a>
					<a href="#qna">Q&A</a>
					<a href="#Review">Review</a>
				</div>
				<div id="Review">
				<h3 class="prd_content">Review</h3>
				<table class="table_1">
						<c:choose>
							<c:when test="${reviewList == null }">
								<tr height="10">
									<td colspan="4">
										<p align="center">
											<b><span style="font-size: 9pt">등록된 글이 없습니다.</span></b>
										</p>
									</td>
								</tr>	
							</c:when>
							<c:when test="${reviewList != null }">
					    	<c:forEach var="reviewList" items="${reviewList}" end="${fn:length(reviewList)}">
							    <tr align="center">
									<td>${reviewList.review_num}</td>
									<c:if test="${reviewList.product_num == null}">
										<td> </td>
									</c:if>
									<c:if test="${reviewList.product_num != null}">
										<td>${reviewList.product_num}</td>
									</c:if>
									<td><a href="${contextPath}/review/content.do?review_num=${reviewList.review_num}">${reviewList.review_title}</a></td>
									<c:if test="${reviewList.review_re == null }">
									<td></td>
									</c:if>	
									<c:if test="${reviewList.review_re != null }">
									<td>√</td>
									</c:if>
									<c:forEach var="memberList" items="${memberList}" end="${fn:length(memberList)}">
										<c:if test="${reviewList.member_num == memberList.member_num}">
											<td>${memberList.member_name}</td>
										</c:if>
									</c:forEach>
									  <td  width="10%"><fmt:formatDate value="${reviewList.review_date}" /></td> 
								</tr>
						    </c:forEach>
					     </c:when>
					</c:choose>	
					</table>
				</div>
			</form>
		</c:if>
		<!-- 관리자 일때 -->
		<c:if test="${member_id eq 'admin'}">
			<form action="${contextPath}/product/save.do" method="post">
			<input type="hidden" value="${vo.product_num}" name="product_num">
			
				<div align="center" id="product">
					<img src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_img=${vo.product_img}" id="preview" width="300px" height="350px"/><br>
				</div>
				<div align="center">
					<input type="text" value="${vo.product_name}" name="product_name" />
				</div>
				<div align="center">
					<input type="text" value="${vo.product_price}" name="product_price" />원
				</div>
				<div align="center">
					<input type="text" value="${vo.product_count}" name="product_count" />개
				</div>
				<div align="center">
					<input type="submit" value="save" />
					<input type="button" value="delete" onclick="fn_product_del(this.form);" />
					<input type="button" value="list" onclick="location.href='${contextPath}/product/list.do'"/>
				</div>
				<div class="prd_content">
					<a href="#product">product</a>
					<a href="#qna">Q&A</a>
					<a href="#Review">Review</a>
				</div>
				<div align="center">
					<td>${vo.product_content}</td>
				</div>
				<div style="width:60%; margin-left: 20%">
					<c:if test="${vo.product_cimg1 ne null}">
						<img alt="" width="100%" height="90%" style="max-height:100%;max-width:100%;" src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_cimg1=${vo.product_cimg1}" />
					</c:if>
					<c:if test="${vo.product_cimg2 ne null}">
						<img alt="" width="100%" height="90%" style="max-height:100%;max-width:100%;" src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_cimg2=${vo.product_cimg2}" />
					</c:if>
					<c:if test="${vo.product_cimg3 ne null}">
						<img alt="" width="100%" height="90%" style="max-height:100%;max-width:100%;" src="${contextPath}/download_p.do?product_num=${vo.product_num}&product_cimg3=${vo.product_cimg3}" />
					</c:if>
				</div>
				<div class="prd_content">
					<a href="#product">product</a>
					<a href="#qna">Q&A</a>
					<a href="#Review">Review</a>
				</div>
				<div id="qna">
				<h3 class="prd_content">Q&A</h3>
					<table class="table_1">
						<c:choose>
							<c:when test="${qnaList == null }">
								<tr height="10">
									<td colspan="4">
										<p align="center">
											<b><span style="font-size: 9pt">등록된 글이 없습니다.</span></b>
										</p>
									</td>
								</tr>	
							</c:when>
							<c:when test="${qnaList != null }">
					    	<c:forEach var="qnaList" items="${qnaList}" end="${fn:length(qnaList)}">
							    <tr align="center">
									<td>${qnaList.qna_num}</td>
									<c:if test="${qnaList.product_num == null}">
										<td> </td>
									</c:if>
									<c:if test="${qnaList.product_num != null}">
										<td>${qnaList.product_num}</td>
									</c:if>
									<% if(member_id != null && member_id.equals("admin")) { %>
										<td><a href="${contextPath}/qna/content.do?qna_num=${qnaList.qna_num}">${qnaList.qna_title}</a></td>
									<% } else { %>
										<td><a href="${contextPath}/qna/content_s_go.do?qna_num=${qnaList.qna_num}">${qnaList.qna_title}</a></td>
									<% } %>
									<c:if test="${qnaList.qna_re == null }">
									<td></td>
									</c:if>	
									<c:if test="${qnaList.qna_re != null }">
									<td>√</td>
									</c:if>
									<c:forEach var="memberList" items="${memberList}" end="${fn:length(memberList)}">
										<c:if test="${qnaList.member_num == memberList.member_num}">
											<td>${memberList.member_name}</td>
										</c:if>
									</c:forEach>
									  <td  width="10%"><fmt:formatDate value="${qnaList.qna_date}" /></td> 
								</tr>
						    </c:forEach>
					     </c:when>
					</c:choose>	
					<td><input type="button" value="글쓰기" onclick="location.href='${contextPath}/qna/write_go_product.do?product_num=${vo.product_num}'"/></td>
					</table>
				</div>
				<div class="prd_content">
					<a href="#product">product</a>
					<a href="#qna">Q&A</a>
					<a href="#Review">Review</a>
				</div>
				<div id="Review">
				<h3 class="prd_content">Review</h3>
				<table class="table_1">
						<c:choose>
							<c:when test="${reviewList == null }">
								<tr height="10">
									<td colspan="4">
										<p align="center">
											<b><span style="font-size: 9pt">등록된 글이 없습니다.</span></b>
										</p>
									</td>
								</tr>	
							</c:when>
							<c:when test="${reviewList != null }">
					    	<c:forEach var="reviewList" items="${reviewList}" end="${fn:length(reviewList)}">
							    <tr align="center">
									<td>${reviewList.review_num}</td>
									<c:if test="${reviewList.product_num == null}">
										<td> </td>
									</c:if>
									<c:if test="${reviewList.product_num != null}">
										<td>${reviewList.product_num}</td>
									</c:if>
									<td><a href="${contextPath}/review/content.do?review_num=${reviewList.review_num}">${reviewList.review_title}</a></td>
									<c:if test="${reviewList.review_re == null }">
									<td></td>
									</c:if>	
									<c:if test="${reviewList.review_re != null }">
									<td>√</td>
									</c:if>
									<c:forEach var="memberList" items="${memberList}" end="${fn:length(memberList)}">
										<c:if test="${reviewList.member_num == memberList.member_num}">
											<td>${memberList.member_name}</td>
										</c:if>
									</c:forEach>
									  <td  width="10%"><fmt:formatDate value="${reviewList.review_date}" /></td> 
								</tr>
						    </c:forEach>
					     </c:when>
					</c:choose>	
					</table>
				</div>
			</form>
		</c:if>
		
	

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