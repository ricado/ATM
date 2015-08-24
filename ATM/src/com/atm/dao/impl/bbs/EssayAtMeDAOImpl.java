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

import com.atm.dao.bbs.EssayAtMeDAO;
import com.atm.model.bbs.EssayAtMe;

/**
 * A data access object (DAO) providing persistence and search support for
 * EssayAtMe entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.EssayAtMe
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class EssayAtMeDAOImpl implements EssayAtMeDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EssayAtMeDAOImpl.class);
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.EssayAtMeDAO#save(com.atm.model.EssayAtMe)
	 */
	@Override
	public void save(EssayAtMe transientInstance) {
		log.debug("saving EssayAtMe instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#delete(com.atm.model.EssayAtMe)
	 */
	@Override
	public void delete(EssayAtMe persistentInstance) {
		log.debug("deleting EssayAtMe instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#findById(java.lang.Integer)
	 */
	@Override
	public EssayAtMe findById(java.lang.Integer id) {
		log.debug("getting EssayAtMe instance with id: " + id);
		try {
			EssayAtMe instance = (EssayAtMe) getCurrentSession().get(
					"com.atm.model.EssayAtMe", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#findByExample(com.atm.model.EssayAtMe)
	 */
	@Override
	public List findByExample(EssayAtMe instance) {
		log.debug("finding EssayAtMe instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.EssayAtMe")
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
	 * @see com.atm.dao.impl.EssayAtMeDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding EssayAtMe instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from EssayAtMe as model where model."
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
	 * @see com.atm.dao.impl.EssayAtMeDAO#findByUserBeAtId(java.lang.Object)
	 */
	@Override
	public List findByUserBeAtId(Object userBeAtId) {
		return findByProperty(USER_BE_AT_ID, userBeAtId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#findByUserAtOtherId(java.lang.Object)
	 */
	@Override
	public List findByUserAtOtherId(Object userAtOtherId) {
		return findByProperty(USER_AT_OTHER_ID, userAtOtherId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all EssayAtMe instances");
		try {
			String queryString = "from EssayAtMe";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#merge(com.atm.model.EssayAtMe)
	 */
	@Override
	public EssayAtMe merge(EssayAtMe detachedInstance) {
		log.debug("merging EssayAtMe instance");
		try {
			EssayAtMe result = (EssayAtMe) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#attachDirty(com.atm.model.EssayAtMe)
	 */
	@Override
	public void attachDirty(EssayAtMe instance) {
		log.debug("attaching dirty EssayAtMe instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayAtMeDAO#attachClean(com.atm.model.EssayAtMe)
	 */
	@Override
	public void attachClean(EssayAtMe instance) {
		log.debug("attaching clean EssayAtMe instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EssayAtMeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EssayAtMeDAO) ctx.getBean("EssayAtMeDAOImpl");
	}
}