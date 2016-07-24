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
		log.info("acceptselector ���� receive");
		while (true) {
			int readyChannels = selector.select(20);
			if (readyChannels == 0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys(); // ����ͨ�����������֪������ͨ���ļ���
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext()) {
				selectionKey = (SelectionKey) keyIterator.next();
				keyIterator.remove();
				// �ж��Ƿ����
				if (selectionKey.isAcceptable()) {
					socketChannel = server.accept();
					managerSelector();
					// ���˶�Ӧ��channel����Ϊ׼�����������ͻ�������
					log.info("���˶�Ӧ��channel����Ϊ׼�����������ͻ�������");
					selectionKey.interestOps(SelectionKey.OP_ACCEPT);
					log.info("----------ok--------");
				}
			}
		}
	}

	/**
	 * ����readSelector
	 * 
	 * @throws IOException
	 */
	public void managerSelector() throws IOException {
		boolean flag = true;

		for (Iterator<ReadSelector> readSelectors = selectors.iterator(); readSelectors
				.hasNext();) {
			ReadSelector readSelector = (ReadSelector) readSelectors.next();
			System.out.println("��ǰreadSocketor������:" + readSelector.getSize());
			// readSelector��socketChannel������С��40
			if (readSelector.getSize() <= 1) {
				readSelectors.remove();
				System.out.println("�Ƴ���һ��selector");
			} else if (readSelector.getSize() < 30) {
				readSelector.register(socketChannel);
				flag = false;
			}
		}
		if (flag) {
			System.out.println("�����µ�readSelector....");
			// ����selector��socketChannel������������
			ReadSelector readSelector = new ReadSelector(server, socketChannel);
			// add readSelector to selectors
			selectors.add(readSelector);
			Thread thread = new Thread(readSelector);
			thread.start();
			System.out.println("size:" + readSelector.getSize());
		}
	}

}