	package com.atm.dao.impl;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.PeopleAttentionAssociationDAO;
import com.atm.model.PeopleAttentionAssociation;
import com.atm.model.define.user.UserList;
import com.atm.util.ApplicationUtil;

/**
 * A data access object (DAO) providing persistence and search support for
 * PeopleAttentionAssociation entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.atm.model.PeopleAttentionAssociation
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PeopleAttentionAssociationDAOImpl implements PeopleAttentionAssociationDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PeopleAttentionAssociationDAOImpl.class);
	// property constants

	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#save(com.atm.model.PeopleAttentionAssociation)
	 */
	@Override
	public void save(PeopleAttentionAssociation transientInstance) {
		log.debug("saving PeopleAttentionAssociation instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#delete(com.atm.model.PeopleAttentionAssociation)
	 */
	@Override
	public void delete(PeopleAttentionAssociation persistentInstance) {
		log.debug("deleting PeopleAttentionAssociation instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#findById(com.atm.model.PeopleAttentionAssociationId)
	 */
	@Override
	public PeopleAttentionAssociation findById(
			com.atm.model.PeopleAttentionAssociationId id) {
		log.debug("getting PeopleAttentionAssociation instance with id: " + id);
		try {
			PeopleAttentionAssociation instance = (PeopleAttentionAssociation) getCurrentSession()
					.get("com.atm.model.PeopleAttentionAssociation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#findByExample(com.atm.model.PeopleAttentionAssociation)
	 */
	@Override
	public List findByExample(PeopleAttentionAssociation instance) {
		log.debug("finding PeopleAttentionAssociation instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.PeopleAttentionAssociation")
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
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding PeopleAttentionAssociation instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PeopleAttentionAssociation as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all PeopleAttentionAssociation instances");
		try {
			String queryString = "from PeopleAttentionAssociation";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#merge(com.atm.model.PeopleAttentionAssociation)
	 */
	@Override
	public PeopleAttentionAssociation merge(
			PeopleAttentionAssociation detachedInstance) {
		log.debug("merging PeopleAttentionAssociation instance");
		try {
			PeopleAttentionAssociation result = (PeopleAttentionAssociation) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#attachDirty(com.atm.model.PeopleAttentionAssociation)
	 */
	@Override
	public void attachDirty(PeopleAttentionAssociation instance) {
		log.debug("attaching dirty PeopleAttentionAssociation instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.atm.dao.PeopleAttentionAssociationDAO#attachClean(com.atm.model.PeopleAttentionAssociation)
	 */
	@Override
	public void attachClean(PeopleAttentionAssociation instance) {
		log.debug("attaching clean PeopleAttentionAssociation instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PeopleAttentionAssociationDAO getFromApplicationContext(
			) {
		return (PeopleAttentionAssociationDAO) ApplicationUtil.getApplicationContext()
				.getBean("PeopleAttentionAssociationDAOImpl");
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
	/* (non-Javadoc)
	 * @see com.atm.dao.ATMDAO#finfByHQL
	 */
	@Override
	public List findByHQL(String HQL){
		log.debug("find by HQL");
		try{
			List list = getCurrentSession().createQuery(HQL).list();
			log.debug("success");
			return list;
		}catch(RuntimeException re){
			log.error("error");
			throw re;
		}
	}
	
	/**
	 * type为0表示查看我关注的
	 * 1表示查看我的粉丝
	 * @param userId
	 * @param type
	 * @return
	 */
	private List<UserList> getAttent(String userId,int type){
		log.info("find attend");
		try{
			List<UserList> userLists  = getCurrentSession().getNamedQuery("getAttent")
					.setString(0, userId)
					.setInteger(1, type)
					.list();
			log.info("find attend success");
			return userLists;
		}catch(Exception e){
			e.printStackTrace();
			log.info("error");
			throw e;
		}
	}
	
	@Override
	public List<UserList> findByUserAttentId(String userAttentId){
		log.info("find by userAttentId");
		return getAttent(userAttentId, 0);
	}
	
	@Override
	public List<UserList> findByUserAttentedId(String userAttentedId){
		log.info("find by userAttentedId");
		return getAttent(userAttentedId, 1);
	}
	@Override
	public List findByAttendUserId(String userAttendId){
    	return  findByProperty("userAttendId",userAttendId);
    }
	@Override
	public List findByAttendedUserId(String userAttendedId){
    	return  findByProperty("userAttendedId",userAttendedId);
    }
	
}