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

import com.atm.dao.bbs.CollectEssayDAO;
import com.atm.model.bbs.CollectEssay;
import com.atm.model.bbs.CollectEssayId;


/**
 * A data access object (DAO) providing persistence and search support for
 * CollectEssay entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.CollectEssay
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CollectEssayDAOImpl implements CollectEssayDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CollectEssayDAOImpl.class);
	// property constants

	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#save(com.atm.model.CollectEssay)
	 */
	@Override
	public void save(CollectEssay transientInstance) {
		log.debug("saving CollectEssay instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#delete(com.atm.model.CollectEssay)
	 */
	@Override
	public void delete(CollectEssay persistentInstance) {
		log.debug("deleting CollectEssay instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#findById(com.atm.model.CollectEssayId)
	 */
	@Override
	public CollectEssay findById(CollectEssayId id) {
		log.debug("getting CollectEssay instance with id: " + id);
		try {
			CollectEssay instance = (CollectEssay) getCurrentSession().get(
					"CollectEssay", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#findByExample(com.atm.model.CollectEssay)
	 */
	@Override
	public List findByExample(CollectEssay instance) {
		log.debug("finding CollectEssay instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.CollectEssay")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CollectEssay instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CollectEssay as model where model."
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
	 * @see com.atm.dao.impl.CollectEssayDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all CollectEssay instances");
		try {
			String queryString = "from CollectEssay";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#merge(com.atm.model.CollectEssay)
	 */
	@Override
	public CollectEssay merge(CollectEssay detachedInstance) {
		log.debug("merging CollectEssay instance");
		try {
			CollectEssay result = (CollectEssay) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#attachDirty(com.atm.model.CollectEssay)
	 */
	@Override
	public void attachDirty(CollectEssay instance) {
		log.debug("attaching dirty CollectEssay instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.CollectEssayDAO#attachClean(com.atm.model.CollectEssay)
	 */
	@Override
	public void attachClean(CollectEssay instance) {
		log.debug("attaching clean CollectEssay instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CollectEssayDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CollectEssayDAO) ctx.getBean("CollectEssayDAOImpl");
	}
	@Override
	public void saveOrUpdate(Object transientInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List findByHQL(String HQL) {
		// TODO Auto-generated method stub
		return null;
	}
}