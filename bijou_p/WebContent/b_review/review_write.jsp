<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
    
    <%
    	request.setCharacterEncoding("UTF-8");
    	String PATH = request.getContextPath();
    	
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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Q&A</title>
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
	
	<script type="text/javascript" src="<%=PATH %>/ckeditor/ckeditor.js"> </script>
	

<script type="text/javascript">

    function readURL(input) { 
	   
	   console.debug(input); 
       console.debug(input.files); 
       
	   if (input.files && input.files[0]) {
		  
		  $("#view").html("<img  id='preview' src='#'   width=200 height=200/>");
		   
	      var reader = new FileReader();
 
	      reader.readAsDataURL(input.files[0]);
	      
	      reader.onload = function (ProgressEvent) {  
    		  
    		  console.debug(ProgressEvent);
    	
	      	$('#preview').attr('src',ProgressEvent.target.result);
          }
    	 
      }  
} 
   
   
   
  function backToList(obj){
    obj.action="${contextPath}/review/list.do";
    obj.submit();
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
				<ul>
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

	<form action="${contextPath}/review/write.do" method="post" id="h1" enctype="multipart/form-data">
	 <h1>글쓰기<hr></h1>
	 <table align="center">
				<tr>
					<td>상품 번호</td>
						<td>${product_num}
							<input type="hidden" value="${product_num}" name="product_num" />
							<input type="hidden" value="${oorder_num}" name="oorder_num" />
						</td>
				</tr>	
				<tr>
					<td>글제목</td>
					<td colspan="3"><input type="text" size="67"  maxlength="500" name="review_title" class="input_box"/></td>
				</tr>
				<tr>
					<td style="margin-top:5px;">글내용</td>
					<td colspan="3">
						<textarea class="form-control" id="r_content" name="review_content"></textarea>
						<script type="text/javascript">
							CKEDITOR.replace('r_content', {height: 500});
						</script>
					</td>
				</tr>		
				<tr>
					<td style="margin-top:5px;">이미지첨부 &nbsp;</td>
					<td><input type="file"  name="review_img"  onchange="readURL(this);" /></td>
					<td id="view"></td>
				</tr> 		 		 		 
	 </table>
	 			<div align="center" id="write_button">
	 					<input type="submit" value="글쓰기">
	 				<input type="button" value="글목록" onClick="backToList(this.form)">
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



