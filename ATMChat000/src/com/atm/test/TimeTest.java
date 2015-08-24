package com.atm.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atm.dao.chat.PrivateChatDAO;
import com.atm.dao.impl.chat.PrivateChatDAOImpl;
import com.atm.model.chat.PrivateChat;
import com.atm.util.HttpUtil;
import com.atm.util.JsonUtil;

public class TimeTest {
	public static void main(String[] args) {
		TimeTest test = new  TimeTest();
		//test.printTimestamp();
		/*String json = test.ObjectTojson();
		test.jsonToObject(json);*/
		test.mapTest();
	}
	public void printTimestamp(){
		PrivateChatDAO chatDAO = PrivateChatDAOImpl.getFromApplicationContext();
		System.out.println("send............");
		List list = chatDAO.findByReceiveAndSend("10001", "10002");
		System.out.println("ok.........");
		for(int i=0;i<list.size();i++){
			PrivateChat chat = (PrivateChat)list.get(i);
			Date date = new Date(chat.getSendTime().getTime());
			System.out.println(date);
		}
		System.out.println("===============");
		//String json = JsonUtil.listToJson(list);
	}
	
	public String ObjectTojson(){
		PrivateChatDAO chatDAO = PrivateChatDAOImpl.getFromApplicationContext();
		System.out.println("send............");
		List list = chatDAO.findByReceiveAndSend("10001", "10002");
		System.out.println("ok.........");
		for(int i=0;i<list.size();i++){
			PrivateChat chat = (PrivateChat)list.get(i);
			System.out.println(chat.getUserReceiveId() + ": " + chat.getSendContent());
		}
		System.out.println("===============");
		String json = JsonUtil.listToJson(list);
		return json;
	}
	
	
	public void jsonToObject(String json){
		System.out.println(json);
		Object[] objects = JsonUtil.jsonToObjects(json, PrivateChat.class);
		for(int i = 0;i<objects.length;i++){
			PrivateChat chat = (PrivateChat)objects[i];
			System.out.println(chat.getSendTime());
		}
	}
	public void mapTest(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", "c");
		
		String a = map.get("4");
		System.out.println(a);
		
	}
}
