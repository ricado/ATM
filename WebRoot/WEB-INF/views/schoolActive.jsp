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

<title>${schoolActive.mainTitle}</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="css/schoolActive/schoolactive.css">
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		var text = window.navigator.userAgent.toLowerCase();
		if (text.match(/MicroMessenger/i) == 'micromessenger') {
			$("#test").html("是微信浏览器");
			$("#tip").html("用浏览器打开本网页即可下载");
			$("#downloadForm").hide();
		} else {
			$("#test").html("不是微信浏览器");
		}
		var text1 = text.match(/MicroMessenger/i);
		$("#test1").html(text1);
	});
</script>
</head>

<!-- 
	数据库: schoolActive(
		activeId(编号);
		titile(标题);
		titleImage(转发时的图片);
		time(时间);
		writer(小编);
		content(正文);
		readCount(阅读量);
	)
	图片存放路径:
	ATM/image/schoolActive/{activeId}/
 -->
<body>
	<div id="download">
		ATM是广东金融学院校内最流行的App,还未使用?赶快下载加入我们的行列吧!!<span id="tip"></span>
		<form action="downloadApp.do" method="post" id="downloadForm">
			<input type="submit" name="submit" value="下载" id="downloadSubmit">
		</form>
		<!--<br>广告位  6W/月-->
	</div>
	<div id="body">
		<div id="head">
			<dl>
				<h3 id="title">${schoolActive.mainTitle}</h3>
				<span id="time">${schoolActive.time} &nbsp;&nbsp;&nbsp;</span>
				<span id="writer">${schoolActive.writer}</span>
			</dl>
		</div>
		<div id="content">
			${schoolActive.content}
		</div>
		<div id="footer"></div>
		<div>
			<span>阅读(${schoolActive.readCount})</span>
		</div>
	</div>
	<p id="test"></p>
	<p id="test1"></p>
</body>
</html>
