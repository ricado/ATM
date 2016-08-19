package com.atm.dao.impl.bbs;

import java.io.Serializable;
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

import com.atm.dao.bbs.EssayDAO;
import com.atm.model.bbs.Essay;


/**
 * A data access object (DAO) providing persistence and search support for Essay
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.atm.model.Essay
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class EssayDAOImpl implements EssayDAO {
	private static final Logger log = LoggerFactory.getLogger(EssayDAOImpl.class);
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.EssayDAO#save(com.atm.model.Essay)
	 */
	@Override
	public Serializable save(Essay transientInstance) {
		log.debug("saving Essay instance");
		try {
			Serializable id = getCurrentSession().save(transientInstance);
			log.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#delete(com.atm.model.Essay)
	 */
	@Override
	public void delete(Essay persistentInstance) {
		log.debug("deleting Essay instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findById(java.lang.Integer)
	 */
	@Override
	public Essay findById(java.lang.Integer id) {
		log.debug("getting Essay instance with id: " + id);
		try {
			Essay instance = (Essay) getCurrentSession().get(
					"com.atm.model.bbs.Essay", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findByExample(com.atm.model.Essay)
	 */
	@Override
	public List findByExample(Essay instance) {
		log.debug("finding Essay instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.Essay")
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
	 * @see com.atm.dao.impl.EssayDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Essay instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Essay as model where model."
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
	 * @see com.atm.dao.impl.EssayDAO#findByTypeId(java.lang.Object)
	 */
	@Override
	public List findByTypeId(Object typeId) {
		return findByProperty(TYPE_ID, typeId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findByTitle(java.lang.Object)
	 */
	@Override
	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findByContent(java.lang.Object)
	 */
	@Override
	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findByDno(java.lang.Object)
	 */
	@Override
	public List findByDno(Object dno) {
		return findByProperty(DNO, dno);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findByLabId(java.lang.Object)
	 */
	@Override
	public List findByLabId(Object labId) {
		return findByProperty(LAB_ID, labId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findByPublisherId(java.lang.Object)
	 */
	@Override
	public List findByPublisherId(Object publisherId) {
		return findByProperty(PUBLISHER_ID, publisherId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findByClickNum(java.lang.Object)
	 */
	@Override
	public List findByClickNum(Object clickNum) {
		return findByProperty(CLICK_NUM, clickNum);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Essay instances");
		try {
			String queryString = "from Essay";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#merge(com.atm.model.Essay)
	 */
	@Override
	public Essay merge(Essay detachedInstance) {
		log.debug("merging Essay instance");
		try {
			Essay result = (Essay) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#attachDirty(com.atm.model.Essay)
	 */
	@Override
	public void attachDirty(Essay instance) {
		log.debug("attaching dirty Essay instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.EssayDAO#attachClean(com.atm.model.Essay)
	 */
	@Override
	public void attachClean(Essay instance) {
		log.debug("attaching clean Essay instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EssayDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EssayDAO) ctx.getBean("EssayDAOImpl");
	}
	
	//TODO *********************�Զ��巽��********************************
	public List g(String propertyName, Object value) {
		log.debug("finding Essay instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Essay as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
}