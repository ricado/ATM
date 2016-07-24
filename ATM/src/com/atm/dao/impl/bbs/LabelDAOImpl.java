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

import com.atm.dao.bbs.LabelDAO;
import com.atm.model.bbs.Label;


/**
 	* A data access object (DAO) providing persistence and search support for Label entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.Label
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class LabelDAOImpl implements LabelDAO  {
	     private static final Logger log = LoggerFactory.getLogger(LabelDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.LabelDAO#save(com.atm.model.Label)
	 */
    @Override
	public void save(Label transientInstance) {
        log.debug("saving Label instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#delete(com.atm.model.Label)
	 */
	@Override
	public void delete(Label persistentInstance) {
        log.debug("deleting Label instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#findById(java.lang.Integer)
	 */
    @Override
	public Label findById( java.lang.Integer id) {
        log.debug("getting Label instance with id: " + id);
        try {
            Label instance = (Label) getCurrentSession()
                    .get("com.atm.model.Label", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#findByExample(com.atm.model.Label)
	 */
    @Override
	public List findByExample(Label instance) {
        log.debug("finding Label instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.Label") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding Label instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "select new Label(labId,labName)"+
        		 		"from Label as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getCurrentSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    
    /**
     * 根据系别获取该系的热门标签
     * "select l.labName from "+
            	"Label l "+
            	"left join LabelAttentionAssociation att "+
            	"left join  UserInfo u "+
            	 "where l.labName<>'未设置标签' and att.labId = l.labId and u.userId = att.userId and u.dno = ? "+
            	 "group by att.labId order by count(att.userId) desc"; 
     */
    @Override
    public List findByDno(Object dNo,int maxResult){
    	try {
            String queryString = 
            	"select l.labName from Label l,LabelAttentionAssociation att,UserInfo u "+
            	"where l.labName<>'未设置标签' and u.userId = att.userId and att.labId = l.labId and u.dno = ? "+
            	 "group by att.labId order by count(att.userId) desc"; 
            Query queryObject = getCurrentSession().createQuery(queryString);
            queryObject.setParameter(0,dNo);
	   		 queryObject.setMaxResults(maxResult);
	   		 return queryObject.list();
         } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
         }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#findByLabName(java.lang.Object)
	 */
	@Override
	public List findByLabName(Object labName
	) {
		return findByProperty(LAB_NAME, labName
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Label instances");
		try {
			String queryString = "from Label";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#merge(com.atm.model.Label)
	 */
    @Override
	public Label merge(Label detachedInstance) {
        log.debug("merging Label instance");
        try {
            Label result = (Label) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#attachDirty(com.atm.model.Label)
	 */
    @Override
	public void attachDirty(Label instance) {
        log.debug("attaching dirty Label instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.LabelDAO#attachClean(com.atm.model.Label)
	 */
    @Override
	public void attachClean(Label instance) {
        log.debug("attaching clean Label instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static LabelDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (LabelDAO) ctx.getBean("LabelDAOImpl");
	}
}