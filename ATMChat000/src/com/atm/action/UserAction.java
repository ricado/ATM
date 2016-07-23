package com.atm.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.UserDeal;
import com.atm.service.user.UserService;
import com.atm.util.JsonUtil;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport implements ServletResponseAware,
		ServletRequestAware, ObjectInterface {
	Logger log = Logger.getLogger(getClass());

	private HttpServletRequest request;
	private HttpServletResponse response;
	UserDeal userDeal = (UserDeal) context.getBean("UserDeal");
	UserService userService = (UserService) context.getBean("UserService");
	private String json;

	/**
	 * 注册方法跳转
	 */
	public String register() throws IOException, JSONException {
		log.debug("json:" + json);
		json = jsonUtil.getJSONArray(request).toString();
		json = userService.register(json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/**
	 * 取消注册，删除之前保存的用户信息
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String exitRegister() throws IOException, JSONException {
		log.info("json:" + json);
		json = jsonUtil.getJSONArray(request).toString();
		json = userService.register(json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/**
	 * 注册的邮箱验证
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String confirmRegisterEmail() throws IOException, JSONException {
		log.info("json:" + json);
		json = jsonUtil.getJSONArray(request).toString();
		json = userService.confirmRegisterEmail(json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String loginConfirm() throws IOException, JSONException {
		log.info("socket登录成功之后，客户端再以请求链接的方式完成登录的后续工作");
		json = userService.loginConfirm(request, response, json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/*
	 * 登陆方法跳转(将登陆操作完全交给service)
	 */
	public String login() {
		userDeal.dealLogin(request, response);
		return null;
	}

	public String exit() {
		String mess = "失败";
		try {
			request.getSession().invalidate();// 销毁该用户的会话
			mess = "成功";
			JSONObject sendJson = new JSONObject();
			sendJson.put("tip", mess);
			JSONArray sendArray = new JSONArray().put(sendJson);
			sendUtil.writeToClient(response, sendArray);
		} catch (JSONException e) {
			log.error("注销错误", e);
			sendUtil.writeToClient(response, errorArray);
		}
		return null;
	}

	String mess;
	JSONObject sendJson;
	JSONArray resultArray;

	// 初始化
	private void init() {
		mess = "获取异常";
		sendJson = new JSONObject();
		resultArray = new JSONArray();
	}

	/**
	 * 学生的验证(教务系统账号登录)
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 *             flag:2,userId:a,number:131544251,password:woaini8874535
	 */
	public String userConfirm() throws IOException, JSONException {
		log.info("教务系统验证");
		json = jsonUtil.getJSONArray(request).toString();
		log.info("json:" + json);
		json = userService.confirmUser(json);
		sendUtil.writeToClient(response, json);
		return null;
	}
	
	/**
	 * 进入个人中心
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public String personalCenter() throws IOException, JSONException{
		log.info("进入个人中心:");
		json = jsonUtil.getJSONArray(request).toString();
		log.info("json:" + json);
		//json = userService;
		sendUtil.writeToClient(response, json);
		return null;
	}

	// 检查结果
	private void check() {
		if (resultArray == null) {
			mess = "没有结果";
			resultArray = new JSONArray();// 重新初始化
		} else {
			String tip = "成功";
			try {
				tip = (String) resultArray.getJSONObject(0).get("tip");
			} catch (JSONException e) {

			}
			mess = tip;
		}
	}

	// 发送，name:为rsultArray的key
	public void send(String name) {
		try {
			sendJson.put("tip", mess);
			sendJson.put(name, resultArray);
			sendUtil.writeToClient(response, sendJson);
		} catch (JSONException e) {
			sendUtil.writeToClient(response, errorJson);
			log.error(e);
		}
	}

	// TODO　获取用户关注的用户
	public String attendedPeople() {
		log.debug("获取关注者列表请求");
		init();
		UserInfo user = (UserInfo) request.getSession(true)
				.getAttribute("user");
		if (user == null) {
			mess = "未登录";
			send("attend");
			return null;
		}
		try {
			resultArray = userDeal.getAttendedPeople(user.getUserId());
			check();
		} catch (Exception e) {
			mess = "获取失败";
			log.error(mess, e);
		}
		send("people");
		return null;
	}

	// TODO 获取用户关注的标签
	public String attendedLabel() {
		log.debug("获取关注的标签请求");
		init();
		UserInfo user = (UserInfo) request.getSession(true)
				.getAttribute("user");
		if (user == null) {
			mess = "未登录";
			send("attend");
			return null;
		}
		try {
			resultArray = userDeal.getAttendedLabel(user.getUserId());
			check();
		} catch (Exception e) {
			mess = "获取失败";
			log.error(mess, e);
		}
		send("label");
		return null;
	}

	// 修改密码
	public String changePassword() throws IOException, JSONException {
		json = jsonUtil.getJSONArray(request).toString();
		log.debug("json:" + json);
		json = userService.changePassword(json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/**
	 * 忘记密码
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String forgetPassword() throws IOException, JSONException {
		json = jsonUtil.getJSONArray(request).toString();
		log.debug("json:" + json);
		json = userService.forgetPasswod(json);
		sendUtil.writeToClient(response, json);
		System.out.println(json);
		return null;
	}

	/**
	 * 找回账号
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String findUserId() throws IOException, JSONException {
		json = jsonUtil.getJSONArray(request).toString();
		log.debug("json:" + json);
		json = userService.findUserId(json.toString());
		sendUtil.writeToClient(response, json);
		return null;
	}

	public String findUserBy() {
		// 获取user的业务逻辑类
		UserService userService = new UserService();
		// userService.fi
		return null;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request = arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response = arg0;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}
