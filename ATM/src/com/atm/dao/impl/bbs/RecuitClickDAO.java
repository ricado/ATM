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

import com.atm.model.bbs.RecuitClick;

/**
 * A data access object (DAO) providing persistence and search support for
 * RecuitClick entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.bbs.RecuitClick
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class RecuitClickDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RecuitClickDAO.class);
	// property constants
	public static final String CLICK_NUM = "clickNum";

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	public void save(RecuitClick transientInstance) {
		log.debug("saving RecuitClick instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RecuitClick persistentInstance) {
		log.debug("deleting RecuitClick instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RecuitClick findById(java.lang.Integer id) {
		log.debug("getting RecuitClick instance with id: " + id);
		try {
			RecuitClick instance = (RecuitClick) getCurrentSession().get(
					"com.atm.model.bbs.RecuitClick", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RecuitClick instance) {
		log.debug("finding RecuitClick instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.bbs.RecuitClick")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding RecuitClick instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RecuitClick as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClickNum(Object clickNum) {
		return findByProperty(CLICK_NUM, clickNum);
	}

	public List findAll() {
		log.debug("finding all RecuitClick instances");
		try {
			String queryString = "from RecuitClick";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RecuitClick merge(RecuitClick detachedInstance) {
		log.debug("merging RecuitClick instance");
		try {
			RecuitClick result = (RecuitClick) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RecuitClick instance) {
		log.debug("attaching dirty RecuitClick instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RecuitClick instance) {
		log.debug("attaching clean RecuitClick instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RecuitClickDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (RecuitClickDAO) ctx.getBean("RecuitClickDAO");
	}
}