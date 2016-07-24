<%@page import="org.apache.struts2.json.JSONUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.atm.util.*" %>
<%@ page import="com.atm.model.chat.PrivateChat" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'chat_login.jsp' starting page</title>
    
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
    	<%
    		String json = (String)request.getAttribute("json");
    		System.out.println(json);
    		Object[] objects = JsonUtil.jsonToObjects(json, PrivateChat.class);
    		for(int i = 0;i<objects.length;i++){
    			PrivateChat chat = (PrivateChat)objects[i];
    	%>
    		<p><%= chat.getSendTime() %>：</p>
    		<p><%= chat.getUserSendId()%>:<%=chat.getSendContent() %></p>
    	<%
    		}
    	 %>
    </div>
  </body>
</html>
