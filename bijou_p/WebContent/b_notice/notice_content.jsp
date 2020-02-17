<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Notice</title>
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

   <script type="text/javascript" >
   
     function backToList(obj){
    	 obj.action = "${contextPath}/notice/list.do";
    	 obj.submit();
     }
 	
	 function fn_enable(obj){
		 
		 document.getElementById("notice_title").disabled=false;
		 document.getElementById("notice_content").disabled=false;
		 document.getElementById("notice_img").disabled=false;
 		 
	 }
	 
	 function fn_modify(obj){
		 obj.action = "${contextPath}/notice/modify.do";
		 obj.submit();
	 }
	 
	 function readURL(input) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $('#preview').attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	 }  
	 
	 $(document).ready(function(){
	     //현재HTML문서가 브라우저에 로딩이 끝났다면   
	       
	           
	        $('#modify_btn').click(function(){
	           $('#notice_modify').show();
	                    });
	        
	        $('#modify_btn').click(function(){
		           $('#notice_btn').hide();
		                    });
	        
	    });
	    
	 
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


	<form action="${contextPath}" method="post" enctype="multipart/form-data" id="h1">
	<h1>공지사항<hr></h1>
	  <table align="center">
		  <tr>
			  <td width="150" align="center">글번호</td>
			  <td >
				  <input type="text"  value="${noticeContent.notice_num }"  disabled />
				  <!-- 글 수정시 글번호를 컨트롤러로 전송하기 위해 미리 <hidden>태그를 이용해 글번호를 request에 저장함. -->
				  <input type="hidden" name="notice_num" value="${noticeContent.notice_num}"  />
			  </td>
		  </tr>
		  <tr>
			  <td width="150" align="center">제목</td>
			  <td><input type=text value="${noticeContent.notice_title }"  name="notice_title"  id="notice_title" disabled /></td>   
		  </tr>
		  <tr>
			   <td width="150" align="center">내용</td>
			   <td>
			   		<textarea rows="20" cols="60"  name="notice_content"  id="notice_content"  disabled><c:out value='${noticeContent.notice_content.replaceAll("\\\<.*?\\\>","")}' /></textarea>
			   </td>  
		  </tr>
	 
	<c:if test="${not empty noticeContent.notice_img && noticeContent.notice_img!='null' }">  
		<tr>
		   <td width="150" align="center" rowspan="2">이미지</td>
		   <td>
		   	 <!-- 이미지 수정에 대비해 미리 원래 이미지 파일이름을 <hidden>태그로 저장 -->
		     <input type= "hidden"   name="originalFileName" value="${noticeContent.notice_img}" />
		     <img src="${contextPath}/download.do?notice_num=${noticeContent.notice_num}&notice_img=${noticeContent.notice_img}" id="preview"/><br>   
		   </td>   
		</tr>  
		<tr>
		    <td>
		    	<!-- 수정된 이미지 파일 이름을 request에 저장하여 컨트롤러로 전송! -->
		       <input  type="file"  name="notice_img" id="notice_img"   disabled   onchange="readURL(this);"   />
		    </td>
		</tr>
	 </c:if>
		  <tr>
			   <td width=20% align=center> 등록일자</td>
			   <td>
			    <input type=text value="<fmt:formatDate value="${noticeContent.notice_date}" />" disabled />
			   </td>   
		  </tr>
		  <% if(member_id != null && member_id.equals("admin")) { %>
		  <tr id="notice_modify">
			   <td colspan="2"   align="center" >
			     <input type=button value="저장"   onClick="fn_modify(this.form)"  >
		         <input type=button value="취소"  onClick="backToList(this.form)"  >
			   </td> 
		  </tr>
		  <tr id="notice_btn">
		   <td colspan=2 align=center>
 		    
		   		<input type=button value="수정하기" onClick="fn_enable(this.form)" id="modify_btn">
		    	<input type=button value="삭제하기" onClick="location.href='${contextPath}/notice/delete.do?notice_num=${noticeContent.notice_num}'">
			<% } %>
			    <input type=button value="리스트로 돌아가기" onClick="backToList(this.form)">
		   </td>
		  </tr>
	 </table>
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


