package com.atm.dao;


public interface TransactionDao{
	//��ȡsessionFactory
	//public abstract void setSessionFactory(SessionFactory sessionFactory);
	//��ȡsesssion
	//private abstract Session getCurrentSession();
	//�ر�session
	public abstract void closeSession();
	//��������
	public abstract void beginTransaction();
	//�����ύ
    public void commit();
    //save
    public abstract void save(Object object);
    //update
    public abstract void update(Object object);
}
