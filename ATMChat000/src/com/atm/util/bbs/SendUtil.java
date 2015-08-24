package com.atm.util.bbs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
public class SendUtil {
	Logger log = Logger.getLogger(getClass());
	/**
	 * @���� ��result���͸��ͻ���
	 * @author Jiong
	 * @param response
	 * @param result
	 * @return
	 */
	public boolean writeToClient(HttpServletResponse response,String sendString){
		boolean ok = false;
		response.setContentType("text/json; charset=utf-8");
		//response.setHeader("Cache-Control", "no-cache"); //ȡ�����������
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(sendString);
			out.flush();
			out.close();
			log.debug("����ing��"+sendString.length()+":"+sendString);
			ok = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok = false;
		}
		return ok;
	}
	
	public boolean writeToClient(HttpServletResponse response,JSONObject sendObject){
		return(writeToClient(response,sendObject.toString()));
	}
	public boolean writeToClient(HttpServletResponse response,JSONArray sendArray){
		return(writeToClient(response,sendArray.toString()));
	}
}
