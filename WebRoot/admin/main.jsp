<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.println("path:" + path);
	System.out.println("basepath:" + basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>main.jsp</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- css -->
<link rel="stylesheet" type="text/css" href="css/admin/main.css">
<link rel="stylesheet" type="text/css" href="css/admin/main_head.css">
<link rel="stylesheet" type="text/css" href="css/admin/main_info.css">
<link rel="stylesheet" type="text/css" href="css/icon/iconfont.css">
<!-- javascript -->
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".head").click(function() {
			var next = $(this).next();
			next.slideToggle("slow");
		});
	});
</script>
</head>

<body>
	<div id="all">
		<div id="head"></div>
		<div id="info">
			<div id="navigation">
				<div id="basicInfo">
					<!-- 关于atm的一些资料消息 -->
					<dl id="manager" class="head">
						<dd>
							<i class="icon iconfont">&#xe60b;</i> 基本资料
						</dd>
					</dl>
				</div>
				<div id="user">
					<!-- 查找用户的资料，信息等等 -->
					<dl class="head">
						<dd>
							<i class="iconfont">&#xe608;</i> 用户
						</dd>
					</dl>
					<dl id="userList" class="navigationList">
						<dd>所有用户</dd>
						<dd>在线用户</dd>
					</dl>
				</div>
				<div id="crowd">
					<!-- 群聊 -->
					<dl class="head">
						<dd>
							<i class="iconfont">&#xe60d;</i> 群聊
						</dd>
					</dl>
					<dl id="crowdList" class="navigationList">
						<dd>所有群</dd>
						<dd>热门群</dd>
						<dd>申请</dd>
					</dl>
				</div>
				<div id="bbs">
					<dl class="head">
						<dd>
							<i class="iconfont">&#xe60e;</i> 贴子
						</dd>
					</dl>
					<dl id="bbsList" class="navigationList">
						<dd>热门贴</dd>
						<dd>最新贴</dd>
					</dl>
				</div>
				<div id="message">
					<dl class="head">
						<dd>
							<i class="iconfont">&#xe607;</i> 消息
						</dd>
					</dl>
					<dl id="messageList" class="navigationList">
						<dd>申述</dd>
						<dd>举报</dd>
						<dd>建议</dd>
					</dl>
				</div>
			</div>
			<div id="content">content</div>
		</div>
	</div>

</body>
</html>
