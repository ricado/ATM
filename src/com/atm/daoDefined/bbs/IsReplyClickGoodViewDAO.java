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

import com.atm.model.define.bbs.IsReplyClickGoodView;


/**
 * A data access object (DAO) providing persistence and search support for
 * IsReplyClickGoodView entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.bbs.IsReplyClickGoodView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class IsReplyClickGoodViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(IsReplyClickGoodViewDAO.class);
	// property constants

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

	public void save(IsReplyClickGoodView transientInstance) {
		log.debug("saving IsReplyClickGoodView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IsReplyClickGoodView persistentInstance) {
		log.debug("deleting IsReplyClickGoodView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IsReplyClickGoodView findById(java.lang.String id) {
		log.debug("getting IsReplyClickGoodView instance with id: " + id);
		try {
			IsReplyClickGoodView instance = (IsReplyClickGoodView) getCurrentSession()
					.get("com.atm.model.define.bbs.IsReplyClickGoodView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IsReplyClickGoodView instance) {
		log.debug("finding IsReplyClickGoodView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.bbs.IsReplyClickGoodView")
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
		log.debug("finding IsReplyClickGoodView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IsReplyClickGoodView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all IsReplyClickGoodView instances");
		try {
			String queryString = "from IsReplyClickGoodView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IsReplyClickGoodView merge(IsReplyClickGoodView detachedInstance) {
		log.debug("merging IsReplyClickGoodView instance");
		try {
			IsReplyClickGoodView result = (IsReplyClickGoodView) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IsReplyClickGoodView instance) {
		log.debug("attaching dirty IsReplyClickGoodView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IsReplyClickGoodView instance) {
		log.debug("attaching clean IsReplyClickGoodView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IsReplyClickGoodViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IsReplyClickGoodViewDAO) ctx.getBean("IsReplyClickGoodViewDAO");
	}
}