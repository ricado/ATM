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

import com.atm.dao.user.DepartmentDAO;
import com.atm.model.user.Department;

/**
 	* A data access object (DAO) providing persistence and search support for Department entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.atm.model.user.Department
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class DepartmentDAOImpl implements DepartmentDAO  {
	     private static final Logger log = LoggerFactory.getLogger(DepartmentDAOImpl.class);
		private SessionFactory sessionFactory;

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.DepartmentDAO#save(com.atm.model.Department)
	 */
    @Override
	public void save(Department transientInstance) {
        log.debug("saving Department instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#delete(com.atm.model.Department)
	 */
	@Override
	public void delete(Department persistentInstance) {
        log.debug("deleting Department instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#findById(java.lang.String)
	 */
    @Override
	public Department findById( java.lang.String id) {
        log.debug("getting Department instance with id: " + id);
        try {
            Department instance = (Department) getCurrentSession()
                    .get(Department.class, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#findByExample(com.atm.model.Department)
	 */
    @Override
	public List findByExample(Department instance) {
        log.debug("finding Department instance by example");
        try {
            List results = getCurrentSession().createCriteria(Department.class) .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    @Override
	public List findByProperty(String propertyName, Object value) {
      log.debug("finding Department instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "select new Department(dno,dname)"+
        		 	"from Department as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getCurrentSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
	public List findDeptList(String scNo) {
	      try {
	         String queryString = "select new Department(dno,dname)"+
	        		 	"from Department as model where model.scno" 
	         						+ "= ?";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 queryObject.setParameter(0, scNo);
			 return queryObject.list();
	      } catch (RuntimeException re) {
	         log.error("find by property name failed", re);
	         throw re;
	      }
		}
	public List findByDNameAndScNo(String dName, String scNo) {
	      try {
	         String queryString = "select new Department(dno,dname)"+
	        		 	"from Department where dname='"
	        		 +dName+"' and scno='"
	        		 	+scNo+"'"; 
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
	      } catch (RuntimeException re) {
	         log.error("find by property name failed", re);
	         throw re;
	      }
		}
	/* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#findByDname(java.lang.Object)
	 */
	@Override
	public List findByDname(Object dname
	) {
		return findByProperty(DNAME, dname
		);
	}
	

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Department instances");
		try {
			String queryString = "from Department";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#merge(com.atm.model.Department)
	 */
    @Override
	public Department merge(Department detachedInstance) {
        log.debug("merging Department instance");
        try {
            Department result = (Department) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#attachDirty(com.atm.model.Department)
	 */
    @Override
	public void attachDirty(Department instance) {
        log.debug("attaching dirty Department instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /* (non-Javadoc)
	 * @see com.atm.dao.impl.DepartmentDAO#attachClean(com.atm.model.Department)
	 */
    @Override
	public void attachClean(Department instance) {
        log.debug("attaching clean Department instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static DepartmentDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (DepartmentDAO) ctx.getBean("DepartmentDAOImpl");
	}
}