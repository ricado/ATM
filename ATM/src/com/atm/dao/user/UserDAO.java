package com.atm.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.user.User;

public interface UserDAO extends AtmDAO{

	//property constants
	public static final String USER_PWD = "userPwd";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(User transientInstance);

	public abstract void delete(User persistentInstance);

	public abstract User findById(java.lang.String id);

	public abstract List findByExample(User instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByUserPwd(Object userPwd);

	public abstract List findAll();

	public abstract User merge(User detachedInstance);

	public abstract void attachDirty(User instance);

	public abstract void attachClean(User instance);
	//update
	public abstract void updateByUser(User user);

	public abstract List<User> findUserList(int first, int max);

	public abstract int getMaxRecord();

	public abstract boolean login(User user);

}