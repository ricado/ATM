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

<title>ATM-申述</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".delete").click(function() {
			var href = $(this).attr("href");
			$("form").attr("action", href).submit();
			return false;
		});
	})
</script>
</head>

<body>
	<!-- 将删除的POST请求转化成DELETE请求 -->
	<form action="" method="post">
		<input type="hidden" name="_method" value="DELETE">
	</form>
	<div>
		<c:if test="${empty requestScope.appeals}">
		没有申述内容
	</c:if>
		<c:if test="${!empty requestScope.appeals}">
			<table id="appealTable" border="1" cellpadding="10" cellspacing="0">
				<!-- appId; number; role; name; photoPath; informEmail; appContent;
			Timestamp appTime; -->
				<tr>
					<th>学号/教职工号</th>
					<th>姓名</th>
					<th>邮箱</th>
					<th>时间</th>
					<th>角色</th>
					<th>查看</th>
					<th>删除</th>
				</tr>
				<%
					int i = 0;
				 %>
				<c:forEach items="${requestScope.appeals}" var="appeal">
					<tr>
						<td>${appeal.number}</td>
						<td>${appeal.name}</td>
						<td>${appeal.informEmail}</td>
						<td>${appeal.appTime}</td>
						<td>${appeal.role}</td>
						<td><a href="appeal/look/<%=i++%>.do">查看</a></td>
						<td><a class="delete" href="appeal/appe/${appeal.appId}.do">删除</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	<a href="appeal/new.do">new a appeal</a>
	<br>
</body>
</html>