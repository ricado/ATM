package com.atm.dao.impl.chat;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.chat.CrowdMenberDAO;
import com.atm.model.chat.CrowdMenber;
import com.atm.model.define.chat.MenberList;
import com.atm.util.ApplicationUtil;

/**
 * A data access object (DAO) providing persistence and search support for
 * CrowdMenber entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.chat.CrowdMenber
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CrowdMenberDAOImpl implements CrowdMenberDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CrowdMenberDAOImpl.class);
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.atm.dao.CrowdMenberDAO#setSessionFactory(org.hibernate.SessionFactory
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
	 * @see com.atm.dao.CrowdMenberDAO#save(com.atm.model.CrowdMenber)
	 */
	@Override
	public void save(CrowdMenber transientInstance) {
		log.debug("saving CrowdMenber instance");
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
	 * @see com.atm.dao.CrowdMenberDAO#delete(com.atm.model.CrowdMenber)
	 */
	@Override
	public void delete(CrowdMenber persistentInstance) {
		log.debug("deleting CrowdMenber instance");
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
	 * @see com.atm.dao.CrowdMenberDAO#findById(com.atm.model.CrowdMenberId)
	 */
	@Override
	public CrowdMenber findById(com.atm.model.chat.CrowdMenberId id) {
		log.debug("getting CrowdMenber instance with id: " + id);
		try {
			CrowdMenber instance = (CrowdMenber) getCurrentSession().get(
					"com.atm.model.CrowdMenber", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.CrowdMenberDAO#findByExample(com.atm.model.CrowdMenber)
	 */
	@Override
	public List findByExample(CrowdMenber instance) {
		log.debug("finding CrowdMenber instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.CrowdMenber")
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
	 * @see com.atm.dao.CrowdMenberDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CrowdMenber instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CrowdMenber as model where model."
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
	 * @see com.atm.dao.CrowdMenberDAO#findByIsShutUp(java.lang.Object)
	 */
	@Override
	public List findByIsShutUp(Object isShutUp) {
		return findByProperty(IS_SHUT_UP, isShutUp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.CrowdMenberDAO#findByRoleId(java.lang.Object)
	 */
	@Override
	public List findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.CrowdMenberDAO#findByCrowdID(java.lang.Object)
	 */
	@Override
	public List findByCrowdId(Object crowdId) {
		return findByProperty(CROWD_ID, crowdId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.CrowdMenberDAO#findByUserId(java.lang.Object)
	 */

	@Override
	public List findByUserId(Object userId) {
		log.debug("finding CrowdMenber by userId ");
		try {
			String queryString = "from CrowdMenber as model where model."
					+ "userId= ? order by model.roleId DESC";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, userId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.CrowdMenberDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all CrowdMenber instances");
		try {
			String queryString = "from CrowdMenber";
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
	 * @see com.atm.dao.CrowdMenberDAO#merge(com.atm.model.CrowdMenber)
	 */
	@Override
	public CrowdMenber merge(CrowdMenber detachedInstance) {
		log.debug("merging CrowdMenber instance");
		try {
			CrowdMenber result = (CrowdMenber) getCurrentSession().merge(
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
	 * @see com.atm.dao.CrowdMenberDAO#attachDirty(com.atm.model.CrowdMenber)
	 */
	@Override
	public void attachDirty(CrowdMenber instance) {
		log.debug("attaching dirty CrowdMenber instance");
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
	 * @see com.atm.dao.CrowdMenberDAO#attachClean(com.atm.model.CrowdMenber)
	 */
	@Override
	public void attachClean(CrowdMenber instance) {
		log.debug("attaching clean CrowdMenber instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CrowdMenberDAO getFromApplicationContext() {
		return (CrowdMenberDAO) ApplicationUtil.getApplicationContext()
				.getBean("CrowdMenberDAOImpl");
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.ATMDAO#findAllMenber
	 */
	@Override
	public List<MenberList> findAllMenber(int crowdId){
		log.info("find all menber");
		try{
			List<MenberList> menberLists = getCurrentSession()
					.getNamedQuery("findAllMenber")
					.setInteger(0,crowdId)
					//.setFirstResult(0)
					//.setMaxResults(5)
					.list();
			log.info("find success");
			return menberLists;
		}catch(Exception e){
			log.info("find error");
			throw e;
		}
	}
	
	private int setManager(String userId,int crowdId,int roleId){
		log.info("set user be a manager of a crowdId");
		try{
			String sql = "update CrowdMenber c set c.roleId='" + roleId 
					+ "' where c.userId='" + userId + "' and c.crowdId='" + crowdId + "'";
			int i = getCurrentSession().createQuery(sql).executeUpdate();
			log.info("success");
			return i;
		}catch(Exception e){
			log.info("error");
			throw e;
		}
	}
	
	@Override
	public int addManager(String userId,int crowdId){
		log.info("set user be a manager of a crowdId");
		try{
			String sql = "UPDATE crowdMenber SET roleID=2 "
					+ "where userID='"+userId+"' and crowdID='"+crowdId+"'"
					+ "and (SELECT a.num FROM"
					+ "(SELECT COUNT(*) as num "
					+ "from crowdMenber cm "
					+ "WHERE cm.crowdID='"+crowdId+"' "
					+ "and cm.roleID<3) as a)<5";
			int i = getCurrentSession().createSQLQuery(sql).executeUpdate();
			log.info("success");
			return i;
		}catch(Exception e){
			log.info("error");
			throw e;
		}
	}
	
	@Override
	public int cancelManager(String userId,int crowdId){
		log.info("cancel user be a manager of a crowdId");
		try{
			int i = setManager(userId, crowdId,3);
			log.info("success");
			return i;
		}catch(Exception e){
			log.info("error");
			throw e;
		}
	}
	
	@Override
	public int removeMenber(String userId,int crowdId){
		log.info("cancel user be a manager of a crowdId");
		try{
			String sql = "delete from CrowdMenber c where c.userId='" + userId + "' and c.crowdId='" + crowdId + "'";
			int i= getCurrentSession().createQuery(sql).executeUpdate();
			log.info("success");
			return i;
		}catch(Exception e){
			log.info("error");
			throw e;
		}
	}
}
