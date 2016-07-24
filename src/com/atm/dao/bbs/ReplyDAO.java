package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.Reply;


public interface ReplyDAO {

	// property constants
	public static final String RECOMMENT_ID = "recommentId";
	public static final String REP_CONTENT = "repContent";
	public static final String USER_ID = "userId";
	public static final String FLOOR_ID = "floorId";

	public abstract void setSessionFactory(SessionFactory sessionFactory);
	public abstract void deleteAFloor(int floorId,int essayId);
	public abstract void save(Reply transientInstance);

	public abstract void delete(Reply persistentInstance);

	public abstract Reply findById(java.lang.Integer id);

	public abstract List findByExample(Reply instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByRecommentId(Object recommentId);

	public abstract List findByRepContent(Object repContent);

	public abstract List findByUserId(Object userId);

	public abstract List findByFloorId(Object floorId);

	public abstract List findAll();

	public abstract Reply merge(Reply detachedInstance);

	public abstract void attachDirty(Reply instance);

	public abstract void attachClean(Reply instance);

}