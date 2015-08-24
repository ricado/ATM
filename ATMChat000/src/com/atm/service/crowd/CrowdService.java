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
 * 群聊的相关处理类，与CrowdAction配用
 * 
 * @author ye
 * @2015.8.1
 */

public class CrowdService implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdService.class);

	private String crowdRecord(String crowdId) {
		// 获取群聊的当前信息
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
	 * 新建群聊
	 * 
	 * @param userId
	 *            群主id
	 * @param crowdChat
	 *            新群的信息
	 * @return 创建成功则CrowdListInfo,否则返回null
	 */
	public int saveCrowdChat(String userId, CrowdChat crowdChat) {
		// Map<String, Object> map = JsonUtil.jsonToMap(json);
		// 群聊model
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
			// 获取群列表的群信息model
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
	 * 获取群聊消息
	 * 
	 * @param crowdId
	 */
	// TODO 获取群聊信息
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
	 * 查找某一个人的所有群
	 * @param userId
	 * @return
	 */
	public List<CrowdList> findUsersCrowd(String userId,int first,int max) {
		CrowdChatDAO chatDAO =
				(CrowdChatDAO)context.getBean("CrowdChatDAOImpl");
		List<CrowdList> crowdLists = chatDAO.findUsersCrowd(userId,first,max);
		log.info("===================" + userId + "的群:"
				+ "===============================");
		for (Iterator iterator = crowdLists.iterator(); iterator.hasNext();) {
			CrowdList crowdList = (CrowdList) iterator.next();
			log.info("群号:" + crowdList.getCrowdId());
			log.info("群名:" + crowdList.getCrowdName());
			log.info("人数:" + crowdList.getCrowdNum() + "/" + crowdList.getNumLimit());
			log.info("-------------------------");
		}
		log.info("========================================");
		return crowdLists;
	}

	/**
	 * 通过输入的词汇，在群名和标签中寻找出符合的群
	 * 
	 * @param string
	 * @return
	 */
	public List<CrowdInfo> fingCrowdByNameOrLabel(String string,int first,int max) {
		CrowdChatDAO crowdChatDAO = 
				(CrowdChatDAO)context.getBean("CrowdChatDAOImpl");
		int type = 1;
		// 字符串是否为空
		if (string != null && string != "") {
			try {
				int num = Integer.parseInt(string);
				type = 0;
			} catch (Exception e) {
				log.info("------不能转化成int");
				type = 1;
			}
		}
		List<CrowdInfo> list = crowdChatDAO.findCrowd(string,type,first,max);
		log.info("===================" + "关于" + string + "的群:"
				+ "===============================");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CrowdInfo crowdChat = (CrowdInfo) iterator.next();
			log.info("群号:" + crowdChat.getCrowdId());
			log.info("群名:" + crowdChat.getCrowdName());
			log.info("群标签:" + crowdChat.getCrowdLabel());
			log.info("path:" + crowdChat.getCrowdHeadImage());
			log.info("-------------------------");
		}
		log.info("========================================");
		return list;
	}

	/**
	 * 查找热门群，即热门的人数多的群
	 * 
	 * @param userId
	 */
	public List<CrowdList> foundHotCrowd(int first, int max) {
		CrowdChatDAO chatDAO = (CrowdChatDAO) context
				.getBean("CrowdChatDAOImpl");
		List<CrowdList> chats = chatDAO.findHotCrowd(first, max);
		log.info("=========================热门的群==========================");
		for (Iterator<CrowdList> iterator = chats.iterator(); iterator.hasNext();) {
			CrowdList chat =  (CrowdList)iterator.next();
			log.info("crowdId:" + chat.getCrowdId());
			log.info("crowdName:" + chat.getCrowdName());
			log.info("人数: " + chat.getCrowdNum() + "/" + chat.getNumLimit());
			log.info("-----------------------------------------------------");
		}
		log.info("========================================================");
		return chats;
	}

	/**
	 * 查找可能感兴趣的群，根据用户的标签，查找出用户可能感兴趣的群
	 */
	public List<CrowdList> foundInterestingCrowd(String userId, int first, int max) {
		CrowdChatDAO chatDAO = (CrowdChatDAO) context
				.getBean("CrowdChatDAOImpl");
		List<CrowdList> chats = chatDAO.findInterestingCrowd(userId, first, max);
		log.info("=========================" + userId + "可能感兴趣的群==========================");
		for (Iterator<CrowdList> iterator = chats.iterator(); iterator.hasNext();) {
			CrowdList chat =  (CrowdList)iterator.next();
			log.info("crowdId:" + chat.getCrowdId());
			log.info("crowdName:" + chat.getCrowdName());
			log.info("人数: " + chat.getCrowdNum() + "/" + chat.getNumLimit());
			log.info("path:" + chat.getCrowdHeadImage());
			log.info("-----------------------------------------------------");
		}
		log.info("========================================================");
		return chats;
	}
}
