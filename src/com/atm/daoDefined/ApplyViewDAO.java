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

import com.atm.model.define.ApplyView;

/**
 * A data access object (DAO) providing persistence and search support for
 * ApplyView entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.define.ApplyView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ApplyViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ApplyViewDAO.class);
	// property constants
	public static final String PUBLISHER_ID = "publisherId";
	public static final String EXPECT_SALARY = "expectSalary";
	public static final String TELEPHONE = "telephone";
	public static final String PERSONAL_INFO = "personalInfo";
	public static final String RE_TYPE_NAME = "reTypeName";
	public static final String WO_TYPE_NAME = "woTypeName";
	public static final String NAME = "name";
	public static final String NICKNAME = "nickname";
	public static final String USER_ID = "userId";

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

	public void save(ApplyView transientInstance) {
		log.debug("saving ApplyView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ApplyView persistentInstance) {
		log.debug("deleting ApplyView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ApplyView findById(java.lang.Integer id) {
		log.debug("getting ApplyView instance with id: " + id);
		try {
			ApplyView instance = (ApplyView) getCurrentSession().get(
					"com.atm.model.define.ApplyView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ApplyView instance) {
		log.debug("finding ApplyView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.ApplyView")
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
		log.debug("finding ApplyView instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ApplyView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPublisherId(Object publisherId) {
		return findByProperty(PUBLISHER_ID, publisherId);
	}

	public List findByExpectSalary(Object expectSalary) {
		return findByProperty(EXPECT_SALARY, expectSalary);
	}

	public List findByTelephone(Object telephone) {
		return findByProperty(TELEPHONE, telephone);
	}

	public List findByPersonalInfo(Object personalInfo) {
		return findByProperty(PERSONAL_INFO, personalInfo);
	}

	public List findByReTypeName(Object reTypeName) {
		return findByProperty(RE_TYPE_NAME, reTypeName);
	}

	public List findByWoTypeName(Object woTypeName) {
		return findByProperty(WO_TYPE_NAME, woTypeName);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findAll() {
		log.debug("finding all ApplyView instances");
		try {
			String queryString = "from ApplyView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ApplyView merge(ApplyView detachedInstance) {
		log.debug("merging ApplyView instance");
		try {
			ApplyView result = (ApplyView) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ApplyView instance) {
		log.debug("attaching dirty ApplyView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ApplyView instance) {
		log.debug("attaching clean ApplyView instance");
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
			List list = getCurrentSession().createQuery(HQL)
					.setFirstResult(first)
					.setMaxResults(max)
					.list();
			log.debug("success");
			return list;		
		}catch(RuntimeException e){
			log.debug("find fail");
			log.debug(e.getMessage());
			throw e;
		}
	}
	
	public List findList(int first,int max){
		String HQL = "select new ApplyView(apInfoId,expectSalary,"
				+"publishTime,reTypeName,woTypeName)"
				+ " from ApplyView a ";
		return findListByHQL(HQL,first, max);
	}
	
	public List findList(Object str,int first,int max){
		String HQL = "select new ApplyView(apInfoId,expectSalary,"
				+"publishTime,reTypeName,woTypeName)"
				+ " from ApplyView a ";
		if(str.toString().length()>0){
			HQL += "where reTypeName like '%" + str + "%' " + 
					"or woTypeName like '%" + str + "%'";
		}
		return findListByHQL(HQL, first, max);
	}

	
	public static ApplyViewDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ApplyViewDAO) ctx.getBean("ApplyViewDAO");
	}
}