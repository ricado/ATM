package com.atm.action;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.service.OnlineService;
import com.atm.service.admin.AdminService;
import com.opensymphony.xwork2.ActionSupport;

public class OnlineAction extends ActionSupport {
	private static final Logger log = 
			LoggerFactory.getLogger(OnlineAction.class);
	// private InputStream inputStream;

	/*
	 * public InputStream getInputStream() { return inputStream; }
	 */
	public AdminService adminService = new AdminService();
	
	public String response = "";

	public String userId;
	
	public int number;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getLoginList() throws Exception {
		log.info("exxcite./.......");
		OnlineService onlineService = new OnlineService();
		response = onlineService.getLoginList();
		// inputStream = new StringBufferInputStream(transcoding(string));
		return SUCCESS;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String delete(){
		System.out.println("deleteLogin....." + userId);
		adminService.deleteLogin(userId);
		return SUCCESS;
	}
	
	/**
	 * ÂÒÂë½â¾ö
	 * @param str
	 * @return
	 */
	private String transcoding(String str) {
		try {
			return new String(str.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public String move(){
		try{
			log.info("userId" + userId + "  number:" + number);
			adminService.move(userId, number);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
}
