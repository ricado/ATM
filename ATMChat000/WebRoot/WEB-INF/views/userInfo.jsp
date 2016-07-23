<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'userInfo.jsp' starting page</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	img{
		width: 150px;
	}
	table{
		width: 100%;
	}
	input {
		width:100%;
	}
</style>
</head>

<body>
	<form action="user/changeUserInfoView.do" enctype="multipart/form-data" method="POST">
		<!-- <input type="hidden" name="_method" value="PUT"> -->
		<table border="1" id="userTable">
			<tr>
				<th>头像:</th>
				<td><img src='${userInfo.headImagePath}'></td>
			</tr>
			<tr>
				<th>签名:</th>
				<td><input type="text" name="sign" value="${userInfo.sign}"></td>
			</tr>
			<tr>
				<th>姓名:</th>
				<td><input type="text" name="name" value="${userInfo.name}"></td>
			</tr>
			<tr>
				<th>性别:</th>
				<td id="sex">
					${userInfo.sex}</td>
				<!-- <input type="radio" name="sex" value="男" >男 <input
					type="radio" name="sex" value="女">女 -->
			</tr>
			<tr>
				<th>职称:</th>
				<td><input type="text" name="name" value="${userInfo.jobTitle}"></td>
			</tr>
			<tr>
				<th>院系:</th>
				<td><input type="text" name="name" value="${userInfo.major.department.dname}"></td>
			</tr>
			<tr>
				<th>专业:</th>
				<td><input type="text" name="name" value="${userInfo.major.mname}"></td>
			</tr>
			<tr>
				<th>昵称:</th>
				<td>${userInfo.nickname }</td>
			</tr>
			<tr>
				<th>邮箱:</th>
				<td><input type="text" name="name"  value="${email}"></td>
			</tr>
			<tr>
				<th></th>
				<td><input type="submit" value="保存"></td>
			</tr>
		</table>
		</form>
</body>
</html>
