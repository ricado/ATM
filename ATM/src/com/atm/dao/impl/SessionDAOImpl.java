package com.atm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.SessionDAO;

@Transactional
public class SessionDAOImpl implements SessionDAO{
	private static final Logger log = LoggerFactory.getLogger("SessionDAOImpl");
	private SessionFactory sessionFactory;
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	 private Session getCurrentSession(){
	     return sessionFactory.getCurrentSession();//.openSession();//getCurrentSession(); 
	 }
	
	 @Override
	 public List findByHQL(String HQL){
		log.debug("find by HQL");
		try{
			List list = getCurrentSession().createQuery(HQL).list();
			log.debug("success");
			return list;
		}catch(RuntimeException re){
			log.error("error");
			throw re;
		}
	}
	
	public Query getQuery(String queryName){
		log.info("user procedrue");
		try{
			log.info("get success");
			return getCurrentSession().getNamedQuery(queryName);		
		}catch(RuntimeException e){
			log.info("get error");
			throw e;
		}
	}
}
