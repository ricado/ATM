package com.atm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.RecuitInfoContentDAO;
import com.atm.daoDefined.RecuitViewDAO;
import com.atm.model.RecuitInfoContent;
import com.atm.model.define.RecuitView;
import com.atm.util.Application;
import com.atm.util.JsonUtil;

/**
 * 招聘的相关业务逻辑
 * @author ye
 * @2015.8.2
 */
public class RecuitService implements Application{
	private static final Logger log = 
			LoggerFactory.getLogger(RecuitService.class);
	public String tip = "";
	public RecuitService() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 保存招聘信息
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	 public String saveRecuit(String json){
		RecuitInfoContentDAO contentDAO = 
				(RecuitInfoContentDAO)context.getBean("RecuitInfoContentDAOImpl");
		//json -> RecuitInfoContent
		RecuitInfoContent recuitInfoContent = 
				(RecuitInfoContent) JsonUtil.jsonToObject(json, RecuitInfoContent.class);
		try{
			contentDAO.save(recuitInfoContent);
			log.debug("========save recuit success=======");
			tip =  "success";
		}catch(RuntimeException e){
			log.debug("===========error=========");
			tip = "error";
		}
		return JsonUtil.put("tip", tip);
	 }
	 /**
	  * 修改招聘信息
	  * @param json
	  * @param request
	  * @param response
	  * @return
	  */
	 public String updateRecuit(String json){
		RecuitInfoContentDAO contentDAO = 
				(RecuitInfoContentDAO)context.getBean("RecuitInfoContentDAOImpl");
		//json -> RecuitInfoContent
		RecuitInfoContent recuitInfoContent = 
				(RecuitInfoContent) JsonUtil.jsonToObject(json, RecuitInfoContent.class);
		try{
			contentDAO.update(recuitInfoContent);
			log.debug("========save recuit success=======");
			tip = "success";
		}catch(RuntimeException e){
			log.debug("===========error=========");
			tip = "error";
		}
		return JsonUtil.put("tip", tip);
	 }
	 
	 /**
	  * 删除某个特定主键对应的招聘信息
	  * @param json
	  * @param request
	  * @param response
	  * @return
	  */
	 public String delete(String json){
		 RecuitInfoContentDAO contentDAO = 
					(RecuitInfoContentDAO)context.getBean("RecuitInfoContentDAOImpl");
			//json -> RecuitInfoContent
		String reInfoId = JsonUtil.getString("reInfoId", json);
		try{
			contentDAO.deleteByProperty("reInfoId", reInfoId);
			log.debug("========save recuit success=======");
			tip = "success";
		}catch(RuntimeException e){
			log.debug("===========error=========");
			tip ="error";
		}
		return JsonUtil.put("tip", tip);
	 }
	 
	 /**
	  * 查找招聘列表所需要的信息
	  * @param json
	  * @param request
	  * @param response
	  * @return
	  */
	 public String findList(String json){
		 RecuitViewDAO recuitViewDAO = 
				 (RecuitViewDAO)context.getBean("RecuitViewDAO");
		 int first = Integer.parseInt(JsonUtil.getString("first", json));
		 int max = Integer.parseInt(JsonUtil.getString("max", json));
		 String string = JsonUtil.getString("str", json);
		 List list  = recuitViewDAO.findList(string,first, max);
		 json = JsonUtil.listArrayToJson(list);
		 log.debug(json);
		 //TODO
		 return json;
	 }
	 
	 /**
	  * 查看某一招聘信息的详细信息
	  * @param json
	  * @param request
	  * @param response
	  * @return json
	  */
	 public String findByRecuitId(String json){
		 int reInfoId = Integer.parseInt(JsonUtil.getString("reInfoId", json));
		 RecuitViewDAO recuitViewDAO = 
				 (RecuitViewDAO)context.getBean("RecuitViewDAO");
		 //获取
		 RecuitView recuitView = recuitViewDAO.findById(reInfoId);
		 json = JsonUtil.objectToJson(recuitView);
		 log.debug(json);
		 return json;
	 }
}
