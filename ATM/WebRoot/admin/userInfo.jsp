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
<%@ taglib uri="/struts-tags" prefix="s"%>
<title>My JSP 'userInfo.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<SCRIPT type="text/javascript" src="js/jquery-2.1.1.min.js"></SCRIPT>

<style type="text/css">
	table,td,th{
		border-collapse: collapse;
	}
	#all {
		width: -moz-max-content;
		margin: auto;
		box-shadow: 0px 0px 7px 1px #333;
		border-radius: 8px;
		margin-top: 50px;
	}
	
</style>
</head>

<body>
	<div id="all">
		<div id="info_table">
			<table id="info" border="1">
				<tr>
					<th>账号:</th>
					<td id="userId"><s:property value="user.userId" /></td>
				</tr>
				<tr>
					<th>昵称:</th>
					<td id="nickname"><s:property value="user.nickname" /></td>
				</tr>
				<tr>
					<th>真实姓名:</th>
					<td id="name"><s:property value="user.name" /></td>
				</tr>
				<tr>
					<th>性别：</th>
					<td id="sex"><s:property value="user.sex" /></td>
				</tr>
				<tr>
					<th>离线时间</th>
					<td id="offTime"><s:property value="user.offTime" /></td>
				</tr>
			</table>
		</div>
	</div>
	<s:debug></s:debug>
</body>
</html>
