package com.atm.service.bbs;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.daoDefined.bbs.LabelRelationViewDAO;
import com.atm.daoDefined.bbs.PeopleRelationViewDAO;
import com.atm.model.user.Student;
import com.atm.model.user.Teacher;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.util.bbs.ObjectInterface;

public class UserDeal implements ObjectInterface{
	
	private Logger log = Logger.getLogger(UserDeal.class);
	/**
	 * @用户注册处理 负责数据持久化，返回反馈信息
	 * @date 2015/7/26
	 * @author Jiong
	 * @param request
	 * @param response
	 * @return
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public String saveRegister(HttpServletRequest request) throws IOException, JSONException{
		String mess = "注册失败";
		//TODO ****************数据操作对象*************************
		//用户信息表
		UserInfoDAO userInfoDao = (UserInfoDAO)context.getBean("UserInfoDAOImpl");
		//用户登陆表
		UserDAO userDao = (UserDAO) context.getBean("UserDAOImpl");
		//用户角色表
		TeacherDAO teacherDao;
		StudentDAO studentDao;
		
		//TODO ********将用户请求以jsonArray格式取出
		JSONArray jsonArray = jsonUtil.getJSONArray(request);
		
		if(jsonArray.length()!=0){
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			//TODO ***********将JSONObject对象转换为实体对象(可能抛出两种错误)***************
			//用户信息对象
			UserInfo userInfo = 
					(UserInfo) jsonUtil.JSONToObj(jsonObject.toString(), UserInfo.class);
			//用户登陆对象
			User user = 
					(User) jsonUtil.JSONToObj(jsonObject.toString(), User.class);
			//用户角色对象（二选一）
			Teacher teacher = null;
			Student student = null;
			
			//此处为用户注册信息格式验证，未完善，可套用客户端的模版
			if(!jsonUtil.registerCheck(jsonObject)){
				mess = "用户信息不规范";
			}else if(userInfoDao.findById(userInfo.getUserId())!=null){
				mess = "用户已被注册";
			}else{
				//TODO 注册用户数据持久化
				userInfoDao.save(userInfo);
				log.debug(">>>>>>保存userInfo");
					
				userDao.save(user);
				log.debug(">>>>>>>>>保存user");
				
				if(userInfo.getFlag()==null){
					log.error(userInfo.getUserId()+":身份信息获取失败");
					return "未获取到身份信息";
				}else if(userInfo.getFlag()==0){
					teacher = (Teacher) jsonUtil.JSONToObj(jsonObject.toString(), Teacher.class);
					teacherDao = (TeacherDAO) context.getBean("TeacherDAO");
					teacherDao.save(teacher);
					log.debug(">>>>>>>保存teacher");
				}else if(userInfo.getFlag()==1){
					student = (Student) jsonUtil.JSONToObj(jsonObject.toString(), Student.class);
					studentDao = (StudentDAO) context.getBean("StudentDAO");
					studentDao.save(student);
					log.debug(">>>>>>>保存student");
				}else{
					log.error(userInfo.getUserId()+":身份信息获取错误");
					return "身份信息错误";
				}
				mess = "注册成功";
				log.info("用户："+user.getUserId()+"执行了注册操作");
			}
		}else{
			mess = "未填写信息";
		}
		log.debug(">>>>>>>>处理后："+mess);
		log.debug("》》》》》注册方法结束");
		return mess;	
	}
	
	
	/**
	 * @登陆方法 查询user表，成功则返回反馈及用户信息，保存到会话，失败则返回失败因素
	 * @author Jiong
	 * @param request
	 * @param response
	 * @创建时间 2015、7、28
	 */
	public void dealLogin(HttpServletRequest request,HttpServletResponse response){
		String mess = "登录失败";
		log.debug(">>>>>>>>>>>>>进入登陆");
		//TODO ****************注入数据操作对象*************************
		//用户登陆表
		UserDAO userDao = (UserDAO) context.getBean("UserDAOImpl");
		//TODO ***************登陆成功后用到的实体和dao*****************
		UserInfo userInfo;
		Teacher teacher;
		Student student;
		UserInfoDAO userInfoDao;
		TeacherDAO teacherDao;
		StudentDAO studentDao;
		
		//TODO ***************反馈信息用的JSON**********************
		JSONObject sendJson = new JSONObject();
		JSONArray sendArray = new JSONArray();
		
		JSONArray jsonArray = null;
		try {
			jsonArray = jsonUtil.getJSONArray(request);
			if(true){//jsonArray.length()!=0){
				//JSONObject jsonObject = jsonArray.getJSONObject(0);
				//TODO ***********将JSONObject对象转换为实体对象***************
				//用户登陆对象
				User user = new User();
						//(User) jsonUtil.JSONToObj(jsonObject.toString(), User.class);
				//测试
				if(jsonArray.length()!=0){
					JSONObject jsonObject = jsonArray.getJSONObject(0);
				 user = 
					(User) jsonUtil.JSONToObj(jsonObject.toString(), User.class);
				}else{
					String userId = request.getParameter("userId");
					String password = (String) request.getParameter("password");
					log.debug(">>>>>>>"+userId+":"+password);
					user.setUserId(userId);
					user.setUserPwd(password);
				}
				//测试结束
				
				if(userDao.findById(user.getUserId())==null){
					mess = "用户不存在";
				}else if(!userDao.login(user)){
					mess = "密码错误";
				}else{
					log.debug(jsonUtil.objectToArray(userDao.findByExample(user)));
					mess = "登录成功";
					int i = 0; //验证步骤次数
					
					//TODO *********登录验证成功，获取用户信息***********
					userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAOImpl");
					userInfo = userInfoDao.findById(user.getUserId());
					sendJson = jsonUtil.objectToJson(userInfo);
					sendArray.put(1, sendJson);
					if(userInfo.getFlag()==null){
						mess = "验证通过，但用户身份获取失败";
						sendArray.remove(1);
						i++;
					}else if(userInfo.getFlag()==0){
						teacherDao = (TeacherDAO) context.getBean("TeacherDAOImpl");
						teacher = teacherDao.findById(user.getUserId());
						sendJson = jsonUtil.objectToJson(teacher);
						sendArray.put(2, sendJson);
					}else if(userInfo.getFlag()==1){
						studentDao = (StudentDAO) context.getBean("StudentDAOImpl");
						student = studentDao.findById(user.getUserId());
						if(student==null){
							mess = "没有此学生";
							i++;
						}else{
							sendJson = jsonUtil.objectToJson(student);
							sendArray.put(2,sendJson);
						}
					}else{
						mess = "验证通过，但用户身份获取失败";
						sendArray.remove(1);
						i++;
					}
					//将登陆状态保存到会话中
					if(i==0){
						request.getSession().setAttribute("user",userInfo);
					}
				}
				log.info(user.getUserId()+" ---执行登陆操作："+mess);
			}else{
				mess = "未填写信息";
			}
		} catch(Exception e){
			mess = "错误:"+e.getMessage();
		}
		try {
			sendJson = new JSONObject();
			sendJson.put("tip", mess);
			sendArray.put(0,sendJson);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendUtil.writeToClient(response, sendArray);
		log.debug("》》》》》登陆方法结束");				
	}
	//TODO 获取关注人集合
	public JSONArray getAttendedPeople(String userId) throws JSONException, IOException{
		PeopleRelationViewDAO relationDao = 
				context.getBean("PeopleRelationViewDAO",PeopleRelationViewDAO.class);
		log.debug("获取关注者账号集合"+userId);
		List userList = relationDao.findAttendedPeople(userId);
		if(userList.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(userList,false);
	}
	
	//TODO 获取关注的标签集合
		public JSONArray getAttendedLabel(String userId) throws JSONException, IOException{
			LabelRelationViewDAO labelDao = 
					context.getBean("LabelRelationViewDAO",LabelRelationViewDAO.class);
			log.debug("获取关注者账号集合"+userId);
			List userList = labelDao.findAttendedLabel(userId);
			if(userList.size()==0){
				return null;
			}
			return jsonUtil.objectToArray(userList,false);
		}
	

	
}
