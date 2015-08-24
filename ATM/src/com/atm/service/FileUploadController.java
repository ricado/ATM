package com.atm.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.atm.model.Appeal;
import com.atm.model.chat.CrowdChat;
import com.atm.service.crowd.CrowdService;
import com.atm.util.JsonUtil;
import com.atm.util.TimeUtil;

@Controller
public class FileUploadController {
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
	/*@RequestParam(value ="file", required = false)*/
	
	@RequestMapping(value = "/appeal.do")
    public void uploadAppeal(@RequestParam(value="file",required=false) MultipartFile file, 
    		HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("name") String name, 
    		@RequestParam("number") String number,
    		@RequestParam("informEmail") String informEmail,
    		@RequestParam("role") String role
    		) throws ServletException, IOException {  
        String path = upload(file, request, response, "appeal");
       //appeal model.������ȡappeal����
        Appeal appeal = new Appeal();
        appeal.setInformEmail(informEmail);
        appeal.setName(name);
        appeal.setNumber(number);
        appeal.setPhotoPath(path);
        appeal.setRole(role);
        appeal.setAppTime(TimeUtil.getTimestamp());
        String json = JsonUtil.objectToJson(appeal);
        //appeal��ҵ���߼���
        AppealService appealService = new AppealService();
        appealService.saveAppeal(json, request, response);
        log.debug("=========success==========");
        //request.getRequestDispatcher("appeal_").forward(request, response);
    }
	/**
	 * �����µ�Ⱥ��
	 * @param file Ⱥͷ��
	 * @param request
	 * @param response
	 * @param crowdName Ⱥ��
	 * @param crowdDescription Ⱥ����
	 * @param crowdLabel Ⱥ��ǩ
	 * @param isHidden Ⱥ�Ƿ�����
	 * @param userId Ⱥ��userId
	 * @throws ServletException
	 * @throws IOException
	 * @author ye
	 * @2015.8.02
	 */
	@RequestMapping(value = "/createCrowd.do")
	public void createCrowd(@RequestParam(value="file",required=false) MultipartFile file, 
    		HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("crowdName") String crowdName,
    		@RequestParam("crowdDescription") String crowdDescription,
    		@RequestParam("crowdLabel") String crowdLabel,
    		@RequestParam("isHidden") int isHidden,
    		@RequestParam("userId") String userId
    		) throws ServletException, IOException {  
		String path = upload(file, request, response,"crowd");
        //��ȡ����Ⱥ�ĵı�Ҫ��Ϣ
		CrowdChat crowdChat = new CrowdChat();
        crowdChat.setCrowdDescription(crowdDescription);
        crowdChat.setCrowdHeadImage(path);
        crowdChat.setCrowdLabel(crowdLabel);
        crowdChat.setCrowdName(crowdName);
        crowdChat.setNumLimit(100);
        if(isHidden==1){
        	crowdChat.setIsHidden(false);
        }else{
        	crowdChat.setIsHidden(true);
        }
        crowdChat.setVerifyMode("0");
        Map map = new HashMap();
        map.put("crowdChat", crowdChat);
        map.put("userId",userId);
        String json = JsonUtil.mapToJson(map);
        log.debug("json:" + json);
        log.debug("========ok==========");
        CrowdService crowdService = new CrowdService();
        //String tip = crowdService.saveCrowdChat(json);
        //log.debug("===============" + tip + "============");
        //request.getRequestDispatcher("appeal_").forward(request, response);
    }
	
	
	public String upload(MultipartFile file,HttpServletRequest request
			,HttpServletResponse response,String type){
		System.out.println("��ʼ");
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/image");
        log.debug("=========="+path+"=============");
        String fileName = file.getOriginalFilename();
        log.debug("fileName------befor:" + fileName);
        //Ϊ�ļ�����һ����һ�޶�������
        fileName = makeFilename(fileName);
		log.debug("filename-------after:" + fileName);
		//�õ��ļ��ı���Ŀ¼
		path = makePath(type,path);
		log.debug("path:" + path);
        File targetFile = new File(path, fileName); 
        //���û������ļ���������һ��
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        //����  
        try {  
            file.transferTo(targetFile);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        //model.addAttribute("fileUrl",path+"/"+fileName); 
        request.setAttribute("photoPath", path+"/"+fileName);
        log.info("==========" + path+"/"+fileName);
        return path+"/"+fileName;
	}
	/**
	 * ����һ����һ�޶����ļ���
	 * @param filename
	 * @return filename
	 */
	public String makeFilename(String filename){
		//Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		 return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * ����ʱ���Ŀ¼�ļ���
	 * @param filename
	 * @param savePath
	 * @return
	 */
	public String makePath(String type,String savePath){
		//�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ�������
		//���ڴ��еĵ�ַ
		//(new java.text.SimpleDateFormat("yyyy/MM/dd")).format(new Date())
		/*int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;  //0--15
		int dir2 = (hashcode&0xf0)>>4; //0--15*/		
		//�����µı����Ŀ¼
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String dir = savePath + "\\" + type + "\\" + date;//upload\2\3  upload\3\5
		//File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		//���Ŀ¼������
		if(!file.exists()){
			//����Ŀ¼
			System.out.println("create file" + file.getName());
			file.mkdirs();
		}
		return dir;
	}
}