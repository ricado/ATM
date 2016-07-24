package com.atm.chat.nio.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.ScMap;

public class AcceptSelector implements ScMap {
	private static final Logger log = LoggerFactory
			.getLogger(AcceptSelector.class);
	private Selector selector;
	private ServerSocketChannel server = null;
	static final int port = 23457;
	private SelectionKey selectionKey;
	private SocketChannel socketChannel;

	private ArrayList<ReadSelector> selectors = new ArrayList<ReadSelector>();

	public AcceptSelector(Selector selector, ServerSocketChannel server) {
		this.selector = selector;
		this.server = server;
	}

	public void receive() throws IOException {
		log.info("acceptselector 开启 receive");
		while (true) {
			int readyChannels = selector.select(20);
			if (readyChannels == 0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 可以通过这个方法，知道可用通道的集合
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext()) {
				selectionKey = (SelectionKey) keyIterator.next();
				keyIterator.remove();
				// 判断是否接收
				if (selectionKey.isAcceptable()) {
					socketChannel = server.accept();
					managerSelector();
					// 将此对应的channel设置为准备接受其他客户端请求
					log.info("将此对应的channel设置为准备接受其他客户端请求");
					selectionKey.interestOps(SelectionKey.OP_ACCEPT);
					log.info("----------ok--------");
				}
			}
		}
	}

	/**
	 * 管理readSelector
	 * 
	 * @throws IOException
	 */
	public void managerSelector() throws IOException {
		boolean flag = true;

		for (Iterator<ReadSelector> readSelectors = selectors.iterator(); readSelectors
				.hasNext();) {
			ReadSelector readSelector = (ReadSelector) readSelectors.next();
			System.out.println("当前readSocketor的数量:" + readSelector.getSize());
			// readSelector的socketChannel的数量小于40
			if (readSelector.getSize() <= 1) {
				readSelectors.remove();
				System.out.println("移除了一个selector");
			} else if (readSelector.getSize() < 30) {
				readSelector.register(socketChannel);
				flag = false;
			}
		}
		if (flag) {
			System.out.println("开启新的readSelector....");
			// 所有selector的socketChannel的数量都超过
			ReadSelector readSelector = new ReadSelector(server, socketChannel);
			// add readSelector to selectors
			selectors.add(readSelector);
			Thread thread = new Thread(readSelector);
			thread.start();
			System.out.println("size:" + readSelector.getSize());
		}
	}

}