package com.atm.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.user.UserInfoDAO;
import com.atm.model.user.Department;
import com.atm.model.user.Major;
import com.atm.model.user.School;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.util.Application;
import com.atm.util.JsonUtil;

public class JsonTest implements Application{
	 private static final Logger log = LoggerFactory.getLogger(JsonTest.class);
	public static void main(String[] args) {
		JsonTest test = new JsonTest();
		//String str = test.objectToJson();
		//test.jsonToObject(str);
		//test.jsonToObject1(str);
		/*String string = test.getJson();
		System.out.println(string);
		String idOrEmail = test.jsonToString(string);
		System.out.println(idOrEmail);*/
		//测试getString
		/*String json = test.mapToJson();
		System.out.println(json);
		String name = JsonUtil.getString("name", json);
		System.out.println(name)*/
		//测试json to onject 和 object to json
		/* log.debug("object to object>>>>>>>>>>>>>>>>>>>>>>>");
		 String json = test.objectToJson();
		 log.debug(json);
		 log.debug("json to object>>>>>>>>>>>>>>>>>>>>>>>>>");
		 UserInfo user = (UserInfo)test.jsonToObject(json, UserInfo.class);
		 log.debug("userId:" + user.getUserId());
		 log.debug("userPwd:" + user.getMajor().getMname());
		 //log.debug("sex:" + userInfo.getSex());
		 log.debug("<<<<<<<<<<<<结束>>>>>>>>>>>>>>");*/
		/*log.debug("--------------");
		String json = test.mapToJson();
		log.debug(json);
		log.debug("--------------");
		Map<String, Object> map = test.jsonToMap(json);
		User user = (User) JsonUtil.jsonToObject(map.get("user"), User.class) ;
		String userId = (String)map.get("userId");
		log.debug(user.getUserPwd());
		log.debug(userId);*/
		test.date();
	}
	//object to json
	public String objectToJson1(){
		User user1 = new User(); //user1
		User user2 = new User(); //user2
		user1.setUserId("10001");
		user1.setUserPwd("131544215");
		user2.setUserId("10002");
		user2.setUserPwd("131544216");
		List list = new ArrayList(); //列表
		list.add(user1);
		list.add(user2);
		JSONArray array = JSONArray.fromObject(list);//生成列表
		System.out.println(array);
		System.out.println(array.toString());
		return array.toString();
	}
	//json to Object
	public Object[] jsonToObject1(String jsonString){
		//JSONObject object = new JSONObject();
		//JSONObject jsonObj = object.accumulate("list", jsonString);
		JSONArray array = JSONArray.fromObject(jsonString); //字符串-->json
        Object[] objects = array.toArray(); //json-->Onjects
        System.out.println(objects.length);
        for(int i=0;i<objects.length;i++){
        	JSONObject obj = JSONObject.fromObject(objects[0]);
        	User user = (User)obj.toBean(obj, User.class);
        	System.out.println(user.getUserId() + " " + user.getUserPwd());
        }
        return objects;
	}
	public String getJson(){
		String idOrEmail = "1169833934@qq.com";
		JSONObject json = new JSONObject();
		json.put("idOrEmail", idOrEmail);
		return json.toString();
	}
	public String jsonToString(String string){
		JSONObject json = JSONObject.fromObject(string);
		return json.getString("idOrEmail");
	}
	
	public String mapToJson1(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "jiong");
		map.put("id", "123");
		return JsonUtil.mapToJson(map);
	}
	public String objectToJson(){
		UserInfoDAO userInfoDAO =  (UserInfoDAO)context.getBean("UserInfoDAOImpl");
		UserInfo user = userInfoDAO.findById("10001");
		/*UserDAO userDAO = (UserDAO)context.getBean("UserDAOImpl");
		User user = userDAO.findById("10001");*/
		String json = JsonUtil.objectToJson(user);
		return json;
	}
	public Object jsonToObject(String json,Class clazz){
		return JsonUtil.jsonToObject(json, clazz);
	}
	public String objectToJson2(){
		//用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("12345");
		userInfo.setName("荷兰");
		userInfo.setFlag(1);
		//学校
		School school = new School();
		school.setScNo("100001");
		school.setScName("广东金融大学");
		Department department = new Department("10001", school, "计科");
		Major major = new Major("10001", department, "计科");
		userInfo.setMajor(major);
		return JsonUtil.objectToJson(userInfo);
	}
	public String mapToJson(){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User("10001", "131544215");
		map.put("user", user);
		map.put("userId", "10002");
		return JsonUtil.mapToJson(map);
	}
	public Map jsonToMap(String json){
		return JsonUtil.jsonToMap(json);
	}
	public void date(){
		String formart = new SimpleDateFormat("yyyy\\MM\\dd").format(new Date());
		System.out.println(formart);
	}
	
}
