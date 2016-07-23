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

<title>广金学子顿号回校援建图书馆————夜赚百万</title>

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
		<br>广告位  6W/月
	</div>
	<div id="body">
		<div id="head">
			<dl>
				<h3 id="title">珠海市和亿投资董事长炖好校友回母校作报告</h3>
				<span id="time">2015-10-20 20:45 &nbsp;&nbsp;&nbsp;</span>
				<span id="writer">业业</span>
			</dl>
		</div>
		<div id="content">

		<img src="image/schoolActive/1.jpg" class="image"><br><br>
		&nbsp;&nbsp;&nbsp;5月28日，“金子之光”校友报告会第二十七场在教学实验中心大楼学术报告厅举行。本次报告会是金子之光“创业板”的第七场，邀请了珠海市和亿投资有限公司董事长兼珠海市和亿餐饮管理有限公司、阳春市和亿种植专业合作社董事长，我校82级校友黄冠回母校为师生作报告，副院长穆林出席了报告会并致欢迎词，报告会由校友办副主任何进江主持。

			<br><img src="image/schoolActive/2.jpg" class="image"><br>
			穆林为黄冠校友颁发了“大学生创业导师”的聘书<br><br>

			&nbsp;&nbsp;&nbsp;穆林简要介绍了黄冠校友的就业、创业经历，高度评价了他踏实肯干，严谨细致的工作作风，服务社会、造福于民的社会责任感，并对他情系母校，为母校建设出力献策的赤子之心表示感谢。穆林希望同学们能向黄冠校友学习，以踏实严谨的工作作风要求自己，以感恩之心回报社会与母校，在人生道路上不懈追求，实现梦想。会上，穆林为黄冠校友颁发了“大学生创业导师”的聘书。
			报告会上，黄冠介绍了自己就业创业过程中的酸甜苦辣，总结了这段人生历程的所思所悟。黄冠说，创业过程中熬过苦过，也笑过甜过，但始终保持着清晰细致的思路，秉承着“世事洞明皆学问，人情练达即文章”的态度，以社会中的人和事为教材，不断丰富自己的阅历和经验，实现了创业的成功。他还结合自己教育子女的经验讲述了“挫折教育”的重要性，教导同学们要做好人生规划，知道什么可为什么不可为，工作中要专注、主动、不怕吃亏，以负责任的态度面对工作、社会和家庭，从而成就人生的价值。
			会后，黄冠接受了校友工作学生助理的采访。参加报告会的还有金融系、校友办、宣传部、团委、关工委和就业创业教育中心的负责人和老师。</div>
		<div id="footer"></div>
		<div>
			<span>阅读(888)</span>
		</div>
	</div>
	<p id="test"></p>
</body>
</html>
