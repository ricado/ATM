package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.RecuitInfoContent;

public interface RecuitInfoContentDAO extends AtmDAO{

	//property constants
	public static final String RECUIT_ID = "recuitId";
	public static final String WORK_ID = "workId";
	public static final String WORK_ADDRESS = "workAddress";
	public static final String SALARY = "salary";
	public static final String TELEPHONE = "telephone";
	public static final String REC_CONTENT = "recContent";
	public static final String PUBLISHER_ID = "publisherId";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(RecuitInfoContent transientInstance);

	public abstract void delete(RecuitInfoContent persistentInstance);

	public abstract RecuitInfoContent findById(java.lang.Integer id);

	public abstract List findByExample(RecuitInfoContent instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByRecuitId(Object recuitId);

	public abstract List findByWorkId(Object workId);

	public abstract List findByWorkAddress(Object workAddress);

	public abstract List findBySalary(Object salary);

	public abstract List findByTelephone(Object telephone);

	public abstract List findByRecContent(Object recContent);

	public abstract List findByPublisherId(Object publisherId);

	public abstract List findAll();

	public abstract RecuitInfoContent merge(RecuitInfoContent detachedInstance);

	public abstract void attachDirty(RecuitInfoContent instance);

	public abstract void attachClean(RecuitInfoContent instance);

	public abstract void update(RecuitInfoContent content);

	public abstract void deleteByProperty(String propertyName, Object value);

}