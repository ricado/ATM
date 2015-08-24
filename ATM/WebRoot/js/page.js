document.write("<script language='javascript' src='jquery-2.1.1.min.js'></script>");
alert("ccy is good boy");
$("#next").click(function() {
	$.getUserList(1);
})
// 下一页
$("#last").click(function() {
	$.getUserList(-1);
})
// 跳转
$("#jump").click(function() {
	$.getUserList(0);
})
// 更换显示的记录数
$("#pageNum").changer(function() {
	$.getUserList(0);
})
$("#fresh").click(function() {
	$.getUserList(0);
})


$.send = function(){
	alert("aaaaa");
}

$.sendAjax = function(url, params) {
	alert("aaaaa");
	return $.ajax({
		type : "POST",
		url : "ajax/" + url + ".action",
		data : params,
		dataType : "text", // ajax返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）
		success : function(json) {
			var obj = $.parseJSON(json); // 使用这个方法解析json
			return obj;
		},
		error : function(json) {
			alert("json=111" + json);
			return false;
		}
	});
}

$.getParams = function(i) {
	// var currentPage = $("#currentPage").attr("value");
	var currentPage = $("#current").val();
	var max = $("#max").attr("value");
	$("#tip").html(
			"currentPage:" + currentPage + "pageNum :" + pageNum + " max:"
					+ max);
	if (i == 1) {
		currentPage++;
	} else if (i == -1) {
		currentPage--;
	}
	if (currentPage >= 1 && currentPage <= max) {
		// var pageNum = $("#pageNum").attr("value");
		var pageNum = $("#pageNum").val();
		$("#tip").html(
				"currentPage:" + currentPage + "pageNum :" + pageNum + " max:"
						+ max);
		var params = {
			currentPage : currentPage,
			pageNum : pageNum
		}
		// $("#tip").append("params" + params);
		return params;
	} else {
		return false;
	}
}
$.getUserList = function(i) {
	var params = $.getParams(i);
	if (params == false) {
		$("#tip").html("无法下一页或者上一页");
	} else {
		$.sendAjax("http://localhost:8080/ATM/ajax/page_userList", params)
	}
}
$.getLoginList = function(i) {
	var params = $.getParams(i);
	if (params == false) {
		$("#tip").html("无法下一页或者上一页");
	} else {
		$.sendAjax("http://localhost:8080/ATM/ajax/page_loginList", params)
	}
}