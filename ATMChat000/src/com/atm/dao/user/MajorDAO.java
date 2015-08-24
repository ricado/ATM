package com.atm.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.user.Major;

public interface MajorDAO extends AtmDAO{

	//property constants
	public static final String MNAME = "mname";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Major transientInstance);

	public abstract void delete(Major persistentInstance);

	public abstract Major findById(java.lang.String id);

	public abstract List findByExample(Major instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByMname(Object mname);

	public abstract List findAll();

	public abstract Major merge(Major detachedInstance);

	public abstract void attachDirty(Major instance);

	public abstract void attachClean(Major instance);

}