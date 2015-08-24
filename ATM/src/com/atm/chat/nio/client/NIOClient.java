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
		// 连接远程主机的IP和端口
		sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ);
		// 开辟一个新线程来读取从服务器端的数据
		Thread runnable = new ClientReceiveThread(selector);
		runnable.start();
		// 在主线程中 从键盘读取数据输入到服务器端
		Thread send = new ClientSend(sc);
		send.start();
	}
	public static void main(String[] args) throws IOException {
		new NIOClient().init();
	}
}
