package com.atm.chat.nio.server.handler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;

import com.atm.chat.nio.server.util.Config;
import com.atm.model.chat.ChatRecord;
import com.atm.model.chat.PrivateChat;
import com.atm.model.define.chat.MenberList;
import com.atm.service.MessageService;
import com.atm.service.crowd.CrowdMenberService;
import com.atm.util.FileUtil;
import com.atm.util.TimeUtil;

public class MessageHandler extends BufferHandler {

	private CrowdMenberService crowdMenberService = new CrowdMenberService();
	public MessageHandler() {}

	public void operate(int type) throws Exception{
		switch (type) {
		case Config.MESSAGE_TO://私信消息
			receivePrivateMessage();
			break;
		case Config.CROWD_MESSAGE_TO://群聊消息
			receiveCrowdMessage();
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
		String content = null;

		PrivateChat chat = new PrivateChat();
		chat.setUserSendId(userId);
		chat.setUserReceiveId(friendId);
		chat.setSendTime(TimeUtil.getTimestamp());
		if (messageType == Config.MESSAGE_TEXT) {
			chat.setFlag(0);
			content = receivePrivateTextMessage(userId, friendId);
		} else if (messageType == Config.MESSAGE_IMG) {
			chat.setFlag(1);
			content = receivePrivateImageMessage(userId, friendId);
		}
		chat.setSendContent(content);

		// 发送消息
		sendMessage(chat);
	}

	/**
	 * 私信 图片 消息
	 * @throws Exception 
	 */
	private String receivePrivateTextMessage(String userId, String friendId) throws Exception {
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
	public void sendMessage(PrivateChat chat) throws Exception {
		if (map.get(chat.getUserReceiveId()) == null) {
			log.info("用户不在线");
			MessageService.savePrivateChat(chat);
		} else {
			log.info("用户在线");
			if (chat.getFlag() == 0) {
				sendTextMessage(chat);
			} else if (chat.getFlag() == 1) {
				sendImageMessage(chat);
			}
		}
	}

	/**
	 * 发送私信文本消息
	 * 
	 * @param chat
	 * @throws UnsupportedEncodingException 
	 */
	private void sendTextMessage(PrivateChat chat) throws UnsupportedEncodingException {
		log.info("=========私信======发送文本消息================");

		buffer = ByteBuffer.allocateDirect(24
				+ chat.getUserSendId().getBytes().length
				+ chat.getUserReceiveId().getBytes().length
				+ chat.getSendTime().toString().getBytes().length
				+ chat.getSendContent().getBytes().length);
		buffer.putInt(Config.MESSAGE_FROM);
		buffer.putInt(Config.MESSAGE_TEXT);
		put(chat.getUserSendId());
		put(chat.getUserReceiveId());
		put(chat.getSendTime().toString());
		put(chat.getSendContent());
		
		writeBuffer(map.get(chat.getUserReceiveId()));
		log.info("=============success==================");
	}

	/**
	 * 发送私信图片消息
	 * 
	 * @param chat
	 * @throws IOException
	 */
	private void sendImageMessage(PrivateChat chat) throws IOException {
		byte[] imageBytes = FileUtil.makeFileToByte(chat.getSendContent());
		String filename = new File(chat.getSendContent()).getName();
		log.info("=========私信======发送图片消息================");
		buffer = ByteBuffer.allocateDirect(24
				+ chat.getUserSendId().getBytes().length
				+ chat.getUserReceiveId().getBytes().length
				+ chat.getSendTime().toString().getBytes().length
				+ filename.getBytes().length + imageBytes.length);

		buffer.putInt(Config.MESSAGE_TO);
		buffer.putInt(Config.MESSAGE_IMG);
		put(chat.getUserSendId());
		put(chat.getUserReceiveId());
		put(chat.getSendTime().toString());
		put(filename);
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
		List<PrivateChat> list = MessageService.getOffLineMessage(userId);
		// 发送
		log.info("开始发送私聊离线消息");
		for (Iterator<PrivateChat> iterator = list.iterator(); iterator
				.hasNext();) {
			PrivateChat chat = iterator.next();
			sendMessage(chat);
		}
		log.info("-----------结束发送--------");
	}

	/**
	 * 接收群聊消息
	 * @throws Exception 
	 */
	public void receiveCrowdMessage() throws Exception {
		int type = getInt();// 群消息类型
		String userId = getString(); // 发送者ID
		int crowdId = getInt(); // 群编号
		String content = null;
		// 打印消息
		log.info("-----------群聊" + crowdId + "------------------");
		log.info(">>>>>>>>>>>userId:" + userId);

		ChatRecord chat = new ChatRecord();
		chat.setCrowdId(crowdId);
		chat.setUserId(userId);
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

		sendCrowdMessage(chat);
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
	public void sendCrowdMessage(ChatRecord chat) throws IOException {
		/**
		 * 获取群聊的人
		 */
		List<MenberList> userIds = crowdMenberService.findAllMenber(chat.getCrowdId());
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
				sendMessage(chat, channel);
			}
		}

	}

	/**
	 * 发送给本管道的群聊消息
	 * 
	 * @param chat
	 * @throws IOException
	 */
	public void sendMessage(ChatRecord chat) throws IOException {
		log.info("发送给" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// 文本消息
			sendTextMessage(chat, socketChannel);
		} else if (chat.getFlag() == 1) {
			// 图片消息
			sendImageMessage(chat, socketChannel);
		}
	}

	/**
	 * 发送给其他管道的群聊消息
	 * 
	 * @param chat
	 * @param channel
	 * @throws IOException
	 */
	public void sendMessage(ChatRecord chat, SocketChannel channel)
			throws IOException {
		log.info("发送给" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// 文本消息
			sendTextMessage(chat, channel);
		} else if (chat.getFlag() == 1) {
			// 图片消息
			sendImageMessage(chat, channel);
		}
	}

	/**
	 * 发送群聊文本消息
	 * 
	 * @param chat
	 * @param channel
	 * @throws UnsupportedEncodingException 
	 */
	private void sendTextMessage(ChatRecord chat, SocketChannel channel) throws UnsupportedEncodingException {
		buffer = ByteBuffer.allocateDirect(35
				+ chat.getUserId().getBytes().length
				+ chat.getSendContent().getBytes().length
				+ chat.getSendTime().toString().getBytes().length);

		log.info("sendMessage:" + chat.getUserId() + " to " + chat.getCrowdId()
				+ " content:" + chat.getSendContent());
		// 将信息写入buffer
		buffer.putInt(Config.CROWD_MESSAGE_FROM);
		buffer.putInt(Config.CROWD_MESSAGE_TEXT);
		put(chat.getUserId());
		buffer.putInt(chat.getCrowdId());
		put(chat.getSendTime().toString());
		put(chat.getSendContent());

		writeBuffer(channel);
	}

	/**
	 * 发送群聊图片消息
	 * 
	 * @param chat
	 * @param channel
	 * @throws IOException
	 */
	private void sendImageMessage(ChatRecord chat, SocketChannel channel)
			throws IOException {
		String filename = new File(chat.getSendContent()).getName();
		byte[] imageBytes = FileUtil.makeFileToByte(chat.getSendContent());
		buffer = ByteBuffer.allocateDirect(35
				+ chat.getUserId().getBytes().length
				+ filename.getBytes().length + imageBytes.length
				+ chat.getSendTime().toString().getBytes().length);

		log.info("sendMessage:" + chat.getUserId() + " to " + chat.getCrowdId()
				+ " content:");
		// 将信息写入buffer

		buffer.putInt(Config.CROWD_MESSAGE_FROM);
		buffer.putInt(Config.CROWD_MESSAGE_IMG);
		put(chat.getUserId());
		buffer.putInt(chat.getCrowdId());
		put(chat.getSendTime().toString());
		put(filename);
		buffer.putInt(imageBytes.length);
		buffer.put(imageBytes);

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
		List<ChatRecord> list2 = MessageService.getOffLineCrowdMessage(userId);
		// 发送
		log.info("开始发送私聊离线消息");
		for (Iterator<ChatRecord> iterator = list2.iterator(); iterator
				.hasNext();) {
			ChatRecord chatRecord = (ChatRecord) iterator.next();
			sendMessage(chatRecord);
		}
		log.info("-----------结束发送--------");
	}

	/**
	 * 发送离线消息
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendOffLineMessage(String userId, SocketChannel channel)
			throws Exception {
		// 发送私聊离线消息
		log.info("============发送私聊离线消息=============");
		sendPrivateOffLineMessage(userId);
		// 群聊离线消息
		log.info("============发送群聊离线消息=============");
		sendCrowdOffLineMessage(userId);
	}
}
