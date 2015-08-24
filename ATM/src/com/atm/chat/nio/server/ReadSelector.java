package com.atm.chat.nio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.atm.chat.nio.server.handler.CrowdHandler;
import com.atm.chat.nio.server.handler.MessageHandler;
import com.atm.chat.nio.server.handler.UserHandler;
import com.atm.chat.nio.server.util.Config;
import com.atm.model.user.User;
import com.atm.service.OnlineService;
import com.atm.service.user.UserService;

public class ReadSelector extends ReadThread {
	private Selector selector = null;
	static final int port = 23457;
	public int num = 0;
	public List<SelectionKey> selectionKeys = new ArrayList<SelectionKey>();
	private MessageHandler messageHandler = new MessageHandler();
	private CrowdHandler crowdHandler = new CrowdHandler();
	private UserHandler userHandler = new UserHandler();

	// 构造函数 开启selector
	public ReadSelector(ServerSocketChannel server, SocketChannel sc, int number) {
		try {
			selector = Selector.open();
			this.number = number;
			server.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (selector == null) {
			log.info("selector is null");
		}
		register(sc);// 注册
	}

	/**
	 * run方法
	 */
	public void run() {
		try {

			log.info("readSelector 开启 run()");
			receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 注册
	 * 
	 * @param sc
	 */
	public void register(SocketChannel sc) {
		try {
			sc.configureBlocking(false);
			// 注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到selector上，之后这个连接的数据，就由这个SocketChannel处理
			sc.register(selector, SelectionKey.OP_READ);
			System.out.println("Server is listening from client :"
					+ sc.getRemoteAddress());
			channelNums.set(number, channelNums.get(number) + 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 接收消息
	 * 
	 * @throws IOException
	 */
	public void receive() throws IOException {
		log.info("readSelector 开启 receive()");
		while (true) {
			// try{
			// System.out.println("write....");
			int readyChannels = selector.select(10);
			// log.info(selector.toString());
			if (readyChannels == 0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 可以通过这个方法，知道可用通道的集合
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			// System.out.println(selectedKeys.size());
			while (keyIterator.hasNext()) {
				selectionKey = (SelectionKey) keyIterator.next();
				keyIterator.remove();
				dealWithSelectionKey(selectionKey);
			}
		}
	}

	public void dealWithSelectionKey(SelectionKey selectionKey)
			throws IOException {

		// 处理来自客户端的数据读取请求
		if (selectionKey.isReadable()) {
			// 返回该SelectionKey对应的 Channel，其中有数据需要读取
			socketChannel = (SocketChannel) selectionKey.channel();
			log.info("remoteAddress:" + socketChannel.getRemoteAddress());
			/**
			 * 获取消息类型
			 */
			int type = -100;
			try {
				log.info("type------调用getInt()");
				type = getInt();
				log.info("type:" + type);
			} catch (Exception e) {
				removeUser(socketChannel);
				log.info("readOver()");
				return;
			}
			try {
				// TODO 中转站
				if (type >= 1400) {
					//为用户的管理类型
					log.info("------------------转入userhandler");
					userHandler.setSocketChannel(socketChannel);
					userHandler.operate(type);
				} else if (type >= 1200) {
					//为群聊的管理类型
					log.info("------------------转入crowdhandler");
					crowdHandler.setSocketChannel(socketChannel);
					crowdHandler.operate(type);
				} else if (type >= 1000) {
					// 为消息类型,由消息类型处理
					log.info("------------------转入messagehandler");
					messageHandler.setSocketChannel(socketChannel);
					messageHandler.operate(type);
				} else {
					//基本操作类型
					log.info("------------------转入basiOoperate");
					basicOperate(type);
				}
			} catch (Exception e) {
				removeUser(socketChannel);
				log.info("error");
			}
		}
	}

	public void basicOperate(int type) throws IOException {
		switch (type) {
		case Config.REQUEST_LOGIN:
			login();
			break;
		default:
			readOver();
			removeUser(socketChannel);
			break;
		}
	}

	/**
	 * *****移动用户到别的
	 * 
	 * @throws IOException
	 */
	public void move() throws IOException {
		String userId = getString();
		int number = getInt();
		log.info("ReadSelector-----------------move");
		// mapkey.get(userId).cancel();
		// map.get(userId).register(readSelectors.get(number).selector,SelectionKey.OP_ACCEPT);
		readSelectors.get(number).register(map.get(userId));
		channelNums.set(number, channelNums.get(number) - 1);
		// map.get(userId).bind(local)
		OnlineService.updateLogin(userId, number);
	}

	/**
	 * 登录
	 * 
	 * @param content
	 * @param sk
	 */
	public void login() {
		// TODO 登录
		getMapInfo();
		String userId = getString();
		String userPwd = getString();

		log.info("===========登录=============");
		log.info("userId:" + userId);
		log.info("userPwd:" + userPwd);
		User user = new User(userId, userPwd);
		buffer = ByteBuffer.allocateDirect(8);
		buffer.putInt(Config.RESULT_LOGIN);
		if (isLogin(user.getUserId())) {
			log.info("已经登录了");
			buffer.putInt(Config.USER_LOGIN_ALREADY);
		} else if (UserService.login(user)) {
			log.info("=============登录成功=============");
			map.put(user.getUserId(), socketChannel);
			mapkey.put(userId, selectionKey);
			buffer.putInt(Config.SUCCESS);
			OnlineService.saveLogin(userId, number);
			log.info("保存成功");
			// sendOffLineMessage(userId);
		} else {
			log.info("登录失败");
			buffer.putInt(Config.FAILED);
		}

		writeBuffer();
		getMapInfo();
	}

	/**
	 * 判断用户是否已经登录
	 * 
	 * @param userId
	 * @return true or false
	 */
	public boolean isLogin(String userId) {
		SocketChannel sc = map.get(userId);
		if (sc == null) {
			return false;
		} else {
			return true;
		}
	}

	
	public List<SelectionKey> getSelectionKeys() {
		return this.selectionKeys;
	}

	public void setSelectionKeyls(List<SelectionKey> keys) {
		this.selectionKeys = keys;
	}
}
