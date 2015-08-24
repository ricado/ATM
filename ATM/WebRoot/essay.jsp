<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*,com.atm.model.user.UserInfo" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserInfo user = (UserInfo)session.getAttribute("user");
String me;
if(user!=null){
	me =  user.getUserId();
}else{
	me = "";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<jsp:useBean id="essayBean" class="com.atm.model.define.bbs.EssayDetailView" scope="request" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- 获取标题 --%>
<title><%= essayBean.getTitle() %></title>

<script src="js/jquery-1.11.3.min.js" type="text/javascript">	
</script>
<script type="text/javascript">

	$(document).ready(function(){
		changeLab();//改变标签样式
		var dept = $(".department").html();
		if(dept!="未知"){
			$("#deptSpan").show();
		}
		$("#clickGood").click(function(){
			//window.location.href="clickGood/<%= essayBean.getEssayId() %>.html";
			//调用客户端的点赞者列表
			window.demo1.goClickGoodView("<%=basePath%>clickGood/<%= essayBean.getEssayId() %>.html");
			
		})
		
		$("#comment").click(function(){
			//window.demo1.goCommentView("http://192.168.191.1:9999/atmDemo1.1/replyList.jsp");
			window.location.href="replyList.jsp?essayId=<%= essayBean.getEssayId() %>";
		})
		
	});
	
	function changeLab(){
		var html = "";
		
        var label = $('#label');//获取jquery对象
		var color =$("#color");
		
		var labList = label.html().split("*#");//拆分字符串
		var colList = color.html().split("*");
		
		for(i=0; i<labList.length; i++){
			 html = html
			 		+'<a href="javascript:void(0)" onclick=toLab("'+labList[i]+'")>'
			 		+'<div style="background-color:'+colList[i]
					+'; display:inline; padding:0.1em 0.3em; font-size:0.8em;border:1px solid #E7E7E7;">'
					+'<font  style="color:#FFFFFB; font-weight:600;">' 
					+ labList[i] + '</font></div></a>';
			 if(i<labList.length-1){
				 html = html+"&nbsp&nbsp&nbsp&nbsp";
			 }
		}
        label.html(html).css("display","block");
    }
 	
 	//检测登陆状态
	function checkLogin(){
		if("<%= me %>".length!=0){
			return true;
		}else{
			showMess("未登录，请下载U.S  APP");
			return false;
		}
	}
	
	function toLab(labName){
		if(checkLogin()==false){
			return;
		}
		alert("跳转标签："+labName);
	}
	function toPerson(userId){
		if(checkLogin()==false){
			return;
		}
		showMess("跳转"+userId);
	}
	//显示提示信息
	function showMess(mess){
			var screenWidth = $(window).width();
			$("#message").html(mess)
			.css("left",(screenWidth-$("#message").width())/2+"px")
			.show(300).delay(3000).hide(300); 
	}
	
</script>

<style type="text/css">
<!--
html,
body {
	font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
	background-color:#FFF;
	margin: 0;
	padding: 0;
	color: #000;
	height:100%;
}


h1, h2, h3, h4, h5, h6, p {
	margin-top: 1em;	 /* 删除上边距可以解决边距会超出其包含的 div 的问题。剩余的下边距可以使 div 与后面的任何元素保持一定距离。 */
	padding-right: 0.5em;
	padding-left: 0.5em; /* 向 div 内的元素侧边（而不是 div 自身）添加填充可避免使用任何方框模型数学。此外，也可将具有侧边填充的嵌套 div 用作替代方法。 */
}
a img { /* 此选择器将删除某些浏览器中显示在图像周围的默认蓝色边框（当该图像包含在链接中时） */
	border: none;
}

/* ~~ 站点链接的样式必须保持此顺序，包括用于创建悬停效果的选择器组在内。 ~~ */
a:link {
	color:#414958;
	text-decoration: none; /* 除非将链接设置成极为独特的外观样式，否则最好提供下划线，以便可从视觉上快速识别 */
}
a:visited {
	color: #4E5869;
	text-decoration: underline;
}
a:hover, a:active, a:focus { /* 此组选择器将为键盘导航者提供与鼠标使用者相同的悬停体验。 */
	text-decoration: none;
}

/* ~~ 此容器包含所有其它 div，并依百分比设定其宽度 ~~ */
.container {
	width: 100%;
	max-width: 100%;/* 可能需要最大宽度，以防止此布局在大型显示器上过宽。这将使行长度更便于阅读。IE6 不遵循此声明。 */
	min-width: 100%;/* 可能需要最小宽度，以防止此布局过窄。这将使侧面列中的行长度更便于阅读。IE6 不遵循此声明。 */
	background-color:#FFF;
	margin: 0 auto; /* 侧边的自动值与宽度结合使用，可以将布局居中对齐。如果将 .container 宽度设置为 100%，则不需要此设置。 */
	height:auto !important;
	min-height:100%;
	height::100%;
}
.department{
	float:right;
	color:#AAA;
	font-size:0.9em;
	margin-right:0.5em;
	margin-left:0.5em;
	margin-top:1em;
}
 #deptSpan{
 	display:none;
 }

/* ~~ 这是布局信息。 ~~ 

1) 填充只会放置于 div 的顶部和/或底部。此 div 中的元素侧边会有填充。这样，您可以避免使用任何“方框模型数学”。请注意，如果向 div 自身添加任何侧边填充或边框，这些侧边填充或边框将与您定义的宽度相加，得出 *总计* 宽度。您也可以选择删除 div 中的元素的填充，并在该元素中另外放置一个没有任何宽度但具有设计所需填充的 div。

*/
.content {
	padding: 0em 0;
	word-wrap:break-word;
	padding-bottom: 40px;/*高度等于footer的高度*/ 
	
}
.footer { 
position: relative; 
margin-top: -40px;/*等于footer的高度*/ 
height: 40px; 
clear:both; 
font-size:0.8em;
width:100%;

} 

/* ~~ 其它浮动/清除类 ~~ */
.fltrt {  /* 此类可用于在页面中使元素向右浮动。浮动元素必须位于其在页面上的相邻元素之前。 */
	float: right;
	margin-left: 0.5em;
}
.fltlft { /* 此类可用于在页面中使元素向左浮动。浮动元素必须位于其在页面上的相邻元素之前。 */
	float: left;
	margin-right: 0.5em;
}
.clearfloat { /* 如果从 #container 中删除或移出了 #footer，则可以将此类放置在 <br /> 或空 div 中，作为 #container 内最后一个浮动 div 之后的最终元素 */
	clear:both;
	height:0;
	font-size: 0.1em;
	line-height: 0em;
}
.goodSpan{
	width:100%;
	display:inline-block;
	min-height:2em;
}
	/* 信息提示框 */
	#message{
		display:none;
		position:fixed;
		bottom:3em;
		font-size:1.2em;
		color:#6B6;
		background-color:#EEE;
		padding:0.3em 0.5em;
	}
-->
</style></head>

<body>

<div class="container">
<!-- 帖子头部-->
  <div>
  	<table width="100%">
    <tr><td valign="top" width="60px">
     <!--头像-->
   	<div style="width:50px; height:50px; border-radius:10%; overflow:hidden; margin-left:0.3em; margin-top:0.3em">
        <a href="javascript:void(0)" onclick="toPerson('<%= essayBean.getPublisherId() %>')"><img src="<%= essayBean.getHeadImagePath() %>" alt="用户头像" name="user_head" width="100%" height="100%" id="user_head" style="background-color:#CF0;display:block;" /></a>
    </div>
    </td>
    <!--头像结束-->
    <td align="left">
    <!--昵称及日期-->
  	<div>
    		
          	<table style="margin-left:0.3em;">
            <tr><td style="overflow:hidden; font-size:1em; text-overflow:ellipsis; display:block;font-weight:300; max-height:2.5em;">
            <a href="javascript:void(0)" onclick="toPerson('<%= essayBean.getPublisherId() %>')">
				<font color="#000000"><%= essayBean.getNickname() %></font>
                </a>
            </td></tr>
           <tr><td  style="font-size:0.7em;color:#A5A5A5; overflow:hidden;text-overflow:ellipsis; display:block; ">
		   			<%= essayBean.getPublishTime() %>
                </td></tr>
           </table>
           
    </div>
    <!--昵称及日期结束 -->
    </td>
    <!--阅读量开始 -->
    <td style="min-width:6em"><!--最小宽度为下面的块保留空间，避免重叠 -->
    		<div  style="font-size:0.7em; position:absolute; right:0.1em; top:0.2em; text-align:center">
            	<div><img src="image/book1.png" height="40px" width="40px" alt="阅读"/></div>
    			<p style="margin-top:-0.5em; color:#666">阅读(<%= essayBean.getClickNum() %>)</p>
   			</div>
    </td>
    <!--阅读量结束-->
    </tr>
    </table>
   
    <!--标题开始-->
    <h4>[<%= essayBean.getEssayType()%>] <%= essayBean.getTitle() %></h4>
    <!--标题结束-->
    <!--标签开始 -->
     <div id="label" style="margin-left:0.5em;display:none; margin-top:-0.5em;"><%= essayBean.getLabName() %></div>
     <div id="color" style="display:none"><%= essayBean.getLabColor() %></div>
     <!--标签结束 -->
     
     <!-- 系别 -->
     <span id="deptSpan">
     <hr color="#AAA" size="1" style="margin-top:1.4em;float:right;width:1em" />
     <span class="department"><%= essayBean.getDepartment() %></span>
     <hr color="#AAA" size="1" style="margin-top:1.4em;" />
     </span>
     <div class="clearfloat"></div>
     <!-- end .header --></div>
     <!--正文开始 -->
  <div class="content">
    <p ><%= essayBean.getContent() %> </p>
    
    <a href="javascript:void(0)" id="comment">测试》进入评论列表</a>
     <a href="testLogin.jsp" >测试》进入测试界面</a>
    <!--  <a onclick="window.demo.goActivity()">测试</a> -->
    <!-- end .content --></div>
      <div id="message"></div>
    <!--正文结束 -->
     <!-- end .container --></div>
    
    <!--脚注开始 -->
  <div class="footer"> 
  <%
  	if(essayBean.getClickGoodNum()>0){
   %>
   <hr />
   <a href="javascript:void(0)" class="goodSpan" id="clickGood">
	<span >
  	&nbsp;&nbsp;&nbsp;<%= essayBean.getClickGoodName() %>&nbsp;&nbsp;<font color="#999999">等<%= essayBean.getClickGoodNum() %>人赞了这篇帖子</font>
   </span>
    </a>
    <%
    	}
     %>
  </div>

    
 
</body>
</html>