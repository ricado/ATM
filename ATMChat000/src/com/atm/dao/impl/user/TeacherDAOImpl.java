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

import com.atm.dao.user.TeacherDAO;
import com.atm.model.Appeal;
import com.atm.model.user.Teacher;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for Teacher entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.user.Teacher
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class TeacherDAOImpl implements TeacherDAO  {
	     private static final Logger log = LoggerFactory.getLogger(TeacherDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.TeacherDAO#save(com.atm.model.Teacher)
	 */
    @Override
	public void save(Teacher transientInstance) {
        log.debug("saving Teacher instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#delete(com.atm.model.Teacher)
	 */
	@Override
	public void delete(Teacher persistentInstance) {
        log.debug("deleting Teacher instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#findById(java.lang.String)
	 */
    @Override
	public Teacher findById( java.lang.String id) {
        log.debug("getting Teacher instance with id: " + id);
        try {
            Teacher instance = (Teacher) getCurrentSession()
                    .get("com.atm.model.Teacher", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#findByExample(com.atm.model.Teacher)
	 */
    @Override
	public List findByExample(Teacher instance) {
        log.debug("finding Teacher instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.Teacher") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding Teacher instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Teacher as model where model." 
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
	 * @see com.atm.dao.TeacherDAO#findByTno(java.lang.Object)
	 */
	@Override
	public List findByTno(Object tno
	) {
		return findByProperty(TNO, tno
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#findByEmail(java.lang.Object)
	 */
	@Override
	public List findByEmail(Object email
	) {
		return findByProperty(EMAIL, email
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Teacher instances");
		try {
			String queryString = "from Teacher";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#merge(com.atm.model.Teacher)
	 */
    @Override
	public Teacher merge(Teacher detachedInstance) {
        log.debug("merging Teacher instance");
        try {
            Teacher result = (Teacher) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#attachDirty(com.atm.model.Teacher)
	 */
    @Override
	public void attachDirty(Teacher instance) {
        log.debug("attaching dirty Teacher instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.TeacherDAO#attachClean(com.atm.model.Teacher)
	 */
    @Override
	public void attachClean(Teacher instance) {
        log.debug("attaching clean Teacher instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static TeacherDAO getFromApplicationContext() {
    	return (TeacherDAO) ApplicationUtil.getApplicationContext().getBean("TeacherDAOImpl");
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
	
	/**
	 * 修改学生的学号
	 * @param userId
	 * @param number
	 * @return
	 */
	@Override
	public int updateTno(String userId,String number){
		log.debug("update sno");
		try{
			String sql = "update Teacher t set t.tno='" + number + "'"
					+ " where t.userId='" + userId + "'";
			int i = getCurrentSession().createQuery(sql).executeUpdate();
			return i;
		}catch(Exception e){
			log.debug("update error");
			throw e;
		}
	}
}