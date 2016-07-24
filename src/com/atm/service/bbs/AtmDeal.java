package com.atm.service.bbs;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.atm.dao.RecuitInfoContentDAO;
import com.atm.dao.bbs.EssayDAO;
import com.atm.dao.bbs.LabelAttentionAssociationDAO;
import com.atm.dao.bbs.LabelDAO;
import com.atm.dao.impl.bbs.ReportDAO;
import com.atm.dao.user.DepartmentDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.daoDefined.bbs.EssayOuterDAO;
import com.atm.daoDefined.bbs.LabelRelationViewDAO;
import com.atm.daoDefined.bbs.LabelViewDAO;
import com.atm.model.RecuitInfoContent;
import com.atm.model.bbs.Essay;
import com.atm.model.bbs.Label;
import com.atm.model.bbs.LabelAttentionAssociation;
import com.atm.model.bbs.Report;
import com.atm.model.user.UserInfo;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO������
 * @fileName : com.atm.service.AtmDeal.java
 * date | author | version |   
 * 2015��8��14�� | Jiong | 1.0 |
 */
public class AtmDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass()); 
	
	//��ȡϵ���б�
	public JSONArray getDeptList(String scNo) throws JSONException, IOException{
		DepartmentDAO deptDao = context.getBean("DepartmentDAOImpl",DepartmentDAO.class);
		List list = deptDao.findDeptList(scNo);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	//��ȡ���ű�ǩ
	public JSONArray getHotLabel() throws JSONException, IOException{
		LabelViewDAO labelDao = 
				context.getBean("LabelViewDAO",LabelViewDAO.class);
		List<String> list = labelDao.findHotLabel();
		return jsonUtil.objectToArray(list);
	}
	//����ϵ���ȡ���ű�ǩ
	public JSONArray getHotLabelByDno(String dNo,int maxResult) throws JSONException, IOException{
		LabelDAO labelDao = context.getBean("LabelDAOImpl",LabelDAO.class);
		List<String> labelList = labelDao.findByDno(dNo,maxResult);
		return jsonUtil.objectToArray(labelList);
	}
	
	//TODO ��ȡ��ע�ı�ǩ����
	public JSONArray getAttendedLabelName(String userId) throws JSONException, IOException{
		LabelRelationViewDAO labelDao = 
				context.getBean("LabelRelationViewDAO",LabelRelationViewDAO.class);
		log.debug("��ȡ��ע���˺ż���"+userId);
		List userList = labelDao.findAttendedLabelName(userId);
		if(userList.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(userList,false);
	}
	
	//��ȡ���ű�ǩ
	public JSONArray getUserLabel() throws JSONException, IOException{
		LabelViewDAO labelDao = 
				context.getBean("LabelViewDAO",LabelViewDAO.class);
		List list = labelDao.findHotLabel();
		return jsonUtil.objectToArray(list);
	}
	
	//�����û�
	public JSONArray searchPeople(String userName) throws JSONException, IOException{
		UserInfoDAO userInfoDao = 
				context.getBean("UserInfoDAOImpl",UserInfoDAO.class);
		List list = userInfoDao.searchPeople(userName);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//����ĳ���ؼ��ʵ�����
	public JSONArray searchEssayByKey(String key,int index) throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		List list = essayOuterDao.searchPeople(key,index);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//���Ӿٱ���¼
	public String saveReport(String userId,int aim,int aimId,String reason){
		String mess = "δ֪���󣬾ٱ�ʧ��";
		if(reason.length()<=0){
			return "δ��д�ٱ�ԭ��";
		}
		if(aim==-1){
			return "δָ���ٱ�Ŀ��";
		}
		if(aimId == -1){
			return "δָ���ٱ�����";
		}
		//�ٱ���̳����
		if(aim==0){
			EssayDAO essay = (EssayDAO) context.getBean("EssayDAOImpl");
			Essay es = essay.findById(aimId);
			if(es==null){
				return "���Ӳ�����";
			}
		}
		//�ٱ���Ƹ����
		if(aim==1){
			RecuitInfoContentDAO contentDAO = 
					(RecuitInfoContentDAO)context.getBean("RecuitInfoContentDAOImpl");
			RecuitInfoContent re = contentDAO.findById(aimId);
			if(re==null){
				return "���Ӳ�����";
			}
		}
		ReportDAO reDao = (ReportDAO) context.getBean("ReportDAO");
		String[] reasons = reason.split("\\*#");
		for(String r:reasons){
			if(r.length()>100){
				return "ԭ���������ܳ���100";
			}
		}
		for(String r:reasons){
			Report report = new Report();
			report.setUserId(userId);
			report.setAim(aim);
			report.setAimId(aimId);
			report.setReportReason(reason);
			reDao.save(report);
		}
	
		return "�ɹ�";
	}
	
	//�û���ע��ǩ
	public String saveAttTag(String userId,String tag){
		JSONArray tagArray = new JSONArray();
		try {
			tagArray = new JSONArray(tag);
		} catch (JSONException e) {
			return "��ʽ����";
		}
		if(tagArray.length()==0){
			return "�ޱ�ǩ����";
		}
		for(int i=0; i<tagArray.length();i++){
			String aTag = "";
			try {
				aTag = tagArray.getString(i);
			} catch (JSONException e) {
				return "��ʽ����!";
			}
			if(aTag==null||aTag.replace(" ","").length()==0){
				continue;
			}
			LabelDAO labelDao = context.getBean("LabelDAOImpl",LabelDAO.class);
			List labelList = labelDao.findByLabName(aTag);
			if(labelList.size()==0){
				Label newLabel = new Label();
				newLabel.setLabName(aTag);
				labelDao.save(newLabel);
				labelList = labelDao.findByLabName(aTag);
				log.debug("�����ǩ��"+aTag);
			}
			//�����ע��ϵ
			Label label = (Label) labelList.get(0);
			LabelAttentionAssociationDAO tagDao = 
					(LabelAttentionAssociationDAO) context.getBean("LabelAttentionAssociationDAOImpl");
			if(tagDao.haveAttend(userId, label.getLabId())!=null){ //�ѹ�ע���ñ�ǩ
				continue;
			}
			log.debug("�����ע��ϵ");
			LabelAttentionAssociation lab = 
					new LabelAttentionAssociation();
			lab.setUserId(userId);
			lab.setLabId(label.getLabId());
			tagDao.save(lab);	
		}
		return "success";
	}
	//�û�ȡ����ע��ǩ
	public String deleteAttTag(String userId,String tag){
		JSONArray tagArray = new JSONArray();
		try {
			tagArray = new JSONArray(tag);
		} catch (JSONException e) {
			return "��ʽ����";
		}
		if(tagArray.length()==0){
			return "�ޱ�ǩ����";
		}
		for(int i=0; i<tagArray.length();i++){
			String aTag = "";
			try {
				aTag = tagArray.getString(i);
			} catch (JSONException e) {
				return "��ʽ����!";
			}
			if(aTag==null||aTag.replace(" ","").length()==0){
				continue;
			}
			LabelDAO labelDao = context.getBean("LabelDAOImpl",LabelDAO.class);
			List labelList = labelDao.findByLabName(aTag);
			//��ǩ������ʱ
			if(labelList.size()==0){
				continue;
				//return "��ǩ:"+aTag+" ������";
			}
			//��֤�Ƿ��й�ע��ϵ������ȡ��������������һѭ��
			Label label = (Label) labelList.get(0);
			LabelAttentionAssociationDAO tagDao = 
					(LabelAttentionAssociationDAO) context.getBean("LabelAttentionAssociationDAOImpl");
			LabelAttentionAssociation attend = 
					tagDao.haveAttend(userId, label.getLabId());
			if(attend==null){ //û�й�ע��ϵ
				continue;
			}
			log.debug("ȡ����ע��ϵ��"+aTag);
			tagDao.delete(attend);
			
		}
		return "success";
	}
	public String getUserHead(String id){
		UserInfoDAO userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAOImpl");
		UserInfo userInfo = userInfoDao.findById(id);
		if(userInfo==null){
			return null;
		}
		return userInfo.getHeadImagePath();
	}
}

