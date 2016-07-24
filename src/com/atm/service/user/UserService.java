package com.atm.service.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.SessionDAO;
import com.atm.dao.bbs.LabelAttentionAssociationDAO;
import com.atm.dao.bbs.LabelDAO;
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.daoDefined.user.UserBasicInfoDAO;
import com.atm.daoDefined.user.UserLabelViewDAO;
import com.atm.model.bbs.Label;
import com.atm.model.bbs.LabelAttentionAssociation;
import com.atm.model.define.FindUser;
import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.define.user.UserLabelView;
import com.atm.model.define.user.UserLabelViewId;
import com.atm.model.define.user.UserList;
import com.atm.model.user.Major;
import com.atm.model.user.Student;
import com.atm.model.user.Teacher;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.util.Application;
import com.atm.util.FileUtil;
import com.atm.util.JsonUtil;
import com.atm.util.ParticipleUtil;
import com.atm.util.mail.SendDemo;

public class UserService extends AttentService implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(UserService.class);
	private String message = "";

	// 2015.11.16������
	// private UserInfoDAO userInfoDAO = (UserInfoDAO) context
	// .getBean("UserInfoDAOImpl");

	public UserService() {
	}

	/**
	 * ע�ᣬ�����Ѿ���֤��
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @return json �Ƿ�ע��ɹ���json
	 */
	public String register(String json) {
		log.debug("ע��(�����Ѿ���֤)");
		// ��ȡjsonObject
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String userId = jsonObject.getString("userId");
		String mess = findById(userId);
		if (mess.equals("")) {
			// userIdδ��ʹ��
			log.info("...........userIdδ��ʹ��");
		} else {
			// userID�Ѿ���ʹ��
			log.info("...........userId�Ѿ���ʹ��");
			mess = "used";
			jsonObject.clear();
			jsonObject.put("tip", mess);
			json = JsonUtil.getJsonString(jsonObject);
			return json;
		}
		// ѧ�����ǽ�ʦ
		String flag = jsonObject.getString("flag");
		if (flag.equals("0")) {
			// ��ʦ
			mess = saveTeacher(jsonObject, userId);
			log.info("teacher-----");
		} else if (flag.equals("1")) {
			// ѧ��
			log.info("student-----");
			mess = saveStudent(jsonObject, userId);
		}

		jsonObject.clear();
		jsonObject.put("tip", mess);
		json = JsonUtil.getJsonString(jsonObject);
		return json;
	}

	/**
	 * ȡ��ע��
	 * 
	 * @param json
	 * @return
	 */
	public String exitRegister(String json) {
		log.info("ȡ��ע��");
		// ��ȡjsonObject
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String userId = jsonObject.getString("userId");
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		int i = userInfoDAO.deleteByUserId(userId);
		String tip = "failed";
		if (i == 1) {
			tip = "success";
			log.info("ɾ���ɹ�");
		}

		// ����json
		jsonObject.clear();
		jsonObject.put("tip", tip);
		json = JsonUtil.getJsonString(jsonObject);
		log.info("json--:" + json);
		return json;
	}

	/**
	 * ѧ��ע��
	 * 
	 * @param jsonObject
	 * @param userId
	 */
	public String saveStudent(JSONObject jsonObject, String userId) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		UserDAO userDAO = (UserDAO) context.getBean("UserDAOImpl");
		StudentDAO studentDAO = (StudentDAO) context.getBean("StudentDAOImpl");
		// ����
		String userPwd = jsonObject.getString("userPwd");
		// ��ȡѧУ��ϵ��רҵ
		String sName = jsonObject.getString("userSchool");
		String dName = jsonObject.getString("userDept");
		String mName = jsonObject.getString("userMajor");
		// ����
		String email = jsonObject.getString("userEmail");
		// ��ѧ���
		String enterSchoolTime = jsonObject.getString("enterSchoolTime");
		// ��ʾ
		log.info("userId:" + userId + ",userPwd:" + userPwd + ",sName:" + sName
				+ ",dName:" + dName + ",mName:" + mName + ",email:" + email
				+ ",enterSchoolTime:" + enterSchoolTime);
		User user = new User(userId, userPwd);
		UserInfo userInfo = new UserInfo(userId);
		// ��ȡѧУ��ϵ��רҵ�ı���
		List list = userInfoDAO.getSdmNO(sName, dName, mName);
		Object[] numbers = (Object[]) list.get(0);
		log.info("sno:" + numbers[0] + ",dNo:" + numbers[1] + ",mno:"
				+ numbers[2]);
		userInfo.setScNo(numbers[0] + "");
		userInfo.setDno(numbers[1] + "");
		userInfo.setMajor(new Major(numbers[2] + ""));
		userInfo.setFlag(1);
		Student student = new Student(userId, "", enterSchoolTime, email, null);
		// ����
		try {
			log.info("---------");
			userInfoDAO.save(userInfo);
			userDAO.save(user);
			studentDAO.save(student);
			log.info("����ɹ�");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

	/**
	 * ��ʦע��
	 * 
	 * @param jsonObject
	 * @param userId
	 * @return
	 */
	public String saveTeacher(JSONObject jsonObject, String userId) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		UserDAO userDAO = (UserDAO) context.getBean("UserDAOImpl");
		TeacherDAO teacherDAO = (TeacherDAO) context.getBean("TeacherDAOImpl");
		// ����
		String userPwd = jsonObject.getString("userPwd");
		// ��ȡѧУ��ϵ��רҵ
		String sName = jsonObject.getString("userSchool");
		String dName = jsonObject.getString("userDept");
		// ��ʦû�в���רҵ
		// String mName = jsonObject.getString("userMajor");
		// ����
		String email = jsonObject.getString("userEmail");
		// ��ѧ���
		String enterSchoolTime = jsonObject.getString("enterSchoolTime");
		// ��ʾ
		log.info("userId:" + userId + ",userPwd:" + userPwd + ",sName:" + sName
				+ ",dName:" + dName + ",email:" + email + ",enterSchoolTime:"
				+ enterSchoolTime);
		User user = new User(userId, userPwd);
		UserInfo userInfo = new UserInfo(userId);
		// ��ȡѧУ��ϵ��רҵ�ı���
		List list = userInfoDAO.getSdmNO(sName, dName, "");
		Object[] numbers = (Object[]) list.get(0);
		log.info("sno:" + numbers[0] + ",dNo:" + numbers[1]);
		// ����userInfo
		userInfo.setScNo(numbers[0] + "");
		userInfo.setDno(numbers[1] + "");
		userInfo.setMajor(null);
		userInfo.setFlag(1);
		Teacher teacher = new Teacher(userId, "", email, null);
		// ����
		try {
			userInfoDAO.save(userInfo);
			userDAO.save(user);
			teacherDAO.save(teacher);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

	// '�㶫����ѧԺ','��������������Ϣ����ϵ','��������'
	public String confirmUserId(HttpServletRequest request,
			HttpServletResponse response, String json) {
		log.debug("��֤�û�ʹ�õ�userId�Ƿ��Ѿ�����");
		// ��ȡ����jsonObject
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		JSONArray jsonArray = new JSONArray();
		// ��ȡ�û�userId
		String userId = jsonObject.getString("userId");
		log.debug("ע��--��֤userid--userId:" + userId);
		String mess = findById(userId);
		if (mess.equals("")) {
			// userIdδ��ʹ��
			mess = "success";
		} else {
			mess = "fail";
		}
		// ����json
		jsonObject = new JSONObject();
		jsonObject.put("tip", mess);
		jsonArray.add(jsonObject);
		json = jsonArray.toString();
		log.debug("ע��--��֤userId--json:" + json);
		return json;
	}

	/**
	 * ע���������֤
	 * 
	 * @author ye 2015.9.9
	 * @param request
	 * @param response
	 * @param json
	 * @return ���ص�json
	 */
	public String confirmRegisterEmail(String json) {
		// ��������Ƿ��Ѿ����û�ʹ�ù�
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		// ������֤��
		String captchas = "";
		// ��־
		String tip = "";
		// ����������
		String email = jsonObject.getString("userEmail");
		log.info("�����������:" + email);
		String mess = findByEmail(email);
		log.info("����ɹ�=============mess:" + mess);
		if (mess.equals("")) {
			// messΪ""����ʾ����û����ʹ�ã�������֤
			SendDemo sendDemo = new SendDemo();
			captchas = sendDemo.sendRegisterEmail(email);
			log.info("captchas:" + captchas);
			if (captchas != null && !captchas.equals("")) {
				tip = "success";
			} else {
				tip = "fail";
			}
		} else {
			tip = "used";
		}
		log.info("captchas>>>>" + captchas);
		// �������װ��JSONArray
		jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("tip", tip);
		jsonObject.put("captchas", captchas);
		jsonArray.add(jsonObject);
		json = jsonArray.toString();
		log.info("json>>>" + json);
		return json;
	}

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
	 * socket��¼��ĺ���
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String loginConfirm(HttpServletRequest request,
			HttpServletResponse response, String json) throws IOException,
			JSONException {
		String tip = "failed";
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");

		json = new com.atm.util.bbs.JsonUtil().getJSONArray(request).toString();
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		// ��ȡuserId
		String userId = jsonObject.getString("userId");
		log.info("userId:" + userId);
		// ��ȡ�û���Ϣ
		UserInfo userInfo = userInfoDAO.findById(userId);
		if (userInfo != null) {
			tip = "success";
		}
		request.getSession().setAttribute("user", userInfo);
		// ���ص�json
		jsonObject.clear();
		jsonObject.put("tip", tip);
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);
		json = jsonArray.toString();
		log.info("json:" + json);
		return json;
	}

	/**
	 * �������������ҳ
	 * 
	 * @param json
	 * @return
	 */
	public String personalCenter(String json) {
		log.info("����userService��personnalCenter");

		return json;
	}

	/**
	 * �û���֤
	 */
	public String confirmUser(String json) {
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String flag = jsonObject.getString("flag");
		System.out.println("flag:" + flag);
		int i = 0;
		String mess = "failed";
		if (flag.equals("3")) {
			log.info("�Ǳ�ҵ��,׼��������֤У��֮��");
			i = confirmGraduate(jsonObject);
		} else {
			log.info("����У����������ʦ��׼��������֤����ϵͳ");
			i = confirmTeach(jsonObject);
		}

		if (i == 1) {
			mess = "success";
		} else if (i == 2) {
			mess = "used";
		} else {
			mess = "failed";
		}
		// json
		jsonObject.clear();
		jsonObject.put("tip", mess);
		json = JsonUtil.getJsonString(jsonObject);
		log.info("json:" + json);
		return json;

	}

	/**
	 * ��֤��ҵ������ʵ���
	 * 
	 * @param jsonObject
	 * @return
	 */
	public int confirmGraduate(JSONObject jsonObject) {
		log.info("������֤У��֮��");
		String number = jsonObject.getString("Num");
		String userId = jsonObject.getString("userId");
		String username = jsonObject.getString("userName");
		String year = jsonObject.getString("enterSchoolTime");
		// У��֮����֤
		boolean tip = graduateConfirm(number, username, year);
		if (tip) {
			// ��֤�ɹ����û�����ʵ������ѧ�Ŵ�����ݿ�
			return getUserInfoDAO().saveNumAndName(userId, number, username);
		}
		return 0;
	}

	/**
	 * ��֤��У������ʦ����ʵ���
	 * 
	 * @param jsonObject
	 * @return
	 */
	public int confirmTeach(JSONObject jsonObject) {
		String flag = jsonObject.getString("flag");
		String userId = jsonObject.getString("userId");
		String number = jsonObject.getString("Num");
		String password = jsonObject.getString("Pwd");
		log.info("flag:" + flag + ",userId:" + userId + ",number:" + number
				+ ",password:" + password);
		// TODO ��֤����ϵͳ�ķ���
		// ��֤�ɹ�
		String name = "";
		if (flag.equals("0")) {
			log.info("������֤��ʦ");
			// �жϽ�ְ�����Ƿ��Ѿ���ʹ��
			if (findTeacherNumber(number)) {
				return 2;
			}

			name = userConfirm(number, password, "��ʦ");
		} else if (flag.equals("2")) {
			log.info("������֤��Уѧ��");
			// �ж�ѧ���Ƿ��Ѿ���ʹ��
			if (findStudentNumber(number)) {
				return 2;// ""
			}

			name = userConfirm(number, password, "ѧ��");
		} else {
			return 0;
		}

		if (name.equals("")) {// ������������
			return 0;
		} else {
			return getUserInfoDAO().saveNumAndName(userId, number, name);
		}
	}

	/**
	 * ���Ҹ�ѧ���Ƿ��Ѿ�����
	 * 
	 * @param number
	 * @return
	 */
	public boolean findStudentNumber(String number) {
		StudentDAO dao = (StudentDAO) context.getBean("StudentDAOImpl");
		List list = dao.findByProperty("sno", number);
		if (list.size() > 0) {
			log.info("ѧ�Ŵ���");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��֤�ý̹����Ƿ��Ѿ�����
	 * 
	 * @param number
	 * @return
	 */
	public boolean findTeacherNumber(String number) {
		TeacherDAO dao = (TeacherDAO) context.getBean("TeacherDAOImpl");
		List list = dao.findByProperty("tno", number);
		if (list.size() == 1) {
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
			User user2 = dao.findById(user.getUserId());
			log.info("userPwd:" + user.getUserPwd() + ",user2Pwd:"
					+ user2.getUserPwd());
			if (user2.getUserPwd().equals(user.getUserPwd())) {
				log.info("login success");
				log.info("-------------");
				return true;
			}
		} catch (Exception e) {
			return flag;
		}
		return flag;
	}

	/**
	 * �һ��˺�
	 */
	// TODO �һ��˺�
	public String findUserId(String json) {
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String email = jsonObject.getString("email");
		System.out.println(email + "=======================");
		String userId = findByEmail(email);
		log.info("userId:" + userId);
		// �ж������Ƿ���������ݿ���
		if (!userId.equals("")) {
			SendDemo demo = new SendDemo();
			log.info("send................:" + demo.toString()
					+ demo.getEmail());
			// ���Ͳ��ж��Ƿ��ͳɹ�
			String tip = demo.sendFindUserIdEmail(email, userId);
			log.info("tip:" + tip);
			if (tip.equals("success")) {
				System.out.println("send.........success");
				return JsonUtil.put("tip", "success");
			} else {
				return JsonUtil.put("tip", "error");
			}
		}
		return JsonUtil.put("tip", "failed");
	}

	/**
	 * ���û���������ʱ�����˺Ż�������ʱ����֤��������˺Ż������䡣����֤�Ƿ����
	 * 
	 * @author ye
	 */
	public String forgetPasswod(String json) {
		// ��ȡ������˺Ż��������ַ���
		// String idOrEmail = (String)request.getAttribute("idOrEmail");
		// id or email isExits?
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String idOrEmail = jsonObject.getString("idOrEmail");
		Map<String, String> map = new HashMap<String, String>();
		SendDemo demo = new SendDemo();
		String flag;
		String userId = "";
		System.out.println(idOrEmail + "===========");
		if (idOrEmail.split("@").length >= 2) {
			// ��֤�����Ƿ���ڣ�������flagΪ���䣬���Ϊ��
			userId = findByEmail(idOrEmail);
			if (!userId.equals("")) {
				flag = idOrEmail;
			} else {
				flag = "";
			}
		} else {
			flag = findById(idOrEmail);
			userId = idOrEmail;
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
				map.put("userId", userId);
				return JsonUtil.mapToJson(map);
			} else {
				map.put("tip", "failed");
				return JsonUtil.mapToJson(map);
			}
		}
		map.put("tip", "unRegister");
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
		log.info("json:" + json);
		UserDAO dao = context.getBean("UserDAOImpl", UserDAO.class);
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String userId = jsonObject.getString("userId");
		String password = jsonObject.getString("password");
		User user = new User(userId, password);
		try {
			dao.updateByUser(user);
			log.debug("success");
			return JsonUtil.put("tip", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.put("tip", "failed");
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
		log.info(email + "==================");
		StudentDAO dao = (StudentDAO) context.getBean("StudentDAOImpl");
		List list = new ArrayList();
		try {
			list = dao.findByEmail(email);
		} catch (Exception e) {
			log.info("findByEmail : ѧ����û�и�����");
		}
		String userId = null;
		if (list.size() > 0) {
			Student student = (Student) list.get(0);
			userId = student.getUserId();
			log.info("=====================" + userId + "===================");
			return userId;
		} else {
			log.info("teacher--------------");
			TeacherDAO teacherDao = (TeacherDAO) context
					.getBean("TeacherDAOImpl");
			try {
				list = teacherDao.findByEmail(email);
			} catch (Exception e) {
				log.info("findByEmail : ��ʦ��û�и�����");
				return "";
			}
			if (list.size() > 0) {
				Teacher teacher = (Teacher) list.get(0);
				userId = teacher.getUserId();
				log.info("=====================" + userId
						+ "===================");
				return userId;
			}
		}
		log.info("���䲻����----------------------");
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
		if (flag == 1) {
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
	 * 
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public byte[] getUserSmallHeadByte(String userId) throws IOException {
		// ��ȡ����ͼ·��
		String path = getUserInfoDAO().getUserHeadPath(userId);
		path = this.getClass().getResource("/").getPath().substring(1)
				.replaceFirst("WEB-INF/classes/", "")
				+ path;
		log.info("path:" + path);
		byte[] image = FileUtil.makeFileToByte(path);
		return image;
	}

	public UserInfo getUserInfo(String userId) {
		UserInfo user = getUserInfoDAO().findById(userId);
		return user;
	}

	public static UserInfoDAO getUserInfoDAO() {
		return context.getBean("UserInfoDAOImpl", UserInfoDAO.class);
	}

	/**
	 * ��֤��ҵ����У��֮��
	 * 
	 * @param number
	 * @param keyword
	 * @param year
	 * @return
	 */
	public boolean graduateConfirm(String number, String keyword, String year) {
		log.info("������֤У��֮��");
		Response res = null;
		// Map<String, String> data = new HashMap<String, String>();
		// data.put("", "");
		// data.put("",
		// "");
		// data.put("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// data.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		// data.put("Accept-Encoding", "gzip, deflate");
		// data.put("Referer", "http://jzzg.gduf.edu.cn/xyml_1.asp");
		// data.put("Connection", "keep-alive");
		// data.put("Content-Type", "application/x-www-form-urlencoded");
		try {
			log.info("����У��֮��" + "���ò���");
			res = Jsoup
					.connect(
							"http://jzzg.gduf.edu.cn/xyml_1.asp?"
									+ "select1=1&imageField.x=27&imageField.y=19&years=+"
									+ year + "&keyword=" + keyword)
					// 2015.11.16�������ӵ�һ�²���
					// .header("Host", "jzzg.gduf.edu.cn")
					// .header("User-Agent",
					// "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					// .header("", "")
					.method(Method.POST).execute();
		} catch (IOException e) {
			log.info("����У��֮��" + "���ò���ʧ��");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			log.info("���� document �ķ���");
			Document doc = res.parse();
			if (doc != null) {
				log.info("��ʼ------------------------");
				// ��ȡa��ǩ
				Elements elements = doc.getElementsByTag("a");
				log.info("a:" + elements.size());
				log.info("�����жϡ���������");
				for (int i = 0; i < elements.size(); i++) {
					log.info("����de xunhuan�ж�");
				}

				// ��Ϊ�õ�����
				for (int i = 0; i < elements.size(); i++) {

					log.info("�����ж�");

					Element a = elements.get(i);
					log.info("get info");
					// }
					// for (Element a : elements) {log
					// a��textΪkeyword���������ϸҳ��
					String aText = a.text();
					aText = aText.replaceAll(" ", "");
					System.out.println("aText:" + aText.trim() + ",length:"
							+ aText.length());
					if (aText.equals(keyword)) {
						log.info("hhhh");
						String text = a.attr("onclick");
						log.info("text:" + text);
						String[] strs = text.split("'");
						log.info(strs[1]);
						// ���ӵ���ϸҳ��
						res = getConnection(
								"http://jzzg.gduf.edu.cn/" + strs[1]).execute();
						doc = res.parse();
						Elements tds = doc.getElementsByTag("td");
						for (Element td : tds) {
							// System.out.println("tds:" + td.text());
							if (td.text().equals("������")) {
								log.info("----------");
								Element element = td.nextElementSibling();
								log.info("����:" + element.text());
								String[] t = element.text().split("��");
								if (t[1].equals(number + ")")) {
									log.info("true");
									return true;
								}
							}
						}
					}
				}
				// http://jzzg.gduf.edu.cn/news_manage/xiaoyou
				// /student_show.asp?studentid=205809
				log.info("����------------------------");
			} else {
				log.info("��ȡʧ��");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ����ϵͳ��֤
	 * 
	 * @param number
	 * @param password
	 * @return
	 */
	public String userConfirm(String number, String password, String flag) {
		log.info("number:" + number + ",pasword:" + password + ",flag:" + flag);
		Connection connection = getConnection("http://jwc.gduf.edu.cn/")
				.header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
				.header("Host", "jwc.gduf.edu.cn")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("Accept-Language",
						"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
				.header("Accept-Encoding", "gzip, deflate")
				.header("Referer", "http://www.gduf.edu.cn/")
				.header("Connection", "Keep-Alive")
				.header("Cache-Control", "max-age=0").timeout(3000)
				.method(Method.GET);
		Response response = null;
		try {
			log.info("���ӽ���ϵͳ��ҳ");
			response = connection.execute();// ��ȡ��Ӧ
			log.info("��ȡdocument");
			Document document = Jsoup.parse(response.body());// ת��ΪDom��
			log.info("title:" + document.title() + ",body:" + document.data());
			log.info("body:" + document.body());
			Map<String, String> map = response.cookies();
			for (String s : map.keySet()) {
				System.out.println(s + "      " + map.get(s));
			}
			// ����
			Map<String, String> params = new HashMap<String, String>();
			// String __VIEWSTATE = "";
			/*
			 * Element e = document.getElementById("form1");//.child(0);
			 * log.info("element-Tag:" + e.tagName()); log.info("element-text:"
			 * + e.text());
			 */
			// dDwyODE2NTM0OTg7Oz4faMm4vKVufBJeqp0/4YQv7YksRA==
			String __VIEWSTATE = "dDwyODE2NTM0OTg7Oz4faMm4vKVufBJeqp0/4YQv7YksRA==";
			params.put("txtUserName", number);
			params.put("TextBox2", password);
			params.put("__VIEWSTATE", __VIEWSTATE);
			params.put("txtSecretCode", "");
			params.put("RadioButtonList1", flag);
			params.put("Button1", "��¼");
			log.info("���ò������");
			// header
			String referer = "http://jwc.gduf.edu.cn/xs_main.aspx?xh=" + number;
			log.info("׼���ٴ�����");
			Connection connection2 = getConnection("http://jwc.gduf.edu.cn/");
			// ����cookie��post�����map����
			log.info("response");
			Response login = null;
			log.info("---");
			try {
				log.info("�������ӵ�¼����ϵͳ");
				connection2 = connection2
						.ignoreContentType(true)
						.data(params)
						/*
						 * .userAgent(
						 * "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0"
						 * )
						 */
						.header("Host", "jwc.gduf.edu.cn")
						.header("Accept",
								"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
						.header("Accept-Language",
								"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
						.header("Accept-Encoding", "gzip, deflate")
						// .header("referer", referer)
						.header("Connection", "Keep-Alive")
						.header("Cache-Control", "max-age=0").cookies(map)
						.timeout(3000).method(Method.POST);
				log.info("connect-------");
				login = connection2.execute();
				log.info("���ӳɹ�");
			} catch (Exception e1) {
				log.info("����ʧ��");
				e1.printStackTrace();
				throw e1;
			}
			log.info("��ȡ�ɹ�");
			// ��ӡ����½�ɹ������Ϣ
			// System.out.println(login.body());
			System.out.println("-----------------------------");
			// ��½�ɹ����cookie��Ϣ�����Ա��浽���أ��Ժ��½ʱ��ֻ��һ�ε�½����
			map = login.cookies();
			for (String s : map.keySet()) {
				System.out.println(s + "      " + map.get(s));
			}
			Document document2 = login.parse();
			log.info("title:" + document.title());
			Element element = document2.getElementById("xhxm");
			if (element != null && element.tagName().equals("span")) {
				// �Ƿ������Ԫ��
				System.out.println("success-tag:" + element.tagName());
				String name = element.text();
				name = name.substring(0, name.length() - 2);
				System.out.println(name);
				return name;
			} else {
				System.out.println("error");
				return "";
			}
		} catch (Exception e) {
			log.info("error--");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @param url
	 * @return
	 */
	public Connection getConnection(String url) {
		// ��ȡ����
		Connection connection = Jsoup.connect(url);
		// ����ģ�������
		connection
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
		return connection;
	}

	public boolean lookNickname(String nickname) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		List userInfos = userInfoDAO.findByNickname(nickname);
		if (userInfos.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * *********************************************************** ��ѯ�û����Ż�����
	 */

	public void findUserB(String keyword) {
		// ��ȡ�����е��û���ǩ���
		LabelAttentionAssociationDAO labelaDAO = (LabelAttentionAssociationDAO) context
				.getBean("LabelAttentionAssociationDAOImpl");
		LabelDAO labelDAO = (LabelDAO) context.getBean("LabelDAOImpl");
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		// ��Label��ӳ���map
		List<Label> labels = labelDAO.findAll();
		List<LabelAttentionAssociation> userLabels = labelaDAO.findAll();
		List<UserInfo> userInfos = userInfoDAO.findAll();

		Map<Integer, String> labelMap = new HashMap<Integer, String>();
		Map<String, FindUser> userLabelMap = new HashMap<String, FindUser>();
		Map<String, UserInfo> userInfoMap = new HashMap<String, UserInfo>();
		labelMap = labelToMap(labels);
		userLabelMap = userLabelToMap(userInfos, userLabels, labelMap);
		userInfoMap = userInfoToMap(userInfos);
		log.info(">>>>>>>>>>>>>>>");
		for (String userId : userLabelMap.keySet()) {
			log.info("userId:" + userId + ",userInfoString:"
					+ userLabelMap.get(userId));
		}

		if (keyword == null && keyword.equals(" ")) {
			return;
		}
		log.info("---------keywords�ķִ�------------");
		log.info("ԭ�ַ���:" + keyword);
		//keyword = keyword.replaceAll(" ", "");
		// ���ж�keyword�ķִʴ���
		/*
		 * ����Ӣ�ĺ�������˵,Ӧ�úϲ������� word64findӦ���Ϊ word,64,find. word find my64 -->
		 * word,find,my,64
		 */

		// String[] keys = new String[keyword.length() - 1];
		// for (int i = 0; i < keys.length; i++) {
		// keys[i] = keyword.substring(i, i + 2);
		// log.info(i + " : " + keys[i]);
		// }

		// a��ƥ������,Ȼ�����b��ƥ��,b��ƥ���Ǹ��ӷ�
		// ���ȶ��û��˺ţ�nickname�ķִ�ƥ��

		// ��keyword�ܷ�ת�������֣�����,���˺Ž���ƥ��
		String[] keys;
		try {
			int keywordInt = Integer.parseInt(keyword);
			keys = new String[1];
			keys[0] = keywordInt + "";
		} catch (Exception e) {
			keys = ParticipleUtil.participleString(keyword);
		}
		// �ִ����,��ʼ����ƥ��
		String infoString = "";
		FindUser findUser = null;
		// ƥ����
		int matchCount = 0;
		// ƥ��ɹ����û�
		List<UserInfo> matchUsers = new ArrayList<UserInfo>();
		Map<String, Integer> matchCounts = new HashMap<String, Integer>();
		log.info("-------------ƥ�����:");
		for (String userId : userLabelMap.keySet()) {
			// 1.����Ϣ���ϳ�һ���ַ���
			infoString = userLabelMap.get(userId).toInfoString();
			// ƥ���

			for (int i = 0; i < keys.length; i++) {
				String[] matches = infoString.split(keys[i]);
				matchCount += matches.length - 1;
			}

			if (matchCount > 0) {
				matchUsers.add(userInfoMap.get(userId));
				matchCounts.put(userId, matchCount);
				log.info("userId:" + userId + ",matchCount:" + matchCount);
			}
			matchCount = 0;
		}
	}

	/**
	 * ��label���mapӳ��ṹ
	 * 
	 * @param labels
	 * @return
	 */
	public Map<Integer, String> labelToMap(List<Label> labels) {
		Map<Integer, String> labelMap = new HashMap<Integer, String>();
		for (Label label : labels) {
			labelMap.put(label.getLabId(), label.getLabName());
		}
		return labelMap;
	}

	/**
	 * ����labelӳ���Լ�userLabel��Ĺ�ϵ,������һ��map,keyΪuserID,valueΪ�û��ɱ����������������������ַ�����
	 * 
	 * @param userInfos
	 * @param userLabels
	 * @param labelMap
	 * @return
	 */
	public Map<String, FindUser> userLabelToMap(List<UserInfo> userInfos,
			List<LabelAttentionAssociation> userLabels,
			Map<Integer, String> labelMap) {
		Map<String, FindUser> userLabelMap = new HashMap<String, FindUser>();
		// ʵ����һ��FindUser����
		FindUser findUser = new FindUser();
		for (UserInfo userInfo : userInfos) {
			userLabelMap.put(userInfo.getUserId(),
					new FindUser(userInfo.getUserId(), userInfo.getNickname()));
		}
		/**
		 * ����ǩ��һ���ؽ�ȥ
		 */
		for (LabelAttentionAssociation userLabel : userLabels) {
			findUser = userLabelMap.get(userLabel.getUserId());
			findUser.getLabels().add(labelMap.get(userLabel.getLabId()));
			userLabelMap.put(userLabel.getUserId(), findUser);
		}
		return userLabelMap;
	}

	public Map<String, UserInfo> userInfoToMap(List<UserInfo> userInfos) {
		Map<String, UserInfo> userInfoMap = new HashMap<String, UserInfo>();

		for (int i = 0; i < userInfos.size(); i++) {
			userInfoMap.put(userInfos.get(i).getUserId(), userInfos.get(i));
		}
		return userInfoMap;
	}

}