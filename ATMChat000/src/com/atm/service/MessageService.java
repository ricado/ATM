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
 * 聊天的相关业务逻辑类。处理有关聊天的事务。包括群聊和私聊。可以增删查改。
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
	 * 查找用户的私信离线消息
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
	 * 查找用户的群的离线消息
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
	 * 保存离线消息
	 * 
	 * @param userSendId
	 *            发送人
	 * @param userReceiveId
	 *            接收人
	 * @param sendContent
	 *            内容
	 * @param flag
	 *            标为未读
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
			log.info("保存成功");
		} catch (Exception e) {
			log.info("保存失败");
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
			log.info("保存成功");
		} catch (Exception e) {
			log.info("保存失败");
		}
	} 
	/**
	 * 保存群聊的聊天
	 * 
	 * @param crowdId
	 *            群聊Id
	 * @param sendContent
	 *            发送内容
	 */
	public static void saveCrowdRecord(int crowdId, String userId,String sendContent) {
		ChatRecord chatRecord = new ChatRecord();
		chatRecord.setUserId(userId);
		chatRecord.setCrowdId(crowdId);
		chatRecord.setSendContent(sendContent);
		chatRecord.setSendTime(TimeUtil.getTimestamp());
		chatRecord.setFlag(1);
		// 获取ChatRecordDAO
		ChatRecordDAO chatRecordDAO = (ChatRecordDAO) context
				.getBean("ChatRecordDAOImpl");
		// TODO 做个触发器，自动删除所有群聊人员都看过的信息
		// 保存
		try {
			chatRecordDAO.save(chatRecord);
			log.info("保存成功");
		} catch (Exception e) {
			log.info("保存失败");
		}
	}
}
