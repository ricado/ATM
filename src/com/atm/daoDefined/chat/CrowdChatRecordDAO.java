package com.atm.daoDefined.chat;

import java.sql.Timestamp;
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

import com.atm.model.define.chat.CrowdChatRecord;

/**
 * A data access object (DAO) providing persistence and search support for
 * CrowdChatRecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.chat.CrowdChatRecord
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CrowdChatRecordDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdChatRecordDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String CROWD_ID = "crowdId";
	public static final String SEND_CONTENT = "sendContent";
	public static final String FLAG = "flag";
	public static final String NICKNAME = "nickname";

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

	public void save(CrowdChatRecord transientInstance) {
		log.debug("saving CrowdChatRecord instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CrowdChatRecord persistentInstance) {
		log.debug("deleting CrowdChatRecord instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CrowdChatRecord findById(java.lang.Integer id) {
		log.debug("getting CrowdChatRecord instance with id: " + id);
		try {
			CrowdChatRecord instance = (CrowdChatRecord) getCurrentSession()
					.get("com.atm.model.define.chat.CrowdChatRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CrowdChatRecord instance) {
		log.debug("finding CrowdChatRecord instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.chat.CrowdChatRecord")
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
		log.debug("finding CrowdChatRecord instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CrowdChatRecord as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByCrowdId(Object crowdId) {
		return findByProperty(CROWD_ID, crowdId);
	}

	public List findBySendContent(Object sendContent) {
		return findByProperty(SEND_CONTENT, sendContent);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List findAll() {
		log.debug("finding all CrowdChatRecord instances");
		try {
			String queryString = "from CrowdChatRecord";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CrowdChatRecord merge(CrowdChatRecord detachedInstance) {
		log.debug("merging CrowdChatRecord instance");
		try {
			CrowdChatRecord result = (CrowdChatRecord) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CrowdChatRecord instance) {
		log.debug("attaching dirty CrowdChatRecord instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CrowdChatRecord instance) {
		log.debug("attaching clean CrowdChatRecord instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CrowdChatRecordDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CrowdChatRecordDAO) ctx.getBean("CrowdChatRecordDAO");
	}
}