<%@page import="java.util.List"%>
<%@page import="BookMarkGroup.BookMarkGroup"%>
<%@page import="BookMarkGroup.BookMarkGroupService"%>
<%@page import="Wifi.Wifi"%>
<%@page import="Wifi.WifiService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	function addBookMark(x_swifi_mgr_no) {
		var group_id = document.getElementById("bookmark_group").value;
		
		if (group_id != "") {
			window.location.href = "/jsp/BookMark/insertBookMark.jsp?x_swifi_mgr_no=" + x_swifi_mgr_no + "&group_id=" + group_id;
		} else {
			alert("북마크 그룹을 선택하세요.");
		}
	}	
</script>
</head>
<body>
	<%
		BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
		List<BookMarkGroup> bookMarkGroupList = bookMarkGroupService.bookMarkGroupList();
	
		String x_swifi_mgr_no = request.getParameter("x_swifi_mgr_no");
		
		WifiService wifiService = new WifiService();
		List<Wifi> wifiList = wifiService.wifiList(x_swifi_mgr_no);
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
		<select id="bookmark_group">
		    <option value="">북마크 그룹 이름 선택</option>
	<%	for (BookMarkGroup group : bookMarkGroupList) { %>
		    <option value="<%=group.getGroup_id()%>"><%=group.getGroup_nm()%></option>
	<%	} %>
		  </select>
		<button onclick="addBookMark('<%=wifiList.get(0).getX_swifi_mgr_no()%>')">북마크 추가하기</button>
	</div>
	
	<div>
		<table id="wifi_detail_table">
			<colgroup>
				<col style="width: 25%" />
				<col style="width: 75%" />
			</colgroup>
			<thead>
			</thead>
				<tbody>
					<tr>
						<th>거리(Km)</td>
						<td><%=wifiList.get(0).getDistance()%></td>
				    </tr>
				    <tr>
						<th>관리번호</td>
						<td><%=wifiList.get(0).getX_swifi_mgr_no()%></td>
				    </tr>
				    <tr>
						<th>자치구</td>
						<td><%=wifiList.get(0).getX_swifi_wrdofc()%></td>
				    </tr>
				    <tr>
						<th>와이파이명</td>
						<td><%=wifiList.get(0).getX_swifi_mgr_no()%></td>
				    </tr>
				    <tr>
						<th>도로명주소</td>
						<td><%=wifiList.get(0).getX_swifi_adres1()%></td>
				    </tr>
				    <tr>
						<th>상세주소</td>
						<td><%=wifiList.get(0).getX_swifi_adres2()%></td>
				    </tr>
				    <tr>
						<th>설치위치(층)</td>
						<td><%=wifiList.get(0).getX_swifi_instl_floor()%></td>
				    </tr>
				    <tr>
						<th>설치유형</td>
						<td><%=wifiList.get(0).getX_swifi_instl_ty()%></td>
				    </tr>
				    <tr>
						<th>설치기관</td>
						<td><%=wifiList.get(0).getX_swifi_instl_mby()%></td>
				    </tr>
				    <tr>
						<th>서비스구분</td>
						<td><%=wifiList.get(0).getX_swifi_svc_se()%></td>
				    </tr>
				    <tr>
						<th>망종류</td>
						<td><%=wifiList.get(0).getX_swifi_cmcwr()%></td>
				    </tr>
				    <tr>
						<th>설치년도</td>
						<td><%=wifiList.get(0).getX_swifi_cnstc_year()%></td>
				    </tr>
				    <tr>
						<th>실내외구분</td>
						<td><%=wifiList.get(0).getX_swifi_inout_door()%></td>
				    </tr>
				    <tr>
						<th>WIFI접속환경</td>
						<td><%=wifiList.get(0).getX_swifi_remars3()%></td>
				    </tr>
				    <tr>
						<th>X좌표</td>
						<td><%=wifiList.get(0).getLat()%></td>
				    </tr>
				    <tr>
						<th>Y좌표</td>
						<td><%=wifiList.get(0).getLnt()%></td>
				    </tr>
				    <tr>
						<th>작업일자</td>
						<td><%=wifiList.get(0).getWork_dttm()%></td>
				    </tr>
				</tbody>
		</table>
	</div>
	
</body>
</html>
