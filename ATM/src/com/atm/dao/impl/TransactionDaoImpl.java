package com.atm.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.TransactionDao;
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.UserDAO;
import com.atm.util.ApplicationUtil;
/**
 * 
 * 对于多个save对象需要事务原子性可提供的方法之一
 * 该类可获取session.
 * @author ye
 *
 */
public class TransactionDaoImpl{
	 private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	 private SessionFactory sessionFactory;
	 private Transaction transaction;
    /* (non-Javadoc)
	 * @see com.atm.dao.UserDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	//获取sessionFactory
	public void setSessionFactory(SessionFactory sessionFactory){
       this.sessionFactory = sessionFactory;
    }
	//获取session
    private Session getCurrentSession(){
     return sessionFactory.openSession();//.openSession();//getCurrentSession(); 
    }
    //关闭session
    public void closeSession(){
    	getCurrentSession().close();
    }
    //开启事务
    public void beginTransaction(){
    	transaction = getCurrentSession().beginTransaction();
    }
    //事务提交
    public void commit(){
    	transaction.commit();
    }
    //save
    public void save(Object object){
    	getCurrentSession().save(object);
    }
    //update
    public void update(Object object){
    	getCurrentSession().update(object);
    }
    //获取TransactionDao对象
    public static TransactionDao getFromApplicationContext() {
    	return (TransactionDao) ApplicationUtil.getApplicationContext().getBean("TransactionDaoImpl");
	}
}