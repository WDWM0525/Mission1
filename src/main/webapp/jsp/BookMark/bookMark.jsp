<%@page import="java.util.List"%>
<%@page import="BookMark.BookMark"%>
<%@page import="BookMark.BookMarkService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	BookMarkService bookMarkService = new BookMarkService();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>
	
    div {
        margin: 15px;
    }
    
	table {
		width: 100%;
		border-collapse: collapse;
  		border: 1px solid #dedede;
	}
	
	th {
    	text-align: center;
        color: #ffffff;
        background-color: #00ab6f;
        border: 1px solid #dedede;
        height: 50px;
	}
	
	tr {
		height: 50px;
	}
	
	tr:nth-child(even) {
		background-color: #f2f2f2;
	}
		
	td {
		text-align: left;
		border: 1px solid #dedede;
		padding: 5px;
	}
	
	.text_center {
		text-align: center;
	}
	
</style>
<script>
	function deleteBookMark(book_mark_id) {
		window.location.href = "/jsp/BookMark/deleteBookMark.jsp?book_mark_id=" + book_mark_id;
	}
</script>
</head>
<body>
	<%
		List<BookMark> bookMarkList = bookMarkService.bookMarkList(); 
	%>
	
	<h1>북마크 보기</h1> 
	
	<div>
		<a href="/">홈</a> | 
		<a href="/jsp/History/history.jsp">위치 히스토리 목록</a> |
		<a href="/jsp/Wifi/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
		<a href="/jsp/BookMark/bookMark.jsp">북마크 보기</a> |
		<a href="/jsp/BookMarkGroup/bookMarkGroup.jsp">북마크 그룹 관리</a>
	</div>
	
	<div>
		<table id="bookMark_table">
			<colgroup>
				<col style="width: 5%" />
				<col style="width: 30%" />
				<col style="width: 30%" />
				<col style="width: 25%" />
				<col style="width: 10%" />
			</colgroup>
			<thead>
				<tr> 
					<th>ID</th>
					<th>북마크 이름</th>
					<th>와이파이명</th>
					<th>등록일자</th>
					<th>비고</th>
			    </tr>
			  </thead>
			  <tbody>
		<% 
			if (bookMarkList.size() < 1) {
		%>
				<tr>
			    	<td colspan="6" class="text_center">등록된 북마크가 없습니다.</td>
			    </tr>
		<% 
			} else {
				for (BookMark bookMark : bookMarkList) {
		%>
				<tr>
					<td><%=bookMark.getBook_mark_id()%></td>
					<td><%=bookMark.getGroup_nm()%></td>
					<td><a href="/jsp/Wifi/detail.jsp?x_swifi_mgr_no=<%=bookMark.getX_swifi_mgr_no()%>"><%=bookMark.getX_swifi_main_nm()%></a></td> 
					<td><%=bookMark.getRegist_dt()%></td>
					<td class="text_center">
						<button onclick="deleteBookMark(<%=bookMark.getBook_mark_id()%>)">삭제</button>
					</td>
			    </tr>
		<%
				}
			}
		%>
			  </tbody>
		</table>
	</div>
	
</body>
</html>
