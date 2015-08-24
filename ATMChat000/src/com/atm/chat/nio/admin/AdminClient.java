package com.atm.chat.nio.admin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class AdminClient {
	private Selector selector = null;
	static final int port = 23456;
	private SocketChannel sc = null;
	public AdminWriteHandler handler;

	public AdminClient() throws IOException {
		init();
	}

	public void init() throws IOException {
		selector = Selector.open();
		// ����Զ��������IP�Ͷ˿�
		sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ);
		// ����һ�����߳�����ȡ�ӷ������˵�����
		Thread runnable = new AdminReadHandler(selector);
		runnable.start();
		// �����߳��� �Ӽ��̶�ȡ�������뵽��������
		handler = new AdminWriteHandler(sc);
	}

	public AdminWriteHandler getHandler() {
		return handler;
	}
}
