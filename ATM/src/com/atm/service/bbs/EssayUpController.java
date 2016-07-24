package com.atm.service.bbs;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atm.model.user.UserInfo;
import com.atm.util.bbs.CommonUtil;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO：
 * @fileName : com.atm.service.bbs.EssayUpController.java
 * date | author | version |   
 * 2015年10月4日 | Jiong | 1.0 |
 */
@Controller
@RequestMapping(value="/essay")
public class EssayUpController implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	
	EssayChangeDeal changeDeal = //更新保存相关操作
			context.getBean("EssayChangeDeal",EssayChangeDeal.class);
	
	//mess:给用户的提示信息-----sendJson:装载mess------resultArray:装载帖子
	String mess;
	JSONObject sendJson;
	JSONArray resultArray;
	//初始化
	private void init(){
		mess = "获取失败";
		sendJson  = new JSONObject();
		resultArray = new JSONArray();
	}
	//发送结果
	private void send(String name,HttpServletResponse response,boolean haveArray){
		try {
			sendJson.put("tip", mess);
			if(haveArray){
				sendJson.put(name,resultArray);
			}
		} catch (JSONException e) {
			sendUtil.writeToClient(response, errorJson);
			log.error(e);
		}
		sendUtil.writeToClient(response,sendJson);
	}
	
	
	//TODO 发布帖子的方法
	@RequestMapping(value="/publish.do",produces = "application/json")
	@ResponseBody
	public void publish(@RequestParam("files") MultipartFile[] files,
			HttpServletRequest request,
			HttpServletResponse response,
			String type,
			String label,
			String title,
			String department,
			String content,
			String aiteID){
		log.debug("发布帖子请求");
		log.debug("内容："+content);
		log.debug("图片："+files.length);
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess="未登录";
			send("publish",response,false);
			return;
		}
		String userId = user.getUserId();
		//获取存放根路径
		String path = request.getSession().getServletContext().getRealPath("/WebRoot/image");
		//保存文件
		ArrayList savePath = commonUtil.saveFile(files, path, "essay",userId);
		int size = 0;
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				size++;
			}
		}
		log.debug("接受到@："+aiteID);
		if(size==savePath.size()){
			try{
				//调用发布帖子方法
				mess = changeDeal.saveAEssay(user, type, label, title, department, content,savePath);
			}catch(Exception e){
				mess = "未知错误,发布失败";
				log.error(mess, e);
			}
		}else{
			mess = "文件保存失败";
		}
		send("publish",response,false);
	}
	
	

}

