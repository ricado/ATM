package com.atm.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atm.springMvc.handlers.FileUploadController;
import com.atm.util.JsonUtil;

/**
 * 
 * @author ye
 * @2015.8.02
 */

//@Controller
public class FileDowmloadController1 {
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
	@RequestMapping("/downlaod.do")
	public String download(@RequestParam("json") String json,
			HttpServletRequest request,HttpServletResponse response){
		String type = JsonUtil.getString("type", json);
		if(type.equals("userInfo")){
			userInfo(json);
		}
		return null;
	}
	
	public void findBy(){
		
	}
	
	public void userInfo(String json){
		String userId = JsonUtil.getString("userId",json);
	}
}
