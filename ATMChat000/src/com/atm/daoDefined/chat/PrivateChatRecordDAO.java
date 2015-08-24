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

import com.atm.model.define.chat.PrivateChatRecord;

/**
 * A data access object (DAO) providing persistence and search support for
 * PrivateChatRecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.chat.PrivateChatRecord
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PrivateChatRecordDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PrivateChatRecordDAO.class);
	// property constants
	public static final String USER_RECEIVE_ID = "userReceiveId";
	public static final String USER_SEND_ID = "userSendId";
	public static final String NICKNAME = "nickname";
	public static final String SEND_CONTENT = "sendContent";
	public static final String FLAG = "flag";

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

	public void save(PrivateChatRecord transientInstance) {
		log.debug("saving PrivateChatRecord instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PrivateChatRecord persistentInstance) {
		log.debug("deleting PrivateChatRecord instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PrivateChatRecord findById(java.lang.Integer id) {
		log.debug("getting PrivateChatRecord instance with id: " + id);
		try {
			PrivateChatRecord instance = (PrivateChatRecord) getCurrentSession()
					.get("com.atm.model.define.chat.PrivateChatRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PrivateChatRecord instance) {
		log.debug("finding PrivateChatRecord instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria(
							"com.atm.model.define.chat.PrivateChatRecord")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<PrivateChatRecord> findByProperty(String propertyName, Object value) {
		log.debug("finding PrivateChatRecord instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PrivateChatRecord as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PrivateChatRecord> findByUserReceiveId(Object userReceiveId) {
		return findByProperty(USER_RECEIVE_ID, userReceiveId);
	}

	public List findByUserSendId(Object userSendId) {
		return findByProperty(USER_SEND_ID, userSendId);
	}

	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List findBySendContent(Object sendContent) {
		return findByProperty(SEND_CONTENT, sendContent);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findAll() {
		log.debug("finding all PrivateChatRecord instances");
		try {
			String queryString = "from PrivateChatRecord";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PrivateChatRecord merge(PrivateChatRecord detachedInstance) {
		log.debug("merging PrivateChatRecord instance");
		try {
			PrivateChatRecord result = (PrivateChatRecord) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PrivateChatRecord instance) {
		log.debug("attaching dirty PrivateChatRecord instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PrivateChatRecord instance) {
		log.debug("attaching clean PrivateChatRecord instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PrivateChatRecordDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PrivateChatRecordDAO) ctx.getBean("PrivateChatRecordDAO");
	}
}