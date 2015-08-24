package com.atm.action.BBS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @TODO：
 * @fileName : com.atm.action.SearchAction.java
 * date | author | version |   
 * 2015年8月16日 | Jiong | 1.0 |
 */
public class SearchAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,ObjectInterface {
	Logger log = Logger.getLogger(getClass());
	private HttpServletRequest request;
	private HttpServletResponse response;
	
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
}

