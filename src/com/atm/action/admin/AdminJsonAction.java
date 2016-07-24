package com.atm.action.admin;

import com.atm.model.define.user.UserBasicInfo;
import com.atm.service.admin.AdminUserService;
import com.atm.util.LogUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AdminJsonAction extends ActionSupport implements LogUtil{
	//UserService userService = new AdminJsonAction();
	AdminUserService adminUser = new AdminUserService();
	UserBasicInfo userBasicInfo = new UserBasicInfo();
	private int first;
	private String json;
	
	private String userId = null;
	public String findAllUser(){
		log.info("---------------------");
		json = adminUser.findAllUserList(first,7);
		first += 7;
		return SUCCESS;
	}
	public String findLoginUser(){
		return null;
	}
	public String findCrowd(){
		return null;
	}
	public void setFirst(int first){
		this.first = first; 
	}
	public int getFirst(){
		return this.first;
	}
	public void setJson(String json){
		this.json = json;
	}
	public String getJson(){
		return this.json;
	}
}
