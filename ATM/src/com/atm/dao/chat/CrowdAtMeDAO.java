package com.atm.dao.chat;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.chat.CrowdAtMe;

public interface CrowdAtMeDAO extends AtmDAO{

	//property constants
	public static final String USER_AT_OTHER_ID = "userAtOtherId";
	public static final String USER_BE_AT_ID = "userBeAtId";
	public static final String CROWD_ID = "crowdId";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(CrowdAtMe transientInstance);

	public abstract void delete(CrowdAtMe persistentInstance);

	public abstract CrowdAtMe findById(java.lang.Integer id);

	public abstract List findByExample(CrowdAtMe instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserAtOtherId(Object userAtOtherId);

	public abstract List findByUserBeAtId(Object userBeAtId);

	public abstract List findByCrowdId(Object crowdId);

	public abstract List findAll();

	public abstract CrowdAtMe merge(CrowdAtMe detachedInstance);

	public abstract void attachDirty(CrowdAtMe instance);

	public abstract void attachClean(CrowdAtMe instance);

}