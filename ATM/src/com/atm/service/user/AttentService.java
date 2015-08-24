package com.atm.service.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.PeopleAttentionAssociationDAO;
import com.atm.model.PeopleAttentionAssociation;
import com.atm.model.define.user.UserList;
import com.atm.util.Application;

public class AttentService implements Application{
	private static final Logger log =
			LoggerFactory.getLogger(AttentService.class);
	/**
	 * �ҹ�ע��
	 * 
	 * @param userAttentId
	 */
	public List<UserList> findMyAttent(String userAttentId) {
		List<UserList> userLists = getAttentDAO().findByUserAttentId(userAttentId);
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
		List<UserList> userLists = getAttentDAO().findByUserAttentedId(userAttentedId);
		log.info("==========��ע" + userAttentedId + "����===========");
		showUserList(userLists);
		return userLists;
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
			log.info("------------------------------");
		}
		log.info("==================end==================");
	}	
	public PeopleAttentionAssociationDAO getAttentDAO(){
		PeopleAttentionAssociationDAO attentDAO = (PeopleAttentionAssociationDAO) context
				.getBean("PeopleAttentionAssociationDAOImpl");
		return attentDAO;
	}
}
