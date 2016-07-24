package com.atm.action;

/**
 * �޸ģ�2015��9��19
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.RecuitDeal;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ��Ƹ
 * @author ye
 * @2015.08.03
 */
public class RecuitAction extends ActionSupport implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	public HttpServletRequest request = getRequest();
	public HttpServletResponse response = getResponse();
	
	//��Ƹ������
	private Integer reInfoId;//����ID
	private String reTypeName; //��Ƹ����
	private String woTypeName;//��������
	private String workAddress;//�����ص�
	private String salary;//н��
	private String telephone;//��ϵ�绰
	private String reContent;//��Ƹ����
	private String publisherId;//�������˺�
	
	//�ͻ��˴�����ز���
	private Integer page;//��ѯ������ʼλ��
	private String tip; //��ѯ����
	//***ע��tipΪrecuitʱ��������Ƹ����Ϊapply��������ְ��
	
	RecuitDeal deal = (RecuitDeal) context.getBean("RecuitDeal");
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
	
	//***********************��ת****************************
		//TODO ��������
	public void publish(){
		log.debug("������Ƹ�����󡣡���");
		init();
		try {
			//��ȡ�û���½��Ϣ
			UserInfo user = (UserInfo) getRequest().getSession().getAttribute("user");
			if(user==null){
				mess = "δ��¼";
				send("recuit");
				return;
			}
			publisherId = user.getUserId();
			if(reInfoId == null){
				reInfoId = 0;
			}
			mess = deal.saveRecuit(reInfoId,reTypeName, woTypeName, workAddress, salary, telephone, reContent, publisherId);
		} catch(Exception e){
			mess = "��������";
			log.error(mess, e);
		}
		send("recuit");
	}
	
	//TODO ��ȡ����
	public void getRecuit(){
		log.debug("��ȡ��Ƹ�����󡣡���");
		init();
		try{
			if(page==null){
				log.debug("Ĭ��page0");
				page = 0;
			}
			/*if(tip!=null)
				tip = jsonUtil.changeString(tip);*/
			log.debug(tip+":"+page+"....");
			resultArray = deal.recuitList(page,tip);
			check();
		}catch(Exception e){
			mess = "��ȡ���ӷ�������";
			log.error(mess, e);
		}
		send("recuit");
	}
	//TODO��ɾ������
	public void delete(){
		log.debug("��ȡɾ���������󡣡�");
		init();
		try{
			//��ȡ�û���½��Ϣ
			UserInfo user = (UserInfo) getRequest().getSession().getAttribute("user");
			if(user==null){
				mess = "δ��¼";
				send("recuit");
				return;
			}
			mess = deal.deleteRecuit(user.getUserId(), reInfoId);
		}catch(Exception e){
			mess = "��ȡ���ӷ�������";
			log.error(mess, e);
		}
		send("recuit");
	}
	
	//TODO ��������
	public String detail(){
		log.debug("������Ƹ��"+reInfoId);
		String mess = deal.recuitDetail(request, reInfoId);
		if(mess.equals("success")){
			log.debug("�Ķ�+1");
			deal.saveClick(reInfoId);
		}
		return mess;
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
	public int getReInfoId() {
		return reInfoId;
	}
	public void setReInfoId(Integer reInfoId) {
		this.reInfoId = reInfoId;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getReContent() {
		return reContent;
	}
	public void setReContent(String reContent) {
		this.reContent = reContent;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherID) {
		this.publisherId = publisherID;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
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
	public Integer getPage() {
		
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	

	
	
}
