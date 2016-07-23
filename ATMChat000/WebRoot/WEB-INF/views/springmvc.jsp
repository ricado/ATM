<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'springmvc.jsp' starting page</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<a href="appeal/hello.do">hello springMvc</a>
	<br>
	<a href="appeal/appeals.do">get all appeal</a>
	<br>
	<a href="appeal/new.do">new a appeal</a>
	<br>
	<a href="user/userHead.do">look user head</a>
	<br>
	<a href="user/userInfo/10001.do">10001 serInfo</a>
	<br>
	<a href="user/userInfo1/10001.do">10001 serInfo1</a>
</body>
</html>
