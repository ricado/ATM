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

import com.atm.dao.bbs.ClickGoodDAO;
import com.atm.model.bbs.ClickGood;
import com.atm.model.bbs.ClickGoodId;


/**
 * A data access object (DAO) providing persistence and search support for
 * ClickGood entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.ClickGood
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ClickGoodDAOImpl implements ClickGoodDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ClickGoodDAOImpl.class);
	// property constants

	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ClickGoodDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.ClickGoodDAO#save(com.atm.model.ClickGood)
	 */
	@Override
	public void save(ClickGood transientInstance) {
		log.debug("saving ClickGood instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ClickGoodDAO#delete(com.atm.model.ClickGood)
	 */
	@Override
	public void delete(ClickGood persistentInstance) {
		log.debug("deleting ClickGood instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ClickGoodDAO#findById(com.atm.model.ClickGoodId)
	 */
	@Override
	public ClickGood findById(ClickGoodId id) {
		log.debug("getting ClickGood instance with id: " + id);
		try {
			ClickGood instance = (ClickGood) getCurrentSession().get(
					"com.atm.model.ClickGood", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ClickGoodDAO#findByExample(com.atm.model.ClickGood)
	 */
	@Override
	public List findByExample(ClickGood instance) {
		log.debug("finding ClickGood instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.ClickGood")
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
	 * @see com.atm.dao.impl.ClickGoodDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ClickGood instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ClickGood as model where model."
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
	 * @see com.atm.dao.impl.ClickGoodDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all ClickGood instances");
		try {
			String queryString = "from ClickGood";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ClickGoodDAO#merge(com.atm.model.ClickGood)
	 */
	@Override
	public ClickGood merge(ClickGood detachedInstance) {
		log.debug("merging ClickGood instance");
		try {
			ClickGood result = (ClickGood) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ClickGoodDAO#attachDirty(com.atm.model.ClickGood)
	 */
	@Override
	public void attachDirty(ClickGood instance) {
		log.debug("attaching dirty ClickGood instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ClickGoodDAO#attachClean(com.atm.model.ClickGood)
	 */
	@Override
	public void attachClean(ClickGood instance) {
		log.debug("attaching clean ClickGood instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ClickGoodDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ClickGoodDAO) ctx.getBean("ClickGoodDAOImpl");
	}
}