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

import com.atm.model.define.bbs.ClickGoodView;



/**
 * A data access object (DAO) providing persistence and search support for
 * ClickGoodView entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.ClickGoodView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ClickGoodViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ClickGoodViewDAO.class);
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

	public void save(ClickGoodView transientInstance) {
		log.debug("saving ClickGoodView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ClickGoodView persistentInstance) {
		log.debug("deleting ClickGoodView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ClickGoodView findById(java.lang.String id) {
		log.debug("getting ClickGoodView instance with id: " + id);
		try {
			ClickGoodView instance = (ClickGoodView) getCurrentSession().get(
					"com.atm.model.define.ClickGoodView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ClickGoodView instance) {
		log.debug("finding ClickGoodView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.ClickGoodView")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value,int first) {
		log.debug("finding ClickGoodView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ClickGoodView as model where model."
					+ propertyName + "= ?"+
					"order by clickGoodTime desc";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setFirstResult(first);
			queryObject.setMaxResults(5);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByEssayId(int essayId,int first){
		return findByProperty("essayId",essayId,first);
	}

	public List findAll() {
		log.debug("finding all ClickGoodView instances");
		try {
			String queryString = "from ClickGoodView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ClickGoodView merge(ClickGoodView detachedInstance) {
		log.debug("merging ClickGoodView instance");
		try {
			ClickGoodView result = (ClickGoodView) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ClickGoodView instance) {
		log.debug("attaching dirty ClickGoodView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ClickGoodView instance) {
		log.debug("attaching clean ClickGoodView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ClickGoodViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ClickGoodViewDAO) ctx.getBean("ClickGoodViewDAO");
	}
}