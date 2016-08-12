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
		case Config.MESSAGE_TO:// ˽����Ϣ
			receivePrivateMessage();
			break;
		case Config.CROWD_MESSAGE_TO:// Ⱥ����Ϣ
			receiveCrowdMessage();
			break;
		case Config.MESSAGE_OFFLINE:// ������Ϣ
			sendOffLineMessage();
			break;
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
		log.info("������Ϣʱ��:" + time);
		String content = null;
		// ��ȡ�����˵���Ϣ
		UserInfo userInfo = userService.getUserInfo(userId);
		// ������ͷ������ͼ�ֽ�����
		String path = this.getClass().getResource("/").getPath().substring(1)
				.replaceFirst("WEB-INF/classes/", "");
		log.info(path + userInfo.getHeadImagePath());
		// ��ȡ�û�ͷ����ֽ�����
		byte[] image = FileUtil.makeFileToByte(path
				+ userInfo.getHeadImagePath());
		// ����˽��ʵ��
		PrivateChatRecord chat = new PrivateChatRecord();
		chat.setUserSendId(userId);
		chat.setUserReceiveId(friendId);
		chat.setNickname(userInfo.getNickname());
		// ʱ��
		chat.setSendTime(TimeUtil.getTimestamp());
		log.info(">>>>�ж���Ϣ����");
		if (messageType == Config.MESSAGE_TEXT) {
			log.info(">>>>Ϊ�ı�����");
			chat.setFlag(0);
			content = receivePrivateTextMessage(userId, friendId);
			log.info("content:" + content);
		} else if (messageType == Config.MESSAGE_IMG) {
			log.info(">>>>ΪͼƬ��Ϣ");
			chat.setFlag(1);
			content = receivePrivateImageMessage(userId, friendId);
		}
		chat.setSendContent(content);

		// ������Ϣ
		log.info("������Ϣ");
		sendMessage(chat, image);
	}

	/**
	 * ˽�� ͼƬ ��Ϣ
	 * 
	 * @throws Exception
	 */
	private String receivePrivateTextMessage(String userId, String friendId)
			throws Exception {
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
		log.info("filename:" + filename);
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
	public void sendMessage(PrivateChatRecord chat, byte[] image)
			throws Exception {
		if (map.get(chat.getUserReceiveId()) == null) {
			log.info("�û�������");
			MessageService.savePrivateChat(chat);
		} else {
			log.info("�û�����");
			if (chat.getFlag() == 0) {
				sendTextMessage(chat, image);
			} else if (chat.getFlag() == 1) {
				sendImageMessage(chat, image);
			}
		}
	}

	/**
	 * ����˽���ı���Ϣ
	 * 
	 * @param chat
	 * @throws UnsupportedEncodingException
	 */
	private void sendTextMessage(PrivateChatRecord chat, byte[] image)
			throws UnsupportedEncodingException {
		log.info("=========˽��======�����ı���Ϣ================");
		log.info("����������������nickname:" + chat.getNickname());
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
		// �������ǳ�
		put(chat.getNickname());
		// ������id
		put(chat.getUserReceiveId());
		// ʱ��
		put(TimeUtil.getDateFormat(chat.getSendTime()).toString());
		// �ı�
		put(chat.getSendContent());
		// ͷ������ͼ
		buffer.putInt(image.length);
		buffer.put(image);
		writeBuffer(map.get(chat.getUserReceiveId()));
		log.info("=============success==================");
	}

	/**
	 * ����˽��ͼƬ��Ϣ
	 * 
	 * @param chat
	 * @throws IOException
	 */
	private void sendImageMessage(PrivateChatRecord chat, byte[] image)
			throws IOException {
		log.info("˽��=======ͼƬ��Ϣ");
		byte[] imageBytes = FileUtil.makeFileToByte(chat.getSendContent());
		String filename = new File(chat.getSendContent()).getName();
		log.info("=========˽��======����ͼƬ��Ϣ================");
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
	 * ��ȡȫ��������Ϣ
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendPrivateOffLineMessage(String userId) throws Exception {
		// ���˽��������Ϣ
		List<PrivateChatRecord> list = MessageService.getOffLineMessage(userId);
		// ͷ��map
		Map<String, byte[]> mapImage = new HashMap<String, byte[]>();
		// ͷ���ֽ�����
		byte[] images = null;
		// ����
		log.info("��ʼ����˽��������Ϣ");
		for (Iterator<PrivateChatRecord> iterator = list.iterator(); iterator
				.hasNext();) {
			PrivateChatRecord chat = iterator.next();
			// �鿴map���Ƿ��Ѿ�����userId��ͷ��
			if (mapImage.get(chat.getUserSendId()) == null) {
				images = new UserService().getUserSmallHeadByte(chat
						.getUserSendId());
				mapImage.put(chat.getUserSendId(), images);
			}
			sendMessage(chat, mapImage.get(chat.getUserSendId()));
		}
		log.info("-----------��������--------");
	}

	/**
	 * ����Ⱥ����Ϣ
	 * 
	 * @throws Exception
	 */
	public void receiveCrowdMessage() throws Exception {
		int type = getInt();// Ⱥ��Ϣ����
		String userId = getString(); // ������ID
		int crowdId = getInt(); // Ⱥ���
		String content = null;
		UserInfo userInfo = userService.getUserInfo(userId);
		// ͷ��ͼƬ������ͼ
		String path = this.getClass().getResource("/").getPath().substring(1)
				.replaceFirst("WEB-INF/classes/", "");
		log.info(path);
		byte[] image = FileUtil.makeFileToByte(path
				+ userInfo.getHeadImagePath());

		// ��ӡ��Ϣ
		log.info("-----------Ⱥ��" + crowdId + "------------------");
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
		log.info("��������Ⱥ����Ϣ");
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
	public void sendCrowdMessage(CrowdChatRecord chat, byte[] image)
			throws IOException {
		/**
		 * ��ȡȺ�ĵ���
		 */
		List<MenberList> userIds = crowdMenberService.findAllMenber(chat
				.getCrowdId());
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
				log.info("���͸�" + userIds.get(i).getUserId());
				sendMessage(chat, image, channel);
			}
		}

	}

	/**
	 * ���͸����ܵ���Ⱥ����Ϣ
	 * 
	 * @param chat
	 * @throws IOException
	 */
	public void sendMessage(CrowdChatRecord chat, byte[] image)
			throws IOException {
		log.info("���͸�" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// �ı���Ϣ
			sendTextMessage(chat, image, socketChannel);
		} else if (chat.getFlag() == 1) {
			// ͼƬ��Ϣ
			sendImageMessage(chat, image, socketChannel);
		}
	}

	/**
	 * ���͸������ܵ���Ⱥ����Ϣ
	 * 
	 * @param chat
	 * @param channel
	 * @throws IOException
	 */
	public void sendMessage(CrowdChatRecord chat, byte[] image,
			SocketChannel channel) throws IOException {
		log.info("���͸�" + chat.getUserId());
		if (chat.getFlag() == 0) {
			// �ı���Ϣ
			sendTextMessage(chat, image, channel);
		} else if (chat.getFlag() == 1) {
			// ͼƬ��Ϣ
			sendImageMessage(chat, image, channel);
		}
	}

	/**
	 * ����Ⱥ���ı���Ϣ
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
		// ����Ϣд��buffer
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
	 * ����Ⱥ��ͼƬ��Ϣ
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
		// ����Ϣд��buffer

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
		List<CrowdChatRecord> list2 = MessageService
				.getOffLineCrowdMessage(userId);
		Map<String, byte[]> mapImage = new HashMap<String, byte[]>();
		byte[] images = null;
		// ����
		log.info("��ʼ����˽��������Ϣ");
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
		log.info("-----------��������--------");
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendOffLineMessage() throws Exception {
		String userId = "";
		try {
			userId = getString();
		} catch (Exception e) {
			log.info("��ȡ������Ϣ....");
		}
		log.info("userId:" + userId);
		// ����˽��������Ϣ
		log.info("============����˽��������Ϣ=============");
		sendPrivateOffLineMessage(userId);
		// �����ҵ���Ϣ������Ϣ
		//log.info("============����˽��������Ϣ=============");
		//new MyMessageHandler().sendOffMyMessage(userId);
		// Ⱥ��������Ϣ
		log.info("============����Ⱥ��������Ϣ=============");
		// sendCrowdOffLineMessage(userId);
	}
}
