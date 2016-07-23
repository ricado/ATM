package com.atm.model.define.user;

/**
 * UserLabelView entity. @author MyEclipse Persistence Tools
 */

public class UserLabelView implements java.io.Serializable {

	// Fields

	private UserLabelViewId id;

	// Constructors

	/** default constructor */
	public UserLabelView() {
	}

	/** full constructor */
	public UserLabelView(UserLabelViewId id) {
		this.id = id;
	}

	// Property accessors

	public UserLabelViewId getId() {
		return this.id;
	}

	public void setId(UserLabelViewId id) {
		this.id = id;
	}

}