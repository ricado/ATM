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
	 * @�û�ע�ᴦ�� �������ݳ־û������ط�����Ϣ
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
		String mess = "ע��ʧ��";
		//TODO ****************���ݲ�������*************************
		//�û���Ϣ��
		UserInfoDAO userInfoDao = (UserInfoDAO)context.getBean("UserInfoDAOImpl");
		//�û���½��
		UserDAO userDao = (UserDAO) context.getBean("UserDAOImpl");
		//�û���ɫ��
		TeacherDAO teacherDao;
		StudentDAO studentDao;
		
		//TODO ********���û�������jsonArray��ʽȡ��
		JSONArray jsonArray = jsonUtil.getJSONArray(request);
		
		if(jsonArray.length()!=0){
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			//TODO ***********��JSONObject����ת��Ϊʵ�����(�����׳����ִ���)***************
			//�û���Ϣ����
			UserInfo userInfo = 
					(UserInfo) jsonUtil.JSONToObj(jsonObject.toString(), UserInfo.class);
			//�û���½����
			User user = 
					(User) jsonUtil.JSONToObj(jsonObject.toString(), User.class);
			//�û���ɫ���󣨶�ѡһ��
			Teacher teacher = null;
			Student student = null;
			
			//�˴�Ϊ�û�ע����Ϣ��ʽ��֤��δ���ƣ������ÿͻ��˵�ģ��
			if(!jsonUtil.registerCheck(jsonObject)){
				mess = "�û���Ϣ���淶";
			}else if(userInfoDao.findById(userInfo.getUserId())!=null){
				mess = "�û��ѱ�ע��";
			}else{
				//TODO ע���û����ݳ־û�
				userInfoDao.save(userInfo);
				log.debug(">>>>>>����userInfo");
					
				userDao.save(user);
				log.debug(">>>>>>>>>����user");
				
				if(userInfo.getFlag()==null){
					log.error(userInfo.getUserId()+":�����Ϣ��ȡʧ��");
					return "δ��ȡ�������Ϣ";
				}else if(userInfo.getFlag()==0){
					teacher = (Teacher) jsonUtil.JSONToObj(jsonObject.toString(), Teacher.class);
					teacherDao = (TeacherDAO) context.getBean("TeacherDAO");
					teacherDao.save(teacher);
					log.debug(">>>>>>>����teacher");
				}else if(userInfo.getFlag()==1){
					student = (Student) jsonUtil.JSONToObj(jsonObject.toString(), Student.class);
					studentDao = (StudentDAO) context.getBean("StudentDAO");
					studentDao.save(student);
					log.debug(">>>>>>>����student");
				}else{
					log.error(userInfo.getUserId()+":�����Ϣ��ȡ����");
					return "�����Ϣ����";
				}
				mess = "ע��ɹ�";
				log.info("�û���"+user.getUserId()+"ִ����ע�����");
			}
		}else{
			mess = "δ��д��Ϣ";
		}
		log.debug(">>>>>>>>�����"+mess);
		log.debug("����������ע�᷽������");
		return mess;	
	}
	
	
	/**
	 * @��½���� ��ѯuser���ɹ��򷵻ط������û���Ϣ�����浽�Ự��ʧ���򷵻�ʧ������
	 * @author Jiong
	 * @param request
	 * @param response
	 * @����ʱ�� 2015��7��28
	 */
	public void dealLogin(HttpServletRequest request,HttpServletResponse response){
		String mess = "��¼ʧ��";
		log.debug(">>>>>>>>>>>>>�����½");
		//TODO ****************ע�����ݲ�������*************************
		//�û���½��
		UserDAO userDao = (UserDAO) context.getBean("UserDAOImpl");
		//TODO ***************��½�ɹ����õ���ʵ���dao*****************
		UserInfo userInfo;
		Teacher teacher;
		Student student;
		UserInfoDAO userInfoDao;
		TeacherDAO teacherDao;
		StudentDAO studentDao;
		
		//TODO ***************������Ϣ�õ�JSON**********************
		JSONObject sendJson = new JSONObject();
		JSONArray sendArray = new JSONArray();
		
		JSONArray jsonArray = null;
		try {
			jsonArray = jsonUtil.getJSONArray(request);
			if(true){//jsonArray.length()!=0){
				//JSONObject jsonObject = jsonArray.getJSONObject(0);
				//TODO ***********��JSONObject����ת��Ϊʵ�����***************
				//�û���½����
				User user = new User();
						//(User) jsonUtil.JSONToObj(jsonObject.toString(), User.class);
				//����
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
				//���Խ���
				
				if(userDao.findById(user.getUserId())==null){
					mess = "�û�������";
				}else if(!userDao.login(user)){
					mess = "�������";
				}else{
					log.debug(jsonUtil.objectToArray(userDao.findByExample(user)));
					mess = "��¼�ɹ�";
					int i = 0; //��֤�������
					
					//TODO *********��¼��֤�ɹ�����ȡ�û���Ϣ***********
					userInfoDao = (UserInfoDAO) context.getBean("UserInfoDAOImpl");
					userInfo = userInfoDao.findById(user.getUserId());
					sendJson = jsonUtil.objectToJson(userInfo);
					sendArray.put(1, sendJson);
					if(userInfo.getFlag()==null){
						mess = "��֤ͨ�������û���ݻ�ȡʧ��";
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
							mess = "û�д�ѧ��";
							i++;
						}else{
							sendJson = jsonUtil.objectToJson(student);
							sendArray.put(2,sendJson);
						}
					}else{
						mess = "��֤ͨ�������û���ݻ�ȡʧ��";
						sendArray.remove(1);
						i++;
					}
					//����½״̬���浽�Ự��
					if(i==0){
						request.getSession().setAttribute("user",userInfo);
					}
				}
				log.info(user.getUserId()+" ---ִ�е�½������"+mess);
			}else{
				mess = "δ��д��Ϣ";
			}
		} catch(Exception e){
			mess = "����:"+e.getMessage();
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
		log.debug("������������½��������");				
	}
	//TODO ��ȡ��ע�˼���
	public JSONArray getAttendedPeople(String userId) throws JSONException, IOException{
		PeopleRelationViewDAO relationDao = 
				context.getBean("PeopleRelationViewDAO",PeopleRelationViewDAO.class);
		log.debug("��ȡ��ע���˺ż���"+userId);
		List userList = relationDao.findAttendedPeople(userId);
		if(userList.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(userList,false);
	}
	
	//TODO ��ȡ��ע�ı�ǩ����
		public JSONArray getAttendedLabel(String userId) throws JSONException, IOException{
			LabelRelationViewDAO labelDao = 
					context.getBean("LabelRelationViewDAO",LabelRelationViewDAO.class);
			log.debug("��ȡ��ע���˺ż���"+userId);
			List userList = labelDao.findAttendedLabel(userId);
			if(userList.size()==0){
				return null;
			}
			return jsonUtil.objectToArray(userList,false);
		}
	

	
}
