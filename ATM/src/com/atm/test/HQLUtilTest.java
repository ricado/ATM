package com.atm.test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.atm.dao.user.UserInfoDAO;
import com.atm.util.Application;
import com.atm.util.HQLUtil;

public class HQLUtilTest implements Application{
	public static void main(String[] args) {
		HQLUtilTest test = new HQLUtilTest();
		String HQL = test.getHQL();
	}
	public String getHQL(){
		String[] select = {"userId","flag","sex"};
		String[] key= {"name","sign"};
		String[] value = {"kaka","2"};
		String HQL = HQLUtil.findByMap(select, key, value, "UserInfo");
		System.out.println(HQL);
		UserInfoDAO userInfoDAO = (UserInfoDAO)context.getBean("UserInfoDAOImpl");
		List list = userInfoDAO.findByHQL(HQL);
		for(Iterator iterator=list.iterator();iterator.hasNext();){
			Object[] objects = (Object[])iterator.next();
			System.out.println(java.util.Arrays.toString(objects));
		}
		return HQL;
	}
}
