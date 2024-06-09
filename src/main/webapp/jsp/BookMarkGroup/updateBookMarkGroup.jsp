<%@page import="BookMarkGroup.BookMarkGroup"%>
<%@page import="BookMarkGroup.BookMarkGroupService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<script>
	window.onload = function() {
		alert("수정 되었습니다.");
        window.location.href = "/jsp/BookMarkGroup/bookMarkGroup.jsp";
	}
</script>
</head>
<body>
	<%
		String group_id  = request.getParameter("group_id");
		String group_nm  = request.getParameter("group_nm");
		String order_num = request.getParameter("order_num");
		
		BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
		BookMarkGroup group = new BookMarkGroup();
		group.setGroup_id(group_id);
		group.setGroup_nm(group_nm);
		group.setOrder_num(order_num);
		
		bookMarkGroupService.updateBookMarkGroup(group); 
	%>
</body>
</html>