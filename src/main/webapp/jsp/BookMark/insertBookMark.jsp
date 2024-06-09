<%@page import="BookMark.BookMark"%>
<%@page import="BookMark.BookMarkService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<script>
	window.onload = function() {
		alert("북마크 정보를 추가하였습니다.");
        window.location.href = "/jsp/BookMark/bookMark.jsp";
	}
</script>
</head>
<body>
	<%
		String x_swifi_mgr_no  = request.getParameter("x_swifi_mgr_no");
		String group_id        = request.getParameter("group_id");
		
		BookMarkService BookMarkService = new BookMarkService();
		
		BookMark bookMark = new BookMark();
		bookMark.setX_swifi_mgr_no(x_swifi_mgr_no);
		bookMark.setGroup_id(group_id);
		
		BookMarkService.insertBookMark(bookMark); 
	%>
</body>
</html>
