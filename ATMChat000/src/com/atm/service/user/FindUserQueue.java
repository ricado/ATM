package com.atm.service.user;

import java.util.List;

import com.atm.model.user.UserInfo;

/**
 * �����û���ƥ������ȼ�����
 * 
 * @author ye
 *
 */
public class FindUserQueue {
	FindUserQueueNode head;
	FindUserQueueNode end;

	public FindUserQueue() {
	}

	public void pop(UserInfo userInfo, int priorityValue) {
		FindUserQueueNode node = new FindUserQueueNode(userInfo, priorityValue);
		FindUserQueueNode p = end;
		if (head == null) {
			head = node;
			end = head;
		} else {
			if (head.getNext() == null) {
				if (head.getPriorityValue() < node.getPriorityValue()) {

				}
				head.setNext(node);
				node.setLast(head);
			} else {
				end.setNext(node);
				node.setLast(end);
			}
			end = node;
		}

	}
}
