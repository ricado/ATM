<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'showAppeal.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
img {
	width: 100%;
}

table {
	width: 100%;
}
</style>
</head>

<body>
	<!-- appId; number; role; name; photoPath; informEmail; appContent;
			Timestamp appTime; -->
	<table border="1" cellpadding="10" cellspacing="0">

		<tr>
			<th>姓名:</th>
			<td>${requestScope.appeal.name}</td>
		</tr>
		<tr>
			<th>${requestScope.appeal.role == '教师' ? '教职工号' : '学号'}</th>
			<td>${requestScope.appeal.number}</td>
		</tr>
		<tr>
			<th>邮箱:</th>
			<td>${requestScope.appeal.informEmail}</td>
		</tr>
		<tr>
			<th>身份:</th>
			<td>${requestScope.appeal.role}</td>
		</tr>
		<tr>
			<th>时间:</th>
			<td>${requestScope.appeal.appTime}</td>
		</tr>
		<tr>
			<th>照片:</th>
			<td><img src="${requestScope.appeal.photoPath}"></td>
		</tr>
	</table>
	<table border="1" cellpadding="10" cellspacing="0">
		<tr>
			<td><c:if test="${requestScope.index > 0}">
					<a href="appeal/look/${requestScope.index - 1}.do">上一个</a>
				</c:if></td>
			<th><c:if test="${requestScope.index < requestScope.max}">
					<a href="appeal/look/${requestScope.index + 1}.do">下一个</a>
				</c:if></th>
		</tr>
	</table>
	<a href="springmvc.jsp">返回到springmvc</a><br>
	<a href="appeal/appeals.do">return appeals</a>
</body>
</html>