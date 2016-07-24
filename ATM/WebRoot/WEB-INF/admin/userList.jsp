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

<title>My JSP 'userList.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<SCRIPT type="text/javascript" src="WebRoot/js/jquery-2.1.1.min.js"></SCRIPT>
<script type="text/javascript">
	/* $(function() {
		$.ajax({
			type : "POST",
			url : "ajax/online_userList.action",
			data : "",
			dataType : "text", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）
			success : function(json) {
				var obj = $.parseJSON(json); //使用这个方法解析json
				var string = obj.response; //result是和action中定义的result变量的get方法对应的
				$("#userList").html(string);
			},
			error : function(json) {
				alert("json=" + json);
				return false;
			}
		});
	}); */
	$(function() {
		//$("#page").append("/5页<button id='jump'>确定</button>");
		$("#page").html("/5页<button id='jump'>确定</button>");
	});
</script>
</head>

<body>
	户列表:
	<div>
		<table id="userList">
		</table>
		<div id="page">
			<button>上一页</button>
			<button>下一页</button>
			每页显示<input type="text" size="3" id="pageNum">条 跳转到第<input
				type="text" size="3" id="pageNum">
		</div>
	</div>
</body>
</html>
