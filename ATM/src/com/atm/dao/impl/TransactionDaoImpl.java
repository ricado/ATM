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
 * ���ڶ��save������Ҫ����ԭ���Կ��ṩ�ķ���֮һ
 * ����ɻ�ȡsession.
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
	//��ȡsessionFactory
	public void setSessionFactory(SessionFactory sessionFactory){
       this.sessionFactory = sessionFactory;
    }
	//��ȡsession
    private Session getCurrentSession(){
     return sessionFactory.openSession();//.openSession();//getCurrentSession(); 
    }
    //�ر�session
    public void closeSession(){
    	getCurrentSession().close();
    }
    //��������
    public void beginTransaction(){
    	transaction = getCurrentSession().beginTransaction();
    }
    //�����ύ
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
    //��ȡTransactionDao����
    public static TransactionDao getFromApplicationContext() {
    	return (TransactionDao) ApplicationUtil.getApplicationContext().getBean("TransactionDaoImpl");
	}
}