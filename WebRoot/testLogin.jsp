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
 
 <form action="<%= basePath %>essay_test.action" method="post">
 	用户id：<input type="text" name="id"/>
 	<input type="submit" value="测试"/>
 </form>
 
  <form action="<%= basePath %>atm_getUserHead.action" method="post">
 	<input type="submit" value="用户头像路径"/>
 </form>
 <form action="<%= basePath %>essay_publishedEssay.action" method="post">
 	起始位置：<input type="text" name="page"/>
 	<input type="submit" value="发过的帖"/>
 </form>
 <form action="<%= basePath %>atm_report.action" method="post">
 	aim：<input type="text" name="aim"/>
 	aimId：<input type="text" name="aimId"/>
 	reportReason：<input type="text" name="reportReason"/>
 	<input type="submit" value="举报测试"/>
 </form>
 <form action="<%= basePath %>atm_searchPeople.action" method="post">
 	<input type="text" name="id" id="id"/>
 	<input type="submit" value="搜索用户"/>
 </form>
  <form action="<%= basePath %>atm_hotLabel.action" method="post">
 	<input type="submit" value="获取标签"/>
 </form>
  <form action="<%= basePath %>atm_attTag.action" method="post">
 	<input type="text" name="tag" id="tag"/>
 	<input type="submit" value="添加用户标签"/>
 </form>
  <form action="<%= basePath %>atm_cancelTag.action" method="post">
 	<input type="text" name="tag" id="tag"/>
 	<input type="submit" value="取消用户关注标签"/>
 </form>
  <form action="<%= basePath %>essay_tagEssay.action" method="post">
 	<input type="text" name="tag" id="tag"/>
 	<input type="text" name="page"/>
 	<input type="submit" value="获取标签帖"/>
 </form>
 
 <form action="<%= basePath %>atm_personDetail.action" method="post">
 	<input type="text" name="id" id="id"/>
 	<input type="submit" value="进入用户"/>
 </form>
 
  <form action="<%= basePath %>essay_publishedEssay.action" method="post">
 	用户id：<input type="text" name="id" id="id"/>
 	索引：<input type ="text" name = "page"/>
 	<input type="submit" value="进入用户帖子"/>
 </form>
 
   <form action="<%= basePath %>essay_hotEssay.action" method="post">
 	索引：<input type ="text" name = "page"/>
 	<input type="submit" value="热门帖子测试"/>
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
 
 <form action="<%= basePath %>reply_publishReply.action" method="post">
 	 帖子编号：<input type="text" name="essayId"/>
 	 回复谁：<input type="text" name="repliedUserId" value="#"/>
 	 内容：<input type="text" name="repContent"/>
 	楼层：<input type="text" name="floorId" value="0"/>
 	<input type="submit" value="发布评论"/>
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
 
  <form action="<%= basePath %>essay_collectedEssay.action" method="post">
 	 page:<input type="text" name="page" id="page" value="1"/>
 	<input type="submit" value="收藏的贴测试"/>
 </form>
 
  <%-- <form action="<%= basePath %>essay_publishEssay.action" method="post"> --%>
 <form action="<%= basePath %>essay/publish.do" method="post" enctype="multipart/form-data">
 	 类型:<input type="text" name="type" id="type"/>
 	 标签：<input type="text" name="label" id="label"/>
 	  标题：<input type="text" name="title" id="title"/>
 	  系别：<input type="text" name="department" id="department"/>
 	    内容：<input type="text" name="content" id="content"/>
 	 文件：<input type="file" name="files"/>
 	  艾特的人，多个以*#分隔：<input type="text" name="aiteID"/>
 	<input type="file" name="files"/>
 	<input type="submit" value="发布帖子测试"/>
 </form>
 
  <form action="<%= basePath %>recuit_publish.action" method="post">
 	 招聘类型:<input type="text" name="reTypeName" />
 	 工作类型：<input type="text" name="woTypeName" />
 	  工作地点：<input type="text" name="workAddress" />
 	  薪资：<input type="text" name="salary"/>
 	    联系方式：<input type="text" name="telephone"/>
 	      内容：<input type="text" name="reContent"/>
 	      是否新发布(不为0则是修改操作)：<input type="text" name="reInfoId"/>
 	<input type="submit" value="发布招聘帖子测试"/>
 </form>
 
  <form action="<%= basePath %>apply_publish.action" method="post">
 	 招聘类型:<input type="text" name="reTypeName" />
 	 工作类型：<input type="text" name="woTypeName" />
 	  期望薪资：<input type="text" name="expectSalary"/>
 	    联系方式：<input type="text" name="telephone"/>
 	      详细介绍：<input type="text" name="personalInfo"/>
 	      是否新发布(不为0则是修改操作，不发或者发0则是发布操作)：<input type="text" name="apInfoId"/>
 	<input type="submit" value="发布求职帖子测试"/>
 </form>
 
   <form action="<%= basePath %>recuit_getRecuit.action" method="post">
 	 起始位置:<input type="text" name="page" />
 	 查询条件:<input type="text" name="tip" />
 	<input type="submit" value="获取招聘帖子测试"/>
 </form>
 
 <form action="<%= basePath %>recuit_delete.action" method="post">
 	 帖子ID:<input type="text" name="reInfoId" />
 	<input type="submit" value="删除招聘帖子测试"/>
 </form>
 
    <form action="<%= basePath %>apply_getApply.action" method="post">
 	 起始位置:<input type="text" name="page" />
 	 查询条件:<input type="text" name="tip" />
 	<input type="submit" value="获取求职帖子测试"/>
 </form>
 <br/>
 <form action="<%= basePath %>atm_deptList.action" method="post">
 	<input type="submit" value="系部列表测试"/>
 </form>
 <form action="<%= basePath %>essay_deptEssay.action" method="post">
 	 系别编号:<input type="text" name="id" />
 	  起始位置:<input type="text" name="page" />
 	<input type="submit" value="系部帖子测试"/>
 </form>
 <form action="<%= basePath %>essay_attendEssay.action" method="post">
 	  起始位置:<input type="text" name="page" />
 	<input type="submit" value="关注的帖子测试"/>
 </form>
 
  <form action="<%= basePath %>essay_content.action" method="post">
 	  帖子编号:<input type="text" name="essayId" />
 	<input type="submit" value="客户端帖子参数详情"/>
 </form>
 
 <form action="<%= basePath %>essay/publish.do" enctype="multipart/form-data" method="post">
 	<input type="file" name="files"/>
 	<input type="file" name="files"/>
 	<input type="submit" value="测试上传文件"/>
 </form>
 
  <form action="<%= basePath %>apply_getApply.action" method="post">
 	 起始位置:<input type="text" name="page" />
 	 查询条件:<input type="text" name="tip" />
 	<input type="submit" value="获取求职帖子测试"/>
 </form>
 
   <form action="<%= basePath %>atm_hotDeptLabel.action" method="post">
 	 系别号:<input type="text" name="id" />
 	<input type="submit" value="获取系别热门标签"/>
 </form>
 
    <form action="<%= basePath %>essay_repliedEssay.action" method="post">
 	 页数:<input type="text" name="page" />
 	  行数:<input type="text" name="rows" />
 	<input type="submit" value="获取用户评论过的帖子"/>
 </form>
 
     <form action="<%= basePath %>essay_deleteBatch.action" method="post">
 	  ids(以逗号分隔):<input type="text" value="1,2,3" name="ids" />
 	<input type="submit" value="批量删除帖子测试"/>
 </form>
 	 <form action="<%= basePath %>essay_collectBatch.action" method="post">
 	 ids:<input type="text" name="ids" id="ids" value="2,3,4"/>
 	 是否已收藏(若为true则是取消收藏操作)：<input type="text" name="collect" id="collect" value="false"/>
 	<input type="submit" value="收藏测试"/>
 </form>
</body>
</html>