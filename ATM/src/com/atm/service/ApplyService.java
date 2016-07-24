package com.atm.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;














import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.atm.action.ApplyAction;
import com.atm.dao.ApplyInfoContentDAO;
import com.atm.dao.RecuitInfoContentDAO;
import com.atm.dao.RecuitTypeDAO;
import com.atm.dao.WorkTypeDAO;
import com.atm.dao.impl.bbs.ApplyClickDAO;
import com.atm.dao.impl.bbs.RecuitClickDAO;
import com.atm.daoDefined.ApplyViewDAO;
import com.atm.daoDefined.RecuitViewDAO;
import com.atm.model.ApplyInfoContent;
import com.atm.model.RecuitInfoContent;
import com.atm.model.RecuitType;
import com.atm.model.WorkType;
import com.atm.model.bbs.ApplyClick;
import com.atm.model.bbs.RecuitClick;
import com.atm.model.define.ApplyView;
import com.atm.model.define.RecuitView;
import com.atm.util.Application;
import com.atm.util.JsonUtil;
import com.atm.util.bbs.ObjectInterface;

public class ApplyService implements ObjectInterface{
	private static final Logger log = Logger.getLogger(ApplyAction.class);
	private String tip = "";
	
	/**
	 * 保存求职信息
	 * @param json
	 * @return
	 */
	/*public String saveApplay(String json){
		log.debug(json);
		ApplyInfoContent applyInfoContent = 
				(ApplyInfoContent)JsonUtil.jsonToObject(json, ApplyInfoContent.class);
		ApplyInfoContentDAO contentDAO = 
				(ApplyInfoContentDAO)context.getBean("ApplyInfoContentDAOImpl");
		try{
			contentDAO.save(applyInfoContent);
			tip = "suuccess";
		}catch(RuntimeException re){
			tip = "error";
		}
		return JsonUtil.put("tip", tip);
	}*/
	public String saveApply(int apInfoId,String reTypeName, String woTypeName, String expectSalary, String telephone, String personalInfo, String publisherId){
		String nullMess = "";
		int num = 0;
		if(reTypeName==null||reTypeName.replaceAll(" ","").length()==0){
			nullMess = nullMess + "招聘类型、";
			num++;
		}
		if(woTypeName==null||woTypeName.replaceAll(" ","").length()==0){
			nullMess = nullMess + "工作类型、";
			num++;
		}
		if(expectSalary==null||expectSalary.replaceAll(" ", "").length()==0){
			nullMess = nullMess + "期望薪资、";
			num++;
		}
		if(num>=2){
			return "未填写"+nullMess;
		}
		//****************数据操作对象**********************
		ApplyInfoContentDAO contentDAO = 
				(ApplyInfoContentDAO)context.getBean("ApplyInfoContentDAOImpl");
		RecuitTypeDAO recuitTypeDAO = 
				(RecuitTypeDAO) context.getBean("RecuitTypeDAOImpl");
		WorkTypeDAO workTypeDAO = 
				(WorkTypeDAO) context.getBean("WorkTypeDAOImpl");
		
		ApplyInfoContent originCon = null;
		if(apInfoId!=0){
			originCon = contentDAO.findById(apInfoId);
			if(originCon==null){
				log.debug("修改操作:要修改的帖子不存在");
				return "要修改的帖子不存在";
			}
		}

		// *******获取有ID属性的对应ID********************
		int recuitId;
		int workId;
		List recuitList = recuitTypeDAO.findByReTypeName(reTypeName);
		List workList = workTypeDAO.findByWoTypeName(woTypeName);
		//若在数据库中无此记录则将此记录录入
		if(recuitList.size()==0){
			RecuitType type = new RecuitType();
			type.setReTypeName(reTypeName);
			recuitTypeDAO.save(type);
			recuitList = recuitTypeDAO.findByReTypeName(reTypeName);
		}
		if(workList.size()==0){
			WorkType type = new WorkType();
			type.setWoTypeName(woTypeName);
			workTypeDAO.save(type);
			workList = workTypeDAO.findByWoTypeName(woTypeName);
		}
		//取出ID
		recuitId = ((RecuitType)recuitList.get(0)).getRecuitId();
		workId = ((WorkType)workList.get(0)).getWorkId();
		
		if(originCon!=null){
			log.debug("执行修改操作...");
			if(!publisherId.equals(originCon.getPublisherId())){
				return "无权修改";
			}
			originCon.setPersonalInfo(personalInfo);
			originCon.setRecuitId(recuitId);
			originCon.setExpectSalary(expectSalary);
			originCon.setTelephone(telephone);
			originCon.setWorkId(workId);
			return "success";
		}
		
		log.debug("执行发布操作...");
		//新建一个RecuitInfoContent对象》》设置参数》》持久化
		ApplyInfoContent con =  new ApplyInfoContent();
		con.setPublisherId(publisherId);
		con.setExpectSalary(expectSalary);
		con.setRecuitId(recuitId);
		con.setPersonalInfo(personalInfo);
		con.setTelephone(telephone);
		con.setWorkId(workId);
		con.setPublicTime(new Timestamp(System.currentTimeMillis()));
		log.debug("保存招聘帖。。。");
		contentDAO.save(con);
		
		return "success";
	}
	/**
	 * 修改求职信息
	 * @param json
	 * @return
	 */
	public String updateApply(String json){
		ApplyInfoContent applyInfoContent = 
				(ApplyInfoContent)JsonUtil.jsonToObject(json, ApplyInfoContent.class);
		ApplyInfoContentDAO contentDAO = 
				(ApplyInfoContentDAO)context.getBean("ApplyInfoContentDAOImpl");
		try{
			contentDAO.update(applyInfoContent);
			tip = "suuccess";
		}catch(RuntimeException re){
			tip = "error";
		}
		return JsonUtil.put("tip", tip);
	}
	/**
	 * 删除求职信息
	 * @param json
	 * @return
	 */
	public String deleteApply(String json){
		//获取要删除的求职Id
		int apInfoId = Integer.parseInt(JsonUtil.getString("apInfoId", json));
		ApplyInfoContentDAO contentDAO = 
				(ApplyInfoContentDAO)context.getBean("ApplyInfoContentDAOImpl");
		try{
			contentDAO.deleteById(apInfoId);
			log.debug("=========success==========");
			tip = "suuccess";
		}catch(RuntimeException re){
			tip = "error";
			log.debug("=========error==========");
		}
		return JsonUtil.put("tip", tip);
	}
	
	/**
	 * 查找列表
	 * @param json
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public JSONArray findList(String type,int first) throws JSONException, IOException{
		ApplyViewDAO applyViewDAO =
				(ApplyViewDAO)context.getBean("ApplyViewDAO");
		try{
			List list;
			if(type!=null&&!type.equals("全部")&&type.length()!=0)
				list = applyViewDAO.findList(type, first, 10);
			else
				list = applyViewDAO.findList(first, 10);
			return jsonUtil.objectToArray(list,false);
		}catch(RuntimeException e){
			return null;
		}
	}
	
	public String applyDetail(HttpServletRequest request,int apInfoId){
			ApplyViewDAO recuitViewDAO = 
				 (ApplyViewDAO)context.getBean("ApplyViewDAO");
		 //获取
		 ApplyView applyView = recuitViewDAO.findById(apInfoId);
		 if(applyView==null){
			 return "error";
		 }
		request.setAttribute("applyBean",applyView);
		return "success";
	}
	public void saveClick(Integer apInfoId){
		ApplyClickDAO dao =
				(ApplyClickDAO)context.getBean("ApplyClickDAO");
		ApplyClick model = dao.findById(apInfoId);
		if(model==null){
			dao.save(new ApplyClick(apInfoId,1));
		}else{
			model.setClickNum(model.getClickNum()+1);
		}
	}
	/**
	 * 查看详细信息
	 * @param json
	 * @return
	 */
	public String findByApplyId(String json){
		 int apInfoId = Integer.parseInt(JsonUtil.getString("apInfoId", json));
		 ApplyViewDAO applyViewDAO =
					(ApplyViewDAO)context.getBean("ApplyViewDAO");
		 ApplyView applyView = applyViewDAO.findById(apInfoId);
		 json = JsonUtil.objectToJson(applyView);
		 log.debug(json);
		 return json;
	 }
}
