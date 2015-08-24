package com.atm.util;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;


/**
 * 
 * @version 1.0
 * @author ye
 * @time 2015.7.28 10:19
 * @作用: Json的格式转换工具类，包括json->对象数组  json<->list json<->Map 
 */
public class JsonUtil {
	
	/**
	 * 得到单个key
	 * @param key
	 * @param myjson
	 * @return
	 */
	public static String getString(String key,String myjson){
		JSONObject json = JSONObject.fromObject(myjson);
		return	json.getString(key);
	}
	public static void jsonToList(){
		
	}
	/**
	 * json -> map
	 * @param json
	 * @return
	 */
	public static Map jsonToMap(String json){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	    while (it.hasNext())  
	     {  
	           String key = String.valueOf(it.next());  
	           Object value =  jsonObject.get(key);
	           map.put(key, value);  
	       }  
	    return map;  
	}
	public static Object jsonToObject(Object json,Class clazz){  
	    String[] formats={"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};  
	    JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));  
	    JSONObject jsonObject=JSONObject.fromObject(json);  
	    return JSONObject.toBean(jsonObject,clazz);  
	}
	/**
	 * json字符串转换成Object数组
	 * @param jsonString
	 * @return Object[]
	 */
	public static Object[] jsonToObjects(String jsonString,Class clazz){
		JSONArray array = JSONArray.fromObject(jsonString); //字符串-->json
        Object[] objects = array.toArray(); //json-->Onjects
        System.out.println(objects.length);
        for(int i=0;i<objects.length;i++){
        	//JSONObject obj = JSONObject.fromObject(objects[i]);//把对象转换成JSONObject实例   	
        	//objects[i] = obj.toBean(obj,clazz);  //再把JSONObject实例转换成所想要的对象
        	objects[i] = jsonToObject(objects[i], clazz);
        }
        return objects;
	}
	/**
	 * list转换成json
	 * @param list
	 * @return jsonString
	 */
	public static String listToJson(List list){
		String json = "[";
		for(Iterator iterator=list.iterator();iterator.hasNext();){
			json += objectToJson(iterator.next());
			if(iterator.hasNext()){
				json +=",";
			}else{
				json +="]";
			}
		}
		JSONArray array = JSONArray.fromObject(json);//生成列表
		//System.out.println(array);
		System.out.println(array.toString());
		return array.toString();
	}
	public static String listToJsonNoTime(List list){
		return null;
	}
	public static String mapToJson(Map map){
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}
	  
	public static void objectToJson(){
		
	}
	public static String objectToJson(Object o){  
	    JsonConfig config=new JsonConfig();  
	    config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));  
	    JSONObject json=JSONObject.fromObject(o,config); 
	    return json.toString();
	}
	
	public static String put(String key,String value){
		JSONObject json = new JSONObject();
		json.put(key, value);
		return json.toString();
	}
	
	
	public static String listArrayToJson(List list){
		JSONArray array = JSONArray.fromObject(list);
		return array.toString();
	}
}
