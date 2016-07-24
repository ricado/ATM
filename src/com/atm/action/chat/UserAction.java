package com.atm.action.chat;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.NIOServer;
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.UserDAO;
import com.atm.model.user.User;
import com.atm.service.user.UserService;
import com.atm.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven {
	private static final Logger log = LoggerFactory.getLogger(UserAction.class);
	private User user;
	private HttpServletRequest request ;
	private HttpServletResponse response ;
	private String json;
	private String email;
	private String idOrEmail;
	private UserService userService = new UserService();	
	
	//登录
	public String login() throws IOException{
		log.info("调用server.....");
		System.out.println("----------aa----------");
		NIOServer server = new NIOServer();
		System.out.println("login..............");
		//System.out.println("user:" + user.getUserPwd());
		if(getUser().getUserId()!=null){
			try {
				request = getRequest();
				response = getResponse();
				log.info("==========登录");
				/*request.setAttribute("user", user);
				request.setAttribute("json", json);*/
				userService.login(request, response,json);
				System.out.println("login..............end");
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		return ERROR;
	}
	//修改密码
	public String changePassword(){
		request = getRequest();
		response = getResponse();
		
		json = JsonUtil.objectToJson(user);
		log.debug("json:" + json);
		json= userService.changePassword(json);
		request.setAttribute("json", json);
		return null;
	}
	/**
	 * 忘记密码
	 * @return
	 */
	public String forgetPassword(){
		request = getRequest();
		response = getResponse();
		//获取账号或者邮箱字符串
		//String idOrEmail = JSONObject.fromObject(json).getString("idOrEmail");
		
		//request.setAttribute("idOrEmail",idOrEmail);
		//忘记密码
		json = JsonUtil.put("idOrEmail", idOrEmail);
		log.debug("json:" + json);
		json = userService.forgetPasswod(json);
		request.setAttribute("json",json);
		System.out.println(json);
		return null;
	}
	/**
	 * 找回账号
	 * @return 
	 */
	public String findUserId(){
		request = getRequest();
		response = getResponse();
		
		JSONObject json = new JSONObject();
		json.put("email", email);
		log.debug("json:" + json);
		String json1 = userService.findUserId(json.toString());
		request.setAttribute("json", json1);
		return null;
	}
	public String findUserBy(){
		//获取user的业务逻辑类
		UserService userService = new UserService();
		//userService.fi
		return null;
	}
	public String delete(){
		return null;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	public void forward(String str) throws ServletException, IOException{
		RequestDispatcher rd;
		rd=request.getRequestDispatcher(str);
		rd.forward(request,response);
	}
	@Override
	public Object getModel() {
		if(user==null){
			user = new User();
		}
		return user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdOrEmail() {
		return idOrEmail;
	}
	public void setIdOrEmail(String idOrEmail) {
		this.idOrEmail = idOrEmail;
	}
}
