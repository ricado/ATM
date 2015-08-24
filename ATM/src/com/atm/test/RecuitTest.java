package com.atm.test;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.RecuitInfoContentDAO;
import com.atm.model.RecuitInfoContent;
import com.atm.service.RecuitService;
import com.atm.util.Application;
import com.atm.util.JsonUtil;
import com.atm.util.TimeUtil;

public class RecuitTest implements Application{
	private static final Logger log =
			LoggerFactory.getLogger(RecuitTest.class);
	public static void main(String[] args) {
		RecuitTest recuitTest = new RecuitTest();
		//recuitTest.saveRecuit();
		recuitTest.findList();
	}
	
	public void saveRecuit(){
		RecuitInfoContentDAO contentDAO = 
				(RecuitInfoContentDAO)context.getBean("RecuitInfoContentDAOImpl");
		RecuitInfoContent content = new RecuitInfoContent();
		content.setRecuitId(3);
		content.setPublisherId("10001");
		content.setRecContent("我公司是一流的XXX");
		content.setSalary("200/day");
		content.setTelephone("10086");
		content.setWorkAddress("广州、龙洞");
		content.setPublishTime(TimeUtil.getTimestamp());
		content.setWorkId(5);
			
		String json = JsonUtil.objectToJson(content);
		log.debug("json : " + json);
		RecuitService service = new RecuitService();
		json = service.saveRecuit(json);
		String tip = JsonUtil.getString("tip", json);
		log.debug("=========="+ tip + "=============");
	}
	
	public void findList(){
		int first = 1;
		int max = 3;
		String str = "";
		JSONObject object = new JSONObject();
		object.put("first", first);
		object.put("max", max);
		object.put("str", str);
		String json = object.toString();
		log.debug(json);
		RecuitService service = new RecuitService();
		json = service.findList(json);
		
	}
}
