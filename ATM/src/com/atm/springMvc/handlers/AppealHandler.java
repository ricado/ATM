package com.atm.springMvc.handlers;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atm.dao.AppealDAO;
import com.atm.model.Appeal;
import com.atm.util.Application;
import com.atm.util.TimeUtil;

@RequestMapping("/appeal")
@Controller
public class AppealHandler implements Application {

	private static final Logger log = LoggerFactory
			.getLogger(AppealHandler.class);
	private static final AppealDAO appealDAO = (AppealDAO) context
			.getBean("AppealDAOImpl");

	@RequestMapping("/hello.do")
	public String hello() {
		return "hello";
	}

	/**
	 * 获取所有的申诉请求
	 * 
	 * @return
	 */
	@RequestMapping("/appeals.do")
	public String getAppeals(Map<String, Object> map) {
		List<Appeal> appeals = (List<Appeal>) appealDAO.findAll();
		map.put("appeals", appeals);
		return "appeal";
	}

	/**
	 * 删除
	 * 
	 * @param appId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/appe/{appId}.do", method = RequestMethod.DELETE)
	public String delete(@PathVariable("appId") Integer appId,
			Map<String, Object> map) {
		log.info("删除申述");
		appealDAO.delete(appealDAO.findById(appId));
		return "redirect:appeals.do";
	}
	
	@RequestMapping(value="/look/{index}.do")
	public String showAppeal(@PathVariable("index") Integer index,
			Map<String, Object> map){
		List<Appeal> appeals = (List<Appeal>)appealDAO.findAll();
		map.put("appeal", appeals.get(index));
		map.put("index", index);
		map.put("max",appeals.size()-1);
		return "showAppeal";
	}
	

	@RequestMapping(value = "/appe/{appId}.do")
	public String deleteAppeal(@PathVariable("appId") Integer appId,
			Map<String, Object> map) {
		log.info("删除申述");
		appealDAO.delete(appealDAO.findById(appId));
		return "springmvc";
	}

	@RequestMapping("/new.do")
	public String newAppeal() {
		return "inputAppeal";
	}

	/**
	 * 添加新的appeal
	 * 
	 * @param file
	 * @param appeal
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appe.do", method = RequestMethod.POST)
	public String saveAppeal(@RequestParam(value = "file") MultipartFile file,
			Appeal appeal, HttpServletRequest request, Map<String, Object> map) {
		log.info("saveAppeal");
		String path = upload(file, request, appeal.getNumber());
		appeal.setPhotoPath(path);
		appeal.setAppTime(TimeUtil.getTimestamp());
		appealDAO.save(appeal);
		return "success";
	}

	public String upload(MultipartFile file, HttpServletRequest request,
			String number) {
		System.out.println("开始保存图片");
		String path = request.getSession().getServletContext()
				.getRealPath("/image");
		String myPath = "image";
		log.info("==========" + path + "=============");
		String fileName = file.getOriginalFilename();
		log.debug("fileName------before:" + fileName);
		// 为文件创建一个独一无二的名字
		fileName = number + "_" + System.currentTimeMillis() + fileName;
		log.info("filename-------after:" + fileName);
		// 得到文件的保存目录
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		path = path + "/appeal/" + date;
		myPath = myPath + "/appeal/" + date;
		log.info("path:" + path);
		File targetFile = new File(path, fileName);
		// 如果没有这个文件，就生成一个
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myPath + "/" + fileName;
	}
}
