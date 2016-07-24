package com.atm.action;

import java.util.List;

import com.atm.dao.chat.ChatRecordDAO;
import com.atm.dao.impl.chat.ChatRecordDAOImpl;
import com.atm.model.chat.CrowdChat;
import com.atm.util.JsonUtil;

public class CrowdAction {
	private int crowdId;
	private int userId;
	private String json;
	public String getCrowdRecord(){
		ChatRecordDAO chatRecordDAO = ChatRecordDAOImpl.getFromApplicationContext();
		List<CrowdChat> list = (List<CrowdChat>) chatRecordDAO.findById(crowdId);
		String  json = JsonUtil.listToJson(list);
		return null;
	}
	
	
	
	
	
	
	public int getCrowdId() {
		return crowdId;
	}
	public void setCrowdId(int crowdId) {
		this.crowdId = crowdId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
}
