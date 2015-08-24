package com.atm.dao.bbs;

import java.util.List;

import org.hibernate.SessionFactory;

import com.atm.model.bbs.LabelAttentionAssociation;



public interface LabelAttentionAssociationDAO {

	public abstract void setSessionFactory(SessionFactory sessionFactory);

	public abstract void save(LabelAttentionAssociation transientInstance);

	public abstract void delete(LabelAttentionAssociation persistentInstance);

	public abstract LabelAttentionAssociation findById(
			com.atm.model.LabelAttentionAssociationId id);

	public abstract List findByExample(LabelAttentionAssociation instance);

	public abstract List findByProperty(String propertyName, Object value);
	public abstract List findByUserId(String userId);

	public abstract List findAll();

	public abstract LabelAttentionAssociation merge(
			LabelAttentionAssociation detachedInstance);

	public abstract void attachDirty(LabelAttentionAssociation instance);

	public abstract void attachClean(LabelAttentionAssociation instance);

}