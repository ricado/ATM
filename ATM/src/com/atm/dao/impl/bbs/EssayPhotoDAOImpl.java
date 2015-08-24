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

import com.atm.dao.bbs.EssayPhotoDAO;
import com.atm.model.bbs.EssayPhoto;
import com.atm.model.bbs.EssayPhotoId;


/**
 * A data access object (DAO) providing persistence and search support for
 * EssayPhoto entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.EssayPhoto
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class EssayPhotoDAOImpl implements EssayPhotoDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EssayPhotoDAOImpl.class);
	// property constants

	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayPhotoDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.EssayPhotoDAO#save(com.atm.model.EssayPhoto)
	 */
	@Override
	public void save(EssayPhoto transientInstance) {
		log.debug("saving EssayPhoto instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayPhotoDAO#delete(com.atm.model.EssayPhoto)
	 */
	@Override
	public void delete(EssayPhoto persistentInstance) {
		log.debug("deleting EssayPhoto instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayPhotoDAO#findById(com.atm.model.EssayPhotoId)
	 */
	@Override
	public EssayPhoto findById(EssayPhotoId id) {
		log.debug("getting EssayPhoto instance with id: " + id);
		try {
			EssayPhoto instance = (EssayPhoto) getCurrentSession().get(
					"com.atm.model.EssayPhoto", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayPhotoDAO#findByExample(com.atm.model.EssayPhoto)
	 */
	@Override
	public List findByExample(EssayPhoto instance) {
		log.debug("finding EssayPhoto instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.EssayPhoto")
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
	 * @see com.atm.dao.impl.EssayPhotoDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding EssayPhoto instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from EssayPhoto as model where model."
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
	 * @see com.atm.dao.impl.EssayPhotoDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all EssayPhoto instances");
		try {
			String queryString = "from EssayPhoto";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayPhotoDAO#merge(com.atm.model.EssayPhoto)
	 */
	@Override
	public EssayPhoto merge(EssayPhoto detachedInstance) {
		log.debug("merging EssayPhoto instance");
		try {
			EssayPhoto result = (EssayPhoto) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayPhotoDAO#attachDirty(com.atm.model.EssayPhoto)
	 */
	@Override
	public void attachDirty(EssayPhoto instance) {
		log.debug("attaching dirty EssayPhoto instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayPhotoDAO#attachClean(com.atm.model.EssayPhoto)
	 */
	@Override
	public void attachClean(EssayPhoto instance) {
		log.debug("attaching clean EssayPhoto instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EssayPhotoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EssayPhotoDAO) ctx.getBean("EssayPhotoDAOImpl");
	}
}