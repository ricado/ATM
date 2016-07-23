package com.atm.service.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.Config;
import com.atm.dao.PeopleAttentionAssociationDAO;
import com.atm.model.PeopleAttentionAssociation;
import com.atm.model.define.user.UserList;
import com.atm.util.Application;

public class AttentService implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(AttentService.class);

	/**
	 * �ҹ�ע��
	 * 
	 * @param userAttentId
	 */
	public List<UserList> findMyAttent(String userAttentId) {
		List<UserList> userLists = getAttentDAO().findByUserAttentId(
				userAttentId);
		log.info("==========" + userAttentId + "��ע����===========");
		showUserList(userLists);
		return userLists;
	}

	/**
	 * ��ע�ҵ�
	 * 
	 * @param userAttentedId
	 */
	public List<UserList> findAttentedMe(String userAttentedId) {
		List<UserList> userLists = getAttentDAO().findByUserAttentedId(
				userAttentedId);
		log.info("==========��ע" + userAttentedId + "����===========");
		showUserList(userLists);
		return userLists;
	}

	public List<UserList> findOtherAttented(String userAttentedId, String userId) {
		//��ȡuserAttentedId��ע��
		List<UserList> userLists = getAttentDAO().findByUserAttentId(userAttentedId);;
		//��ȡ�ҹ�ע��
		List<UserList> userLists2 = findMyAttent(userId);
		
		List<UserList> list = new ArrayList<UserList>();
		// �ж�flag
		for (UserList userList : userLists) {
			userList.setFlag(Config.RELATIONSHIP_NO_MATTER);
			for (UserList userList1 : userLists2) {
				// �û��Ѿ���ע��
				if (userList.getUserId().equals(userList1.getUserId())) {
					userList.setFlag(Config.RELATIONSHIP_ATTENTION);
					break;
				}
			}
			list.add(userList);
		}
		showUserList(list);
		return list;
	}

	public List<UserList> findOtherFans(String userAttentedId, String userId) {
		//��ȡ���˵ķ�˿
		List<UserList> userLists = getAttentDAO().findByUserAttentedId(userAttentedId);
		//��ȡ�ҹ�ע��
		List<UserList> userLists2 = findMyAttent(userId);
		
		List<UserList> list = new ArrayList<UserList>();
		// �ж�flag
		for (UserList userList : userLists) {
			userList.setFlag(Config.RELATIONSHIP_NO_MATTER);
			for (UserList userList1 : userLists2) {
				// �û��Ѿ���ע��
				if (userList.getUserId().equals(userList1.getUserId())) {
					userList.setFlag(Config.RELATIONSHIP_ATTENTION);
					break;
				}
			}
			list.add(userList);
		}
		showUserList(list);
		return list;
	}

	/**
	 * ��ӹ�ע��Ϣ
	 * 
	 * @param userAttentId
	 * @param userAttentedId
	 */
	public void addAttent(String userAttentId, String userAttentedId) {
		PeopleAttentionAssociation attent = new PeopleAttentionAssociation(
				userAttentId, userAttentedId);
		getAttentDAO().save(attent);
	}

	/**
	 * ȡ����ע
	 * 
	 * @param userAttentId
	 * @param userAttentedId
	 */
	public void cancelAttent(String userAttentId, String userAttentedId) {
		log.info("--------" + userAttentId + "ȡ����ע" + userAttentedId
				+ "-----------");
		PeopleAttentionAssociation attent = new PeopleAttentionAssociation(
				userAttentId, userAttentedId);
		getAttentDAO().delete(attent);
		log.info("ȡ����ע�ɹ�");
	}

	/**
	 * ��ʾ�û���Ϣ
	 * 
	 * @param userLists
	 */
	public void showUserList(List<UserList> userLists) {
		for (int i = 0; i < userLists.size(); i++) {
			UserList userList = userLists.get(i);
			log.info("userId:" + userList.getUserId());
			log.info("nickname:" + userList.getNickname());
			log.info("sex:" + userList.getSex());
			log.info("path:" + userList.getHeadImagePath());
			log.info("flag:" + userList.getFlag());
			log.info("------------------------------");
		}
		log.info("==================end==================");
	}

	public PeopleAttentionAssociationDAO getAttentDAO() {
		PeopleAttentionAssociationDAO attentDAO = (PeopleAttentionAssociationDAO) context
				.getBean("PeopleAttentionAssociationDAOImpl");
		return attentDAO;
	}

	public int getRelationShip(String userId1, String userId2) {
		List list = getAttentDAO().findByAttendUserId(userId1);
		log.info(list.size() + "");
		for (int i = 0; i < list.size(); i++) {
			PeopleAttentionAssociation association = (PeopleAttentionAssociation) list
					.get(i);
			log.info(association.getUserAttendId() + "--"
					+ association.getUserAttendedId());
			if (association.getUserAttendedId().equals(userId2)) {
				log.info("��ע��");
				return Config.RELATIONSHIP_ATTENTION;
			}
		}
		log.info("û�й�ϵ");
		return Config.RELATIONSHIP_NO_MATTER;
	}
}
