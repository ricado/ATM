package com.atm.service.crowd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.chat.PrivateChatDAO;
import com.atm.model.chat.PrivateChat;
import com.atm.util.Application;
import com.atm.util.TimeUtil;

/**
 * ˽�ĵ����ҵ���߼���
 * @author ye
 * @2015.08.05
 */
public class PrivateChatService implements Application{
	private static final Logger log =
			LoggerFactory.getLogger(PrivateChatService.class);
	public static void savePrivateChat(PrivateChat chat){
		//��ȡ˽�����DAO
		PrivateChatDAO chatDAO = getPrivateChatDAO();
		
		chat.setSendTime(TimeUtil.getTimestamp());
		log.info(">>>>>>>>>>save");
		chatDAO.save(chat);
		log.info(">>>>>>>>>>success");
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @param userId
	 * @return
	 */
	public static List<PrivateChat> getOffLineRecord(String userId){
		PrivateChatDAO chatDAO = getPrivateChatDAO();
		List<PrivateChat> list = chatDAO.findByUserReceiveId(userId);
		for(Iterator<PrivateChat> iterator=list.iterator();iterator.hasNext();){
			PrivateChat chat = (PrivateChat)iterator.next();
			log.info(chat.getSendTime() + ":" + chat.getSendContent());
		}
		/*String json = JsonUtil.listToJson(list);
		log.info(json);*/
		return list;
	}
	
	/**
	 * ��ȡPrivateChatDAO
	 * @return
	 */
	private static PrivateChatDAO getPrivateChatDAO(){
		return (PrivateChatDAO)context.getBean("PrivateChatDAOImpl");
	}
}
