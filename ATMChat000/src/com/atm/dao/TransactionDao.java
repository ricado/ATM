package com.atm.dao;


public interface TransactionDao{
	//获取sessionFactory
	//public abstract void setSessionFactory(SessionFactory sessionFactory);
	//获取sesssion
	//private abstract Session getCurrentSession();
	//关闭session
	public abstract void closeSession();
	//开启事务
	public abstract void beginTransaction();
	//事务提交
    public void commit();
    //save
    public abstract void save(Object object);
    //update
    public abstract void update(Object object);
}
