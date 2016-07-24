package com.atm.daoDefined;

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

import com.atm.model.define.RecommendSm;

/**
 * A data access object (DAO) providing persistence and search support for
 * RecommendSm entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.RecommendSm
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class RecommendSmDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RecommendSmDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String TIME = "time";
	public static final String REASON = "reason";

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

	public void save(RecommendSm transientInstance) {
		log.info("saving RecommendSm instance");
		try {
			getCurrentSession().save(transientInstance);
			log.info("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RecommendSm persistentInstance) {
		log.debug("deleting RecommendSm instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RecommendSm findById(java.lang.Integer id) {
		log.debug("getting RecommendSm instance with id: " + id);
		try {
			RecommendSm instance = (RecommendSm) getCurrentSession().get(
					"com.atm.model.define.RecommendSm", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RecommendSm instance) {
		log.debug("finding RecommendSm instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.RecommendSm")
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
		log.debug("finding RecommendSm instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RecommendSm as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findByReason(Object reason) {
		return findByProperty(REASON, reason);
	}

	public List findAll() {
		log.debug("finding all RecommendSm instances");
		try {
			String queryString = "from RecommendSm";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RecommendSm merge(RecommendSm detachedInstance) {
		log.debug("merging RecommendSm instance");
		try {
			RecommendSm result = (RecommendSm) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RecommendSm instance) {
		log.debug("attaching dirty RecommendSm instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RecommendSm instance) {
		log.debug("attaching clean RecommendSm instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RecommendSmDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (RecommendSmDAO) ctx.getBean("RecommendSmDAO");
	}
}