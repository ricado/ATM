package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.VerifyInfo;

public interface VerifyInfoDAO extends AtmDAO{

	//property constants
	public static final String USER_ID = "userId";
	public static final String CROWD_ID = "crowdId";
	public static final String IS_SUCCESS = "isSuccess";
	public static final String CONTENT = "content";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(VerifyInfo transientInstance);

	public abstract void delete(VerifyInfo persistentInstance);

	public abstract VerifyInfo findById(java.lang.Integer id);

	public abstract List findByExample(VerifyInfo instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserId(Object userId);

	public abstract List findByCrowdId(Object crowdId);

	public abstract List findByIsSuccess(Object isSuccess);

	public abstract List findByContent(Object content);

	public abstract List findAll();

	public abstract VerifyInfo merge(VerifyInfo detachedInstance);

	public abstract void attachDirty(VerifyInfo instance);

	public abstract void attachClean(VerifyInfo instance);

	public abstract int deleteApply(String applyId, int crowdId);

}