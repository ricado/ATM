package com.atm.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.user.School;

public interface SchoolDAO extends AtmDAO{

	//property constants
	public static final String SC_NAME = "scName";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(School transientInstance);

	public abstract void delete(School persistentInstance);

	public abstract School findById(java.lang.String id);

	public abstract List findByExample(School instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByScName(Object scName);

	public abstract List findAll();

	public abstract School merge(School detachedInstance);

	public abstract void attachDirty(School instance);

	public abstract void attachClean(School instance);

}