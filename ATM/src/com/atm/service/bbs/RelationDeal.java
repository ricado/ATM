package com.atm.service.bbs;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO��
 * @fileName : com.atm.service.RelationDeal.java
 * date | author | version |   
 * 2015��8��11�� | Jiong | 1.0 |
 */
public class RelationDeal  implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	private JSONObject sendJson;
	private JSONArray sendArray;
	
	//��ʼ��
	private void init(){
		sendJson = new JSONObject();
		sendArray = new JSONArray();
	}
	
	/*public JSONArray getMyAttendPeople(String userId) throws JSONException, IOException{
		init();
		MyAttendDAO myAttendDao = context.getBean("MyAttendDAO",MyAttendDAO.class);
		List list = myAttendDao.findByUserAttendId(userId);
		sendArray = jsonUtil.objectToArray(list);
		return sendArray;
	}*/
}

