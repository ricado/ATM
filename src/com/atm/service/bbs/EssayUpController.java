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
 * @TODO��
 * @fileName : com.atm.service.bbs.EssayUpController.java
 * date | author | version |   
 * 2015��10��4�� | Jiong | 1.0 |
 */
@Controller
@RequestMapping(value="/essay")
public class EssayUpController implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	
	EssayChangeDeal changeDeal = //���±�����ز���
			context.getBean("EssayChangeDeal",EssayChangeDeal.class);
	
	//mess:���û�����ʾ��Ϣ-----sendJson:װ��mess------resultArray:װ������
	String mess;
	JSONObject sendJson;
	JSONArray resultArray;
	//��ʼ��
	private void init(){
		mess = "��ȡʧ��";
		sendJson  = new JSONObject();
		resultArray = new JSONArray();
	}
	//���ͽ��
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
	
	
	//TODO �������ӵķ���
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
		log.debug("������������");
		log.debug("���ݣ�"+content);
		log.debug("ͼƬ��"+files.length);
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess="δ��¼";
			send("publish",response,false);
			return;
		}
		String userId = user.getUserId();
		//��ȡ��Ÿ�·��
		String path = request.getSession().getServletContext().getRealPath("/WebRoot/image");
		//�����ļ�
		ArrayList savePath = commonUtil.saveFile(files, path, "essay",userId);
		int size = 0;
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				size++;
			}
		}
		log.debug("���ܵ�@��"+aiteID);
		if(size==savePath.size()){
			try{
				//���÷������ӷ���
				mess = changeDeal.saveAEssay(user, type, label, title, department, content,savePath);
			}catch(Exception e){
				mess = "δ֪����,����ʧ��";
				log.error(mess, e);
			}
		}else{
			mess = "�ļ�����ʧ��";
		}
		send("publish",response,false);
	}
	
	

}

