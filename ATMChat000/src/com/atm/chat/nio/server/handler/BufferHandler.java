package com.atm.chat.nio.server.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.ScMap;

public class BufferHandler implements ScMap {
	public static final Logger log = LoggerFactory
			.getLogger(BufferHandler.class);
	public static ByteBuffer buffer;
	public static Charset charset = Charset.forName("GBK");
	public SocketChannel socketChannel;
	public static int size;
	public static byte[] bytes;

	/**
	 * ��buffer��ȡint����
	 * 
	 * @return
	 * @throws IOException
	 */
	public int getInt() throws IOException {
		// TODO getInt
		buffer = ByteBuffer.allocateDirect(4);
		size = socketChannel.read(buffer);
		if (size >= 0) {
			buffer.flip();
			int userIdnum = buffer.getInt();
			buffer.clear();
			log.info("length:" + userIdnum);
			return userIdnum;
		}
		return -100;
	}

	/**
	 * ��bugger��ȡString
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getString() throws Exception {
		// TODO getString
		try {
			buffer = ByteBuffer.allocateDirect(getInt());
			size = socketChannel.read(buffer);
			bytes = new byte[size];
			if (size >= 0) {
				buffer.flip();
				buffer.get(bytes);
				buffer.clear();
				log.info("String:" + new String(bytes, "UTF-8"));
			}
			return new String(bytes, "GBK");
		} catch (Exception e) {
			log.info("read error");
			throw e;
		}
	}

	/**
	 * ��buffer�з��ַ������ֽ����鳤���Լ����ֽ�����
	 * 
	 * @param string
	 * @throws UnsupportedEncodingException
	 */
	public void put(String string) {
		// TODO put
		try {
			buffer.putInt(string.getBytes("GBK").length);
			buffer.put(string.getBytes("GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void writeBuffer() { buffer.flip();// ��buffer����ת�� while
	 * (buffer.hasRemaining()) { int n = 0; try { n =
	 * socketChannel.write(buffer); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * if (n == 0 && buffer.remaining() > 0) { socketChannel.register(selector,
	 * SelectionKey.OP_READ); break; }
	 * 
	 * key = socketChannel.register(selector, SelectionKey.OP_WRITE); } }
	 */

	/**
	 * �Խ�bufferд��socketchannel.
	 */
	public void writeBuffer() {
		// TODO writeBuffer
		buffer.flip();// ��buffer����ת��
		/*
		 * ByteBuffer[] buffers = null; int l = buffer.limit()/200000;
		 */
		while (buffer.hasRemaining()) {
			try {
				// socketChannel.configureBlocking(true);
				int y = socketChannel.write(buffer);
				log.info("---buffer.limit:" + buffer.limit());
				log.info("---buffer.position:" + buffer.position());
				log.info("Y:" + y);
				log.info("д��ɹ�");
			} catch (Exception e) {
				e.printStackTrace();
				log.info("д��ʧ��");
			}
		}
		buffer.clear();
	}

	public void writeBuffer(ByteBuffer[] buffers) {
		// TODO writeBuffer
		// buffer.flip();// ��buffer����ת��
		/*
		 * ByteBuffer[] buffers = null; int l = buffer.limit()/200000;
		 */
		try {
			long y = socketChannel.write(buffers);
			log.info("--------------Y:" + y);
			for (int i = 0; i < buffers.length; i++) {
				log.info("buffers" + i + ":" + buffers[i].position());
			}
			long t = socketChannel.write(buffers);
			log.info("--------------Y:" + y);
			for (int i = 0; i < buffers.length; i++) {
				log.info("buffers" + i + ":" + buffers[i].position());
			}
			// buffer.clear();
			log.info("д��ɹ�");
		} catch (Exception e) {
			log.info("д��ʧ��");
		}
	}

	/**
	 * �Խ�bufferд��socketchannel.
	 */
	public void writeBuffer(SocketChannel channel) {
		// TODO writeBuffer
		buffer.flip();// ��buffer����ת��
		try {
			channel.write(buffer);
			buffer.clear();
			log.info("д��ɹ�");
		} catch (Exception e) {
			log.info("д��ʧ��");
		}
	}

	/**
	 * ��ȡ��¼map����Ϣ
	 */
	public void getMapInfo() {
		Set<String> keys = map.keySet();
		log.info("------------mapInfo--------------");
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			try {
				log.info("userId:" + key + "channel:"
						+ map.get(key).getRemoteAddress());
			} catch (IOException e) {
			}
		}
		log.info("---------------end----------------");
	}

	public void writeInt(int a) {
		try {
			ByteBuffer buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(a);
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
		} catch (Exception e) {
			log.info("error");
		}
	}

	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
}
