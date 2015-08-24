package com.atm.dao.impl;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.RecuitTypeDAO;
import com.atm.model.Appeal;
import com.atm.model.RecuitType;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for RecuitType entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.RecuitType
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class RecuitTypeDAOImpl implements RecuitTypeDAO  {
	     private static final Logger log = LoggerFactory.getLogger(RecuitTypeDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
    @Override
	public void setSessionFactory(SessionFactory sessionFactory){
       this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession(){
     return sessionFactory.getCurrentSession(); 
    }
	protected void initDao() {
		//do nothing
	}
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#save(com.atm.model.RecuitType)
	 */
    @Override
	public void save(RecuitType transientInstance) {
        log.debug("saving RecuitType instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#delete(com.atm.model.RecuitType)
	 */
	@Override
	public void delete(RecuitType persistentInstance) {
        log.debug("deleting RecuitType instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#findById(java.lang.Integer)
	 */
    @Override
	public RecuitType findById( java.lang.Integer id) {
        log.debug("getting RecuitType instance with id: " + id);
        try {
            RecuitType instance = (RecuitType) getCurrentSession()
                    .get("com.atm.model.RecuitType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#findByExample(com.atm.model.RecuitType)
	 */
    @Override
	public List findByExample(RecuitType instance) {
        log.debug("finding RecuitType instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.RecuitType") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding RecuitType instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from RecuitType as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getCurrentSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#findByReTypeName(java.lang.Object)
	 */
	@Override
	public List findByReTypeName(Object reTypeName
	) {
		return findByProperty(RE_TYPE_NAME, reTypeName
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all RecuitType instances");
		try {
			String queryString = "from RecuitType";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#merge(com.atm.model.RecuitType)
	 */
    @Override
	public RecuitType merge(RecuitType detachedInstance) {
        log.debug("merging RecuitType instance");
        try {
            RecuitType result = (RecuitType) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#attachDirty(com.atm.model.RecuitType)
	 */
    @Override
	public void attachDirty(RecuitType instance) {
        log.debug("attaching dirty RecuitType instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitTypeDAO#attachClean(com.atm.model.RecuitType)
	 */
    @Override
	public void attachClean(RecuitType instance) {
        log.debug("attaching clean RecuitType instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static RecuitTypeDAO getFromApplicationContext() {
    	return (RecuitTypeDAO) ApplicationUtil.getApplicationContext().getBean("RecuitTypeDAOImpl");
	}
	@Override
	public void saveOrUpdate(Object transientInstance) {
		log.debug("saving Appeal instance");
        try {
            getCurrentSession().saveOrUpdate(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ATMDAO#finfByHQL
	 */
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
}