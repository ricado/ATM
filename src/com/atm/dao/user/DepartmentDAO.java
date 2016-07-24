package com.atm.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.user.Department;

public interface DepartmentDAO {

	//property constants
	public static final String DNAME = "dname";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(Department transientInstance);

	public abstract void delete(Department persistentInstance);

	public abstract Department findById(java.lang.String id);

	public abstract List findByExample(Department instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByDname(Object dname);
	
	public abstract List findAll();

	public abstract Department merge(Department detachedInstance);

	public abstract void attachDirty(Department instance);

	public abstract void attachClean(Department instance);
	public abstract List findByDNameAndScNo(String dName, String scNo);
	public abstract List findDeptList(String scNo) ;
	public abstract Object findMaxNo(String scNo);

}