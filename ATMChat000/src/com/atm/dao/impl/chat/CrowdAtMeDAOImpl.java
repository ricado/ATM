package com.atm.dao.impl.chat;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.chat.CrowdAtMeDAO;
import com.atm.model.Appeal;
import com.atm.model.chat.CrowdAtMe;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for CrowdAtMe entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.chat.CrowdAtMe
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class CrowdAtMeDAOImpl implements CrowdAtMeDAO  {
	     private static final Logger log = LoggerFactory.getLogger(CrowdAtMeDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.CrowdAtMeDAO#save(com.atm.model.CrowdAtMe)
	 */
    @Override
	public void save(CrowdAtMe transientInstance) {
        log.debug("saving CrowdAtMe instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#delete(com.atm.model.CrowdAtMe)
	 */
	@Override
	public void delete(CrowdAtMe persistentInstance) {
        log.debug("deleting CrowdAtMe instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#findById(java.lang.Integer)
	 */
    @Override
	public CrowdAtMe findById( java.lang.Integer id) {
        log.debug("getting CrowdAtMe instance with id: " + id);
        try {
            CrowdAtMe instance = (CrowdAtMe) getCurrentSession()
                    .get("com.atm.model.CrowdAtMe", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#findByExample(com.atm.model.CrowdAtMe)
	 */
    @Override
	public List findByExample(CrowdAtMe instance) {
        log.debug("finding CrowdAtMe instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.CrowdAtMe") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding CrowdAtMe instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from CrowdAtMe as model where model." 
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
	 * @see com.atm.dao.CrowdAtMeDAO#findByUserAtOtherId(java.lang.Object)
	 */
	@Override
	public List findByUserAtOtherId(Object userAtOtherId
	) {
		return findByProperty(USER_AT_OTHER_ID, userAtOtherId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#findByUserBeAtId(java.lang.Object)
	 */
	@Override
	public List findByUserBeAtId(Object userBeAtId
	) {
		return findByProperty(USER_BE_AT_ID, userBeAtId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#findByCrowdId(java.lang.Object)
	 */
	@Override
	public List findByCrowdId(Object crowdId
	) {
		return findByProperty(CROWD_ID, crowdId
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all CrowdAtMe instances");
		try {
			String queryString = "from CrowdAtMe";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#merge(com.atm.model.CrowdAtMe)
	 */
    @Override
	public CrowdAtMe merge(CrowdAtMe detachedInstance) {
        log.debug("merging CrowdAtMe instance");
        try {
            CrowdAtMe result = (CrowdAtMe) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#attachDirty(com.atm.model.CrowdAtMe)
	 */
    @Override
	public void attachDirty(CrowdAtMe instance) {
        log.debug("attaching dirty CrowdAtMe instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdAtMeDAO#attachClean(com.atm.model.CrowdAtMe)
	 */
    @Override
	public void attachClean(CrowdAtMe instance) {
        log.debug("attaching clean CrowdAtMe instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static CrowdAtMeDAO getFromApplicationContext() {
    	return (CrowdAtMeDAO) ApplicationUtil.getApplicationContext().getBean("CrowdAtMeDAOImpl");
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