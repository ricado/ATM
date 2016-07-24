package com.atm.dao.chat;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.chat.CrowdRole;

public interface CrowdRoleDAO extends AtmDAO{

	//property constants
	public static final String ROLE_NAME = "roleName";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(CrowdRole transientInstance);

	public abstract void delete(CrowdRole persistentInstance);

	public abstract CrowdRole findById(java.lang.Integer id);

	public abstract List findByExample(CrowdRole instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByRoleName(Object roleName);

	public abstract List findAll();

	public abstract CrowdRole merge(CrowdRole detachedInstance);

	public abstract void attachDirty(CrowdRole instance);

	public abstract void attachClean(CrowdRole instance);

}