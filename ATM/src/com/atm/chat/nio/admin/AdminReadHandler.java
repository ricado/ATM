package com.atm.chat.nio.admin;

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

import com.atm.chat.nio.client.ClientReceiveThread;
import com.atm.chat.nio.server.util.Config;
import com.atm.model.chat.CrowdChat;
import com.atm.model.chat.CrowdMenber;
import com.atm.model.user.UserInfo;

public class AdminReadHandler extends Thread{
	private static final Logger log = LoggerFactory
			.getLogger(ClientReceiveThread.class);
	private Selector selector = null;
	static final int port = 23457;
	private Charset charset = Charset.forName("UTF-8");
	private SocketChannel socketChannel = null;
	private int size;
	private byte[] bytes;
	private ByteBuffer buffer = null;
	private JSONObject jsonObject;
	private Scanner in = new Scanner(System.in);
	public AdminReadHandler(Selector selector) {
		// TODO Auto-generated constructor stub
		this.selector = selector;
	}
	@Override
	public void run() {
		log.info("........");
		try {
			while (true) {
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
			}
		} catch (IOException io) {
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
			/*case Config.LOGIN_SUCCESS:// 登录
				loginSuccess();
				break;
			case Config.LOGIN_FAILED:
				loginFail();
				break;
			case Config.LOGIN_ALREADY:
				loginAlready();*/
				//break;
			case Config.MESSAGE_FROM:// 接收消息
				receiveMessage();
				break;
			case Config.CROWD_MESSAGE_FROM: // 接收群聊消息
				receiveCrowdMessage();
				break;
			/*case Config.FIND_MY_CROWD: // 我的群
				printMyCrowd();
			case Config.FIND_CROWD:// 搜索的群
				printfindCrowd();
				break;
			case Config.FIND_USER:// 搜索的用户
				printfindUser();*/
			case Config.CROWD_APPLY://申请加入群
				applyToCrowd();
				break;
			case Config.CROWD_FULL: //群已满
				crowdFull();
				break;
			case Config.CROWD_INTIVE://接收到邀请进群
				receiveToCrowd();
				break;
			/*case Config.CROWD_AGREE://同意进群
				agreeToCrowd();
				break;
			case Config.CROWD_NOAGREE://被拒绝进群装逼
				noAgreeToCrowd();
				break;*/
			case Config.USER_GET_ATTENT://我关注的
				attentMe();
				break;
			case Config.USER_GET_ATTENTED://关注我的
				myAttent();
				break;
			default:
				break;
			}
			sk.interestOps(SelectionKey.OP_READ);
		}
	}

	// TODO 登录成功--失败---已登录
	public void loginSuccess() {
		log.info("login success");
	}

	public void loginFail() {
		log.info("login fail");
	}

	public void loginAlready() {
		log.info("login already");
	}

	/**
	 * 收到私信 进行显示处理
	 */
	// TODO receiveMessage
	public void receiveMessage() {
		try {
			String userId = getString();
			String friendId = getString();
			String content = getString();
			String time = getString();

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
				byte[] bytes = getFileBytes();
				content = saveFile(filename, userId, bytes);
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
	 */
	public void printfindCrowd() {
		String crowds = jsonObject.getString("crowds");
		JSONArray jsonArray = new JSONArray().fromObject(crowds);
		log.info("=============查找群===============");
		Object[] menbers = jsonArray.toArray();
		for (int i = 0; i < menbers.length; i++) {
			JSONObject jsonObject = JSONObject.fromObject(menbers[i]);
			CrowdChat crowdChat = (CrowdChat) JSONObject.toBean(jsonObject,
					CrowdChat.class);
			log.info("crowd id:" + crowdChat.getCrowdId() + " crowd name:"
					+ crowdChat.getCrowdName());
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
	 * @throws IOException
	 */
	public void receiveCrowdListInfo() throws IOException{
		int crowdId = getInt();//群ID
		String crowdName = getString();
		String filename = getString();
		int fileLength = getInt();
		byte[] bytes = getFileBytes();
		String path = saveFile(filename, crowdId+"", bytes);
		
		//显示出来
		log.info("=============群列表==============");
		log.info("crowdId:" + crowdId);
		log.info("crowdName: " + crowdName);
		log.info("filename:" + filename);
		log.info("fileLength:" + fileLength);
		log.info("bytesLength:" + bytes.length);
		log.info("---------------------------------");
	}
	
	/**
	 * 接收到邀请群邀请
	 * @throws IOException
	 */
	public void receiveToCrowd() throws IOException{
		String intiveId = getString();
		String intivedId = getString();
		String time = getString();
		int crowdId = getInt();
		log.info("=========接收到" + intiveId + "的进群邀请");
		log.info("时间:" + time);
		log.info("crowdId:" + crowdId);
		log.info("是否同意该群(1303---1304)");
		//clientSend.acceptToCrowd(intiveId, intivedId, crowdId, time);
	}
	
	/**
	 * 有用户申请进入某群
	 * @throws IOException
	 */
	public void applyToCrowd() throws IOException{
		String applyId = getString();
		int crowdId = getInt();
		String time = getString();
		log.info("=======申请进群=======");
		log.info("time:" + time);
		log.info("userId:" + applyId);
		log.info("crowdId:" + crowdId);
		log.info("是否加入该群(1306---1307)");
		//log.info(">>>>>>>>>>进入 ClientSend.applyToCrowd()");
	}
	//被接收加进群
	public void agreeToCrowd() throws IOException{
		int corwdId = getInt();
		log.info("同意进群" + corwdId);
	}
	//被拒绝
	public void noAgreeToCrowd() throws IOException{
		int corwdId = getInt();
		log.info("被拒绝加入群" + getInt());
	}
	public void crowdFull() throws IOException{
		int crowdId = getInt();
		log.info(crowdId+ "群已满");
	}
	
	/****************************************关注***************************************************/
	
	
	/**
	 * 关注我的
	 * @throws IOException 
	 */
	public void attentMe() throws IOException{
		int length = getInt();
		log.info("---------------关注我的--------------");
		for(int i=0;i<length;i++){
			log.info("user--" + i + ":" + getString());
		}
		log.info("-----------------end----------------");
	}
	
	/**
	 * 我关注的
	 * @throws IOException 
	 */
	public void myAttent() throws IOException{
		int length = getInt();
		log.info("---------------我关注的--------------");
		for(int i=0;i<length;i++){
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
		}
		return size;
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
	 * 保存文件消息
	 * 
	 * @param filename
	 * @param userId
	 * @return filepath
	 * @throws IOException
	 */
	// TODO 保存文件消息
	public String saveFile(String filename, String userId, byte[] filebytes)
			throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy\\MM\\dd"))
				.format(new Date());
		String path = "G:/atm/users/" + userId + "/" + "image" + "/" + date;
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
