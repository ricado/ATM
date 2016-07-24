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
	 * ע�᷽����ת
	 */
	public String register() throws IOException, JSONException {
		log.debug("json:" + json);
		json = jsonUtil.getJSONArray(request).toString();
		json = userService.register(json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/**
	 * ȡ��ע�ᣬɾ��֮ǰ������û���Ϣ
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
	 * ע���������֤
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
	 * ��¼
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public String loginConfirm() throws IOException, JSONException {
		log.info("socket��¼�ɹ�֮�󣬿ͻ��������������ӵķ�ʽ��ɵ�¼�ĺ�������");
		json = userService.loginConfirm(request, response, json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/*
	 * ��½������ת(����½������ȫ����service)
	 */
	public String login() {
		userDeal.dealLogin(request, response);
		return null;
	}

	public String exit() {
		String mess = "ʧ��";
		try {
			request.getSession().invalidate();// ���ٸ��û��ĻỰ
			mess = "�ɹ�";
			JSONObject sendJson = new JSONObject();
			sendJson.put("tip", mess);
			JSONArray sendArray = new JSONArray().put(sendJson);
			sendUtil.writeToClient(response, sendArray);
		} catch (JSONException e) {
			log.error("ע������", e);
			sendUtil.writeToClient(response, errorArray);
		}
		return null;
	}

	String mess;
	JSONObject sendJson;
	JSONArray resultArray;

	// ��ʼ��
	private void init() {
		mess = "��ȡ�쳣";
		sendJson = new JSONObject();
		resultArray = new JSONArray();
	}

	/**
	 * ѧ������֤(����ϵͳ�˺ŵ�¼)
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 *             flag:2,userId:a,number:131544251,password:woaini8874535
	 */
	public String userConfirm() throws IOException, JSONException {
		log.info("����ϵͳ��֤");
		json = jsonUtil.getJSONArray(request).toString();
		log.info("json:" + json);
		json = userService.confirmUser(json);
		sendUtil.writeToClient(response, json);
		return null;
	}
	
	/**
	 * �����������
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public String personalCenter() throws IOException, JSONException{
		log.info("�����������:");
		json = jsonUtil.getJSONArray(request).toString();
		log.info("json:" + json);
		//json = userService;
		sendUtil.writeToClient(response, json);
		return null;
	}

	// �����
	private void check() {
		if (resultArray == null) {
			mess = "û�н��";
			resultArray = new JSONArray();// ���³�ʼ��
		} else {
			String tip = "�ɹ�";
			try {
				tip = (String) resultArray.getJSONObject(0).get("tip");
			} catch (JSONException e) {

			}
			mess = tip;
		}
	}

	// ���ͣ�name:ΪrsultArray��key
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

	// TODO����ȡ�û���ע���û�
	public String attendedPeople() {
		log.debug("��ȡ��ע���б�����");
		init();
		UserInfo user = (UserInfo) request.getSession(true)
				.getAttribute("user");
		if (user == null) {
			mess = "δ��¼";
			send("attend");
			return null;
		}
		try {
			resultArray = userDeal.getAttendedPeople(user.getUserId());
			check();
		} catch (Exception e) {
			mess = "��ȡʧ��";
			log.error(mess, e);
		}
		send("people");
		return null;
	}

	// TODO ��ȡ�û���ע�ı�ǩ
	public String attendedLabel() {
		log.debug("��ȡ��ע�ı�ǩ����");
		init();
		UserInfo user = (UserInfo) request.getSession(true)
				.getAttribute("user");
		if (user == null) {
			mess = "δ��¼";
			send("attend");
			return null;
		}
		try {
			resultArray = userDeal.getAttendedLabel(user.getUserId());
			check();
		} catch (Exception e) {
			mess = "��ȡʧ��";
			log.error(mess, e);
		}
		send("label");
		return null;
	}

	// �޸�����
	public String changePassword() throws IOException, JSONException {
		json = jsonUtil.getJSONArray(request).toString();
		log.debug("json:" + json);
		json = userService.changePassword(json);
		sendUtil.writeToClient(response, json);
		return null;
	}

	/**
	 * ��������
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
	 * �һ��˺�
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
		// ��ȡuser��ҵ���߼���
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
