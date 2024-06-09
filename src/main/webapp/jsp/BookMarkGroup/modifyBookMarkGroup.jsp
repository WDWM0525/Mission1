<%@page import="java.util.List"%>
<%@page import="BookMarkGroup.BookMarkGroup"%>
<%@page import="BookMarkGroup.BookMarkGroupService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
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
	function updateBookMarkGroup(group_id) {
		var group_nm  = document.getElementById("group_nm").value;
		var order_num = document.getElementById("order_num").value;
		window.location.href = "/jsp/BookMarkGroup/updateBookMarkGroup.jsp?group_id=" + group_id + "&group_nm=" + group_nm + "&order_num=" + order_num;
	}
</script>
</head>
<body>
	<%
		String group_id = request.getParameter("group_id");
		List<BookMarkGroup> bookMarkGroupList = bookMarkGroupService.bookMarkGroupList(group_id); 
	%>
	
	<h1>북마크 그룹 수정</h1> 
	
	<div>
		<a href="/">홈</a> | 
		<a href="/jsp/History/history.jsp">위치 히스토리 목록</a> |
		<a href="/jsp/Wifi/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
		<a href="/jsp/BookMark/bookMark.jsp">북마크 보기</a> |
		<a href="/jsp/BookMarkGroup/bookMarkGroup.jsp">북마크 그룹 관리</a>
	</div>
	
	<div>
		<table id="bookMarkGroup_detail_table">
			<colgroup>
				<col style="width: 25%" />
				<col style="width: 75%" />
			</colgroup>
			<thead>
			</thead>
				<tbody>
					<tr>
						<th>북마크 그룹 이름</th>
						<td><input type="text" id="group_nm" value="<%=bookMarkGroupList.get(0).getGroup_nm()%>"></td>
				    </tr>
				    <tr>
						<th>순서</th>
						<td><input type="number" id="order_num" value="<%=bookMarkGroupList.get(0).getOrder_num()%>"></td>
				    </tr>
				    <tr>
				    	<td colspan="2" class="text_center">
				    		<a href="/jsp/BookMarkGroup/bookMarkGroup.jsp">돌아가기</a> | 
				    		<button onclick="updateBookMarkGroup(<%=bookMarkGroupList.get(0).getGroup_id()%>)">수정</button>
				    	</td>
				    </tr>
				</tbody>
		</table>
	</div>
	
</body>
</html>
