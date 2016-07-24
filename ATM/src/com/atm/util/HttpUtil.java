package com.atm.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class HttpUtil {
	private static HttpServletRequest request;
	private static HttpServletResponse response;
	
	public static void forward(String str) throws ServletException, IOException{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		RequestDispatcher rd;
		rd=request.getRequestDispatcher(str);
		rd.forward(request,response);
	}
}
