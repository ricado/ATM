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

	// ���캯�� ����selector
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
		register(sc);// ע��
	}

	/**
	 * run����
	 */
	public void run() {
		try {

			log.info("readSelector ���� run()");
			receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ע��
	 * 
	 * @param sc
	 */
	public void register(SocketChannel sc) {
		try {
			sc.configureBlocking(false);
			// ע��ѡ������������Ϊ��ȡģʽ���յ�һ����������Ȼ����һ��SocketChannel����ע�ᵽselector�ϣ�֮��������ӵ����ݣ��������SocketChannel����
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
	 * ������Ϣ
	 * 
	 * @throws IOException
	 */
	public void receive() throws IOException {
		log.info("readSelector ���� receive()");
		while (true) {
			// try{
			// System.out.println("write....");
			int readyChannels = selector.select(10);
			// log.info(selector.toString());
			if (readyChannels == 0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys(); // ����ͨ�����������֪������ͨ���ļ���
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

		// �������Կͻ��˵����ݶ�ȡ����
		if (selectionKey.isReadable()) {
			// ���ظ�SelectionKey��Ӧ�� Channel��������������Ҫ��ȡ
			socketChannel = (SocketChannel) selectionKey.channel();
			log.info("remoteAddress:" + socketChannel.getRemoteAddress());
			/**
			 * ��ȡ��Ϣ����
			 */
			int type = -100;
			try {
				log.info("type------����getInt()");
				type = getInt();
				log.info("type:" + type);
			} catch (Exception e) {
				removeUser(socketChannel);
				log.info("readOver()");
				return;
			}
			try {
				// TODO ��תվ
				if (type >= 1400) {
					//Ϊ�û��Ĺ�������
					log.info("------------------ת��userhandler");
					userHandler.setSocketChannel(socketChannel);
					userHandler.operate(type);
				} else if (type >= 1200) {
					//ΪȺ�ĵĹ�������
					log.info("------------------ת��crowdhandler");
					crowdHandler.setSocketChannel(socketChannel);
					crowdHandler.operate(type);
				} else if (type >= 1000) {
					// Ϊ��Ϣ����,����Ϣ���ʹ���
					log.info("------------------ת��messagehandler");
					messageHandler.setSocketChannel(socketChannel);
					messageHandler.operate(type);
				} else {
					//������������
					log.info("------------------ת��basiOoperate");
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
	 * *****�ƶ��û������
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
	 * ��¼
	 * 
	 * @param content
	 * @param sk
	 */
	public void login() {
		// TODO ��¼
		getMapInfo();
		String userId = getString();
		String userPwd = getString();

		log.info("===========��¼=============");
		log.info("userId:" + userId);
		log.info("userPwd:" + userPwd);
		User user = new User(userId, userPwd);
		buffer = ByteBuffer.allocateDirect(8);
		buffer.putInt(Config.RESULT_LOGIN);
		if (isLogin(user.getUserId())) {
			log.info("�Ѿ���¼��");
			buffer.putInt(Config.USER_LOGIN_ALREADY);
		} else if (UserService.login(user)) {
			log.info("=============��¼�ɹ�=============");
			map.put(user.getUserId(), socketChannel);
			mapkey.put(userId, selectionKey);
			buffer.putInt(Config.SUCCESS);
			OnlineService.saveLogin(userId, number);
			log.info("����ɹ�");
			// sendOffLineMessage(userId);
		} else {
			log.info("��¼ʧ��");
			buffer.putInt(Config.FAILED);
		}

		writeBuffer();
		getMapInfo();
	}

	/**
	 * �ж��û��Ƿ��Ѿ���¼
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
