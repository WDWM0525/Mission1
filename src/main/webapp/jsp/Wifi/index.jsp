<%@page import="java.util.List"%>
<%@page import="Wifi.Wifi"%>
<%@page import="Wifi.WifiService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</script>
</head>
<body>
	<%
		String lat = request.getParameter("lat");
		String lnt = request.getParameter("lnt");
		
		WifiService wifiService = new WifiService();
		List<Wifi> wifiList = wifiService.wifiList(lat, lnt); 
	%>

	<h1>와이파이 정보 구하기</h1> 
	
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
		<table id="wifi_info_table">
			<thead>
				<tr> 
					<th>거리(Km)</th>
					<th>관리번호</th>
					<th>자치구</th>
					<th>와이파이명</th>
					<th>도로명주소</th>
					<th>상세주소</th>
					<th>설치위치(층)</th>
					<th>설치유형</th>
					<th>설치기관</th>
					<th>서비스구분</th>
					<th>망종류</th>
					<th>설치년도</th>
					<th>실내외구분</th>
					<th>WIFI접속환경</th>
					<th>X좌표</th>
					<th>Y좌표</th>
					<th>작업일자</th>
			    </tr>
			  </thead>
			  <tbody>
		<% 
			if (wifiList.size() < 1) {
		%>
				<tr>
			    	<td colspan="17" class ="text_center">위치 정보를 입력한 후에 조회해 주세요.</td>
			    </tr>
		<% 
			} else {
				for (Wifi wifi : wifiList) {
		%>
				<tr>
					<td><%=wifi.getDistance()%></td>
					<td><%=wifi.getX_swifi_mgr_no()%></td>
					<td><%=wifi.getX_swifi_wrdofc()%></td>
					<td><a href="/jsp/Wifi/detail.jsp?x_swifi_mgr_no=<%=wifi.getX_swifi_mgr_no()%>"><%=wifi.getX_swifi_main_nm()%></a></td>
					<td><%=wifi.getX_swifi_adres1()%></td>
					<td><%=wifi.getX_swifi_adres2()%></td>
					<td><%=wifi.getX_swifi_instl_floor()%></td>
					<td><%=wifi.getX_swifi_instl_ty()%></td>
					<td><%=wifi.getX_swifi_instl_mby()%></td>
					<td><%=wifi.getX_swifi_svc_se()%></td>
					<td><%=wifi.getX_swifi_cmcwr()%></td>
					<td><%=wifi.getX_swifi_cnstc_year()%></td>
					<td><%=wifi.getX_swifi_inout_door()%></td>
					<td><%=wifi.getX_swifi_remars3()%></td>
					<td><%=wifi.getLat()%></td>
					<td><%=wifi.getLnt()%></td>
					<td><%=wifi.getWork_dttm()%></td>
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
