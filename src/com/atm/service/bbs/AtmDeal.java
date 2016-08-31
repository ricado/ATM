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
 * @TODO：杂项
 * @fileName : com.atm.service.AtmDeal.java
 * date | author | version |   
 * 2015年8月14日 | Jiong | 1.0 |
 */
public class AtmDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass()); 
	
	//获取系部列表
	public JSONArray getDeptList(String scNo) throws JSONException, IOException{
		DepartmentDAO deptDao = context.getBean("DepartmentDAOImpl",DepartmentDAO.class);
		List list = deptDao.findDeptList(scNo);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	//获取热门标签
	public JSONArray getHotLabel() throws JSONException, IOException{
		LabelViewDAO labelDao = 
				context.getBean("LabelViewDAO",LabelViewDAO.class);
		List<String> list = labelDao.findHotLabel();
		return jsonUtil.objectToArray(list);
	}
	//根据系别获取热门标签
	public JSONArray getHotLabelByDno(String dNo,int maxResult) throws JSONException, IOException{
		LabelDAO labelDao = context.getBean("LabelDAOImpl",LabelDAO.class);
		List<String> labelList = labelDao.findByDno(dNo,maxResult);
		return jsonUtil.objectToArray(labelList);
	}
	
	//TODO 获取关注的标签集合
	public JSONArray getAttendedLabelName(String userId) throws JSONException, IOException{
		LabelRelationViewDAO labelDao = 
				context.getBean("LabelRelationViewDAO",LabelRelationViewDAO.class);
		log.debug("获取关注者账号集合"+userId);
		List userList = labelDao.findAttendedLabelName(userId);
		if(userList.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(userList,false);
	}
	
	//获取热门标签
	public JSONArray getUserLabel() throws JSONException, IOException{
		LabelViewDAO labelDao = 
				context.getBean("LabelViewDAO",LabelViewDAO.class);
		List list = labelDao.findHotLabel();
		return jsonUtil.objectToArray(list);
	}
	
	//搜索用户
	public JSONArray searchPeople(String userName) throws JSONException, IOException{
		UserInfoDAO userInfoDao = 
				context.getBean("UserInfoDAOImpl",UserInfoDAO.class);
		List list = userInfoDao.searchPeople(userName);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//搜索某个关键词的帖子
	public JSONArray searchEssayByKey(String key,int index) throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		List list = essayOuterDao.searchPeople(key,index);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//增加举报记录
	public String saveReport(String userId,int aim,int aimId,String reason){
		String mess = "未知错误，举报失败";
		if(reason.length()<=0){
			return "未填写举报原因";
		}
		if(aim==-1){
			return "未指定举报目标";
		}
		if(aimId == -1){
			return "未指定举报帖子";
		}
		//举报论坛帖子
		if(aim==0){
			EssayDAO essay = (EssayDAO) context.getBean("EssayDAOImpl");
			Essay es = essay.findById(aimId);
			if(es==null){
				return "帖子不存在";
			}
		}
		//举报招聘帖子
		if(aim==1){
			RecuitInfoContentDAO contentDAO = 
					(RecuitInfoContentDAO)context.getBean("RecuitInfoContentDAOImpl");
			RecuitInfoContent re = contentDAO.findById(aimId);
			if(re==null){
				return "帖子不存在";
			}
		}
		ReportDAO reDao = (ReportDAO) context.getBean("ReportDAO");
		String[] reasons = reason.split("\\*#");
		for(String r:reasons){
			if(r.length()>100){
				return "原因字数不能超过100";
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
	
		return "成功";
	}
	
	//用户关注标签
	public String saveAttTag(String userId,String tag){
		JSONArray tagArray = new JSONArray();
		try {
			tagArray = new JSONArray(tag);
		} catch (JSONException e) {
			return "格式错误";
		}
		if(tagArray.length()==0){
			return "无标签内容";
		}
		for(int i=0; i<tagArray.length();i++){
			String aTag = "";
			try {
				aTag = tagArray.getString(i);
			} catch (JSONException e) {
				return "格式错误!";
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
				log.debug("保存标签："+aTag);
			}
			//保存关注关系
			Label label = (Label) labelList.get(0);
			LabelAttentionAssociationDAO tagDao = 
					(LabelAttentionAssociationDAO) context.getBean("LabelAttentionAssociationDAOImpl");
			if(tagDao.haveAttend(userId, label.getLabId())!=null){ //已关注过该标签
				continue;
			}
			log.debug("保存关注关系");
			LabelAttentionAssociation lab = 
					new LabelAttentionAssociation();
			lab.setUserId(userId);
			lab.setLabId(label.getLabId());
			tagDao.save(lab);	
		}
		return "success";
	}
	//用户取消关注标签
	public String deleteAttTag(String userId,String tag){
		JSONArray tagArray = new JSONArray();
		try {
			tagArray = new JSONArray(tag);
		} catch (JSONException e) {
			return "格式错误";
		}
		if(tagArray.length()==0){
			return "无标签内容";
		}
		for(int i=0; i<tagArray.length();i++){
			String aTag = "";
			try {
				aTag = tagArray.getString(i);
			} catch (JSONException e) {
				return "格式错误!";
			}
			if(aTag==null||aTag.replace(" ","").length()==0){
				continue;
			}
			LabelDAO labelDao = context.getBean("LabelDAOImpl",LabelDAO.class);
			List labelList = labelDao.findByLabName(aTag);
			//标签不存在时
			if(labelList.size()==0){
				continue;
				//return "标签:"+aTag+" 不存在";
			}
			//验证是否有关注关系，有则取消，无则跳到下一循环
			Label label = (Label) labelList.get(0);
			LabelAttentionAssociationDAO tagDao = 
					(LabelAttentionAssociationDAO) context.getBean("LabelAttentionAssociationDAOImpl");
			LabelAttentionAssociation attend = 
					tagDao.haveAttend(userId, label.getLabId());
			if(attend==null){ //没有关注关系
				continue;
			}
			log.debug("取消关注关系："+aTag);
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

