package com.atm.chat.nio.server.handler;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.Config;
import com.atm.daoDefined.MyMessageDAO;
import com.atm.model.define.MyMessage;
import com.atm.util.Application;

/**
 * 我的消息 使用json
 * 
 * @author ye
 *
 * @data 2016-7-31
 */
public class MyMessageHandler extends BufferHandler implements Application {

	private static final Logger log = LoggerFactory
			.getLogger(MyMessageHandler.class);
	private static JSONObject jsonObject = new JSONObject();
	private static MyMessageDAO myMessageDAO = (MyMessageDAO) context
			.getBean("MyMessageDAO");

	/**
	 * 发送我的消息
	 * 
	 * @param userId
	 *            接受者
	 * @param type
	 *            我的消息类型(@我的,回复我的或评论我的，系统消息)
	 * @param content
	 *            消息内容(json格式 内容包含自定)
	 * @throws JSONException
	 */
	public void sendMyMessage(String userId, int type, String content)
			throws JSONException {
		SocketChannel socketChannel = map.get(userId);
		MyMessage message = new MyMessage(userId, type, content);
		if (socketChannel == null) {
			// 用户不在线 存进数据库
			myMessageDAO.save(message);
		} else {
			// 否则发送消息
			sendMyMessage(message);
		}
	}

	/**
	 * 发送离线的“我的消息”
	 * 
	 * @throws Exception
	 */
	public void sendOffMyMessage(String userId) {
		try {
			List<MyMessage> myMessages = new ArrayList<MyMessage>();
			if (userId != null && !userId.equals("")) {
				myMessages = myMessageDAO.findByUserId(userId);
			}
			// 发送与删除
			for (MyMessage myMessage : myMessages) {
				sendMyMessage(myMessage);
				// 删除离线消息
				myMessageDAO.delete(myMessage);
			}
		} catch (Exception e) {
			log.info("获取userId失败");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.MY_RESULT_MESSAGE);
			buffer.putInt(Config.FAILED);
		}
	}

	/**
	 * 发送我的消息
	 * 
	 * @param myMessage
	 * @return
	 * @throws JSONException
	 */
	private void sendMyMessage(MyMessage myMessage) throws JSONException {
		log.info("发送我的消息:userId:" + myMessage.getUserId() + "type:"
				+ myMessage.getType());

		// 封装成json
		jsonObject = new JSONObject();
		jsonObject.put("userId", myMessage.getUserId());
		jsonObject.put("type", myMessage.getType());
		jsonObject.put("content", myMessage.getContent());
		String json = jsonObject.toString();

		// 写进buffer
		buffer = ByteBuffer.allocateDirect(8 + json.getBytes().length);
		buffer.putInt(Config.MY_OFF_MESSAGE);
		put(json);

		writeBuffer(map.get(myMessage.getUserId()));
	}

}
