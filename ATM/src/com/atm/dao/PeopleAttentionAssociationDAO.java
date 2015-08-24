package com.atm.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.PeopleAttentionAssociation;
import com.atm.model.define.user.UserList;

public interface PeopleAttentionAssociationDAO extends AtmDAO{

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(PeopleAttentionAssociation transientInstance);

	public abstract void delete(PeopleAttentionAssociation persistentInstance);

	public abstract PeopleAttentionAssociation findById(
			com.atm.model.PeopleAttentionAssociationId id);

	public abstract List findByExample(PeopleAttentionAssociation instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findAll();

	public abstract PeopleAttentionAssociation merge(
			PeopleAttentionAssociation detachedInstance);

	public abstract void attachDirty(PeopleAttentionAssociation instance);

	public abstract void attachClean(PeopleAttentionAssociation instance);

	public abstract List<UserList> findByUserAttentId(String userAttentId);

	public abstract List<UserList> findByUserAttentedId(String userAttentedId);

	public abstract List findByAttendUserId(String userAttendId);
	
	public abstract List findByAttendedUserId(String userAttendedId);

}