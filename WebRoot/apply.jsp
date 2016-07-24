<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
  <head>
    <base href="<%=basePath%>" />
    <jsp:useBean id="applyBean" class="com.atm.model.define.ApplyView" scope="request"></jsp:useBean>
    <title><%= applyBean.getReTypeName()+"-"+applyBean.getWoTypeName() %></title>  
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0,user-scalable=0"  />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	
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
	margin-top: 1em;	 /* 删除上边距可以解决边距会超出其包含的 div 的问题。剩余的下边距可以使 div 与后面的任何元素保持一定距离。 

*/
	padding-right: 0.5em;
	padding-left: 0.5em; /* 向 div 内的元素侧边（而不是 div 自身）添加填充可避免使用任何方框模型数学。此外，也可将具有侧边填充的嵌套 

div 用作替代方法。 */
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
	
	
	.container {
	<!--background-image:url(<%=basePath%>WebRoot/back/back.jpg);-->
	width: 100%;
	max-width: 100%;/* 可能需要最大宽度，以防止此布局在大型显示器上过宽。这将使行长度更便于阅读。IE6 不遵循此声明。 */
	min-width: 100%;/* 可能需要最小宽度，以防止此布局过窄。这将使侧面列中的行长度更便于阅读。IE6 不遵循此声明。 */
	background-color:#d3d7d4;
	margin: 0 auto; /* 侧边的自动值与宽度结合使用，可以将布局居中对齐。如果将 .container 宽度设置为 100%，则不需要此设置。 */
	height:auto !important;
	min-height:100%;
	height::100%;
}
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
.clearfloat { /* 如果从 #container 中删除或移出了 #footer，则可以将此类放置在 <br /> 或空 div 中，作为 #container 内最后一个浮动 div 之后

的最终元素 */
	clear:both;
	height:0;
	font-size: 0.1em;
	line-height: 0em;
}
.recuitType{
	float:right;
	color:#C00;
	font-size:1.2em;
	margin-right:0.5em;
	margin-left:0.5em;
	margin-top:0.5em;
}
.baseInform{
	text-align:left;
	margin:0.5em;
	padding:0.5em;
	background-color:#EEE;
	border-radius:1em; 
	overflow:hidden;
}

.salary{
	color:#F30;
	font-weight:bolder;
	font-size:1.5em;
}
.call{
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #eae0c2), color-stop(1, #bdb190));
	background:-moz-linear-gradient(top, #eae0c2 5%, #bdb190 100%);
	background:-webkit-linear-gradient(top, #eae0c2 5%, #bdb190 100%);
	background:-o-linear-gradient(top, #eae0c2 5%, #bdb190 100%);
	background:-ms-linear-gradient(top, #eae0c2 5%, #bdb190 100%);
	background:linear-gradient(to bottom, #eae0c2 5%, #bdb190 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#eae0c2', endColorstr='#bdb190',GradientType=0);
	background-color:#eae0c2;
	-moz-border-radius:15px;
	-webkit-border-radius:15px;
	border-radius:15px;
	border:2px solid #333029;
	display:inline-block;
	cursor:pointer;
	color:#505739;
	font-family:Arial;
	font-size:14px;
	font-weight:bold;
	padding:5px 10px;
	text-decoration:none;
	margin:0 2em;
}
.call:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #bdb190), color-stop(1, #eae0c2));
	background:-moz-linear-gradient(top, #bdb190 5%, #eae0c2 100%);
	background:-webkit-linear-gradient(top, #bdb190 5%, #eae0c2 100%);
	background:-o-linear-gradient(top, #bdb190 5%, #eae0c2 100%);
	background:-ms-linear-gradient(top, #bdb190 5%, #eae0c2 100%);
	background:linear-gradient(to bottom, #bdb190 5%, #eae0c2 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#bdb190', endColorstr='#eae0c2',GradientType=0);
	background-color:#bdb190;
}

.call2 { 
		color:rgb(255, 255, 255);
		margin:1em;
		font-size:1em;
		padding-top:0.3em;
		padding-bottom:0.3em;
		padding-left:0.7em;
		padding-right:0.7em;
		border-width:2px;
		border-color:rgb(197, 229, 145);
		border-style:solid;
		border-radius:16px;
		background-color:#45b97c;
		}
.call2:hover{color:#ffffff;background-color:#78c300;border-color:#c5e591;}

	</style>
  </head>
  
  <body>
    <div class="container">
<!-- 帖子头部-->
  <div>
  	<table width="100%">
    <tr><td valign="top" width="60px">
     <!--头像-->
   	<div style="width:50px; height:50px; border-radius:10%; overflow:hidden; margin-left:0.3em; margin-top:0.3em">
        <a href="javascript:void(0)" onclick="toPerson('<%= applyBean.getPublisherId() %>')"><img src="<%= applyBean.getHeadImagePath() %>" alt="用户头像" 

name="user_head" width="100%" height="100%" id="user_head" style="background-color:#CF0;display:block;" /></a>
    </div>
    </td>
    <!--头像结束-->
    <td align="left">
    <!--昵称及日期-->
  	<div>
    		
          	<table style="margin-left:0.3em;">
            <tr><td style="overflow:hidden; font-size:1em; text-overflow:ellipsis; display:block;font-weight:300; max-height:2.5em;">
            <a href="javascript:void(0)" onclick="toPerson('<%= applyBean.getPublisherId() %>')">
				<font color="#000000"><%= applyBean.getNickname() %></font>
                </a>
            </td></tr>
           <tr><td  style="font-size:0.7em;color:#A5A5A5; overflow:hidden;text-overflow:ellipsis; display:block; "> <%= applyBean.getPublishTime() %>
		   			 <%-- <% if(me.equals(applyBean.getPublisherId())) %> (应有删除按钮)  --%>
                </td>
                </tr>
           </table>
           
           
    </div>
    <!--昵称及日期结束 -->
    </td>
    <!--阅读量开始 -->
    <td style="min-width:6em"><!--最小宽度为下面的块保留空间，避免重叠 -->
    		<div  style="font-size:0.7em; position:absolute; right:0.1em; top:0.2em; text-align:center">
            	<div><img src="image/book1.png" height="40px" width="40px" alt="阅读"/></div>
    			<p style="margin-top:-0.5em; color:#666">阅读(<%= applyBean.getClickNum() %>)</p>
   			</div>
    </td>
    <!--阅读量结束-->
    </tr>
    </table>
     
     <!-- 招聘类型 -->
     <span id="deptSpan">
     <hr color="#AAA" size="1" style="margin-top:1.4em;float:right;width:1em" />
     <span class="recuitType"><%= applyBean.getWoTypeName() %></span>
     <hr color="#AAA" size="1" style="margin-top:1.4em;" />
     </span>
     <div class="clearfloat"></div>
     <!-- end .header --></div>
     <!--正文开始 -->
  <div class="content">
    <div class="baseInform">
	<h3>基本信息：</h3>
    <table>
        <tr>
        <td>期望薪资：</td><td colspan="1"><span class="salary"><%= applyBean.getExpectSalary() %></span></td>
        </tr>
        <tr>
        <td>招聘类型：</td><td><%= applyBean.getReTypeName() %></td>
        </tr>
         <tr>
        <td>工作类型：</td><td><%= applyBean.getWoTypeName() %></td>
        </tr>
       <%--   <tr>
        <td> 工作地点：</td><td><%= applyBean.getWorkAddress() %></td>
        </tr> --%>
        <tr>
        <td> 联系方式：</td><td><%= applyBean.getTelephone() %></td>
        </tr>
	
   </table>  
    </div>
<center>
    <a class="call" href="tel:<%= applyBean.getTelephone() %>">打电话</a>
   <a class="call" href="sms:<%= applyBean.getTelephone() %>">发短信</a>
</center>
    <div class="baseInform">
    <h3>详细介绍：</h3>
    &nbsp;&nbsp;&nbsp;&nbsp;<%= applyBean.getPersonalInfo() %>
    </div>
    
    <!-- end .content --></div>
    <!--正文结束 -->
     <!-- end .container --></div>
    
    <!--脚注开始 -->
 <!--  <div class="footer"> 
   <hr />
   <a href="javascript:void(0)" class="goodSpan" id="clickGood">
	<span >
  	需要放点什么吗
   </span>
    </a>
  </div> -->

  </body>
</html>
