<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- <jsp:useBean id="online" class="com.atm.chat.bean.OnLineBean" scope="session"/> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'creatCrowd.jsp' starting page</title>
    
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
    	<form action="createCrowd.do" enctype="multipart/form-data" method="post">
    		群头像:<input type="file" name="file" id="file">
    		群名:<input type="text" name="crowdName"><br>
    		群描述:<input type="text" name="crowdDescription"><br>
    		群标签:<input type="text" name="crowdLabel"><br>
    		群性质:<input type="radio" name="isHidden" value="0">隐藏
    		<input type="radio" name="isHidden" value="1">公开<br>
    		群主:<input type="text" name="userId"><br>
    		<input type="submit" value="提交">	
    	</form>
    </div>
  </body>
</html>
