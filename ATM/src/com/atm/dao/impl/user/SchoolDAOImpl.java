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

import com.atm.dao.user.SchoolDAO;
import com.atm.model.Appeal;
import com.atm.model.user.School;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for School entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.user.School
  * @author MyEclipse Persistence Tools 
 */
 @Transactional   
public class SchoolDAOImpl implements SchoolDAO  {
	     private static final Logger log = LoggerFactory.getLogger(SchoolDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.SchoolDAO#save(com.atm.model.School)
	 */
    @Override
	public void save(School transientInstance) {
        log.debug("saving School instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#delete(com.atm.model.School)
	 */
	@Override
	public void delete(School persistentInstance) {
        log.debug("deleting School instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#findById(java.lang.String)
	 */
    @Override
	public School findById( java.lang.String id) {
        log.debug("getting School instance with id: " + id);
        try {
            School instance = (School) getCurrentSession()
                    .get(School.class, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#findByExample(com.atm.model.School)
	 */
    @Override
	public List findByExample(School instance) {
        log.debug("finding School instance by example");
        try {
            List results = getCurrentSession().createCriteria(School.class) .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding School instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from School as model where model." 
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
	 * @see com.atm.dao.SchoolDAO#findByScName(java.lang.Object)
	 */
	@Override
	public List findByScName(Object scName
	) {
		return findByProperty(SC_NAME, scName
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all School instances");
		try {
			String queryString = "from School";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#merge(com.atm.model.School)
	 */
    @Override
	public School merge(School detachedInstance) {
        log.debug("merging School instance");
        try {
            School result = (School) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#attachDirty(com.atm.model.School)
	 */
    @Override
	public void attachDirty(School instance) {
        log.debug("attaching dirty School instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.SchoolDAO#attachClean(com.atm.model.School)
	 */
    @Override
	public void attachClean(School instance) {
        log.debug("attaching clean School instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static SchoolDAO getFromApplicationContext() {
    	return (SchoolDAO) ApplicationUtil.getApplicationContext().getBean("SchoolDAOImpl");
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