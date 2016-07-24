package com.atm.dao.chat;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.chat.ChatRecord;

public interface ChatRecordDAO {

	//property constants
	public static final String USER_ID = "userId";
	public static final String CROWD_ID = "crowdId";
	public static final String SEND_CONTENT = "sendContent";
	public static final String FLAG = "flag";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(ChatRecord transientInstance);

	public abstract void delete(ChatRecord persistentInstance);

	public abstract ChatRecord findById(java.lang.Integer id);

	public abstract List findByExample(ChatRecord instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserId(Object userId);

	public abstract List findByCrowdId(Object crowdId);

	public abstract List findBySendContent(Object sendContent);

	public abstract List findByFlag(Object flag);

	public abstract List findAll();

	public abstract ChatRecord merge(ChatRecord detachedInstance);

	public abstract void attachDirty(ChatRecord instance);

	public abstract void attachClean(ChatRecord instance);

}