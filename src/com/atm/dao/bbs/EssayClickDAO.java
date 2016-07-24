package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.EssayClick;

/**
 * @TODO£º
 * @fileName : com.atm.model.EssayClickDAO.java
 * date | author | version |   
 * 2015Äê8ÔÂ7ÈÕ | Jiong | 1.0 |
 */
public interface EssayClickDAO {

	// property constants
	public static final String CLICK_NUM = "clickNum";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(EssayClick transientInstance);

	public abstract void delete(EssayClick persistentInstance);

	public abstract EssayClick findById(java.lang.Integer id);

	public abstract List findByExample(EssayClick instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByClickNum(Object clickNum);

	public abstract List findAll();

	public abstract EssayClick merge(EssayClick detachedInstance);

	public abstract void attachDirty(EssayClick instance);

	public abstract void attachClean(EssayClick instance);

}
