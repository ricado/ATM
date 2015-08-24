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

import com.atm.dao.SystemInfoDAO;
import com.atm.model.Appeal;
import com.atm.model.SystemInfo;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for SystemInfo entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.SystemInfo
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class SystemInfoDAOImpl implements SystemInfoDAO  {
	     private static final Logger log = LoggerFactory.getLogger(SystemInfoDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.SystemInfoDAO#save(com.atm.model.SystemInfo)
	 */
    @Override
	public void save(SystemInfo transientInstance) {
        log.debug("saving SystemInfo instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#delete(com.atm.model.SystemInfo)
	 */
	@Override
	public void delete(SystemInfo persistentInstance) {
        log.debug("deleting SystemInfo instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#findById(java.lang.Integer)
	 */
    @Override
	public SystemInfo findById( java.lang.Integer id) {
        log.debug("getting SystemInfo instance with id: " + id);
        try {
            SystemInfo instance = (SystemInfo) getCurrentSession()
                    .get("com.atm.model.SystemInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#findByExample(com.atm.model.SystemInfo)
	 */
    @Override
	public List findByExample(SystemInfo instance) {
        log.debug("finding SystemInfo instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.SystemInfo") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding SystemInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from SystemInfo as model where model." 
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
	 * @see com.atm.dao.SystemInfoDAO#findByUserId(java.lang.Object)
	 */
	@Override
	public List findByUserId(Object userId
	) {
		return findByProperty(USER_ID, userId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#findBySysContent(java.lang.Object)
	 */
	@Override
	public List findBySysContent(Object sysContent
	) {
		return findByProperty(SYS_CONTENT, sysContent
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all SystemInfo instances");
		try {
			String queryString = "from SystemInfo";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#merge(com.atm.model.SystemInfo)
	 */
    @Override
	public SystemInfo merge(SystemInfo detachedInstance) {
        log.debug("merging SystemInfo instance");
        try {
            SystemInfo result = (SystemInfo) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#attachDirty(com.atm.model.SystemInfo)
	 */
    @Override
	public void attachDirty(SystemInfo instance) {
        log.debug("attaching dirty SystemInfo instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SystemInfoDAO#attachClean(com.atm.model.SystemInfo)
	 */
    @Override
	public void attachClean(SystemInfo instance) {
        log.debug("attaching clean SystemInfo instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static SystemInfoDAO getFromApplicationContext() {
    	return (SystemInfoDAO) ApplicationUtil.getApplicationContext().getBean("SystemInfoDAOImpl");
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