package com.atm.dao.impl.bbs;

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

import com.atm.dao.bbs.EssayTypeDAO;
import com.atm.model.bbs.EssayType;


/**
 	* A data access object (DAO) providing persistence and search support for EssayType entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.EssayType
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class EssayTypeDAOImpl implements EssayTypeDAO  {
	     private static final Logger log = LoggerFactory.getLogger(EssayTypeDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.EssayTypeDAO#save(com.atm.model.EssayType)
	 */
    @Override
	public void save(EssayType transientInstance) {
        log.debug("saving EssayType instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#delete(com.atm.model.EssayType)
	 */
	@Override
	public void delete(EssayType persistentInstance) {
        log.debug("deleting EssayType instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#findById(java.lang.Integer)
	 */
    @Override
	public EssayType findById( java.lang.Integer id) {
        log.debug("getting EssayType instance with id: " + id);
        try {
            EssayType instance = (EssayType) getCurrentSession()
                    .get("com.atm.model.EssayType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#findByExample(com.atm.model.EssayType)
	 */
    @Override
	public List findByExample(EssayType instance) {
        log.debug("finding EssayType instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.EssayType") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding EssayType instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from EssayType as model where model." 
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
	 * @see com.atm.dao.impl.EssayTypeDAO#findByEssayType(java.lang.Object)
	 */
	@Override
	public List findByEssayType(Object essayType
	) {
		return findByProperty(ESSAY_TYPE, essayType
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all EssayType instances");
		try {
			String queryString = "from EssayType";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#merge(com.atm.model.EssayType)
	 */
    @Override
	public EssayType merge(EssayType detachedInstance) {
        log.debug("merging EssayType instance");
        try {
            EssayType result = (EssayType) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#attachDirty(com.atm.model.EssayType)
	 */
    @Override
	public void attachDirty(EssayType instance) {
        log.debug("attaching dirty EssayType instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayTypeDAO#attachClean(com.atm.model.EssayType)
	 */
    @Override
	public void attachClean(EssayType instance) {
        log.debug("attaching clean EssayType instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static EssayTypeDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (EssayTypeDAO) ctx.getBean("EssayTypeDAOImpl");
	}
}