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
 * ��Ƹ�����ҵ���߼�
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
	 * ������Ƹ��Ϣ
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
	  * �޸���Ƹ��Ϣ
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
	  * ɾ��ĳ���ض�������Ӧ����Ƹ��Ϣ
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
	  * ������Ƹ�б�����Ҫ����Ϣ
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
	  * �鿴ĳһ��Ƹ��Ϣ����ϸ��Ϣ
	  * @param json
	  * @param request
	  * @param response
	  * @return json
	  */
	 public String findByRecuitId(String json){
		 int reInfoId = Integer.parseInt(JsonUtil.getString("reInfoId", json));
		 RecuitViewDAO recuitViewDAO = 
				 (RecuitViewDAO)context.getBean("RecuitViewDAO");
		 //��ȡ
		 RecuitView recuitView = recuitViewDAO.findById(reInfoId);
		 json = JsonUtil.objectToJson(recuitView);
		 log.debug(json);
		 return json;
	 }
}
