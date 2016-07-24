package com.atm.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.admin.AdminClient;
import com.atm.chat.nio.admin.AdminWriteHandler;

public class AdminService {
	private static AdminWriteHandler handler;
	private static final Logger log = LoggerFactory
			.getLogger(AdminService.class);
	
	public String deleteLogin(String userId) {
		log.info("delete...............");
		log.info("handler:" + handler.toString());
		if (handler == null) {
			log.info("handler 为null");
		} else {
			handler.delete(userId);
			log.info("删除成功");
		}
		return new OnlineService().getLoginList();
	}

	/**
	 * ****** 管理员的登录
	 * 
	 * @param userId
	 * @param userPwd
	 */
	public void login(String userId, String userPwd) {
		try {
			AdminClient adminClient = new AdminClient();
			handler = adminClient.getHandler();
			handler.login(userId, userPwd);
			log.info("连接成功");
		} catch (Exception e) {
			log.info("连接失败");
		}
	}

	/**
	 * ****** 移动用户到别的readSelector
	 * 
	 * @param userId
	 * @param number
	 */
	public void move(String userId, int number) {
		try {
			log.info("move to other ReadSelctor...");
			handler.move(userId, number);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Set<String> getLoginList(){
		return handler.getMap().keySet();
	}
	
}
