<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>운송장번호 등록</title>
<%
	int oorder_num = Integer.parseInt(request.getParameter("oorder_num"));
%>

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

function fn_transnum() {
	
	var oorder_transnum = document.getElementById("transnum").value;
	
	if (oorder_transnum != "") {
		location.href='./transnum.do?oorder_num=' + <%=oorder_num%> + '&oorder_transnum=' + oorder_transnum;
		close();
		opener.location.reload();
	}else{
		alert('운송장번호를 입력해주세요.');
		document.getElementById("transnum").focus();
	}
}

</script>

</head>
<body>
<div>
	새로 등록할 운송장 번호를 입력하세요.<br>
	<input type="text" id="transnum" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' />
	<input type="button" value="운송장번호 등록" onclick="fn_transnum();" />
</div>
</body>
</html>