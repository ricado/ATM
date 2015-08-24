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
	 * 申述时上传图片
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
		System.out.println("开始");
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/image");
        log.debug("=========="+path+"=============");
        String fileName = file.getOriginalFilename();
        log.debug("fileName------befor:" + fileName);
        //为文件创建一个独一无二的名字
        fileName = makeFilename(fileName);
		log.debug("filename-------after:" + fileName);
		//得到文件的保存目录
		path = makePath(type,path);
		log.debug("path:" + path);
        File targetFile = new File(path, fileName); 
        //如果没有这个文件，就生成一个
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        //保存  
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
	 * 创建一个独一无二的文件名
	 * @param filename
	 * @return filename
	 */
	public String makeFilename(String filename){
		//为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		 return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * 创建时间的目录文件夹
	 * @param filename
	 * @param savePath
	 * @return
	 */
	public String makePath(String type,String savePath){
		//得到文件名的hashCode的值，得到的就是filename这个字符串对象
		//在内存中的地址
		//(new java.text.SimpleDateFormat("yyyy/MM/dd")).format(new Date())
		/*int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;  //0--15
		int dir2 = (hashcode&0xf0)>>4; //0--15*/		
		//构造新的保存的目录
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String dir = savePath + "\\" + type + "\\" + date;//upload\2\3  upload\3\5
		//File既可以代表文件也可以代表目录
		File file = new File(dir);
		//如果目录不存在
		if(!file.exists()){
			//创建目录
			System.out.println("create file" + file.getName());
			file.mkdirs();
		}
		return dir;
	}
}
