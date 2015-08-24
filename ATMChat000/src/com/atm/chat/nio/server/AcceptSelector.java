package com.atm.chat.nio.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.ScMap;

public class AcceptSelector implements ScMap {
	private static final Logger log = LoggerFactory
			.getLogger(AcceptSelector.class);
	private Selector selector = null;
	private ServerSocketChannel server = null;
	static final int port = 23457;
	private SelectionKey selectionKey;
	private ReadSelector readSelector = null;
	private SocketChannel socketChannel;
	//private List<ReadSelector> readSelectors= new ArrayList<ReadSelector>();
	
	public AcceptSelector(Selector selector, ServerSocketChannel server) {
		this.selector = selector;
		this.server = server;
	}

	public void receive() throws IOException {
		log.info("acceptselector 开启 receive");
		while (true) {
			int readyChannels = selector.select(10);
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
					managerReader();
					// 将此对应的channel设置为准备接受其他客户端请求
					selectionKey.interestOps(SelectionKey.OP_ACCEPT);
				}
			}
		}
	}

	/**
	 * 监听所有的readSelector,检测其selector里的管道的数量。然后进行一系列的整合。
	 * 
	 * @author ye
	 * @2015.8.11
	 */
	public void listenSelector() {
		// TODO 未完成 listenSeletor
		for (Iterator<ReadSelector> iterator = readSelectors.iterator(); iterator
				.hasNext();) {
			ReadSelector readSelector = iterator.next();

		}
	}

	public void managerReader() {
		ReadSelector read = null;
		// int i = 1;
		log.info("进入了managerReader..." + readSelectors.size());
		// for (Iterator<ReadSelector> iterator = readSelectors.iterator();
		// iterator.hasNext();) {
		int i = 0;
		for (; i < readSelectors.size(); i++) {
			// read = iterator.next();
			read = readSelectors.get(i);
			log.info("第" + i + "个readSelectors");
			log.info("num:" + channelNums.get(i));
			if (channelNums.get(i) < 3) {
				read.register(socketChannel);
				log.info("新接的socketChannel加入到第" + i + "个readSelector,当前有"
						+ readSelectors.size() + "个readSelector");
				return;
			}
		}
		// if(readSelector == null) {
		log.info("开启新的selector");
		channelNums.add(0);
		readSelector = new ReadSelector(server, socketChannel, i);
		readSelectors.add(readSelector);
		readSelector.start();
		// }
	}
}