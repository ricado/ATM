package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.ClickGood;
import com.atm.model.bbs.ClickGoodId;


public interface ClickGoodDAO {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(ClickGood transientInstance);

	public abstract void delete(ClickGood persistentInstance);

	public abstract ClickGood findById(ClickGoodId id);

	public abstract List findByExample(ClickGood instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findAll();

	public abstract ClickGood merge(ClickGood detachedInstance);

	public abstract void attachDirty(ClickGood instance);

	public abstract void attachClean(ClickGood instance);

}