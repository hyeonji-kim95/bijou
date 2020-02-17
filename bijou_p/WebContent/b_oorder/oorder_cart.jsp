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
<title>Order</title>
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


	function fn_point() {
		var member_point = document.getElementById("member_point").value;
		var use_point = document.getElementById("use_point").value;

		if(member_point < use_point) {
			alert('포인트가 부족합니다.');
			return;
		} else {
			var member_point_n = member_point - use_point;
			document.getElementById("member_point_n").setAttribute("value", member_point_n);
			var oorder_price = document.getElementById("oorder_price").value - use_point;
			document.getElementById("oorder_price").removeAttribute("value");
			document.getElementById("oorder_price").setAttribute("value", oorder_price);
			
		}
	}
	
function fn_check() { 
	var chk = false;  
	
	if(document.fr.check.checked){
		chk = true;
		} else {
			chk = false; 
	}
		if(chk == true){
			
			document.getElementById('oorder_name').value = document.getElementById('oorder_name_1').value;
			document.getElementById('oorder_phone').value = document.getElementById('oorder_phone_1').value;
			document.getElementById('sample6_postcode').value = document.getElementById('oorder_post_1').value;
			document.getElementById('sample6_address').value = document.getElementById('oorder_address1_1').value;
			document.getElementById('sample6_detailAddress').value = document.getElementById('oorder_address2_1').value;
			document.getElementById('sample6_extraAddress').value = document.getElementById('oorder_address3_1').value;
			
		}else{
			document.getElementById('oorder_name').value = "";
			document.getElementById('oorder_phone').value = "";
			document.getElementById('sample6_postcode').value = "";
			document.getElementById('sample6_address').value = "";
			document.getElementById('sample6_detailAddress').value = "";
			document.getElementById('sample6_extraAddress').value = "";
			
		}
		return false; 
	}

function fn_payment(){
	  var form = document.fr; // 폼이름
		
		if(form.oorder_name.value == "") {
			alert("이름은 필수입력입니다.");
			form.oorder_name.focus();
			return ;
		} else if(form.oorder_phone.value == "") {
			alert("휴대폰 번호는 필수입력입니다.");
			form.oorder_phone.focus();
			return ;
		} else if(form.oorder_phone.value.length != 11) {
			alert("전화번호는 11자로 입력하세요.");
			form.oorder_phone.value="";
			form.oorder_phone.focus();
			return ;
		} else if(form.oorder_post.value == "") {
			alert("우편번호는 필수입력입니다.");
			form.oorder_post.focus();
			return ;
		} else if(form.oorder_address1.value == "") {
			alert("주소는 필수입력입니다.");
			form.oorder_address1.focus();
			return ;
		} else if(form.oorder_address2.value == "") {
			alert("주소는 필수입력입니다.");
			form.oorder_address2.focus();
			return ;
		} else if(form.oorder_address3.value == "") {
			alert("주소는 필수입력입니다.");
			form.oorder_address3.focus();
			return ;
		} else if(form.oorder_pay.value == "") {
			alert("결제 방식을 선택해주세요.");
			form.oorder_pay.focus();
			return ;
		} else {
				//빈값이 없을 경우에 다음 페이지 이동을 합니다.
				form.action = "${contextPath}/oorder/cart.do"; //다음페이지 주소
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

<h1>주문하기</h1>
<form action="${contextPath}/oorder/cart.do" method="post" name="fr">
	<table class="table_1">
	<tr>
	<th>주문하는 상품 개수 : ${count} 개</th>
	<th>총 금액 : ${total} 개</th>
	</tr>
	<tr>
	<th>주문자 정보</th>
		<td><input type="text" value="${mem_vo.member_name}" readonly id="oorder_name_1"/>
			<input type="text" value="${mem_vo.member_phone}" readonly id="oorder_phone_1"/>
			<input type="text" value="${mem_vo.member_post}" readonly id="oorder_post_1"/>
			<input type="text" value="${mem_vo.member_address1}" readonly id="oorder_address1_1"/>
			<input type="text" value="${mem_vo.member_address2}" readonly id="oorder_address2_1"/>
			<input type="text" value="${mem_vo.member_address3}" readonly id="oorder_address3_1"/>
		</td>
	</tr>
	<tr>
		<th>배송지 정보</th>
		<td><input type="checkbox" onchange="fn_check();" name="check" /> 주문자 정보 동일<br>
			<input type="text" value="" name="oorder_name" id="oorder_name" />
			<input type="text" value="" name="oorder_phone" id="oorder_phone" />
			<input type="text" value="" name="oorder_post" id="sample6_postcode" />
			<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" name="member_postBtn"><br>
			<input type="text" value="" name="oorder_address1" id="sample6_address" />
			<input type="text" value="" name="oorder_address2" id="sample6_detailAddress" placeholder="상세주소" />
			<input type="text" value="" name="oorder_address3" id="sample6_extraAddress" placeholder="참고항목" />
			<input type="hidden" value="${mem_vo.member_num}" name="member_num" />
			<input type="hidden" value="${oorder_count}" name="oorder_count" />
			
		</td>
	</tr>
	<tr>
		<th>포인트</th>
		<td>사용가능 포인트 : <input type="text" value="${mem_vo.member_point}" id="member_point" name="member_point" readonly/> point</td>
		<td>사용할 포인트 : <input type="text" placeholder="숫자만 입력" value="0" id="use_point" name="use_point" onblur="fn_point();" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)'/>point</td>
		<td>총 결제 금액 : <input type="text" value="${total}" name="oorder_price" id="oorder_price" readonly /></td>
		<input type="hidden" name="member_point_n" id="member_point_n" />
	</tr>
	<tr>
		<th>결제 방식</th>
		<td>계좌번호 : 000-00000-000-0 <br> 00은행</td>
		<td><input type="radio" value="cash" name="oorder_pay" />무통장 입금</td>
		<td><input type="radio" value="card" name="oorder_pay" />신용/체크 카드</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="결제하기" onclick="fn_payment();">
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
