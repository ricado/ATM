package com.atm.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.user.Teacher;

public interface TeacherDAO extends AtmDAO{

	//property constants
	public static final String TNO = "tno";
	public static final String EMAIL = "email";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Teacher transientInstance);

	public abstract void delete(Teacher persistentInstance);

	public abstract Teacher findById(java.lang.String id);

	public abstract List findByExample(Teacher instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByTno(Object tno);

	public abstract List findByEmail(Object email);

	public abstract List findAll();

	public abstract Teacher merge(Teacher detachedInstance);

	public abstract void attachDirty(Teacher instance);

	public abstract void attachClean(Teacher instance);

}