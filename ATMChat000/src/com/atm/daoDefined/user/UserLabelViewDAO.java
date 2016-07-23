package com.atm.daoDefined.user;

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

import com.atm.model.define.user.UserLabelView;

/**
 	* A data access object (DAO) providing persistence and search support for UserLabelView entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.com.atm.model.define.user.UserLabelView
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class UserLabelViewDAO  {
	     private static final Logger log = LoggerFactory.getLogger(UserLabelViewDAO.class);
		//property constants
	public static final String LAB_NAME = "labName";
	public static final String NICKNAME = "nickname";
	public static final String USER_ID = "userId";
	public static final String LAB_ID = "labId";


    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory){
       this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession(){
     return sessionFactory.getCurrentSession(); 
    }
	protected void initDao() {
		//do nothing
	}
    
    public void save(UserLabelView transientInstance) {
        log.debug("saving UserLabelView instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(UserLabelView persistentInstance) {
        log.debug("deleting UserLabelView instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
      
    public List findByExample(UserLabelView instance) {
        log.debug("finding UserLabelView instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.define.UserLabelView") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding UserLabelView instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UserLabelView as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getCurrentSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByLabName(Object labName
	) {
		return findByProperty(LAB_NAME, labName
		);
	}
	
	public List findByNickname(Object nickname
	) {
		return findByProperty(NICKNAME, nickname
		);
	}
	
	public List fingByUserId(Object userId){
		return findByProperty(USER_ID, userId);
	}
	
	public List fingByLabId(Object labId){
		return findByProperty(LAB_ID, labId);
	}

	public List findAll() {
		log.debug("finding all UserLabelView instances");
		try {
			String queryString = "from UserLabelView";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public UserLabelView merge(UserLabelView detachedInstance) {
        log.debug("merging UserLabelView instance");
        try {
            UserLabelView result = (UserLabelView) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserLabelView instance) {
        log.debug("attaching dirty UserLabelView instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(UserLabelView instance) {
        log.debug("attaching clean UserLabelView instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static UserLabelViewDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (UserLabelViewDAO) ctx.getBean("UserLabelViewDAO");
	}
	
	
	public List findByHQL(String HQL){
		log.debug("UserLavel----select by HQL");
		try{
			List list = getCurrentSession().createQuery(HQL).list();
			log.debug("select by HQL-------success");
			return list;
		}catch(RuntimeException e){
			log.debug("select by HQL-------error");
			throw e;
		}
	}
}