package com.atm.chat.nio.frame.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public class ServerFrameHandler {
	private static ServerFrame frame;
	private static ServerFrameHandler handler;

	public ServerFrameHandler() {
	}

	/**
	 * 构造函数，指定serverframe
	 * 
	 * @param frame
	 */
	public ServerFrameHandler(ServerFrame frame) {
		this.frame = frame;
	}

	/**
	 * 单例模式,获取当前的ServerFrameHandler
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
	 * 添加新结点到login子节点中
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
