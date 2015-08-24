package com.atm.action;

import java.io.IOException;

import javax.servlet.ServletException;

import com.atm.model.user.User;
import com.atm.util.HttpUtil;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 私聊聊天记录
 * @author ye
 *
 */
public class chatAction extends ActionSupport{
	private String userReceiveId;//聊天双方Id
	private String userSendId;
	public String getUserReceiveId() {
		return userReceiveId;
	}
	public void setUserReceiveId(String userReceiveId) {
		this.userReceiveId = userReceiveId;
	}
	public String getUserSendId() {
		return userSendId;
	}
	public void setUserSendId(String userSendId) {
		this.userSendId = userSendId;
	}
	public String chat(){
		try {
			HttpUtil.forward("/chatServlet");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
