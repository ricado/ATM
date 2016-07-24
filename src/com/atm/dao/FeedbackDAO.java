package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.Feedback;

public interface FeedbackDAO extends AtmDAO{

	//property constants
	public static final String USER_ID = "userId";
	public static final String FEE_CONTENT = "feeContent";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Feedback transientInstance);

	public abstract void delete(Feedback persistentInstance);

	public abstract Feedback findById(java.lang.Integer id);

	public abstract List findByExample(Feedback instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserId(Object userId);

	public abstract List findByFeeContent(Object feeContent);

	public abstract List findAll();

	public abstract Feedback merge(Feedback detachedInstance);

	public abstract void attachDirty(Feedback instance);

	public abstract void attachClean(Feedback instance);

}