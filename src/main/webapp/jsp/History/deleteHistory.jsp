<%@page import="History.HistoryService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<script>
	window.onload = function() {
		alert("삭제 되었습니다.");
        window.location.href = "/jsp/History/history.jsp";
	}
</script>
</head>
<body>
	<%
		String id = request.getParameter("id");
		HistoryService historyService = new HistoryService();
		historyService.deleteSiteHistory(id); 
	%>
</body>
</html>
