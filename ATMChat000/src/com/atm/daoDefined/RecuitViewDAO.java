package com.atm.daoDefined;

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

import com.atm.model.define.RecuitView;

/**
 * A data access object (DAO) providing persistence and search support for
 * RecuitView entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.define.RecuitView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class RecuitViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RecuitViewDAO.class);
	// property constants
	public static final String RE_TYPE_NAME = "reTypeName";
	public static final String WO_TYPE_NAME = "woTypeName";
	public static final String WORK_ADDRESS = "workAddress";
	public static final String SALARY = "salary";
	public static final String TELEPHONE = "telephone";
	public static final String REC_CONTENT = "recContent";
	public static final String PUBLISHER_ID = "publisherId";
	public static final String NICKNAME = "nickname";
	public static final String HEAD_IMAGE_PATH = "headImagePath";

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

	public void save(RecuitView transientInstance) {
		log.debug("saving RecuitView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RecuitView persistentInstance) {
		log.debug("deleting RecuitView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RecuitView findById(java.lang.Integer id) {
		log.debug("getting RecuitView instance with id: " + id);
		try {
			RecuitView instance = (RecuitView) getCurrentSession().get(
					"com.atm.model.define.RecuitView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RecuitView instance) {
		log.debug("finding RecuitView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.RecuitView")
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
		log.debug("finding RecuitView instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RecuitView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReTypeName(Object reTypeName) {
		return findByProperty(RE_TYPE_NAME, reTypeName);
	}

	public List findByWoTypeName(Object woTypeName) {
		return findByProperty(WO_TYPE_NAME, woTypeName);
	}

	public List findByWorkAddress(Object workAddress) {
		return findByProperty(WORK_ADDRESS, workAddress);
	}

	public List findBySalary(Object salary) {
		return findByProperty(SALARY, salary);
	}

	public List findByTelephone(Object telephone) {
		return findByProperty(TELEPHONE, telephone);
	}

	public List findByRecContent(Object recContent) {
		return findByProperty(REC_CONTENT, recContent);
	}

	public List findByPublisherId(Object publisherId) {
		return findByProperty(PUBLISHER_ID, publisherId);
	}

	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List findByHeadImagePath(Object headImagePath) {
		return findByProperty(HEAD_IMAGE_PATH, headImagePath);
	}

	public List findAll() {
		log.debug("finding all RecuitView instances");
		try {
			String queryString = "from RecuitView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RecuitView merge(RecuitView detachedInstance) {
		log.debug("merging RecuitView instance");
		try {
			RecuitView result = (RecuitView) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RecuitView instance) {
		log.debug("attaching dirty RecuitView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RecuitView instance) {
		log.debug("attaching clean RecuitView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**
	 * ²éÕÒÁÐ±í
	 * @param first
	 * @param max
	 * @return
	 */
	public List findListByHQL(String HQL,int first,int max){
		log.debug("find list");
		try{
			List list = getCurrentSession().createCriteria(HQL)
					.setFirstResult(first)
					.setMaxResults(max)
					.list();
			log.debug("success");
			return list;		
		}catch(RuntimeException e){
			log.debug("find fail");
			throw e;
		}
	}
	
	public List findListByHQL1(String HQL,int first,int max){
		log.debug("find list");
		try{
			List list = getCurrentSession().createQuery(HQL)
					.setFirstResult(first)
					.setMaxResults(max)
					.list();
			log.debug("success");
			return list;		
		}catch(RuntimeException e){
			log.debug("find fail");
			throw e;
		}
	}
	
	public List findList(int first,int max){
		String HQL = "select r.reInfoId,r.reTypeName,r.woTypeName,"
				+ "r.workAddress,r.publishTime,r.salary"
				+ " from RecuitView r";
		return findListByHQL1(HQL,first, max);
	}
	
	public List findList(String str,int first,int max){
		String HQL = "select r.reInfoId,r.reTypeName,r.woTypeName,"
				+ "r.workAddress,r.publishTime,r.salary"
				+ " from RecuitView r ";
		if(str.length()>0){
			HQL += "where reTypeNmae like '%" + str + "%' " + 
					"or workTypeName like '%" + str + "%' " +
					"or workAddress like '%" + str + "%'";
		}
		return findListByHQL1(HQL, first, max);
	}

	public static RecuitViewDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RecuitViewDAO) ctx.getBean("RecuitViewDAO");
	}
}