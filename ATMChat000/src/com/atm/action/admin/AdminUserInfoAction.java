package com.atm.action.admin;

import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.user.UserInfo;
import com.atm.service.user.UserService;
import com.atm.util.LogUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AdminUserInfoAction extends ActionSupport implements LogUtil{
	public UserBasicInfo user;

	private String userId;

	public String userInfo(){
		log.info("========userInfo============");
		user = UserService.getUserBasicInfo(userId);;
		return SUCCESS;
	}
	public String userLogin(){
		return "userLogin";
	}
	public String loginUser(){
		return SUCCESS;
	}
	
	public UserBasicInfo getUser() {
		return user;
	}

	public void setUser(UserBasicInfo user) {
		this.user = user;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
