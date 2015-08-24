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
 * @TODO��
 * @fileName : com.atm.action.AtmAction.java
 * date | author | version |   
 * 2015��8��14�� | Jiong | 1.0 |
 */
public class AtmAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,ObjectInterface {
	Logger log = Logger.getLogger(getClass()); 
	private HttpServletRequest request;
	private HttpServletResponse response;
	AtmDeal deal = context.getBean("AtmDeal",AtmDeal.class);
	
	 private String searchCondition;
	 private String id;
	 private int page;
	
	//mess:���û�����ʾ��Ϣ-----sendJson:װ��mess------resultArray:װ��sendJson������
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
				mess = "û�н��";
				resultArray = new JSONArray();//���³�ʼ��
			}else{
				String tip="�ɹ�";
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
	
		//TODO ��ȡϵ���б�
	public String deptList(){
		log.debug("��ȡϵ���б�����");
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="δ��¼";
				send("department");
				return null;
			}
			String scNo = user.getScNo();
			log.debug("��ȡϵ���б�");
			resultArray = deal.getDeptList(scNo);
			check();	
		} catch (Exception e) {
			mess="��ȡ���ӷ�������";
			log.error(mess, e);
		} 
		send("department");
		return null;	
	}
	
	//TODO ��ȡ���ű�ǩ
	public void hotLabel(){
		log.debug("��ȡ���ű�ǩ����");
		init();
		try {
			log.debug("��ȡ���ű�ǩ");
			String labels =  deal.getHotLabel();
			mess = "�ɹ�";
			sendJson.put("tip", mess);
			sendJson.put("label",labels);
			sendUtil.writeToClient(response,sendJson);	
		} catch (Exception e) {
			mess="��ȡ���ӷ�������";
			log.error(mess, e);
			send("label");
		} 
	}
	
	
	//TODO �����û�
	public String searchPeople(){
		log.debug("�����û�����");
		init();
		
		String searchCondition = getSearchCondition();
		try{
			log.debug("�����ؼ��ʣ�"+searchCondition);
			resultArray = deal.searchPeople(searchCondition);
			check();
		}catch(Exception e){
			mess = "��ȡʧ��";
			log.error(mess,e);
		}
		if(mess.equals("�ɹ�"))
			mess = "�ɹ�_"+resultArray.length();
		send("searchPeople");
		return null;
	}
	//TODO ��������
	public String searchEssay(){
		log.debug("������������");
		init();

		String id = getId();
		try{
			log.debug("�����ؼ��ʣ�"+id);
			resultArray = deal.searchEssayByKey(id,getPage());
			check();
			/*if(mess.equals("�ɹ�"))
				mess = "�ɹ�_"+resultArray.length();*/
		}catch(Exception e){
			mess = "��ȡʧ��";
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

