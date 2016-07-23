<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	function tempClick() {

		//  火狐浏览器实现点击图片出现文件上传界面
		var a = document.createEvent("MouseEvents");
		a.initEvent("click", true, true);
		document.getElementById("upload_main_img").dispatchEvent(a);

		//IE浏览器实现点击图片出现文件上传界面
		//document.getElementById('main_img').click(); //调用main_img的onclick事件
	}

	/**
	 * 预览图片
	 * @param obj
	 * @returns {Boolean}
	 */
	function PreviewImg(obj) {

		var newPreview = document.getElementById("img_2");
		var imgPath = getPath(obj);
		alert(imgPath);
		if (!obj.value.match(/.jpg|.gif|.png|.bmp/i)) {
			alert("图片格式错误！");
			return false;
		}

		newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgPath;
	}
	/**
	 * 得到图片绝对路径
	 * @param obj
	 * @returns
	 */
	function getPath(obj) {
		if (obj) {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				obj.select();
				//IE下取得图片的本地路径
				return document.selection.createRange().text;
			} else if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0) {
				if (obj.files) { // Firefox下取得的是图片的数据
					return files.item(0).getAsDataURL();
				}
				return obj.value;
			}
			return obj.value;
		}
	}
</script>
</head>
<body>
aaa
	<form>
		<div>
			<input type="file"
				style="position: absolute; filter: alpha(opacity = 0); opacity: 0; width: 30px;"
				size="1" id="main_img" name="main_img" onchange="PreviewImg(this)">
		</div>
		<div id="img_2"
			style="width:133px;height:95px; cursor:pointer; background-image:url('image/headImage/user/10001/10001.jpg');
			onclick="tempClick()"></div>
	</form>
</body>
</html>
