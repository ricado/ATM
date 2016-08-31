package com.atm.action.BBS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.define.user.UserList;
import com.atm.model.user.UserInfo;
import com.atm.service.bbs.AtmDeal;
import com.atm.service.bbs.UserDeal;
import com.atm.service.user.UserService;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @TODO：
 * @fileName : com.atm.action.AtmAction.java
 * date | author | version |   
 * 2015年8月14日 | Jiong | 1.0 |
 */
public class AtmAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,ObjectInterface {
	Logger log = Logger.getLogger(getClass()); 
	private HttpServletRequest request;
	private HttpServletResponse response;
	AtmDeal deal = context.getBean("AtmDeal",AtmDeal.class);
	UserDeal userDeal;
	
	 private String id;//搜索关键字,或用户账号
	 private int page;
	 
	 //举报
	 private String reportReason; //举报原因	
	 private int aim = -1; //举报目标（0为论坛贴，1为招聘贴）
	 private int aimId = -1; //（帖子Id)
	 
	 //用户标签
	 private String tag;
	
	 //系别热门标签
	 private String dno;
	 private int rows = 9;
	 
	//mess:给用户的提示信息-----sendJson:装载mess------resultArray:装载sendJson及帖子
		String mess;
		JSONObject sendJson;
		JSONArray resultArray;
		
		//多个JSONArray时用list
/*		List<String> nameList;
		List<JSONArray> resultList;*/
		
		//初始化
		private void init(){
			mess = "获取失败";
			sendJson  = new JSONObject();
			resultArray = new JSONArray();
		}
		//检查有没有获取到帖子
		private void check(){
			if(resultArray == null){
				mess = "没有结果";
				resultArray = new JSONArray();//重新初始化
			}else{
				String tip="success";
				try{
					tip = (String) resultArray.getJSONObject(0).get("tip");
				}catch(JSONException e){
					
				}
				mess = tip;
			}
		}
		//发送结果
		private void send(String name){
			send(name,true);
		}
		private void send(String name,boolean haveArray){
			try {
				sendJson.put("tip", mess);
				if(haveArray){
					sendJson.put(name,resultArray);
				}
				//此时有多个JSONarray放进sendJson
				/*if(resultList!=null&&nameList!=null){
					for(int i=0;i<resultList.size()&&i<nameList.size();i++){
						sendJson.put(nameList.get(i), resultList.get(i));
					}
				}*/
			} catch (JSONException e) {
				sendUtil.writeToClient(response, errorJson);
				log.error(e);
			}
			sendUtil.writeToClient(response,sendJson);
		}
	
		//TODO 获取系部列表
	public String deptList(){
		log.debug("获取系部列表请求");
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="未登录";
				send("department");
				return null;
			}
			String scNo = user.getScNo();
			log.debug("获取系部列表");
			resultArray = deal.getDeptList(scNo);
			check();	
		} catch (Exception e) {
			mess="获取帖子发生错误";
			log.error(mess, e);
		} 
		send("department");
		return null;	
	}

	
	
	
	//TODO 搜索用户
	public String searchPeople(){
		log.debug("搜索用户请求");
		init();
		UserService userService = new UserService();
		try{
			log.debug("搜索关键词："+id);
			List<UserList> userLists = userService.findUser(id, page, 20);
			resultArray = jsonUtil.objectToArray(userLists);
			sendJson.put("user", resultArray);
			sendUtil.writeToClient(response, sendJson);
		}catch(Exception e){
			mess = "获取失败";
			log.error(mess,e);
			sendUtil.writeToClient(response, errorJson);
		}
	/*	if(mess.equals("成功"))
			mess = "成功_"+resultArray.length();*/
		//send("user");
		return null;
	}
	
	//TODO 进入详情
	public String personDetail(){
		log.debug("进入用户详情");
		init();
		UserService userService = new UserService();
		try{
			log.debug("进入用户："+id);
			if(id!=null&&id.length()!=0){
				UserBasicInfo basicInfo = userService.getUserBasicInfo(id);
				sendJson.put("data", jsonUtil.objectToJson(basicInfo));
				mess = "success";
			}else{
				mess = "id incorrect";
			}
		}catch(Exception e){
			mess = "获取失败";
			log.error(mess,e);
		}
		/*if(mess.equals("成功"))
			mess = "成功_"+resultArray.length();*/
		send("person",false);
		return null;
	}
	
	//TODO 搜索帖子
	public String searchEssay(){
		log.debug("搜索帖子请求");
		init();

		String id = getId();
		try{
			log.debug("搜索关键词："+id);
			resultArray = deal.searchEssayByKey(id,getPage());
			check();
			/*if(mess.equals("成功"))
				mess = "成功_"+resultArray.length();*/
		}catch(Exception e){
			mess = "获取失败";
			log.error(mess,e);
		}
		send("bbs");
		return null;
	}
	
	//TODO 举报
	public void report(){
		init();
		
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="未登录";
				send("report",false);
				return;
			}
			mess = deal.saveReport(user.getUserId(), aim, aimId, reportReason);
		} catch (Exception e) {
			mess="举报发生错误";
			log.error(mess, e);
		} 
		send("report",false);	
	}
	
	
	//TODO 获取热门标签
	public void hotLabel(){
		String name="tag";
		init();
		log.debug("获取热门标签请求");
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if(user==null){
				mess="未登录";
				send("tag",false);
				return;
			}
			 resultArray = deal.getAttendedLabelName(user.getUserId());
			 check();
			 name = "userTag";
			 //当用户没有关注标签时才返回热门标签
			 if(mess.equals("没有结果")){
				log.debug("获取热门标签");
				resultArray =  deal.getHotLabel();
				mess = "success";
				name = "hotTag";
			 }	
		} catch (Exception e) {
			mess="获取标签发生错误";
			log.error(mess, e);
		}
		send(name);
	}
	
	public void hotDeptLabel(){
		String name = "hotTag";
		init();
		log.debug("获取系别热门标签请求:id:"+id);
		try {
				resultArray =  deal.getHotLabelByDno(id, rows);
				mess = "success";
		} catch (Exception e) {
			mess="获取标签发生错误";
			log.error(mess, e);
		}
		send(name);
	}
	
	//用户添加自己的标签
	public void attTag(){
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="未登录";
				send("tag",false);
				return;
			}
			mess = deal.saveAttTag(user.getUserId(), tag);
		} catch (Exception e) {
			mess="发生错误";
			log.error(mess, e);
		} 
		send("tag",false);	
		
	}
	//用户删除自己的标签
	public void cancelTag(){
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="未登录";
				send("tag",false);
				return;
			}
			mess = deal.deleteAttTag(user.getUserId(), tag);
		} catch (Exception e) {
			mess="发生错误";
			log.error(mess, e);
		} 
		send("tag",false);	
	}
	//获取用户头像路径
	public void getUserHead(){
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="未登录";
				send("",false);
				return;
			}
			String userHead = deal.getUserHead(user.getUserId());
			if(userHead==null){
				mess = "用户不存在";
				send("",false);
			}else{
				sendJson.put("userHead",userHead);
				sendUtil.writeToClient(response,sendJson);
			}
		} catch (Exception e) {
			mess="发生错误";
			log.error(mess, e);
		} 
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getReportReason() {
		return reportReason;
	}
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	public int getAim() {
		return aim;
	}
	public void setAim(int aim) {
		this.aim = aim;
	}
	public int getAimId() {
		return aimId;
	}
	public void setAimId(int aimId) {
		this.aimId = aimId;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
}

