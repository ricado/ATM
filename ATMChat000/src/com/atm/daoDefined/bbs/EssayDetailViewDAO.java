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

import com.atm.model.define.bbs.EssayDetailView;



/**
 * A data access object (DAO) providing persistence and search support for
 * EssayDetailView entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.define.EssayDetailView
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class EssayDetailViewDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EssayDetailViewDAO.class);
	// property constants
	public static final String PUBLISHER_ID = "publisherId";
	public static final String TITLE = "title";
	public static final String CLICK_NUM = "clickNum";
	public static final String CONTENT = "content";
	public static final String DEPARTMENT = "department";
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

	public void save(EssayDetailView transientInstance) {
		log.debug("saving EssayDetailView instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(EssayDetailView persistentInstance) {
		log.debug("deleting EssayDetailView instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EssayDetailView findById(java.lang.Integer id) {
		log.debug("getting EssayDetailView instance with id: " + id);
		try {
			EssayDetailView instance = (EssayDetailView) getCurrentSession()
					.get("com.atm.model.define.EssayDetailView", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EssayDetailView instance) {
		log.debug("finding EssayDetailView instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.atm.model.define.EssayDetailView")
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
		log.debug("finding EssayDetailView instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from EssayDetailView as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	//获取客户端进入帖子详情需要参数
	public EssayDetailView findSomeValue(int essayId) {
		try {
			String queryString = "select new EssayDetailView(replyNum)"+
						"from EssayDetailView as model where model.essayId"
					+ "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, essayId);
			return (EssayDetailView) queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List findByPublisherId(Object publisherId) {
		return findByProperty(PUBLISHER_ID, publisherId);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByClickNum(Object clickNum) {
		return findByProperty(CLICK_NUM, clickNum);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByDepartment(Object department) {
		return findByProperty(DEPARTMENT, department);
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
		log.debug("finding all EssayDetailView instances");
		try {
			String queryString = "from EssayDetailView";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public EssayDetailView merge(EssayDetailView detachedInstance) {
		log.debug("merging EssayDetailView instance");
		try {
			EssayDetailView result = (EssayDetailView) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(EssayDetailView instance) {
		log.debug("attaching dirty EssayDetailView instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EssayDetailView instance) {
		log.debug("attaching clean EssayDetailView instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EssayDetailViewDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (EssayDetailViewDAO) ctx.getBean("EssayDetailViewDAO");
	}
}