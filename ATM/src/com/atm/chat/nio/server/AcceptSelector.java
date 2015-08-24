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
		log.info("acceptselector ���� receive");
		while (true) {
			int readyChannels = selector.select(10);
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
					managerReader();
					// ���˶�Ӧ��channel����Ϊ׼�����������ͻ�������
					selectionKey.interestOps(SelectionKey.OP_ACCEPT);
				}
			}
		}
	}

	/**
	 * �������е�readSelector,�����selector��Ĺܵ���������Ȼ�����һϵ�е����ϡ�
	 * 
	 * @author ye
	 * @2015.8.11
	 */
	public void listenSelector() {
		// TODO δ��� listenSeletor
		for (Iterator<ReadSelector> iterator = readSelectors.iterator(); iterator
				.hasNext();) {
			ReadSelector readSelector = iterator.next();

		}
	}

	public void managerReader() {
		ReadSelector read = null;
		// int i = 1;
		log.info("������managerReader..." + readSelectors.size());
		// for (Iterator<ReadSelector> iterator = readSelectors.iterator();
		// iterator.hasNext();) {
		int i = 0;
		for (; i < readSelectors.size(); i++) {
			// read = iterator.next();
			read = readSelectors.get(i);
			log.info("��" + i + "��readSelectors");
			log.info("num:" + channelNums.get(i));
			if (channelNums.get(i) < 3) {
				read.register(socketChannel);
				log.info("�½ӵ�socketChannel���뵽��" + i + "��readSelector,��ǰ��"
						+ readSelectors.size() + "��readSelector");
				return;
			}
		}
		// if(readSelector == null) {
		log.info("�����µ�selector");
		channelNums.add(0);
		readSelector = new ReadSelector(server, socketChannel, i);
		readSelectors.add(readSelector);
		readSelector.start();
		// }
	}
}