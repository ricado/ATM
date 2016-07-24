package com.atm.model.define;

/**
 * Login entity. @author MyEclipse Persistence Tools
 */

public class Login implements java.io.Serializable {

	// Fields

	private String userId;
	private Integer number;

	// Constructors

	/** default constructor */
	public Login() {
	}

	/** full constructor */
	public Login(String userId, Integer number) {
		this.userId = userId;
		this.number = number;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}