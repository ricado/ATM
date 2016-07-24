package com.atm.springMvc.handlers;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.atm.model.Appeal;
import com.atm.model.chat.CrowdChat;
import com.atm.service.AppealService;
import com.atm.service.crowd.CrowdService;
import com.atm.util.JsonUtil;
import com.atm.util.TimeUtil;
import com.atm.util.bbs.SendUtil;

/**
 * ��̨�ϴ�ͼƬ
 * 
 * @author ye 2015.8.1
 */
@Controller
public class FileUploadController {
	private static final Logger log = LoggerFactory
			.getLogger(FileUploadController.class);

	/* @RequestParam(value ="file", required = false) */

	/**
	 * ����
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @param json
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/appeal.do")
	public String uploadAppeal(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			Writer writer, @RequestParam("json") String json
	// @RequestParam("name") String name,
	// @RequestParam("number") String number,
	// @RequestParam("informEmail") String informEmail,
	// @RequestParam("role") String role
	) throws ServletException, IOException {
		// ����ͼƬ����ȡͼƬ·��
		log.info("����upload");
		// appeal model.������ȡappeal����
		Appeal appeal = new Appeal();
		// json��ȡ��������
		JSONObject jsonObject = JsonUtil.getJsonObject(json);
		String informEmail = jsonObject.getString("informEmail");
		String name = jsonObject.getString("name");
		String number = jsonObject.getString("number");
		String role = jsonObject.getString("role");
		String path = upload(file, request, number);
		log.info("path:" + path);
		// ����appealʵ���ĸ�������
		appeal.setInformEmail(informEmail);
		appeal.setName(name);
		appeal.setNumber(number);
		appeal.setPhotoPath(path);
		appeal.setRole(role);
		appeal.setAppTime(TimeUtil.getTimestamp());
		json = JsonUtil.objectToJson(appeal);
		// appeal��ҵ���߼���
		AppealService appealService = new AppealService();
		String tip = appealService.saveAppeal(json, request, response);
		log.debug("=========success==========");
		// TODO ����
		log.info(tip);
		jsonObject.clear();
		jsonObject.put("tip", tip);
		
		// request.getRequestDispatcher("appeal_").forward(request, response);
		log.info("[" + jsonObject.toString() + "]");
		new SendUtil().writeToClient(response, "[" + jsonObject.toString()
				+ "]");
		log.info("writer:" + writer.toString());
		/*PrintWriter out = response.getWriter();
		out.write("[" + jsonObject.toString() + "]");
		out.flush();*/
		return "[" + jsonObject.toString() + "]";
	}

	/**
	 * �����µ�Ⱥ��
	 * 
	 * @param file
	 *            Ⱥͷ��
	 * @param request
	 * @param response
	 * @param crowdName
	 *            Ⱥ��
	 * @param crowdDescription
	 *            Ⱥ����
	 * @param crowdLabel
	 *            Ⱥ��ǩ
	 * @param isHidden
	 *            Ⱥ�Ƿ�����
	 * @param userId
	 *            Ⱥ��userId
	 * @throws ServletException
	 * @throws IOException
	 * @author ye
	 * @2015.8.02
	 */
	@RequestMapping(value = "/createCrowd.do")
	public void createCrowd(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("crowdName") String crowdName,
			@RequestParam("crowdDescription") String crowdDescription,
			@RequestParam("crowdLabel") String crowdLabel,
			@RequestParam("isHidden") int isHidden,
			@RequestParam("userId") String userId) throws ServletException,
			IOException {
		String path = upload(file, request, response, "crowd");
		// ��ȡ����Ⱥ�ĵı�Ҫ��Ϣ
		CrowdChat crowdChat = new CrowdChat();
		crowdChat.setCrowdDescription(crowdDescription);
		crowdChat.setCrowdHeadImage(path);
		crowdChat.setCrowdLabel(crowdLabel);
		crowdChat.setCrowdName(crowdName);
		crowdChat.setNumLimit(100);
		if (isHidden == 1) {
			crowdChat.setIsHidden(false);
		} else {
			crowdChat.setIsHidden(true);
		}
		crowdChat.setVerifyMode("0");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crowdChat", crowdChat);
		map.put("userId", userId);
		String json = JsonUtil.mapToJson(map);
		log.debug("json:" + json);
		log.debug("========ok==========");
		CrowdService crowdService = new CrowdService();
		// String tip = crowdService.saveCrowdChat(json);
		// log.debug("===============" + tip + "============");
		// request.getRequestDispatcher("appeal_").forward(request, response);
	}

	
	public String upload(MultipartFile file, HttpServletRequest request,
			String number) {
		System.out.println("��ʼ����ͼƬ");
		String path = request.getSession().getServletContext()
				.getRealPath("/image");
		String myPath = "image";
		log.info("==========" + path + "=============");
		String fileName = file.getOriginalFilename();
		log.debug("fileName------before:" + fileName);
		// Ϊ�ļ�����һ����һ�޶�������
		fileName = number + "_" + System.currentTimeMillis() + fileName;
		log.info("filename-------after:" + fileName);
		// �õ��ļ��ı���Ŀ¼
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		path = path + "/appeal/" + date;
		myPath = myPath + "/appeal/" + date;
		log.info("path:" + path);
		File targetFile = new File(path, fileName);
		// ���û������ļ���������һ��
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// ����
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myPath + "/" + fileName;
	}
	
	public String upload(MultipartFile file, HttpServletRequest request,
			HttpServletResponse response, String type) {
		System.out.println("��ʼ");
		String path = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/image");
		log.debug("==========" + path + "=============");
		String fileName = file.getOriginalFilename();
		log.debug("fileName------befor:" + fileName);
		// Ϊ�ļ�����һ����һ�޶�������
		fileName = makeFilename(fileName);
		log.debug("filename-------after:" + fileName);
		// �õ��ļ��ı���Ŀ¼
		path = makePath(type, path);
		log.debug("path:" + path);
		File targetFile = new File(path, fileName);
		// ���û������ļ���������һ��
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// ����
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// model.addAttribute("fileUrl",path+"/"+fileName);
		request.setAttribute("photoPath", path + "/" + fileName);
		log.info("==========" + path + "/" + fileName);
		return path + "/" + fileName;
	}

	/**
	 * ����һ����һ�޶����ļ���
	 * 
	 * @param filename
	 * @return filename
	 */
	public String makeFilename(String filename) {
		// Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		return UUID.randomUUID().toString() + "_" + filename;
	}

	/**
	 * ����ʱ���Ŀ¼�ļ���
	 * 
	 * @param filename
	 * @param savePath
	 * @return
	 */
	public String makePath(String type, String savePath) {
		// �õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ�������
		// ���ڴ��еĵ�ַ
		// (new java.text.SimpleDateFormat("yyyy/MM/dd")).format(new Date())
		/*
		 * int hashcode = filename.hashCode(); int dir1 = hashcode&0xf; //0--15
		 * int dir2 = (hashcode&0xf0)>>4; //0--15
		 */
		// �����µı����Ŀ¼
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String dir = savePath + "\\" + type + "\\" + date;// upload\2\3
															// upload\3\5
		// File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		// ���Ŀ¼������
		if (!file.exists()) {
			// ����Ŀ¼
			System.out.println("create file" + file.getName());
			file.mkdirs();
		}
		return dir;
	}
}