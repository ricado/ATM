package com.atm.service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.chat.ChatRecordDAO;
import com.atm.dao.chat.PrivateChatDAO;
import com.atm.daoDefined.MySessionDAO;
import com.atm.daoDefined.chat.PrivateChatRecordDAO;
import com.atm.model.chat.ChatRecord;
import com.atm.model.chat.PrivateChat;
import com.atm.model.define.chat.CrowdChatRecord;
import com.atm.model.define.chat.PrivateChatRecord;
import com.atm.util.Application;
import com.atm.util.TimeUtil;

/**
 * ��������ҵ���߼��ࡣ�����й���������񡣰���Ⱥ�ĺ�˽�ġ�������ɾ��ġ�
 * 
 * @author ye
 * 
 * @version 1.0
 *
 * @time 2015.8.12
 */
public class MessageService implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(MessageService.class);

	/**
	 * �����û���˽��������Ϣ
	 * 
	 * @param userId
	 * @return PrivateChat
	 */
	public static List<PrivateChatRecord> getOffLineMessage(String userId) {
		PrivateChatRecordDAO chatDAO = (PrivateChatRecordDAO) context
				.getBean("PrivateChatRecordDAO");
		List<PrivateChatRecord> chatList = chatDAO.findByUserReceiveId(userId);
		return chatList;
	}
	/*public static List<Object[]> getOffLineCrowdMessage(String userId) {
			MySessionDAO mySessionDAO = (MySessionDAO) context
					.getBean("MySessionDAO");
			List<Object[]> list = mySessionDAO.getCrowdOffRecord(userId);
			for (Iterator<Object[]> iterator = list.iterator(); iterator.hasNext();) {
				Object[] objects = iterator.next();
				Timestamp timestamp = (Timestamp) objects[2];
				log.info("crowdId:" + objects[0]);
				log.info("content:" + objects[1]);
				log.info("time: " + TimeUtil.getDateFormat(timestamp));
			}
			return list;
		}*/
	/**
	 * �����û���Ⱥ��������Ϣ
	 * 
	 * @param userId
	 * @return ChatRecord
	 */
	public static List<CrowdChatRecord> getOffLineCrowdMessage(String userId) {
		MySessionDAO mySessionDAO = (MySessionDAO) context
				.getBean("MySessionDAO");
		List<CrowdChatRecord> list = mySessionDAO.getCrowdOffRecord(userId);
		for (Iterator<CrowdChatRecord> iterator = list.iterator(); iterator.hasNext();) {
			CrowdChatRecord chat = iterator.next();
			Timestamp timestamp = chat.getSendTime();
			log.info("crowdId:" + chat.getCrowdId());
			log.info("content:" + chat.getSendContent());
			log.info("time: " + TimeUtil.getDateFormat(timestamp));
		}
		return list;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param userSendId
	 *            ������
	 * @param userReceiveId
	 *            ������
	 * @param sendContent
	 *            ����
	 * @param flag
	 *            ��Ϊδ��
	 * @author ye
	 * 
	 */
	public static void savePrivateChat(String userSendId, String userReceiveId,
			String sendContent, int flag) {
		PrivateChat chat = new PrivateChat();
		chat.setFlag(1);
		chat.setSendContent(sendContent);
		chat.setSendTime(TimeUtil.getTimestamp());
		chat.setUserReceiveId(userReceiveId);
		chat.setUserSendId(userSendId);
		PrivateChatDAO chatDAO = (PrivateChatDAO) context
				.getBean("PrivateChatDAOImpl");
		try {
			chatDAO.save(chat);
			log.info("����ɹ�");
		} catch (Exception e) {
			log.info("����ʧ��");
		}
	}

	public static void savePrivateChat(PrivateChatRecord chat) {
		chat.setSendTime(TimeUtil.getTimestamp());
		PrivateChatDAO chatDAO = (PrivateChatDAO) context
				.getBean("PrivateChatDAOImpl");
		PrivateChat privateChat = new PrivateChat();
		privateChat.setFlag(chat.getFlag());
		privateChat.setSendContent(chat.getSendContent());
		privateChat.setSendTime(chat.getSendTime());
		privateChat.setUserReceiveId(chat.getUserReceiveId());
		privateChat.setUserSendId(chat.getUserSendId());
		try {
			chatDAO.save(privateChat);
			log.info("����ɹ�");
		} catch (Exception e) {
			log.info("����ʧ��");
		}
	} 
	/**
	 * ����Ⱥ�ĵ�����
	 * 
	 * @param crowdId
	 *            Ⱥ��Id
	 * @param sendContent
	 *            ��������
	 */
	public static void saveCrowdRecord(int crowdId, String userId,String sendContent) {
		ChatRecord chatRecord = new ChatRecord();
		chatRecord.setUserId(userId);
		chatRecord.setCrowdId(crowdId);
		chatRecord.setSendContent(sendContent);
		chatRecord.setSendTime(TimeUtil.getTimestamp());
		chatRecord.setFlag(1);
		// ��ȡChatRecordDAO
		ChatRecordDAO chatRecordDAO = (ChatRecordDAO) context
				.getBean("ChatRecordDAOImpl");
		// TODO �������������Զ�ɾ������Ⱥ����Ա����������Ϣ
		// ����
		try {
			chatRecordDAO.save(chatRecord);
			log.info("����ɹ�");
		} catch (Exception e) {
			log.info("����ʧ��");
		}
	}
}
