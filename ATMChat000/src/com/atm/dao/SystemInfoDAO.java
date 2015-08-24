package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.SystemInfo;

public interface SystemInfoDAO extends AtmDAO{

	//property constants
	public static final String USER_ID = "userId";
	public static final String SYS_CONTENT = "sysContent";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(SystemInfo transientInstance);

	public abstract void delete(SystemInfo persistentInstance);

	public abstract SystemInfo findById(java.lang.Integer id);

	public abstract List findByExample(SystemInfo instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserId(Object userId);

	public abstract List findBySysContent(Object sysContent);

	public abstract List findAll();

	public abstract SystemInfo merge(SystemInfo detachedInstance);

	public abstract void attachDirty(SystemInfo instance);

	public abstract void attachClean(SystemInfo instance);

}