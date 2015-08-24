package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.ApplyInfoContent;

public interface ApplyInfoContentDAO extends AtmDAO{

	//property constants
	public static final String RECUIT_ID = "recuitId";
	public static final String WORK_ID = "workId";
	public static final String PUBLISHER_ID = "publisherId";
	public static final String EXPECT_SALARY = "expectSalary";
	public static final String TELEPHONE = "telephone";
	public static final String PERSONAL_INFO = "personalInfo";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(ApplyInfoContent transientInstance);

	public abstract void delete(ApplyInfoContent persistentInstance);

	public abstract ApplyInfoContent findById(java.lang.Integer id);

	public abstract List findByExample(ApplyInfoContent instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByRecuitId(Object recuitId);

	public abstract List findByWorkId(Object workId);

	public abstract List findByPublisherId(Object publisherId);

	public abstract List findByExpectSalary(Object expectSalary);

	public abstract List findByTelephone(Object telephone);

	public abstract List findByPersonalInfo(Object personalInfo);

	public abstract List findAll();

	public abstract ApplyInfoContent merge(ApplyInfoContent detachedInstance);

	public abstract void attachDirty(ApplyInfoContent instance);

	public abstract void attachClean(ApplyInfoContent instance);

	public abstract void update(ApplyInfoContent transientInstance);

	public abstract int deleteById(int apInfoId);

}