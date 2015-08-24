package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.EssayAtMe;

public interface EssayAtMeDAO {

	// property constants
	public static final String USER_BE_AT_ID = "userBeAtId";
	public static final String USER_AT_OTHER_ID = "userAtOtherId";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(EssayAtMe transientInstance);

	public abstract void delete(EssayAtMe persistentInstance);

	public abstract EssayAtMe findById(java.lang.Integer id);

	public abstract List findByExample(EssayAtMe instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserBeAtId(Object userBeAtId);

	public abstract List findByUserAtOtherId(Object userAtOtherId);

	public abstract List findAll();

	public abstract EssayAtMe merge(EssayAtMe detachedInstance);

	public abstract void attachDirty(EssayAtMe instance);

	public abstract void attachClean(EssayAtMe instance);

}