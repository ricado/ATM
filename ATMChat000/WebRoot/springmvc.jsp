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

<title>My JSP 'springmvc.jsp' starting page</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	/* $("#file0").change(function() {
		var objUrl = getObjectURL(this.files[0]);
		console.log("objUrl = " + objUrl);
		if (objUrl) {
			$("#img0").attr("src", objUrl);
		}
	});
	//建立一個可存取到該file的url
	function getObjectURL(file) {
		var url = null;
		if (window.createObjectURL != undefined) { // basic
			url = window.createObjectURL(file);
		} else if (window.URL != undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file);
		} else if (window.webkitURL != undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	} */
		window._bd_share_config = {
			"common" : {
				"bdSnsKey" : {},
				"bdText" : "",
				"bdMini" : "2",
				"bdMiniList" : false,
				"bdPic" : "",
				"bdStyle" : "1",
				"bdSize" : "16"
			},
			"share" : {},
			"image" : {
				"viewList" : [ "qzone", "tsina", "tqq", "renren", "weixin",
						"sqq" ],
				"viewText" : "分享到：",
				"viewSize" : "16"
			},
			"selectShare" : {
				"bdContainerClass" : null,
				"bdSelectMiniList" : [ "qzone", "tsina", "tqq", "renren",
						"weixin", "sqq" ]
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
</script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<a href="appeal/hello.do">hello springMvc</a>
	<br>
	<a href="appeal/appeals.do">get all appeal</a>
	<br>
	<a href="appeal/new.do">new a appeal</a>
	<br>
	<a href="user/userHead.do">look user head</a>
	<br>
	<a href="user/userInfo/10001.do">10001 serInfo</a>
	<br>
	<form action="user/userInfo.do" method="get">
		<input type="text" name="userId"> <input type="submit"
			value="get userInfo(return view)">
	</form>

	<form action="user/userInfoJ.do" method="get">
		<input type="text" name="userId"> <input type="submit"
			value="get userInfo(return Json)">
	</form>


	<br>
	<form action="user/changeUserInfo.do" enctype="multipart/form-data"
		method="POST">
		<!-- <input type="hidden" name="_method" value="PUT"> -->
		<input type="text" name="userId" value="10001"> <input
			type="text" name="nickname" value="yeye"> <input type="file"
			name="file"> <input type="submit" value="input">
	</form>

	<br>
	<form action="user/changeUserInfoV.do" enctype="multipart/form-data"
		method="POST">
		<!-- <input type="hidden" name="_method" value="PUT"> -->
		<input type="text" name="userId" value="10001"> <input
			type="text" name="nickname" value="yeye"> <input type="file"
			name="file"> <input type="submit" value="input">
	</form>

	tupian:
	<input type="file" name="file"
		value="<img src='image/headImage/user/10001/10001.jpg'>">
	tupian:

	<h3>请选择图片文件：JPG/GIF</h3>

	<form name="form0" id="form0">
		<input type="file" name="file0" id="file0" multiple="multiple" /><br>
		<img src="" id="img0">
	</form>

	<div class="bdsharebuttonbox">
		<a href="#" class="bds_more" data-cmd="more"></a><a title="分享到QQ空间"
			href="#" class="bds_qzone" data-cmd="qzone"></a><a title="分享到新浪微博"
			href="#" class="bds_tsina" data-cmd="tsina"></a><a title="分享到腾讯微博"
			href="#" class="bds_tqq" data-cmd="tqq"></a><a title="分享到人人网"
			href="#" class="bds_renren" data-cmd="renren"></a><a title="分享到微信"
			href="#" class="bds_weixin" data-cmd="weixin"></a><a title="分享到QQ好友"
			href="#" class="bds_sqq" data-cmd="sqq"></a>
	</div>
	


</body>
</html>
</body>
</html>
