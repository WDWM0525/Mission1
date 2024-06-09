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
		alert("북마크 정보를 삭제하였습니다.");
        window.location.href = "/jsp/BookMark/bookMark.jsp";
	}
</script>
</head>
<body>
	<%
		String book_mark_id  = request.getParameter("book_mark_id");
		
		BookMarkService BookMarkService = new BookMarkService();
		
		BookMark bookMark = new BookMark();
		bookMark.setBook_mark_id(book_mark_id);
		
		BookMarkService.deleteBookMark(bookMark); 
	%>
</body>
</html>
