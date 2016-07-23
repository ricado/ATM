package com.atm.test;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atm.dao.TransactionDao;
import com.atm.dao.chat.PrivateChatDAO;
import com.atm.dao.impl.TransactionDaoImpl;
import com.atm.dao.impl.chat.PrivateChatDAOImpl;
import com.atm.dao.impl.user.MajorDAOImpl;
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.impl.user.UserInfoDAOImpl;
import com.atm.dao.user.MajorDAO;
import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.model.chat.PrivateChat;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.util.Application;
//@Transactional
public class test implements Application{
	private static final Logger log = LoggerFactory.getLogger(test.class);
	
	public static void main(String[] args) {
		test test = new test();
		String path = test.getPath();
		log.info(path);
		
		String string = "8.jpg";
		String[] strings = string.split("\\.");
		log.info("length:" + strings.length);
		for (int i = 0; i < strings.length; i++) {
			log.info(strings[i]);
		}
		
	}
	public String getPath() {
		// String path =
		// this.getClass().getClassLoader().getResource("/").getPath();
		String path = this.getClass().getResource("/").getPath();
		path = path.substring(1);
		path = path.replaceFirst("WEB-INF/classes/", "");
		return path;
	}
	/*public static void main(String[] args) throws Exception{*/
		/*String userId = "111005050228";
		String userPwd = "131544215";
		String name = "yiyi";
		User user = new User();
		user.setUserId("1000268");
		user.setUserPwd(userPwd);
		UserInfo info = new UserInfo();
		info.setUserId(userId);
		info.setName(name);
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		UserInfoDAO infoDAO = UserInfoDAOImpl.getFromApplicationContext();
		try{
			System.out.println("========save--info===========");
			infoDAO.save(info);
			System.out.println("========save--user===========");
			dao.save(user);
			System.out.println("========success==============");
		}catch(RuntimeException e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("========exception============");
		}
		test test = 
				new ClassPathXmlApplicationContext("applicationContext.xml").getBean("Test",test.class);
		//chat
		//test.chat1();
		//jsontext
		//test.jsonTest();
		//json to Onject
		try{
			test.saveRegister();
		}catch(RuntimeException e){
			log.debug("======================出错");
			throw e;
		}catch(Exception ee){
			log.debug("==============exception");
			throw ee;
		}
	}*/
	
	//@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public void saveByT(){
		String userId = "200030248";
		String userPwd = "131544215";
		String name = "jenter";
		User user = new User();//user对象
		
		user.setUserId("200030248");
		user.setUserPwd(userPwd);
		UserInfo info = new UserInfo();//userInfo对象
		
		info.setUserId(userId);
		info.setName(name);
		UserInfo user1 = new UserInfo();
		/*user1.setUserId("10001");
		user1.setName("stru");*/
		
		TransactionDao transaction = TransactionDaoImpl.getFromApplicationContext();
		//Transaction transaction = session.beginTransaction();
		/*UserDAO dao = UserDAOImpl.getFromApplicationContext();
		UserInfoDAO infoDAO = UserInfoDAOImpl.getFromApplicationContext();*/
		
		transaction.beginTransaction();
			System.out.println("========save--info===========");
			transaction.save(info);
			System.out.println("========save--user===========");
			transaction.save(user);
			System.out.println("========save--user1===========");
			/*session.save(user1);
			System.out.println("========commit==============");*/
			transaction.commit();
			System.out.println("========success==============");
			transaction.closeSession();
		/*}catch(RuntimeException e){
			System.out.println("---------");
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("========exception============");
		}*/
	}
	public void save(){
		String userId = "400005";
		String userPwd = "131544215";
		String name = "jenter";
		User user = new User();//user对象
		user.setUserId("200030252");
		user.setUserPwd(userPwd);
		UserInfo info = new UserInfo();//userInfo对象
		
		info.setUserId(userId);
		info.setName(name);
		UserInfo user1 = new UserInfo();
		/*user1.setUserId("10001");
		user1.setName("stru");*/
		
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		UserInfoDAO infoDAO = UserInfoDAOImpl.getFromApplicationContext();
		
		System.out.println("========save--info===========");
		infoDAO.save(info);
		System.out.println("========save--user===========");
		dao.save(user);
		System.out.println("========sucess===========");
		/*}catch(RuntimeException e){
			System.out.println("---------");
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("========exception============");
		}*/
	}
	public String saveRegister(){
		//TODO
		String mess = "注册失败";
		UserInfoDAO userInfoDao = 
				(UserInfoDAO)context.getBean("UserInfoDAOImpl");
		//用户登陆表
		UserDAO userDao = (UserDAO)context.getBean("UserDAOImpl");
		//用户角色表
		TeacherDAO teacherDao;
		StudentDAO studentDao;
		
		//TODO ********将用户请求以jsonArray格式取出
		
			String userId = "400017";
			String userPwd = "131544215";
			String name = "jenter";
			User user = new User();//user对象
			user.setUserId("500008");
			user.setUserPwd(userPwd);
			UserInfo userInfo = new UserInfo();//userInfo对象
			
			userInfo.setUserId(userId);
			userInfo.setName(name);
			UserInfo user1 = new UserInfo();
			//此处为用户注册信息格式验证，未完善，可套用客户端的模版
				//TODO 注册用户数据持久化
				userInfoDao.save(userInfo);
				log.debug(">>>>>>保存userInfo");
					
				userDao.save(user);
				log.debug(">>>>>>>>>保存user");
				mess = "注册成功";
				log.info("用户："+user.getUserId()+"执行了注册操作");
		log.debug(">>>>>>>>处理后：");
		log.debug("》》》》》注册方法结束");
		return mess;	
	}
	public void info(){
		String userId = "10001";
		UserInfoDAO userInfoDAO = UserInfoDAOImpl.getFromApplicationContext();
		UserInfo userInfo = userInfoDAO.findById(userId); 
		System.out.println(userInfo.getUserId());
		if(userInfo!=null){
			//Session session = HibernateSessionFactory.getSession();
			MajorDAO majorDao = MajorDAOImpl.getFromApplicationContext();
			//majorDao.findById();
			System.out.println(userInfo.getMajor().getDepartment().getSchool().getScName());
			//Major major = MajorDAOImpl.getFromApplicationContext().findById(userInfo.getMajor().getMno());
			//System.out.println(major.getDepartment().getSchool().getScName());
		}else{
			System.out.println("没有找到");
		}
	}
	public void chat(){
		PrivateChatDAO chatDAO = PrivateChatDAOImpl.getFromApplicationContext();
		System.out.println("send............");
		List list = chatDAO.findByReceiveAndSend("10001", "10002");
		List list2 = chatDAO.findByReceiveAndSend("10002", "10001");
		List listChat = new ArrayList();
		int i = 0;
		int j = 0;
		System.out.println("get............");
		int size1 = list.size();
		int size2 = list2.size();
		System.out.println(size1 + "=========" + size2);
		//聊天排序
		while(i<size1 || j<size2){
			if(i==size1){
				while(j<size2){
					listChat.add(list2.get(j++));
				}
				break;
			}
			if(j==size2){
				while(i<size1){
					listChat.add(list.get(i++));
				}
				break;
			}
			PrivateChat chat1 = (PrivateChat)list.get(i);
			PrivateChat chat2 = (PrivateChat)list2.get(j);
			if(chat1.getSendTime().before(chat2.getSendTime())){
				listChat.add(chat1);
				i++;
			}else{
				listChat.add(chat2);
				j++;
			}
		}
		System.out.println("========" + listChat.size() + "========");
		for(int x=0;x<listChat.size();x++){
			PrivateChat chat = (PrivateChat)listChat.get(x);
			System.out.println(chat.getUserReceiveId() + ": " + chat.getSendContent());
		}
		System.out.println("回话结束");
	}
	public void chat1(){
		PrivateChatDAO chatDAO = PrivateChatDAOImpl.getFromApplicationContext();
		System.out.println("send............");
		List list = chatDAO.findByReceiveAndSend("10001", "10002");
		System.out.println("ok.........");
		for(int i=0;i<list.size();i++){
			PrivateChat chat = (PrivateChat)list.get(i);
			System.out.println(chat.getUserReceiveId() + ": " + chat.getSendContent());
		}
		System.out.println("===============");
	}
	//json to object
	public void jTo(){
		String json = createJson();
		//List list= jsonToObject1(json, User.class);
		/*for (int i = 0; i < list.size(); i++) {
			User user = (User)list.get(i);
			System.out.println(user.getUserId() + " " + user.getUserPwd());
		}*/
		Object[] objects = jsonToObject1(json);
	}
	//json的测试
	public void jsonTest(){
		String json = createJson();
		String names = readJson(json);
		System.out.println(names);
	}
	//生成json
	public String createJson() throws JSONException{  
	    JSONObject json=new JSONObject();  
	    JSONArray jsonMembers = new JSONArray();  
	    JSONObject member1 = new JSONObject();  
	    member1.put("userId", "10001");  
	    member1.put("userPwd", "131544215");  
	   /* member1.put("email","10371443@qq.com");  
	    member1.put("sign_date", "2007-06-12");*/  
	    jsonMembers.add(member1);  
	    	
	    JSONObject member2 = new JSONObject();  
	    member2.put("userId", "10002");  
	    member2.put("userPwd", "131544215");  
	    /*member2.put("email","8223939@qq.com");  
	    member2.put("sign_date", "2008-07-16"); */ 
	    jsonMembers.add(member2);  
	    json.put("user", jsonMembers);  
	    
	    System.out.println(json.toString()+"///////////////");
	    return json.toString();  
	} 
	//解析json
	public String readJson(String jsonString) throws JSONException{  
	    //String jsonString="{\"users\":[{\"loginname\":\"zhangfan\",\"password\":\"userpass\",\"email\":\"10371443@qq.com\"},{\"loginname\":\"zf\",\"password\":\"userpass\",\"email\":\"822393@qq.com\"}]}";  
	    JSONObject json= JSONObject.fromObject(jsonString);  
	    JSONArray jsonArray=json.getJSONArray("users");  
	    String loginNames="loginname list:";  
	    for(int i=0;i<jsonArray.size();i++){  
	        JSONObject user=(JSONObject) jsonArray.get(i);  
	        String userName=(String) user.get("loginname");  
	        if(i==jsonArray.size()-1){  
	            loginNames+=userName;  
	        }else{  
	            loginNames+=userName+",";  
	        }  
	    } 
	    System.out.println(loginNames + "==================");
	    return loginNames;
	}
	//json转换成对象
	public List jsonToObject(String jsonString,Class clazz){
		JSONObject object = new JSONObject();
		JSONObject jsonObj = object.accumulate("list", jsonString);
		JSONArray array = JSONArray.fromObject(jsonObj);
        List list = new ArrayList();
        for(int i = 0; i < array.size(); i++){        
            JSONObject jsonObject = array.getJSONObject(i);        
            list.add(JSONObject.toBean(jsonObject, clazz));        
        }
        return list;
	}
	public Object[] jsonToObject1(String jsonString){
		JSONObject object = new JSONObject();
		JSONObject jsonObj = object.accumulate("list", jsonString);
		JSONArray array = JSONArray.fromObject(jsonObj);
        Object[] objects = array.toArray();
        System.out.println(objects.length);
        for(int i=0;i<objects.length;i++){
        	JSONObject obj = JSONObject.fromObject(objects[0]);
        	User user = (User)obj.toBean(obj, User.class);
        	System.out.println(user.getUserId() + " " + user.getUserPwd());
        }
        return objects;
	}
}
