package com.atm.action;

import javax.servlet.http.HttpServletRequest;

import com.atm.util.HttpUtil;
import com.opensymphony.xwork2.ActionSupport;

public class InfoAction extends ActionSupport{
	private String userId;
	private String tip;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String info(){
		try{
			HttpUtil.forward("/infoServlet");
		}catch(Exception e){
			tip="提取用户信息失败";
		}
		return null;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
}
