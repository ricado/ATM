package com.atm.chat.nio.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.Config;
import com.atm.model.chat.CrowdMenber;
import com.atm.model.user.UserInfo;
import com.atm.util.FileUtil;

public class ClientReceiveThread extends Thread {
	private static final Logger log = LoggerFactory
			.getLogger(ClientReceiveThread.class);
	private Selector selector = null;
	static final int port = 23457;
	private Charset charset = Charset.forName("UTF-8");
	private SocketChannel socketChannel = null;
	private int size;
	private byte[] bytes;
	private int type = 0;
	private ByteBuffer buffer = null;
	private JSONObject jsonObject;
	private Scanner in = new Scanner(System.in);
	private boolean listen = true;

	public ClientReceiveThread(Selector selector) {
		// TODO Auto-generated constructor stub
		this.selector = selector;
	}

	@Override
	public void run() {
		log.info("........");
		while (listen) {
			try {
				int readyChannels = selector.select();
				if (readyChannels == 0)
					continue;
				Set selectedKeys = selector.selectedKeys(); // 可以通过这个方法，知道可用通道的集合
				Iterator keyIterator = selectedKeys.iterator();
				log.info("selectorKey:" + selectedKeys.size());
				while (keyIterator.hasNext()) {
					log.info("key-" + keyIterator.toString());
					SelectionKey sk = (SelectionKey) keyIterator.next();
					keyIterator.remove();
					dealWithSelectionKey(sk);
				}
			} catch (Exception e) {
				listen = false;
			}
		}
	}

	/**
	 * 处理服务器发送过来的信息的转发器
	 * 
	 * @param sk
	 * @throws IOException
	 */
	// TODO 信息中转站
	private void dealWithSelectionKey(SelectionKey sk) throws IOException {

		if (sk.isReadable()) {
			// 使用 NIO 读取 Channel中的数据，这个和全局变量sc是一样的，因为只注册了一个SocketChannel
			// sc既能写也能读，这边是读
			socketChannel = (SocketChannel) sk.channel();
			int type = getInt();
			log.info("type:" + type);
			// 消息中转
			switch (type) {
			case Config.RESULT_LOGIN:// 登录
				loginResult();
				break;
			case Config.MESSAGE_FROM:// 接收消息
				receiveMessage();
				break;
			case Config.CROWD_MESSAGE_FROM: // 接收群聊消息
				receiveCrowdMessage();
				break;
			case Config.CROWD_MY: // 我的群
				printMyCrowd();
			case Config.CROWD_FIND:// 搜索的群
				printfindCrowd();
				break;
			case Config.CROWD_LIST:
				printfindCrowd();
				break;
			case Config.USER_FIND:// 搜索的用户
				printfindUser();
			case Config.CROWD_APPLY:// 申请加入群
				applyToCrowd();
				break;
			case Config.CROWD_INTIVE:// 接收到邀请进群
				receiveToCrowd();
				break;
			case Config.CROWD_RESULT_APPLY:// 同意进群
				applyResult();
				break;
			case Config.USER_GET_ATTENT:// 我关注的
				attentMe();
				break;
			case Config.USER_GET_ATTENTED:// 关注我的
				myAttent();
				break;
			default:
				sk.cancel();
				socketChannel.close();
				break;
			}
			sk.interestOps(SelectionKey.OP_READ);
		}
	}

	/**
	 * 登录结果
	 * 
	 * @throws IOException
	 */
	public void loginResult() throws IOException {
		int result = getInt();
		if (result == Config.SUCCESS) {
			log.info("login success");
		} else if (result == Config.FAILED) {
			log.info("login fail");
		} else {
			log.info("login already");
		}
	}

	/**
	 * 收到私信 进行显示处理
	 */
	// TODO receiveMessage
	public void receiveMessage() {
		try {
			int type = getInt();
			String userId = getString();
			String friendId = getString();
			String time = getString();
			String content = getString();

			log.info("-------------来自" + userId + "的私信---------------");
			log.info(">>>>>>>>>>>time:" + time);
			log.info("content:" + content);
			log.info("-------------end---------------");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("receiveMessage error");
		}
	}

	/**
	 * 打印群聊信息
	 * 
	 * @param json
	 */
	public void receiveCrowdMessage() {
		try {
			int messageType = getInt();
			String userId = getString();
			int crowdId = getInt();
			String time = getString();
			String content;

			log.info("-----------群聊" + crowdId + "------------------");
			log.info(">>>>>>>>>>>time:" + time);
			log.info(">>>>>>>>>>>userId:" + userId);

			if (messageType == Config.CROWD_MESSAGE_TEXT) {
				content = getString();
				log.info("content:" + content);
			} else if (messageType == Config.CROWD_MESSAGE_IMG) {
				// TODO 群聊图片图片的处理
				String filename = getString();
				int pathLength = getInt();
				byte[] bytes = FileUtil.getFileBytes(socketChannel);
				content = getUserPath(filename, userId, bytes);
				log.info("content:" + content);
			}
			log.info("-------------end---------------");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("get error");
		}
	}

	/**
	 * 我的群
	 * 
	 * @param json
	 */
	public void printMyCrowd() {
		String menbersList = jsonObject.getString("menbersList");
		JSONArray jsonArray = new JSONArray().fromObject(menbersList);
		log.info("=============我的群===============");
		Object[] menbers = jsonArray.toArray();
		for (int i = 0; i < menbers.length; i++) {
			JSONObject jsonObject = JSONObject.fromObject(menbers[i]);
			CrowdMenber menber = (CrowdMenber) JSONObject.toBean(jsonObject,
					CrowdMenber.class);
			log.info("crowd id:" + menber.getCrowdId() + "  roleId:"
					+ menber.getRoleId());
		}
	}

	/**
	 * 打印出我查找的群
	 * 
	 * @throws IOException
	 */
	public void printfindCrowd() throws IOException {
		int number = getInt();
		for (int i = 0; i < number; i++) {
			int crowdId = getInt();
			String crowdName = getString();
			String filename = getString();
			int length = getInt();
			byte[] head = getFileBytes(length);
			String path = getCrowdPath(filename, crowdId, head);
			log.info("============" + crowdId + "=============");
			log.info("crowdName:" + crowdName);
			log.info("filename:" + filename);
			log.info("path:" + path);
			log.info("===========================");
		}
	}

	/**
	 * 打印出我查找的用户
	 */
	public void printfindUser() {
		String users = jsonObject.getString("users");
		JSONArray jsonArray = new JSONArray().fromObject(users);
		log.info("=============查找用户===============");
		Object[] menbers = jsonArray.toArray();
		for (int i = 0; i < menbers.length; i++) {
			JSONObject jsonObject = JSONObject.fromObject(menbers[i]);
			UserInfo user = (UserInfo) JSONObject.toBean(jsonObject,
					UserInfo.class);
			log.info("userId:" + user.getUserId() + " userName:"
					+ user.getName());
		}
	}

	/**
	 * 接受群列表的消息。
	 * 
	 * @throws IOException
	 */
	public void receiveCrowdListInfo() throws IOException {
		int crowdId = getInt();// 群ID
		String crowdName = getString();
		String filename = getString();
		int fileLength = getInt();
		byte[] bytes = getFileBytes();
		String path = saveFile(filename, crowdId + "", bytes);

		// 显示出来
		log.info("=============群列表==============");
		log.info("crowdId:" + crowdId);
		log.info("crowdName: " + crowdName);
		log.info("filename:" + filename);
		log.info("fileLength:" + fileLength);
		log.info("bytesLength:" + bytes.length);
		log.info("---------------------------------");
	}

	public void createCrowdSuccess() throws IOException {
		log.info("创建成功，群号为:" + getString());
	}

	public void createCrowdFail() {
		log.info("创建失败，so sad");
	}

	/**
	 * 接收到邀请群邀请
	 * 
	 * @throws IOException
	 */
	public void receiveToCrowd() throws IOException {
		String intiveId = getString();
		String intivedId = getString();
		String time = getString();
		int crowdId = getInt();
		log.info("=========接收到" + intiveId + "的进群邀请");
		log.info("时间:" + time);
		log.info("crowdId:" + crowdId);
		log.info("是否同意该群(1303---1304)");
		// clientSend.acceptToCrowd(intiveId, intivedId, crowdId, time);
	}

	/**
	 * 有用户申请进入某群
	 * 
	 * @throws IOException
	 */
	public void applyToCrowd() throws IOException {
		String applyId = getString();
		int crowdId = getInt();
		String time = getString();
		log.info("=======申请进群=======");
		log.info("time:" + time);
		log.info("userId:" + applyId);
		log.info("crowdId:" + crowdId);
		log.info("是否加入该群(1306---1307)");
		// log.info(">>>>>>>>>>进入 ClientSend.applyToCrowd()");
	}

	public void applyResult() throws IOException {
		int result = getInt();
		int crowdId = getInt();
		if (result == Config.SUCCESS) {
			log.info("同意进群" + crowdId);
		} else if (result == Config.FAILED) {
			log.info("被拒绝加入群" + crowdId);
		} else {
			log.info(crowdId + "群已满");
		}
	}

	/**************************************** 关注 ***************************************************/

	/**
	 * 关注我的
	 * 
	 * @throws IOException
	 */
	public void attentMe() throws IOException {
		int length = getInt();
		log.info("---------------关注我的--------------");
		for (int i = 0; i < length; i++) {
			log.info("user--" + i + ":" + getString());
		}
		log.info("-----------------end----------------");
	}

	/**
	 * 我关注的
	 * 
	 * @throws IOException
	 */
	public void myAttent() throws IOException {
		int length = getInt();
		log.info("---------------我关注的--------------");
		for (int i = 0; i < length; i++) {
			log.info("user--" + i + ":" + getString());
		}
		log.info("-----------------end----------------");
	}

	public void write(String line) {
		try {
			socketChannel.write(charset.encode(line));
		} catch (Exception e) {
			log.info("error");
		}
	}

	public void writeInt(int a) {
		try {
			ByteBuffer buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(a);
			socketChannel.write(buffer);
			buffer.flip();
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

	// TODO getInt()
	public int getInt() throws IOException {
		buffer = ByteBuffer.allocateDirect(4);
		size = socketChannel.read(buffer);
		log.info("size:" + size);
		if (size >= 0) {
			buffer.flip();
			int userIdnum = buffer.getInt();
			buffer.clear();
			log.info("length:" + userIdnum);
			return userIdnum;
		} else {
			return -100;
		}
	}

	// TODO getString
	public String getString() throws IOException {
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
	}

	/**
	 * 获取文件的byte数组
	 * 
	 * @return byte[]
	 * @throws IOException
	 */
	public byte[] getFileBytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 文件可能会很大
		buffer = ByteBuffer.allocate(1024);
		log.info("11");
		int i = 0;
		while ((size = socketChannel.read(buffer)) > 0) {
			buffer.flip();
			bytes = new byte[size];
			buffer.get(bytes);
			baos.write(bytes);
			buffer.clear();
			log.info(i++ + "");
		}
		return baos.toByteArray();
	}

	/**
	 * 获取文件的byte数组
	 * 
	 * @return byte[]
	 * @throws IOException
	 */
	public byte[] getFileBytes(int length) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 文件可能会很大
		buffer = ByteBuffer.allocate(length);
		log.info("11");
		int i = 0;
		if ((size = socketChannel.read(buffer)) > 0) {
			buffer.flip();
			bytes = new byte[size];
			buffer.get(bytes);
			baos.write(bytes);
			buffer.clear();
			log.info(i++ + "");
		}
		return baos.toByteArray();
	}

	public String getUserPath(String filename, String userId, byte[] filebytes)
			throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String path = "G:/atm/image/user/" + userId + "/" + date;
		path = saveFile(filename, path, filebytes);
		return path;
	}

	public String getCrowdPath(String filename, int crowdId, byte[] filebytes)
			throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String path = "G:/atm/image/crowd/" + crowdId + "/" + date;
		path = saveFile(filename, path, filebytes);
		return path;
	}

	/**
	 * 保存文件消息
	 * 
	 * @param filename
	 * @param userId
	 * @return filepath
	 * @throws IOException
	 */
	// TODO 保存文件消息
	public String saveFile(String filename, String path, byte[] filebytes)
			throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream os = null;
		new File(path + "/" + filename);
		try {
			os = new FileOutputStream(
					new File(path + File.separator + filename));
			os.write(filebytes);
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path + "/" + filename;
	}
}
