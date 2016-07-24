package com.atm.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.user.Student;

public interface StudentDAO extends AtmDAO{

	//property constants
	public static final String SNO = "sno";
	public static final String EMAIL = "email";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Student transientInstance);

	public abstract void delete(Student persistentInstance);

	public abstract Student findById(java.lang.String id);

	public abstract List findByExample(Student instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findBySno(Object sno);

	public abstract List findByEmail(Object email);

	public abstract List findAll();

	public abstract Student merge(Student detachedInstance);

	public abstract void attachDirty(Student instance);

	public abstract void attachClean(Student instance);

	public abstract int updateSno(String userId, String number);

}