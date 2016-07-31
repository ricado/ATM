<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'newsList.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
table,td,th{
	border:1px solid black; 
}
</style>
</head>

<body>
	<!-- 显示头条新闻 -->
	头条新闻:
	<br>
	<table>
		<tr>
			<th>newsId</th>
			<th>mainTitle</th>
			<th>小编</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${requestScope.headNews}" var="schoolActive">
			<tr>
				<td>${schoolActive.newsId}</td>
				<td>${schoolActive.mainTitle}</td>
				<td>${schoolActive.writer}</td>
				<td><a
					href="schoolActive/setIsHeadNews/${schoolActive.newsId}.do">set</a></td>
<td><a
					href="schoolActive/changeSchoolActive/${schoolActive.newsId}.do">修改</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<br> 列表:
	<br>
	<table>
		<tr>
			<th>newsId</th>
			<th>mainTitle</th>
			<th>小编</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${requestScope.news}" var="schoolActive">
			<tr>
				<td>${schoolActive.newsId}</td>
				<td>${schoolActive.mainTitle}</td>
				<td>${schoolActive.writer}</td>
				<td><a
					href="schoolActive/setIsHeadNews/${schoolActive.newsId}.do">set</a></td>
				<td><a
					href="schoolActive/changeSchoolActive/${schoolActive.newsId}.do">修改</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>