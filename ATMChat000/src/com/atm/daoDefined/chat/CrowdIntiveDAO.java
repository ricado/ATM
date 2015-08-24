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

import com.atm.model.define.chat.CrowdIntive;

/**
 * A data access object (DAO) providing persistence and search support for
 * CrowdIntive entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.chat.CrowdIntive
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CrowdIntiveDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdIntiveDAO.class);
	// property constants

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

	public void save(CrowdIntive transientInstance) {
		log.debug("saving CrowdIntive instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CrowdIntive persistentInstance) {
		log.debug("deleting CrowdIntive instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public List findByExample(CrowdIntive instance) {
		log.debug("finding CrowdIntive instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.CrowdIntive")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<CrowdIntive> findByProperty(String propertyName, Object value) {
		log.debug("finding CrowdIntive instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CrowdIntive as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<CrowdIntive> findByIntiveId(String intiveId){
		return findByProperty("intiveId", intiveId);
	}
	
	public List<CrowdIntive> findByIntivedId(String intivedId){
		return findByProperty("intivedId", intivedId);
	}
	
	public List<CrowdIntive> findByCrowdId(int crowdId){
		return findByProperty("crowdId", crowdId);
	}
	
	public List findAll() {
		log.debug("finding all CrowdIntive instances");
		try {
			String queryString = "from CrowdIntive";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CrowdIntive merge(CrowdIntive detachedInstance) {
		log.debug("merging CrowdIntive instance");
		try {
			CrowdIntive result = (CrowdIntive) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CrowdIntive instance) {
		log.debug("attaching dirty CrowdIntive instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CrowdIntive instance) {
		log.debug("attaching clean CrowdIntive instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public int deleteIntive(String intivedId,int crowdId){
		log.info("delete by intiveId and crowdId");
		try{
			String hQL = "delete CrowdIntive c where c.intivedId='" + intivedId + "'" +
						"c.crowd='" + crowdId + "'";
			int i = getCurrentSession().createQuery(hQL).executeUpdate();
			log.info("delete success");
			return i;
		}catch(Exception e){
			log.info("delete error");
			e.printStackTrace();
			return 0;
		}
	}
	
	public static CrowdIntiveDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CrowdIntiveDAO) ctx.getBean("CrowdIntiveDAO");
	}
}