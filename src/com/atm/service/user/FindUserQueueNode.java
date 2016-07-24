package com.atm.service.user;

import com.atm.model.user.UserInfo;

public class FindUserQueueNode {
	private UserInfo userInfo;
	private int priorityValue;

	private FindUserQueueNode next;
	private FindUserQueueNode last;

	public FindUserQueueNode(UserInfo userInfo, int priorityValue) {
		this(userInfo, priorityValue, null, null);
	}
	
	public FindUserQueueNode(UserInfo userInfo, int priorityValue,
			FindUserQueueNode next, FindUserQueueNode last) {
		super();
		this.userInfo = userInfo;
		this.priorityValue = priorityValue;
		this.next = next;
		this.last = last;
	}



	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public int getPriorityValue() {
		return priorityValue;
	}

	public void setPriorityValue(int priorityValue) {
		this.priorityValue = priorityValue;
	}

	public FindUserQueueNode getNext() {
		return next;
	}

	public void setNext(FindUserQueueNode next) {
		this.next = next;
	}

	public FindUserQueueNode getLast() {
		return last;
	}

	public void setLast(FindUserQueueNode last) {
		this.last = last;
	}
}
