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

import com.atm.dao.chat.CrowdChatDAO;
import com.atm.model.chat.CrowdChat;
import com.atm.model.define.chat.CrowdInfo;
import com.atm.model.define.chat.CrowdList;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for CrowdChat entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.chat.CrowdChat
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class CrowdChatDAOImpl implements CrowdChatDAO  {
	     private static final Logger log = LoggerFactory.getLogger(CrowdChatDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.CrowdChatDAO#save(com.atm.model.CrowdChat)
	 */
    @Override
	public void save(CrowdChat transientInstance) {
        log.debug("saving CrowdChat instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#delete(com.atm.model.CrowdChat)
	 */
	@Override
	public void delete(CrowdChat persistentInstance) {
        log.debug("deleting CrowdChat instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findById(java.lang.Integer)
	 */
    @Override
	public CrowdChat findById( java.lang.Integer id) {
        log.debug("getting CrowdChat instance with id: " + id);
        try {
            CrowdChat instance = (CrowdChat) getCurrentSession()
                    .get("com.atm.model.CrowdChat", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByExample(com.atm.model.CrowdChat)
	 */
    @Override
	public List findByExample(CrowdChat instance) {
        log.debug("finding CrowdChat instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.CrowdChat") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding CrowdChat instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from CrowdChat as model where model." 
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
	 * @see com.atm.dao.CrowdChatDAO#findByCrowdLabel(java.lang.Object)
	 */
	@Override
	public List findByCrowdLabel(Object crowdLabel
	) {
		return findByProperty(CROWD_LABEL, crowdLabel
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByCrowdDescription(java.lang.Object)
	 */
	@Override
	public List findByCrowdDescription(Object crowdDescription
	) {
		return findByProperty(CROWD_DESCRIPTION, crowdDescription
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByCrowdHeadImage(java.lang.Object)
	 */
	@Override
	public List findByCrowdHeadImage(Object crowdHeadImage
	) {
		return findByProperty(CROWD_HEAD_IMAGE, crowdHeadImage
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByIsHidden(java.lang.Object)
	 */
	@Override
	public List findByIsHidden(Object isHidden
	) {
		return findByProperty(IS_HIDDEN, isHidden
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByCrowdName(java.lang.Object)
	 */
	@Override
	public List findByCrowdName(Object crowdName
	) {
		return findByProperty(CROWD_NAME, crowdName
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByNumLimit(java.lang.Object)
	 */
	@Override
	public List findByNumLimit(Object numLimit
	) {
		return findByProperty(NUM_LIMIT, numLimit
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findByVerifyMode(java.lang.Object)
	 */
	@Override
	public List findByVerifyMode(Object verifyMode
	) {
		return findByProperty(VERIFY_MODE, verifyMode
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all CrowdChat instances");
		try {
			String queryString = "from CrowdChat";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#merge(com.atm.model.CrowdChat)
	 */
    @Override
	public CrowdChat merge(CrowdChat detachedInstance) {
        log.debug("merging CrowdChat instance");
        try {
            CrowdChat result = (CrowdChat) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#attachDirty(com.atm.model.CrowdChat)
	 */
    @Override
	public void attachDirty(CrowdChat instance) {
        log.debug("attaching dirty CrowdChat instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.CrowdChatDAO#attachClean(com.atm.model.CrowdChat)
	 */
    @Override
	public void attachClean(CrowdChat instance) {
        log.debug("attaching clean CrowdChat instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static CrowdChatDAO getFromApplicationContext() {
    	return (CrowdChatDAO) ApplicationUtil.getApplicationContext().getBean("CrowdChatDAOImpl");
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
	
	@Override
	public List<CrowdInfo> findCrowd(String keyword,int type,int first,int max){
		log.info("find by keyword");
		try{
			List<CrowdInfo> list = getCurrentSession().getNamedQuery("findCrowd")
						.setString(0, keyword)
						.setInteger(1, type)
						.setInteger(2,first)
						.setInteger(3,max)
						.list();
			log.info("find success");
			return list;
		}catch(Exception e){
			log.info("error");
			throw e;
		}
	}
	
	@Override
	public List<CrowdList> findInterestingCrowd(String userId, int first, int max) {
		log.info("find interesting crowd by userId");
		try {
			List<CrowdList> chats = getCurrentSession()
					.getNamedQuery("interestingCrowd")
					.setString(0, userId)
					.setInteger(1, first)
					.setInteger(2, max)
					.list();
			log.info("find success");
			return chats;
		} catch (Exception e) {
			log.info("find error");
			throw e;
		}
	}
	@Override
	public List<CrowdList> findHotCrowd(int first,int max){
		log.info("find hot crowd by userId");
		try{
			List<CrowdList> list = getCurrentSession().getNamedQuery("hotCrowd")
					.setInteger(0, first)
					.setInteger(1, max)
					.list();
			log.info("success");
			return list;
		}catch(Exception e){
			log.info("error");
			throw e;
		}
	}
	@Override
	public List<CrowdList> findUsersCrowd(String userId,int first,int max){
		log.info("find user's crowd");
		try{
			List<CrowdList> crowdLists =
					getCurrentSession()
					.getNamedQuery("usersCrowd")
					.setString(0, userId)
					//.setString(0, userId)
					//.setFirstResult(1)
					//.setMaxResults(4)
					.list();
			log.info("find success");
			return crowdLists;
		}catch(Exception e){
			log.info("error");
			e.printStackTrace();
			throw e;
		}
	}
}