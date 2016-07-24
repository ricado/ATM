package com.atm.service.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atm.util.JsonUtil;

@Controller
@RequestMapping("/admin")
public class AdminiService {
	//日志文件
	private static Logger log = 
			LoggerFactory.getLogger(AdminiService.class);
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public @ResponseBody String login(HttpServletRequest request, HttpServletResponse response){
		String userId = (String) request.getParameter("userId");
		String password = (String) request.getParameter("password");
		log.info("userId:" + userId + " " + "password:" + password);
		Map<String,String> map = new HashMap<String, String>();
		if(userId.equals("10001")&&password.equals("1315")){
			log.info("登录成功!!");
			map.put("tip", "success");
		}else{
			log.info("登录失败");
			map.put("tip", "failed");
		}
		map.put("user", "chuangye");
		JSONArray jsonArray = JSONArray.fromObject(JsonUtil.mapToJson(map));
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		return jsonObject.toString();
	}
}
