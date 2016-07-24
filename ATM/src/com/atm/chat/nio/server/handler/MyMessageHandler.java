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

public class MyMessageHandler extends BufferHandler implements Application {

	private static final Logger log = LoggerFactory
			.getLogger(MyMessageHandler.class);
	private static JSONObject jsonObject = new JSONObject();
	private static MyMessageDAO myMessageDAO = (MyMessageDAO) context
			.getBean("MyMessageDAO");

	public void opera(int config) {
		switch (config) {
		case Config.MY_OFF_MESSAGE:
			sendOffMyMessage();
			break;
		default:
			break;
		}
	}

	/**
	 * ������Ϣ
	 * 
	 * @param userId
	 * @param type
	 * @param content
	 * @throws JSONException
	 */
	public void sendMyMessage(String userId, int type, String content)
			throws JSONException {
		SocketChannel socketChannel = map.get(userId);
		MyMessage message = new MyMessage(userId, type, content);
		if (socketChannel == null) {
			// �û������� ������ݿ�
			myMessageDAO.save(message);
		} else {
			// ��������Ϣ
			sendMyMessage(message);
		}
	}

	/**
	 * �������ߵġ��ҵ���Ϣ��
	 * 
	 * @throws Exception
	 */
	public void sendOffMyMessage() {
		try {
			String userId = getString();
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
		log.info("�����ҵ���Ϣ:userId:" + myMessage.getUserId() + "type:"
				+ myMessage.getType());

		//��װ��json
		jsonObject.put("userId", myMessage.getUserId());
		jsonObject.put("type", myMessage.getType());
		jsonObject.put("content", myMessage.getContent());
		String json = jsonObject.toString();

		//д��buffer
		buffer = ByteBuffer.allocateDirect(8 + json.getBytes().length);
		buffer.putInt(Config.MY_OFF_MESSAGE);
		put(json);

		writeBuffer(map.get(myMessage.getUserId()));
	}

}
