package com.atm.chat.nio.newServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.ScMap;
import com.atm.service.user.UserService;

public class ReadThread extends Thread implements ScMap {
	public static final Logger log = LoggerFactory.getLogger(ReadThread.class);
	public Charset charset = Charset.forName("UTF-8");
	public byte[] bytes;
	static final int port = 23457;
	public int size;
	public SelectionKey selectionKey;
	public SocketChannel socketChannel;
	public ByteBuffer buffer = null;
	public int number;// 表明这是第几个readSeletor

	/**
	 * 获取登录map的信息
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

	/*
	 * public void showInfo(){ for(int i = 0;i<readSelectors.size();i++){
	 * readSelectors.get(i).get }
	 * 
	 * }
	 */
	/**
	 * 用户下线
	 * 
	 * @param sc
	 * @throws IOException
	 */
	public void removeUser(SocketChannel sc) throws IOException {
		log.info("用户下线");
		sc.socket().close();
		sc.close();
		for (String userId : map.keySet()) {
			if (map.get(userId) == sc) {
				log.info("找到userId:" + userId);
				UserService.exit(userId);
				/*sc.socket().close();
				// 关闭
				sc.close();*/
				map.remove(userId);
				mapkey.remove(userId);		
				//selectionKey.cancel();
				break;
			}
		}
		getMapInfo();
	}

	public void exit(String userId) {
		try {
			UserService.exit(userId);// 用户退出，修改离线时间
			// socketChannel.shutdownInput();
			// socketChannel.shutdownOutput();
			// OnlineService.deleteLogin(userId, number);
			map.get(userId).socket().close();
			map.get(userId).close();
			mapkey.get(userId).cancel();
			log.info("exit....");
		} catch (Exception e) {
			log.info("close error");
		}
		map.remove(userId);
		mapkey.remove(userId);
		log.info("exit success");
	}

	/**
	 * 用户退出
	 */
	public void exit() {
		getMapInfo();
		String userId = getString();
		log.info("userId : " + userId);
		exit(userId);
		getMapInfo();

		// //返回发送请求
		// buffer.clear();
		// buffer.putInt(Config.REQUEST_EXIT);
		// writeBuffer();
	}

	/**
	 * 将字符串写入
	 * 
	 * @param line
	 */
	public void write(String line) {
		try {
			SocketChannel sk = (SocketChannel) selectionKey.channel();
			sk.write(charset.encode(line));
		} catch (Exception e) {
			log.info("error");
		}
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

	public void write(String line, SocketChannel socketChannel) {
		try {
			socketChannel.write(charset.encode(line));
		} catch (Exception e) {
			log.info("error");
		}
	}

	/**
	 * 向buffer获取int类型
	 * 
	 * @return
	 * @throws IOException
	 */
	public int getInt() throws IOException {
		// TODO getInt
		log.info("get int");
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
	 * 向bugger获取String
	 * 
	 * @return
	 */
	public String getString() {
		// TODO getString
		log.info("get String");
		try {
			buffer = ByteBuffer.allocateDirect(getInt());
			size = socketChannel.read(buffer);
			bytes = new byte[size];
			if (size >= 0) {
				buffer.flip();
				buffer.get(bytes);
				buffer.clear();
				log.info("String:" + new String(bytes, "GBK"));
			}
			return new String(bytes, "GBK");
		} catch (Exception e) {
			log.info("read error");
			return "error";
		}
	}

	public void readOver() {
		try {
			buffer = ByteBuffer.allocateDirect(1024);
			while ((size = socketChannel.read(buffer)) >= 0) {
				buffer.clear();
			}
		} catch (Exception e) {
			log.info("read error");
		}
	}

	/**
	 * 在buffer中放字符串的字节数组长度以及该字节数组
	 * 
	 * @param string
	 */
	public void put(String string) {
		// TODO put
		buffer.putInt(string.getBytes().length);
		buffer.put(string.getBytes());
	}

	/**
	 * 对将buffer写入socketchannel.
	 */
	public void writeBuffer() {
		// TODO writeBuffer
		writeBuffer(socketChannel);
	}

	/**
	 * 对将buffer写入socketchannel.
	 */
	public void writeBuffer(SocketChannel channel) {
		// TODO writeBuffer
		buffer.flip();// 对buffer进行转换
		int num = 0;
		if (channel == null) {
			return;
		}
		while (buffer.hasRemaining()) {
			try {
				int y = channel.write(buffer);
				log.info("y:" + y);
				log.info("写入成功");
			} catch (Exception e) {
				num++;
				log.info("写入失败");
				if (num == 5) {
					break;
				}
			}
		}
		buffer.clear();
	}
}
