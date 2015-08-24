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

	// �û���¼
	public boolean login(HttpServletRequest request,
			HttpServletResponse response, String json) {
		log.info("��¼");
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
	 * �ж��û��Ƿ���ڡ�
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
	 * �һ��˺�
	 */
	// TODO �һ��˺�
	public String findUserId(HttpServletRequest request,
			HttpServletResponse response, String json) {
		String email = JSONObject.fromObject(json).getString("email");
		System.out.println(email + "=======================");
		String userId = findByEmail(email);
		if (!userId.equals("")) {
			SendDemo demo = new SendDemo();
			System.out.println("send................");
			// ���Ͳ��ж��Ƿ��ͳɹ�
			if (demo.sendFindUserIdEmail(email, userId).equals("success")) {
				System.out.println("send.........success");
			}
		}
		return userId;
	}

	/**
	 * ���û���������ʱ�����˺Ż�������ʱ����֤��������˺Ż������䡣����֤�Ƿ����
	 * 
	 * @author ye
	 */
	public String forgetPasswod(HttpServletRequest request,
			HttpServletResponse response, String json) {
		// ��ȡ������˺Ż��������ַ���
		// String idOrEmail = (String)request.getAttribute("idOrEmail");
		// id or email isExits?
		String idOrEmail = JsonUtil.getString("idOrEmail", json);
		Map<String, String> map = new HashMap<String, String>();
		SendDemo demo = new SendDemo();
		String flag;
		String confirm;
		System.out.println(idOrEmail + "===========");
		if (idOrEmail.split("@").length >= 2) {
			// ��֤�����Ƿ���ڣ�������flagΪ���䣬���Ϊ��
			if (!findByEmail(idOrEmail).equals("")) {
				flag = idOrEmail;
			} else {
				flag = "";
			}
		} else {
			flag = findById(idOrEmail);
		}
		if (!flag.equals("")) {
			// ��������˺���ȷ����������
			// ��������
			String captchas = demo.sendChangePasswordEmail(flag);
			// �������䲢��֤�Ƿ�ɹ�
			if (demo.send().equals("success")) {
				// �ɹ����óɹ���json
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
	 * ��֤�˺��Ƿ������ݿ���
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
	 * �ı�����
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
	 * ���û���������ʱ�����������ʱ����֤�����Ƿ������ݿ���
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
	 * ͨ��UserId���ҳ��û���Email
	 * 
	 * @param userId
	 * @return
	 */
	// TODO ͨ��UserId���ҳ��û���Email
	public String findEmailById(String userId) {
		// �õ�UserInfoDAOͨ��UserId���ҳ����û������
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
	 * ͨ���ʻ�ƥ�䣬���ҳ������������û��������б��json
	 * 
	 * @param string
	 *            Ҫƥ��Ĵʻ�
	 * @return
	 */
	public List<UserList> findUser(String string, int first, int max) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		log.info("������ص��û�");
		List<UserList> userLists = userInfoDAO.findUser(string, first, max);
		log.info("==========" + string + "����û�===========");
		showUserList(userLists);
		return userLists;
	}

	public List<UserList> findInterestingUser(String userId, int first, int max) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		log.info("������ص��û�");
		List<UserList> userLists = userInfoDAO.findinterestingUser(userId,
				first, max);
		log.info("==========" + userId + "��ص��û�===========");
		showUserList(userLists);
		return userLists;
	}
	/**
	 * �û��˳� ��������ʱ��
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
	 * ��ȡ�û���������Ϣ
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
	 * ��ʾ�û�������Ϣ
	 */
	public static void showUserBasicInfo(UserBasicInfo userBasicInfo) {
		log.info("===========" + userBasicInfo.getUserId() + "==============");
		log.info("�ǳ�:" + userBasicInfo.getNickname());
		log.info("image:" + userBasicInfo.getHeadImagePath());
		log.info("ǩ��:" + userBasicInfo.getSign());
		log.info("ѧУ:" + userBasicInfo.getScName());
		log.info("ϵ��:" + userBasicInfo.getDname());
		log.info("��˿����:" + userBasicInfo.getFansNum());
		log.info("��ע:" + userBasicInfo.getFocusNum());
		log.info("�Ա�:" + userBasicInfo.getSex());
		log.info("=======================================");
	}
	/**
	 * ��ȡ����ͼ���ֽ�����
	 * @param userId
	 * @return
	 * @throws IOException 
	 */
	public static byte[] getUserSmallHeadByte(String userId) throws IOException{
		//��ȡ����ͼ·��
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
