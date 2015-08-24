package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.ReplyPhoto;



public interface ReplyPhotoDAO {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(ReplyPhoto transientInstance);

	public abstract void delete(ReplyPhoto persistentInstance);

	public abstract ReplyPhoto findById(com.atm.model.ReplyPhotoId id);

	public abstract List findByExample(ReplyPhoto instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findAll();

	public abstract ReplyPhoto merge(ReplyPhoto detachedInstance);

	public abstract void attachDirty(ReplyPhoto instance);

	public abstract void attachClean(ReplyPhoto instance);

}