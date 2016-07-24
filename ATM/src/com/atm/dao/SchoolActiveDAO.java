package com.atm.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.atm.model.SchoolActive;

/**
 * A data access object (DAO) providing persistence and search support for
 * SchoolActive entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.atm.model.SchoolActive
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class SchoolActiveDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SchoolActiveDAO.class);
	// property constants
	public static final String MAIN_TITLE = "mainTitle";
	public static final String NEW_IMAGE_PATH = "newImagePath";
	public static final String WRITER = "writer";
	public static final String CONTENT = "content";
	public static final String READ_COUNT = "readCount";
	public static final String VICE_TITLE = "viceTitle";

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

	public void save(SchoolActive transientInstance) {
		log.debug("saving SchoolActive instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SchoolActive persistentInstance) {
		log.debug("deleting SchoolActive instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SchoolActive findById(java.lang.Integer id) {
		log.debug("getting SchoolActive instance with id: " + id);
		try {
			SchoolActive instance = (SchoolActive) getCurrentSession().get(
					"com.atm.model.SchoolActive", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SchoolActive> findByExample(SchoolActive instance) {
		log.debug("finding SchoolActive instance by example");
		try {
			List<SchoolActive> results = (List<SchoolActive>) getCurrentSession()
					.createCriteria("com.atm.dao.SchoolActive")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SchoolActive instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SchoolActive as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SchoolActive> findByMainTitle(Object mainTitle) {
		return findByProperty(MAIN_TITLE, mainTitle);
	}

	public List<SchoolActive> findByNewImagePath(Object newImagePath) {
		return findByProperty(NEW_IMAGE_PATH, newImagePath);
	}

	public List<SchoolActive> findByWriter(Object writer) {
		return findByProperty(WRITER, writer);
	}

	public List<SchoolActive> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<SchoolActive> findByReadCount(Object readCount) {
		return findByProperty(READ_COUNT, readCount);
	}

	public List<SchoolActive> findByViceTitle(Object viceTitle) {
		return findByProperty(VICE_TITLE, viceTitle);
	}

	public List findAll() {
		log.debug("finding all SchoolActive instances");
		try {
			String queryString = "from SchoolActive";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List<SchoolActive> findList(int index, int max) {
		log.debug("finding all SchoolActive instances");
		try {
			String queryString = "from SchoolActive where isHeadNews=0 order by newsId desc";
			Query queryObject = getCurrentSession().createQuery(queryString)
					.setFirstResult(index).setMaxResults(max);
			List<SchoolActive> actives = (List<SchoolActive>)queryObject.list();
			return actives;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public List<SchoolActive> findHeadNews() {
		log.debug("finding all SchoolActive instances");
		try {
			String queryString = "from SchoolActive where isHeadNews=1 order by newsId desc";
			Query queryObject = getCurrentSession().createQuery(queryString);
			List<SchoolActive> actives = (List<SchoolActive>)queryObject.list();
			return actives;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	public SchoolActive merge(SchoolActive detachedInstance) {
		log.debug("merging SchoolActive instance");
		try {
			SchoolActive result = (SchoolActive) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SchoolActive instance) {
		log.debug("attaching dirty SchoolActive instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SchoolActive instance) {
		log.debug("attaching clean SchoolActive instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SchoolActiveDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (SchoolActiveDAO) ctx.getBean("SchoolActiveDAO");
	}
}