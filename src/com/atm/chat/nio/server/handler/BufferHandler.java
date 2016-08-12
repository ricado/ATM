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

import com.atm.chat.nio.server.util.Config;
import com.atm.chat.nio.server.util.ScMap;

public class BufferHandler implements ScMap {
	public static final Logger log = LoggerFactory.getLogger(BufferHandler.class);
	public static ByteBuffer buffer;
	public static Charset charset = Charset.forName("GBK");
	public SocketChannel socketChannel;
	public static int size;
	public static byte[] bytes;

	/**
	 * 锟斤拷buffer锟斤拷取int锟斤拷锟斤拷
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
	 * 锟斤拷bugger锟斤拷取String
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
			return new String(bytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("read error");
			throw e;
		}
	}

	/**
	 * 锟斤拷buffer锟叫凤拷锟街凤拷锟斤拷锟斤拷锟街斤拷锟斤拷锟介长锟斤拷锟皆硷拷锟斤拷锟街斤拷锟斤拷锟斤拷
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
	 * public void writeBuffer() { buffer.flip();// 锟斤拷buffer锟斤拷锟斤拷转锟斤拷 while
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
	 * 锟皆斤拷buffer写锟斤拷socketchannel.
	 */
	public void writeBuffer() {
		// TODO writeBuffer
		buffer.flip();// 锟斤拷buffer锟斤拷锟斤拷转锟斤拷
		/*
		 * ByteBuffer[] buffers = null; int l = buffer.limit()/200000;
		 */
		while (buffer.hasRemaining()) {
			try {
				// socketChannel.configureBlocking(true);
				if (socketChannel == null) {
					log.info("socketChannel为锟斤拷");
					return;
				}
				int y = socketChannel.write(buffer);
				if (y > 0) {
					log.info("---buffer.limit:" + buffer.limit());
					log.info("---buffer.position:" + buffer.position());
					log.info("Y:" + y);
					log.info("写锟斤拷晒锟�");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		buffer.clear();
	}

	public void writeBuffer(ByteBuffer[] buffers) {
		// TODO writeBuffer
		// buffer.flip();// 锟斤拷buffer锟斤拷锟斤拷转锟斤拷
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
		} catch (Exception e) {
		}
	}

	/**
	 * 锟皆斤拷buffer写锟斤拷socketchannel.
	 */
	public void writeBuffer(SocketChannel channel) {
		// TODO writeBuffer
		buffer.flip();// 锟斤拷buffer锟斤拷锟斤拷转锟斤拷
		/*
		 * ByteBuffer[] buffers = null; int l = buffer.limit()/200000;
		 */
		while (buffer.hasRemaining()) {
			try {
				// socketChannel.configureBlocking(true);
				if (channel == null) {
					log.info("channel为锟斤拷");
					return;
				}
				int y = channel.write(buffer);
				if (y > 0) {
					log.info("---buffer.limit:" + buffer.limit());
					log.info("---buffer.position:" + buffer.position());
					log.info("Y:" + y);
					log.info("写锟斤拷晒锟�");
				}
			} catch (Exception e) {

			}
		}
		buffer.clear();
	}

	/**
	 * 锟斤拷取锟斤拷录map锟斤拷锟斤拷息
	 */
	public void getMapInfo() {
		Set<String> keys = map.keySet();
		log.info("------------mapInfo--------------");
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			try {
				log.info("userId:" + key + "channel:" + map.get(key).getRemoteAddress());
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

	public boolean isExit() {
		if (socketChannel == null) {
			log.info("socketChannel为锟斤拷");
			return false;
		}
		return true;
	}

	/**
	 * 鍚戝鎴风鍙戦�乯son鏁版嵁
	 * 
	 * @param config
	 * @param json
	 * @param socketChannel
	 */
	public void sendJson(int config, String json, SocketChannel socketChannel) {
		buffer = ByteBuffer.allocateDirect(8 + json.getBytes().length);
		buffer.putInt(config);
		put(json);
		writeBuffer(socketChannel);
	}

	public void sendJson(int config, String json, String userId) {
		sendJson(config, json, map.get(userId));
	}

}
