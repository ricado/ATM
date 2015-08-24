<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
    <form action="user_login.action" method="post">
    	用户:<input type="text" name="userId"><br>
    	密码:<input type="password" name="userPwd"><br>
    	<input type="submit" value="submit">
    </form>
    <div>
    	<form action="user_findUserId.action">
    		找回账号:<input type="text" name="email"><br>
    		<input type="submit" value="找回账号">
    	</form>
    	<form action="user_forgetPassword.action">
    		输入账号或邮箱:<input type="text" name="idOrEmail"><br>
    		<input type="submit" value="忘记密码">
    	</form>
    </div>
    <a href="appeal.jsp">申述</a>
  </body>
</html>
