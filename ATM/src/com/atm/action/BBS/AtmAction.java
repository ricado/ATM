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
	UserDeal userDeal;
	
	 private String id;//�����ؼ���,���û��˺�
	 private int page;
	 
	 //�ٱ�
	 private String reportReason; //�ٱ�ԭ��	
	 private int aim = -1; //�ٱ�Ŀ�꣨0Ϊ��̳����1Ϊ��Ƹ����
	 private int aimId = -1; //������Id)
	 
	 //�û���ǩ
	 private String tag;
	
	 //ϵ�����ű�ǩ
	 private String dno;
	 private int rows = 9;
	 
	//mess:���û�����ʾ��Ϣ-----sendJson:װ��mess------resultArray:װ��sendJson������
		String mess;
		JSONObject sendJson;
		JSONArray resultArray;
		
		//���JSONArrayʱ��list
/*		List<String> nameList;
		List<JSONArray> resultList;*/
		
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
			send(name,true);
		}
		private void send(String name,boolean haveArray){
			try {
				sendJson.put("tip", mess);
				if(haveArray){
					sendJson.put(name,resultArray);
				}
				//��ʱ�ж��JSONarray�Ž�sendJson
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

	
	
	
	//TODO �����û�
	public String searchPeople(){
		log.debug("�����û�����");
		init();
		UserService userService = new UserService();
		try{
			log.debug("�����ؼ��ʣ�"+id);
			List<UserList> userLists = userService.findUser(id, page, 20);
			resultArray = jsonUtil.objectToArray(userLists);
			sendJson.put("user", resultArray);
			sendUtil.writeToClient(response, sendJson);
		}catch(Exception e){
			mess = "��ȡʧ��";
			log.error(mess,e);
			sendUtil.writeToClient(response, errorJson);
		}
	/*	if(mess.equals("�ɹ�"))
			mess = "�ɹ�_"+resultArray.length();*/
		//send("user");
		return null;
	}
	
	//TODO ��������
	public String personDetail(){
		log.debug("�����û�����");
		init();
		UserService userService = new UserService();
		try{
			log.debug("�����û���"+id);
			if(id!=null&&id.length()!=0){
				UserBasicInfo basicInfo = userService.getUserBasicInfo(id);
				sendJson.put("data", jsonUtil.objectToJson(basicInfo));
				mess = "success";
			}else{
				mess = "id incorrect";
			}
		}catch(Exception e){
			mess = "��ȡʧ��";
			log.error(mess,e);
		}
		/*if(mess.equals("�ɹ�"))
			mess = "�ɹ�_"+resultArray.length();*/
		send("person",false);
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
	
	//TODO �ٱ�
	public void report(){
		init();
		
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="δ��¼";
				send("report",false);
				return;
			}
			mess = deal.saveReport(user.getUserId(), aim, aimId, reportReason);
		} catch (Exception e) {
			mess="�ٱ���������";
			log.error(mess, e);
		} 
		send("report",false);	
	}
	
	
	//TODO ��ȡ���ű�ǩ
	public void hotLabel(){
		String name="tag";
		init();
		log.debug("��ȡ���ű�ǩ����");
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if(user==null){
				mess="δ��¼";
				send("tag",false);
				return;
			}
			 resultArray = deal.getAttendedLabelName(user.getUserId());
			 check();
			 name = "userTag";
			 //���û�û�й�ע��ǩʱ�ŷ������ű�ǩ
			 if(mess.equals("û�н��")){
				log.debug("��ȡ���ű�ǩ");
				resultArray =  deal.getHotLabel();
				mess = "success";
				name = "hotTag";
			 }	
		} catch (Exception e) {
			mess="��ȡ��ǩ��������";
			log.error(mess, e);
		}
		send(name);
	}
	
	public void hotDeptLabel(){
		String name = "hotTag";
		init();
		log.debug("��ȡϵ�����ű�ǩ����:id:"+id);
		try {
				resultArray =  deal.getHotLabelByDno(id, rows);
				mess = "success";
		} catch (Exception e) {
			mess="��ȡ��ǩ��������";
			log.error(mess, e);
		}
		send(name);
	}
	
	//�û�����Լ��ı�ǩ
	public void attTag(){
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="δ��¼";
				send("tag",false);
				return;
			}
			mess = deal.saveAttTag(user.getUserId(), tag);
		} catch (Exception e) {
			mess="��������";
			log.error(mess, e);
		} 
		send("tag",false);	
		
	}
	//�û�ɾ���Լ��ı�ǩ
	public void cancelTag(){
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="δ��¼";
				send("tag",false);
				return;
			}
			mess = deal.deleteAttTag(user.getUserId(), tag);
		} catch (Exception e) {
			mess="��������";
			log.error(mess, e);
		} 
		send("tag",false);	
	}
	//��ȡ�û�ͷ��·��
	public void getUserHead(){
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		try {
			if(user==null){
				mess="δ��¼";
				send("",false);
				return;
			}
			String userHead = deal.getUserHead(user.getUserId());
			if(userHead==null){
				mess = "�û�������";
				send("",false);
			}else{
				sendJson.put("userHead",userHead);
				sendUtil.writeToClient(response,sendJson);
			}
		} catch (Exception e) {
			mess="��������";
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

