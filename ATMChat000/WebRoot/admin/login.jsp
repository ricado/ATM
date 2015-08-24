<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	out.println(path);
	out.println(basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login.jsp' starting page</title>

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
	<div algin="center">
	<div algin="center">登录</div>
    	<div>
    		<form action="http://localhost:8080/ATM/login_login.action" method="post">
	    		<table>
	    			<tr>
	    			<th>账号:</th>
	    			<td><input type="text" name="userId"></td>
	    			</tr>
	    			<tr>
	    			<th>密码:</th>
	    			<td><input type="password" name="userPwd"></td>
	    			</tr>
	    		</table>
	    		<input type="submit" value="登录">
    		</form>
    	</div>
    </div>
  </body>
</html>
