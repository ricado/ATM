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

import com.atm.dao.bbs.EssayClickDAO;
import com.atm.model.bbs.EssayClick;



/**
 * A data access object (DAO) providing persistence and search support for
 * EssayClick entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.EssayClick
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class EssayClickDAOImpl implements EssayClickDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EssayClickDAOImpl.class);
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.model.EssayClickDAO#save(com.atm.model.EssayClick)
	 */
	@Override
	public void save(EssayClick transientInstance) {
		log.debug("saving EssayClick instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#delete(com.atm.model.EssayClick)
	 */
	@Override
	public void delete(EssayClick persistentInstance) {
		log.debug("deleting EssayClick instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#findById(java.lang.Integer)
	 */
	@Override
	public EssayClick findById(java.lang.Integer id) {
		log.debug("getting EssayClick instance with id: " + id);
		try {
			EssayClick instance = (EssayClick) getCurrentSession().get(
					"com.atm.model.EssayClick", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#findByExample(com.atm.model.EssayClick)
	 */
	@Override
	public List findByExample(EssayClick instance) {
		log.debug("finding EssayClick instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.EssayClick")
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
	 * @see com.atm.model.EssayClickDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding EssayClick instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from EssayClick as model where model."
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
	 * @see com.atm.model.EssayClickDAO#findByClickNum(java.lang.Object)
	 */
	@Override
	public List findByClickNum(Object clickNum) {
		return findByProperty(CLICK_NUM, clickNum);
	}

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all EssayClick instances");
		try {
			String queryString = "from EssayClick";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#merge(com.atm.model.EssayClick)
	 */
	@Override
	public EssayClick merge(EssayClick detachedInstance) {
		log.debug("merging EssayClick instance");
		try {
			EssayClick result = (EssayClick) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#attachDirty(com.atm.model.EssayClick)
	 */
	@Override
	public void attachDirty(EssayClick instance) {
		log.debug("进入attachDirty方法");
		log.debug("attaching dirty EssayClick instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.model.EssayClickDAO#attachClean(com.atm.model.EssayClick)
	 */
	@Override
	public void attachClean(EssayClick instance) {
		log.debug("attaching clean EssayClick instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EssayClickDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EssayClickDAO) ctx.getBean("EssayClickDAOImpl");
	}
}