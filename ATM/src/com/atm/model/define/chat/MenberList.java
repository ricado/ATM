package com.atm.model.define.chat;

public class MenberList {
	private String userId;
	private String nickname;
	private String headImagePath;
	private int roleId;
	public MenberList(){};
	public MenberList(String userId,String nickname,String headImagePath,int roleId){
		this.userId = userId;
		this.nickname = nickname;
		this.headImagePath = headImagePath;
		this.roleId = roleId;
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
	public String getHeadImagePath() {
		return headImagePath;
	}
	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
