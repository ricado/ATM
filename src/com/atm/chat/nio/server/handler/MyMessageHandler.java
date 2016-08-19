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
import com.atm.util.JsonUtil;

/**
 * �ҵ���Ϣ ʹ��json
 * 
 * @author ye
 *
 * @data 2016-7-31
 */
public class MyMessageHandler extends BufferHandler implements Application {

	private static final Logger log = LoggerFactory.getLogger(MyMessageHandler.class);
	private static JSONObject jsonObject = new JSONObject();
	private static MyMessageDAO myMessageDAO = (MyMessageDAO) context.getBean("MyMessageDAO");

	public MyMessageHandler() {
	}

	public void operate(int type) throws Exception {
		switch (type) {
		case Config.ISHAS_NEW_MYMESSAGE:
			isHasNewMessage();
			break;
		case Config.MYMESSAGE:
			sendMyMessage();
			break;
		default:
			break;
		}
	}

	/**
	 * 0,@ 1���ظ� 2ϵͳ
	 * 
	 * @throws Exception
	 */
	public void isHasNewMessage() throws Exception {
		JSONObject json = new JSONObject(getString());
		String userId = json.getString("userId");
		isHasNewMessage(userId);
	}

	public void isHasNewMessage(String userId) throws JSONException {

		if (map.get(userId) == null) {
			return;
		}
		JSONObject json = new JSONObject();
		List<MyMessage> myMessages = new ArrayList<MyMessage>();
		for (int i = 0; i < 3; i++) {
			// ����@�ҵ�
			myMessages = myMessageDAO.findMyMessage(userId, i);
			json.put(i + "", myMessages.size());
		}
		// ����json
		sendJson(Config.ISHAS_NEW_MYMESSAGE, json.toString(), userId);
	}

	/**
	 * �����ҵ���Ϣ
	 * 
	 * @param userId
	 *            ������
	 * @param type
	 *            �ҵ���Ϣ����(@�ҵ�,�ظ��ҵĻ������ҵģ�ϵͳ��Ϣ)
	 * @param content
	 *            ��Ϣ����(json��ʽ ���ݰ����Զ�)
	 * @throws Exception
	 * @throws JSONException
	 */
	public void sendMyMessage() throws JSONException, Exception {
		JSONObject json = new JSONObject(getString());
		String userId = json.getString("userId");
		List<MyMessage> myMessages = new ArrayList<MyMessage>();
		myMessages = myMessageDAO.findMyMessage(userId, json.getInt("type"));
		json.put("messgae", JsonUtil.listToJson(myMessages));
		sendJson(Config.MY_MESSAGE, json.toString(), userId);
	}

	/**
	 * �����ҵ���Ϣ
	 * 
	 * @param userId
	 *            ������
	 * @param type
	 *            �ҵ���Ϣ����(0:@�ҵ�,1:�ظ��ҵĻ������ҵģ�2:ϵͳ��Ϣ)
	 * @param content
	 *            ��Ϣ����(json��ʽ ���ݰ����Զ�)
	 * @throws Exception
	 */
	public void sendMyMessage(String userId, int type, String content) throws Exception {
		// SocketChannel socketChannel = map.get(userId);
		MyMessage message = new MyMessage(userId, type, content);
		myMessageDAO.save(message);
		isHasNewMessage(userId);
	}

	/**
	 * �������ߵġ��ҵ���Ϣ��
	 * 
	 * @throws Exception
	 */
	public void sendOffMyMessage(String userId) {
		try {
			List<MyMessage> myMessages = new ArrayList<MyMessage>();
			if (userId != null && !userId.equals("")) {
				myMessages = myMessageDAO.findByUserId(userId);
			}
			// ������ɾ��
			for (MyMessage myMessage : myMessages) {
				sendMyMessage(myMessage);
				// ɾ��������Ϣ
				myMessageDAO.delete(myMessage);
			}
		} catch (Exception e) {
			log.info("��ȡuserIdʧ��");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.MY_RESULT_MESSAGE);
			buffer.putInt(Config.FAILED);
		}
	}

	/**
	 * �����ҵ���Ϣ
	 * 
	 * @param myMessage
	 * @return
	 * @throws JSONException
	 */
	private void sendMyMessage(MyMessage myMessage) throws JSONException {
		log.info("�����ҵ���Ϣ:userId:" + myMessage.getUserId() + "type:" + myMessage.getType());

		// ��װ��json
		jsonObject = new JSONObject();
		jsonObject.put("userId", myMessage.getUserId());
		jsonObject.put("type", myMessage.getType());
		jsonObject.put("content", myMessage.getContent());
		String json = jsonObject.toString();

		// д��buffer
		buffer = ByteBuffer.allocateDirect(8 + json.getBytes().length);
		buffer.putInt(Config.MY_OFF_MESSAGE);
		put(json);

		writeBuffer(map.get(myMessage.getUserId()));
	}

}
