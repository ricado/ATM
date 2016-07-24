package com.atm.dao.impl.chat;

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

import com.atm.dao.chat.PrivateChatDAO;
import com.atm.model.Appeal;
import com.atm.model.chat.PrivateChat;
import com.atm.util.ApplicationUtil;

/**
 * A data access object (DAO) providing persistence and search support for
 * PrivateChat entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.chat.PrivateChat
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PrivateChatDAOImpl implements PrivateChatDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PrivateChatDAOImpl.class);
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.atm.dao.PrivateChatDAO#setSessionFactory(org.hibernate.SessionFactory
	 * )
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.PrivateChatDAO#save(com.atm.model.PrivateChat)
	 */
	@Override
	public void save(PrivateChat transientInstance) {
		log.debug("saving PrivateChat instance");
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
	 * @see com.atm.dao.PrivateChatDAO#delete(com.atm.model.PrivateChat)
	 */
	@Override
	public void delete(PrivateChat persistentInstance) {
		log.debug("deleting PrivateChat instance");
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
	 * @see com.atm.dao.PrivateChatDAO#findById(java.lang.Integer)
	 */
	@Override
	public PrivateChat findById(java.lang.Integer id) {
		log.debug("getting PrivateChat instance with id: " + id);
		try {
			PrivateChat instance = (PrivateChat) getCurrentSession().get(
					"com.atm.model.PrivateChat", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.PrivateChatDAO#findByExample(com.atm.model.PrivateChat)
	 */
	@Override
	public List<PrivateChat> findByExample(PrivateChat instance) {
		log.debug("finding PrivateChat instance by example");
		try {
			List<PrivateChat> results = getCurrentSession()
					.createCriteria("com.atm.model.PrivateChat")
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
	 * @see com.atm.dao.PrivateChatDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public List<PrivateChat> findByProperty(String propertyName, Object value) {
		log.debug("finding PrivateChat instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PrivateChat as model where model."
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
	 * @see com.atm.dao.PrivateChatDAO#findByUserReceiveId(java.lang.Object)
	 */
	@Override
	public List<PrivateChat> findByUserReceiveId(Object userReceiveId) {
		log.debug("finding PrivateChat by userReceiveId");
		try {
			String queryString = "from PrivateChat as model where model.userReceiveId"
				 + "='"+ userReceiveId +"' and model.flag=1 order by model.userSendId";
			List<PrivateChat> list = getCurrentSession().createQuery(queryString).list();
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.PrivateChatDAO#findByUserSendId(java.lang.Object)
	 */
	@Override
	public List<PrivateChat> findByUserSendId(Object userSendId) {
		return findByProperty(USER_SEND_ID, userSendId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.PrivateChatDAO#findBySendContent(java.lang.Object)
	 */
	@Override
	public List<PrivateChat> findBySendContent(Object sendContent) {
		return findByProperty(SEND_CONTENT, sendContent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.PrivateChatDAO#findByFlag(java.lang.Object)
	 */
	@Override
	public List<PrivateChat> findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.PrivateChatDAO#findAll()
	 */
	@Override
	public List<PrivateChat> findAll() {
		log.debug("finding all PrivateChat instances");
		try {
			String queryString = "from PrivateChat";
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
	 * @see com.atm.dao.PrivateChatDAO#merge(com.atm.model.PrivateChat)
	 */
	@Override
	public PrivateChat merge(PrivateChat detachedInstance) {
		log.debug("merging PrivateChat instance");
		try {
			PrivateChat result = (PrivateChat) getCurrentSession().merge(
					detachedInstance);
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
	 * @see com.atm.dao.PrivateChatDAO#attachDirty(com.atm.model.PrivateChat)
	 */
	@Override
	public void attachDirty(PrivateChat instance) {
		log.debug("attaching dirty PrivateChat instance");
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
	 * @see com.atm.dao.PrivateChatDAO#attachClean(com.atm.model.PrivateChat)
	 */
	@Override
	public void attachClean(PrivateChat instance) {
		log.debug("attaching clean PrivateChat instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PrivateChatDAO getFromApplicationContext() {
		return (PrivateChatDAO) ApplicationUtil.getApplicationContext()
				.getBean("PrivateChatDAOImpl");
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

	@Override
	public List<PrivateChat> findByReceiveAndSend(String receiveId,
			String sendId) {
		log.debug("find recevieId and sendId");
		try {
			/*
			 * List list = getCurrentSession().createQuery(
			 * "from PrivateChat chat where userReceiveId=" + receiveId +
			 * "and userSendId=" + sendId) .list();
			 */

			List<PrivateChat> list = getCurrentSession().createQuery(
					"from PrivateChat chat where (userReceiveId=" + receiveId
							+ "and userSendId=" + sendId
							+ ") or (userReceiveId=" + sendId
							+ "and userSendId=" + receiveId + ")").list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	public void deleteByUserId(String userId){
		log.info("delete by userId :" + userId);
		try {
			String sql = "delete PrivateChat as model where model.userReceiveId ='" + userId +"'";
			int i = getCurrentSession().createQuery(sql).executeUpdate();
			log.info("success");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("error");
		}
	}
}