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

import org.apache.struts2.components.Else;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.handler.CrowdHandler;
import com.atm.chat.nio.server.handler.MessageHandler;
import com.atm.chat.nio.server.handler.MyMessageHandler;
import com.atm.chat.nio.server.handler.UserHandler;
import com.atm.chat.nio.server.util.Config;
import com.atm.model.user.User;
import com.atm.service.user.UserService;

public class ReadSelector extends ReadThread {
	private static Logger log = LoggerFactory.getLogger(ReadSelector.class);
	private Selector selector = null;
	static final int port = 23457;
	public int num = 0;
	public List<SelectionKey> selectionKeys = new ArrayList<SelectionKey>();
	private MessageHandler messageHandler = new MessageHandler();
	private CrowdHandler crowdHandler = new CrowdHandler();
	private UserHandler userHandler = new UserHandler();
	private MyMessageHandler myMessageHandler = new MyMessageHandler();

	// 构造函数 开启selector
	public ReadSelector(ServerSocketChannel server, SocketChannel sc) {
		log.info("readSelector");
		try {
			log.info("构造函数 readselector");
			selector = Selector.open();
			log.info("注册。。。服务器");
			selector.wakeup();
			server.register(selector, SelectionKey.OP_ACCEPT);
			log.info("注册成功");
		} catch (IOException e) {
			log.info("失败。。。。。。。。。。。。。。。。");
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
		} catch (IOException | InterruptedException e) {
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
			synchronized (gate) {
				log.info("进入注册一个socketChannel");
				sc.configureBlocking(false);
				log.info("注册socketchannel--唤醒selector");
				selector.wakeup();
				log.info("唤醒完毕");
				// 注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到selector上，之后这个连接的数据，就由这个SocketChannel处理
				sc.register(selector, SelectionKey.OP_READ);
			}
			log.info("Server is listening from client :" + sc.getRemoteAddress());
			log.info("注册选择器陈功");
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
			log.info("注册失败");
		}
	}

	//
	private Object gate = new Object();

	/**
	 * 接收消息
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void receive() throws IOException, InterruptedException {
		log.info("readSelector 开启 receive()");
		while (true) {
			// try{
			// System.out.println("write....");
			// selector.wait(50);
			synchronized (gate) {
			}
			int readyChannels = selector.select(20);
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

	public void dealWithSelectionKey(SelectionKey selectionKey) throws IOException {

		// 处理来自客户端的数据读取请求
		if (selectionKey.isReadable()) {
			log.info("=-----selectionkey可读");
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
					// 为用户的管理类型
					log.info("------------------转入userhandler");
					userHandler.setSocketChannel(socketChannel);
					userHandler.operate(type);
				} else if (type >= 1200) {
					// 为群聊的管理类型
					log.info("------------------转入crowdhandler");
					crowdHandler.setSocketChannel(socketChannel);
					crowdHandler.operate(type);
				} else if (type > 1150) {
					log.info("------------------转入mymessageHandler");
					myMessageHandler.setSocketChannel(socketChannel);
					myMessageHandler.operate(type);
				} else if (type >= 1000) {
					// 为消息类型,由消息类型处理
					log.info("------------------转入messagehandler");
					messageHandler.setSocketChannel(socketChannel);
					messageHandler.operate(type);
				} else {
					// 基本操作类型
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
		case Config.REQUEST_EXIT:
			exit();
			break;
		default:
			// readOver();
			removeUser(socketChannel);
			break;
		}
	}

	/**
	 * 登录
	 * 
	 * @param content
	 * @param sk
	 * @throws IOException
	 */
	public void login() throws IOException {
		// TODO 登录
		getMapInfo();
		String userId = getString();
		String userPwd = getString();
		log.info("===========登录=============");
		log.info("userId:" + userId);
		log.info("userPwd:" + userPwd);
		User user = new User(userId, userPwd);
		// 验证账号密码是否正确
		if (UserService.login(user)) {
			if (isLogin(userId)) {
				log.info("有人登录了,逼他下线");
				buffer.clear();
				buffer = ByteBuffer.allocateDirect(4);
				buffer.putInt(Config.REQUEST_BE_OFF);
				// log.info("user -- socket:" +
				// map.get(userId).getLocalAddress());
				writeBuffer(map.get(userId));
				exit(userId);
				// buffer.putInt(Config.USER_LOGIN_ALREADY);
			}
			log.info("=============登录成功=============");
			map.put(user.getUserId(), socketChannel);
			mapkey.put(userId, selectionKey);
			buffer.clear();
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.RESULT_LOGIN);
			buffer.putInt(Config.SUCCESS);
			// OnlineService.saveLogin(userId, number);
			log.info("保存成功");
			// sendOffLineMessage(userId);
		} else {
			log.info("登录失败");
			buffer.clear();
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.RESULT_LOGIN);
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
	 * @throws IOException
	 */
	public boolean isLogin(String userId) throws IOException {

		for (String id : map.keySet()) {
			if (id.equals(userId)) {
				log.info("用户已经登录了");
				return true;
			}
		}
		return false;

		/*
		 * SocketChannel sc = map.get(userId); if (sc == null) { return false; }
		 * else{ log.info("用户已经登录"); // log.info("socket:" +
		 * sc.getLocalAddress().toString()); return true; }
		 */
	}

	public List<SelectionKey> getSelectionKeys() {
		return this.selectionKeys;
	}

	public void setSelectionKeyls(List<SelectionKey> keys) {
		this.selectionKeys = keys;
	}

	/**
	 * 获取当前连接的socketchannel的数量
	 * 
	 * @return
	 * @throws IOException
	 */
	public int getSize() throws IOException {
		return selector.keys().size();
	}
}
