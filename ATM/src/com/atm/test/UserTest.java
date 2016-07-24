package com.atm.test;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.collections.impl.Vector;

import com.atm.dao.SessionDAO;
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
	private static UserService userService = new UserService();

	public static void main(String[] args) throws IOException {
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
		// UserTest test = new UserTest();

		// java.util.Vector<Integer> peoples = new java.util.Vector<Integer>();
		// test.findUserId();
		// test.isAttetd();
		// test.findByEmail("116983393@qq.com");
		// test.getSdmNO();
		// test.confirm();

		// test.findOtherAttend();
		/*
		 * String email = test.findEmailById(); log.debug("email:" + email);
		 */
		// test.saveUserInfo();
		/*
		 * for(int i = 10007;i<20000;i++){ test.saveUser(i+""); }
		 */
		// 检验退出登录修改离线时间
		// test.upateOffTime();
		// 查找用户
		// test.findUser();
		// 可能感兴趣的用骨灰
		// test.findInterestIngUser();
		// 我关注的
		// test.findAttent();
		// 关注我的
		// test.findAttented();
		// 用户的基本信息
		// test.getUserBasicInfo();
		// test.pathTest();
		// userService.graduateConfirm("091544247", "刘彬", "2009");
		// test.getUserHeadImage();
		// 测试分词搜索
		// userService.findUserA("", 10, 1);
		userService.findUserB("程序猿 红发");
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
	 * 找回账号
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

	public void findUserId() {
		String json = "[{'email':'550260496@qq.com'}]";
		userService.findUserId(json);
	}

	/**
	 * 当用户忘记密码时输入账号或者邮箱时，验证输入的是账号还是邮箱。并验证是否存在
	 * 
	 * @author ye
	 */
	public String forgetPasswod(String idOrEmail) {
		// 获取输入的账号或者邮箱字符串
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
			// 邮箱或者账号正确，发送邮箱
			SendDemo demo = new SendDemo();
			tip = demo.send();
			// 设置返回的邮箱验证码
		} else {
			tip = "error";
		}
		return tip;
	}

	/**
	 * 验证账号是否在数据库中
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
	 * 当用户忘记密码时输入的是邮箱时，验证邮箱是否在数据库中
	 * 
	 * @param email
	 * @return true or false
	 */
	public String findByEmail(String email) {
		/*
		 * System.out.println(email + "==========="); StudentDAO dao =
		 * StudentDAOImpl.getFromApplicationContext(); String userId =
		 * ((Student) dao.findByEmail(email).get(0)).getUserId();
		 * System.out.println("=====================" + userId +
		 * "==================="); if (userId != null && !userId.equals("")) {
		 * return userId; } else { TeacherDAO teacherDao =
		 * TeacherDAOImpl.getFromApplicationContext(); userId = ((Teacher)
		 * teacherDao.findByEmail(email).get(0)) .getUserId(); if (userId !=
		 * null && userId.equals("")) { return userId; } } return "";
		 */

		log.info(email + "==================");
		StudentDAO dao = (StudentDAO) context.getBean("StudentDAOImpl");
		List list = dao.findByEmail(email);
		String userId = null;
		if (list.size() > 0) {
			Student student = (Student) list.get(0);
			userId = student.getUserId();
			log.info("=====================" + userId + "===================");
			return userId;
		} else {
			log.info("teacher---------------------------");
			TeacherDAO teacherDao = (TeacherDAO) context
					.getBean("TeacherDAOImpl");
			list = teacherDao.findByEmail(email);
			if (list.size() > 0) {
				Teacher teacher = (Teacher) list.get(0);
				userId = teacher.getUserId();
				log.info("=====================" + userId
						+ "===================");
				return userId;
			}
		}
		log.info("======aaaaaaaa======");
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
	 * 保存用户信息的测试
	 */
	public String saveUserInfo() {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		UserInfo userInfo = new UserInfo();
		userInfo.setName("李伟");
		userInfo.setNickname("不离不弃");
		userInfo.setFlag(1);
		userInfoDAO.save(userInfo);
		log.debug(userInfo.getUserId());
		return "ok";
	}

	public void saveUser(String id) {
		UserInfo userInfo = new UserInfo(id, null, "1", "1", "", "2", "atm",
				"aa", "男", "", 1);
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
	 * 测试用户离线，修改离线时间
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

	public void pathTest() {
		/*
		 * String path = ServletActionContext.getRequest().getContextPath();
		 * String basePath = ServletActionContext.getRequest().getScheme() +
		 * "://" + ServletActionContext.getRequest().getServerName() + ":" +
		 * ServletActionContext.getRequest().getServerPort() + path + "/";
		 */
		String string = System.getProperty("catalina.base");
		log.info("string:" + string);
		/*
		 * log.info("parh:" + path); log.info("basePath:" + basePath);
		 */
	}

	public void findAttent() {
		userService.findMyAttent("10001");
	}

	public void findAttented() {
		userService.findAttentedMe("10001");
	}

	public void getUserBasicInfo() {
		UserService.getUserBasicInfo("10001");
		UserService.getUserBasicInfo("10005");
		UserService.getUserBasicInfo("10006");
		UserService.getUserBasicInfo("10007");
		UserService.getUserBasicInfo("10008");
		UserService.getUserBasicInfo("10009");
		UserService.getUserBasicInfo("10010");

	}

	public void getUserHeadImage() throws IOException {
		userService.getUserSmallHeadByte("10001");
	}

	public void getSdmNO() {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		List list = userInfoDAO.getSdmNO("广东金融学院", "互联网金融与信息工程系", "计算机科学与技术");
		Object[] numbers = (Object[]) list.get(0);
		log.info("sno:" + numbers[0] + ",dNo:" + numbers[1] + ",mno:"
				+ numbers[2]);
	}

	public void confirm() {
		/*
		 * String json =
		 * "[{'Num':'131544215','Pwd':'1315CCYa','flag':'2','userId':'10001'}]";
		 * userService.confirmUser(json);
		 */
		userService.userConfirm("131544215", "1315CCYa", "学生");
	}

	public void findOtherAttend() {
		userService.findOtherAttented("10001", "10002");
	}

	public void isAttetd() {
		userService.getRelationShip("10002", "10004");
	}
}
