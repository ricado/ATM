package com.atm.test;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.chat.ChatRecordDAO;
import com.atm.dao.chat.CrowdChatDAO;
import com.atm.dao.chat.CrowdMenberDAO;
import com.atm.daoDefined.MySessionDAO;
import com.atm.daoDefined.chat.CrowdRecordViewDAO;
import com.atm.model.chat.ChatRecord;
import com.atm.model.chat.CrowdChat;
import com.atm.model.chat.CrowdMenber;
import com.atm.model.define.chat.CrowdRecordView;
import com.atm.service.crowd.CrowdMenberService;
import com.atm.service.crowd.CrowdService;
import com.atm.util.Application;
import com.atm.util.FileUtil;
import com.atm.util.TimeUtil;

/**
 * 
 * @author ye
 * @2015.8.2
 */
public class CrowdTest implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdService.class);
	private CrowdMenberService crowdMenberService = new CrowdMenberService();

	public static void main(String[] args) {
		CrowdTest test = new CrowdTest();
		// test.getCrowdMenberInfo();
		// test.getCrowdMenebrInfoBySQL();
		// test.getCrowdAllMenber();

		// test.crowdRecord(10001);
		/*
		 * CrowdChat chat = test.saveCrowd();
		 * log.debug(chat.getCrowdId()+"=============");
		 */
		/*
		 * log.debug("save CrowdReord-------------------");
		 * test.saveCrowdRecord(); log.debug("save success-------------------");
		 */
		// 查找我的群
		// test.findMyCrowd();
		// 查找与互联网有关的群
		// test.fingCrowdByNameOrLabel("互联网");
		// 群是否已满
		// CrowdService.isFull(10001);

		// 邀请进群
		// test.intiveToCrowd();

		// 查找感兴趣的群
		// test.findIterestingCrowd();
		// 查找热门群
		// test.findHotCrowd();
		// 查找群
		// test.fingCrowdByNameOrLabel();
		// 查找我的群
		test.findUserCrowd();
		// test.getCrowdInfo();
		// test.getHeadImage();

	}

	/**
	 * 查询某个群聊的聊天记录。
	 * 
	 * @param crowdId
	 * @return
	 */
	private String crowdRecord(int crowdId) {
		// 获取群聊的当前信息
		CrowdRecordViewDAO viewDAO = (CrowdRecordViewDAO) context
				.getBean("CrowdRecordViewDAO");
		List list = viewDAO.findByCrowdId(crowdId);
		log.debug("list.size:" + list.size()
				+ "===================================");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CrowdRecordView recordView = (CrowdRecordView) iterator.next();
			log.debug(recordView.getNickname() + ":"
					+ recordView.getSendContent());
		}
		return null;
	}

	/**
	 * 保存群的信息
	 * 
	 * @return
	 */
	public CrowdChat saveCrowd() {
		CrowdChatDAO crowdChatDAO = (CrowdChatDAO) context
				.getBean("CrowdChatDAOImpl");
		CrowdChat crowdChat = new CrowdChat();
		// crowdChat.setCrowdId();
		crowdChat.setCrowdName("广金609");
		crowdChat.setCrowdLabel("宿舍生活");
		crowdChat.setNumLimit(10000);
		crowdChat.setCrowdDescription("宿舍是每一位成员睡觉的地方");
		crowdChat.setIsHidden(false);
		crowdChat.setVerifyMode("1");
		log.debug(crowdChat.getCrowdId() + "==================");
		crowdChatDAO.save(crowdChat);
		return crowdChat;
	}

	/**
	 * 保存群聊的聊天记录
	 * 
	 * @return
	 */
	public String saveCrowdRecord() {
		ChatRecordDAO chatRecordDAO = (ChatRecordDAO) context
				.getBean("ChatRecordDAOImpl");
		ChatRecord chatRecord = new ChatRecord();
		// chatRecord.setCrowdId();
		chatRecord.setFlag(1);
		// chatRecord.setRecordId(5);
		chatRecord.setSendContent("不要说粗话好么");
		chatRecord.setSendTime(TimeUtil.getTimestamp());
		chatRecord.setUserId("10002");
		chatRecordDAO.save(chatRecord);
		log.debug("save");
		return null;
	}

	/**
	 * 得到聊天信息
	 * 
	 * @param crowdId
	 * @return
	 */
	public CrowdChat getCrowdChat(int crowdId) {
		CrowdChatDAO crowdChatDAO = (CrowdChatDAO) context
				.getBean("CrowdChatDAOImpl");
		CrowdChat crowdChat = crowdChatDAO.findById(crowdId);
		return crowdChat;
	}

	/**
	 * 寻找user所加入的群
	 */
	public void findMyCrowd() {
		CrowdMenberDAO crowdMenberDAO = (CrowdMenberDAO) context
				.getBean("CrowdMenberDAOImpl");
		List list = crowdMenberDAO.findByUserId("10001");
		log.debug("===================" + "10001:的群:"
				+ "===============================");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CrowdMenber crowdMenber = (CrowdMenber) iterator.next();
			log.debug("群号:" + crowdMenber.getCrowdId());
			log.debug("角色号:" + crowdMenber.getRoleId());
		}
	}

	/**
	 * 通过输入的词汇，在群名和标签中寻找出符合的群
	 * 
	 * @param string
	 * @return
	 */
	public void fingCrowdByNameOrLabel() {
		// CrowdService.fingCrowdByNameOrLabel("计算机");
	}

	public void getCrowdMenberInfo() {
		log.info("===========begin===============");
		MySessionDAO mySession = (MySessionDAO) context.getBean("MySessionDAO");
		List list = mySession.getQuery("crowdMenberInfo").setString(0, "10001")
				.setInteger(1, 10001).list();
		log.info("list size:" + list.size());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			// CrowdMenberInfo crowdMenberInfo =
			// (CrowdMenberInfo)iterator.next();
			/*
			 * log.info("userId:" + crowdMenberInfo.getUserId());
			 * log.info("crowdId:" + crowdMenberInfo.getCrowdId());
			 * log.info("name:" + crowdMenberInfo.getName());
			 * log.info("nickName:" + crowdMenberInfo.getNickName());
			 * log.info("crowdName:" + crowdMenberInfo.getCrowdName());
			 * log.info("roleName:" + crowdMenberInfo.getRoleName());
			 */
		}
		log.info("===========end===============");
	}

	public void getCrowdMenebrInfoBySQL() {
		log.info("=============begin=============");
		MySessionDAO mySessionDAO = (MySessionDAO) context
				.getBean("MySessionDAO");
		/*
		 * List list = mySessionDAO.getSqlQuery("{call crowdMenberInfo(?,?)}")
		 * .setString(0, "10001") .setInteger(1,10001) .list();
		 */
		List list = mySessionDAO.getCrowdMenberInfo("10001", 10001);
		log.info("list size:" + list.size());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			log.info("userId:" + objects[0]);
			log.info("crowdId:" + objects[1]);
			log.info("crowdName:" + objects[2]);
			log.info("nickName:" + objects[3]);
			log.info("name:" + objects[4]);
			log.info("roleName:" + objects[5]);
		}
		log.info("===========end===============");
	}

	public void getCrowdAllMenber() {
		log.info("=============begin=============");
		MySessionDAO mySessionDAO = (MySessionDAO) context
				.getBean("MySessionDAO");
		List list = mySessionDAO.getAllCrowdMenberInfo(10001);
		log.info("list size:" + list.size());

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			log.info("userId:" + objects[0]);
			log.info("crowdId:" + objects[1]);
			log.info("crowdName:" + objects[2]);
			log.info("nickName:" + objects[3]);
			log.info("name:" + objects[4]);
			log.info("roleName:" + objects[5]);
			log.info("===================================");
		}
		log.info("===========end===============");
	}

	public void isGull() {
		crowdMenberService.isFull(10001);
	}

	public void intiveToCrowd() {
		crowdMenberService.intiveToCrowd("10001", "10002", 10003);
		log.info("successs");
	}

	public void findIterestingCrowd() {
		CrowdService crowdService = new CrowdService();
		crowdService.foundInterestingCrowd("10005", 0, 10);
	}

	public void findHotCrowd() {
		CrowdService crowdService = new CrowdService();
		crowdService.foundHotCrowd(0, 2);
	}

	public void getCrowdInfo() {
		CrowdService crowdService = new CrowdService();
		/*
		 * List<CrowdInfo> crowdInfos = crowdService.getCrowdInfo(10001);
		 * CrowdInfo crowdInfo = crowdInfos.get(0); log.info("crowdId" +
		 * crowdInfo.getCrowdId()); log.info("crowdName:" +
		 * crowdInfo.getCrowdName()); log.info("crowdNum:" +
		 * crowdInfo.getCrowdNum());
		 */
	}

	public void getHeadImage() {
		try {
			byte[] bytes = FileUtil.makeFileToByte("F:/image/well.jpg");
			new com.atm.chat.nio.server.handler.ImageHandler("jpg", bytes, 1,
					"10001").start();
		} catch (Exception e) {
			log.info("error");
		}

	}

	public void findUserCrowd() {
		CrowdService crowdService = new CrowdService();
		crowdService.findUsersCrowd("10005", 0, 10);
	}
}