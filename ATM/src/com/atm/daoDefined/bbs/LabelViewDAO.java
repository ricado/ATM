package com.atm.daoDefined.bbs;

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

import com.atm.model.define.bbs.LabelView;

/**
 	* A data access object (DAO) providing persistence and search support for LabelView entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.define.LabelView
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class LabelViewDAO  {
	     private static final Logger log = LoggerFactory.getLogger(LabelViewDAO.class);
		//property constants
	public static final String LAB_NAME = "labName";
	public static final String ATTEND_NUM = "attendNum";



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
    
    public void save(LabelView transientInstance) {
        log.debug("saving LabelView instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(LabelView persistentInstance) {
        log.debug("deleting LabelView instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public LabelView findById( java.lang.Integer id) {
        log.debug("getting LabelView instance with id: " + id);
        try {
            LabelView instance = (LabelView) getCurrentSession()
                    .get("com.atm.model.define.LabelView", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(LabelView instance) {
        log.debug("finding LabelView instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.define.LabelView") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding LabelView instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from LabelView as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getCurrentSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    //TODO 获取热门标签
    public List findHotLabel() {
        try {
           String queryString = "select labName from LabelView where labName<>'未设置标签' order by attendNum desc"; 
           Query queryObject = getCurrentSession().createQuery(queryString);
  		 queryObject.setMaxResults(10);
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
	
	public List findByAttendNum(Object attendNum
	) {
		return findByProperty(ATTEND_NUM, attendNum
		);
	}
	

	public List findAll() {
		log.debug("finding all LabelView instances");
		try {
			String queryString = "from LabelView";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public LabelView merge(LabelView detachedInstance) {
        log.debug("merging LabelView instance");
        try {
            LabelView result = (LabelView) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(LabelView instance) {
        log.debug("attaching dirty LabelView instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(LabelView instance) {
        log.debug("attaching clean LabelView instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static LabelViewDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (LabelViewDAO) ctx.getBean("LabelViewDAO");
	}
}