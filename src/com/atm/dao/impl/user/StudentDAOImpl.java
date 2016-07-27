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

import com.atm.dao.user.StudentDAO;
import com.atm.model.Appeal;
import com.atm.model.user.Student;
import com.atm.util.ApplicationUtil;

/**
 	* A data access object (DAO) providing persistence and search support for Student entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.user.Student
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class StudentDAOImpl implements StudentDAO  {
	     private static final Logger log = LoggerFactory.getLogger(StudentDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.StudentDAO#save(com.atm.model.user.Student)
	 */
    @Override
	public void save(Student transientInstance) {
        log.debug("saving Student instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#delete(com.atm.model.user.Student)
	 */
	@Override
	public void delete(Student persistentInstance) {
        log.debug("deleting Student instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#findById(java.lang.String)
	 */
    @Override
	public Student findById( java.lang.String id) {
        log.debug("getting Student instance with id: " + id);
        try {
            Student instance = (Student) getCurrentSession()
                    .get("com.atm.model.user.Student", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#findByExample(com.atm.model.user.Student)
	 */
    @Override
	public List findByExample(Student instance) {
        log.debug("finding Student instance by example");
        try {
            List results = getCurrentSession().createCriteria("com.atm.model.user.Student") .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.info("finding Student instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Student as model where model." 
         						+ propertyName + " = ?";
         Query queryObject = getCurrentSession().createQuery(queryString)
        		 .setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }catch(Exception e){
    	  e.printStackTrace();
    	  throw e;
      }
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#findBySno(java.lang.Object)
	 */
	@Override
	public List findBySno(Object sno
	) {
		return findByProperty(SNO, sno
		);
	}
	
	/* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#findByEmail(java.lang.Object)
	 */
	@Override
	public List findByEmail(Object email
	) {
		log.info("find by email");
		return findByProperty(EMAIL, email
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Student instances");
		try {
			String queryString = "from Student";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#merge(com.atm.model.user.Student)
	 */
    @Override
	public Student merge(Student detachedInstance) {
        log.debug("merging Student instance");
        try {
            Student result = (Student) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#attachDirty(com.atm.model.user.Student)
	 */
    @Override
	public void attachDirty(Student instance) {
        log.debug("attaching dirty Student instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.StudentDAO#attachClean(com.atm.model.user.Student)
	 */
    @Override
	public void attachClean(Student instance) {
        log.debug("attaching clean Student instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static StudentDAO getFromApplicationContext() {
    	return (StudentDAO) ApplicationUtil.getApplicationContext().getBean("StudentDAOImpl");
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
	public int updateSno(String userId,String number){
		log.debug("update sno");
		try{
			String sql = "update Student s set s.sno='" + number + "'"
					+ " where s.userId='" + userId + "'";
			int i = getCurrentSession().createQuery(sql).executeUpdate();
			return i;
		}catch(Exception e){
			log.debug("update error");
			throw e;
		}
	}
}