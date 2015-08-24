package com.atm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class UploadAction extends ActionSupport{
	@RequestMapping("upload")
	public String getmyFile(@RequestParam("file") MultipartFile file,HttpServletRequest request
			,HttpServletResponse response){
		System.out.println(file.getOriginalFilename());
		return null;
	}	
}
