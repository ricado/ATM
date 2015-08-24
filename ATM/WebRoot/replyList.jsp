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
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评论列表</title>

<script src="js/jquery-1.11.3.min.js" type="text/javascript">	
</script>
<!-- <script src="js/jquery.mobile-1.4.5.min.js" type="text/javascript">	
</script> -->
<script type="text/javascript">
	$(function(){
		init();
		getReply();//调用一次getReply方法
		//滚动事件
		 $(window).scroll(function() {
		 	$("#jump").delay(3000).hide(1000);//跳转块隐藏
		 	if($(document).height()<=$(window).height()){//文档高度不够时不执行方法
		 		return;
		 	}
             if ($(document).scrollTop() <= 0) {
                 $("#refresh").html("点击刷新页面").show();
             }else if ($(document).scrollTop()+70 >= $(document).height() - $(window).height()) {
                $("#moreReply").html("点击加载更多").show();
             }else{
             	 $("#refresh").hide();
             	 $("#moreReply").hide();
             }
         });
         
         //正在滚动时显示跳转块
          window.onscroll = function() {
          	$("#jumpDiv").hide();
          	$("#jump").show();
          }
          //点击跳转块的方法
          $("#jump").click(function(){
          	$("#jump").hide();
          	showJumpDiv();
          	$("#page").focus();
          })
          //点击确认按钮的方法
           $("#jumpButton").click(function(){
          		try{
          			inJump = true;//正在进行跳转事件
          			$("#jumpDiv").css("position","static").hide();//输入框隐藏
          			var index = (parseInt($("#page").val())-1) * 10;//索引 = (page-1)*10
          			if(index>=0==false){ //简单判断输入的格式
          				showMess("输入的参数格式不正确");
          				return;
          			}
          			//如果是跳转到已经加载的页面
          			if(index<=(replyIndex-1)){
          				document.getElementById("reply_"+index).scrollIntoView();//定位到元素
          				showMess("已跳转第"+parseInt($("#page").val())+"页");
          				return;
          			}
          			//定位到底部并加载新的div
          			document.getElementById("bottom").scrollIntoView();//定位到底部
          			beforeJumpIndex = replyIndex;//记录跳页前的索引，用来判断索引是否需复原
          			replyIndex = index;
          			getReply();
          		}catch(e){
          			showMess("查询错误");
          		}
          })
         //刷新事件
         $("#refresh").click(function(){
         	if(succ){
         		succ = false;//响应成功设为false，防止重复提交
         		$("#moreReply").hide();
         		freshReply();
         	}else{
         		 $("#refresh").html("正在等待响应...");
         	}
         });
         //加载更多事件
          $("#moreReply").click(function(){
         	 inJump = false;//不是跳页事件
          	if(succ){
         		succ = false;//响应成功设为false，防止重复提交
         		$(this).html("加载中。。");
         		getReply();
         	}else{
         		$("#moreReply").html("正在等待响应...");
         	}	 
         });
	})
	
	//初始化
	function init(){
		//变量初始化
		replyIndex=-1;//查找的起始位置（索引），-1为获取热门
		hotFloor = -1;//热门评论所在楼层
		essayId = $("#essayId").html(); //获取帖子编号	
		succ = false; //响应成功
		inJump = false;//在跳页中
		beforeJumpIndex = 0;//跳页前的索引
	}
	//调用客户端的输入框
	function getTextEditor(userId,userName,position){
		var floorId = parseInt(position.substring(0,position.indexOf("_")));
      	var top=$("#"+position).offset().top;//被点击的元素的位置
		//调用客户端回复框
		window.commentView.replyWho(userId,userName,floorId);
	}
	
	//接收客户端调用，增加一条评论
	function addAReply(repliedUserId,floorId,repContent){
			$.ajax({
				url:"<%= basePath %>reply_publishReply.action",
				data:{  
						"essayId":essayId,
						"repliedUserId":repliedUserId,
						"repContent":repContent,
						"floorId":floorId
				},
				dataType:"json",
             	error: errFunction,  //错误执行方法    
             	success: succFunction //成功执行方法   
			})
			function errFunction(){
			 	showMess("加载错误");
			}
			function succFunction(json){
				if(json.tip!="success"){
				showMess(json.tip);
				}else{
					if(floorId==0){
						getReply()
					}else{
						getInnerDiv(floorId);
					}
					showMess("发布成功");
				}
			}
		
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
	
	//重新加载
	function freshReply(){
		$("#replyList").empty();
		init();
		getReply();
	}
	//删除评论
	function deleteReply(replyId,position){
		var boo = confirm("确定删除这条评论?");
		if(boo){
			var pos = parseInt(position.substring(position.indexOf("_")+1,position.length));
			$.ajax({
				url:"<%= basePath %>reply_deleteReply.action",
				data:{  
						"replyId":replyId,
						"first":pos
				},
				dataType:"json",
             	error: errFunction,  //错误执行方法    
             	success: succFunction //成功执行方法   
			})
			function errFunction(){
			 	showMess("加载错误");
			}
			function succFunction(json){
				if(json.tip!="success"){
				showMess(json.tip);
				}else{
					var floorId = position.substring(0,position.indexOf("_"));
					if(pos==0){
						replyIndex--;//索引减一
						//删除楼层
						$("#floor_"+floorId).remove();
						if(replyIndex==0){
							$("#tipDiv").html("当前帖子还没有任何评论哦，快来抢占沙发把~！").show();
						}
					}else{
						var first = parseInt($("#inner_"+floorId).html());
						first--;
						$("#inner_"+floorId).html(first);
						$("#"+position).remove();
					}
					showMess("删除成功");
				}
			}
		}
	}
	
	//显示提示信息
	function showMess(mess){
			var screenWidth = $(window).width();
			$("#message").html(mess)
			.css("left",(screenWidth-$("#message").width())/2+"px")
			.show(300).delay(3000).hide(300); 
	}
	
	//显示跳转页面输入框
	function showJumpDiv(){
		var screenWidth = $(window).width();
		var screenHeight = $(window).height();
		$("#jumpDiv")
		.css({"position":"fixed","left":(screenWidth-$("#jumpDiv").width())/2+"px","top":screenHeight/2-$("#jumpDiv").height()*2}).show();
	}
	//跳转个人界面
	function personPage(u){//跳转用户个人界面
		if(checkLogin()==false){
			return;
		}
		 document.write("此处应跳转到"+u);
	}
	//点赞方法
	function clickGood(replyId,floorId){
		var clickGood = $("#clickGood_"+floorId).html();//字符串，判断是否点赞
		var clickGoodNum = parseInt($("#clickGoodNumHide_"+floorId).html());
			$.ajax({
				url:"<%= basePath %>reply_clickGood.action",
				data:{  "replyId":replyId,
						"clickGood":clickGood//根据是否点赞了来决定执行取消还是增加点赞
				},
				dataType:"json",
             	error: errFunction,  //错误执行方法    
             	success: succFunction //成功执行方法   
			})
			function errFunction(){
			 	showMess("加载错误");
			}
			function succFunction(json){
				if(json.length==0){
					showMess("加载错误");
				}else if(json[0].tip=="未登录"){
					showMess("未登录，请下载U.S  APP");
				}else{
					//替换点赞按钮
					var old = $("#good_"+floorId).html();

					if(clickGood=="true"){
						var newOne = old.replace("haveClickGood.png","notClickGood.png");
						$("#good_"+floorId).html(newOne);
						
						if(clickGoodNum>0){
							clickGoodNum--;
						}
						$("#clickGood_"+floorId).html(false);//修改点赞判断标志
						
					}else{
						var newOne = old.replace("notClickGood.png","haveClickGood.png");
						$("#good_"+floorId).html(newOne);
						clickGoodNum++;
						$("#clickGood_"+floorId).html(true);
					}
					
					//替换点赞数，更新点赞数标志
					$("#clickGoodNum_"+floorId).html("("+clickGoodNum+")");
					$("#clickGoodNumHide_"+floorId).html(clickGoodNum);
					
					
				}
			}
	}

	//判断文字是否超过100字，超过部分隐藏
	function checkContent(content,position){
		//文本总长度超过100，进入下一步判断
		if(content.length>100){
			var pos = spiltContent(content,100);//获取实际文字100的索引值
			if(content.length<=pos){//总长度小于或等于索引值，即字数不大于100，不必截取
				return content;
			}
			var show = content.substring(0,pos);//截取展示部分
			var hide = content.substring(pos);//截取隐藏部分
			//生成元素
			var showSome = "<span id='show_"+position+"' class='text'>"+show+"</span>";
			var showMore = "<a id='sM_"+position+"' style='font-size:0.9em;' href='javascript:void(0)' onclick=showContent('"+position+"') >  查看全部▼</a>"
			var hideSome = "<span id='hide_"+position+"' class='text' style='display:none;'>"+hide+"</span>";
			var hideMore = "<a id='hM_"+position+"' style='display:none;font-size:0.9em;' href='javascript:void(0)' onclick=hideContent('"+position+"') >  收起部分▲</a>"
			return showSome+showMore+hideSome+hideMore;
		}else{
			return content;
		}
	}
	
	//改进方法：将<标签>及转义字符&nbsp;&lt;等等视为一个字符，返回传入的截取字数的索引值
	function spiltContent(content,maxNum){
		var num = 0;//字数
		var pos = 0;//返回索引
		var index = 0;//标记索引,记录开始符"<"的位置
		var lastIndex = 0;//标记索引，记录结束符(">"或";"的位置)
		var anotherIndex = 0;//标记索引，记录符号"&"的位置，并与index比较
		while(true){
			//记录开始符
			index = content.indexOf("<");
			anotherIndex = content.indexOf("&");
			//若已没有特殊标签则将当前字数补全后返回
			if(anotherIndex==-1&&index==-1){
				if(num<maxNum){
					pos = pos+maxNum-num;
				}
				break;
			}
			if(index==-1||anotherIndex!=-1&&anotherIndex<index){//"&"符号在前
				index = anotherIndex;
				lastIndex = content.indexOf(";");
			}else{//"<"符号在前
				lastIndex = content.indexOf(">");
			}
			num = num+index+1;//字数 = 字数+截至开始符的长度+1（标签当作一个字符）
			pos = pos+lastIndex+1;//索引 = 索引+截至结束符的长度（lastIndex+1)
			if(num>maxNum){
				/*当字数超过设定的最大字数时，将索引值减去当前标签的长度，再减去超过的字数
				* num-1是因为此时已经将标签减去，所以应把标签所占的字数1减去
				*/
				return pos-(lastIndex-index+1)-(num-1-maxNum);
			}
			//截取掉已作数的字符，剩下的进入下一次运算
			content = content.substring(lastIndex+1);
		}
		return pos;
	}
	
	//显示全部正文
	function showContent(position){
		$("#sM_"+position).hide();
		var hideStr = $("#hide_"+position).html();
		$("#show_"+position).append(hideStr);
		$("#hM_"+position).show();
	}
	//隐藏部分正文
	function hideContent(position){
		$("#hM_"+position).hide();//隐藏收起部分按钮
		var showStr = $("#show_"+position).html();//获取全部正文
		var pos = spiltContent(showStr,100);//获取截取的索引
		var show = showStr.substring(0,pos);//截取即将展示的正文
		if($("#"+position).height()>$(window).height()/1.5){//当前块高度较大时
			document.getElementById(position).scrollIntoView();//定位到收起的块
		}
		$("#show_"+position).html(show);
		$("#sM_"+position).show();//显示查看全部按钮
	}
	
	
	
	//获取评论
	function getReply(){
		$.ajax({
				url:"<%= basePath %>reply_replyList.action",
				data:{
					"essayId":essayId,
					"replyIndex":replyIndex//索引
				},
				dataType:"json",
				beforeSend: loadFunction, //加载执行方法    
             	error: errFunction,  //错误执行方法    
             	success: succFunction //成功执行方法   
			})
			function loadFunction(){
				if($(document).scrollTop()==0){
				  $("#refresh").html("正在加载...").show();  
				 }
			}
			function errFunction(){
			 	showMess("加载错误0");
			}
			function succFunction(json){
				succ = true;
				$("#refresh").hide();
				if(json[0].num!=null){//没有更多评论
					if(json[0].num==0){//replyIndex==-1
						$("#tipDiv").html("当前帖子还没有任何评论哦，快来抢占沙发把~！").show();
					}else{
						$("#moreReply").hide();
						if(inJump==true){
							showMess("客官，总共只有"+Math.ceil((json[0].num)/10)+"页哦");
						}else{
							showMess("没有更多评论了");
						}
					}
				}else{
					if(json[0].tip!=null){
						showMess(json[0].tip);
					}else{
						if(inJump==true){
							showMess("跳转到第"+json[0].floorId+"楼");
						}
						$.each(json,function(index,item){
						var replyId = json[index].replyId;
						var floorId = json[index].floorId;
						var repContent = json[index].repContent;
						var repTime = json[index].repTime;
						var headImagePath = json[index].headImagePath;
						var clickGoodNum = json[index].clickGoodNum;
						var userName = json[index].userName;
						var userId = json[index].userId;
						var repliedUserId = json[index].repliedUserId;
						var repliedName = json[index].repliedName;
						var clickGood = json[index].clickGood;
						repContent = checkContent(repContent,floorId+"_0");
						var realHot = 0;
						if(replyIndex==-1){
							if(clickGoodNum>3){//当点赞数到达一定数字才认定为是热门评论
								realHot = 1;
								var aDiv = getADiv(userId,headImagePath,userName,repTime,repContent,floorId,clickGoodNum,clickGood,replyId);
								$("#replyList").append(aDiv);//将此评论显示
								$("#hot_"+floorId).html("<img src='image/fire.png' class='hotIcon'/>");//显示图标
								getInnerDiv(floorId);//获取楼中楼回复
							}
						}else if(floorId != hotFloor){//若是热门评论的楼层（已显示）则不显示
							var aDiv = getADiv(userId,headImagePath,userName,repTime,repContent,floorId,clickGoodNum,clickGood,replyId);
							$("#replyList").append(aDiv);//将此评论显示
							getInnerDiv(floorId);//获取楼中楼回复
						}
						replyIndex++; //索引+1
						if(replyIndex == 0){//说明刚刚获取了热门评论，再进行一次普通获取
							if(realHot==1){
								hotFloor = floorId;//若点赞数最多的帖子被判定为最热评论，则更新最热帖子所在楼层
							}
						getReply();
						}
						})
					}
				}
				//如果进行的是跳页，需要将索引倒退回去(跳的是下一页则不需要)
				if(inJump==true&&beforeJumpIndex+json.length!=replyIndex){
					replyIndex = beforeJumpIndex;//复原
				}
			}	
	}
	//获取一个楼层
	function getADiv(userId,headImagePath,userName,repTime,repContent,floorId,clickGoodNum,clickGood,replyId){
				var position = floorId+"_0";
				var goodSource="";
				if(clickGood){
					goodSource = "haveClickGood.png";
				}else{
					goodSource = "notClickGood.png";
				}
				var trashButton = "";
				if("<%= me %>"==userId){
					trashButton = '<a href="javascript:void(0)" onclick=deleteReply('+replyId+',"'+position+'")>'+
									'<img src="image/trash.png" class="trashButton"/>'+
									'</a>';
				}
				 var str = '<div id="reply_'+replyIndex+'"></div>'+ 
	'<div style="margin:0.5em;" id="floor_'+floorId+'">'+ 
				 '<div class="clickGood">'+//点赞按钮块
				  '<a href="javascript:void(0)" onclick="clickGood('+replyId+','+floorId+')" >'+
				 '<span class="goodImage" id="good_'+floorId+'"><img src="image/'+goodSource+'" width="20px" height="20px"/></span>'+
				 '<div color="#CCC" id="clickGoodNum_'+floorId+'">('+ clickGoodNum +')</div>'+   <%-- 显示当前点赞数--%>
				  '<span style="display:none" id="clickGoodNumHide_'+floorId+'">'+clickGoodNum+'</span>'+ <%-- 当前点赞数--%>
				 '<span style="display:none" id="clickGood_'+floorId+'">'+clickGood+'</span>'+ <%-- 为立即修改页面的点赞提供 当前是否点赞标志 --%>
				 '</a>'+
				 '</div>'+
			 '<div><table>'+
			 '<tr><td valign="top">'+
			   '<div >'+//用户头像及楼层数
			   		'<a href="javascript:void(0)"  onclick=personPage("'+ userId +'");return false>'+
			   		'<div style=" margin-right:0.5em;"><table><tr><td><span class="headImage">'+
					'<img src="'+ headImagePath +'" alt="用户头像"  width="100%" height="100%"/>'+
					'</span></td></tr>'+
					'<tr><td><div class="floorID" >'+floorId+'楼<div id="hot_'+floorId+'"></div></div></td></tr>'+
					'</table></div>'+
				'</a></div>'+
			'</td><td>'+
				
				 '<div>'+//右边评论及楼中楼
					  '<div>'+//昵称及回复时间
							'<table>'+
							'<tr><td>'+
								'<a href="javascript:void(0)"  onclick=personPage("'+ userId +'");return false>'+
								'<span class="nickname">'+ userName +'</span></a>'+
							'</td></tr>'+
						   '<tr><td>'+
								'<span class="repTime">'+ repTime +trashButton+'</span>'+
						   '</td></tr>'+
						' </table>'+
					  '</div>'+
				  	<%-- 回复内容  --%>
				  	//floorId_0作为回复时回复框出现位置的位置的块名
				   '<div id="'+position+'">'+
					  '<a href="javascript:void(0)" onclick=getTextEditor("'+userId+'","'+userName+'","'+floorId+'_0'+'")><br/>'+
					  '<div class="text" >'+repContent+'</div>'+
					  '</a>'+
					  //楼中楼回复块，由方法添加
					  '<div class="text" id="'+floorId +'">'+
					 	 '<div id="innerTip"></div>'+
					  '</div>'+
					  '<span style="display:none;" id="inner_'+floorId+'">1</span>'+   <%--楼中回复 当前显示层数（楼中楼层数） --%>
				   '</div>'+
					  //点击加载更多
					  '<a id="innerMore_'+floorId+'" class="more" href="javascript:void(0)" onclick="getInnerDiv('+floorId+')" ></a>'+
				'</div>'+//评论及楼中楼结束
			 '</div>'+
			'</td></tr></table>'+
			'<hr color="#BBB" size="1"/></div>'+
		'</div>'
			
   			 return str;
			}
	//获取楼层的楼中楼回复
	function getInnerDiv(floorId){
		var first = parseInt($("#inner_"+floorId).html());
		$.ajax({
				url:"<%= basePath %>reply_replyInnerList.action",
				data:{ "essayId":essayId,
						"floorId":floorId,
						"first":first
				},
				dataType:"json",
				beforeSend: loadFunction, //加载执行方法    
             	error: errFunction,  //错误执行方法    
             	success: succFunction //成功执行方法   
			})
			function loadFunction(){
				$("#innerTip").html("正在加载..."); 
			}
			function errFunction(xhr, type, exception){
			 	alert(xhr.responseText, "Failed");
			}
			function succFunction(json){
				$("#innerTip").hide(); 
				if(json.length==0){
					$("#innerMore_"+floorId).html("没有更多回复了");
					$("#innerMore_"+floorId).hide(2000);
				}else{
					if(json[0].tip!=null){
						ADiv=json[0].tip;
					}else{
						$.each(json,function(index,item){
						var replyId = json[index].replyId;
						//var floorId = json[index].floorId;
						var repContent = json[index].repContent;
						var repTime = json[index].repTime;
						var headImagePath = json[index].headImagePath;
						var clickGoodNum = json[index].clickGoodNum;
						var userName = json[index].userName;
						var userId = json[index].userId;
						var repliedUserId = json[index].repliedUserId;
						var repliedName = json[index].repliedName;
						repContent = checkContent(repContent,floorId+"_"+first);
						var position = floorId+"_"+first;
						if(first == 1){
							$("#"+floorId).append('<hr color="#CCC" size="1"/>');
						}
						var trashButton = "";
						if("<%= me %>"==userId){
						trashButton = '<a href="javascript:void(0)" onclick=deleteReply('+replyId+',"'+position+'")>'+
									'<img src="image/trash.png" class="trashButton"/>'+
									'</a>';
				}
						//floorId+'_'+first作为回复时回复框出现位置的位置的块名
						ADiv = '<div class="innerReply" id="'+position+'"><a href="javascript:void(0)"  onclick=personPage("'+ userId +'")>'+userName+':</a>&nbsp;'+
								'<a href="javascript:void(0)" onclick=getTextEditor("'+userId+'","'+userName+'","'+floorId+'_'+first+'")>'+
								'<span class="innerReplyName">回复&nbsp;'+repliedName+'</span>&nbsp;&nbsp;'+
								'<span class="innerReplyContent">'+repContent+'&nbsp;&nbsp;</span>'+
								'</a>'+
								'<span class="innerRepTime">'+repTime+trashButton+'</span><div>';
						$("#"+floorId).append(ADiv);
						
						
						//first为2说明已显示第二条楼中楼
						if(json.length==5||first==2){
							$("#innerMore_"+floorId).html("点击加载更多");
							$("#innerMore_"+floorId).show();
						}else{
							$("#innerMore_"+floorId).html("没有更多回复了");
							$("#innerMore_"+floorId).hide(4000);
						}
						
						first++;
						$("#inner_"+floorId).html(first);
						})
					}
				}
			}
	}
	 
</script>
<style type="text/css">
<!--
html,
body {
	font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
	background-color: #FFF;
	margin: 0;
	padding: 0;
	color: #000;
	height:100%;
}
	a{
		text-decoration:none;
		font-weight:normal;
		font-color:#999;
		 -webkit-tap-highlight-color: rgba(255,0,0,5);/* 去掉点击效果 */
	}
	.clickGood a{
		 -webkit-tap-highlight-color: rgba(255,0,0,0);
	}
	/*头像样式*/
	.headImage{
		float:left; 
		width:35px;
		height:35px; 
		border-radius:10%;
		overflow:hidden; 
	}
	/*昵称样式*/
	.nickname{
		overflow:hidden; 
		font-size:1em;
		text-overflow:ellipsis; 
		display:block;
		font-weight:300; 
		max-height:2.5em;
		color:#333;
	}
	/*回复时间样式*/
	.repTime{
		font-size:0.8em;
		color:#A5A5A5; 
		overflow:hidden;
		text-overflow:ellipsis; 
		display:block;
	}
	/*楼中楼回复时间样式*/
	.innerRepTime{
		font-size:0.8em;
		color:#A5A5A5;
		display:inline-block;
	}
	/*楼层计数样式 居中，在头像下方*/
	.floorID{
		font-size:0.9em;
		color:#999;
		margin-top:0.5em;
		text-align:center;
	}
	/*点赞块样式*/
	.clickGood{
		font-size:1em;
		color:#333;
		min-width:3em;
		position:absolute;
		right:0;
		margin-top:0.5em;
	}
	/* 楼中楼样式*/
	.innerReply{
		font-size:0.9em;
		color:#333;
	}
	/* 回复内容样式*/
	.text{
	color:#000;
	margin-right:0.5em; 
	letter-spacing:0.1em;
	font-weight:500;
	}
	div,
	span{
		font-weight:normal;
	}
	.innerReplyName{
		color:#999;
	}
	.innerReplyContent{
		color:#111;
	}
	/*点赞按钮保持父元素右边距一定的位置*/
	.goodImage{
		float:right;
		margin-right:1.6em;
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
	/* “点击获取更多”连接 */
	.more{
		font-size:0.8em;
		display:none;
		float:right;
	}
	/* 删除评论按钮 */
	.trashButton{
		width:1.4em;
		height:1.4em;
		margin-left:1.5em;
	}
	/* 热门帖图样 */
	.hotIcon{
		margin-top:0.5em;
	}
	/* 表情 */
	.expression{
		width:1.7em;
		height:1.7em;
		margin-bottom:-0.4em;
	}
/* ~~ 此容器包含所有其它 div，并依百分比设定其宽度 ~~ */
.container {
	width: 100%;
	max-width: 100%;/* 可能需要最大宽度，以防止此布局在大型显示器上过宽。这将使行长度更便于阅读。IE6 不遵循此声明。 */
	min-width: 100%;/* 可能需要最小宽度，以防止此布局过窄。这将使侧面列中的行长度更便于阅读。IE6 不遵循此声明。 */
	background-color:#FFF;
	/* margin: 0 auto; */ /* 侧边的自动值与宽度结合使用，可以将布局居中对齐。如果将 .container 宽度设置为 100%，则不需要此设置。 */
	height:auto !important;
	min-height:100%;
	height::100%;
}
#replyList{
	width:100%;
}
.refreshButton {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #74ad5a), color-stop(1, #68a54b));
	background:-moz-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background:-webkit-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background:-o-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background:-ms-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background:linear-gradient(to bottom, #74ad5a 5%, #68a54b 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#74ad5a', endColorstr='#68a54b',GradientType=0);
	background-color:#74ad5a;
	border:1px solid #3b6e22;
	display:none;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:1em;
	font-weight:bold;
	padding:4px 0px;
	text-align:center;
}

.jump{
	display:none;
	position:fixed;
	right:0.2em;
	bottom:5em;
	background-color:#EEE;
	border:1px solid red;
	padding:0.3em 0.3em;
	z-index:99;
}
#jumpDiv{
	display:none;
	background-color:#EEE;
	padding:0.5em 0.2em;
	z-index:199;
	border:1px solid red;
}

/* 使文字不会将网页撑开 */
table{table-layout: fixed;}
td{word-break: break-all; word-wrap:break-word;}


/* .ui-loader-default{ display:none}
.ui-mobile-viewport{ border:none;}
.ui-page {padding: 0; margin: 0; outline: 0}
.ui-content{padding: 0; margin: 0; outline: 0}
 */
-->
</style>
</head>

<body>
<div>
	<div id="essayId" style="display:none"><%= request.getParameter("essayId") %></div>
	<div id="tipDiv" style="postion:absolute;top:0.5em"></div>
	<div id="atDiv"></div>
	<div class="container" id="container">
		<div id="jump" class="jump">跳页</div>
		<div class="refreshButton" id="refresh">点击刷新页面</div>
		<div id="replyList"></div>
		<!-- <a href="javascript:void(0)" onclick="addAReply('#','4','我我我#(exp21)哈哈#(exp21)#(exp21)哈哈#(exp23)#(exp21)哈哈#(exp25)#(exp21)哈哈#(exp21)#(exp21)哈哈#(exp21)#(exp21)哈哈#(exp21)#(exp21)哈哈#(exp21)#(exp21)啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦#(exp21)哈哈#(exp21)啦啦啦啦啦啦啦啦啦啦#(exp21)哈哈#(exp21)啦啦啦啦啦啦啦1#(exp21)哈哈2#(exp21)啦啦啦啦啦啦啦3#(exp21)哈哈456#(exp21)#(exp21)#(exp21)1啦啦啦啦啦啦啦啦啦啦啦7#(exp21)8#(exp21)啦啦啦啦啦啦啦啦啦啦啦啦啦')">测试回复</a> -->
		<div class="refreshButton" id="moreReply">点击加载更多</div>
	</div>
	<div id="bottom"></div>
	<div id="jumpDiv">第<input type="text" id="page" size="11">页<input type="button" value="跳转" id="jumpButton" style="width:5em;height:2 em;"/></div>
	<div id="message"></div>
</div>

</body>
</html>