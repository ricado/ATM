<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'appeal.jsp' starting page</title>

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
	<div>
		<form action="appeal.do" enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<td>学号:</td>
					<td><input type="text" name="number" value="131544215"></td>
				</tr>
				<tr>
					<td>真实姓名:</td>
					<td><input type="text" name="name" value="ccy"></td>
				</tr>
				<tr>
					<td>通知邮箱:</td>
					<td><input type="text" name="informEmail"
						value="1169833934@qq.com"></td>
				</tr>
				<tr>
					<td>角色</td>
					<td><input type="radio" name="role" value="学生"> <input
						type="radio" name="role" value="教师"></td>
				</tr>
			</table>
			手持身份证照片:<br> <input type="file" name="file" id="file"><br>
			<img src="${file.value}"> <input type="submit" value="提交" />
		</form>
	</div>
</body>
</html>
