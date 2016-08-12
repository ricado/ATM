package com.atm.daoDefined;

import java.util.ArrayList;
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

import com.atm.model.define.MyMessage;

/**
 * A data access object (DAO) providing persistence and search support for
 * MyMessage entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.daoDefined.MyMessage
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class MyMessageDAO {
	private static final Logger log = LoggerFactory.getLogger(MyMessageDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String TYPE = "type";
	public static final String CONTENT = "content";

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

	public void save(MyMessage transientInstance) {
		log.debug("saving MyMessage instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MyMessage persistentInstance) {
		log.debug("deleting MyMessage instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MyMessage findById(java.lang.Integer id) {
		log.debug("getting MyMessage instance with id: " + id);
		try {
			MyMessage instance = (MyMessage) getCurrentSession().get("com.atm.daoDefined.MyMessage", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MyMessage instance) {
		log.debug("finding MyMessage instance by example");
		try {
			List results = getCurrentSession().createCriteria("com.atm.daoDefined.MyMessage")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding MyMessage instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from MyMessage as model where model." + propertyName + "= ?";
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

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all MyMessage instances");
		try {
			String queryString = "from MyMessage";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MyMessage merge(MyMessage detachedInstance) {
		log.debug("merging MyMessage instance");
		try {
			MyMessage result = (MyMessage) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MyMessage instance) {
		log.debug("attaching dirty MyMessage instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MyMessage instance) {
		log.debug("attaching clean MyMessage instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MyMessageDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MyMessageDAO) ctx.getBean("MyMessageDAO");
	}

	/**
	 * 使用userid和type查找消息类型
	 * 
	 * @param userId
	 * @param type
	 */
	public List<MyMessage> findMyMessage(String userId, int type) {
		List<MyMessage> list = new ArrayList<>();
		log.debug("finding all MyMessage by userId and type");
		try {
			String queryString = "select * from mymessage where userId='" + userId + "' and type=" + type;
			list = getCurrentSession().createQuery(queryString).list();
			return list;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}