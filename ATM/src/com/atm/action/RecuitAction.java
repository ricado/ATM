package com.atm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.atm.service.RecuitService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ��Ƹ
 * @author ye
 * @2015.08.03
 */
public class RecuitAction extends ActionSupport{
	public String json;
	public HttpServletRequest request;
	public HttpServletResponse response;
	private RecuitService recuitService = new RecuitService();
	
	/**
	 * �����½�����Ƹ��Ϣ
	 * @return
	 */
	public String saveRecuit(){
		json = recuitService.saveRecuit(json);
		setJson();
		return null;
	}
	
	/**
	 * �޸���Ƹ��Ϣ
	 * @return
	 */
	public String updateRecuit(){
		json = recuitService.updateRecuit(json);
		setJson();
		return null;
	}
	
	/**
	 * ɾ����Ƹ��Ϣ
	 * @return
	 */
	public String deleteRecuit(){
		json = recuitService.delete(json);
		setJson();
		return null;
	}
	
	/**
	 * ���ҷ�����������Ƹ�б�
	 * @return
	 */
	public String findList(){
		json = recuitService.findList(json);
		setJson();
		return null;
	}
	
	/**
	 * �鿴ĳһ��Ƹ����ϸ��Ϣ
	 * @return
	 */
	public String findRecuit(){
		json = recuitService.findByRecuitId(json);
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
	public RecuitService getRecuitService() {
		return recuitService;
	}
	public void setRecuitService(RecuitService recuitService) {
		this.recuitService = recuitService;
	}
	
	
}
