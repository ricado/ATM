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

import com.atm.dao.ApplyInfoContentDAO;
import com.atm.model.Appeal;
import com.atm.model.ApplyInfoContent;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for ApplyInfoContent entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.ApplyInfoContent
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class ApplyInfoContentDAOImpl implements ApplyInfoContentDAO  {
	     private static final Logger log = LoggerFactory.getLogger(ApplyInfoContentDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.ApplyInfoContentDAO#save(com.atm.model.ApplyInfoContent)
	 */
    @Override
	public void save(ApplyInfoContent transientInstance) {
        log.debug("saving ApplyInfoContent instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
   	 * @see com.atm.dao.ApplyInfoContentDAO#update(com.atm.model.ApplyInfoContent)
   	 */
       @Override
   	public void update(ApplyInfoContent transientInstance) {
           log.debug("saving ApplyInfoContent instance");
           try {
               getCurrentSession().update(transientInstance);
               log.debug("save successful");
           } catch (RuntimeException re) {
               log.error("save failed", re);
               throw re;
           }
       }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#delete(com.atm.model.ApplyInfoContent)
	 */
	@Override
	public void delete(ApplyInfoContent persistentInstance) {
        log.debug("deleting ApplyInfoContent instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findById(java.lang.Integer)
	 */
    @Override
	public ApplyInfoContent findById( java.lang.Integer id) {
        log.debug("getting ApplyInfoContent instance with id: " + id);
        try {
            ApplyInfoContent instance = (ApplyInfoContent) getCurrentSession()
                    .get("com.atm.model.ApplyInfoContent", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findByExample(com.atm.model.ApplyInfoContent)
	 */
    @Override
	public List findByExample(ApplyInfoContent instance) {
        log.debug("finding ApplyInfoContent instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.ApplyInfoContent") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding ApplyInfoContent instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from ApplyInfoContent as model where model." 
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
	 * @see com.atm.dao.ApplyInfoContentDAO#findByRecuitId(java.lang.Object)
	 */
	@Override
	public List findByRecuitId(Object recuitId
	) {
		return findByProperty(RECUIT_ID, recuitId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findByWorkId(java.lang.Object)
	 */
	@Override
	public List findByWorkId(Object workId
	) {
		return findByProperty(WORK_ID, workId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findByPublisherId(java.lang.Object)
	 */
	@Override
	public List findByPublisherId(Object publisherId
	) {
		return findByProperty(PUBLISHER_ID, publisherId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findByExpectSalary(java.lang.Object)
	 */
	@Override
	public List findByExpectSalary(Object expectSalary
	) {
		return findByProperty(EXPECT_SALARY, expectSalary
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findByTelephone(java.lang.Object)
	 */
	@Override
	public List findByTelephone(Object telephone
	) {
		return findByProperty(TELEPHONE, telephone
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findByPersonalInfo(java.lang.Object)
	 */
	@Override
	public List findByPersonalInfo(Object personalInfo
	) {
		return findByProperty(PERSONAL_INFO, personalInfo
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all ApplyInfoContent instances");
		try {
			String queryString = "from ApplyInfoContent";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#merge(com.atm.model.ApplyInfoContent)
	 */
    @Override
	public ApplyInfoContent merge(ApplyInfoContent detachedInstance) {
        log.debug("merging ApplyInfoContent instance");
        try {
            ApplyInfoContent result = (ApplyInfoContent) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#attachDirty(com.atm.model.ApplyInfoContent)
	 */
    @Override
	public void attachDirty(ApplyInfoContent instance) {
        log.debug("attaching dirty ApplyInfoContent instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ApplyInfoContentDAO#attachClean(com.atm.model.ApplyInfoContent)
	 */
    @Override
	public void attachClean(ApplyInfoContent instance) {
        log.debug("attaching clean ApplyInfoContent instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static ApplyInfoContentDAO getFromApplicationContext() {
    	return (ApplyInfoContentDAO) ApplicationUtil.getApplicationContext().getBean("ApplyInfoContentDAOImpl");
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
	
	@Override
	public int deleteById(int apInfoId){
		log.debug("delete by apInfoId");
		try{
			String HQL = "delete form ApplyInfoContent a where apInfoId='" + apInfoId + "'";
			int i = getCurrentSession().createQuery(HQL).executeUpdate();
			log.debug("success");
			return i;
		}catch(RuntimeException re){
			log.error("error");
			throw re;
		}
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
}