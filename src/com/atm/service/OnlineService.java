package com.atm.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.user.UserDAO;
import com.atm.daoDefined.LoginDAO;
import com.atm.model.define.Login;
import com.atm.model.user.User;
import com.atm.service.admin.AdminService;
import com.atm.util.Application;

public class OnlineService implements Application{
	private static final Logger log =
			LoggerFactory.getLogger(OnlineService.class);
	private String loginList = "<tr>"
			+ "<th>�˺�</th>"
			+ "<th>���ڵ�readselector���</th>"
			+ "<th>�鿴��ϸ��Ϣ</th>"
			+ "<th>����</th></tr>";
	private String userList = "<tr id='table_head'>"
			+ "<th>�˺�</th>"
			+ "<th>�Ƿ��Ѿ���¼</th>"
			+ "<th>�鿴��ϸ��Ϣ</th>"
			+ "<th>����</th></tr>";   
	public OnlineService(){}
	
	public String getLoginList(){
		log.info("getLoginList()");
		LoginDAO loginDAO = (LoginDAO)context.getBean("LoginDAO");
		List<Login> logins = loginDAO.findAll();
		for(int i=0;i<logins.size();i++){
			Login login = logins.get(i);
			addLoginList(login.getUserId(), login.getNumber(),i);
		}
		return loginList;
	}
	
	public String getLoginList(int first,int max){
		log.info("getLoginList()");
		LoginDAO loginDAO = (LoginDAO)context.getBean("LoginDAO");
		List<Login> logins = loginDAO.findLoginList(first, max);
		for(int i=0;i<logins.size();i++){
			Login login = logins.get(i);
			addLoginList(login.getUserId(), login.getNumber(),i);
		}
		System.out.println(loginList);
		return loginList;
	}
	/**
	 * �����ҳ������ ��userID ReadSocketor�ı��number 
	 * ��ֹĳ�˵�¼�Լ��鿴�û���Ϣ
	 * @param userId
	 * @param number
	 * @param i
	 */
	public void addLoginList(String userId,int number,int i){
		loginList += "<tr>"
				+ "<th id='userId" + i + "' value='"+ userId +"'>" + userId + "</th>"
				+ "<td id='number" + i + "' value='"+ number +"'>" + number + "</td>"
				+ "<td>" + "<input type='button' value='������Ϲ����' onclick='deleteLogin(" + i + ")'>"
				+ "</td>"
				+ "<td><input type='button' value='�鿴����' onclick='lookInfo('" + i + ")'>"
				+ "</tr>" ;
	}
	
	/**
	 * �鿴
	 * @param first
	 * @param max
	 * @return
	 */
	public String getUserList(int first,int max){
		log.info("...........getUserList().............");
		UserDAO userDao = 
				(UserDAO)context.getBean("UserDAOImpl");
		List<User> list = userDao.findUserList(first, max);
		log.info("find first is " + first + " and get " + max + "");
		Set<String> userLogins = AdminService.getLoginList();
		for(int i=0;i<list.size();i++){
			User user = list.get(i);
			if(userLogins.remove(user.getUserId())){
				addUserList(user.getUserId(),"yes", i);
			}else{
				addUserList(user.getUserId(), "no", i);
			}
			//addUserList(user.getUserId(), "no", i);
		}
		System.out.println(userList);
		return userList;
	}
	public void addUserList(String userId,String isLogin,int i){
		userList += "<tr>"
				+ "<th name='userId' id='userId" + i + "' value='"+ userId +"'>" + userId + "</th>"
				+ "<td id='isLogin" + i + "' value='"+ isLogin +"'>" + isLogin + "</td>"
				+ "<td><a href='admin_userInfo.action?userId=" + userId +"'>�鿴����</a></td>"
				+ "</tr>" ;
	}
	
	public static int getLoginMaxRecord(){
		log.info("-------getMaxRecord----------");
		LoginDAO loginDao = 
				(LoginDAO)context.getBean("LoginDAO");
		return loginDao.getMaxRecord();
	}
	
	public int getMaxRecord(){
		log.info("-------getMaxRecord----------");
		UserDAO userDao = 
				(UserDAO)context.getBean("UserDAOImpl");
		return userDao.getMaxRecord();
	}
	
	public static void saveLogin(String userId,int number){
		LoginDAO loginDAO = (LoginDAO)context.getBean("LoginDAO");
		Login login = new Login(userId, number);
		log.info("save login");
		loginDAO.save(login);
	}
	
	public static void deleteLogin(String userId,int number){
		LoginDAO loginDAO = (LoginDAO)context.getBean("LoginDAO");
		Login login = new Login(userId, number);
		log.info("delete login");
		loginDAO.delete(login);
	}
	
	public static void updateLogin(String userId,int number){
		LoginDAO loginDAO = (LoginDAO)context.getBean("LoginDAO");
		Login login = new Login(userId, number);
		log.info("OnlineService update---user:" + userId + "  number:" + number);
		loginDAO.update(login);	
	}
}
