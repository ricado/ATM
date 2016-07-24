package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.WorkType;

public interface WorkTypeDAO extends AtmDAO{

	//property constants
	public static final String WO_TYPE_NAME = "woTypeName";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(WorkType transientInstance);

	public abstract void delete(WorkType persistentInstance);

	public abstract WorkType findById(java.lang.Integer id);

	public abstract List findByExample(WorkType instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByWoTypeName(Object woTypeName);

	public abstract List findAll();

	public abstract WorkType merge(WorkType detachedInstance);

	public abstract void attachDirty(WorkType instance);

	public abstract void attachClean(WorkType instance);

}