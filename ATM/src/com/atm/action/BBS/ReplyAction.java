package com.atm.action.BBS;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.ReplyDeal;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @TODO：
 * @fileName : com.atm.action.ReplyAction.java
 * date | author | version |   
 * 2015年8月11日 | Jiong | 1.0 |
 */
public class ReplyAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,ObjectInterface{
Logger log = Logger.getLogger(getClass());
	
	private int essayId;
	private int first = 0;
	private int floorId;
	private int replyIndex;//查找的起始位置，若为-1则是获取热门的一条评论
	private int replyId;
	private boolean clickGood;
	
	private String repliedUserId;//被回复的人的账号
	private String repContent;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	ReplyDeal deal = context.getBean("ReplyDeal",ReplyDeal.class);
	
	String mess;
	JSONObject sendJson;
	JSONArray sendArray;
	//初始化
	private void init(){
		mess = "获取失败";
		sendJson  = new JSONObject();
		sendArray = new JSONArray();
	}
	
	private void sendTip(){
		sendTip(true);
	}
	//发送提示消息:true为发送JSONArray ,false为发送JSONObject
	private void sendTip(boolean method){
		try {
			sendJson.put("tip",mess);
			sendArray.put(0,sendJson);
			if(method)
				sendUtil.writeToClient(response, sendArray);
			else
				sendUtil.writeToClient(response, sendJson);
		} catch (JSONException e) {
			if(method)
				sendUtil.writeToClient(response, errorArray);
			else
				sendUtil.writeToClient(response, errorJson);
			log.error(e);
		}
	}
	public void replyList(){
		log.debug("获取评论列表请求");
		init();
		try{
			log.debug(">>>>>>>>>正在获取评论的帖子:"+getEssayId());
			sendArray = deal.getReply(request,getEssayId(),getReplyIndex());
			sendUtil.writeToClient(response, sendArray);
		}catch (Exception e) {
			mess = "获取错误";
			log.debug(mess, e);
			sendTip();
		}
	}
	
	
	public void replyInnerList(){
		log.debug("获取评论楼中楼请求");
		init();
		try {
			log.debug(">>>>>>>>>正在获取评论的帖子:"+getEssayId());
			sendArray = deal.getInnerReply(getEssayId(),getFloorId(),getFirst());
			sendUtil.writeToClient(response, sendArray);
		} catch (Exception e) {
			mess = "获取错误";
			log.debug(mess, e);
			sendTip();
		} 
		
	}
	
	//TODO 点赞方法
	public void clickGood(){
		log.debug("获取点赞请求");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "未登录";
			sendTip();
		}else{
			try {
				mess = deal.saveOrDeleteClickGood(request, getReplyId(), isClickGood(),user.getUserId());
				sendTip();
			} catch (Exception e) {
				mess = "获取错误";
				log.debug(mess, e);
				sendTip();
			} 
		}
	}
	
	//TODO 发布一条评论
	public void publishReply(){
		log.debug("获取发布评论请求");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "未登录";
			sendTip(false);
			return;
		}
		try {
			String userId = user.getUserId();
			boolean boo = true;
			if(floorId!=0){//楼层为不为0说明回复的是楼中楼，需判断回复的楼层是否存在
			  boo = deal.haveFloor(floorId);
			}
			mess = deal.saveAReply(request, essayId, userId, repliedUserId, repContent, floorId,boo);
			sendTip(false);
		} catch (Exception e) {
			mess = "获取错误";
			log.debug(mess, e);
			sendTip(false);
		} 
		
		return;
	}
	
	//TODO 删除一条评论
	public String deleteReply(){
		log.debug("获取删除评论请求"+getReplyId());
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "未登录";
			sendTip(false);
			return null;
		}
		try {
			int position = getFirst();//删除评论所在的位置
			mess = deal.deleteAReply(user.getUserId(),getReplyId(),position);
			log.debug("删除结束"+mess);
			sendTip(false);
		} catch (Exception e) {
			mess = "获取错误";
			log.debug(mess, e);
			sendTip(false);
		} 
		
		return null;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request = arg0;
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response = arg0;	
	}
	public int getEssayId() {
		return essayId;
	}
	public void setEssayId(int essayId) {
		this.essayId = essayId;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getFloorId() {
		return floorId;
	}
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	public boolean isClickGood() {
		return clickGood;
	}
	public void setClickGood(boolean clickGood) {
		this.clickGood = clickGood;
	}
	public int getReplyIndex() {
		return replyIndex;
	}
	public void setReplyIndex(int replyIndex) {
		this.replyIndex = replyIndex;
	}

	public String getRepliedUserId() {
		return repliedUserId;
	}

	public void setRepliedUserId(String repliedUserId) {
		this.repliedUserId = repliedUserId;
	}

	public String getRepContent() {
		return repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
	}
}

