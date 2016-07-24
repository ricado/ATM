<%@ page language="java" import="java.util.*,com.atm.model.define.bbs.ClickGoodView,com.atm.model.user.UserInfo" pageEncoding="UTF-8"%>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <%
	   List list = (List)request.getAttribute("clickGoodPeople");
	%>
	<%! int j=0; 
		ClickGoodView clickGood;
	%>
    <title>点赞人列表</title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script src="js/jquery-1.11.3.min.js" type="text/javascript">	
</script>
	<script type="text/javascript">
		function personPage(u){
			if(checkLogin()==false){
				//return;
			}
		   window.demo1.toPersonalMsg(u);
		}
		
		var first = 0;//计数器，指明下次获取的位置
		
		
		function getMore(essayId,index){
		if(first == 0){
			first = index;
		}
			$.ajax({
				url:"<%= basePath %>moreClickGood/"+essayId+"/"+first+".html",
				dataType:"json",
				beforeSend: loadFunction, //加载执行方法    
             	error: errFunction,  //错误执行方法    
             	success: succFunction //成功执行方法   
			})
			function loadFunction(){
				/* $("#tipDiv").html("正在加载..."); */
			}
			function errFunction(){
			 	alert("加载错误");
			}
			function succFunction(json){
				$("#tipDiv").html("");
				if(json.length==0){
					$("#tipDiv").html("没有更多记录了");
					$("#getM").html("");
				}else{
					$("#getM").html("点击查看更多>");
					$.each(json,function(index,item){
					var userId = json[index].userId;
					var headImagePath = json[index].headImagePath;
					var clickGoodTime = json[index].clickGoodTime;
					var nickname = json[index].nickname;
					var aDiv = getADiv(userId,headImagePath,nickname,clickGoodTime);
					$("#list").append(aDiv);
					first++;
					})
				}
			}
			
		}
			
	 	//检测登陆状态
		function checkLogin(){
			if("<%= me %>".length!=0){
				return true;
			}else{
				showMess("未登录~");
				return false;
			}
		}
		//显示提示信息
		function showMess(mess){
				var screenWidth = $(window).width();
				$("#message").html(mess)
				.css("left",(screenWidth-$("#message").width())/2+"px")
				.show(300).delay(3000).hide(300); 
		}
		
			function getADiv(userId,headImagePath,nickname,clickGoodTime){
				 var str = '<div style="margin:0.5em;">'+ 
   '<a href="javascript:void(0)"  onclick=personPage("'+ userId +'")>'+
   '<span class="headImage">'+
        '<img src="'+ headImagePath +'" alt="用户头像"  width="100%" height="100%"/>'+
    '</span>'+
    '<span>'+
    	'<table>'+
            '<tr><td>'+
				'<span class="nickname">'+ nickname +'</span>'+
            '</td></tr>'+
            '<tr><td></td></tr>'+
           '<tr><td>'+
		   		'<span class="clickGoodTime">'+ clickGoodTime +'</span>'+
           '</td></tr>'+
        ' </table>'+
      '</span>'+
     '<hr color="#CCC" size="1"/>'+
      '</a>'+
    '</div>'
    return str;
			}
	</script>
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
	a{
		text-decoration:none;
	}
	.headImage{
		float:left; 
		width:50px;
		height:50px; 
		border-radius:10%;
		overflow:hidden; 
		margin-left:0.3em; 
		margin-right:1em;
	}
	.nickname{
		overflow:hidden; 
		font-size:1em;
		text-overflow:ellipsis; 
		display:block;
		font-weight:300; 
		max-height:2.5em;
	}
	.clickGoodTime{
		font-size:0.8em;
		color:#A5A5A5; 
		overflow:hidden;
		text-overflow:ellipsis; 
		display:block;
	}
	.moreA{
		text-align:right;
		padding:0.6em;
		text-decoration:none;
	}
	.footer { 
	position: relative; 
	/*margin-top: -40px;/*等于footer的高度*/ 
	height: 1.5em; 
	clear:both; 
	text-align:right;
	}
	.list{
		padding-bottom: 40px;/*高度等于footer的高度*/ 
	}
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
	</style>
  </head>
  
  <body>
  <!--头部 -->
  <div>
 <div id="list" >
  <%
     if(list.size()==0){
     	out.println("暂时还没人点赞");
     }
	 else{
	 	for(int i=0; i<list.size(); i++){
	 		clickGood = (ClickGoodView)list.get(i);
   %>
   <div style="margin:0.5em;"> 
   <a href="#" onclick="personPage('<%= clickGood.getUserId() %>');return false">
   <span class="headImage">
        <img src="<%= clickGood.getHeadImagePath() %>" alt="用户头像"  width="100%" height="100%"/>
    </span>
    <span>
    	<table>
            <tr><td>
				<span class="nickname"><%= clickGood.getNickname() %></span>
            </td></tr>
            <tr><td></td></tr>
           <tr><td>
		   		<span class="clickGoodTime"><%= clickGood.getClickGoodTime() %></span>
           </td></tr>
         </table>
      </span>
     <hr color="#CCC" size="1"/>
      </a>
    </div>
    <%
   	 }
    j = list.size();//计数器设置为当前已加载的记录数（仅首次载入页面使用）
    }
     %>
   </div>
   </div>
   <div class="footer">
   	 <span id="tipDiv" style="margin:1em;"></span>
     <span class="moreA"><a href="#" onclick="getMore(<%= clickGood.getEssayId() %>,<%= j %>);return false" id=getM >点击查看更多></a></span>
    </div>
     <div id="message"></div>
  </body>
</html>
