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

import com.atm.dao.bbs.ReplyDAO;
import com.atm.model.bbs.Reply;


/**
 * A data access object (DAO) providing persistence and search support for Reply
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.atm.model.Reply
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ReplyDAOImpl implements ReplyDAO {
	private static final Logger log = LoggerFactory.getLogger(ReplyDAOImpl.class);
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.impl.ReplyDAO#save(com.atm.model.Reply)
	 */
	@Override
	public Serializable save(Reply transientInstance) {
		log.debug("saving Reply instance");
		try {
			log.debug("save successful");
			return getCurrentSession().save(transientInstance);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#delete(com.atm.model.Reply)
	 */
	@Override
	public void delete(Reply persistentInstance) {
		log.debug("deleting Reply instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#findById(java.lang.Integer)
	 */
	@Override
	public Reply findById(java.lang.Integer id) {
		log.debug("getting Reply instance with id: " + id);
		try {
			Reply instance = (Reply) getCurrentSession().get(
					"com.atm.model.bbs.Reply", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#findByExample(com.atm.model.Reply)
	 */
	@Override
	public List findByExample(Reply instance) {
		log.debug("finding Reply instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.bbs.Reply")
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
	 * @see com.atm.dao.impl.ReplyDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Reply instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Reply as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//TODO ɾ�����¥��
	public void deleteAFloor(int floorId,int essayId){
		try {
			String queryString = "delete from Reply as model where "+
								" model.floorId = ? and"+
								" model.essayId = ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, floorId);
			queryObject.setParameter(1, essayId);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#findByRecommentId(java.lang.Object)
	 */
	@Override
	public List findByRecommentId(Object recommentId) {
		return findByProperty(RECOMMENT_ID, recommentId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#findByRepContent(java.lang.Object)
	 */
	@Override
	public List findByRepContent(Object repContent) {
		return findByProperty(REP_CONTENT, repContent);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#findByUserId(java.lang.Object)
	 */
	@Override
	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#findByFloorId(java.lang.Object)
	 */
	@Override
	public List findByFloorId(Object floorId) {
		return findByProperty(FLOOR_ID, floorId);
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Reply instances");
		try {
			String queryString = "from Reply";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#merge(com.atm.model.Reply)
	 */
	@Override
	public Reply merge(Reply detachedInstance) {
		log.debug("merging Reply instance");
		try {
			Reply result = (Reply) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#attachDirty(com.atm.model.Reply)
	 */
	@Override
	public void attachDirty(Reply instance) {
		log.debug("attaching dirty Reply instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.impl.ReplyDAO#attachClean(com.atm.model.Reply)
	 */
	@Override
	public void attachClean(Reply instance) {
		log.debug("attaching clean Reply instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReplyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ReplyDAO) ctx.getBean("ReplyDAOImpl");
	}
}