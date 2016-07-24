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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @version 1.0
 * @author ye
 * @time 2015.7.28 10:19
 * @����: Json�ĸ�ʽת�������࣬����json->�������� json<->list json<->Map
 */
public class JsonUtil {
	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * �õ�����key
	 * 
	 * @param key
	 * @param myjson
	 * @return
	 */
	public static String getString(String key, String myjson) {
		JSONObject json = JSONObject.fromObject(myjson);
		return json.getString(key);
	}

	public static void jsonToList() {

	}

	/**
	 * json -> map
	 * 
	 * @param json
	 * @return
	 */
	public static Map jsonToMap(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator it = jsonObject.keys();
		// ����jsonObject���ݣ���ӵ�Map����
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			map.put(key, value);
		}
		return map;
	}

	public static Object jsonToObject(Object json, Class clazz) {
		String[] formats = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
		JSONUtils.getMorpherRegistry().registerMorpher(
				new TimestampMorpher(formats));
		JSONObject jsonObject = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObject, clazz);
	}

	/**
	 * json�ַ���ת����Object����
	 * 
	 * @param jsonString
	 * @return Object[]
	 */
	public static Object[] jsonToObjects(String jsonString, Class clazz) {
		JSONArray array = JSONArray.fromObject(jsonString); // �ַ���-->json
		Object[] objects = array.toArray(); // json-->Onjects
		System.out.println(objects.length);
		for (int i = 0; i < objects.length; i++) {
			// JSONObject obj =
			// JSONObject.fromObject(objects[i]);//�Ѷ���ת����JSONObjectʵ��
			// objects[i] = obj.toBean(obj,clazz); //�ٰ�JSONObjectʵ��ת��������Ҫ�Ķ���
			objects[i] = jsonToObject(objects[i], clazz);
		}
		return objects;
	}

	/**
	 * listת����json
	 * 
	 * @param list
	 * @return jsonString
	 */
	public static String listToJson(List list) {
		String json = "[";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			json += objectToJson(iterator.next());
			if (iterator.hasNext()) {
				json += ",";
			} else {
				json += "]";
			}
		}
		JSONArray array = JSONArray.fromObject(json);// �����б�
		// System.out.println(array);
		System.out.println(array.toString());
		return array.toString();
	}

	public static String listToJsonNoTime(List list) {
		return null;
	}

	public static String mapToJson(Map map) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = JSONObject.fromObject(map);
		jsonArray.add(jsonObject);
		return jsonArray.toString();
	}

	public static void objectToJson() {

	}

	public static String objectToJson(Object o) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject json = JSONObject.fromObject(o, config);
		return json.toString();
	}

	public static String put(String key, String value) {
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		json.put(key, value);
		jsonArray.add(json);
		log.info("jsonUtil--put: json:" + jsonArray.toString());
		return jsonArray.toString();
	}

	public static String listArrayToJson(List list) {
		JSONArray array = JSONArray.fromObject(list);
		return array.toString();
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static JSONObject getJsonObject(String json) {
		log.info("jsonUtil getJsonObejct:" + json);
		JSONArray jsonArray = JSONArray.fromObject(json);
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		return jsonObject;
	}

	public static String getJsonString(JSONObject jsonObject) {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);
		return jsonArray.toString();
	}
}
