package com.atm.daoDefined.chat;

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

import com.atm.model.define.chat.CrowdList;

/**
 * A data access object (DAO) providing persistence and search support for
 * CrowdListView entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.chat.CrowdList
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CrowdListViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdListViewDAO.class);
	// property constants
	public static final String CROWD_HEAD_IMAGE = "crowdHeadImage";
	public static final String CROWD_NAME = "crowdName";
	public static final String NUM_LIMIT = "numLimit";
	public static final String CROWD_NUM = "crowdNum";

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

	public void save(CrowdList transientInstance) {
		log.debug("saving CrowdListView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CrowdList persistentInstance) {
		log.debug("deleting CrowdListView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CrowdList findById(java.lang.Integer id) {
		log.debug("getting CrowdListView instance with id: " + id);
		try {
			CrowdList instance = (CrowdList) getCurrentSession().get(
					"com.atm.model.define.CrowdListView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CrowdList instance) {
		log.debug("finding CrowdListView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.CrowdListView")
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
		log.debug("finding CrowdListView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CrowdListView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCrowdHeadImage(Object crowdHeadImage) {
		return findByProperty(CROWD_HEAD_IMAGE, crowdHeadImage);
	}

	public List findByCrowdName(Object crowdName) {
		return findByProperty(CROWD_NAME, crowdName);
	}

	public List findByNumLimit(Object numLimit) {
		return findByProperty(NUM_LIMIT, numLimit);
	}

	public List findByCrowdNum(Object crowdNum) {
		return findByProperty(CROWD_NUM, crowdNum);
	}

	public List findAll() {
		log.debug("finding all CrowdListView instances");
		try {
			String queryString = "from CrowdListView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CrowdList merge(CrowdList detachedInstance) {
		log.debug("merging CrowdListView instance");
		try {
			CrowdList result = (CrowdList) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CrowdList instance) {
		log.debug("attaching dirty CrowdListView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CrowdList instance) {
		log.debug("attaching clean CrowdListView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CrowdListViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CrowdListViewDAO) ctx.getBean("CrowdListViewDAO");
	}
	
	/*public static List<CrowdListView> findByKeyWord(String key){
		log.info("find by key word");
		try{
			String HQL = "";
			if(key!=null&&key.equals("")){
				
			}else{
				
			}
		}catch(Exception e){
			
		}
	}*/
}