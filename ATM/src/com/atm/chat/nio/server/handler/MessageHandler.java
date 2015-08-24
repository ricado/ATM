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
		case Config.MESSAGE_TO://˽����Ϣ
			receivePrivateMessage();
			break;
		case Config.CROWD_MESSAGE_TO://Ⱥ����Ϣ
			receiveCrowdMessage();
		default:
			break;
		}
	}
	/**
	 * ����˽��������Ϣ
	 * 
	 * @throws Exception
	 */
	public void receivePrivateMessage() throws Exception {
		int messageType = getInt();// ˽������
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

		// ������Ϣ
		sendMessage(chat);
	}

	/**
	 * ˽�� ͼƬ ��Ϣ
	 * @throws Exception 
	 */
	private String receivePrivateTextMessage(String userId, String friendId) throws Exception {
		log.info("�����ı���Ϣ");
		String content = getString();
		return content;
	}

	/**
	 * �����ı���Ϣ
	 * 
	 * @param userId
	 * @param friendId
	 * @throws Exception 
	 */
	private String receivePrivateImageMessage(String userId, String friendId)
			throws Exception {
		log.info("����ͼƬ��Ϣ");
		String filename = getString();
		int imageLength = getInt();
		byte[] imagebytes = FileUtil.getFileBytes(socketChannel, imageLength);
		String path = FileUtil.getPrivateChatPath(filename, userId, friendId,
				imagebytes);
		return path;
	}

	/**
	 * ������Ϣ
	 * 
	 * @param chat
	 * @throws Exception
	 * @throws IOException
	 */
	public void sendMessage(PrivateChat chat) throws Exception {
		if (map.get(chat.getUserReceiveId()) == null) {
			log.info("�û�������");
			MessageService.savePrivateChat(chat);
		} else {
			log.info("�û�����");
			if (chat.getFlag() == 0) {
				sendTextMessage(chat);
			} else if (chat.getFlag() == 1) {
				sendImageMessage(chat);
			}
		}
	}

	/**
	 * ����˽���ı���Ϣ
	 * 
	 * @param chat
	 * @throws UnsupportedEncodingException 
	 */
	private void sendTextMessage(PrivateChat chat) throws UnsupportedEncodingException {
		log.info("=========˽��======�����ı���Ϣ================");

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
	 * ����˽��ͼƬ��Ϣ
	 * 
	 * @param chat
	 * @throws IOException
	 */
	private void sendImageMessage(PrivateChat chat) throws IOException {
		byte[] imageBytes = FileUtil.makeFileToByte(chat.getSendContent());
		String filename = new File(chat.getSendContent()).getName();
		log.info("=========˽��======����ͼƬ��Ϣ================");
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
	 * ��ȡȫ��������Ϣ
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendPrivateOffLineMessage(String userId) throws Exception {
		// ���˽��������Ϣ
		List<PrivateChat> list = MessageService.getOffLineMessage(userId);
		// ����
		log.info("��ʼ����˽��������Ϣ");
		for (Iterator<PrivateChat> iterator = list.iterator(); iterator
				.hasNext();) {
			PrivateChat chat = iterator.next();
			sendMessage(chat);
		}
		log.info("-----------��������--------");
	}

	/**
	 * ����Ⱥ����Ϣ
	 * @throws Exception 
	 */
	public void receiveCrowdMessage() throws Exception {
		int type = getInt();// Ⱥ��Ϣ����
		String userId = getString(); // ������ID
		int crowdId = getInt(); // Ⱥ���
		String content = null;
		// ��ӡ��Ϣ
		log.info("-----------Ⱥ��" + crowdId + "------------------");
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
	 * ����Ⱥ���ı�
	 * 
	 * @return
	 * @throws Exception 
	 */
	private String receiveTextMessage() throws Exception {
		String content = getString();
		return content;
	}

	/**
	 * ����Ⱥ��ͼƬ
	 * 
	 * @param crowdId
	 * @return
	 * @throws Exception 
	 */
	private String receiveImageMessage(int crowdId) throws Exception {
		String filename = getString();// �ļ���
		int fileLenght = getInt();// �ļ�����
		byte[] imagebyte = FileUtil.getFileBytes(socketChannel, fileLenght);// �ļ��ֽ�����
		log.info("��������Ⱥ�ĵ�ͼƬ������·��");
		String content = FileUtil.getCrowdPath(filename, crowdId, imagebyte);// ����ͼƬ

		log.info("ͼƬ��-----");
		log.info("filelenght:" + fileLenght + "  contentsLength:"
				+ imagebyte.length + "filepath:" + content);
		return content;
	}

	/**
	 * ����Ⱥ����Ϣ
	 * 
	 * @param chat
	 * @throws IOException
	 */
	public void sendCrowdMessage(ChatRecord chat) throws IOException {
		/**
		 * ��ȡȺ�ĵ���
		 */
		List<MenberList> userIds = crowdMenberService.findAllMenber(chat.getCrowdId());
		log.info(">>>>>>>>>>>>>��ʼȺ����Ϣ:");
		// Ⱥ����Ϣ�������ݿ�
		MessageService.saveCrowdRecord(chat.getCrowdId(), chat.getUserId(),
				chat.getSendContent());

		for (int i = 0; i < userIds.size(); i++) {
			SocketChannel channel = map.get(userIds.get(i).getUserId());
			if (channel == null) {
				log.info("�û�" + userIds.get(i).getUserId() + "������");
			} else if (socketChannel.equals(channel)) {
				log.info("�û�����");
			} else {
				sendMessage(chat, channel);
			}
		}

	}

	/**
	 * ���͸����ܵ���Ⱥ����Ϣ
	 * 
	 * @param chat
	 * @throws IOException
	 */
	public void sendMessage(ChatRecord chat) throws IOException {
		log.info("���͸�" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// �ı���Ϣ
			sendTextMessage(chat, socketChannel);
		} else if (chat.getFlag() == 1) {
			// ͼƬ��Ϣ
			sendImageMessage(chat, socketChannel);
		}
	}

	/**
	 * ���͸������ܵ���Ⱥ����Ϣ
	 * 
	 * @param chat
	 * @param channel
	 * @throws IOException
	 */
	public void sendMessage(ChatRecord chat, SocketChannel channel)
			throws IOException {
		log.info("���͸�" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// �ı���Ϣ
			sendTextMessage(chat, channel);
		} else if (chat.getFlag() == 1) {
			// ͼƬ��Ϣ
			sendImageMessage(chat, channel);
		}
	}

	/**
	 * ����Ⱥ���ı���Ϣ
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
		// ����Ϣд��buffer
		buffer.putInt(Config.CROWD_MESSAGE_FROM);
		buffer.putInt(Config.CROWD_MESSAGE_TEXT);
		put(chat.getUserId());
		buffer.putInt(chat.getCrowdId());
		put(chat.getSendTime().toString());
		put(chat.getSendContent());

		writeBuffer(channel);
	}

	/**
	 * ����Ⱥ��ͼƬ��Ϣ
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
		// ����Ϣд��buffer

		buffer.putInt(Config.CROWD_MESSAGE_FROM);
		buffer.putInt(Config.CROWD_MESSAGE_IMG);
		put(chat.getUserId());
		buffer.putInt(chat.getCrowdId());
		put(chat.getSendTime().toString());
		put(filename);
		buffer.putInt(imageBytes.length);
		buffer.put(imageBytes);

		writeBuffer(channel);
		log.info("���ͳɹ�");
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param userId
	 * @throws IOException
	 */
	public void sendCrowdOffLineMessage(String userId) throws IOException {
		// ��ȡȺ�ĵ�������Ϣ
		List<ChatRecord> list2 = MessageService.getOffLineCrowdMessage(userId);
		// ����
		log.info("��ʼ����˽��������Ϣ");
		for (Iterator<ChatRecord> iterator = list2.iterator(); iterator
				.hasNext();) {
			ChatRecord chatRecord = (ChatRecord) iterator.next();
			sendMessage(chatRecord);
		}
		log.info("-----------��������--------");
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendOffLineMessage(String userId, SocketChannel channel)
			throws Exception {
		// ����˽��������Ϣ
		log.info("============����˽��������Ϣ=============");
		sendPrivateOffLineMessage(userId);
		// Ⱥ��������Ϣ
		log.info("============����Ⱥ��������Ϣ=============");
		sendCrowdOffLineMessage(userId);
	}
}
