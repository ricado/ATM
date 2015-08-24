package com.atm.action;

import java.io.IOException;

import com.atm.chat.nio.admin.AdminClient;
import com.atm.chat.nio.admin.AdminWriteHandler;
import com.atm.model.user.User;
import com.atm.service.AdminService;
import com.atm.service.user.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{
	private String userId;
	private String userPwd;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	public String login() throws IOException{
		AdminService adminService = new AdminService();
		adminService.login(userId, userPwd);
		return SUCCESS;
	}
}
