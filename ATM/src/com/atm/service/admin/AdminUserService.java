package com.atm.service.admin;

import java.util.List;

import com.atm.daoDefined.user.UserBasicInfoDAO;
import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.user.UserInfo;
import com.atm.service.user.UserService;

public class AdminUserService extends UserService {
	public static String findAllUserList(int first,int max) {
		System.out.println("find All userList");
		UserBasicInfoDAO infoDAO = (UserBasicInfoDAO) context
				.getBean("UserBasicInfoDAO");
		// List<UserInfo> userList = infoDAO.findAll();
		List<UserBasicInfo> userInfos = infoDAO.findAll(first,max);
		String userString = makeUserBasicsToHtml(userInfos);
		return userString;
	}

	public static String makeUserBasicsToHtml(List<UserBasicInfo> userBasicInfos) {
		String userString = "";
		for (int i = 0; i < userBasicInfos.size(); i++) {
			userString += makeUserBasicToHtml(userBasicInfos.get(i), i);
		}
		return userString;
	}

	public static String makeUserBasicToHtml(UserBasicInfo userBasicInfo, int i) {
		return "<div id='content_list'>"
				+ "<dl class='user_head'>"
				+ "<dt class='headImage'>" + "<img src='"
				+ userBasicInfo.getHeadImagePath() + "' />" + "</dt>" + "</dl>"
				+ "<dl class='users_info'>"
				+ "<input type='hidden' class='userId' value='"
				+ userBasicInfo.getUserId() + "' id='userId' />"
				+ "<dt class='username'>" + userBasicInfo.getNickname()
				+ "</dt>" + "<dt class='usersInfo'>" + "<span>"
				+ userBasicInfo.getScName() + "</span><span id='f'>|</span>"
				+ "<span>" + userBasicInfo.getDname() + "</span>" + "</dt>"
				+ "</dl>" + "<dl class='focus_fans'>" + "<dd id='focus_num'>"
				+ "<b><a target='_blank' href=''>"
				+ userBasicInfo.getFocusNum() + "</a></b><br>¹Ø×¢" + "</dd>"
				+ "<dd id='fans_num'>" + "<b><a target='_blank' href=''>"
				+ userBasicInfo.getFansNum() + "</a></b><br>·ÛË¿" + "</dd>"
				+ "</dl></div>";
	}

	public String getUserList(UserInfo userInfo) {
		// userList += "";
		return null;
	}
}
