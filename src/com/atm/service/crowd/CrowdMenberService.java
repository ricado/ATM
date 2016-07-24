package com.atm.service.crowd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.VerifyInfoDAO;
import com.atm.dao.chat.CrowdMenberDAO;
import com.atm.daoDefined.MySessionDAO;
import com.atm.daoDefined.chat.CrowdIntiveDAO;
import com.atm.model.VerifyInfo;
import com.atm.model.chat.CrowdMenber;
import com.atm.model.define.chat.CrowdInfo;
import com.atm.model.define.chat.CrowdIntive;
import com.atm.model.define.chat.MenberList;
import com.atm.util.Application;

public class CrowdMenberService implements Application{
	private static final Logger log = LoggerFactory
			.getLogger(CrowdMenberService.class);
	
	/**
	 * 添加群的成员
	 * 
	 * @param userId
	 *            成员ID
	 * @param crowdId
	 *            群ID
	 * @param roleId
	 *            角色
	 * @return 
	 */
	public String saveCrowdMenber(String userId, int crowdId, int roleId) {
		CrowdMenber crowdMenber = new CrowdMenber();
		// 群聊DAO
		CrowdMenberDAO crowdMenberDAO = (CrowdMenberDAO) context
				.getBean("CrowdMenberDAOImpl");
		crowdMenber.setCrowdId(crowdId);
		crowdMenber.setUserId(userId);
		crowdMenber.setRoleId(roleId);
		try {
			crowdMenberDAO.save(crowdMenber);
			return "success";
		} catch (RuntimeException e) {
			return "error";
		}
	}

	/**
	 * 获取每个成员
	 * 
	 * @param crowdId
	 * @return
	 */
	public List<MenberList> findAllMenber(int crowdId) {
		CrowdMenberDAO menberDAO = 
				(CrowdMenberDAO)context.getBean("CrowdMenberDAOImpl");
		List<MenberList> menberLists = menberDAO.findAllMenber(crowdId);
		log.info("=============" + crowdId + "的成员==================");
		for(int i=0;i<menberLists.size();i++){
			MenberList menberList = menberLists.get(i);
			
			log.info("userId:" + menberList.getUserId());
			log.info("nickname:" + menberList.getNickname());
			log.info("headImagePath:" + menberList.getHeadImagePath());
			log.info("roleID:" + menberList.getRoleId());
			log.info("---------------------------------------------");
		}
		log.info("==========end========================");
		return menberLists;
	}

	/**
	 * 获取每个管理员
	 * 
	 * @param crowdId
	 * @return
	 */
	public List<String> getCrowdManagers(int crowdId) {
		MySessionDAO mySessionDAO = (MySessionDAO) context
				.getBean("MySessionDAO");
		List list = mySessionDAO.getAllCrowdMenberInfo(crowdId);
		List<String> managers = new ArrayList<String>();
		int i = 0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			if (!(objects[5].toString().equals("群员"))) {
				managers.add(objects[0].toString());
			}
		}
		return managers;
	}

	/**
	 * 是否为群主
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public boolean isHeadman(String userId, int crowdId) {
		if (getCrowdRole(userId, crowdId) == 1) {
			log.info(userId + "为" + crowdId + "的群主");
			return true;
		}
		return false;
	}

	/**
	 *  是否为管理员
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public boolean isManager(String userId, int crowdId) {
		int role = getCrowdRole(userId, crowdId);
		if (role == 1 || role == 2) {
			log.info(userId + "为" + crowdId + "的管理员");
			return true;
		}
		return false;
	}

	/**
	 * 是否为群成员
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public boolean isgrouper(String userId, int crowdId) {
		if (getCrowdRole(userId, crowdId) != 0) {
			log.info(userId + "是" + crowdId + "的群成员");
			return true;
		}
		return false;
	}

	/**
	 *  判断成员角色
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public int getCrowdRole(String userId, int crowdId) {
		CrowdMenberDAO menberDAO = (CrowdMenberDAO) context
				.getBean("CrowdMenberDAOImpl");

		String HQL = "from CrowdMenber c where userId='" + userId
				+ "' and crowdId='" + crowdId + "'";
		List list = menberDAO.findByHQL(HQL);

		if (list.size() == 0) {
			return 0;
		}
		// 只有一个
		CrowdMenber menber = (CrowdMenber) list.get(0);
		return menber.getRoleId();
	}

	/**
	 * 是否为群成员
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public boolean isCrowder(String userId, int crowdId) {
		int i = getCrowdRole(userId, crowdId);
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 判断群是否已经满人了
	 * 
	 * @param crowdID
	 * @return 满人返回true,否则返回false
	 */
	public int isFull(int crowdID) {
		log.info("-----------isfull");
		MySessionDAO session = (MySessionDAO) context.getBean("MySessionDAO");
		List<CrowdInfo> list = session.getCrowdInfo(crowdID);
		log.info(">>>>>>>>>>>>>>" + list.size() + ">>>>>>>>>>>>>>");
		CrowdInfo crowdInfo = (CrowdInfo) list.get(0);
		try {
			log.info("numlimit:" + crowdInfo.getNumLimit());
			log.info("num:" + crowdInfo.getCrowdNum());
			if (crowdInfo.getCrowdNum() == crowdInfo.getNumLimit()) {
				return 1;
			}
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 邀请人进群
	 * 
	 * @param intiveId
	 * @param intivedId
	 * @param crowdId
	 */
	public void intiveToCrowd(String intiveId, String intivedId,
			int crowdId) {
		CrowdIntiveDAO crowdIntiveDAO = (CrowdIntiveDAO) context
				.getBean("CrowdIntiveDAO");
		// 设置crowdIntive
		CrowdIntive crowdIntive = new CrowdIntive();
		crowdIntive.setIntiveId(intiveId);
		crowdIntive.setIntivedId(intivedId);
		crowdIntive.setCrowdId(crowdId);
		log.info("save.....");
		crowdIntiveDAO.save(crowdIntive);
	}

	/**
	 * 申请进群
	 * 
	 * @param applyId
	 * @param crowdId
	 */
	public void applyToCrowd(String applyId, int crowdId) {
		// 申请进群DAO
		VerifyInfoDAO infoDAO = (VerifyInfoDAO) context
				.getBean("VerifyInfoDAOImpl");
		VerifyInfo verifyInfo = new VerifyInfo();
		verifyInfo.setContent("我的咸鱼就是翻身");
		verifyInfo.setCrowdId(crowdId);
		verifyInfo.setIsSuccess(false);
		verifyInfo.setUserId(applyId);

		// 保存信息
		infoDAO.save(verifyInfo);
	}
	/**
	 *  删除申请信息
	 * @param applyId
	 * @param crowdId
	 */
	public void deleteApply(String applyId, int crowdId) {
		VerifyInfoDAO dao = (VerifyInfoDAO) context
				.getBean("VerifyInfoDAOImpl");
		dao.deleteApply(applyId, crowdId);
	}

	/**
	 *  删除邀请信息
	 * @param intivedId
	 * @param crowdId
	 */
	public void deleteIntive(String intivedId, int crowdId) {
		CrowdIntiveDAO intiveDAO = (CrowdIntiveDAO) context
				.getBean("CrowdIntiveDAO");
		intiveDAO.deleteIntive(intivedId, crowdId);
	}
	
	/**
	 * 添加管理员
	 * @param userId
	 * @param crowdId
	 */
	public void addManager(String userId,int crowdId){
		int i = getMenberDAO().addManager(userId, crowdId);
		log.info("i:" + i);
	}
	
	/**
	 * 删除管理员
	 * @param userId
	 * @param crowdId
	 */
	public void cancelManager(String userId,int crowdId){
		int i = getMenberDAO().cancelManager(userId, crowdId);
		log.info("i:" + i);
	}
	
	/**
	 * 获取CrowdMenberDAO
	 * @return
	 */
	public CrowdMenberDAO getMenberDAO(){
		return (CrowdMenberDAO)context.getBean("CrowdMenberDAOImpl");
	}
}
