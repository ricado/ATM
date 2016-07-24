package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.Label;



/**
 * @TODO£º
 * @fileName : com.atm.dao.impl.LabelDAO.java
 * date | author | version |   
 * 2015Äê8ÔÂ6ÈÕ | Jiong | 1.0 |
 */
public interface LabelDAO {

	//property constants
	public static final String LAB_NAME = "labName";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Label transientInstance);

	public abstract void delete(Label persistentInstance);

	public abstract Label findById(java.lang.Integer id);

	public abstract List findByExample(Label instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByLabName(Object labName);

	public abstract List findAll();

	public abstract Label merge(Label detachedInstance);

	public abstract void attachDirty(Label instance);

	public abstract void attachClean(Label instance);
	
	public abstract List findByDno(Object dNo,int maxResult);

}
