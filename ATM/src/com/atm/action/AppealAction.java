package com.atm.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.atm.model.Appeal;
import com.atm.service.AppealService;
import com.atm.service.UploadService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AppealAction extends ActionSupport implements ModelDriven{
	private Appeal appeal;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AppealService appealService = new AppealService();
	private UploadService uploadService = new UploadService();
	public String appeal() throws ServletException, IOException{
		/*request = getRequest();
		response = getResponse();
		request.setAttribute("type","appeal");
		request.setAttribute("appeal", appeal);
		///uploadService.upload(request, response);
		request.getRequestDispatcher("/upload.do").forward(request, response);
		
		String photoPath = (String)request.getAttribute("photoPath");
		//保存路径
		System.out.println(photoPath);
		appeal.setPhotoPath(photoPath);
		//保存appeal
		String tip = appealService.save(appeal);
		System.out.println("=====================");
		request.getAttribute("tip");
		if(tip.equals("success")){
			return SUCCESS;
		}*/
		String photoPath = (String)request.getAttribute("photoPath");
		//保存路径
		System.out.println(photoPath);
		appeal.setPhotoPath(photoPath);
		//保存appeal
		/*String tip = appealService.save();
		System.out.println("=====================");
		request.getAttribute("tip");
		if(tip.equals("success")){
			return SUCCESS;
		}*/
		return ERROR;
	}
	public String saveAppeal(){
		
		return ERROR;
	}
	
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		if(appeal==null){
			appeal = new Appeal();
		}
		return appeal;
	}
	public Appeal getAppeal() {
		return appeal;
	}
	public void setAppeal(Appeal appeal) {
		this.appeal = appeal;
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
}
