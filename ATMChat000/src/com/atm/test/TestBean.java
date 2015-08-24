package com.atm.test;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.impl.user.UserInfoDAOImpl;
import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;

public class TestBean {
	public static void main(String[] args){
		TestBean bean = new TestBean();
		bean.run();
	}
	public void run(){
		UserDAO userDao = UserDAOImpl.getFromApplicationContext();
		UserInfoDAO userInfoDao = UserInfoDAOImpl.getFromApplicationContext();
		User user = new User();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("300010");
		userInfo.setName("Ò£Ô¶µÄËû");
		user.setUserId("300002");
		user.setUserPwd("10086");
		try{
			System.out.println("=========userinfo===========");
			userInfoDao.save(userInfo);
			System.out.println("===========user=============");
			userDao.save(user);
			System.out.println("=========success===========");
		}catch(Exception e){
			System.out.println("=========error===========");
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
}
