package com.atm.springMvc.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.daoDefined.user.UserBasicInfoDAO;
import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;
import com.atm.service.user.UserService;
import com.atm.util.Application;
import com.atm.util.ImageUtil;

@RequestMapping("/user")
@Controller
public class HeadHandler implements Application {

	private static final Logger log = LoggerFactory
			.getLogger(HeadHandler.class);
	private ImageUtil imageUtil = new ImageUtil();
	private UserInfoDAO userInfoDao = (UserInfoDAO) context
			.getBean("UserInfoDAOImpl");
	private UserDAO userDao = (UserDAO) context.getBean("UserDAOImpl");
	
	@ModelAttribute
	public void getUserInfobean(
			@RequestParam(value = "userId", required = false) String userId,
			Map<String, Object> map) {
		log.info("getUserInfoBean");
		if (userId != null && !userId.equals("")) {
			log.info(userId);
			map.put("userInfo", userInfoDao.findById(userId));
		}
	}

	/**
	 * 查看用户头像
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/userHead.do")
	public String lookUserHeads(Map<String, Object> map) {
		List<User> users = (List<User>) userDao.findAll();
		List<String> headPaths = new ArrayList<String>();
		List<String> smallHeadPaths = new ArrayList<String>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			headPaths.add(imageUtil.getUserHead(user.getUserId()));
			smallHeadPaths.add(imageUtil.getUserSmallHead(user.getUserId()));
			log.info("userId:" + user.getUserId());
			log.info("headPath:" + headPaths.get(i));
			log.info("smallHeadPath:" + smallHeadPaths.get(i));
		}
		map.put("users", users);
		map.put("headPaths", headPaths);
		map.put("smallHeadPaths", smallHeadPaths);
		return "userHead";
	}

	/**
	 * ATM/user/userInfoJ.do
	 * 
	 * @param userId
	 * @param map
	 * @return userInfo json
	 */
	@ResponseBody
	@RequestMapping(value = "/userInfoJ.do", produces = "text/plain;charset=UTF-8")
	public String getUserInfoJson(Map<String, Object> map) {
		log.info("getUserInfo---");
		String tip = (String) map.get("tip");
		log.info("tip:" + tip);
		JSONObject jsonObject = new JSONObject();
		UserInfo userInfo = (UserInfo) map.get("userInfo");
		String email = new UserService().findEmailById(userInfo.getUserId());
		map.put("userInfo", userInfo);
		map.put("email", email);
		jsonObject.put("userId", userInfo.getUserId());
		jsonObject.put("name", userInfo.getName());
		jsonObject.put("nickname", userInfo.getNickname());
		jsonObject.put("headImagePath", userInfo.getHeadImagePath());
		jsonObject.put("mname", userInfo.getMajor().getMname());
		jsonObject.put("dname", userInfo.getMajor().getDepartment().getDname());
		jsonObject.put("jobTitle", userInfo.getJobTitle() == null ? ""
				: userInfo.getJobTitle());
		jsonObject.put("sign", userInfo.getSign());
		jsonObject.put("sex", userInfo.getSex());
		jsonObject.put("email", email);
		jsonObject.put("tip", tip);
		
		log.info("json:" + jsonObject.toString());
		return jsonObject.toString();
	}

	/**
	 * 
	 * @param userId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/userInfo.do", produces = "text/plain;charset=UTF-8")
	public String getUserInfo(Map<String, Object> map) {
		log.info("getUserInfo---");
		try {
			UserInfo userInfo = (UserInfo) map.get("userInfo");
			String email = new UserService()
					.findEmailById(userInfo.getUserId());
			map.put("userInfo", userInfo);
			map.put("email", email);
			log.info("aaaaa");
		} catch (Exception e) {
			return "error";
		}
		// head,sign,name,sex,jobTitle,department,major,phone email
		return "userInfo";
	}

	/**
	 *
	 * user/changeUserInfo.do
	 */
	@RequestMapping(value = "/changeUserInfoV.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String changeUserInfoJ(
			@RequestParam(value = "file", required = false) MultipartFile file,
			UserInfo userInfo, Map<String, Object> map) {
		log.info("change");
		log.info("userInfo：" + userInfo.toString());
		String nickname = userInfo.getNickname();
		try {
			if(userInfoDao.findByNickname(nickname).size()<1){
				//return 
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (file != null) {
			try {
				String path = imageUtil
						.saveUserHead(file, userInfo.getUserId());
				log.info("pothoPath:" + path);
				userInfo.setHeadImagePath(path);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("文件出错");
			}
		}
		try {
			userInfoDao.saveOrUpdate(userInfo);
		} catch (Exception e) {
			log.info("出错了");
		}
		userInfo = userInfoDao.findById(userInfo.getUserId());
		String email = new UserService().findEmailById(userInfo.getUserId());
		map.put("userId", userInfo.getUserId());
		return "redirect:/user/userInfo.do";
	}

	/**
	 * 修改用户资料信息 返回json user/changeUserInfo.do
	 */
	@RequestMapping(value = "/changeUserInfo.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String changeUserInfo(
			@RequestParam(value = "file", required = false) MultipartFile file,
			UserInfo userInfo, Map<String, Object> map) {
		log.info("change");
		log.info("userInfo：" + userInfo.toString());
		
		String nickname = userInfo.getNickname();
		try {
			//判断昵称是否已经被使用
			if(userInfoDao.findByNickname(nickname).size()<1){
				log.info("昵称未被使用");
				if (file != null) {
					try {
						String path = imageUtil
								.saveUserHead(file, userInfo.getUserId());
						log.info("pothoPath:" + path);
						userInfo.setHeadImagePath(path);
					} catch (Exception e) {
						e.printStackTrace();
						log.info("文件出错");
					}
				}
				try {
					userInfoDao.saveOrUpdate(userInfo);
					map.put("tip", "success");
				} catch (Exception e) {
					map.put("tip", "error");
					log.info("出错了");
				}
			}else{
				map.put("tip", "error");
				map.put("tip", "nicknameUsed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("tip", "error");
		}
		
		map.put("userId", userInfo.getUserId());
		//"redirect:/user/userInfo.do"
		//"redirect:/user/userInfo.do";
		return "redirect:/user/userInfoJ.do";// 重定向到获取
	}

	// text/plain;charset=UTF-8
	@ResponseBody
	@RequestMapping(value = "/userInfo1/{userId}.do", produces = "text/plain;charset=UTF-8")
	public String getUserInfo1(@PathVariable("userId") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = userInfoDao.findById(userId);
		// head,sign,name,sex,jobTitle,department,major,phone email
		String email = new UserService().findEmailById(userInfo.getUserId());
		map.put("userInfo", userInfo);
		map.put("email", email);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userInfo.getUserId());
		jsonObject.put("name", userInfo.getName());
		jsonObject.put("nickname", userInfo.getNickname());
		jsonObject.put("headImagePath", userInfo.getHeadImagePath());
		jsonObject.put("mname", userInfo.getMajor().getMname());
		jsonObject.put("dname", userInfo.getMajor().getDepartment().getDname());
		jsonObject.put("jobTitle", userInfo.getJobTitle());
		jsonObject.put("sign", userInfo.getSign());
		jsonObject.put("sex", userInfo.getSex());
		jsonObject.put("email", email);
		log.info("json:" + jsonObject.toString());
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("/userCenter.do")
	public String userCenter(Map<String,Object> map){
		String userId = (String)map.get("userId");
		UserBasicInfoDAO basicInfoDAO = (UserBasicInfoDAO)context.getBean("UserBasicInfoDAO");
		UserBasicInfo basicInfo = basicInfoDAO.findById(userId);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", basicInfo.getUserId());
		jsonObject.put("nickname", basicInfo.getNickname());
		jsonObject.put("headImagePath", basicInfo.getHeadImagePath());
		jsonObject.put("fans", basicInfo.getFansNum().toString());
		jsonObject.put("focus",basicInfo.getFocusNum().toString());
	
		log.info("json:" + jsonObject.toString());
		
		return "";
	}
	
}
