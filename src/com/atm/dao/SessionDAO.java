package com.atm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface SessionDAO {
	public abstract void setSessionFactory(SessionFactory sessionFactory);
	public abstract List findByHQL(String HQL);
}
