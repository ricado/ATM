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

<title>My JSP 'userInfo1.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" type="text/css" href="css/userInfo.css">
<SCRIPT type="text/javascript" src="js/jquery-2.1.1.min.js"></SCRIPT>
<script type="text/javascript">
	$(function() {
		$("#tip").html("开始进入界面");
		$.addContent();
		$("#content").scroll(function() {
			var $this = $(this), viewH = $(this).height(), //可见高度  
			contentH = $(this).get(0).scrollHeight, //内容高度  
			scrollTop = $(this).scrollTop();//滚动高度  
			//if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容
			if (scrollTop / (contentH - viewH) >= 1.00) { //到达底部100px时,加载新内容  
				$("#tip").html("滚动条到底");
				$.addContent();
			} else {
				$("#tip").html("起来");
			}
		});
	});
	$.addContent=function(){
		$("#tip").html("aaaaaaaaaaaaaaaaaaaaaa");
		var first = $("#first").val();
		var params = {
			first:first
		}
		$.ajax({
			type : "POST",
			url : "ajax/adminJson_findAllUser.action",
			data : params,
			dataType : "text", //ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）
			success : function(json) {
				var obj = $.parseJSON(json); //使用这个方法解析json
				$("#first").val(obj.first);
				$("#content").append(obj.json);
			},
			error : function(json) {
				alert("json=111" + json);
				return false;
			}
		});
	}
</script>
</head>

<body>
	<p id="tip">aa</p>
	<div id="main">
		<div id="userinfo">
			<dl id="head">
				<dt>
					<img id="headImage" src="image/well.jpg">
				</dt>
				<dd id="focus_num">
					<b><a target="_blank" href="">25</a></b><br>关注
				</dd>
				<dd id="fans_num">
					<b><a target="_blank" href="">256</a></b><br>粉丝
				</dd>
			</dl>
			<dl id="info">
				<dt id="nickname">
					<b>威尔-斯密斯</b>
				</dt>
				<dt id="normal">
					<span id="sex">男</span> <span id="f">|</span> <span id="address">广州龙洞</span><span
						id="f">|</span> <span id="school">广东金融学院</span> <span id="f">|</span>
					<span id="deparment">互联网金融与信息工程系</span><span id="f">|</span> <span
						id="major">计算机科学与技术</span> <span id="f">|</span>
				</dt>
				<dt id="order">
					上次离线时间:<span id="offTime">2015-8-17 19:20:21</span> <span id="f">|</span>
					<span>在线<img id="order_online"
						src="image/flag/iconfont-On-line.png"></span>
				</dt>
				<dt id="number">
					<span id="f">学号</span><span id="sno">131544215</span> <span id="f">|</span>
					<span id="f">账号</span><span id="userId">10001</span>
				</dt>
			</dl>
		</div>
	</div>
	<div id="crowdOrUser">
		<dl id="dl_list">
			<dt id="list">
				<span class="span_list"> 所有用户 </span> <span class="span_list">
					已登录用户 </span> <span class="span_list"> 所有群聊 </span>
			</dt>
		</dl>
		<div id="content">
		<input type="hidden" name="first" id="first" value="0">
			<!-- <div id='content_list'>
				<dl class='user_head'>
					<dt class='headImage'>
						<img src='image/well.jpg' />
					</dt>
				</dl>
				<dl class='users_info'>
					<input type='hidden' class='userId' value='10001' id='userId' />
					<dt class='username'>贾登-斯密斯</dt>
					<dt class='usersInfo'>
						<span>男</span><span id='f'>|</span> <span>广东金融学院</span><span id='f'>|</span>
						<span>互联网金融与信息工程系</span>
					</dt>
				</dl>
				<dl class='focus_fans'>
					<dd id='focus_num'>
						<b><a target='_blank' href=''>25</a></b><br>关注
					</dd>
					<dd id='fans_num'>
						<b><a target='_blank' href=''>256</a></b><br>粉丝
					</dd>
				</dl>
			</div>
			<iframe id="list_iframe" src="admin/userInfo_iframe.jsp"/> -->
		</div>
	</div>
	<div id="ccy">
		<table>
			<tr><td>yyyyy</td></tr>
		</table>
	</div>
	<s:debug></s:debug>
</body>
</html>
