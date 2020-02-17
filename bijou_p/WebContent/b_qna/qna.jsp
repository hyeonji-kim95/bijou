<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<title>Q&A</title>
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
<style>
   .no-uline {text-decoration:none;}
   .sel-page{text-decoration:none;color:red;}
   .cls1 {text-decoration:none;}
   .cls2{text-align:center; font-size:20px;}
 </style>
  
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

	<form action="${contextPath}/qna/list.do" method="post" id="h1">
	 <h1>Q & A</h1>
	 <table align="center" class="table_1">
 			<tr align="center" class="line">
				<th class="qna__no" width="10%">글번호</th>
				<th class="product_no" width="10%">상품번호</th>
				<th class="title" width="30%">글제목</th>
				<th class="title" width="5%">댓글</th>
				<th class="write" width="10%">작성자</th>
				<th class="date" width="10%">작성일</th>
			</tr> 	
	 	<c:choose>
		<c:when test="${qnaList == null}">
			<tr height="10">
				<td colspan="4">
					<p align="center">
						<b><span style="font-size: 9pt">등록된 글이 없습니다.</span></b>
					</p>
				</td>
			</tr>	
		</c:when>
		<c:when test="${qnaList != null}">
    	<c:forEach  var="qnaList" items="${qnaList}" end="${fn:length(qnaList)}">
		    <tr align="center">
				<td>${qnaList.qna_num}</td>
				<c:if test="${qnaList.product_num == 0}">
					<td>일반</td>
				</c:if>
				<c:if test="${qnaList.product_num != 0}">
					<td>${qnaList.product_num}</td>
				</c:if>
				<% if(member_id.equals("admin") && member_id != null) { %>
					<td><a href="${contextPath}/qna/content.do?qna_num=${qnaList.qna_num}">${qnaList.qna_title}</a></td>
				<% } else { %>
					<td><a href="${contextPath}/qna/content_s_go.do?qna_num=${qnaList.qna_num}">${qnaList.qna_title}</a></td>
				<% } %>
				<c:if test="${qnaList.qna_re == null }">
				<td> </td>
				</c:if>	
				<c:if test="${qnaList.qna_re != null }">
				<td>√</td>
				</c:if>
				<c:forEach var="memberList" items="${memberList}" end="${fn:length(memberList)}">
					<c:if test="${qnaList.member_num eq memberList.member_num}">
						<td>${memberList.member_name}</td>
					</c:if>
				</c:forEach>
				  <td  width="10%"><fmt:formatDate value="${qnaList.qna_date}" /></td> 
			</tr>
	    </c:forEach>
     </c:when>
</c:choose>	
	 </table>
	 
	<div class="page_num">
 	<c:if test="${count != null }" >
      <c:choose>
        <c:when test="${count >100 }">  <!-- 글 개수가 100 초과인경우 -->
	      <c:forEach   var="page" begin="1" end="10" step="1" >
	         <c:if test="${section >1 && page==1 }">
	          <a class="no-uline" href="${contextPath }/qna/list.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp; 이전 </a>
	         </c:if>
	          <a class="no-uline" href="${contextPath }/qna/list.do?section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
	         <c:if test="${page ==10 }">
	          <a class="no-uline" href="${contextPath }/qna/list.do?section=${section+1}&pageNum=${section*10+1}">&nbsp; 다음</a>
	         </c:if>
	      </c:forEach>
        </c:when>
        <c:when test="${count ==100 }" >  <!--등록된 글 개수가 100개인경우  -->
	      <c:forEach   var="page" begin="1" end="10" step="1" >
	        <a class="no-uline"  href="#">${page } </a>
	      </c:forEach>
        </c:when>
        <c:when test="${count< 100 }" >   <!--등록된 글 개수가 100개 미만인 경우  -->
	      <c:forEach   var="page" begin="1" end="${count/10 +1}" step="1" >
	         <c:choose>
	           <c:when test="${page==pageNum }">
	            <a class="sel-page"  href="${contextPath }/qna/list.do?section=${section}&pageNum=${page}">${page } </a>
	          </c:when>
	          <c:otherwise>
	            <a class="no-uline"  href="${contextPath }/qna/list.do?section=${section}&pageNum=${page}">${page } </a>
	          </c:otherwise>
	        </c:choose>
	      </c:forEach>
        </c:when>
      </c:choose>
    </c:if>
	</div>    
	 <br><br>
	 	<div align="right" class="btn_write">
			<% if(member_id != null) { %>
	 			<input type="button" value="글쓰기" onclick="location.href='${contextPath}/qna/write_go.do'">
			<%} %>
		</div>
	</form>

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

