package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.Appeal;

public interface AppealDAO extends AtmDAO{

	//property constants
	public static final String NUMBER = "number";
	public static final String ROLE = "role";
	public static final String NAME = "name";
	public static final String PHOTO_PATH = "photoPath";
	public static final String INFORM_EMAIL = "informEmail";
	public static final String APP_CONTENT = "appContent";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Appeal transientInstance);

	public abstract void delete(Appeal persistentInstance);

	public abstract Appeal findById(java.lang.Integer id);

	public abstract List findByExample(Appeal instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByNumber(Object number);

	public abstract List findByRole(Object role);

	public abstract List findByName(Object name);

	public abstract List findByPhotoPath(Object photoPath);

	public abstract List findByInformEmail(Object informEmail);

	public abstract List findByAppContent(Object appContent);

	public abstract List findAll();

	public abstract Appeal merge(Appeal detachedInstance);

	public abstract void attachDirty(Appeal instance);

	public abstract void attachClean(Appeal instance);

}