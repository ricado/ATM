package com.atm.daoDefined.bbs;

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

import com.atm.model.define.bbs.LabelRelationView;

/**
 * A data access object (DAO) providing persistence and search support for
 * LabelRelationView entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.LabelRelationView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class LabelRelationViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(LabelRelationViewDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String LAB_ID = "labId";
	public static final String LAB_NAME = "labName";

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

	public void save(LabelRelationView transientInstance) {
		log.debug("saving LabelRelationView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LabelRelationView persistentInstance) {
		log.debug("deleting LabelRelationView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LabelRelationView findById(java.lang.String id) {
		log.debug("getting LabelRelationView instance with id: " + id);
		try {
			LabelRelationView instance = (LabelRelationView) getCurrentSession()
					.get("com.atm.model.define.LabelRelationView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(LabelRelationView instance) {
		log.debug("finding LabelRelationView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.LabelRelationView")
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
		log.debug("finding LabelRelationView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from LabelRelationView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//TODO 获取关注标签集合
	public List findAttendedLabel(Object userId){
		try {
			String queryString = "select new LabelRelationView(labId,labName) "
					+"from LabelRelationView as model where model.userId"
					 + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, userId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//TODO 获取关注标签集合(只要名字)
		public List findAttendedLabelName(Object userId){
			try {
				String queryString = "select labName "
						+"from LabelRelationView as model where model.userId"
						 + "= ?";
				Query queryObject = getCurrentSession().createQuery(queryString);
				queryObject.setParameter(0, userId);
				return queryObject.list();
			} catch (RuntimeException re) {
				log.error("find by property name failed", re);
				throw re;
			}
		}
	
	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByLabId(Object labId) {
		return findByProperty(LAB_ID, labId);
	}

	public List findByLabName(Object labName) {
		return findByProperty(LAB_NAME, labName);
	}

	public List findAll() {
		log.debug("finding all LabelRelationView instances");
		try {
			String queryString = "from LabelRelationView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LabelRelationView merge(LabelRelationView detachedInstance) {
		log.debug("merging LabelRelationView instance");
		try {
			LabelRelationView result = (LabelRelationView) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LabelRelationView instance) {
		log.debug("attaching dirty LabelRelationView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LabelRelationView instance) {
		log.debug("attaching clean LabelRelationView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LabelRelationViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (LabelRelationViewDAO) ctx.getBean("LabelRelationViewDAO");
	}
}