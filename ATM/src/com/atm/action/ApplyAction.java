package com.atm.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.ApplyService;
import com.atm.util.bbs.ObjectInterface;

public class ApplyAction implements ObjectInterface{
	private static final Logger log = Logger.getLogger(ApplyAction.class);
	private HttpServletRequest request = getRequest();
	private HttpServletResponse response = getResponse();
	
	 private Integer apInfoId;
     private Integer workId;
     private String publisherId;
     private String telephone;
     private String personalInfo;
     
     private String expectSalary;
     private String reTypeName;
     private String woTypeName;
   //�ͻ��˴�����ز���
 	private Integer page;//��ѯ������ʼλ��
 	private String tip; //��ѯ����
	
	
	private ApplyService applyService = (ApplyService) context.getBean("ApplyService");
	
	//mess:���û�����ʾ��Ϣ-----sendJson:װ��mess��result------resultArray:װ������
			String mess;
			JSONObject sendJson;
			JSONArray resultArray;
			
			//��ʼ��
			private void init(){
				mess = "��ȡʧ��";
				sendJson  = new JSONObject();
				resultArray = new JSONArray();
			}
			//�����û�л�ȡ������
			private void check(){
				if(resultArray == null){
					mess = "û������";
					resultArray = new JSONArray();//���³�ʼ��
				}else{
					String tip="success";
					try{
						tip = (String) resultArray.getJSONObject(0).get("tip");
					}catch(JSONException e){
						
					}
					mess = tip;
				}
			}
			//���ͽ��
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
	
	public String save(){
		//json = applyService.saveApplay(json);
		//setJson();
		return null;
	}
	//TODO ��������
	public void publish(){
		log.debug("������ְ�����󡣡���");
		init();
		try {
			//��ȡ�û���½��Ϣ
			UserInfo user = (UserInfo) getRequest().getSession().getAttribute("user");
			if(user==null){
				mess = "δ��¼";
				send("apply");
				return;
			}
			publisherId = user.getUserId();
			if(apInfoId == null){
				apInfoId = 0;
			}
			if(apInfoId>0){
				log.debug("�޸Ĳ���");
			}
			mess = applyService.saveApply(apInfoId,reTypeName, woTypeName,expectSalary, telephone, personalInfo, publisherId);
		} catch(Exception e){
			mess = "��������";
			log.error(mess, e);
		}
		send("apply");
	}
	
	public String update(){
		//json = applyService.updateApply(json);
		//setJson();
		return null;
	}
	
	public String delete(){
		//json = applyService.deleteApply(json);
		//setJson();
		return null;
	}
	
	public String getApply(){
		init();
		if(page==null){
			log.debug("û�н���page,Ĭ��0");
			page = 0;
		}
	/*	if(tip!=null)
			tip = jsonUtil.changeString(tip);*/
		try {
			log.debug("��ְ������:"+tip);
			resultArray = applyService.findList(tip,page);
		} catch (Exception e1) {
			mess = "��ȡ�쳣";
		} 
		mess = "success";
		send("apply");
		return null;
	}
	//TODO ��������
	public String detail(){
		log.debug("������ְ��"+apInfoId);
		String mess = applyService.applyDetail(request,apInfoId);
		if(mess.equals("success")){
			log.debug("�Ķ�+1");
			applyService.saveClick(apInfoId);
		}
		return mess;
	}
	public String findApply(){
		//json = applyService.findByApplyId(json);
		//setJson();
		return null;
	}
	

	public Integer getApInfoId() {
		return apInfoId;
	}

	public void setApInfoId(Integer apInfoId) {
		this.apInfoId = apInfoId;
	}


	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getExpectSalary() {
		return expectSalary;
	}

	public void setExpectSalary(String expectSalary) {
		this.expectSalary = expectSalary;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(String personalInfo) {
		this.personalInfo = personalInfo;
	}

	public ApplyService getApplyService() {
		return applyService;
	}

	public void setApplyService(ApplyService applyService) {
		this.applyService = applyService;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
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
	public String getReTypeName() {
		return reTypeName;
	}
	public void setReTypeName(String reTypeName) {
		this.reTypeName = reTypeName;
	}
	public String getWoTypeName() {
		return woTypeName;
	}
	public void setWoTypeName(String woTypeName) {
		this.woTypeName = woTypeName;
	}
}
