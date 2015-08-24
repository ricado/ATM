package com.atm.action.BBS;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.AtmDeal;
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
	
	 private String searchCondition;
	 private String id;
	 private int page;
	
	//mess:给用户的提示信息-----sendJson:装载mess------resultArray:装载sendJson及帖子
		String mess;
		JSONObject sendJson;
		JSONArray resultArray;
		
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
				String tip="成功";
				try{
					tip = (String) resultArray.getJSONObject(0).get("tip");
				}catch(JSONException e){
					
				}
				mess = tip;
			}
		}
		//发送结果
		private void send(String name){
			try {
				sendJson.put("tip", mess);
				sendJson.put(name,resultArray);
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
	
	//TODO 获取热门标签
	public void hotLabel(){
		log.debug("获取热门标签请求");
		init();
		try {
			log.debug("获取热门标签");
			String labels =  deal.getHotLabel();
			mess = "成功";
			sendJson.put("tip", mess);
			sendJson.put("label",labels);
			sendUtil.writeToClient(response,sendJson);	
		} catch (Exception e) {
			mess="获取帖子发生错误";
			log.error(mess, e);
			send("label");
		} 
	}
	
	
	//TODO 搜索用户
	public String searchPeople(){
		log.debug("搜索用户请求");
		init();
		
		String searchCondition = getSearchCondition();
		try{
			log.debug("搜索关键词："+searchCondition);
			resultArray = deal.searchPeople(searchCondition);
			check();
		}catch(Exception e){
			mess = "获取失败";
			log.error(mess,e);
		}
		if(mess.equals("成功"))
			mess = "成功_"+resultArray.length();
		send("searchPeople");
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
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
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
}

