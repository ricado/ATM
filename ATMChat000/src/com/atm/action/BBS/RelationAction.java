package com.atm.action.BBS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.RelationDeal;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @TODO��
 * @fileName : com.atm.action.RelationAction.java
 * date | author | version |   
 * 2015��8��11�� | Jiong | 1.0 |
 */
public class RelationAction   extends ActionSupport 
			implements ServletResponseAware,ServletRequestAware,ObjectInterface{
Logger log = Logger.getLogger(getClass());
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	RelationDeal deal = context.getBean("RelationDeal",RelationDeal.class);
	String mess;
	JSONObject sendJson;
	JSONArray sendArray;
	//��ʼ��
	private void init(){
		mess = "��ȡʧ��";
		sendJson  = new JSONObject();
		sendArray = new JSONArray();
	}
	//������ʾ������)��Ϣ
	private void sendError(){
		try {
			sendJson.put("tip",mess);
			sendArray.put(0,sendJson);
			sendUtil.writeToClient(response, sendArray);
		} catch (JSONException e) {
			sendUtil.writeToClient(response, errorArray);
			log.error(e);
		}
	}
	
	/*public String myAttend(){
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			sendError();
			return null;
		}
		try {
			sendArray = deal.getMyAttendPeople("1");
			sendUtil.writeToClient(response, sendArray);
		} catch (Exception e) {
			mess = "��ȡ����";
			sendError();
		} 
		return null;
	}*/

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

