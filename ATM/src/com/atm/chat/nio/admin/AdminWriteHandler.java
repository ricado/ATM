package com.atm.chat.nio.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;

import net.sf.json.JSONObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.client.ClientSend;
import com.atm.chat.nio.server.handler.BufferHandler;
import com.atm.chat.nio.server.util.Config;
import com.atm.chat.nio.server.util.ScMap;

public class AdminWriteHandler extends BufferHandler implements ScMap{
	private static final Logger log = LoggerFactory.getLogger(ClientSend.class);
	private Selector selector = null;
	static final int port = 23457;
	private Charset charset = Charset.forName("UTF-8");
	private static SocketChannel socketChannel = null;
	private String tip = "";
	private static Scanner in = new Scanner(System.in);
	private int sendType;
	private String userId;
	private static ByteBuffer buffer = null;
	private JSONObject jsonObject = new JSONObject();

	public AdminWriteHandler(SocketChannel sc) {
		this.socketChannel = sc;
	}

	/*public void run() {
		log.info("start....");
		while (in.hasNextLine()) {
			// TODO 中转
			sendType = Integer.parseInt(in.nextLine());
			log.info(sendType + "");
			switch (sendType) {
			case Config.REQUEST_LOGIN: // 登录
				login();
				break;
			case Config.MESSAGE_TO:
				sendMessage();
				break;
			case Config.CROWD_MESSAGE_TO: // 发送群聊消息
				sendCrowdMessage();
				break;
			case Config.FIND_MY_CROWD: // 我的群
				findMyCrowd();
				break;
			case Config.FIND_CROWD: // 查找群
				findCrowd();
				break;
			case Config.FIND_USER: // 查找用户
				findUser();
				break;
			case Config.CROWD_INTIVE:// 邀请进群
				intiveToCrowd();
				break;
			case Config.REQUEST_EXIT: // 申请退出
				exit();
				break;
			case Config.CROWD_APPLY:// 申请进群
				applyToCrowd();
				break;
			case Config.CROWD_ACCEPT:// 接受邀请:
				applyToCrowd();
				break;
			case Config.CROWD_NOACCEPT:// 拒绝邀请
				log.info("拒绝邀请");
				break;
			case Config.CROWD_AGREE: // 同意申请
				agreeApply();
				break;
			case Config.CROWD_NOAGREE:// 拒绝
				log.info("拒绝");
				break;
			case Config.ADD_FRIEND: // 添加关注
				addFriend();
				break;
			case Config.USER_ATTENT:
				findMyAttent();
				break;
			case Config.USER_ATTENTED:
				findAttentMe();
			case 9999:
				lookOnline();
				break;
			default:
				break;
			}
		}
	}*/

	public Map<String,SocketChannel> getMap(){
		return map;
	}
	/**
	 * 登录请求
	 * 
	 * @throws Exception
	 */
	// TODO 登录
	public void login(String userId,String userPwd) {
		buffer = ByteBuffer.allocate(12 + userId.getBytes().length
				+ userPwd.getBytes().length);
		
		buffer.putInt(Config.REQUEST_LOGIN);

		buffer.putInt(userId.length());
		buffer.put(userId.getBytes());

		buffer.putInt(userPwd.getBytes().length);
		buffer.put(userPwd.getBytes());

		writeBuffer();

	}

	public void login2() {
		IoBuffer ioBuffer = IoBuffer.allocate(10);
		ioBuffer.setAutoExpand(true);

		ioBuffer.putInt(Config.REQUEST_LOGIN);

	}

	/**
	 * 发送私信
	 */
	// TODO 发送私信
	public void sendMessage() {
		log.info("==============发私信===============");
		// 获取内容
		log.info("你的id:");
		String userId = in.nextLine();
		log.info("friendId:");
		String friendId = in.nextLine();
		log.info("内容:");
		String content = in.nextLine();

		buffer = ByteBuffer.allocateDirect(16 + userId.getBytes().length
				+ friendId.getBytes().length + content.getBytes().length);
		// 写进buffer缓冲区
		buffer.putInt(Config.MESSAGE_TO);
		put(userId);
		put(friendId);
		put(content);

		writeBuffer();

	}

	/**
	 * 发送群聊消息
	 */
	// TODO 发送群聊消息
	public void sendCrowdMessage() {
		log.info("============发群聊==============");
		log.info("your id:");
		String userId = in.nextLine();
		log.info("群id:");
		int crowdId = in.nextInt();
		in.nextLine();// 群ID(Int)需要指向下一行
		log.info("内容:");
		String content = in.nextLine();
		// ////////////////重定义buffer
		buffer = ByteBuffer.allocateDirect(20 + userId.getBytes().length
				+ content.getBytes().length);

		buffer.putInt(Config.CROWD_MESSAGE_TO);// 群聊消息
		buffer.putInt(Config.CROWD_MESSAGE_TEXT);// 文本消息
		put(userId);
		buffer.putInt(crowdId);
		put(content);

		writeBuffer();
	}

	/**
	 * 创建群聊
	 */
	// TODO 创建群聊
	public void createCrowd() {
		log.info("=====创建新群聊=====");
		String userId = in.nextLine();
		String crowdName = in.nextLine();
		buffer = ByteBuffer.allocateDirect(12 + userId.getBytes().length
				+ crowdName.getBytes().length);

		buffer.putInt(Config.CROWD_CREATE);
		// 创建群聊的群主
		put(userId);
		// 群聊名字
		put(crowdName);
		writeBuffer();
	}

	/**
	 * 获取用户信息
	 */
	public void getUserInfo() {
		// TODO getUserInfo
	}

	/**
	 * 获取群聊信息
	 */
	public void getCrowdInfo() {
		// TODO getCrowdInfo
	}

	/**
	 * 查找我加入的群，包括管理的群和创建的群
	 */
	public void findMyCrowd() {
		log.info("===========查看我的群=============");
		log.info("your id:");
		String userId = in.nextLine();

		buffer = ByteBuffer.allocateDirect(8);
		buffer.putInt(Config.CROWD_MY);
		put(userId);

		writeBuffer();
	}

	/**
	 * 通过输入的内容寻找相关的群
	 */
	public void findCrowd() {
		// TODO findCrowd
		log.info("========查找群==========");
		log.info("输入搜索的内容:");
		String tip = in.nextLine();
		// 查找群
		buffer = ByteBuffer.allocateDirect(8 + tip.getBytes().length);
		buffer.putInt(Config.CROWD_FIND);
		put(tip);

		writeBuffer();
	}

	/**
	 * 通过输入的内容寻扎相关的user
	 */
	public void findUser() {
		// TODO findUser
		log.info("========查找用户==========");
		log.info("输入搜索的内容:");
		String tip = in.nextLine();
		// 查找用户
		buffer = ByteBuffer.allocateDirect(8 + tip.getBytes().length);
		buffer.putInt(Config.USER_FIND);
		put(tip);

		writeBuffer();
	}

	/**
	 * 邀请进群
	 */
	public void intiveToCrowd() {
		log.info("===========邀请进群=========");
		log.info("输入你的id:");
		String userId = in.nextLine();
		log.info("受邀人ID:");
		String intivedId = in.nextLine();
		log.info("群编号:");
		int crowdId = in.nextInt();
		in.nextLine();

		// 传送buffer
		buffer = ByteBuffer.allocateDirect(16 + userId.getBytes().length
				+ intivedId.getBytes().length);

		buffer.putInt(Config.CROWD_INTIVE);
		put(userId);
		put(intivedId);
		buffer.putInt(crowdId);
		writeBuffer();

	}

	/******************* 添加好友 ************************************************/

	public void addFriend() {
		log.info("===========添加好友==============");
		log.info("你的ID:");
		String userAttendId = in.nextLine();// 关注人的Id
		log.info("关注人Id:");
		String userAttendedId = in.nextLine();// 被关注的人Id

		buffer = ByteBuffer.allocateDirect(12
				+ userAttendedId.getBytes().length
				+ userAttendedId.getBytes().length);
		buffer.putInt(Config.USER_ADD_ATTENT);// 添加关注人
		put(userAttendId);
		put(userAttendedId);

		writeBuffer();

	}

	/**
	 * 查找关注我的
	 */
	public void findAttentMe() {
		log.info("你的id:");
		String userId = in.nextLine();
		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);
		buffer.putInt(Config.USER_GET_ATTENTED);
		put(userId);
		writeBuffer();
	}

	/**
	 * 查找我关注的
	 */
	public void findMyAttent() {
		log.info("你的id:");
		String userId = in.nextLine();
		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);
		buffer.putInt(Config.USER_GET_ATTENT);
		put(userId);
		writeBuffer();
	}

	/**
	 * 申请退出
	 */
	public void exit() {
		// TODO apply exit
		log.info("==============退出==============");
		String userId = in.nextLine();

		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);

		buffer.putInt(Config.REQUEST_EXIT);
		put(userId);
		writeBuffer();
	}

	/**
	 * 写进管道缓冲区
	 * 
	 * @param line
	 */
	public void write(String line) {
		try {
			socketChannel.write(charset.encode(line));
			log.info("============success==============");
		} catch (Exception e) {
			log.info("============error==============");
		}
	}

	/**
	 * 将文件转变成byte[]数组
	 * 
	 * @param fileFath
	 * @return
	 * @throws IOException
	 */
	private byte[] makeFileToByte(String fileFath) throws IOException {
		File file = new File(fileFath);
		FileInputStream fis = new FileInputStream(file);
		int length = (int) file.length();
		byte[] bytes = new byte[length];
		int temp = 0;
		int index = 0;
		while (true) {
			index = fis.read(bytes, temp, length - temp);
			if (index <= 0)
				break;
			temp += index;
		}
		fis.close();
		return bytes;
	}

	/**
	 * 在buffer中放字符串的字节数组长度以及该字节数组
	 * 
	 * @param string
	 */
	public void put(String string) {
		buffer.putInt(string.getBytes().length);
		buffer.put(string.getBytes());
	}

	/**
	 * 对将buffer写入socketchannel.
	 */
	public void writeBuffer() {
		buffer.flip();// 对buffer进行转换
		try {
			socketChannel.write(buffer);
			buffer.clear();
			log.info("写入成功");
		} catch (Exception e) {
			log.info("写入失败");
		}
	}

	public void lookOnline() {
		buffer = ByteBuffer.allocateDirect(4);
		buffer.putInt(9999);
	}

	/**
	 * 是否接收邀请加入群聊。如果接收，进入申请进群
	 * 
	 * @param intiveId
	 * @param intivedId
	 * @param crowdId
	 * @param time
	 */
	public void acceptToCrowd(String intiveId, String intivedId, int crowdId,
			String time) {
		log.info("============是否同意邀请加入" + crowdId + "(Y/N)=========");
		String y = in.nextLine();
		if (y.equals("Y")) {
			// 同意。
			log.info("同意");
			sendApply(intiveId, crowdId, intiveId);
		} else {
			log.info("=======已经拒绝=========");
		}
	}

	/**
	 * 申请加入群聊
	 */
	public void applyToCrowd() {
		log.info("===========申请加入群=========");
		log.info("你的Id:");
		String applyId = in.nextLine();
		log.info("群Id:");
		int crowdId = in.nextInt();
		in.nextLine();
		log.info("有无介绍人:");
		String intiveId = in.nextLine();
		sendApply(applyId, crowdId, intiveId);
	}

	/**
	 * 发送申请消息
	 * 
	 * @param applyId
	 *            申请人Id
	 * @param crowdId
	 *            群编号
	 * @param intiveId
	 *            邀请人id
	 */
	public void sendApply(String applyId, int crowdId, String intiveId) {
		// 设置buffer大小
		buffer = ByteBuffer.allocateDirect(16 + applyId.getBytes().length
				+ intiveId.getBytes().length);
		buffer.putInt(Config.CROWD_APPLY);
		put(applyId);
		buffer.putInt(crowdId);
		put(intiveId);

		writeBuffer();
	}

	/**
	 * 是否同意进群
	 * 
	 * @param applyId
	 *            申请人ID
	 * @param crowdId
	 *            群编号
	 */
	public void agreeApply() {
		log.info("===========是否同意进群(Y/N)");

		log.info("你的Id:");
		String userId = in.nextLine();
		log.info("申请人ID:");
		String applyId = in.nextLine();
		log.info("群编号:");
		int crowdId = in.nextInt();
		in.nextLine();
		buffer = ByteBuffer.allocateDirect(16 + applyId.getBytes().length
				+ userId.getBytes().length);

		log.info("-----------同意");
		buffer.putInt(Config.SUCCESS);
		put(userId);
		put(applyId);
		buffer.putInt(crowdId);

		// 写进管道
		writeBuffer();
	}
	/**
	 * 让用户强制下线
	 * @param userId
	 */
	public void delete(String userId){
		
		log.info("进入 AdminWriteHandler--------delete");
		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);
		buffer.putInt(Config.REQUEST_EXIT);
		put(userId);
		log.info("userId");
		writeBuffer();
	}
	
	public void move(String userId,int number){
		log.info("移动用户到别的ReadSelector");
		
		buffer = ByteBuffer.allocateDirect(12+userId.getBytes().length);
		buffer.putInt(Config.USER_MOVE);
		put(userId);
		buffer.putInt(number);
		
		writeBuffer();
	}
}
