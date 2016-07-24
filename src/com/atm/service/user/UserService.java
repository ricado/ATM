package com.atm.service.user;

import java.io.IOException;
import java.util.HashMap;
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
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.daoDefined.user.UserBasicInfoDAO;
import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.define.user.UserList;
import com.atm.model.user.Major;
import com.atm.model.user.Student;
import com.atm.model.user.Teacher;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.util.Application;
import com.atm.util.FileUtil;
import com.atm.util.JsonUtil;
import com.atm.util.mail.SendDemo;

public class UserService extends AttentService implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(UserService.class);

	public UserService() {
	}

	/**
	 * 注册，邮箱已经验证好
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @return json 是否注册成功的json
	 */
	public String register(String json) {
		log.debug("注册(邮箱已经验证)");
		// 获取jsonObject
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String userId = jsonObject.getString("userId");
		String mess = findById(userId);
		if (mess.equals("")) {
			// userId未被使用
			log.info("...........userId未被使用");
		} else {
			// userID已经被使用
			log.info("...........userId已经被使用");
			mess = "used";
			jsonObject.clear();
			jsonObject.put("tip", mess);
			json = JsonUtil.getJsonString(jsonObject);
			return json;
		}
		// 学生还是教师
		String flag = jsonObject.getString("flag");
		if (flag.equals("0")) {
			// 教师
			mess = saveTeacher(jsonObject, userId);
			log.info("teacher-----");
		} else if (flag.equals("1")) {
			// 学生
			log.info("student-----");
			mess = saveStudent(jsonObject, userId);
		}

		jsonObject.clear();
		jsonObject.put("tip", mess);
		json = JsonUtil.getJsonString(jsonObject);
		return json;
	}

	/**
	 * 取消注册
	 * 
	 * @param json
	 * @return
	 */
	public String exitRegister(String json) {
		log.info("取消注册");
		// 获取jsonObject
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String userId = jsonObject.getString("userId");
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		int i = userInfoDAO.deleteByUserId(userId);
		String tip = "failed";
		if (i == 1) {
			tip = "success";
			log.info("删除成功");
		}

		// 返回json
		jsonObject.clear();
		jsonObject.put("tip", tip);
		json = JsonUtil.getJsonString(jsonObject);
		log.info("json--:" + json);
		return json;
	}

	/**
	 * 学生注册
	 * 
	 * @param jsonObject
	 * @param userId
	 */
	public String saveStudent(JSONObject jsonObject, String userId) {
		UserInfoDAO userInfoDAO = (UserInfoDAO) context
				.getBean("UserInfoDAOImpl");
		UserDAO userDAO = (UserDAO) context.getBean("UserDAOImpl");
		StudentDAO studentDAO = (StudentDAO) context.getBean("StudentDAOImpl");
		// 密码
		String userPwd = jsonObject.getString("userPwd");
		// 获取学校，系别，专业
		String sName = jsonObject.getString("userSchool");
		String dName = jsonObject.getString("userDept");
		String mName = jsonObject.getString("userMajor");
		// 邮箱
		String email = jsonObject.getString("userEmail");
		// 入学年份
		String enterSchoolTime = jsonObject.getString("enterSchoolTime");
		// 显示
		log.info("userId:" + userId + ",userPwd:" + userPwd + ",sName:" + sName
				+ ",dName:" + dName + ",mName:" + mName + ",email:" + email
				+ ",enterSchoolTime:" + enterSchoolTime);
		User user = new User(userId, userPwd);
		UserInfo userInfo = new UserInfo(userId);
		// 获取学校，系别，专业的编码
		List list = userInfoDAO.getSdmNO(sName, dName, mName);
		Object[] numbers = (Object[]) list.get(0);
		log.info("sno:" + numbers[0] + ",dNo:" + numbers[1] + ",mno:"
				+ numbers[2]);
		userInfo.setScNo(numbers[0] + "");
		userInfo.setDno(numbers[1] + "");
		userInfo.setMajor(new Major(numbers[2] + ""));
		userInfo.setFlag(1);
		Student student = new Student(userId, "", enterSchoolTime, email, null);
		// 保存
		try {
			userInfoDAO.save(userInfo);
			userDAO.save(user);
			studentDAO.save(student);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

	/**
	 * 老师注册
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
		// 密码
		String userPwd = jsonObject.getString("userPwd");
		// 获取学校，系别，专业
		String sName = jsonObject.getString("userSchool");
		String dName = jsonObject.getString("userDept");
		// 老师没有不用专业
		// String mName = jsonObject.getString("userMajor");
		// 邮箱
		String email = jsonObject.getString("userEmail");
		// 入学年份
		String enterSchoolTime = jsonObject.getString("enterSchoolTime");
		// 显示
		log.info("userId:" + userId + ",userPwd:" + userPwd + ",sName:" + sName
				+ ",dName:" + dName + ",email:" + email + ",enterSchoolTime:"
				+ enterSchoolTime);
		User user = new User(userId, userPwd);
		UserInfo userInfo = new UserInfo(userId);
		// 获取学校，系别，专业的编码
		List list = userInfoDAO.getSdmNO(sName, dName, "");
		Object[] numbers = (Object[]) list.get(0);
		log.info("sno:" + numbers[0] + ",dNo:" + numbers[1]);
		// 设置userInfo
		userInfo.setScNo(numbers[0] + "");
		userInfo.setDno(numbers[1] + "");
		userInfo.setMajor(null);
		userInfo.setFlag(1);
		Teacher teacher = new Teacher(userId, "", email, null);
		// 保存
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

	// '广东金融学院','互联网金融与信息工程系','电子商务'
	public String confirmUserId(HttpServletRequest request,
			HttpServletResponse response, String json) {
		log.debug("验证用户使用的userId是否已经存在");
		// 获取接送jsonObject
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		JSONArray jsonArray = new JSONArray();
		// 获取用户userId
		String userId = jsonObject.getString("userId");
		log.debug("注册--验证userid--userId:" + userId);
		String mess = findById(userId);
		if (mess.equals("")) {
			// userId未被使用
			mess = "success";
		} else {
			mess = "fail";
		}
		// 返回json
		jsonObject = new JSONObject();
		jsonObject.put("tip", mess);
		jsonArray.add(jsonObject);
		json = jsonArray.toString();
		log.debug("注册--验证userId--json:" + json);
		return json;
	}

	/**
	 * 注册邮箱的验证
	 * 
	 * @author ye 2015.9.9
	 * @param request
	 * @param response
	 * @param json
	 * @return 返回的json
	 */
	public String confirmRegisterEmail(String json) {
		// 检查邮箱是否已经被用户使用过
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		// 邮箱验证码
		String captchas = "";
		// 标志
		String tip = "";
		// 搜索出邮箱
		String email = jsonObject.getString("userEmail");
		log.info("请求的邮箱是:" + email);
		String mess = findByEmail(email);
		log.info("请求成功=============mess:" + mess);
		if (mess.equals("")) {
			// mess为""，表示邮箱没有人使用，可以验证
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
		// 将结果封装成JSONArray
		jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("tip", tip);
		jsonObject.put("captchas", captchas);
		jsonArray.add(jsonObject);
		json = jsonArray.toString();
		log.info("json>>>" + json);
		return json;
	}

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
	 * socket登录后的后续
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
		// 获取userId
		String userId = jsonObject.getString("userId");
		log.info("userId:" + userId);
		// 获取用户信息
		UserInfo userInfo = userInfoDAO.findById(userId);
		if (userInfo != null) {
			tip = "success";
		}
		request.getSession().setAttribute("user", userInfo);
		// 返回的json
		jsonObject.clear();
		jsonObject.put("tip", tip);
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);
		json = jsonArray.toString();
		log.info("json:" + json);
		return json;
	}

	/**
	 * 用户认证
	 */
	public String confirmUser(String json) {
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String flag = jsonObject.getString("flag");
		int i = 0;
		String mess = "failed";
		if (flag.equals("3")) {
			i = confirmGraduate(jsonObject);
		} else {
			i = confirmTeach(jsonObject);
		}

		if (i == 1) {
			mess = "success";
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
	 * 验证毕业生的真实身份
	 * 
	 * @param jsonObject
	 * @return
	 */
	public int confirmGraduate(JSONObject jsonObject) {
		String number = jsonObject.getString("Num");
		String userId = jsonObject.getString("userId");
		String username = jsonObject.getString("userName");
		String year = jsonObject.getString("enterSchoolTime");
		//校友之窗验证
		boolean tip = graduateConfirm(number, username, year);
		if(tip){
			//验证成功，用户的真实姓名和学号存进数据库
			return getUserInfoDAO().saveNumAndName(userId, number, username);
		}
		return 0;
	}

	/**
	 * 验证在校生与老师的真实身份
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
		// TODO 验证教务系统的方法
		// 验证成功
		String name = "";
		if (flag.equals("0")) {
			log.info("进入验证教师");
			name = userConfirm(number, password, "教师");
		} else if (flag.equals("2")) {
			log.info("进入验证在校学生");
			name = userConfirm(number, password, "学生");
		} else {
			return 0;
		}
		
		if(name.equals("")){
			return 0;
		}else{
			return getUserInfoDAO().saveNumAndName(userId, number, name);
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
			log.info("-------------");
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}

	/**
	 * 找回账号
	 */
	// TODO 找回账号
	public String findUserId(String json) {
		String email = JSONObject.fromObject(json).getString("email");
		System.out.println(email + "=======================");
		String userId = findByEmail(email);
		//判断邮箱是否存在在数据库中
		if (!userId.equals("")) {
			SendDemo demo = new SendDemo();
			System.out.println("send................");
			// 发送并判断是否发送成功
			if (demo.sendFindUserIdEmail(email, userId).equals("success")) {
				System.out.println("send.........success");
				return JsonUtil.put("tip", "success");
			}
		}
		return JsonUtil.put("tip", "failed");
	}

	/**
	 * 当用户忘记密码时输入账号或者邮箱时，验证输入的是账号还是邮箱。并验证是否存在
	 * 
	 * @author ye
	 */
	public String forgetPasswod(String json) {
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
			log.info("teacher--------------");
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
		log.info("邮箱可用----------------------");
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
	 * 
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public static byte[] getUserSmallHeadByte(String userId) throws IOException {
		// 获取缩略图路径
		String path = getUserInfoDAO().getUserHeadPath(userId);
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
	 * 验证毕业生的校友之窗
	 * @param number
	 * @param keyword
	 * @param year
	 * @return
	 */
	public boolean graduateConfirm(String number, String keyword, String year) {
		Response res = null;
		try {
			res = Jsoup
					.connect(
							"http://jzzg.gduf.edu.cn/xyml_1.asp?"
									+ "select1=1&imageField.x=27&imageField.y=19&years=+"
									+ year + "&keyword=" + keyword)
					.method(Method.POST).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Document doc = res.parse();
			if (doc != null) {
				System.out.println("开始------------------------");
				// 获取a标签
				Elements elements = doc.getElementsByTag("a");
				for (Element a : elements) {
					// a的text为keyword。则进入详细页面
					if (a.text().equals(keyword)) {
						System.out.println("hhhh");
						String text = a.attr("onclick");
						System.out.println(text);
						String[] strs = text.split("'");
						System.out.println(strs[1]);
						// 连接到详细页面
						res = getConnection(
								"http://jzzg.gduf.edu.cn/" + strs[1]).execute();
						doc = res.parse();
						Elements tds = doc.getElementsByTag("td");
						for (Element td : tds) {
							// System.out.println("tds:" + td.text());
							if (td.text().equals("姓名：")) {
								System.out.println("----------");
								Element element = td.nextElementSibling();
								System.out.println("姓名:" + element.text());
								String[] t = element.text().split("：");
								if (t[1].equals(number + ")")) {
									System.out.println("true");
									return true;
								}
							}
						}
					}
				}
				// http://jzzg.gduf.edu.cn/news_manage/xiaoyou
				// /student_show.asp?studentid=205809
				System.out.println("结束------------------------");
			} else {
				System.out.println("获取失败");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 教务系统验证
	 * 
	 * @param number
	 * @param password
	 * @return
	 */
	public String userConfirm(String number, String password, String flag) {
		log.info("number:" + number + ",pasword:" + password + ",flag:" + flag);
		Connection connection = getConnection("http://jwc.gduf.edu.cn/")
				.header("Host", "jwc.gduf.edu.cn")
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
				.header("Accept-Encoding", "gzip, deflate")
				.header("referer", "http://jwc.gduf.edu.cn/")
				.header("Connection", "Keep-Alive")
				.header("Cache-Control", "max-age=0");
		Response response = null;
		try {
			log.info("连接教务系统首页");
			response = connection.execute();// 获取响应
			log.info("获取document");
			Document document = Jsoup.parse(response.body());// 转换为Dom树
			log.info("title:" + document.title() + ",body:" + document.data());
			Map<String, String> map = response.cookies();
			for (String s : map.keySet()) {
				System.out.println(s + "      " + map.get(s));
			}
			// 参数
			Map<String, String> params = new HashMap<String, String>();
			String __VIEWSTATE = "dDwyODE2NTM0OTg7Oz4faMm4vKVufBJeqp0/4YQv7YksRA==";
			params.put("txtUserName", number);
			params.put("TextBox2", password);
			params.put("__VIEWSTATE", __VIEWSTATE);
			params.put("txtSecretCode", "");
			params.put("RadioButtonList1", flag);
			params.put("Button1", "登录");
			log.info("设置参数完毕");
			// header
			String referer = "http://jwc.gduf.edu.cn/xs_main.aspx?xh="
					+ number;
			log.info("准备再次连接");
			Connection connection2 = getConnection("http://jwc.gduf.edu.cn/");
			// 设置cookie和post上面的map数据
			log.info("response");
			Response login=null;
			log.info("---");
			try{
				log.info("进入连接登录教务系统");
				connection2 = connection2
						.ignoreContentType(true)
						.data(params)
						/*.userAgent(
								"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0")*/
						.header("Host", "jwc.gduf.edu.cn")
						.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
						.header("Accept-Encoding", "gzip, deflate")
						//.header("referer", referer)
						.header("Connection", "Keep-Alive")
						.header("Cache-Control", "max-age=0")
						.cookies(map)
						.timeout(3000)
						.method(Method.POST);
				log.info("connect-------");
				login=connection2.execute();
				log.info("连接成功");
			}catch(Exception e){
				log.info("连接失败");
				e.printStackTrace();
				throw e;
			}
			log.info("获取成功");
			// 打印，登陆成功后的信息
			//System.out.println(login.body());
			System.out.println("-----------------------------");
			// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
			map = login.cookies();
			for (String s : map.keySet()) {
				System.out.println(s + "      " + map.get(s));
			}
			Document document2 = login.parse();
			log.info("title:" + document.title());
			Element element = document2.getElementById("xhxm");
			if (element != null&&element.tagName().equals("span")) {
				//是否有这个元素
				System.out.println("success-tag:" + element.tagName());
				String name = element.text();
				name = name.substring(0, name.length()-2);
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
	 * 获取连接
	 * 
	 * @param url
	 * @return
	 */
	public Connection getConnection(String url) {
		// 获取连接
		Connection connection = Jsoup.connect(url);
		// 配置模拟浏览器
		connection
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
		return connection;
	}
}
