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
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.AppealDAO;
import com.atm.model.Appeal;
import com.atm.util.ApplicationUtil;
import com.sun.glass.ui.Application;

/**
 	* A data access object (DAO) providing persistence and search support for Appeal entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.Appeal
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class AppealDAOImpl implements AppealDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AppealDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.AppealDAO#save(com.atm.model.Appeal)
	 */
    @Override
	public void save(Appeal transientInstance) {
        log.debug("saving Appeal instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#delete(com.atm.model.Appeal)
	 */
	@Override
	public void delete(Appeal persistentInstance) {
        log.debug("deleting Appeal instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findById(java.lang.Integer)
	 */
    @Override
	public Appeal findById( java.lang.Integer id) {
        log.debug("getting Appeal instance with id: " + id);
        try {
            Appeal instance = (Appeal) getCurrentSession()
                    .get("com.atm.model.Appeal", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findByExample(com.atm.model.Appeal)
	 */
    @Override
	public List findByExample(Appeal instance) {
        log.debug("finding Appeal instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.Appeal") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding Appeal instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Appeal as model where model." 
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
	 * @see com.atm.dao.AppealDAO#findByNumber(java.lang.Object)
	 */
	@Override
	public List findByNumber(Object number
	) {
		return findByProperty(NUMBER, number
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findByRole(java.lang.Object)
	 */
	@Override
	public List findByRole(Object role
	) {
		return findByProperty(ROLE, role
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findByName(java.lang.Object)
	 */
	@Override
	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findByPhotoPath(java.lang.Object)
	 */
	@Override
	public List findByPhotoPath(Object photoPath
	) {
		return findByProperty(PHOTO_PATH, photoPath
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findByInformEmail(java.lang.Object)
	 */
	@Override
	public List findByInformEmail(Object informEmail
	) {
		return findByProperty(INFORM_EMAIL, informEmail
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findByAppContent(java.lang.Object)
	 */
	@Override
	public List findByAppContent(Object appContent
	) {
		return findByProperty(APP_CONTENT, appContent
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Appeal instances");
		try {
			String queryString = "from Appeal";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#merge(com.atm.model.Appeal)
	 */
    @Override
	public Appeal merge(Appeal detachedInstance) {
        log.debug("merging Appeal instance");
        try {
            Appeal result = (Appeal) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#attachDirty(com.atm.model.Appeal)
	 */
    @Override
	public void attachDirty(Appeal instance) {
        log.debug("attaching dirty Appeal instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.AppealDAO#attachClean(com.atm.model.Appeal)
	 */
    @Override
	public void attachClean(Appeal instance) {
        log.debug("attaching clean Appeal instance");
        try {
            getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
	public static AppealDAO getFromApplicationContext() {
    	return (AppealDAO) ApplicationUtil.getApplicationContext().getBean("AppealDAOImpl");
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