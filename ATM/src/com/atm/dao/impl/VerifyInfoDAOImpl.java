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

import com.atm.dao.VerifyInfoDAO;
import com.atm.model.Appeal;
import com.atm.model.VerifyInfo;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for VerifyInfo entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.VerifyInfo
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class VerifyInfoDAOImpl implements VerifyInfoDAO  {
	     private static final Logger log = LoggerFactory.getLogger(VerifyInfoDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.VerifyInfoDAO#save(com.atm.model.VerifyInfo)
	 */
    @Override
	public void save(VerifyInfo transientInstance) {
        log.debug("saving VerifyInfo instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#delete(com.atm.model.VerifyInfo)
	 */
	@Override
	public void delete(VerifyInfo persistentInstance) {
        log.debug("deleting VerifyInfo instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#findById(java.lang.Integer)
	 */
    @Override
	public VerifyInfo findById( java.lang.Integer id) {
        log.debug("getting VerifyInfo instance with id: " + id);
        try {
            VerifyInfo instance = (VerifyInfo) getCurrentSession()
                    .get("com.atm.model.VerifyInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#findByExample(com.atm.model.VerifyInfo)
	 */
    @Override
	public List findByExample(VerifyInfo instance) {
        log.debug("finding VerifyInfo instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.VerifyInfo") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding VerifyInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from VerifyInfo as model where model." 
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
	 * @see com.atm.dao.VerifyInfoDAO#findByUserId(java.lang.Object)
	 */
	@Override
	public List findByUserId(Object userId
	) {
		return findByProperty(USER_ID, userId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#findByCrowdId(java.lang.Object)
	 */
	@Override
	public List findByCrowdId(Object crowdId
	) {
		return findByProperty(CROWD_ID, crowdId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#findByIsSuccess(java.lang.Object)
	 */
	@Override
	public List findByIsSuccess(Object isSuccess
	) {
		return findByProperty(IS_SUCCESS, isSuccess
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#findByContent(java.lang.Object)
	 */
	@Override
	public List findByContent(Object content
	) {
		return findByProperty(CONTENT, content
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all VerifyInfo instances");
		try {
			String queryString = "from VerifyInfo";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#merge(com.atm.model.VerifyInfo)
	 */
    @Override
	public VerifyInfo merge(VerifyInfo detachedInstance) {
        log.debug("merging VerifyInfo instance");
        try {
            VerifyInfo result = (VerifyInfo) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#attachDirty(com.atm.model.VerifyInfo)
	 */
    @Override
	public void attachDirty(VerifyInfo instance) {
        log.debug("attaching dirty VerifyInfo instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.VerifyInfoDAO#attachClean(com.atm.model.VerifyInfo)
	 */
    @Override
	public void attachClean(VerifyInfo instance) {
        log.debug("attaching clean VerifyInfo instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static VerifyInfoDAO getFromApplicationContext() {
    	return (VerifyInfoDAO) ApplicationUtil.getApplicationContext().getBean("VerifyInfoDAOImpl");
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
	@Override
	public int deleteApply(String applyId,int crowdId){
		log.info("find by applyId and crowdId");
		try{
			String HQL = "delete VerifyInfo v where v.applyId ='" + applyId + "'" +
					"v.crowdId='" + crowdId + "'";
			int i = getCurrentSession().createQuery(HQL).executeUpdate();
			log.info("success");
			return i;
		}catch(RuntimeException re){
			log.error("error");
			re.printStackTrace();
			return 0;
		}
	}
}