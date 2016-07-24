package com.atm.service.bbs;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.atm.dao.RecuitInfoContentDAO;
import com.atm.dao.RecuitTypeDAO;
import com.atm.dao.WorkTypeDAO;
import com.atm.dao.impl.bbs.RecuitClickDAO;
import com.atm.daoDefined.RecuitViewDAO;
import com.atm.model.RecuitInfoContent;
import com.atm.model.RecuitType;
import com.atm.model.WorkType;
import com.atm.model.bbs.RecuitClick;
import com.atm.model.define.RecuitView;
import com.atm.util.JsonUtil;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO：
 * @fileName : com.atm.service.RecuitDeal.java
 * date | author | version |   
 * 2015年9月19日 | Jiong | 1.0 |
 */
public class RecuitDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	private JSONArray resultArray;
	//初始化
	private void init(){
		resultArray = new JSONArray();
	}
	
	//TODO　修改帖子
	public String updateRecuit(String userId,int reInfoId){
		
		return null;
	}
	
	//TODO 删除帖子
	public String deleteRecuit(String userId,int reInfoId){
		RecuitInfoContentDAO contentDAO = 
				context.getBean("RecuitInfoContentDAOImpl",RecuitInfoContentDAO.class);
		RecuitInfoContent content = contentDAO.findById(reInfoId);
		if(content==null){
			log.warn(userId+">>尝试删除帖子，不存在:"+reInfoId);
			return "帖子不存在";
		}
		if(!userId.equals(content.getPublisherId())){
			log.warn(userId+">>尝试删除帖子，无权:"+reInfoId);
			return "无权删除";
		}
		contentDAO.delete(content);
		return "success";
	}
	
	//TODO 获取招聘帖
	public JSONArray recuitList(int index,Object type) throws JSONException, IOException{
		init();
		RecuitViewDAO recuitDAO = 
				(RecuitViewDAO) context.getBean("RecuitViewDAO");
		List list;
		if(type==null||type.equals("全部")){
			list = recuitDAO.findList(index, 10);
		}else{
			list = recuitDAO.findList(type,index, 10);
		}
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//TODO 进入具体帖子
	public String recuitDetail(HttpServletRequest request,int reInfoId){
		 RecuitViewDAO recuitViewDAO = 
				 (RecuitViewDAO)context.getBean("RecuitViewDAO");
		 //获取
		 RecuitView recuitView = recuitViewDAO.findById(reInfoId);
		 if(recuitView==null){
			 return "error";
		 }
		 request.setAttribute("recuitBean",recuitView);
		 return "success";
		 
	}
	
	//TODO　阅读量＋１
	public void saveClick(int reInfoId){
		RecuitClickDAO dao =
				(RecuitClickDAO)context.getBean("RecuitClickDAO");
		RecuitClick model = dao.findById(reInfoId);
		if(model==null){
			dao.save(new RecuitClick(reInfoId,1));
		}else{
			model.setClickNum(model.getClickNum()+1);
		}
	}
	
	
	//TODO　发布招聘帖(当reInfoId不为0的时候说明是在修改帖子)
	public String saveRecuit(int reInfoId,String recuitType,String workType,String workAddress,String salary,
			String telephone,String reContent,String publisherId){
		
		//****************数据操作对象**********************
		RecuitInfoContentDAO contentDAO = 
				(RecuitInfoContentDAO)context.getBean("RecuitInfoContentDAOImpl");
		RecuitTypeDAO recuitTypeDAO = 
				(RecuitTypeDAO) context.getBean("RecuitTypeDAOImpl");
		WorkTypeDAO workTypeDAO = 
				(WorkTypeDAO) context.getBean("WorkTypeDAOImpl");
		
		RecuitInfoContent originCon = null;
		if(reInfoId!=0){
			originCon = contentDAO.findById(reInfoId);
			if(originCon==null){
				return "要修改的帖子不存在";
			}
		}
		
		// *******获取有ID属性的对应ID********************
		int recuitId;
		int workId;
		List recuitList = recuitTypeDAO.findByReTypeName(recuitType);
		List workList = workTypeDAO.findByWoTypeName(workType);
		//若在数据库中无此记录则将此记录录入
		if(recuitList.size()==0){
			RecuitType type = new RecuitType();
			type.setReTypeName(recuitType);
			recuitTypeDAO.save(type);
			recuitList = recuitTypeDAO.findByReTypeName(recuitType);
		}
		if(workList.size()==0){
			WorkType type = new WorkType();
			type.setWoTypeName(workType);
			workTypeDAO.save(type);
			workList = workTypeDAO.findByWoTypeName(workType);
		}
		//取出ID
		recuitId = ((RecuitType)recuitList.get(0)).getRecuitId();
		workId = ((WorkType)workList.get(0)).getWorkId();
		
		if(originCon!=null){
			log.debug("执行修改操作...");
			if(!publisherId.equals(originCon.getPublisherId())){
				return "无权修改";
			}
			originCon.setRecContent(reContent);
			originCon.setRecuitId(recuitId);
			originCon.setSalary(salary);
			originCon.setTelephone(telephone);
			originCon.setWorkAddress(workAddress);
			originCon.setWorkId(workId);
			return "success";
		}
		
		log.debug("执行发布操作...");
		//新建一个RecuitInfoContent对象》》设置参数》》持久化
		RecuitInfoContent con = new RecuitInfoContent();
		con.setPublisherId(publisherId);
		con.setRecContent(reContent);
		con.setRecuitId(recuitId);
		con.setSalary(salary);
		con.setTelephone(telephone);
		con.setWorkAddress(workAddress);
		con.setWorkId(workId);
		con.setPublishTime(new Timestamp(System.currentTimeMillis()));
		log.debug("保存招聘帖。。。");
		contentDAO.save(con);
		return "success";
	}
}

