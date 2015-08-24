package com.atm.service.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.atm.daoDefined.user.UserBasicInfoDAO;
import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.define.user.UserList;
import com.atm.model.user.Student;
import com.atm.model.user.Teacher;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.util.Application;
import com.atm.util.FileUtil;
import com.atm.util.JsonUtil;
import com.atm.util.mail.SendDemo;

public class UserService extends AttentService implements Application{
	private static final Logger log = LoggerFactory
			.getLogger(UserService.class);

	public UserService(){}

	// 用户登录
	public boolean login(HttpServletRequest request,
			HttpServletResponse response, String json) {
		log.info("登录");
		UserDAO dao = UserDAOImpl.getFromApplicationContext();

		User user = (User) request.getAttribute("user");

		request.setAttribute("userId", user.getUserId());
		if (!dao.findByExample(user).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断用户是否存在。
	 * 
	 * @param user
	 * @return true or flase
	 */
	public static boolean login(User user) {
		UserDAO dao = (UserDAO) context.getBean("UserDAOImpl");
		boolean flag = false;
		try {
			flag = !dao.findByExample(user).isEmpty();
			log.info("login success");
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}

	/**
	 * 找回账号
	 */
	// TODO 找回账号
	public String findUserId(HttpServletRequest request,
			HttpServletResponse response, String json) {
		String email = JSONObject.fromObject(json).getString("email");
		System.out.println(email + "=======================");
		String userId = findByEmail(email);
		if (!userId.equals("")) {
			SendDemo demo = new SendDemo();
			System.out.println("send................");
			// 发送并判断是否发送成功
			if (demo.sendFindUserIdEmail(email, userId).equals("success")) {
				System.out.println("send.........success");
			}
		}
		return userId;
	}

	/**
	 * 当用户忘记密码时输入账号或者邮箱时，验证输入的是账号还是邮箱。并验证是否存在
	 * 
	 * @author ye
	 */
	public String forgetPasswod(HttpServletRequest request,
			HttpServletResponse response, String json) {
		// 获取输入的账号或者邮箱字符串
		// String idOrEmail = (String)request.getAttribute("idOrEmail");
		// id or email isExits?
		String idOrEmail = JsonUtil.getString("idOrEmail", json);
		Map<String, String> map = new HashMap<String, String>();
		SendDemo demo = new SendDemo();
		String flag;
		String confirm;
		System.out.println(idOrEmail + "===========");
		if (idOrEmail.split("@").length >= 2) {
			// 验证邮箱是否存在，存在则flag为邮箱，否侧为空
			if (!findByEmail(idOrEmail).equals("")) {
				flag = idOrEmail;
			} else {
				flag = "";
			}
		} else {
			flag = findById(idOrEmail);
		}
		if (!flag.equals("")) {
			// 邮箱或者账号正确，发送邮箱
			// 设置邮箱
			String captchas = demo.sendChangePasswordEmail(flag);
			// 发送邮箱并验证是否成功
			if (demo.send().equals("success")) {
				// 成功设置成功的json
				map.put("tip", "success");
				map.put("captchas", captchas);
				return JsonUtil.mapToJson(map);
			}
		}
		map.put("tip", "error");
		map.put("confirmString", "");
		return JsonUtil.mapToJson(map);
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
			return findEmailById(userId);
		}
		return "";
	}

	/**
	 * 改变密码
	 * 
	 * @param json
	 * @return
	 */
	public String changePassword(String json) {
		UserDAO dao = context.getBean("UserDAOImpl", UserDAO.class);
		User user = (User) JsonUtil.jsonToObject(json, User.class);
		try {
			dao.updateByUser(user);
			log.debug("success");
			return JsonUtil.put("tip", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.put("tip", "error");
	}

	/**
	 * 当用户忘记密码时输入的是邮箱时，验证邮箱是否在数据库中
	 * 
	 * @param email
	 * @return true or false
	 * @2015.7.29
	 */
	public String findByEmail(String email) {
		// TODO
		System.out.println(email + "==================");
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

	/**
	 * 通过UserId查找出用户的Email
	 * 
	 * @param userId
	 * @return
	 */
	// TODO 通过UserId查找出用户的Email
	public String findEmailById(String userId) {
		// 得到UserInfoDAO通过UserId查找出该用户的身份
		/*
		 * UserInfoDAO userInfoDAO=
		 * (UserInfoDAO)context.getBean("UserInfoDAOImpl");
		 */
		SessionDAO sessionDAO = (SessionDAO) context.getBean("SessionDAOImpl");
		String HQL = "select u.flag from UserInfo u where userId='" + userId
				+ "'";
		List list = (List) sessionDAO.findByHQL(HQL);
		int flag = (int) list.get(0);
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
	 * 通过词汇匹配，查找出符合条件的用户，返回列表的json
	 * 
	 * @param string
	 *            要匹配的词汇
	 * @return
	 */
	public List<UserList> findUser(String string, int first, int max) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		log.info("查找相关的用户");
		List<UserList> userLists = userInfoDAO.findUser(string, first, max);
		log.info("==========" + string + "相关用户===========");
		showUserList(userLists);
		return userLists;
	}

	public List<UserList> findInterestingUser(String userId, int first, int max) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		log.info("查找相关的用户");
		List<UserList> userLists = userInfoDAO.findinterestingUser(userId,
				first, max);
		log.info("==========" + userId + "相关的用户===========");
		showUserList(userLists);
		return userLists;
	}
	/**
	 * 用户退出 更改下线时间
	 * 
	 * @param userId
	 */
	public static void exit(String userId) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		int i = userInfoDAO.updateOffTime(userId);
		log.info("update time ----" + i);
	}
	/**
	 * 获取用户基本的信息
	 * 
	 * @return
	 */
	public static UserBasicInfo getUserBasicInfo(String userId) {
		log.info("get userBasicinfo----------");
		UserBasicInfoDAO infoDAO = (UserBasicInfoDAO) context
				.getBean("UserBasicInfoDAO");
		UserBasicInfo userBasicInfo = infoDAO.findById(userId);
		showUserBasicInfo(userBasicInfo);
		return userBasicInfo;
	}

	/**
	 * 显示用户基本信息
	 */
	public static void showUserBasicInfo(UserBasicInfo userBasicInfo) {
		log.info("===========" + userBasicInfo.getUserId() + "==============");
		log.info("昵称:" + userBasicInfo.getNickname());
		log.info("image:" + userBasicInfo.getHeadImagePath());
		log.info("签名:" + userBasicInfo.getSign());
		log.info("学校:" + userBasicInfo.getScName());
		log.info("系别:" + userBasicInfo.getDname());
		log.info("粉丝数量:" + userBasicInfo.getFansNum());
		log.info("关注:" + userBasicInfo.getFocusNum());
		log.info("性别:" + userBasicInfo.getSex());
		log.info("=======================================");
	}
	/**
	 * 获取缩略图的字节数组
	 * @param userId
	 * @return
	 * @throws IOException 
	 */
	public static byte[] getUserSmallHeadByte(String userId) throws IOException{
		//获取缩略图路径
		String path = getUserInfoDAO().getUserHeadPath(userId);
		log.info("path:" + path);
		byte[] image = FileUtil.makeFileToByte(path);
		return image;
	}
	
	public UserInfo getUserInfo(String userId){
		UserInfo user = getUserInfoDAO().findById(userId);
		return user;
	}
	public static UserInfoDAO getUserInfoDAO(){
		return context.getBean("UserInfoDAOImpl", UserInfoDAO.class);
	}
}
