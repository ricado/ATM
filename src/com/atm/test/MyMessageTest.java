package com.atm.test;

import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atm.daoDefined.MyMessageDAO;
import com.atm.model.define.MyMessage;

/**
 * @author ye
 * @dete 2016年9月8日 下午8:25:26
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 加载配置
public class MyMessageTest {

	@Autowired
	private MyMessageDAO myMessageDAO;

	@Test
	public void test() throws JSONException {
		String userId = "Lzj";
		int type = 1;
		org.json.JSONObject json = new org.json.JSONObject();
		List<MyMessage> messages = myMessageDAO.findMyMessage(userId, type);
		System.out.println(messages.size());
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < messages.size(); i++) {
			MyMessage message = messages.get(i);
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(message);
			jsonObject.put("content", JSONObject.parse(message.getContent()));
			jsonArray.add(jsonObject);
		}
		json.put("message", jsonArray);
		System.out.println(json.toString());
	}

}
