package com.atm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.service.ApplyService;

public class ApplyAction {
	private static final Logger log = LoggerFactory.getLogger(ApplyAction.class);
	private String json;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ApplyService applyService = new ApplyService();
	public String save(){
		json = applyService.saveApplay(json);
		setJson();
		return null;
	}
	
	public String update(){
		json = applyService.updateApply(json);
		setJson();
		return null;
	}
	
	public String delete(){
		json = applyService.deleteApply(json);
		setJson();
		return null;
	}
	
	public String findList(){
		json = applyService.findList(json);
		setJson();
		return null;
	}
	
	public String findApply(){
		json = applyService.findByApplyId(json);
		setJson();
		return null;
	}
	
	public void setJson(){
		getRequest().setAttribute("json", json);
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public static Logger getLog() {
		return log;
	}
}
