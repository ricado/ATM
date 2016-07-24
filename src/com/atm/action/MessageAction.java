package com.atm.action;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.atm.service.ConfirmUser;
import com.atm.util.mail.SendDemo;
import com.opensymphony.xwork2.ActionSupport;

public class MessageAction extends ActionSupport{
	private String massage;
	private String idOrEmail;
	private String tip;
	HttpServletRequest request;
	HttpServletResponse response;
	
	public String forgetPassword(){
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		ConfirmUser confirmUser = new ConfirmUser();
		boolean flag;
		System.out.println(idOrEmail+"===========");
		if(idOrEmail.split("@").length>=2){
			flag = confirmUser.findByEmail(idOrEmail);
		}else{
			flag = confirmUser.findById(idOrEmail);
		}
		if(flag){
			tip = "success";
			SendDemo demo = new SendDemo();
				demo.send();
			//request.setAttribute("confirm", demo.confirmStr);
			//request.setAttribute("confirm", demo.content);
		}else{
			tip = "error";
		}
		return tip;
	}
	
	
	public String getMassage() {
		return massage;
	}
	public void setMassage(String massage) {
		this.massage = massage;
	}
	public String getIdOrEmail() {
		return idOrEmail;
	}
	public void setIdOrEmail(String idOrEmail) {
		this.idOrEmail = idOrEmail;
	}
	
}
