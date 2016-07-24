package com.atm.model.define;

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

/**
 * A data access object (DAO) providing persistence and search support for
 * WebSite entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.define.WebSite
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class WebSiteDAO {
	private static final Logger log = LoggerFactory.getLogger(WebSiteDAO.class);
	// property constants
	public static final String WEB_SITE = "webSite";

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

	public void save(WebSite transientInstance) {
		log.debug("saving WebSite instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(WebSite persistentInstance) {
		log.debug("deleting WebSite instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WebSite findById(java.lang.Integer id) {
		log.debug("getting WebSite instance with id: " + id);
		try {
			WebSite instance = (WebSite) getCurrentSession().get(
					"com.atm.model.define.WebSite", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(WebSite instance) {
		log.debug("finding WebSite instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.WebSite")
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
		log.debug("finding WebSite instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from WebSite as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByWebSite(Object webSite) {
		return findByProperty(WEB_SITE, webSite);
	}

	public List findAll() {
		log.debug("finding all WebSite instances");
		try {
			String queryString = "from WebSite";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public WebSite merge(WebSite detachedInstance) {
		log.debug("merging WebSite instance");
		try {
			WebSite result = (WebSite) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(WebSite instance) {
		log.debug("attaching dirty WebSite instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WebSite instance) {
		log.debug("attaching clean WebSite instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static WebSiteDAO getFromApplicationContext(ApplicationContext ctx) {
		return (WebSiteDAO) ctx.getBean("WebSiteDAO");
	}
}