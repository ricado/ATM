package com.atm.dao.impl;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.RecuitInfoContentDAO;
import com.atm.model.RecuitInfoContent;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for RecuitIinfoContent entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.RecuitInfoContent
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class RecuitInfoContentDAOImpl implements RecuitInfoContentDAO  {
	     private static final Logger log = LoggerFactory.getLogger(RecuitInfoContentDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.RecuitIinfoContentDAO#save(com.atm.model.RecuitIinfoContent)
	 */
    @Override
	public void save(RecuitInfoContent transientInstance) {
        log.debug("saving RecuitIinfoContent instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#delete(com.atm.model.RecuitIinfoContent)
	 */
	@Override
	public void delete(RecuitInfoContent persistentInstance) {
        log.debug("deleting RecuitIinfoContent instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findById(java.lang.Integer)
	 */
    @Override
	public RecuitInfoContent findById( java.lang.Integer id) {
        log.debug("getting RecuitIinfoContent instance with id: " + id);
        try {
            RecuitInfoContent instance = (RecuitInfoContent) getCurrentSession()
                    .get("com.atm.model.RecuitIinfoContent", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByExample(com.atm.model.RecuitIinfoContent)
	 */
    @Override
	public List findByExample(RecuitInfoContent instance) {
        log.debug("finding RecuitIinfoContent instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.RecuitIinfoContent") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding RecuitIinfoContent instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from RecuitIinfoContent as model where model." 
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
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByRecuitId(java.lang.Object)
	 */
	@Override
	public List findByRecuitId(Object recuitId
	) {
		return findByProperty(RECUIT_ID, recuitId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByWorkId(java.lang.Object)
	 */
	@Override
	public List findByWorkId(Object workId
	) {
		return findByProperty(WORK_ID, workId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByWorkAddress(java.lang.Object)
	 */
	@Override
	public List findByWorkAddress(Object workAddress
	) {
		return findByProperty(WORK_ADDRESS, workAddress
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findBySalary(java.lang.Object)
	 */
	@Override
	public List findBySalary(Object salary
	) {
		return findByProperty(SALARY, salary
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByTelephone(java.lang.Object)
	 */
	@Override
	public List findByTelephone(Object telephone
	) {
		return findByProperty(TELEPHONE, telephone
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByRecContent(java.lang.Object)
	 */
	@Override
	public List findByRecContent(Object recContent
	) {
		return findByProperty(REC_CONTENT, recContent
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findByPublisherId(java.lang.Object)
	 */
	@Override
	public List findByPublisherId(Object publisherId
	) {
		return findByProperty(PUBLISHER_ID, publisherId
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all RecuitIinfoContent instances");
		try {
			String queryString = "from RecuitIinfoContent";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#merge(com.atm.model.RecuitIinfoContent)
	 */
    @Override
	public RecuitInfoContent merge(RecuitInfoContent detachedInstance) {
        log.debug("merging RecuitIinfoContent instance");
        try {
            RecuitInfoContent result = (RecuitInfoContent) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#attachDirty(com.atm.model.RecuitIinfoContent)
	 */
    @Override
	public void attachDirty(RecuitInfoContent instance) {
        log.debug("attaching dirty RecuitIinfoContent instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.RecuitIinfoContentDAO#attachClean(com.atm.model.RecuitIinfoContent)
	 */
    @Override
	public void attachClean(RecuitInfoContent instance) {
        log.debug("attaching clean RecuitIinfoContent instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static RecuitInfoContentDAO getFromApplicationContext() {
    	return (RecuitInfoContentDAO) ApplicationUtil.getApplicationContext().getBean("RecuitIinfoContentDAOImpl");
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
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ATMDAO#finfByHQL
	 */
	@Override
	public void update(RecuitInfoContent content){
		log.debug("update RecuitInfoContent");
		try{
			getCurrentSession().update(content);
			log.debug("success");
		}catch(RuntimeException re){
			log.error("error");
			throw re;
		}
	}
	
	@Override
	public void deleteByProperty(String propertyName, Object value){
		log.debug("delete recuitInfoContent by prooperty");
		try{
			String HQL = "delect RecuitInfoContent r where r."
					+propertyName + "='" + value + "'";
			int i = getCurrentSession().createQuery(HQL).executeUpdate();
			log.debug("delete success");
		}catch(RuntimeException e){
			log.debug("error");
			throw e;
		}
	}
}