package com.atm.dao.chat;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.chat.PrivateChat;

public interface PrivateChatDAO extends AtmDAO{

	//property constants
	public static final String USER_RECEIVE_ID = "userReceiveId";
	public static final String USER_SEND_ID = "userSendId";
	public static final String SEND_CONTENT = "sendContent";
	public static final String FLAG = "flag";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(PrivateChat transientInstance);

	public abstract void delete(PrivateChat persistentInstance);

	public abstract PrivateChat findById(java.lang.Integer id);

	public abstract List findByExample(PrivateChat instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserReceiveId(Object userReceiveId);

	public abstract List findByUserSendId(Object userSendId);

	public abstract List findBySendContent(Object sendContent);

	public abstract List findByFlag(Object flag);

	public abstract List findAll();

	public abstract PrivateChat merge(PrivateChat detachedInstance);

	public abstract void attachDirty(PrivateChat instance);

	public abstract void attachClean(PrivateChat instance);

	public abstract List findByReceiveAndSend(String receiveId, String sendId);

	public abstract void deleteByUserId(String userId);

}