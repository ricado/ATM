package com.atm.chat.nio.frame.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public class ServerFrameHandler {
	private static ServerFrame frame;
	private static ServerFrameHandler handler;

	public ServerFrameHandler() {
	}

	/**
	 * ���캯����ָ��serverframe
	 * 
	 * @param frame
	 */
	public ServerFrameHandler(ServerFrame frame) {
		this.frame = frame;
	}

	/**
	 * ����ģʽ,��ȡ��ǰ��ServerFrameHandler
	 * 
	 * @return
	 */
	public static ServerFrameHandler getHandler() {
		if (handler == null) {
			handler = new ServerFrameHandler();
			handler.frame = frame;
		}
		return handler;
	}

	/**
	 * ����½�㵽login�ӽڵ���
	 * 
	 * @param name
	 */
	public void addNodeToLogin(String name) {
		frame.getLoginNode().add(new DefaultMutableTreeNode(name));
		frame.getTree().updateUI();
	}

	public void addNode() {

	}

}
