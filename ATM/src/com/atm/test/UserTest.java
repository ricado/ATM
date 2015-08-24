package com.atm.test;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.SessionDAO;
import com.atm.dao.impl.user.StudentDAOImpl;
import com.atm.dao.impl.user.TeacherDAOImpl;
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.model.user.Student;
import com.atm.model.user.Teacher;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.service.user.UserService;
import com.atm.util.Application;
import com.atm.util.mail.SendDemo;

public class UserTest implements Application {
	private static final Logger log = LoggerFactory.getLogger(UserTest.class);
	public int i = 0;
	private UserService userService = new UserService();
	public static void main(String[] args) {
		/*
		 * UserTest test = new UserTest(); String email = "15622797401@163.com";
		 * JSONArray jsonArray = new JSONArray(); JSONObject json = new
		 * JSONObject(); json.put("email", email); System.out.println(json);
		 */
		// jsonArray.add(json);
		// System.out.println(jsonArray);
		/*
		 * test.findUserId(json.toString()); test test = (test)context
		 * .getBean("Test");
		 */
		// test.saveRegister();
		UserTest test = new UserTest();
		/*
		 * String email = test.findEmailById(); log.debug("email:" + email);
		 */
		// test.saveUserInfo();
		/*
		 * for(int i = 10007;i<20000;i++){ test.saveUser(i+""); }
		 */
		// �����˳���¼�޸�����ʱ��
		// test.upateOffTime();
		// �����û�
		//test.findUser();
		// ���ܸ���Ȥ���ùǻ�
		// test.findInterestIngUser();
		//�ҹ�ע��
		//test.findAttent();
		//��ע�ҵ�
		//test.findAttented();
		//�û��Ļ�����Ϣ
		test.getUserBasicInfo();
		//test.pathTest();
	}

	public UserTest() {
	}

	public boolean login(User user) {
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		if (!dao.findByExample(user).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �һ��˺�
	 */
	public String findUserId(String json) {
		String email = JSONObject.fromObject(json).getString("email");
		System.out.println(email);
		String userId = findByEmail(email);
		if (!userId.equals("")) {
			SendDemo demo = new SendDemo();
			demo.sendFindUserIdEmail(email, userId);
			System.out.println("send................");
			if (demo.send().equals("success")) {
				System.out.println("send.........success");
				return userId;
			}
		}
		return "error";
	}

	/**
	 * ���û���������ʱ�����˺Ż�������ʱ����֤��������˺Ż������䡣����֤�Ƿ����
	 * 
	 * @author ye
	 */
	public String forgetPasswod(String idOrEmail) {
		// ��ȡ������˺Ż��������ַ���
		// String idOrEmail = (String)request.getAttribute("idOrEmail");
		// id or email isExits?
		String flag;
		String tip;
		System.out.println(idOrEmail + "===========");
		if (idOrEmail.split("@").length >= 2) {
			flag = findByEmail(idOrEmail);
		} else {
			flag = findById(idOrEmail);
		}
		if (!flag.equals("")) {
			// ��������˺���ȷ����������
			SendDemo demo = new SendDemo();
			tip = demo.send();
			// ���÷��ص�������֤��
		} else {
			tip = "error";
		}
		return tip;
	}

	/**
	 * ��֤�˺��Ƿ������ݿ���
	 * 
	 * @param userId
	 * @return true or false
	 */
	public String findById(String userId) {
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		if (dao.findById(userId) != null) {
			return userId;
		}
		return "";
	}

	/**
	 * ���û���������ʱ�����������ʱ����֤�����Ƿ������ݿ���
	 * 
	 * @param email
	 * @return true or false
	 */
	public String findByEmail(String email) {
		System.out.println(email + "===========");
		StudentDAO dao = StudentDAOImpl.getFromApplicationContext();
		String userId = ((Student) dao.findByEmail(email).get(0)).getUserId();
		System.out.println("=====================" + userId
				+ "===================");
		if (userId != null && !userId.equals("")) {
			return userId;
		} else {
			TeacherDAO teacherDao = TeacherDAOImpl.getFromApplicationContext();
			userId = ((Teacher) teacherDao.findByEmail(email).get(0))
					.getUserId();
			if (userId != null && userId.equals("")) {
				return userId;
			}
		}
		return "";
	}

	public String findEmailById() {
		String userId = "10002";
		SessionDAO sessionDAO = (SessionDAO) context.getBean("SessionDAOImpl");
		String HQL = "select u.flag from UserInfo u where userId='" + userId
				+ "'";
		List list = (List) sessionDAO.findByHQL(HQL);
		int flag = (int) list.get(0);
		log.debug(">>>>>>>>>>>>>>>flag:" + flag);
		if (flag == 2) {
			List list2 = (List) sessionDAO
					.findByHQL("select s.email from Student s where userId='"
							+ userId + "'");
			return (String) list2.get(0);
		} else {
			List list3 = (List) sessionDAO
					.findByHQL("select t.email from Teacher t where userId='"
							+ userId + "'");
			return (String) list3.get(0);
		}
	}

	/**
	 * �����û���Ϣ�Ĳ���
	 */
	public String saveUserInfo() {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		UserInfo userInfo = new UserInfo();
		userInfo.setName("��ΰ");
		userInfo.setNickname("���벻��");
		userInfo.setFlag(1);
		userInfoDAO.save(userInfo);
		log.debug(userInfo.getUserId());
		return "ok";
	}

	public void saveUser(String id) {
		UserInfo userInfo = new UserInfo(id, null, "1", "1", "", "2", "atm",
				"aa", "��", "", 1);
		User user = new User(id, "1315");
		UserDAO userDAO = (UserDAO) context.getBean("UserDAOImpl");
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		try {
			log.info("======save " + id + "========");
			userInfoDAO.save(userInfo);
			userDAO.save(user);
			log.info("========success==========");
		} catch (Exception e) {
			log.info("======error=======");
		}
	}

	/**
	 * �����û����ߣ��޸�����ʱ��
	 * 
	 * @result success
	 */
	public void upateOffTime() {
		UserService.exit("10001");
	}

	public void findUser() {
		userService.findUser("888", 0, 4);
	}

	public void findInterestIngUser() {
		UserService userService = new UserService();
		userService.findInterestingUser("10001", 0, 4);
	}

	public void pathTest(){
		/*String path = ServletActionContext.getRequest().getContextPath();
		String basePath = ServletActionContext.getRequest().getScheme() + "://"
				+ ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort()
				+ path + "/";*/
		String string = System.getProperty("catalina.base");
		log.info("string:" + string);
		/*log.info("parh:" + path);
		log.info("basePath:" + basePath);*/
	}
	
	public void findAttent(){
		userService.findMyAttent("10001");
	}
	public void findAttented(){
		userService.findAttentedMe("10001");
	}
	public void getUserBasicInfo(){
		UserService.getUserBasicInfo("10001");
		UserService.getUserBasicInfo("10005");
		UserService.getUserBasicInfo("10006");
		UserService.getUserBasicInfo("10007");
		UserService.getUserBasicInfo("10008");
		UserService.getUserBasicInfo("10009");
		UserService.getUserBasicInfo("10010");
		
	}
}
