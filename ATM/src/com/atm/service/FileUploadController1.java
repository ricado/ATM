package com.atm.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//@Controller
public class FileUploadController1 {
	private static final Logger log = LoggerFactory.getLogger(FileUploadController1.class);
	/*@RequestParam(value ="file", required = false)*/
	
	/**
	 * ����ʱ�ϴ�ͼƬ
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * @param name
	 * @param number
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/.do")
    public void upload(@RequestParam(value="file",required=false) MultipartFile file, 
    		HttpServletRequest request,HttpServletResponse response,
    		ModelMap model,@RequestParam("type") String type,
    		@RequestParam("name") String name,@RequestParam("number") String number
    		) throws ServletException, IOException {  
        upload(file, request, response, type);
        log.debug("========ok==========");
        if(type.equals("appeal")){
        	request.getRequestDispatcher("appeal_").forward(request, response);
        }
    }
	
	@RequestMapping(value = "/appeal.do")
    public void uploadAppeal(@RequestParam(value="file",required=false) MultipartFile file, 
    		HttpServletRequest request,HttpServletResponse response,
    		ModelMap model,@RequestParam("type") String type,
    		@RequestParam("name") String name,@RequestParam("number") String number
    		) throws ServletException, IOException {  
        upload(file, request, response, type);
        log.debug("========ok==========");
        request.getRequestDispatcher("appeal_").forward(request, response);
    }
	
	@RequestMapping(value = "/userInfo.do")
    public void uploadUserInfo(@RequestParam(value="file",required=false) MultipartFile file, 
    		HttpServletRequest request,HttpServletResponse response,
    		ModelMap model,@RequestParam("type") String type,
    		@RequestParam("name") String name,@RequestParam("number") String number
    		) throws ServletException, IOException {  
        upload(file, request, response, type);
        log.debug("========ok==========");
        request.getRequestDispatcher("user_save").forward(request, response);
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
        log.debug("==========" + path+"/"+fileName);
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
