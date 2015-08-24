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
	 * ���Ⱥ�ĳ�Ա
	 * 
	 * @param userId
	 *            ��ԱID
	 * @param crowdId
	 *            ȺID
	 * @param roleId
	 *            ��ɫ
	 * @return 
	 */
	public String saveCrowdMenber(String userId, int crowdId, int roleId) {
		CrowdMenber crowdMenber = new CrowdMenber();
		// Ⱥ��DAO
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
	 * ��ȡÿ����Ա
	 * 
	 * @param crowdId
	 * @return
	 */
	public List<MenberList> findAllMenber(int crowdId) {
		CrowdMenberDAO menberDAO = 
				(CrowdMenberDAO)context.getBean("CrowdMenberDAOImpl");
		List<MenberList> menberLists = menberDAO.findAllMenber(crowdId);
		log.info("=============" + crowdId + "�ĳ�Ա==================");
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
	 * ��ȡÿ������Ա
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
			if (!(objects[5].toString().equals("ȺԱ"))) {
				managers.add(objects[0].toString());
			}
		}
		return managers;
	}

	/**
	 * �Ƿ�ΪȺ��
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public boolean isHeadman(String userId, int crowdId) {
		if (getCrowdRole(userId, crowdId) == 1) {
			log.info(userId + "Ϊ" + crowdId + "��Ⱥ��");
			return true;
		}
		return false;
	}

	/**
	 *  �Ƿ�Ϊ����Ա
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public boolean isManager(String userId, int crowdId) {
		int role = getCrowdRole(userId, crowdId);
		if (role == 1 || role == 2) {
			log.info(userId + "Ϊ" + crowdId + "�Ĺ���Ա");
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ�ΪȺ��Ա
	 * @param userId
	 * @param crowdId
	 * @return
	 */
	public boolean isgrouper(String userId, int crowdId) {
		if (getCrowdRole(userId, crowdId) != 0) {
			log.info(userId + "��" + crowdId + "��Ⱥ��Ա");
			return true;
		}
		return false;
	}

	/**
	 *  �жϳ�Ա��ɫ
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
		// ֻ��һ��
		CrowdMenber menber = (CrowdMenber) list.get(0);
		return menber.getRoleId();
	}

	/**
	 * �Ƿ�ΪȺ��Ա
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
	 * �ж�Ⱥ�Ƿ��Ѿ�������
	 * 
	 * @param crowdID
	 * @return ���˷���true,���򷵻�false
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
	 * �����˽�Ⱥ
	 * 
	 * @param intiveId
	 * @param intivedId
	 * @param crowdId
	 */
	public void intiveToCrowd(String intiveId, String intivedId,
			int crowdId) {
		CrowdIntiveDAO crowdIntiveDAO = (CrowdIntiveDAO) context
				.getBean("CrowdIntiveDAO");
		// ����crowdIntive
		CrowdIntive crowdIntive = new CrowdIntive();
		crowdIntive.setIntiveId(intiveId);
		crowdIntive.setIntivedId(intivedId);
		crowdIntive.setCrowdId(crowdId);
		log.info("save.....");
		crowdIntiveDAO.save(crowdIntive);
	}

	/**
	 * �����Ⱥ
	 * 
	 * @param applyId
	 * @param crowdId
	 */
	public void applyToCrowd(String applyId, int crowdId) {
		// �����ȺDAO
		VerifyInfoDAO infoDAO = (VerifyInfoDAO) context
				.getBean("VerifyInfoDAOImpl");
		VerifyInfo verifyInfo = new VerifyInfo();
		verifyInfo.setContent("�ҵ�������Ƿ���");
		verifyInfo.setCrowdId(crowdId);
		verifyInfo.setIsSuccess(false);
		verifyInfo.setUserId(applyId);

		// ������Ϣ
		infoDAO.save(verifyInfo);
	}
	/**
	 *  ɾ��������Ϣ
	 * @param applyId
	 * @param crowdId
	 */
	public void deleteApply(String applyId, int crowdId) {
		VerifyInfoDAO dao = (VerifyInfoDAO) context
				.getBean("VerifyInfoDAOImpl");
		dao.deleteApply(applyId, crowdId);
	}

	/**
	 *  ɾ��������Ϣ
	 * @param intivedId
	 * @param crowdId
	 */
	public void deleteIntive(String intivedId, int crowdId) {
		CrowdIntiveDAO intiveDAO = (CrowdIntiveDAO) context
				.getBean("CrowdIntiveDAO");
		intiveDAO.deleteIntive(intivedId, crowdId);
	}
	
	/**
	 * ��ӹ���Ա
	 * @param userId
	 * @param crowdId
	 */
	public void addManager(String userId,int crowdId){
		int i = getMenberDAO().addManager(userId, crowdId);
		log.info("i:" + i);
	}
	
	/**
	 * ɾ������Ա
	 * @param userId
	 * @param crowdId
	 */
	public void cancelManager(String userId,int crowdId){
		int i = getMenberDAO().cancelManager(userId, crowdId);
		log.info("i:" + i);
	}
	
	/**
	 * ��ȡCrowdMenberDAO
	 * @return
	 */
	public CrowdMenberDAO getMenberDAO(){
		return (CrowdMenberDAO)context.getBean("CrowdMenberDAOImpl");
	}
}
