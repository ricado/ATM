package com.atm.chat.nio.server.handler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.Config;
import com.atm.model.define.chat.CrowdChatRecord;
import com.atm.model.define.chat.MenberList;
import com.atm.model.define.chat.PrivateChatRecord;
import com.atm.model.user.UserInfo;
import com.atm.service.MessageService;
import com.atm.service.crowd.CrowdMenberService;
import com.atm.service.user.UserService;
import com.atm.util.FileUtil;
import com.atm.util.TimeUtil;

public class MessageHandler extends BufferHandler {
	private static final Logger log = LoggerFactory
			.getLogger(MessageHandler.class);
	private CrowdMenberService crowdMenberService = new CrowdMenberService();
	private UserService userService = new UserService();

	public MessageHandler() {
	}

	public void operate(int type) throws Exception {
		switch (type) {
		case Config.MESSAGE_TO:// 私信消息
			receivePrivateMessage();
			break;
		case Config.CROWD_MESSAGE_TO:// 群聊消息
			receiveCrowdMessage();
			break;
		case Config.MESSAGE_OFFLINE:// 离线消息
			sendOffLineMessage();
			break;
		default:
			break;
		}
	}

	/**
	 * 接收私信聊天消息
	 * 
	 * @throws Exception
	 */
	public void receivePrivateMessage() throws Exception {
		int messageType = getInt();// 私信类型
		String userId = getString();
		String friendId = getString();
		String time = getString();
		log.info("接收消息时间:" + time);
		String content = null;
		// 获取发信人的信息
		UserInfo userInfo = userService.getUserInfo(userId);
		// 发信人头像缩略图字节数组
		String path = this.getClass().getResource("/").getPath().substring(1)
				.replaceFirst("WEB-INF/classes/", "");
		log.info(path + userInfo.getHeadImagePath());
		// 获取用户头像的字节数组
		byte[] image = FileUtil.makeFileToByte(path
				+ userInfo.getHeadImagePath());
		// 创建私聊实例
		PrivateChatRecord chat = new PrivateChatRecord();
		chat.setUserSendId(userId);
		chat.setUserReceiveId(friendId);
		chat.setNickname(userInfo.getNickname());
		// 时间
		chat.setSendTime(TimeUtil.getTimestamp());
		log.info(">>>>判断消息类型");
		if (messageType == Config.MESSAGE_TEXT) {
			log.info(">>>>为文本类型");
			chat.setFlag(0);
			content = receivePrivateTextMessage(userId, friendId);
			log.info("content:" + content);
		} else if (messageType == Config.MESSAGE_IMG) {
			log.info(">>>>为图片消息");
			chat.setFlag(1);
			content = receivePrivateImageMessage(userId, friendId);
		}
		chat.setSendContent(content);

		// 发送消息
		log.info("发送消息");
		sendMessage(chat, image);
	}

	/**
	 * 私信 图片 消息
	 * 
	 * @throws Exception
	 */
	private String receivePrivateTextMessage(String userId, String friendId)
			throws Exception {
		log.info("接收文本消息");
		String content = getString();
		return content;
	}

	/**
	 * 接收文本消息
	 * 
	 * @param userId
	 * @param friendId
	 * @throws Exception
	 */
	private String receivePrivateImageMessage(String userId, String friendId)
			throws Exception {
		log.info("接收图片消息");
		String filename = getString();
		log.info("filename:" + filename);
		int imageLength = getInt();
		byte[] imagebytes = FileUtil.getFileBytes(socketChannel, imageLength);
		String path = FileUtil.getPrivateChatPath(filename, userId, friendId,
				imagebytes);
		return path;
	}

	/**
	 * 发送消息
	 * 
	 * @param chat
	 * @throws Exception
	 * @throws IOException
	 */
	public void sendMessage(PrivateChatRecord chat, byte[] image)
			throws Exception {
		if (map.get(chat.getUserReceiveId()) == null) {
			log.info("用户不在线");
			MessageService.savePrivateChat(chat);
		} else {
			log.info("用户在线");
			if (chat.getFlag() == 0) {
				sendTextMessage(chat, image);
			} else if (chat.getFlag() == 1) {
				sendImageMessage(chat, image);
			}
		}
	}

	/**
	 * 发送私信文本消息
	 * 
	 * @param chat
	 * @throws UnsupportedEncodingException
	 */
	private void sendTextMessage(PrivateChatRecord chat, byte[] image)
			throws UnsupportedEncodingException {
		log.info("=========私信======发送文本消息================");
		log.info("〉〉〉〉〉〉〉〉nickname:" + chat.getNickname());
		log.info(".............content:" + chat.getSendContent());
		buffer = ByteBuffer.allocateDirect(32
				+ chat.getUserSendId().getBytes().length
				+ chat.getNickname().getBytes().length
				+ chat.getUserReceiveId().getBytes().length
				+ TimeUtil.getDateFormat(chat.getSendTime()).toString()
						.getBytes().length
				+ chat.getSendContent().getBytes("GBK").length + image.length);
		buffer.putInt(Config.MESSAGE_FROM);
		buffer.putInt(Config.MESSAGE_TEXT);
		// userId
		put(chat.getUserSendId());
		// 发送者昵称
		put(chat.getNickname());
		// 接受者id
		put(chat.getUserReceiveId());
		// 时间
		put(TimeUtil.getDateFormat(chat.getSendTime()).toString());
		// 文本
		put(chat.getSendContent());
		// 头像缩略图
		buffer.putInt(image.length);
		buffer.put(image);
		writeBuffer(map.get(chat.getUserReceiveId()));
		log.info("=============success==================");
	}

	/**
	 * 发送私信图片消息
	 * 
	 * @param chat
	 * @throws IOException
	 */
	private void sendImageMessage(PrivateChatRecord chat, byte[] image)
			throws IOException {
		log.info("私信=======图片消息");
		byte[] imageBytes = FileUtil.makeFileToByte(chat.getSendContent());
		String filename = new File(chat.getSendContent()).getName();
		log.info("=========私信======发送图片消息================");
		buffer = ByteBuffer.allocateDirect(36
				+ chat.getUserSendId().getBytes().length
				+ chat.getUserReceiveId().getBytes().length
				+ chat.getNickname().getBytes().length
				+ TimeUtil.getDateFormat(chat.getSendTime()).toString()
						.getBytes().length + filename.getBytes().length
				+ imageBytes.length + image.length);

		buffer.putInt(Config.MESSAGE_FROM);
		buffer.putInt(Config.MESSAGE_IMG);
		put(chat.getUserSendId());
		put(chat.getNickname());
		put(chat.getUserReceiveId());
		put(TimeUtil.getDateFormat(chat.getSendTime()).toString());
		put(filename);
		buffer.putInt(image.length);
		buffer.put(image);
		buffer.putInt(imageBytes.length);
		buffer.put(imageBytes);

		writeBuffer(map.get(chat.getUserReceiveId()));
		log.info("=============success==================");
	}

	/**
	 * 获取全部离线消息
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendPrivateOffLineMessage(String userId) throws Exception {
		// 获得私聊离线消息
		List<PrivateChatRecord> list = MessageService.getOffLineMessage(userId);
		// 头像map
		Map<String, byte[]> mapImage = new HashMap<String, byte[]>();
		// 头像字节数组
		byte[] images = null;
		// 发送
		log.info("开始发送私聊离线消息");
		for (Iterator<PrivateChatRecord> iterator = list.iterator(); iterator
				.hasNext();) {
			PrivateChatRecord chat = iterator.next();
			// 查看map中是否已经存在userId的头像
			if (mapImage.get(chat.getUserSendId()) == null) {
				images = new UserService().getUserSmallHeadByte(chat
						.getUserSendId());
				mapImage.put(chat.getUserSendId(), images);
			}
			sendMessage(chat, mapImage.get(chat.getUserSendId()));
		}
		log.info("-----------结束发送--------");
	}

	/**
	 * 接收群聊消息
	 * 
	 * @throws Exception
	 */
	public void receiveCrowdMessage() throws Exception {
		int type = getInt();// 群消息类型
		String userId = getString(); // 发送者ID
		int crowdId = getInt(); // 群编号
		String content = null;
		UserInfo userInfo = userService.getUserInfo(userId);
		// 头像图片的缩略图
		String path = this.getClass().getResource("/").getPath().substring(1)
				.replaceFirst("WEB-INF/classes/", "");
		log.info(path);
		byte[] image = FileUtil.makeFileToByte(path
				+ userInfo.getHeadImagePath());

		// 打印消息
		log.info("-----------群聊" + crowdId + "------------------");
		log.info(">>>>>>>>>>>userId:" + userId);

		CrowdChatRecord chat = new CrowdChatRecord();
		chat.setCrowdId(crowdId);
		chat.setUserId(userId);
		chat.setNickname(userInfo.getNickname());
		chat.setSendTime(TimeUtil.getTimestamp());
		if (type == Config.CROWD_MESSAGE_TEXT) {
			chat.setFlag(0);
			content = receiveTextMessage();
		} else if (type == Config.CROWD_MESSAGE_IMG) {
			chat.setFlag(1);
			content = receiveImageMessage(crowdId);
		}
		chat.setSendContent(content);
		log.info("content:" + content);
		log.info("-------------end---------------");

		sendCrowdMessage(chat, image);
		log.info("结束发送群聊消息");
	}

	/**
	 * 接收群聊文本
	 * 
	 * @return
	 * @throws Exception
	 */
	private String receiveTextMessage() throws Exception {
		String content = getString();
		return content;
	}

	/**
	 * 接收群聊图片
	 * 
	 * @param crowdId
	 * @return
	 * @throws Exception
	 */
	private String receiveImageMessage(int crowdId) throws Exception {
		String filename = getString();// 文件名
		int fileLenght = getInt();// 文件长度
		byte[] imagebyte = FileUtil.getFileBytes(socketChannel, fileLenght);// 文件字节数组
		log.info("保存属于群聊的图片并返回路径");
		String content = FileUtil.getCrowdPath(filename, crowdId, imagebyte);// 保存图片

		log.info("图片：-----");
		log.info("filelenght:" + fileLenght + "  contentsLength:"
				+ imagebyte.length + "filepath:" + content);
		return content;
	}

	/**
	 * 发送群聊消息
	 * 
	 * @param chat
	 * @throws IOException
	 */
	public void sendCrowdMessage(CrowdChatRecord chat, byte[] image)
			throws IOException {
		/**
		 * 获取群聊的人
		 */
		List<MenberList> userIds = crowdMenberService.findAllMenber(chat
				.getCrowdId());
		log.info(">>>>>>>>>>>>>开始群发消息:");
		// 群聊消息存入数据库
		MessageService.saveCrowdRecord(chat.getCrowdId(), chat.getUserId(),
				chat.getSendContent());

		for (int i = 0; i < userIds.size(); i++) {
			SocketChannel channel = map.get(userIds.get(i).getUserId());
			if (channel == null) {
				log.info("用户" + userIds.get(i).getUserId() + "不在线");
			} else if (socketChannel.equals(channel)) {
				log.info("用户本身");
			} else {
				log.info("发送给" + userIds.get(i).getUserId());
				sendMessage(chat, image, channel);
			}
		}

	}

	/**
	 * 发送给本管道的群聊消息
	 * 
	 * @param chat
	 * @throws IOException
	 */
	public void sendMessage(CrowdChatRecord chat, byte[] image)
			throws IOException {
		log.info("发送给" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// 文本消息
			sendTextMessage(chat, image, socketChannel);
		} else if (chat.getFlag() == 1) {
			// 图片消息
			sendImageMessage(chat, image, socketChannel);
		}
	}

	/**
	 * 发送给其他管道的群聊消息
	 * 
	 * @param chat
	 * @param channel
	 * @throws IOException
	 */
	public void sendMessage(CrowdChatRecord chat, byte[] image,
			SocketChannel channel) throws IOException {
		log.info("发送给" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// 文本消息
			sendTextMessage(chat, image, channel);
		} else if (chat.getFlag() == 1) {
			// 图片消息
			sendImageMessage(chat, image, channel);
		}
	}

	/**
	 * 发送群聊文本消息
	 * 
	 * @param chat
	 * @param channel
	 * @throws UnsupportedEncodingException
	 */
	private void sendTextMessage(CrowdChatRecord chat, byte[] image,
			SocketChannel channel) throws UnsupportedEncodingException {
		buffer = ByteBuffer.allocateDirect(32
				+ chat.getUserId().getBytes().length
				+ chat.getNickname().getBytes().length
				+ chat.getSendContent().getBytes().length
				+ TimeUtil.getDateFormat(chat.getSendTime()).toString()
						.getBytes().length + image.length);

		log.info("sendMessage:" + chat.getUserId() + " to " + chat.getCrowdId()
				+ " content:" + chat.getSendContent());
		// 将信息写入buffer
		buffer.putInt(Config.CROWD_MESSAGE_FROM);
		buffer.putInt(Config.CROWD_MESSAGE_TEXT);
		put(chat.getUserId());
		put(chat.getNickname());
		buffer.putInt(chat.getCrowdId());
		put(TimeUtil.getDateFormat(chat.getSendTime()).toString());
		put(chat.getSendContent());
		buffer.putInt(image.length);
		buffer.put(image);
		writeBuffer(channel);
	}

	/**
	 * 发送群聊图片消息
	 * 
	 * @param chat
	 * @param channel
	 * @throws IOException
	 */
	private void sendImageMessage(CrowdChatRecord chat, byte[] image,
			SocketChannel channel) throws IOException {
		String filename = new File(chat.getSendContent()).getName();
		byte[] imageBytes = FileUtil.makeFileToByte(chat.getSendContent());
		buffer = ByteBuffer.allocateDirect(36
				+ chat.getUserId().getBytes().length
				+ chat.getNickname().getBytes().length
				+ filename.getBytes().length
				+ imageBytes.length
				+ TimeUtil.getDateFormat(chat.getSendTime()).toString()
						.getBytes().length + image.length);

		log.info("sendMessage:" + chat.getUserId() + " to " + chat.getCrowdId()
				+ " content:");
		// 将信息写入buffer

		buffer.putInt(Config.CROWD_MESSAGE_FROM);
		buffer.putInt(Config.CROWD_MESSAGE_IMG);
		put(chat.getUserId());
		put(chat.getNickname());
		buffer.putInt(chat.getCrowdId());
		put(TimeUtil.getDateFormat(chat.getSendTime()).toString());
		put(filename);
		buffer.putInt(imageBytes.length);
		buffer.put(imageBytes);
		buffer.putInt(image.length);
		buffer.put(image);

		writeBuffer(channel);
		log.info("发送成功");
	}

	/**
	 * 发送离线消息
	 * 
	 * @param userId
	 * @throws IOException
	 */
	public void sendCrowdOffLineMessage(String userId) throws IOException {
		// 获取群聊的离线消息
		List<CrowdChatRecord> list2 = MessageService
				.getOffLineCrowdMessage(userId);
		Map<String, byte[]> mapImage = new HashMap<String, byte[]>();
		byte[] images = null;
		// 发送
		log.info("开始发送私聊离线消息");
		for (Iterator<CrowdChatRecord> iterator = list2.iterator(); iterator
				.hasNext();) {
			CrowdChatRecord chat = (CrowdChatRecord) iterator.next();
			if (mapImage.get(chat.getUserId()) == null) {
				images = new UserService().getUserSmallHeadByte(chat
						.getUserId());
				mapImage.put(chat.getUserId(), images);
			}
			sendCrowdMessage(chat, mapImage.get(chat.getUserId()));
		}
		log.info("-----------结束发送--------");
	}

	/**
	 * 发送离线消息
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendOffLineMessage() throws Exception {
		String userId = "";
		try {
			userId = getString();
		} catch (Exception e) {
			log.info("获取离线消息....");
		}
		log.info("userId:" + userId);
		// 发送私聊离线消息
		log.info("============发送私聊离线消息=============");
		sendPrivateOffLineMessage(userId);
		// 发送我的消息离线消息
		//log.info("============发送私聊离线消息=============");
		//new MyMessageHandler().sendOffMyMessage(userId);
		// 群聊离线消息
		log.info("============发送群聊离线消息=============");
		// sendCrowdOffLineMessage(userId);
	}
}
