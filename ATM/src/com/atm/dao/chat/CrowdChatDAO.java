package com.atm.dao.chat;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.chat.CrowdChat;
import com.atm.model.define.chat.CrowdInfo;
import com.atm.model.define.chat.CrowdList;

public interface CrowdChatDAO extends AtmDAO{

	//property constants
	public static final String CROWD_LABEL = "crowdLabel";
	public static final String CROWD_DESCRIPTION = "crowdDescription";
	public static final String CROWD_HEAD_IMAGE = "crowdHeadImage";
	public static final String IS_HIDDEN = "isHidden";
	public static final String CROWD_NAME = "crowdName";
	public static final String NUM_LIMIT = "numLimit";
	public static final String VERIFY_MODE = "verifyMode";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(CrowdChat transientInstance);

	public abstract void delete(CrowdChat persistentInstance);

	public abstract CrowdChat findById(java.lang.Integer id);

	public abstract List findByExample(CrowdChat instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByCrowdLabel(Object crowdLabel);

	public abstract List findByCrowdDescription(Object crowdDescription);

	public abstract List findByCrowdHeadImage(Object crowdHeadImage);

	public abstract List findByIsHidden(Object isHidden);

	public abstract List findByCrowdName(Object crowdName);

	public abstract List findByNumLimit(Object numLimit);

	public abstract List findByVerifyMode(Object verifyMode);

	public abstract List findAll();

	public abstract CrowdChat merge(CrowdChat detachedInstance);

	public abstract void attachDirty(CrowdChat instance);

	public abstract void attachClean(CrowdChat instance);

	public abstract List<CrowdList> findInterestingCrowd(String userId, int first, int max);

	public abstract List<CrowdList> findHotCrowd(int first, int max);
	
	public abstract List<CrowdList> findUsersCrowd(String userId,int first,int max);

	public abstract List<CrowdInfo> findCrowd(String keyword, int type, int first, int max);

}