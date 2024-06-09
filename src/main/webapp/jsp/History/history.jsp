<%@page import="java.util.List"%>
<%@page import="History.History"%>
<%@page import="History.HistoryService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	HistoryService historyService = new HistoryService();
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
	window.onload = function() {
		var urlParams = new URLSearchParams(window.location.search);
		var lat = urlParams.has("lat") ? urlParams.get('lat') : "0.0";
		var lnt = urlParams.has("lnt") ? urlParams.get('lnt') : "0.0";
		document.getElementById("lat").setAttribute("value", lat);
        document.getElementById("lnt").setAttribute("value", lnt);
	}

	function getMyPosition() {
		window.navigator.geolocation.getCurrentPosition (
			function (position) {
				var lat = position.coords.latitude;
                var lnt = position.coords.longitude;
                console.log("lat : " + lat + ", lnt : " + lnt);
                document.getElementById("lat").setAttribute("value", lat);
                document.getElementById("lnt").setAttribute("value", lnt);
			
			}, 
			function (error) {
				switch (error.code) {
					case error.PERMISSION_DENIED:
	                    str="사용자 거부";
	                    break;
	                
					case error.POSITION_UNAVAILABLE:
	                    str="지리정보 없음";
	                    break;
	                
					case error.TIMEOUT:
	                    str="시간 초과";
	                    break;
	                
					case error.UNKNOWN_ERROR:
	                    str="알수없는 에러";
	                    break;
				}
			}
		);
	}
	
	function showWifi() {
		var lat = document.getElementById("lat").value;
		var lnt = document.getElementById("lnt").value;
		window.location.href = "/jsp/Wifi/index.jsp?lat=" + lat + "&lnt=" + lnt;
	}
	
	function deleteHistory(id) {
		window.location.href = "/jsp/History/deleteHistory.jsp?id=" + id;
	}
</script>
</head>
<body>
	<%
		String lat = request.getParameter("lat");
		String lnt = request.getParameter("lnt");
		
		List<History> siteHistoryList = historyService.siteHistoryList(); 
	%>
	
	<h1>위치 히스토리 목록</h1> 
	
	<div>
		<a href="/">홈</a> | 
		<a href="/jsp/History/history.jsp">위치 히스토리 목록</a> |
		<a href="/jsp/Wifi/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
		<a href="/jsp/BookMark/bookMark.jsp">북마크 보기</a> |
		<a href="/jsp/BookMarkGroup/bookMarkGroup.jsp">북마크 그룹 관리</a>
	</div>
	
	<div>
		<label for="lat">LAT:
		<input type="text" id = "lat">
		<label for="lnt">, LNT:
		<input type="text" id = "lnt">
		<button onclick="getMyPosition()">내 위치 가져오기</button>
		<button onclick="showWifi()">근처 WIFI 정보 보기</button>
	</div>
	
	<div>
		<table id="site_history_table">
			<thead>
				<tr> 
					<th>ID</th>
					<th>X좌표</th>
					<th>Y좌표</th>
					<th>조회일자</th>
					<th>비고</th>
			    </tr>
			  </thead>
			  <tbody>
		<% 
			if (siteHistoryList.size() < 1) {
		%>
				<tr>
			    	<td colspan="5" class="text_center">히스토리가 없습니다.</td>
			    </tr>
		<% 
			} else {
				for (History history : siteHistoryList) {
		%>
				<tr>
					<td><%=history.getId()%></td>
					<td><%=history.getLat()%></td>
					<td><%=history.getLnt()%></td> 
					<td><%=history.getSearch_dt()%></td>
					<td class="text_center"><button onclick="deleteHistory(<%=history.getId()%>)">삭제</button></td>
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
