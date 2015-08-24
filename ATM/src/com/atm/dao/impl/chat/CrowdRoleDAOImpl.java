package com.atm.dao.impl.chat;

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

import com.atm.dao.chat.CrowdRoleDAO;
import com.atm.model.Appeal;
import com.atm.model.chat.CrowdRole;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for CrowdRole entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.chat.CrowdRole
  * @author MyEclipse Persistence Tools 
 */
@Transactional   
public class CrowdRoleDAOImpl implements CrowdRoleDAO  {
	     private static final Logger log = LoggerFactory.getLogger(CrowdRoleDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.CrowdRoleDAO#save(com.atm.model.CrowdRole)
	 */
    @Override
	public void save(CrowdRole transientInstance) {
        log.debug("saving CrowdRole instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#delete(com.atm.model.CrowdRole)
	 */
	@Override
	public void delete(CrowdRole persistentInstance) {
        log.debug("deleting CrowdRole instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#findById(java.lang.Integer)
	 */
    @Override
	public CrowdRole findById( java.lang.Integer id) {
        log.debug("getting CrowdRole instance with id: " + id);
        try {
            CrowdRole instance = (CrowdRole) getCurrentSession()
                    .get("com.atm.model.CrowdRole", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#findByExample(com.atm.model.CrowdRole)
	 */
    @Override
	public List findByExample(CrowdRole instance) {
        log.debug("finding CrowdRole instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.CrowdRole") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding CrowdRole instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from CrowdRole as model where model." 
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
	 * @see com.atm.dao.CrowdRoleDAO#findByRoleName(java.lang.Object)
	 */
	@Override
	public List findByRoleName(Object roleName
	) {
		return findByProperty(ROLE_NAME, roleName
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all CrowdRole instances");
		try {
			String queryString = "from CrowdRole";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#merge(com.atm.model.CrowdRole)
	 */
    @Override
	public CrowdRole merge(CrowdRole detachedInstance) {
        log.debug("merging CrowdRole instance");
        try {
            CrowdRole result = (CrowdRole) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#attachDirty(com.atm.model.CrowdRole)
	 */
    @Override
	public void attachDirty(CrowdRole instance) {
        log.debug("attaching dirty CrowdRole instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdRoleDAO#attachClean(com.atm.model.CrowdRole)
	 */
    @Override
	public void attachClean(CrowdRole instance) {
        log.debug("attaching clean CrowdRole instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static CrowdRoleDAO getFromApplicationContext() {
    	return (CrowdRoleDAO) ApplicationUtil.getApplicationContext().getBean("CrowdRoleDAOImpl");
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