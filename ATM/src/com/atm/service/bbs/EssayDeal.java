/**
 * 
 */
package com.atm.service.bbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.dao.PeopleAttentionAssociationDAO;
import com.atm.dao.bbs.ClickGoodDAO;
import com.atm.dao.bbs.CollectEssayDAO;
import com.atm.dao.bbs.LabelAttentionAssociationDAO;
import com.atm.dao.user.DepartmentDAO;
import com.atm.daoDefined.bbs.ClickGoodViewDAO;
import com.atm.daoDefined.bbs.CollectEssayViewDAO;
import com.atm.daoDefined.bbs.EssayDetailViewDAO;
import com.atm.daoDefined.bbs.EssayOuterDAO;
import com.atm.model.PeopleAttentionAssociation;
import com.atm.model.bbs.ClickGood;
import com.atm.model.bbs.ClickGoodId;
import com.atm.model.bbs.CollectEssay;
import com.atm.model.bbs.CollectEssayId;
import com.atm.model.bbs.LabelAttentionAssociation;
import com.atm.model.define.bbs.ClickGoodView;
import com.atm.model.define.bbs.CollectEssayView;
import com.atm.model.define.bbs.EssayDetailView;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO�������߼�ҵ����
 * @fileName : com.atm.service.EssayDeal.java
 * date | author | version |   
 * 2015��7��30�� | Jiong | 1.0 |
 */
public class EssayDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	private JSONArray sendArray;
	
	//��ʼ��
	private void init(){
		sendArray = new JSONArray();
	}
	
	
	//TODO***********************
	/*
	 * ��ȡ��ҳ����ʾ������(���ţ�����)
	 */
	public JSONArray getMainEssay() throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		
		//��ȡ������
		List list = essayOuterDao.getHotEssay();
		String hotEssay = jsonUtil.objectToArray(list,false).toString();
		if(list.size()==0){//ȡ����ֵ
			return null;
		}
		//��ȡ������
		list = essayOuterDao.getCurrentEssay(0);
		String currentEssay = jsonUtil.objectToArray(list,false).toString();
		//�ϲ�����array
		String essay = hotEssay+currentEssay;
		essay = essay.replace("][", ",");
		return new JSONArray(essay);
	}
	
	
	//TODO ��ȡʮ�����û�ˢ�´�(index)�����������
	public JSONArray getTenEssay(HttpServletRequest request,int index) throws IOException, JSONException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		if(index<0){
			index = 0;
		}
		List list = essayOuterDao.getCurrentEssay(index);//�ӵ�index��ȡʮ����������
		if(list.size()==0){ //��ȡ���������ˣ�����null
			return null;
		}
		return jsonUtil.objectToArray(list);
	}
	
	
	//TODO ��ȡϵ����
	public JSONArray getDeptEssay(HttpServletRequest request,String dNo,int index) throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		DepartmentDAO deptDao = 
				context.getBean("DepartmentDAO",DepartmentDAO.class);
		
		/*List deptList = (List) deptDao.findById(dNo);
		if(deptList.size()==0){
			return new JSONArray("[{'tip':'�Ҳ�����ϵ��'}]");
		}
		*/
		//String dNo = ((Department) deptList.get(0)).getDno();
		if(index<0){
			index = 0;
		}
		
		List list = essayOuterDao.getDeptEssay(index,dNo);//�ӵ�first��ȡʮ����������
		return jsonUtil.objectToArray(list,false);
	}
	
	
	//TODO ��ȡ��ע�˺ͱ�ǩ����
	public JSONArray getAttendEssay(HttpServletRequest request,String userId,int index) throws JSONException, IOException{
		PeopleAttentionAssociationDAO attendDao = 
				context.getBean("PeopleAttentionAssociationDAO",PeopleAttentionAssociationDAO.class);
		EssayOuterDAO essayDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		LabelAttentionAssociationDAO labelAttDao = 
				context.getBean("LabelAttentionAssociationDAO",LabelAttentionAssociationDAO.class);
		boolean haveSomething = false;
		
		log.debug("��ȡ��ע���˺ż���"+userId);
		List userList = attendDao.findByAttendUserId(userId);
		List<String> ids = new ArrayList<String>();
		if(userList.size()>0){
			haveSomething = true;	
		}
		//ȡ����ע��ϵ�еı���ע��
		for(int i=0;i<userList.size();i++){
			String aId = ((PeopleAttentionAssociation)userList.get(i)).getUserAttendedId();
			ids.add(aId);
		}
		log.debug("����ע�߲�ѯ����"+ids.toString());
		
		log.debug("��ȡ��ע�ı�ǩ��ż���");
		List labelList = labelAttDao.findByUserId(userId);
		if(labelList.size()>0){
			haveSomething = true;
		}
		if(!haveSomething){
			return new JSONArray("[{'tip':'δ��ע���˻��ǩ'}]");
		}
		//����ȡ�ı�ǩ��ϵȡ����ǩID���ճ�sql��ѯ������
		String labelCondition = "";
		for(int i=0;i<labelList.size();i++){
			labelCondition += "FIND_IN_SET('"+((LabelAttentionAssociation)labelList.get(i)).getLabId()+"',labId)";
			if(i<labelList.size()-1){
				labelCondition += " or ";
			}
		}
		log.debug("��ǩ��ѯ������"+labelCondition);
		
		if(index<0){
			index = 0;
		}
		log.debug("��ȡ��ע�˺ͱ�ǩ������");
		List list = essayDao.getAttendEssay(index, ids,labelCondition);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list);
	}
	
	
	//TODO ��ȡ�û������ĺ��ղص�һ������
	public JSONArray getUserEssay(String userId) throws JSONException, IOException{
		init();
		CollectEssayViewDAO collectEssayDao =
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		
		List list1 = collectEssayDao.findByProperty("userId",userId, 0, 1);
		int collectNum = collectEssayDao.getCollectEssayNum(userId);
		List list2 = essayOuterDao.getPublishedEssay(userId,0,1);
		int publishNum = essayOuterDao.getPublishedEssayNum(userId);
		
		if(list1.size()!=0){//0������ղ���
			sendArray.put(0,jsonUtil.objectToJson(list1.get(0)).put("num",collectNum));
		}
		if(list2.size()!=0){//1����ŷ�����
			sendArray.put(1,jsonUtil.objectToJson(list2.get(0)).put("num", publishNum));
		}
		
		return sendArray;
		
	}
	
	//TODO  ��ȡ�û���userId)�ղص�����
	public JSONArray getCollectedEssay(HttpServletRequest request,String userId,int index) throws IOException, JSONException {
		init();
		CollectEssayViewDAO collectEssayDao =
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		List list = collectEssayDao.findByUserId(userId,index);
		
		if(list.size()==0){ //��ȡ���������ˣ�����null
			return null;
		}
		
		
		return jsonUtil.objectToArray(list,false);
	}
	
	/*
	 //TODO  ��ȡ�û�����������
	 * ���������essayId�����ӱ��--------userId:�û��˺�
	 */
	public JSONArray getPublishedEssay(HttpServletRequest request,String userId,int index) throws IOException, JSONException{
		init();
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		List list = essayOuterDao.getPublishedEssay(userId, index,10);
		
		if(list.size()==0){ //��ȡ���������ˣ�����null
			return null;
		}
		
		
		return jsonUtil.objectToArray(list);
	}
	
	//TODO���ͻ��˽��������������Ҫ����
	public JSONObject getContent(HttpServletRequest request,int essayId,String userId) throws JSONException, IOException{
		EssayDetailViewDAO essayDetailDao =
				context.getBean("EssayDetailViewDAO",EssayDetailViewDAO.class);
		ClickGoodViewDAO clickGoodDao = 
				context.getBean("ClickGoodViewDAO",ClickGoodViewDAO.class);
		CollectEssayViewDAO collectEssayDao = 
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		log.debug("��ȡ���ӣ�"+essayId);
		EssayDetailView essayDetail = essayDetailDao.findSomeValue(essayId);
		if(essayDetail==null){//��ȡ��������
			return null;
		}
		log.debug(">>>"+jsonUtil.objectToJson(essayDetail));
		//��ѯ�Ƿ��ѵ���
		boolean isClickGood = false;
		//��ѯ�Ƿ����ղ�
		boolean isCollect = false;
		
		log.debug("��֤�û��뱾����ϵ");
		ClickGoodView clickGood = clickGoodDao.findById(userId+"_"+essayId);
		if(clickGood!=null){
			isClickGood = true;
		}	
		CollectEssayView collectEssay= collectEssayDao.findById(userId+"_"+essayId);
		if(collectEssay!=null){
			isCollect = true;
		}
		essayDetail.setClickGood(isClickGood);
		essayDetail.setCollect(isCollect);
		System.out.println(">>>>>>>>>>"+jsonUtil.objectToJson(essayDetail, false));
		return jsonUtil.objectToJson(essayDetail, false);
	}
	/*
	 //TODO ������������
	 * ���������essayId�����ӱ��
	 * read-only = false;
	 */
	public String getEssayDetail(HttpServletRequest request,int essayId) throws IOException, JSONException{

		EssayDetailViewDAO essayDetailDao =
				context.getBean("EssayDetailViewDAO",EssayDetailViewDAO.class);
		ClickGoodViewDAO clickGoodDao = 
				context.getBean("ClickGoodViewDAO",ClickGoodViewDAO.class);
		CollectEssayViewDAO collectEssayDao = 
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		
		
		//��ȡ������ϸ����
		EssayDetailView essayDetail = essayDetailDao.findById(essayId);
		if(essayDetail==null){//��ȡ��������
			return null;
		}
		log.debug("����������Ϣ��request");
		request.setAttribute("essayBean", essayDetail);
		
		log.debug("EssayDeal��������");
		
		return "success";
	}
 
	/*
	 * ��ȡ���ӵ�����
	 */
	public String getClickGoodPeople(HttpServletRequest request,int essayId,int first){
		ClickGoodViewDAO clickGoodDao = 
				context.getBean("ClickGoodViewDAO",ClickGoodViewDAO.class);
		List list = clickGoodDao.findByEssayId(essayId,first);
		request.setAttribute("clickGoodPeople", list);
		return "success";
	}
	/*
	 * ���������
	 */
	public JSONArray getMorePeople(int essayId,int first) throws JSONException, IOException{
		ClickGoodViewDAO clickGoodDao = 
				context.getBean("ClickGoodViewDAO",ClickGoodViewDAO.class);
		List list = clickGoodDao.findByEssayId(essayId,first);
		return jsonUtil.objectToArray(list);
	}
	//TODO ���޺�ȡ���޲���
	public String saveOrDeleteClickGood(HttpServletRequest request,int essayId,boolean isClickGood,String userId){
		ClickGoodDAO clickGoodDao = 
				context.getBean("ClickGoodDAO",ClickGoodDAO.class);
		ClickGood clickGood = new ClickGood();
		ClickGoodId clickGoodId = new ClickGoodId();
		clickGoodId.setEssayId(essayId);
		clickGoodId.setUserId(userId);
		clickGood.setId(clickGoodId);
		//�ѵ��ޣ���ִ��ȡ�����޲���
		if(isClickGood){
			log.debug("ȡ�����޲���");
			clickGoodDao.delete(clickGood);
		}else{
			log.debug("���ӵ��޼�¼");
			clickGoodDao.attachDirty(clickGood);
		}
		return "�ɹ�";
	}
	//TODO �ղغ�ȡ���ղز���
		public String saveOrDeleteCollect(HttpServletRequest request,int essayId,boolean isCollect,String userId){
			CollectEssayDAO collectDao = 
					context.getBean("CollectEssayDAO",CollectEssayDAO.class);
			CollectEssay collectEssay = new CollectEssay();
			CollectEssayId collectEssayId = new CollectEssayId();
			collectEssayId.setEssayId(essayId);
			collectEssayId.setUserId(userId);
			collectEssay.setId(collectEssayId);
			//���ղأ���ִ��ȡ���ղز���
			if(isCollect){
				log.debug("ȡ���ղز���");
				collectDao.delete(collectEssay);
			}else{
				log.debug("�����ղؼ�¼");
				collectDao.attachDirty(collectEssay);
			}
			return "�ɹ�";
		}
}

