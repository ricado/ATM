package com.atm.dao.chat;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.chat.CrowdChat;
import com.atm.model.chat.CrowdMenber;
import com.atm.model.define.chat.MenberList;

public interface CrowdMenberDAO extends AtmDAO{

	//property constants
	public static final String IS_SHUT_UP = "isShutUp";
	public static final String ROLE_ID = "roleId";
	public static final String USER_ID = "userId";
	public static final String CROWD_ID = "crowdId";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(CrowdMenber transientInstance);

	public abstract void delete(CrowdMenber persistentInstance);

	public abstract CrowdMenber findById(com.atm.model.chat.CrowdMenberId id);

	public abstract List findByExample(CrowdMenber instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByIsShutUp(Object isShutUp);

	public abstract List findByRoleId(Object roleId);
	
	public abstract List findByCrowdId(Object crowdId);

	public abstract List findByUserId(Object userId);

	public abstract List findAll();

	public abstract CrowdMenber merge(CrowdMenber detachedInstance);

	public abstract void attachDirty(CrowdMenber instance);

	public abstract void attachClean(CrowdMenber instance);

	public abstract List<MenberList> findAllMenber(int crowdId);

	public abstract int addManager(String userId, int crowdId);
	
	public abstract int cancelManager(String userId, int crowdId);

	public abstract int removeMenber(String userId, int crowdId);

}