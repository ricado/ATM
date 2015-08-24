package com.atm.dao.user;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.dao.AtmDAO;
import com.atm.model.define.user.UserList;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;

public interface UserInfoDAO extends AtmDAO{

	//property constants
	public static final String SC_NO = "scNo";
	public static final String DNO = "dno";
	public static final String HEAD_IMAGE_PATH = "headImagePath";
	public static final String SIGN = "sign";
	public static final String NAME = "name";
	public static final String NICKNAME = "nickname";
	public static final String SEX = "sex";
	public static final String JOB_TITLE = "jobTitle";
	public static final String FLAG = "flag";

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(UserInfo transientInstance);

	public abstract void delete(UserInfo persistentInstance);

	public abstract UserInfo findById(java.lang.String id);

	public abstract List findByExample(UserInfo instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByScNo(Object scNo);

	public abstract List findByDno(Object dno);

	public abstract List findByHeadImagePath(Object headImagePath);

	public abstract List findBySign(Object sign);

	public abstract List findByName(Object name);

	public abstract List findByNickname(Object nickname);

	public abstract List findBySex(Object sex);

	public abstract List findByJobTitle(Object jobTitle);

	public abstract List findByFlag(Object flag);

	public abstract List<UserInfo> findAll();

	public abstract UserInfo merge(UserInfo detachedInstance);

	public abstract void attachDirty(UserInfo instance);

	public abstract void attachClean(UserInfo instance);

	public abstract List findByHQL(String HQL);//更改离线时间

	public abstract int updateOffTime(String userId);

	public abstract List<UserList> findUser(String keyWord, int first, int max);

	public abstract List<UserList> findinterestingUser(String userId, int first, int max);

	public abstract List<UserInfo> findAll(int first, int max);

	public abstract List searchPeople(Object userName);
}