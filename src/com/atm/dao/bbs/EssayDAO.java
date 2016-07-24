package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.Essay;

public interface EssayDAO {

	// property constants
	public static final String TYPE_ID = "typeId";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String DNO = "dno";
	public static final String LAB_ID = "labId";
	public static final String PUBLISHER_ID = "publisherId";
	public static final String CLICK_NUM = "clickNum";
	
	
	//**********************************************************************

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Essay transientInstance);

	public abstract void delete(Essay persistentInstance);

	public abstract Essay findById(java.lang.Integer id);

	public abstract List findByExample(Essay instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByTypeId(Object typeId);

	public abstract List findByTitle(Object title);

	public abstract List findByContent(Object content);

	public abstract List findByDno(Object dno);

	public abstract List findByLabId(Object labId);

	public abstract List findByPublisherId(Object publisherId);

	public abstract List findByClickNum(Object clickNum);

	public abstract List findAll();

	public abstract Essay merge(Essay detachedInstance);

	public abstract void attachDirty(Essay instance);

	public abstract void attachClean(Essay instance);

}