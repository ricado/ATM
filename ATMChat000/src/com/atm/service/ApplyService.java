package com.atm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.action.ApplyAction;
import com.atm.dao.ApplyInfoContentDAO;
import com.atm.daoDefined.ApplyViewDAO;
import com.atm.model.ApplyInfoContent;
import com.atm.model.define.ApplyView;
import com.atm.util.Application;
import com.atm.util.JsonUtil;

public class ApplyService implements Application{
	private static final Logger log = LoggerFactory.getLogger(ApplyAction.class);
	private String tip = "";
	
	/**
	 * 保存求职信息
	 * @param json
	 * @return
	 */
	public String saveApplay(String json){
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
	 */
	public String findList(String json){
		int first = Integer.parseInt(JsonUtil.getString("first", json));
		int max = Integer.parseInt(JsonUtil.getString("max", json));
		String str = JsonUtil.getString("string", json);
		ApplyViewDAO applyViewDAO =
				(ApplyViewDAO)context.getBean("ApplyViewDAO");
		try{
			List list = applyViewDAO.findList(str, first, max);
			json = JsonUtil.listToJson(list);
			log.debug(json);
			return json;
		}catch(RuntimeException e){
			return null;
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
