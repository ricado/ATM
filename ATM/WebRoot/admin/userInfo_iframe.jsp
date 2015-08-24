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

<title>My JSP 'userIndo_iframe.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
body {
	margin: 0px;
	padding: 0px;
}

#list dt {
	width: 500px;
	background-color: gray;
	margin-bottom: 10px;
}
</style>
</head>

<body>
	<div>
		<dl id="list">
			<dt>这个存贮过程是要执行多个语句的,当第一部分执行完之后肯定会
				涉及到该表的改动,那么就会立即触发触发器的操作,触发器会使用该字段.但是存贮过程还没有结束,他</dt>
			<dt>议存贮过程实现的功能一体实现,不建议存贮过
				程+触发器的实现方式.容易产生该错误,并且在后续变更等方面限制会更大,性能也有所影响</dt>
			<dt>议存贮过程实现的功能一体实现,不建议存贮过
				程+触发器的实现方式.容易产生该错误,并且在后续变更等方面限制会更大,性能也有所影响</dt>
			<dt>议存贮过程实现的功能一体实现,不建议存贮过
				程+触发器的实现方式.容易产生该错误,并且在后续变更等方面限制会更大,性能也有所影响</dt>
			<dt>议存贮过程实现的功能一体实现,不建议存贮过
				程+触发器的实现方式.容易产生该错误,并且在后续变更等方面限制会更大,性能也有所影响</dt>

		</dl>
	</div>
</body>
</html>
