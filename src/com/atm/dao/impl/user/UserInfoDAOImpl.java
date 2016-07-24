package com.atm.dao.impl.user;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.dao.impl.bbs.ClickGoodDAOImpl;
import com.atm.dao.user.UserInfoDAO;
import com.atm.model.define.user.UserList;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.util.ApplicationUtil;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.user.UserInfo
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserInfoDAOImpl implements UserInfoDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserInfoDAOImpl.class);
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.atm.dao.UserInfoDAO#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.atm.dao.UserInfoDAO#save(com.atm.model.UserInfo)
	 */
	@Override
	public void save(UserInfo transientInstance) {
		log.debug("saving UserInfo instance");
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
	 * @see com.atm.dao.UserInfoDAO#delete(com.atm.model.UserInfo)
	 */
	@Override
	public void delete(UserInfo persistentInstance) {
		log.debug("deleting UserInfo instance");
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
	 * @see com.atm.dao.UserInfoDAO#findById(java.lang.String)
	 */
	@Override
	public UserInfo findById(java.lang.String id) {
		log.debug("getting UserInfo instance with id: " + id);
		try {
			UserInfo instance = (UserInfo) getCurrentSession().get(
					UserInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findByExample(com.atm.model.UserInfo)
	 */
	@Override
	public List findByExample(UserInfo instance) {
		log.debug("finding UserInfo instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria(UserInfo.class)
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
	 * @see com.atm.dao.UserInfoDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserInfo as model where model."
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
	 * @see com.atm.dao.UserInfoDAO#findByScNo(java.lang.Object)
	 */
	@Override
	public List findByScNo(Object scNo) {
		return findByProperty(SC_NO, scNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findByDno(java.lang.Object)
	 */
	@Override
	public List findByDno(Object dno) {
		return findByProperty(DNO, dno);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findByHeadImagePath(java.lang.Object)
	 */
	@Override
	public List findByHeadImagePath(Object headImagePath) {
		return findByProperty(HEAD_IMAGE_PATH, headImagePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findBySign(java.lang.Object)
	 */
	@Override
	public List findBySign(Object sign) {
		return findByProperty(SIGN, sign);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findByName(java.lang.Object)
	 */
	@Override
	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findByNickname(java.lang.Object)
	 */
	@Override
	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findBySex(java.lang.Object)
	 */
	@Override
	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findByJobTitle(java.lang.Object)
	 */
	@Override
	public List findByJobTitle(Object jobTitle) {
		return findByProperty(JOB_TITLE, jobTitle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findByFlag(java.lang.Object)
	 */
	@Override
	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#findAll()
	 */
	@Override
	public List<UserInfo> findAll() {
		log.debug("finding all UserInfo instances");
		try {
			String queryString = "from UserInfo";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public List<UserInfo> findAll(int first,int max) {
		log.debug("finding all UserInfo instances");
		try {
			String queryString = "from UserInfo";
			Query queryObject = getCurrentSession()
					.createQuery(queryString)
					.setFirstResult(first)
					.setMaxResults(max);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atm.dao.UserInfoDAO#merge(com.atm.model.UserInfo)
	 */
	@Override
	public UserInfo merge(UserInfo detachedInstance) {
		log.debug("merging UserInfo instance");
		try {
			UserInfo result = (UserInfo) getCurrentSession().merge(
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
	 * @see com.atm.dao.UserInfoDAO#attachDirty(com.atm.model.UserInfo)
	 */
	@Override
	public void attachDirty(UserInfo instance) {
		log.debug("attaching dirty UserInfo instance");
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
	 * @see com.atm.dao.UserInfoDAO#attachClean(com.atm.model.UserInfo)
	 */
	@Override
	public void attachClean(UserInfo instance) {
		log.debug("attaching clean UserInfo instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserInfoDAO getFromApplicationContext() {
		return (UserInfoDAO) ApplicationUtil.getApplicationContext().getBean(
				"UserInfoDAOImpl");
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
	public int updateOffTime(String userId) {
		log.debug("update time ");
		try {
			String queryString = "update UserInfo as model set offTime=now() where model."
					+ "userId=?";
			int i = getCurrentSession().createQuery(queryString)
					.setString(0, userId).executeUpdate();
			return i;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 通过keyword查找相关的用户
	 * 
	 * @param keyWord
	 * @param first
	 * @param max
	 * @return
	 */
	@Override
	public List<UserList> findUser(String keyWord, int first, int max) {
		log.info("find by keyWord");
		try {
			List<UserList> userLists = getCurrentSession()
					.getNamedQuery("findUser").setString(0, keyWord)
					.setInteger(1, first).setInteger(2, max).list();
			log.info("find success");
			return userLists;
		} catch (Exception e) {
			log.info("find error");
			throw e;
		}
	}
	
	/**
	 * 查找可能感性趣的用户
	 * @param userId
	 * @param first
	 * @param max
	 * @return
	 */
	@Override
	public List<UserList> findinterestingUser(String userId, int first, int max) {
		log.info("find interesting user");
		try {
			List<UserList> userLists = getCurrentSession()
					.getNamedQuery("interestingUser").setString(0, userId)
					.setInteger(1, first).setInteger(2, max).list();
			log.info("find success");
			return userLists;
		} catch (Exception e) {
			log.info("find error");
			throw e;
		}
	}
	
	 //搜索用户，结果按相似度排列
	@Override
    public List searchPeople(Object userName){
    	 try {
    	       String queryString = "from UserInfo as model where model." +
    	       						"nickname like ? or model." +
    	       						"userId like ?"+
    	       						"order by "+
    	       						"case when userId like ? "+
    	       						"then (length(userId)-length('"+userName+"'))"+
    	       						"else (length(nickname)-length('"+userName+"')) end";
    	       Query queryObject = getCurrentSession().createQuery(queryString);
    	       queryObject.setParameter(0, "%"+userName+"%");
    	       queryObject.setParameter(1, "%"+userName+"%");
    	       queryObject.setParameter(2, "%"+userName+"%");
    		   return queryObject.list();
    	   } catch (RuntimeException re) {
    	        log.error("find by property name failed", re);
    	        throw re;
    	   }
    }
	@Override
	public String getUserHeadPath(String userId){
		log.info("用户头像路径");
		try{
			String sql = "select u.headImagePath from UserInfo u where u.userId='" +userId + "'";
			//List<String> path = getCurrentSession().createCriteria(UserInfo.class);
			List<String> list = getCurrentSession().createQuery(sql)
					.list();
			return list.get(0)+"";
		}catch(Exception e){
			log.info("error");
			throw e;
		}
	}
	
	@Override
	public List getSdmNO(String sName,String dName,String mName){
		log.info("获取学校系别专业相关编号");
		try{
			List list = getCurrentSession().createSQLQuery("{call sdmNO(?,?,?)}")
					.setString(0, sName)
					.setString(1, dName)
					.setString(2, mName)
					.list();
			return list;
		}catch(Exception e){
			log.info("获取失败");
			throw e;
		}
	}
	/**
	 * 删除某一个用户
	 * @param userId
	 * @return
	 */
	@Override
	public int deleteByUserId(String userId){
		log.info("删除用户");
		try{
			String sql = "delete from UserInfo u where u.userId='" + userId + "'";
			return getCurrentSession().createQuery(sql).executeUpdate();
		}catch(Exception e){
			log.info("删除失败");
			throw e;
		}
	}
	
	@Override
	public int saveNumAndName(String userId,String number,String name){
		log.info("插入学号以及真实姓名");
		try {
			int i=getCurrentSession().createSQLQuery("{call nameAndNum(?,?,?)}")
				.setString(0, userId)
				.setString(1, number)
				.setString(2, name)
				.executeUpdate();
			log.info("i:" + i);
			return i;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
}