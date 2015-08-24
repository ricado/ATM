package com.atm.model.define.user;

public class UserList {
	private String userId;
	private String nickname;
	private String dName;
	private String headImagePath;
	private String sex;
	public UserList(){
		
	}
	public UserList(String userId,String nickname,String headImagePath,
			String sex){
		this.userId = userId;
		this.nickname = nickname;
		this.headImagePath = headImagePath;
		this.sex = sex;
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
	
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getHeadImagePath() {
		return headImagePath;
	}
	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
