<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Q&A</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
	<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
	<meta name="author" content="FREEHTML5.CO" />


	<!-- Animate.css -->
	<link rel="stylesheet" href="../css/animate.css">
	<!-- Icomoon Icon Fonts -->
	<link rel="stylesheet" href="../css/icomoon.css">
	<!-- Bootstrap  -->
	<link rel="stylesheet" href="../css/bootstrap.css">
	<!-- Owl Carousel -->
	<link rel="stylesheet" href="../css/owl.carousel.min.css">
	<link rel="stylesheet" href="../css/owl.theme.default.min.css">

	<link rel="stylesheet" href="../css/style.css">

	<script  src="http://code.jquery.com/jquery-latest.min.js"></script> 

   <script type="text/javascript" >
   
	 function fn_enable(obj){
		 
		 document.getElementById("review_title").disabled=false;
		 document.getElementById("review_content").disabled=false;
		 document.getElementById("review_img").disabled=false;
	 }
	 

	 
	 function fn_modify(obj){
		 obj.action = "${contextPath}/review/modify.do";
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
	       
	           $('#qna_modify').hide();
	       
	           
	        $('#qna_btn').click(function(){
	           $('#qna_modify').show();
	                    });
	        
	        $('#qna_btn').click(function(){
		           $('#qna_btn').hide();
		                    });
	        
	    });
	    
	 function fn_re(review_num) {
		 var review_re = document.getElementById("admin_re").value;
		 location.href="${contextPath}/review/re.do?review_num=" + review_num + "&review_re=" + review_re;
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
                              <a href="${contextPath}qna/list.do">Q&A</a>
                        </li>
				</ul>
			</nav>
		</div>
	</header>


	<form action="${contextPath}" method="post" enctype="multipart/form-data" id="h1">
	<h1>review<hr></h1>
	  <table align="center" class="table_1">
	  	<tr>
					<td>상품 번호</td>
						<td>${reviewContent.product_num}
					</td>
				</tr>
		  <tr>
			  <td width="150" align="center">글번호</td>
			  <td >
				  <input type="text"  value="${reviewContent.review_num }"  disabled />
				  <!-- 글 수정시 글번호를 컨트롤러로 전송하기 위해 미리 <hidden>태그를 이용해 글번호를 request에 저장함. -->
				  <input type="hidden" name="review_num" value="${reviewContent.review_num}"  />
			  </td>
		  </tr>
		  <tr>
			  <td width="150" align="center">제목</td>
			  <td><input type=text value="${reviewContent.review_title }"  name="review_title"  id="review_title" disabled /></td>   
		  </tr>
		  <tr>
			   <td width="150" align="center">내용</td>
			   <td>
			   		<textarea rows="20" cols="60"  name="review_content"  id="review_content"  disabled><c:out value='${reviewContent.review_content.replaceAll("\\\<.*?\\\>","")}' /></textarea>
			   </td>  
		  </tr>
	 
	<c:if test="${not empty reviewContent.review_img && reviewContent.review_img!='null' }">  
		<tr>
		   <td width="150" align="center" rowspan="2">이미지</td>
		   <td>
		   	 <!-- 이미지 수정에 대비해 미리 원래 이미지 파일이름을 <hidden>태그로 저장 -->
		     <input type= "hidden"   name="originalFileName" value="${reviewContent.review_img}" />
		     <img src="${contextPath}/download_r.do?review_num=${reviewContent.review_num}&review_img=${reviewContent.review_img}" id="preview" width="500px"/><br>   
		   </td>   
		</tr>  
		<tr>
		    <td>
		    	<!-- 수정된 이미지 파일 이름을 request에 저장하여 컨트롤러로 전송! -->
		       <input  type="file"  name="review_img" id="review_img" disabled  onchange="readURL(this);" />
		    </td>
		</tr>
	 </c:if>
		  <tr>
			   <td width=20% align=center> 등록일자</td>
			   <td>
			    <input type=text value="<fmt:formatDate value="${reviewContent.review_date}" />"  disabled />
			   </td>   
		  </tr>
		  <tr>
		  	<td width="150" align="center">댓글</td>
		  	<td>
			  <% if(member_id == null || !member_id.equals("admin")) { %>
			  	<input type="text" size="67" value="${reviewContent.review_re}" maxlength="500" disabled="disabled">
		  	  <% } else { %>
				  <input type="text" size="67" value="${reviewContent.review_re}" maxlength="500" id="admin_re" name="review_re">
				  <input type="button" value="댓글등록" onclick="fn_re(${reviewContent.review_num})" />
		 	  <% } %>
		  	</td>
		  </tr>
		  <tr id="review_modify">
			   <td colspan="2" align="center">
			   <c:if test="${reviewContent.member_num eq member_num || member_num eq 1}">
			     <input type=button value="저장" onClick="fn_modify(this.form)"  >
		         </c:if>
			   </td> 
		  </tr>
		  <tr id="review_btn">
		   <td colspan=2 align=center>
 		    <% if(member_id != null) { %>
 		    	<c:if test="${reviewContent.member_num eq member_num || member_num eq 1}">
		   			<input type=button value="수정하기" onClick="fn_enable(this.form)" id="modify_btn">
		   		</c:if>
		   	<% if(member_id != null && member_id.equals("admin")) {%>	
		    	<input type=button value="삭제하기" onClick="location.href='${contextPath}/review/delete.do?review_num=${reviewContent.review_num}'">
			<% } }%>
			    <input type=button value="이전페이지" onClick='history.go(-1)'>
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


