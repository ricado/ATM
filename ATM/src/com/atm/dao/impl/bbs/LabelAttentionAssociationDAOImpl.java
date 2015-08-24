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

import com.atm.dao.bbs.LabelAttentionAssociationDAO;
import com.atm.model.bbs.LabelAttentionAssociation;

/**
 	* A data access object (DAO) providing persistence and search support for LabelAttentionAssociation entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.LabelAttentionAssociation
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class LabelAttentionAssociationDAOImpl implements LabelAttentionAssociationDAO  {
	     private static final Logger log = LoggerFactory.getLogger(LabelAttentionAssociationDAOImpl.class);
		//property constants



    private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#save(com.atm.model.LabelAttentionAssociation)
	 */
    @Override
	public void save(LabelAttentionAssociation transientInstance) {
        log.debug("saving LabelAttentionAssociation instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#delete(com.atm.model.LabelAttentionAssociation)
	 */
	@Override
	public void delete(LabelAttentionAssociation persistentInstance) {
        log.debug("deleting LabelAttentionAssociation instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#findById(com.atm.model.LabelAttentionAssociationId)
	 */
    @Override
	public LabelAttentionAssociation findById( com.atm.model.LabelAttentionAssociationId id) {
        log.debug("getting LabelAttentionAssociation instance with id: " + id);
        try {
            LabelAttentionAssociation instance = (LabelAttentionAssociation) getCurrentSession()
                    .get("com.atm.model.LabelAttentionAssociation", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#findByExample(com.atm.model.LabelAttentionAssociation)
	 */
    @Override
	public List findByExample(LabelAttentionAssociation instance) {
        log.debug("finding LabelAttentionAssociation instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.LabelAttentionAssociation") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding LabelAttentionAssociation instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from LabelAttentionAssociation as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getCurrentSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    public List findByUserId(String userId){
    	return findByProperty("userId", userId);
    }

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all LabelAttentionAssociation instances");
		try {
			String queryString = "from LabelAttentionAssociation";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#merge(com.atm.model.LabelAttentionAssociation)
	 */
    @Override
	public LabelAttentionAssociation merge(LabelAttentionAssociation detachedInstance) {
        log.debug("merging LabelAttentionAssociation instance");
        try {
            LabelAttentionAssociation result = (LabelAttentionAssociation) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#attachDirty(com.atm.model.LabelAttentionAssociation)
	 */
    @Override
	public void attachDirty(LabelAttentionAssociation instance) {
        log.debug("attaching dirty LabelAttentionAssociation instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelAttentionAssociationDAO#attachClean(com.atm.model.LabelAttentionAssociation)
	 */
    @Override
	public void attachClean(LabelAttentionAssociation instance) {
        log.debug("attaching clean LabelAttentionAssociation instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static LabelAttentionAssociationDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (LabelAttentionAssociationDAO) ctx.getBean("LabelAttentionAssociationDAOImpl");
	}
}