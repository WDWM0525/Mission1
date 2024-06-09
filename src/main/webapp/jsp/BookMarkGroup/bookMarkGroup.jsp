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
	function addBookMarkGroup() {
		window.location.href = "/jsp/BookMarkGroup/addBookMarkGroup.jsp";
	}

	function modifyBookMarkGroup(group_id) {
		window.location.href = "/jsp/BookMarkGroup/modifyBookMarkGroup.jsp?group_id=" + group_id;
	}
	
	function deleteBookMarkGroup(group_id) {
		window.location.href = "/jsp/BookMarkGroup/deleteBookMarkGroup.jsp?group_id=" + group_id;
	}
</script>
</head>
<body>
	<%
		List<BookMarkGroup> bookMarkGroupList = bookMarkGroupService.bookMarkGroupList(); 
	%>
	
	<h1>북마크 그룹 관리</h1> 
	
	<div>
		<a href="/">홈</a> | 
		<a href="/jsp/History/history.jsp">위치 히스토리 목록</a> |
		<a href="/jsp/Wifi/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
		<a href="/jsp/BookMark/bookMark.jsp">북마크 보기</a> |
		<a href="/jsp/BookMarkGroup/bookMarkGroup.jsp">북마크 그룹 관리</a>
	</div>
	
	<div>
		<button onclick="addBookMarkGroup()">북마크 그룹 이름 추가</button>
	</div>
	
	<div>
		<table id="bookMarkGroup_table">
			<colgroup>
				<col style="width: 5%" />
				<col style="width: 25%" />
				<col style="width: 5%" />
				<col style="width: 25%" />
				<col style="width: 25%" />
				<col style="width: 15%" />
			</colgroup>
			<thead>
				<tr> 
					<th>ID</th>
					<th>북마크 이름</th>
					<th>순서</th>
					<th>등록일자</th>
					<th>수정일자</th>
					<th>비고</th>
			    </tr>
			  </thead>
			  <tbody>
		<% 
			if (bookMarkGroupList.size() < 1) {
		%>
				<tr>
			    	<td colspan="6" class="text_center">등록된 북마크 그룹이 없습니다.</td>
			    </tr>
		<% 
			} else {
				for (BookMarkGroup group : bookMarkGroupList) {
		%>
				<tr>
					<td><%=group.getGroup_id()%></td>
					<td><%=group.getGroup_nm()%></td>
					<td><%=group.getOrder_num()%></td> 
					<td><%=group.getRegist_dt()%></td>
					<td><%=group.getUpdate_dt()%></td>
					<td class="text_center">
						<button onclick="modifyBookMarkGroup(<%=group.getGroup_id()%>)">수정</button>
						<button onclick="deleteBookMarkGroup(<%=group.getGroup_id()%>)">삭제</button>
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
