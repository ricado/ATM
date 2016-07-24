package com.atm.model.define;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找用户所需要的bean
 * 
 * @author ye
 *
 */

public class FindUser {
	private String userId;
	private String nickname;
	private List<String> labels = new ArrayList<String>();

	public FindUser() {
		super();
	}

	public FindUser(String userId, String nickname) {
		super();
		this.userId = userId;
		this.nickname = nickname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	@Override
	public String toString() {
		return "FindUser [userId=" + userId + ", nickname=" + nickname
				+ ", labels=" + labels + "]";
	}

	public String toInfoString() {
		String infoString = userId + " " + nickname;
		for (int i = 0; i < labels.size(); i++) {
			infoString += " " + labels.get(i);
		}
		return infoString;
	}
}
