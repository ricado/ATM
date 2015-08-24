package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.bbs.CollectEssay;

public interface CollectEssayDAO extends AtmDAO {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(CollectEssay transientInstance);

	public abstract void delete(CollectEssay persistentInstance);

	public abstract CollectEssay findById(com.atm.model.bbs.CollectEssayId id);

	public abstract List findByExample(CollectEssay instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findAll();

	public abstract CollectEssay merge(CollectEssay detachedInstance);

	public abstract void attachDirty(CollectEssay instance);

	public abstract void attachClean(CollectEssay instance);

}