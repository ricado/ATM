package com.atm.chat.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.ScMap;

/**
 * �����ͻ��������� ����1�� �ͻ���ͨ��Java NIO���ӵ�����ˣ�֧�ֶ�ͻ��˵�����
 * ����2���ͻ��˳�������ʱ���������ʾ�����ǳƣ�����ǳ��Ѿ�����ʹ�ã���ʾ�������룬����ǳ�Ψһ�����¼�ɹ���֮������Ϣ����Ҫ���չ涨��ʽ�����ǳƷ�����Ϣ
 * ����3���ͻ��˵�¼�󣬷����Ѿ����úõĻ�ӭ��Ϣ�������������ͻ��ˣ�����֪ͨ�����ͻ��˸ÿͻ�������
 * ����4���������յ��ѵ�¼�ͻ����������ݣ�ת����������¼�ͻ��ˡ�
 */
public class NIOServer implements ScMap {
	private static final Logger log = LoggerFactory.getLogger(NIOServer.class);
	private Selector selector = null;
	static final int port = 23458;
	/*public NIOServer() throws IOException{
		log.info("------------------");
		String path = ServletActionContext.getRequest().getContextPath();
		String basicPath = ServletActionContext.getRequest().getRealPath("/");
		log.info("path:" + path);
		log.info("basicPath:" + path);
		init();
	}*/
	// ������¼�����������Լ��ǳ�
	/*
	 * private static HashSet<String> users = new HashSet<String>(); private
	 * ServerReceive receive = null;
	 */
	public void init() throws IOException {
		selector = Selector.open();
		ServerSocketChannel server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(port));
		// �������ķ�ʽ
		server.configureBlocking(false);
		// ע�ᵽѡ�����ϣ�����Ϊ����״̬
		server.register(selector, SelectionKey.OP_ACCEPT);
		log.info("Server is listening now...");
		AcceptSelector acceptSelector = new AcceptSelector(selector, server);
		acceptSelector.receive();
		/*
		 * System.out.println("Server is listening now..."); receive = new
		 * ServerReceive(selector, server); receive.receive();
		 */
	}

	public static void main(String[] args) throws IOException {
		new NIOServer().init();
	}
	public int selectorNum(Selector selector) {
		Set<SelectionKey> keys = selector.keys();
		return keys.size();
	}
}
