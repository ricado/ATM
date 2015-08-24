package com.atm.daoDefined;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.model.chat.ChatRecord;
import com.atm.model.define.chat.CrowdInfo;

@Transactional
public class MySessionDAO {
	private static final Logger log = LoggerFactory.getLogger("SessionDAOImpl");
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();// .openSession();//getCurrentSession();
	}

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

	public Query getQuery(String queryName) {
		log.info("user procedrue");
		try {
			log.info("get success");
			return getCurrentSession().getNamedQuery(queryName);
		} catch (RuntimeException e) {
			log.info("get error");
			throw e;
		}
	}

	public SQLQuery getSqlQuery(String queryName) {
		log.info("user procedrue");
		try {
			log.info("get success");
			return getCurrentSession().createSQLQuery(queryName);
		} catch (RuntimeException e) {
			log.info("get error");
			throw e;
		}
	}

	public List getCrowdMenberInfo(String userId, int crowdId) {
		log.info("user procedrue");
		try {
			log.info("get success");
			return getCurrentSession()
					.createSQLQuery("{call crowdMenberInfo(?,?)}")
					.setString(0, userId).setInteger(1, crowdId).list();
		} catch (RuntimeException e) {
			log.info("get error");
			throw e;
		}
	}

	public List getAllCrowdMenberInfo(int crowdId) {
		log.info("user procedrue");
		try {
			return getCurrentSession()
					.createSQLQuery("{call crowdAllMenber(?)}")
					.setInteger(0, crowdId).list();
		} catch (RuntimeException e) {
			log.info("get error");
			throw e;
		}
	}

	public List getCrowdByUserId(String userId) {
		log.info("user procedrue");
		try {
			log.info("get success");
			return getCurrentSession().createSQLQuery("{call}")
					.setString(1, userId).list();
		} catch (RuntimeException e) {
			log.info("get error");
			throw e;
		}
	}
	
	
	//获取群聊消息
	public List<CrowdInfo> getCrowdInfo(int crowdID) {
		log.info("crowd info");
		try {
			List<CrowdInfo> crowdInfos = getCurrentSession().getNamedQuery("crowdInfo")
					.setInteger(0, crowdID).list();
			log.info("get success");
			return crowdInfos;
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.info("get error");
			throw e;
		}
	}
	
	/*public List<ChatRecord> getCrowdOffRecord(String userId){
		log.info("crowd info");
		try {
			log.info("get success");
			return getCurrentSession().createSQLQuery("{call crowdOffRecord(?)}")
					.setString(0, userId)..list();
		} catch (RuntimeException e) {
			log.info("get error");
			throw e;
		}
	}*/
	/**
	 * 获取群聊的离线消息
	 * @param userId
	 * @return List<ChatRecord>
	 */
	public List<ChatRecord> getCrowdOffRecord(String userId){
		log.info("crowd info");
		try {
			log.info("get success");
			return getCurrentSession().getNamedQuery("crowdOffRecord")
					.setString(0, userId).list();
		} catch (RuntimeException e) {
			log.info("get error");
			throw e;
		}
	}
}
