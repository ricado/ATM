package com.atm.chat.nio.newServer;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.handler.CrowdHandler;
import com.atm.chat.nio.server.handler.MessageHandler;
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

	// ���캯�� ����selector
	public ReadSelector(ServerSocketChannel server, SocketChannel sc) {
		log.info("readSelector");
		try {
			log.info("���캯�� readselector");
			selector = Selector.open();
			log.info("ע�ᡣ����������");
			selector.wakeup();
			server.register(selector, SelectionKey.OP_ACCEPT);
			log.info("ע��ɹ�");
		} catch (IOException e) {
			log.info("ʧ�ܡ�������������������������������");
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
		} catch (IOException | InterruptedException e) {
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
			synchronized (gate) {
				log.info("����ע��һ��socketChannel");
				sc.configureBlocking(false);
				log.info("ע��socketchannel--����selector");
				selector.wakeup();
				log.info("�������");
				// ע��ѡ������������Ϊ��ȡģʽ���յ�һ����������Ȼ����һ��SocketChannel����ע�ᵽselector�ϣ�֮��������ӵ����ݣ��������SocketChannel����
				sc.register(selector, SelectionKey.OP_READ);
			}
			log.info("Server is listening from client :"
					+ sc.getRemoteAddress());
			log.info("ע��ѡ�����¹�");
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
			log.info("ע��ʧ��");
		}
	}

	//
	private Object gate = new Object();

	/**
	 * ������Ϣ
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void receive() throws IOException, InterruptedException {
		log.info("readSelector ���� receive()");
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
			log.info("=-----selectionkey�ɶ�");
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
					// Ϊ�û��Ĺ�������
					log.info("------------------ת��userhandler");
					userHandler.setSocketChannel(socketChannel);
					userHandler.operate(type);
				} else if (type >= 1200) {
					// ΪȺ�ĵĹ�������
					log.info("------------------ת��crowdhandler");
					crowdHandler.setSocketChannel(socketChannel);
					crowdHandler.operate(type);
				} else if (type >= 1000) {
					// Ϊ��Ϣ����,����Ϣ���ʹ���
					log.info("------------------ת��messagehandler");
					messageHandler.setSocketChannel(socketChannel);
					messageHandler.operate(type);
				} else {
					// ������������
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
	 * ��¼
	 * 
	 * @param content
	 * @param sk
	 * @throws IOException
	 */
	public void login() throws IOException {
		// TODO ��¼
		getMapInfo();
		String userId = getString();
		String userPwd = getString();
		log.info("===========��¼=============");
		log.info("userId:" + userId);
		log.info("userPwd:" + userPwd);
		User user = new User(userId, userPwd);
		// ��֤�˺������Ƿ���ȷ
		if (UserService.login(user)) {
			if (isLogin(userId)) {
				log.info("���˵�¼��,��������");
				buffer.clear();
				buffer = ByteBuffer.allocateDirect(4);
				buffer.putInt(Config.REQUEST_BE_OFF);
				// log.info("user -- socket:" +
				// map.get(userId).getLocalAddress());
				writeBuffer(map.get(userId));
				exit(userId);
				// buffer.putInt(Config.USER_LOGIN_ALREADY);
			}
			log.info("=============��¼�ɹ�=============");
			map.put(user.getUserId(), socketChannel);
			mapkey.put(userId, selectionKey);
			buffer.clear();
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.RESULT_LOGIN);
			buffer.putInt(Config.SUCCESS);
			// OnlineService.saveLogin(userId, number);
			log.info("����ɹ�");
			// sendOffLineMessage(userId);
		} else {
			log.info("��¼ʧ��");
			buffer.clear();
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.RESULT_LOGIN);
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
	 * @throws IOException
	 */
	public boolean isLogin(String userId) throws IOException {

		for (String id : map.keySet()) {
			if (id.equals(userId)) {
				log.info("�û��Ѿ���¼��");
				return true;
			}
		}
		return false;

		/*
		 * SocketChannel sc = map.get(userId); if (sc == null) { return false; }
		 * else{ log.info("�û��Ѿ���¼"); // log.info("socket:" +
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
	 * ��ȡ��ǰ���ӵ�socketchannel������
	 * 
	 * @return
	 * @throws IOException
	 */
	public int getSize() throws IOException {
		return selector.keys().size();
	}
}
