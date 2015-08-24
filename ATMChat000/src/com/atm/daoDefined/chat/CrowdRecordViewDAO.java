package com.atm.daoDefined.chat;

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

import com.atm.model.define.chat.CrowdRecordView;

/**
 * A data access object (DAO) providing persistence and search support for
 * CrowdRecordView entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.chat.CrowdRecordView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CrowdRecordViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdRecordViewDAO.class);
	// property constants
	public static final String CROWD_ID = "crowdId";
	public static final String CROWD_NAME = "crowdName";
	public static final String SEND_CONTENT = "sendContent";
	public static final String NICKNAME = "nickname";
	public static final String HEAD_IMAGE_PATH = "headImagePath";
	public static final String USER_ID = "userId";

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

	public void save(CrowdRecordView transientInstance) {
		log.debug("saving CrowdRecordView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CrowdRecordView persistentInstance) {
		log.debug("deleting CrowdRecordView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CrowdRecordView findById(java.lang.Integer id) {
		log.debug("getting CrowdRecordView instance with id: " + id);
		try {
			CrowdRecordView instance = (CrowdRecordView) getCurrentSession()
					.get("com.atm.model.define.CrowdRecordView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CrowdRecordView instance) {
		log.debug("finding CrowdRecordView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.CrowdRecordView")
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
		log.debug("finding CrowdRecordView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CrowdRecordView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCrowdId(Object crowdId) {
		return findByProperty(CROWD_ID, crowdId);
	}

	public List findByCrowdName(Object crowdName) {
		return findByProperty(CROWD_NAME, crowdName);
	}

	public List findBySendContent(Object sendContent) {
		return findByProperty(SEND_CONTENT, sendContent);
	}

	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List findByHeadImagePath(Object headImagePath) {
		return findByProperty(HEAD_IMAGE_PATH, headImagePath);
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findAll() {
		log.debug("finding all CrowdRecordView instances");
		try {
			String queryString = "from CrowdRecordView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CrowdRecordView merge(CrowdRecordView detachedInstance) {
		log.debug("merging CrowdRecordView instance");
		try {
			CrowdRecordView result = (CrowdRecordView) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CrowdRecordView instance) {
		log.debug("attaching dirty CrowdRecordView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CrowdRecordView instance) {
		log.debug("attaching clean CrowdRecordView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CrowdRecordViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CrowdRecordViewDAO) ctx.getBean("CrowdRecordViewDAO");
	}

}