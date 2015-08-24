package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.EssayType;



public interface EssayTypeDAO {

	//property constants
	public static final String ESSAY_TYPE = "essayType";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(EssayType transientInstance);

	public abstract void delete(EssayType persistentInstance);

	public abstract EssayType findById(java.lang.Integer id);

	public abstract List findByExample(EssayType instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByEssayType(Object essayType);

	public abstract List findAll();

	public abstract EssayType merge(EssayType detachedInstance);

	public abstract void attachDirty(EssayType instance);

	public abstract void attachClean(EssayType instance);

}