package com.atm.service.crowd;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.chat.CrowdChatDAO;
import com.atm.dao.chat.CrowdMenberDAO;
import com.atm.daoDefined.MySessionDAO;
import com.atm.daoDefined.chat.CrowdListViewDAO;
import com.atm.daoDefined.chat.CrowdRecordViewDAO;
import com.atm.model.chat.CrowdChat;
import com.atm.model.chat.CrowdMenber;
import com.atm.model.define.chat.CrowdInfo;
import com.atm.model.define.chat.CrowdList;
import com.atm.model.define.chat.CrowdRecordView;
import com.atm.util.Application;

/**
 * Ⱥ�ĵ���ش����࣬��CrowdAction����
 * 
 * @author ye
 * @2015.8.1
 */

public class CrowdService implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdService.class);

	private String crowdRecord(String crowdId) {
		// ��ȡȺ�ĵĵ�ǰ��Ϣ
		CrowdRecordViewDAO viewDAO = (CrowdRecordViewDAO) context
				.getBean("CrowdRecordViewDAO");
		List list = viewDAO.findByCrowdId(crowdId);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CrowdRecordView recordView = (CrowdRecordView) iterator.next();
			log.debug(recordView.getNickname() + ":"
					+ recordView.getSendContent());
		}
		return null;
	}

	/**
	 * �½�Ⱥ��
	 * 
	 * @param userId
	 *            Ⱥ��id
	 * @param crowdChat
	 *            ��Ⱥ����Ϣ
	 * @return �����ɹ���CrowdListInfo,���򷵻�null
	 */
	public int saveCrowdChat(String userId, CrowdChat crowdChat) {
		// Map<String, Object> map = JsonUtil.jsonToMap(json);
		// Ⱥ��model
		/*
		 * CrowdChat crowdChat = (CrowdChat) JsonUtil.jsonToObject(
		 * json.get("crowd"), CrowdChat.class);
		 */
		CrowdChatDAO crowdChatDAO = (CrowdChatDAO) context
				.getBean("CrowdChatDAOImpl");
		log.debug("save crowdChat -----------------");
		try {
			crowdChatDAO.save(crowdChat);
			new CrowdMenberService().saveCrowdMenber(userId, crowdChat.getCrowdId(), 1);
			log.debug("save success   -----------------");
			// ��ȡȺ�б��Ⱥ��Ϣmodel
			/*
			 * crowdListInfo crowdListInfo = new crowdListInfo(
			 * crowdChat.getCrowdId(), crowdChat.getCrowdName(),
			 * crowdChat.getCrowdHeadImage());
			 */
			return crowdChat.getCrowdId();
		} catch (RuntimeException e) {
			return -100;
		}

	}
	
	/**
	 * ��ȡȺ����Ϣ
	 * 
	 * @param crowdId
	 */
	// TODO ��ȡȺ����Ϣ
	public CrowdInfo getCrowdInfo(int crowdId) {
		MySessionDAO mySessionDAO = (MySessionDAO) context
				.getBean("MySessionDAO");
		List<CrowdInfo> crowdChats = mySessionDAO.getCrowdInfo(crowdId);
		if (crowdChats.size() >= 1) {
			return crowdChats.get(0);
		}
		return null;
	}

	/**
	 * ����ĳһ���˵�����Ⱥ
	 * @param userId
	 * @return
	 */
	public List<CrowdList> findUsersCrowd(String userId,int first,int max) {
		CrowdChatDAO chatDAO =
				(CrowdChatDAO)context.getBean("CrowdChatDAOImpl");
		List<CrowdList> crowdLists = chatDAO.findUsersCrowd(userId,first,max);
		log.info("===================" + userId + "��Ⱥ:"
				+ "===============================");
		for (Iterator iterator = crowdLists.iterator(); iterator.hasNext();) {
			CrowdList crowdList = (CrowdList) iterator.next();
			log.info("Ⱥ��:" + crowdList.getCrowdId());
			log.info("Ⱥ��:" + crowdList.getCrowdName());
			log.info("����:" + crowdList.getCrowdNum() + "/" + crowdList.getNumLimit());
			log.info("-------------------------");
		}
		log.info("========================================");
		return crowdLists;
	}

	/**
	 * ͨ������Ĵʻ㣬��Ⱥ���ͱ�ǩ��Ѱ�ҳ����ϵ�Ⱥ
	 * 
	 * @param string
	 * @return
	 */
	public List<CrowdInfo> fingCrowdByNameOrLabel(String string,int first,int max) {
		CrowdChatDAO crowdChatDAO = 
				(CrowdChatDAO)context.getBean("CrowdChatDAOImpl");
		int type = 1;
		// �ַ����Ƿ�Ϊ��
		if (string != null && string != "") {
			try {
				int num = Integer.parseInt(string);
				type = 0;
			} catch (Exception e) {
				log.info("------����ת����int");
				type = 1;
			}
		}
		List<CrowdInfo> list = crowdChatDAO.findCrowd(string,type,first,max);
		log.info("===================" + "����" + string + "��Ⱥ:"
				+ "===============================");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CrowdInfo crowdChat = (CrowdInfo) iterator.next();
			log.info("Ⱥ��:" + crowdChat.getCrowdId());
			log.info("Ⱥ��:" + crowdChat.getCrowdName());
			log.info("Ⱥ��ǩ:" + crowdChat.getCrowdLabel());
			log.info("path:" + crowdChat.getCrowdHeadImage());
			log.info("-------------------------");
		}
		log.info("========================================");
		return list;
	}

	/**
	 * ��������Ⱥ�������ŵ��������Ⱥ
	 * 
	 * @param userId
	 */
	public List<CrowdList> foundHotCrowd(int first, int max) {
		CrowdChatDAO chatDAO = (CrowdChatDAO) context
				.getBean("CrowdChatDAOImpl");
		List<CrowdList> chats = chatDAO.findHotCrowd(first, max);
		log.info("=========================���ŵ�Ⱥ==========================");
		for (Iterator<CrowdList> iterator = chats.iterator(); iterator.hasNext();) {
			CrowdList chat =  (CrowdList)iterator.next();
			log.info("crowdId:" + chat.getCrowdId());
			log.info("crowdName:" + chat.getCrowdName());
			log.info("����: " + chat.getCrowdNum() + "/" + chat.getNumLimit());
			log.info("-----------------------------------------------------");
		}
		log.info("========================================================");
		return chats;
	}

	/**
	 * ���ҿ��ܸ���Ȥ��Ⱥ�������û��ı�ǩ�����ҳ��û����ܸ���Ȥ��Ⱥ
	 */
	public List<CrowdList> foundInterestingCrowd(String userId, int first, int max) {
		CrowdChatDAO chatDAO = (CrowdChatDAO) context
				.getBean("CrowdChatDAOImpl");
		List<CrowdList> chats = chatDAO.findInterestingCrowd(userId, first, max);
		log.info("=========================" + userId + "���ܸ���Ȥ��Ⱥ==========================");
		for (Iterator<CrowdList> iterator = chats.iterator(); iterator.hasNext();) {
			CrowdList chat =  (CrowdList)iterator.next();
			log.info("crowdId:" + chat.getCrowdId());
			log.info("crowdName:" + chat.getCrowdName());
			log.info("����: " + chat.getCrowdNum() + "/" + chat.getNumLimit());
			log.info("path:" + chat.getCrowdHeadImage());
			log.info("-----------------------------------------------------");
		}
		log.info("========================================================");
		return chats;
	}
}
