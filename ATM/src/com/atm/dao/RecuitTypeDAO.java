package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.RecuitType;

public interface RecuitTypeDAO extends AtmDAO{

	//property constants
	public static final String RE_TYPE_NAME = "reTypeName";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(RecuitType transientInstance);

	public abstract void delete(RecuitType persistentInstance);

	public abstract RecuitType findById(java.lang.Integer id);

	public abstract List findByExample(RecuitType instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByReTypeName(Object reTypeName);

	public abstract List findAll();

	public abstract RecuitType merge(RecuitType detachedInstance);

	public abstract void attachDirty(RecuitType instance);

	public abstract void attachClean(RecuitType instance);

}