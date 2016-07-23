package com.atm.dao.impl.user;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.user.UserDAO;
import com.atm.model.user.User;
import com.atm.util.ApplicationUtil;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.atm.model.user.User
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserDAOImpl implements UserDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserDAOImpl.class);
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();// .openSession();//getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#save(com.atm.model.User)
	 */
	@Override
	public void save(User transientInstance) {
		log.debug("saving User instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#delete(com.atm.model.User)
	 */
	@Override
	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#findById(java.lang.String)
	 */
	@Override
	public User findById(java.lang.String id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getCurrentSession().get(
					"com.atm.model.user.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#findByExample(com.atm.model.User)
	 */
	@Override
	public List findByExample(User instance) {
		log.debug("finding User instance by example");
		try {

			List results = getCurrentSession().createCriteria(User.class)
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#findByUserPwd(java.lang.Object)
	 */
	@Override
	public List findByUserPwd(Object userPwd) {
		return findByProperty(USER_PWD, userPwd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#merge(com.atm.model.User)
	 */
	@Override
	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#attachDirty(com.atm.model.User)
	 */
	@Override
	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserDAO#attachClean(com.atm.model.User)
	 */
	@Override
	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext() {
		return (UserDAO) ApplicationUtil.getApplicationContext().getBean(
				"UserDAOImpl");
	}

	@Override
	public void saveOrUpdate(Object transientInstance) {
		log.debug("saving Appeal instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public int updateByUser(User user) {
		log.debug("update user");
		int i = getCurrentSession().createQuery(
				"update User u set u.userPwd='" + user.getUserPwd() + "'"
						+ " where u.userId='" + user.getUserId() + "'")
				.executeUpdate();
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.ATMDAO#finfByHQL
	 */
	@Override
	public List findByHQL(String HQL) {
		log.debug("find by HQL");
		try {
			List list = getCurrentSession().createQuery(HQL).list();
			log.debug("success");
			return list;
		} catch (RuntimeException re) {
			log.error("error");
			throw re;
		}
	}

	@Override
	public List<User> findUserList(int first, int max) {
		log.info("find userList with limit");
		try {
			List<User> list = getCurrentSession().createCriteria(User.class)
					.setFirstResult(first).setMaxResults(max).list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int getMaxRecord() {
		log.info("获取用户的数量");
		try {
			int max = findAll().size();
			return max;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(" get  ------error");
			throw e;
		}
	}

	// 验证登录
	@Override
	public boolean login(User user) {
		try {
			String queryString = "from User as model where model."
					+ "userId= ? and model.userPwd=? ";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, user.getUserId());
			queryObject.setParameter(1, user.getUserPwd());
			User u = (User) queryObject.uniqueResult();
			if (u == null) {
				return false;
			} else {
				return true;
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

}