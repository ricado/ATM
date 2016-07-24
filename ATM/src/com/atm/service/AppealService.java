package com.atm.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.AppealDAO;
import com.atm.dao.impl.AppealDAOImpl;
import com.atm.model.Appeal;
import com.atm.util.Application;
import com.atm.util.JsonUtil;

/**
 * 申诉的业务逻辑类
 * @author ye
 * @2015.08.02
 */
public class AppealService implements Application{
	private static final Logger log = LoggerFactory.getLogger(AppealService.class);
	
	/**
	 * 保存申诉的必要信息
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	public String saveAppeal(String json,HttpServletRequest request,
			HttpServletResponse response){
		//获取DAO
		AppealDAO appealDao = (AppealDAO)context.getBean("AppealDAOImpl");
		Appeal appeal = (Appeal)JsonUtil.jsonToObject(json, Appeal.class);
		log.debug("=======" + appeal.getPhotoPath());
		try{
			log.debug("=========save appeal===========");
			appealDao.save(appeal);
			log.debug("=========save appeal success=======");
			return "success";
		}catch(RuntimeException e){
			e.printStackTrace();
			log.debug("=========error=============");
			return "error";
		}
		
	}
}
