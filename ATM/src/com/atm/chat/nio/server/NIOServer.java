package com.atm.chat.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.ScMap;

/**
 * 网络多客户端聊天室 功能1： 客户端通过Java NIO连接到服务端，支持多客户端的连接
 * 功能2：客户端初次连接时，服务端提示输入昵称，如果昵称已经有人使用，提示重新输入，如果昵称唯一，则登录成功，之后发送消息都需要按照规定格式带着昵称发送消息
 * 功能3：客户端登录后，发送已经设置好的欢迎信息和在线人数给客户端，并且通知其他客户端该客户端上线
 * 功能4：服务器收到已登录客户端输入内容，转发至其他登录客户端。
 */
public class NIOServer implements ScMap {
	private static final Logger log = LoggerFactory.getLogger(NIOServer.class);
	private Selector selector = null;
	static final int port = 23458;
	/*public NIOServer() throws IOException{
		log.info("------------------");
		String path = ServletActionContext.getRequest().getContextPath();
		String basicPath = ServletActionContext.getRequest().getRealPath("/");
		log.info("path:" + path);
		log.info("basicPath:" + path);
		init();
	}*/
	// 用来记录在线人数，以及昵称
	/*
	 * private static HashSet<String> users = new HashSet<String>(); private
	 * ServerReceive receive = null;
	 */
	public void init() throws IOException {
		selector = Selector.open();
		ServerSocketChannel server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(port));
		// 非阻塞的方式
		server.configureBlocking(false);
		// 注册到选择器上，设置为监听状态
		server.register(selector, SelectionKey.OP_ACCEPT);
		log.info("Server is listening now...");
		AcceptSelector acceptSelector = new AcceptSelector(selector, server);
		acceptSelector.receive();
		/*
		 * System.out.println("Server is listening now..."); receive = new
		 * ServerReceive(selector, server); receive.receive();
		 */
	}

	public static void main(String[] args) throws IOException {
		new NIOServer().init();
	}
	public int selectorNum(Selector selector) {
		Set<SelectionKey> keys = selector.keys();
		return keys.size();
	}
}
