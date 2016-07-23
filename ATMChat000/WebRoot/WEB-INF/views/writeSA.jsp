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

<title>ATM——编辑校友动态</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#photo").click(function() {
			$(".file").trigger("click");
		});
		$(".file").change(function() {
			$("#tip").html("file");
			var file = this.files[0];
			$("#tip").html(file)
			var reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onload = function(e) {
				$("#photo").attr("src", this.result);
			}
		});
		$("#submit").click(function() {
			var content = $("#content").html();
			//var images = $(".content_image");
			$("#content_hidden").attr("value", content);
			$("#form_submit").submit();
		});
	});
</script>
<style type="text/css">
body {
	font-family: 微软雅黑;
}

#write {
	border: 1px solid rgb(0, 128, 0);
	border-radius: 10px;
	box-shadow: 0px 0px 5px 0px rgb(25, 233, 34);
	padding: 10px;
	box-shadow: 0px 0px 5px 0px rgb(25, 233, 34);
}

#head {
	border: 3px solid rgb(65, 186, 65);
	border-radius: 10px;
	padding: 10px;
	margin-bottom: 10px;
}
/*表格的大小操作*/
table {
	width: 90%;
}

table td {
	width: 100%;
}

table td .header {
	width: 100%;
}

#photo {
	width: 100px;
}
/* #head .header {
	width: 80%;
	height: auto;
	padding: 5px;
	margin-bottom: 5px;
} */
/*内容框的操作*/
#content {
	word-break: break-all;
	overflow: auto;
	border: 3px solid rgb(65, 186, 65);
	border-radius: 10px;
	width: auto;
	padding: 10px;
	height: 70%;
}
/*隐藏加载文件的input*/
#input_submit, .file {
	display: none;
}

.content_image {
	width: auto;
}
</style>
</head>
<body>
	<div>
		<div id="write">
			<div id="head">
				<form action="schoolActive/saveSchoolActive.do"
					enctype="multipart/form-data" method="Post" id="form_submit">
					<table>
						<tr>
							<th>标&nbsp;&nbsp;&nbsp;&nbsp;题:</th>
							<td><input type="text" id="title" name="mainTitle"
								class="header" value="${news.mainTitle}"> <input
								type="hidden" name="newsId" id="newsId" value="${news.newsId}"></td>
							<th rowspan="3"><input type="hidden" id="content_hidden"
								name="content"> <img alt="选择图片"
								src="${news.newImagePath}" id="photo" class="opera_image">
								<input type="file" name="file" class="file"> <input
								type="hidden" name="describe" id="describe"> <input
								type="radio" name="isHeadNews" value="1">头条 <input
								type="radio" name="isHeadNews" value="0">非头条</th>
						</tr>
						<tr>
							<th>副标题:</th>
							<td><input type="text" id="subHead" name="viceTitle"
								class="header" value="${news.viceTitle}"></td>
						</tr>
						<tr>
							<th>小&nbsp;&nbsp;&nbsp;编:</th>
							<td><input type="text" id="writer" name="writer"
								class="header" value="${news.writer}"></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="content" contenteditable="true">${news.content}</div>
			<div id="opera">
				<img alt="预览" src="image/icon/iconfont-yulan.png" id="tosee"
					class="opera_image"> <img alt="提交"
					src="image/icon/iconfont-tijiao.png" id="submit"
					class="opera_image">
			</div>
		</div>
	</div>
	<p id="tip">aaaa</p>
</body>
</html>
