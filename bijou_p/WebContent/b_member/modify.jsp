<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
  request.setCharacterEncoding("UTF-8");

	String member_id = (String)session.getAttribute("member_id");
	
%>
  
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%	if(member_id == null) { %>
		<script>
			location.href="${contextPath}/member/login_go.do";
		</script>
<%	} %>  

	<script>
		var a = 0;
		 /* 비밀번호 유효성 검사 메서드*/
	   	 function checkPwd(){
			var pwd1 = document.getElementById("member_pw_n").value;
	    	var checkSpan = document.getElementById("member_pwcheck");
	    	var reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
	    	
	     	if(!reg.test(pwd1)){
	     		checkSpan.innerHTML = "<font color='red' font size='2px'><b> X 비밀번호는 숫자, 알파벳, 특수문자 조합으로 8자리 이상 입력해야 합니다.</b></font>";
	     	}else{
	     		checkSpan.innerHTML = "<font color='green'><b> √ </b></font>";
	     		result_pwd = true;
	     		a = 1;
	     	}
	    }
 	
		 /* 전화번호 유효성 검사 */
		 function checkPhone(){
			 var phone1 = document.getElementById("member_phone").value;
			 var checkSpan = document.getElementById("member_phonecheck");
			 var reg = /^[0-9]*$/;
				 
			if(!reg.test(phone1)){
				checkSpan.innerHTML = "<font color='red' font size='2px'><b> X 숫자만 입력해야 합니다. </b></font>";
	     	}else{
	     		checkSpan.innerHTML = "<font color='green' ><b> √ </b></font>";
	     		result_phone = true;
	     	}
	    }
		 
		 
		 function check() {
				var form = document.fr; //폼이름
				var reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
			 
				if(form.member_id.value == "") {
					alert("아이디는 필수입력입니다.");
					form.member_id.focus();
					return ;
				} else if(form.member_pw.value == "") {
					alert("기존 비밀번호는 필수입력입니다.");
					form.member_pw.focus();
					return ;
		 		} else if(form.member_pw_n.value == "") {
					alert("새 비밀번호는 필수입력입니다.");
					form.member_pw_n.focus();
					return ;
				} else if (form.member_pw_n.value.length < 8 || form.member_pw_n.value.length > 20) {
					alert("새 비밀번호는 형식에 맞게 입력하세요.");
					form.member_pw_n.value="";
					form.member_pw_n.focus();
					return ;
				} else if (a != 1) {
					alert("비밀번호는 형식에 맞게 입력하세요.");
					form.member_pw_n.value="";
					form.member_pw_n.focus();
					return ;
				} else if(form.member_pw_n2.value == "") {
					alert("새 비밀번호 확인은 필수입력입니다.");
					form.member_pw_n2.focus();
					return ;
				} else if (form.member_pw_n.value != form.member_pw_n2.value) {
					alert("새 비밀번호가 다릅니다.");
					form.member_pw_n2.value="";
					form.member_pw_n2.focus();
					return ;
				} else if(form.member_name.value == "") {
					alert("이름은 필수입력입니다.");
					form.member_name.focus();
					return ;
				} else if(form.member_name.value.length < 2 || form.member_name.value.length > 7) {
					alert("이름은 2자~7자 이내로 작성하세요.");
					form.member_name.value="";
					form.member_name.focus();
					return ;
				} else if(form.member_phone.value == "") {
					alert("전화번호는 필수입력입니다.");
					form.member_phone.focus();
					return ;
				} else if(form.member_phone.value.length != 11) {
					alert("전화번호는 11자로 입력하세요.");
					form.member_phone.value="";
					form.member_phone.focus();
					return ;
				} else if(form.member_post.value == "") {
					alert("우편번호는 필수입력입니다.");
					form.member_postBtn.focus();
					return ;
				} else if(form.member_address1.value == "") {
					alert("주소는 필수입력입니다.");
					form.member_address.focus();
					return ;
				} else if(form.member_address2.value == "") {
					alert("주소는 필수입력입니다.");
					form.member_address2.focus();
					return ;
				} else if(form.member_address3.value == "") {
					alert("주소는 필수입력입니다.");
					form.member_address3.focus();
					return ;
		 		} else {
						//빈값이 없을 경우에 다음 페이지 이동을 합니다.
						form.action = "${contextPath}/member/modify.do"; //다음페이지 주소
						form.submit(); // 폼 실행
					}
				}
		 
	</script>


	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
	    function sample6_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var addr = ''; // 주소 변수
	                var extraAddr = ''; // 참고항목 변수
	
	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }
	
	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                    if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                    // 조합된 참고항목을 해당 필드에 넣는다.
	                    document.getElementById("sample6_extraAddress").value = extraAddr;
	                
	                } else {
	                    document.getElementById("sample6_extraAddress").value = '';
	                }
	
	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('sample6_postcode').value = data.zonecode;
	                document.getElementById("sample6_address").value = addr;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById("sample6_detailAddress").focus();
	            }
	        }).open();
	    }
	</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Modify</title>
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
	<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
	<meta name="author" content="FREEHTML5.CO" />

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Jua&display=swap" rel="stylesheet">
	
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
	

	<form action="${contextPath}/member/delete.do" method="post" id="h1" name="fr">
	 <h1>회원정보 수정</h1>
	 <table class="login" style="width: 500px;">
	 	<tr>
	 		<td width="200"><p align="left">아이디 &nbsp;</p></td>
	 		<td width="400"><input type="text" name="member_id" readonly value="${member_id}" class="input_box"></td>
	 	</tr>
	 	<tr>
	 		<td width="200"><p align="left">기존 비밀번호 &nbsp;</p></td>
	 		<td width="400">
	 		<input type="password" name="member_pw" id="member_pw" maxlength="20">
	 	</tr>
	 	<tr>
	 		<td width="200"><p align="left">새 비밀번호 &nbsp;</p></td>
	 		<td width="400">
	 		<input type="password" name="member_pw_n" id="member_pw_n" onblur="checkPwd();" maxlength="20">
	 		<br><span id="member_pwcheck"></span></td>
	 	</tr>
	 	<tr>
	 		<td width="200"><p align="left">새 비밀번호 확인&nbsp;</p></td>
	 		<td width="400">
	 		<input type="password" name="member_pw_n2" id="member_pw_n2" onblur="checkPwd();" maxlength="20">
	 		<br><span id="member_pwcheck2"></span></td>
	 	</tr>
	 	<tr>
	 		<td width="200"><p align="left">이름 &nbsp;</p></td>
	 		<td width="400"><input type="text" name="member_name" value="${member_name}" maxlength="7" class="input_box"></td>
	 	</tr>
	 	<tr>
	 		<td width="200"><p align="left">전화번호 &nbsp;</p></td>
	 		<td width="400"><input type="text" name="member_phone" id="member_phone" onblur="checkPhone();" value="${member_phone}" maxlength="11" class="input_box">
	 		<span id="member_phonecheck"></span></td>
	 	</tr>
	 	<!-- 우편번호 -->
	 	<tr>
	 		<td width="200"><p align="left">주소 &nbsp;</p></td><td width="400">
	 		<input type="text" id="sample6_postcode" name="member_post" value="${member_post}" class="input_box">
	 		<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" name="member_postBtn"><br>
			<input type="text" id="sample6_address" name="member_address1" value="${member_address1}" class="input_box">
			<input type="text" id="sample6_detailAddress" placeholder="상세주소" name="member_address2" maxlength="30" value="${member_address2}" class="input_box">
			<input type="text" id="sample6_extraAddress" placeholder="참고항목" name="member_address3" value="${member_address3}" class="input_box">
			</td>
		</tr>
		<!-- 우편번호 -->
		<tr>
			<td width="200"><p align="left">등급 &nbsp;</p></td>
			<td width="400"><input type="text" name="member_rating" readonly value="${member_rating}" class="input_box"></td>
		</tr>
		<tr>
			<td width="200"><p align="left">포인트 &nbsp;</p></td>
			<td width="400"><input type="text" name="member_point" readonly value="${member_point}" class="input_box"> 점</td>
		</tr>
	 	<tr>
	 		<td width="200"><p>&nbsp;</p></td>
	 		<td width="400">
	 			<input type="button" value="수정하기" onclick="check();" style="width:60pt; height: 25pt;">
	 			<input type="reset" value="다시작성" style="width:60pt; height: 25pt;">
	 			<input type="submit" value="회원탈퇴" style="width:60pt; height: 25pt;">
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


