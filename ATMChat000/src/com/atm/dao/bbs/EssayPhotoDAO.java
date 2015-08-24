package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.EssayPhoto;
import com.atm.model.bbs.EssayPhotoId;



public interface EssayPhotoDAO {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(EssayPhoto transientInstance);

	public abstract void delete(EssayPhoto persistentInstance);

	public abstract EssayPhoto findById(EssayPhotoId id);

	public abstract List findByExample(EssayPhoto instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findAll();

	public abstract EssayPhoto merge(EssayPhoto detachedInstance);

	public abstract void attachDirty(EssayPhoto instance);

	public abstract void attachClean(EssayPhoto instance);

}