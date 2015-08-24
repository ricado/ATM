package com.atm.chat.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NIOClient {

	private Selector selector = null;
	static final int port = 23456;
	private SocketChannel sc = null;
	//
	private FileChannel fileChannel = null;

	public void init() throws IOException {
		selector = Selector.open();
		// ����Զ��������IP�Ͷ˿�
		sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ);
		// ����һ�����߳�����ȡ�ӷ������˵�����
		Thread runnable = new ClientReceiveThread(selector);
		runnable.start();
		// �����߳��� �Ӽ��̶�ȡ�������뵽��������
		Thread send = new ClientSend(sc);
		send.start();
	}
	public static void main(String[] args) throws IOException {
		new NIOClient().init();
	}
}
