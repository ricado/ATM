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

<title>My JSP 'recommendSm.jsp' starting page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<form action="RecommendSm/recommend.do">
		<table>
			<tr>
				<th>姓名</th>
				<td><input type="text" name="name" id="name"></td>
			</tr>
			<tr>
				<th>入学时间</th>
				<td><input type="text" name="time" id="time"></td>
			</tr>
			<tr>
				<th colspan="3">推荐理由</th>
				<td colspan="3"><input type="text" name="reason" id="reason"></td>
			</tr>
			<tr>
				<th rowspan="2"><input type="submit" value="确定"></th>
			</tr>
		</table>
	</form>
</body>
</html>
