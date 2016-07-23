package com.atm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Path {
	private static final Logger log = LoggerFactory.getLogger(Path.class);

	private static String basicPath;

	private static String userPath;

	private static String crowdPath;

	private static String defaultUserHeadPath;

	private static String defaultUserSHeadPath;

	private static String defaultCrowdHeadPath;

	private static String defaultCrowdSHeadPath;

	private static String crowdChatPath;
	private static String privateChatPath;
	
	public static final String crowdHeadName = "crowd.jpg";
	public static final String crowdSHeadName = "crowd_small.jpg";
	public static final String userHeadName = "user.jpg";
	public static final String userSHeadName = "user_small.jpg";

	// 构造方法,初始化
	public Path() {
		basicPath = this.getClass().getResource("/").getPath().substring(1)
				.replaceAll("WEB-INF/classes/", "");
		log.info("basicpath:" + basicPath);
		userPath = basicPath + "image/headImage/user/";
		log.info("userpath:" + userPath);
		crowdPath = basicPath + "image/headImage/crowd/";
		log.info("crowdpath:" + crowdPath);
		
		/**
		 * 群聊
		 */
		crowdChatPath = basicPath + "image/chat/crowd/";
		log.info("crowdChatPath:" + crowdChatPath);
		
		/**
		 * 私聊
		 */
		privateChatPath = basicPath + "image/chat/privateChat/";
		log.info("privateChatPath:" + privateChatPath);
		
		defaultUserHeadPath = basicPath + "image/headImage/default/user.jpg";
		defaultUserSHeadPath = basicPath + "image/headImage/default/user_small.jpg";
		defaultCrowdHeadPath = basicPath + "image/headImage/default/crowd.jpg";
		defaultCrowdSHeadPath = basicPath
				+ "image/headImage/default/crowd_small.jpg";
	}

	public static String getBasicPath() {
		return basicPath;
	}

	public static String getUserPath() {
		return userPath;
	}

	public static String getCrowdPath() {
		return crowdPath;
	}

	public static String getDefaultUserHeadPath() {
		return defaultUserHeadPath;
	}

	public static String getDefaultUserSHeadPath() {
		return defaultUserSHeadPath;
	}

	public static String getDefaultCrowdHeadPath() {
		return defaultCrowdHeadPath;
	}

	public static String getDefaultCrowdSHeadPath() {
		return defaultCrowdSHeadPath;
	}

	public static String getCrowdChatPath() {
		return crowdChatPath;
	}

	public static String getPrivateChatPath() {
		return privateChatPath;
	}
}
