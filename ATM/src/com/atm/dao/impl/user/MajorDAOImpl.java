package com.atm.dao.impl.user;

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

import com.atm.dao.user.MajorDAO;
import com.atm.model.Appeal;
import com.atm.model.user.Major;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for Major entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.user.Major
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class MajorDAOImpl implements MajorDAO  {
	     private static final Logger log = LoggerFactory.getLogger(MajorDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.MajorDAO#save(com.atm.model.Major)
	 */
    @Override
	public void save(Major transientInstance) {
        log.debug("saving Major instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#delete(com.atm.model.Major)
	 */
	@Override
	public void delete(Major persistentInstance) {
        log.debug("deleting Major instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#findById(java.lang.String)
	 */
    @Override
	public Major findById( java.lang.String id) {
        log.debug("getting Major instance with id: " + id);
        try {
            Major instance = (Major) getCurrentSession()
                    .get("com.atm.model.Major", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#findByExample(com.atm.model.Major)
	 */
    @Override
	public List findByExample(Major instance) {
        log.debug("finding Major instance by example");
        try {
            List results = getCurrentSession().createCriteria(Major.class).add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding Major instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Major as model where model." 
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
	 * @see com.atm.dao.MajorDAO#findByMname(java.lang.Object)
	 */
	@Override
	public List findByMname(Object mname
	) {
		return findByProperty(MNAME, mname
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Major instances");
		try {
			String queryString = "from Major";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#merge(com.atm.model.Major)
	 */
    @Override
	public Major merge(Major detachedInstance) {
        log.debug("merging Major instance");
        try {
            Major result = (Major) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#attachDirty(com.atm.model.Major)
	 */
    @Override
	public void attachDirty(Major instance) {
        log.debug("attaching dirty Major instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.MajorDAO#attachClean(com.atm.model.Major)
	 */
    @Override
	public void attachClean(Major instance) {
        log.debug("attaching clean Major instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static MajorDAO getFromApplicationContext() {
    	return (MajorDAO) ApplicationUtil.getApplicationContext().getBean("MajorDAOImpl");
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