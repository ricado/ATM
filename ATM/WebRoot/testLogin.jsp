<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*,com.atm.model.user.UserInfo" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	UserInfo user  = (UserInfo)session.getAttribute("user");
	if(user!=null){
		out.println("当前用户："+user.getUserId()+":"+user.getNickname());
	}else{
		out.println("未登录，请先登录：测试账号：1到6  密码：1<br/>");
	}
  	/* String mess = (String)request.getAttribute("mess");
  	if(mess==null){
  		mess = request.getParameter("message");
  		if(mess==null){
  			mess = "";
  		}
  	}
  	if(mess.equals("unLogin")){
  		out.println("未登录，请先登录：测试账号：1到6  密码：1<br/>");
  	} */

 %>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>测试登录</title>
<script src="<%= basePath %>js/jquery-1.11.3.min.js" type="text/javascript">	
</script>
<script type="text/javascript">
	$(function(){
		$("#login").click(function(){
			var userId = document.getElementById("userId").value;
			var password = document.getElementById("password").value;
			$.ajax({
				url:"<%= basePath %>user_login.action",
				type:"post",
				data:{  "userId":userId,
						"password":password
				},
				dataType:"json",
				beforeSend: loadFunction, //加载执行方法    
	            error: errFunction,  //错误执行方法    
	            success: succFunction //成功执行方法   
			})
			
			function loadFunction(){
			}
			function errFunction(){
			 	alert("加载错误");
			}
			function succFunction(json){
				if(json.length==0){
					alert("加载错误0");
				}else if(json[0].tip!="登录成功"){
					alert(json[0].tip);
				}else{
					alert(json[0].tip);
					if(window.location.pathname!="/testLogin.jsp"){
					 history.go(0);
					 }
					 else{
					 	window.location.href="<%= basePath %>index.jsp";
					 }
				}
			}
		})
		
		$("#exit").click(function(){
			$.ajax({
				url:"<%= basePath %>user_exit.action",
				dataType:"json",
				beforeSend: loadFunction, //加载执行方法    
	            error: errFunction,  //错误执行方法    
	            success: succFunction //成功执行方法   
			})
			
			function loadFunction(){
			}
			function errFunction(){
			 	alert("加载错误");
			}
			function succFunction(json){
				if(json[0].tip=="成功"){
					alert("注销成功");
					if(window.location.pathname!="/testLogin.jsp"){
					 	history.go(0);
					 }
					 else{
					 	window.location.href="<%= basePath %>testLogin.jsp";
					 }
				}else{
					alert(json[0].tip);
				}
			}
		})
		
	})
	
</script>

</head>
 
<body>

<form>
 id: <input type="text" name="userId" id="userId" value="2"/><br />
 密码：<input type="password" name="password" id="password" value="1"/><br />
 <input type="button" value="提交" id="login" />
</form>
 <input type="button" value="注销当前用户" id="exit" /><br/>
 <form action="<%= basePath %>atm_searchPeople.action" method="post">
 	<input type="text" name="id" id="id"/>
 	<input type="submit" value="搜索用户"/>
 </form>
 <form action="<%= basePath %>atm_searchEssay.action" method="post">
 	<input type="text" name="id" id="id"/>
 	<input type="submit" value="搜索帖子"/>
 </form>
  <form action="<%= basePath %>user_attendedPeople.action" method="post">
 	<input type="submit" value="关注的人"/>
 </form>
 <form action="<%= basePath %>user_attendedLabel.action" method="post">
 	<input type="submit" value="关注的标签"/>
 </form>
  <form action="<%= basePath %>reply_deleteReply.action" method="post">
 	 <input type="text" name="replyId" id="replyId"/>
 	<input type="submit" value="删除评论"/>
 </form>
  <form action="<%= basePath %>essay_clickGood.action" method="post">
 	 帖子ID:<input type="text" name="essayId" id="essayId" value="2"/>
 	 是否已点赞：<input type="text" name="clickGood" id="clickGood" value="false"/>
 	<input type="submit" value="点赞测试"/>
 </form>
 <form action="<%= basePath %>essay_collectEssay.action" method="post">
 	 帖子ID:<input type="text" name="essayId" id="essayId" value="2"/>
 	 是否已收藏：<input type="text" name="collect" id="collect" value="false"/>
 	<input type="submit" value="收藏测试"/>
 </form>
</body>
</html>