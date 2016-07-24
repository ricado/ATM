package com.atm.daoDefined.user;

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

import com.atm.model.define.user.UserBasicInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserBasicInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.user.UserBasicInfo
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserBasicInfoDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserBasicInfoDAO.class);
	// property constants
	public static final String HEAD_IMAGE_PATH = "headImagePath";
	public static final String SIGN = "sign";
	public static final String SC_NAME = "scName";
	public static final String DNAME = "dname";
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

	public void save(UserBasicInfo transientInstance) {
		log.debug("saving UserBasicInfo instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserBasicInfo persistentInstance) {
		log.debug("deleting UserBasicInfo instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserBasicInfo findById(java.lang.String id) {
		log.info("getting UserBasicInfo instance with id: " + id);
		try {
			UserBasicInfo instance = (UserBasicInfo) getCurrentSession().get(
					UserBasicInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.info("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserBasicInfo instance) {
		log.debug("finding UserBasicInfo instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria(UserBasicInfo.class)
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<UserBasicInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding UserBasicInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from UserBasicInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<UserBasicInfo> findByHeadImagePath(String headImagePath) {
		return findByProperty(HEAD_IMAGE_PATH, headImagePath);
	}

	public List<UserBasicInfo> findBySign(String sign) {
		return findByProperty(SIGN, sign);
	}

	public List<UserBasicInfo> findByScName(String scName) {
		return findByProperty(SC_NAME, scName);
	}

	public List<UserBasicInfo> findByDname(String dname) {
		return findByProperty(DNAME, dname);
	}

	public List<UserBasicInfo> findByNickname(String nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List<UserBasicInfo> findAll() {
		log.debug("finding all UserBasicInfo instances");
		try {
			String queryString = "from UserBasicInfo";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List<UserBasicInfo> findAll(int first,int max) {
		log.debug("finding all UserBasicInfo instances");
		try {
			String queryString = "from UserBasicInfo";
			Query queryObject = getCurrentSession()
					.createQuery(queryString)
					.setFirstResult(first)
					.setMaxResults(max);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public UserBasicInfo merge(UserBasicInfo detachedInstance) {
		log.debug("merging UserBasicInfo instance");
		try {
			UserBasicInfo result = (UserBasicInfo) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserBasicInfo instance) {
		log.debug("attaching dirty UserBasicInfo instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserBasicInfo instance) {
		log.debug("attaching clean UserBasicInfo instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserBasicInfoDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (UserBasicInfoDAO) ctx.getBean("UserBasicInfoDAO");
	}
}