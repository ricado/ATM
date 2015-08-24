package com.atm.dao;

import java.util.List;

import com.atm.model.Appeal;

public interface AtmDAO {
	public abstract void saveOrUpdate(Object transientInstance);

	public abstract List findByHQL(String HQL);
}
