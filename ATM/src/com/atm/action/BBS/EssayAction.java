package com.atm.action.BBS;

import java.net.HttpCookie;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.EssayChangeDeal;
import com.atm.service.bbs.EssayDeal;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

public class EssayAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,ObjectInterface {
	Logger log = Logger.getLogger(getClass());
	
	
	private int essayId;
	private int first = 0;//��ʼλ��
	private int page;//�����б�����ʾ������
	private String id;
	
	private boolean clickGood;//�Ƿ����
	private boolean collect;//�Ƿ��ղ�
	
	private String deptName;//����ϵ������
	
	//��������
	private String type;
	private String title;
	private String content;
	private String label;
	private String department;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	EssayDeal deal = //ֻ����ز���
			(EssayDeal) context.getBean("EssayDeal");
	EssayChangeDeal changeDeal = //���±�����ز���
			context.getBean("EssayChangeDeal",EssayChangeDeal.class);
	
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
			mess = "û������";
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
	private void send(){
		send("bbs");
	}
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
	
	//TODO*****************��ת****************************
	
	//��ת��ȡ����������,������ˢ��
	public String mainEssay(){
		log.debug("��ȡ��������������");
		try{
			Cookie[] cookies = request.getCookies();
			for(int i=0;i<cookies.length;i++){
				System.out.println("cookis:"+cookies[i].getName());
				System.out.println("comm"+cookies[i].getComment());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		init();
		try {
			
			int index = getPage();//ȡ���ͻ��˴�����������λ��
			log.debug(">>>>>>>>>>>>>��"+index+"��");
			if(index==0){
				resultArray = deal.getMainEssay();
			}else{
				resultArray = deal.getTenEssay(request,index-5);//������ȥ5����Ϊ��ȡ��5����������
			}
			check();
		} catch(Exception e){
			mess = "��ȡ���ӷ�������";
			log.error(mess, e);
		}
		send();
		return null;
	}
	
	public String deptEssay(){
		log.debug("��ȡԺϵ��������");
		init();
		//��ȡ�û���½��Ϣ
		/*UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "�û�δ��½";
			send();
			return null;
		}*/
		int index = getPage();//ȡ���ͻ��˴�����������λ��
		try {
			log.debug(">>>>>>>>>>>>>>>>>>>>��ȡϵ������"+getId());
			resultArray = deal.getDeptEssay(request, getId(), index);
			check();
		} catch(Exception e){
			mess = "��ȡ���ӷ�������";
			log.error(mess, e);
		}
		send();
		return null;
	}
	
	//��ȡ��ע������ǩ���ˣ�
	public String attendEssay(){
		log.debug("��ȡ��ע��ص���������");
		init();
		//��ȡ�û���½��Ϣ
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			send();
			return null;
		}
		String userId = user.getUserId();
		try {
			int index = getPage();//ȡ���ͻ��˴�����������λ��
			log.debug(">>>>>>>>>>>>>>>>>>>>��ȡ��ע����"+userId);
			resultArray = deal.getAttendEssay(request, userId, index);
			check();
		} catch(Exception e){
			mess = "��ȡ���ӷ�������";
			log.error(mess, e);
		}
		send();
		return null;
	}
	
	//��ת��ȡʮ��������
	/*
	 * ��Ҫ�ͻ��˴��������index:�û�ˢ�´�λ��
	 */
	/*public String tenEssay(){
		init();
		try {
			resultArray = deal.getTenEssay(request);
			check();
		} catch (Exception e) {
			mess = "��ȡ��������";
			log.error(e);
		} 
		send();
		return null;
	}*/
	
	
	public void userEssay(){
		log.debug("��ȡ�û���һ���������ղص�����");
		init();
		//��ȡ�û���½��Ϣ
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			send();
			return;
		}
		try{
			resultArray = deal.getUserEssay(user.getUserId());
			check();
		}catch(Exception e){
			mess = "��ȡ��������";
			log.error(e);
		}
		send();
		log.debug("�û����ӷ�������");
	}
	//��ת��ȡ�û��ղص�����
	/*
	 * ��Ҫ�ͻ��˴��������index:��ѯλ��
	 */
	public String collectedEssay(){
		log.debug("��ȡ�ղص���������");
		init();
		//��ȡ�û���½��Ϣ
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			send();
			return null;
		}
		try{
			int index = getPage();//ȡ���ͻ��˴�����������λ��
			resultArray = deal.getCollectedEssay(request,user.getUserId(),index);
			check();
		}catch(Exception e){
			mess = "��ȡ��������";
			log.error(e);
		}
		send();
		log.debug("�ղط�������");
		return null;
	}
	
	//��ת�û�����������
	/*
	 * ��Ҫ�ͻ��˴��������index:��ѯλ��
	 */
	public String publishedEssay(){
		log.debug("��ȡ��������������");
		init();
		
		//��ȡ�û���½��Ϣ
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "�û�δ��½";
			send();
			return null;
		}
		try{
			int index = getPage();//ȡ���ͻ��˴�����������λ��
			resultArray = deal.getPublishedEssay(request,user.getUserId(),index);
			check();
			
		}catch(Exception e){
			mess = "��ȡ��������";
			log.error(e);
		}
		send();
		return null;
	}
	//�ͻ�������������ز���(��ȡ��׿�ؼ���ʾ�����ԣ�
	public String content(){
		log.debug("��ȡ��������ؼ���������");
		String mess = "ʧ��";
		try{
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			/*if(user==null){
				mess = "δ��¼";
				sendUtil.writeToClient(response, new JSONObject().put("tip", mess));
				return null;
			}*/
			JSONObject sendObject = deal.getContent(request,getEssayId(),"2");
			sendUtil.writeToClient(response, sendObject);
		}catch(Exception e){
			log.debug("��ȡ��������",e);
			sendUtil.writeToClient(response, errorJson);
		}
			return null;
	}
	//��ת������������(��ҳ��
	/*
	 * ��Ҫ���������essayId:���ӱ��
	 */
	public String detail(){
		log.debug("��ȡ��������ҳ������");
		try{
			log.debug("����detail");
			String boo = deal.getEssayDetail(request,getEssayId());
			if(boo==null){
				return ERROR;
			}else if(boo.equals("success")){
				log.debug("��ȡ���ӳɹ���������ת"+getEssayId());
				log.debug("�Ķ�������1");
				changeDeal.updateClickNum(getEssayId());
				return "essaySuccess";
			}else{
				return ERROR;
			}
		}catch(Exception e){
			log.error(e);
			return ERROR;
		}
	}
	
	//��ת���������б�ҳ��
	public String clickGoodPeople(){
		log.debug("��ȡ�������б�����");
		try{
			log.debug("��ȡ������,index:"+getFirst());
			deal.getClickGoodPeople(request, getEssayId(),0);
			return "clickGoodPeople";
		}catch(Exception e){
			log.error("��ȡ�����ߴ���",e);
			return ERROR;
		}
	}
	//��ȡ���������
	public void moreClickGoodPeople(){
	try{
		log.debug("��ȡ���������"+getEssayId()+":"+getFirst());
		JSONArray resultArray = deal.getMorePeople(getEssayId(), getFirst());
		sendUtil.writeToClient(response, resultArray);	
		}catch(Exception e){
			log.error("��ȡ��������ߴ���", e);
			sendUtil.writeToClient(response,"[]");
		}
	}
	
	//TODO ���޷���
	public void clickGood(){
		log.debug("��ȡ��������");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			send();
		}else{
			try {
				mess = deal.saveOrDeleteClickGood(request, getEssayId(), isClickGood(),user.getUserId());
				send();
			} catch (Exception e) {
				mess = "��ȡ����";
				log.debug(mess, e);
				send();
			} 
		}
	}
	//TODO �ղط���
	public void collectEssay(){
		log.debug("��ȡ�ղ�����");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			send();
		}else{
			try {
				mess = deal.saveOrDeleteCollect(request, getEssayId(), isCollect(),user.getUserId());
				send();
			} catch (Exception e) {
				mess = "��ȡ����";
				log.debug(mess, e);
				send();
			} 
		}
	}
	
	//TODO***********************�޸���صĲ���
	
	//��������
	public String publishEssay(){
		log.debug("��ȡ������������");
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		/*if(user==null){
			mess="δ��¼";
			send();
			return null;
		}*/
		String userId = "5";//����
		try{
			mess = changeDeal.saveAEssay(userId, type, label, title, department, content);
		}catch(Exception e){
			mess = "δ֪����,����ʧ��";
			log.error(mess, e);
		}
		send();
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
	public int getEssayId() {
		return essayId;
	}
	public void setEssayId(int essayId) {
		this.essayId = essayId;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isClickGood() {
		return clickGood;
	}
	public void setClickGood(boolean clickGood) {
		this.clickGood = clickGood;
	}
	public boolean isCollect() {
		return collect;
	}
	public void setCollect(boolean collect) {
		this.collect = collect;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	

}