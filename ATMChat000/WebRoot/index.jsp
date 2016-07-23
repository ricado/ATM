<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.atm.chat.nio.oldServer.NIOServer"%>
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
<title>My JSP 'index.jsp' starting page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<SCRIPT type="text/javascript" src="js/jquery-2.1.1.min.js"></SCRIPT>
<script type="text/javascript">
	$(function(){
		$("#submit").click(function(){
			var newsId = $("#newsId").val();
			alert(newsId);
			$("#form").attr("action","schoolActive/getNews/" + newsId + ".do");
			return true;
		});
	});
</script>
</head>

<body>
	This is my JSP page.
	<br>

	<form action="" id="form">
		<input type="text" name="newsId" id="newsId">
		<input type="submit" id="submit" value="submit">
	</form>
	
	<a href="schoolActive/newsList.do">查看校友动态列表</a><br>

	<a href="schoolActive/getHeadNews.do">查看校友动态头条</a><br>
	
	<a href="schoolActive/getNewsList.do">查看校友动态列表View</a><br>
	
	<a href="writeSA.jsp">编辑校友动态</a><br>
		
	<a href="springmvc.jsp">编辑校友动态</a><br>
	
	<a href="recommendSm.jsp">推荐校友</a><br>
</body>
</html>
