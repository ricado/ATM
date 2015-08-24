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

import com.atm.model.define.bbs.PeopleRelationView;

/**
 * A data access object (DAO) providing persistence and search support for
 * PeopleRelationView entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.PeopleRelationView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PeopleRelationViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PeopleRelationViewDAO.class);
	// property constants
	public static final String ATTEND_NAME = "attendName";
	public static final String ATTENDED_NAME = "attendedName";
	public static final String USER_ATTENDED_ID = "userAttendedId";
	public static final String USER_ATTEND_ID = "userAttendId";

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

	public void save(PeopleRelationView transientInstance) {
		log.debug("saving PeopleRelationView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PeopleRelationView persistentInstance) {
		log.debug("deleting PeopleRelationView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PeopleRelationView findById(java.lang.String id) {
		log.debug("getting PeopleRelationView instance with id: " + id);
		try {
			PeopleRelationView instance = (PeopleRelationView) getCurrentSession()
					.get("com.atm.model.define.PeopleRelationView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PeopleRelationView instance) {
		log.debug("finding PeopleRelationView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.PeopleRelationView")
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
		log.debug("finding PeopleRelationView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PeopleRelationView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	//TODO 获取关注者集合
	public List findAttendedPeople(Object attendId){
		try {
			String queryString = "select new PeopleRelationView(userAttendedId,attendedName)"
					+"from PeopleRelationView as model where model.userAttendId"
					 + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, attendId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List findByAttendName(Object attendName) {
		return findByProperty(ATTEND_NAME, attendName);
	}

	public List findByAttendedName(Object attendedName) {
		return findByProperty(ATTENDED_NAME, attendedName);
	}

	public List findByUserAttendedId(Object userAttendedId) {
		return findByProperty(USER_ATTENDED_ID, userAttendedId);
	}

	public List findByUserAttendId(Object userAttendId) {
		return findByProperty(USER_ATTEND_ID, userAttendId);
	}

	public List findAll() {
		log.debug("finding all PeopleRelationView instances");
		try {
			String queryString = "from PeopleRelationView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PeopleRelationView merge(PeopleRelationView detachedInstance) {
		log.debug("merging PeopleRelationView instance");
		try {
			PeopleRelationView result = (PeopleRelationView) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PeopleRelationView instance) {
		log.debug("attaching dirty PeopleRelationView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PeopleRelationView instance) {
		log.debug("attaching clean PeopleRelationView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PeopleRelationViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PeopleRelationViewDAO) ctx.getBean("PeopleRelationViewDAO");
	}
}