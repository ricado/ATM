package com.atm.daoDefined.bbs;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.atm.model.define.bbs.ReplyView;


/**
 * A data access object (DAO) providing persistence and search support for
 * ReplyView entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.atm.model.define.ReplyView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ReplyViewDAO {
	Logger log = Logger.getLogger(getClass());
	// property constants
	public static final String ESSAY_ID = "essayId";
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String REPLIED_USER_ID = "repliedUserId";
	public static final String REPLIED_NAME = "repliedName";
	public static final String REP_CONTENT = "repContent";
	public static final String FLOOR_ID = "floorId";
	public static final String CLICK_GOOD_NUM = "clickGoodNum";

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

	public void save(ReplyView transientInstance) {
		log.debug("saving ReplyView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ReplyView persistentInstance) {
		log.debug("deleting ReplyView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReplyView findById(java.lang.Integer id) {
		log.debug("getting ReplyView instance with id: " + id);
		try {
			ReplyView instance = (ReplyView) getCurrentSession().get(
					"com.atm.model.define.ReplyView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ReplyView instance) {
		log.debug("finding ReplyView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.ReplyView")
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
		return findByProperty(propertyName,value,0);
	}
	public List findByProperty(String propertyName, Object value,int first) {
		int maxNum = 10;
		try {
			String queryString = "from ReplyView as model where model."
					+ propertyName + "= ? group by floorId order by floorId";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setFirstResult(first);
			queryObject.setMaxResults(maxNum);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	//查找热门楼
	public List findHotReply(int essayId) {
		try {
			String queryString = "from ReplyView as model where model."+
					"essayId= ? group by floorId order by clickGoodNum desc";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, essayId);
			//queryObject.setFirstResult(0);
			queryObject.setMaxResults(1);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	//TODO 查找楼中楼
	public List findInnerReply(int essayId,int floorId,int index) {
		log.debug(">>>>>>>>>>>>>>>>>"+essayId+":"+floorId+":"+index);
		int max = 5;
		if(index==1){
			max = 2;
		}
		try {
			String queryString = "from ReplyView as model where model."+
					"essayId= ? and model.floorId=? order by model.repTime";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, essayId);
			queryObject.setParameter(1, floorId);
			queryObject.setFirstResult(index);
			queryObject.setMaxResults(max);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	//TODO 判断楼层是否存在
	public boolean haveFloor(int floorId){
		try {
			String queryString = "from ReplyView as model where model."+
					"floorId=?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, floorId);
			queryObject.setMaxResults(1);
			ReplyView reply = (ReplyView) queryObject.uniqueResult();
			if(reply==null){
				return false;
			}else{
				return true;
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//TODO 获取评论数
		public int getReplyNum(int essayId){
			try {
				String queryString = "select count(distinct floorId) from ReplyView as model where model."+
						"essayId=?";
				Query queryObject = getCurrentSession().createQuery(queryString);
				queryObject.setParameter(0, essayId);
				return Integer.parseInt(queryObject.uniqueResult().toString());
			} catch (RuntimeException re) {
				log.error("find by property name failed", re);
				throw re;
			}
		}
	
	
	public List findByEssayId(Object essayId,int first) {
		return findByProperty(ESSAY_ID, essayId,first);
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByRepliedUserId(Object repliedUserId) {
		return findByProperty(REPLIED_USER_ID, repliedUserId);
	}

	public List findByRepliedName(Object repliedName) {
		return findByProperty(REPLIED_NAME, repliedName);
	}

	public List findByRepContent(Object repContent) {
		return findByProperty(REP_CONTENT, repContent);
	}

	public List findByFloorId(Object floorId) {
		return findByProperty(FLOOR_ID, floorId);
	}

	public List findByClickGoodNum(Object clickGoodNum) {
		return findByProperty(CLICK_GOOD_NUM, clickGoodNum);
	}

	public List findAll() {
		log.debug("finding all ReplyView instances");
		try {
			String queryString = "from ReplyView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReplyView merge(ReplyView detachedInstance) {
		log.debug("merging ReplyView instance");
		try {
			ReplyView result = (ReplyView) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReplyView instance) {
		log.debug("attaching dirty ReplyView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReplyView instance) {
		log.debug("attaching clean ReplyView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReplyViewDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ReplyViewDAO) ctx.getBean("ReplyViewDAO");
	}
}