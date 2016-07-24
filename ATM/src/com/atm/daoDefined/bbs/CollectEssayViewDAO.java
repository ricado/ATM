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

import com.atm.model.define.bbs.CollectEssayView;



/**
 * A data access object (DAO) providing persistence and search support for
 * CollectEssayView entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.CollectEssayView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CollectEssayViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CollectEssayViewDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String ESSAY_ID = "essayId";
	public static final String TITLE = "title";
	public static final String SOME_CONTENT = "someContent";
	public static final String ESSAY_TYPE = "essayType";
	public static final String LAB_NAME = "labName";
	public static final String NICKNAME = "nickname";
	public static final String CLICK_GOOD_NUM = "clickGoodNum";
	public static final String REPLY_NUM = "replyNum";

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

	public void save(CollectEssayView transientInstance) {
		log.debug("saving CollectEssayView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CollectEssayView persistentInstance) {
		log.debug("deleting CollectEssayView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CollectEssayView findById(java.lang.String id) {
		log.debug("getting CollectEssayView instance with id: " + id);
		try {
			CollectEssayView instance = (CollectEssayView) getCurrentSession()
					.get("com.atm.model.define.bbs.CollectEssayView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CollectEssayView instance) {
		log.debug("finding CollectEssayView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.bbs.CollectEssayView")
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
	public List findByProperty(String propertyName, Object value,int first){
		return findByProperty(propertyName, value,first,10);
	}
	//first:查询起始位置
	public List findByProperty(String propertyName, Object value,int first,int num) {
		log.debug("finding CollectEssayView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			//只取出需要显示的部分
			String queryString = 
					"select new CollectEssayView(essayId,essayType,title,"+
			"labName,labColor,nickname,clickGoodNum,replyNum,someContent,publishTime) "+
							"from CollectEssayView as model where model."
					+ propertyName + "= ? order by publishTime desc";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setFirstResult(first);
			queryObject.setMaxResults(num);//查询十条记录
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	//TODO 获取用户收藏的帖子数
	public int getCollectEssayNum(Object userId){
		try {
			String queryString = 
					"select count(essayId)"+
							"from CollectEssayView as model where model."
								+"userId= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, userId);
			return Integer.parseInt(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	//准备获取收藏贴
	public List findByUserId(Object userId,int first) {
		return findByProperty(USER_ID, userId,first);
	}

	public List findByEssayId(Object essayId) {
		return findByProperty(ESSAY_ID, essayId);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findBySomeContent(Object someContent) {
		return findByProperty(SOME_CONTENT, someContent);
	}

	public List findByEssayType(Object essayType) {
		return findByProperty(ESSAY_TYPE, essayType);
	}

	public List findByLabName(Object labName) {
		return findByProperty(LAB_NAME, labName);
	}

	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List findByClickGoodNum(Object clickGoodNum) {
		return findByProperty(CLICK_GOOD_NUM, clickGoodNum);
	}

	public List findByReplyNum(Object replyNum) {
		return findByProperty(REPLY_NUM, replyNum);
	}

	public List findAll() {
		log.debug("finding all CollectEssayView instances");
		try {
			String queryString = "from CollectEssayView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CollectEssayView merge(CollectEssayView detachedInstance) {
		log.debug("merging CollectEssayView instance");
		try {
			CollectEssayView result = (CollectEssayView) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CollectEssayView instance) {
		log.debug("attaching dirty CollectEssayView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CollectEssayView instance) {
		log.debug("attaching clean CollectEssayView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CollectEssayViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CollectEssayViewDAO) ctx.getBean("CollectEssayViewDAO");
	}
}