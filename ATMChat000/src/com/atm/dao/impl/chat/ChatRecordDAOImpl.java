package com.atm.dao.impl.chat;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.chat.ChatRecordDAO;
import com.atm.model.chat.ChatRecord;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for ChatRecord entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.chat.ChatRecord
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class ChatRecordDAOImpl implements ChatRecordDAO  {
	     private static final Logger log = LoggerFactory.getLogger(ChatRecordDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.ChatRecordDAO#save(com.atm.model.ChatRecord)
	 */
    @Override
	public void save(ChatRecord transientInstance) {
        log.debug("saving ChatRecord instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#delete(com.atm.model.ChatRecord)
	 */
	@Override
	public void delete(ChatRecord persistentInstance) {
        log.debug("deleting ChatRecord instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#findById(java.lang.Integer)
	 */
    @Override
	public ChatRecord findById( java.lang.Integer id) {
        log.debug("getting ChatRecord instance with id: " + id);
        try {
            ChatRecord instance = (ChatRecord) getCurrentSession()
                    .get("com.atm.model.ChatRecord", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#findByExample(com.atm.model.ChatRecord)
	 */
    @Override
	public List findByExample(ChatRecord instance) {
        log.debug("finding ChatRecord instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.ChatRecord") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding ChatRecord instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from ChatRecord as model where model." 
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
	 * @see com.atm.dao.ChatRecordDAO#findByUserId(java.lang.Object)
	 */
	@Override
	public List findByUserId(Object userId
	) {
		return findByProperty(USER_ID, userId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#findByCrowdId(java.lang.Object)
	 */
	@Override
	public List findByCrowdId(Object crowdId
	) {
		return findByProperty(CROWD_ID, crowdId
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#findBySendContent(java.lang.Object)
	 */
	@Override
	public List findBySendContent(Object sendContent
	) {
		return findByProperty(SEND_CONTENT, sendContent
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#findByFlag(java.lang.Object)
	 */
	@Override
	public List findByFlag(Object flag
	) {
		return findByProperty(FLAG, flag
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all ChatRecord instances");
		try {
			String queryString = "from ChatRecord";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#merge(com.atm.model.ChatRecord)
	 */
    @Override
	public ChatRecord merge(ChatRecord detachedInstance) {
        log.debug("merging ChatRecord instance");
        try {
            ChatRecord result = (ChatRecord) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#attachDirty(com.atm.model.ChatRecord)
	 */
    @Override
	public void attachDirty(ChatRecord instance) {
        log.debug("attaching dirty ChatRecord instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.ChatRecordDAO#attachClean(com.atm.model.ChatRecord)
	 */
    @Override
	public void attachClean(ChatRecord instance) {
        log.debug("attaching clean ChatRecord instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static ChatRecordDAO getFromApplicationContext() {
    	return (ChatRecordDAO) ApplicationUtil.getApplicationContext().getBean("ChatRecordDAOImpl");
	}
/*	@Override
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
	
	 (non-Javadoc)
	 * @see com.atm.dao.ATMDAO#finfByHQL
	 
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
	}*/
}