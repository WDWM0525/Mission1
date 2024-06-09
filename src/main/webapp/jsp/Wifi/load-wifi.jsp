<%@page import="Wifi.Wifi"%>
<%@page import="OpenApi.OpenApiService"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>
	div {
		text-align: center;
	}
</style>
</head>
<body>
	<%
		OpenApiService openApiService = new OpenApiService();
		HashMap<String, String> resultMap = openApiService.loadWifi(); 
	%>

	<div>
		<h1>
		<%= resultMap.get("content") %>
		</h1>
		<a href="/">홈으로 가기</a> 
	</div>
</body>
</html>
