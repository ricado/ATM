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
			// TODO ��ת
			sendType = Integer.parseInt(in.nextLine());
			log.info(sendType + "");
			switch (sendType) {
			case Config.REQUEST_LOGIN: // ��¼
				login();
				break;
			case Config.MESSAGE_TO:
				sendMessage();
				break;
			case Config.CROWD_MESSAGE_TO: // ����Ⱥ����Ϣ
				sendCrowdMessage();
				break;
			case Config.FIND_MY_CROWD: // �ҵ�Ⱥ
				findMyCrowd();
				break;
			case Config.FIND_CROWD: // ����Ⱥ
				findCrowd();
				break;
			case Config.FIND_USER: // �����û�
				findUser();
				break;
			case Config.CROWD_INTIVE:// �����Ⱥ
				intiveToCrowd();
				break;
			case Config.REQUEST_EXIT: // �����˳�
				exit();
				break;
			case Config.CROWD_APPLY:// �����Ⱥ
				applyToCrowd();
				break;
			case Config.CROWD_ACCEPT:// ��������:
				applyToCrowd();
				break;
			case Config.CROWD_NOACCEPT:// �ܾ�����
				log.info("�ܾ�����");
				break;
			case Config.CROWD_AGREE: // ͬ������
				agreeApply();
				break;
			case Config.CROWD_NOAGREE:// �ܾ�
				log.info("�ܾ�");
				break;
			case Config.ADD_FRIEND: // ��ӹ�ע
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
	 * ��¼����
	 * 
	 * @throws Exception
	 */
	// TODO ��¼
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
	 * ����˽��
	 */
	// TODO ����˽��
	public void sendMessage() {
		log.info("==============��˽��===============");
		// ��ȡ����
		log.info("���id:");
		String userId = in.nextLine();
		log.info("friendId:");
		String friendId = in.nextLine();
		log.info("����:");
		String content = in.nextLine();

		buffer = ByteBuffer.allocateDirect(16 + userId.getBytes().length
				+ friendId.getBytes().length + content.getBytes().length);
		// д��buffer������
		buffer.putInt(Config.MESSAGE_TO);
		put(userId);
		put(friendId);
		put(content);

		writeBuffer();

	}

	/**
	 * ����Ⱥ����Ϣ
	 */
	// TODO ����Ⱥ����Ϣ
	public void sendCrowdMessage() {
		log.info("============��Ⱥ��==============");
		log.info("your id:");
		String userId = in.nextLine();
		log.info("Ⱥid:");
		int crowdId = in.nextInt();
		in.nextLine();// ȺID(Int)��Ҫָ����һ��
		log.info("����:");
		String content = in.nextLine();
		// ////////////////�ض���buffer
		buffer = ByteBuffer.allocateDirect(20 + userId.getBytes().length
				+ content.getBytes().length);

		buffer.putInt(Config.CROWD_MESSAGE_TO);// Ⱥ����Ϣ
		buffer.putInt(Config.CROWD_MESSAGE_TEXT);// �ı���Ϣ
		put(userId);
		buffer.putInt(crowdId);
		put(content);

		writeBuffer();
	}

	/**
	 * ����Ⱥ��
	 */
	// TODO ����Ⱥ��
	public void createCrowd() {
		log.info("=====������Ⱥ��=====");
		String userId = in.nextLine();
		String crowdName = in.nextLine();
		buffer = ByteBuffer.allocateDirect(12 + userId.getBytes().length
				+ crowdName.getBytes().length);

		buffer.putInt(Config.CROWD_CREATE);
		// ����Ⱥ�ĵ�Ⱥ��
		put(userId);
		// Ⱥ������
		put(crowdName);
		writeBuffer();
	}

	/**
	 * ��ȡ�û���Ϣ
	 */
	public void getUserInfo() {
		// TODO getUserInfo
	}

	/**
	 * ��ȡȺ����Ϣ
	 */
	public void getCrowdInfo() {
		// TODO getCrowdInfo
	}

	/**
	 * �����Ҽ����Ⱥ�����������Ⱥ�ʹ�����Ⱥ
	 */
	public void findMyCrowd() {
		log.info("===========�鿴�ҵ�Ⱥ=============");
		log.info("your id:");
		String userId = in.nextLine();

		buffer = ByteBuffer.allocateDirect(8);
		buffer.putInt(Config.CROWD_MY);
		put(userId);

		writeBuffer();
	}

	/**
	 * ͨ�����������Ѱ����ص�Ⱥ
	 */
	public void findCrowd() {
		// TODO findCrowd
		log.info("========����Ⱥ==========");
		log.info("��������������:");
		String tip = in.nextLine();
		// ����Ⱥ
		buffer = ByteBuffer.allocateDirect(8 + tip.getBytes().length);
		buffer.putInt(Config.CROWD_FIND);
		put(tip);

		writeBuffer();
	}

	/**
	 * ͨ�����������Ѱ����ص�user
	 */
	public void findUser() {
		// TODO findUser
		log.info("========�����û�==========");
		log.info("��������������:");
		String tip = in.nextLine();
		// �����û�
		buffer = ByteBuffer.allocateDirect(8 + tip.getBytes().length);
		buffer.putInt(Config.USER_FIND);
		put(tip);

		writeBuffer();
	}

	/**
	 * �����Ⱥ
	 */
	public void intiveToCrowd() {
		log.info("===========�����Ⱥ=========");
		log.info("�������id:");
		String userId = in.nextLine();
		log.info("������ID:");
		String intivedId = in.nextLine();
		log.info("Ⱥ���:");
		int crowdId = in.nextInt();
		in.nextLine();

		// ����buffer
		buffer = ByteBuffer.allocateDirect(16 + userId.getBytes().length
				+ intivedId.getBytes().length);

		buffer.putInt(Config.CROWD_INTIVE);
		put(userId);
		put(intivedId);
		buffer.putInt(crowdId);
		writeBuffer();

	}

	/******************* ��Ӻ��� ************************************************/

	public void addFriend() {
		log.info("===========��Ӻ���==============");
		log.info("���ID:");
		String userAttendId = in.nextLine();// ��ע�˵�Id
		log.info("��ע��Id:");
		String userAttendedId = in.nextLine();// ����ע����Id

		buffer = ByteBuffer.allocateDirect(12
				+ userAttendedId.getBytes().length
				+ userAttendedId.getBytes().length);
		buffer.putInt(Config.USER_ADD_ATTENT);// ��ӹ�ע��
		put(userAttendId);
		put(userAttendedId);

		writeBuffer();

	}

	/**
	 * ���ҹ�ע�ҵ�
	 */
	public void findAttentMe() {
		log.info("���id:");
		String userId = in.nextLine();
		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);
		buffer.putInt(Config.USER_GET_ATTENTED);
		put(userId);
		writeBuffer();
	}

	/**
	 * �����ҹ�ע��
	 */
	public void findMyAttent() {
		log.info("���id:");
		String userId = in.nextLine();
		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);
		buffer.putInt(Config.USER_GET_ATTENT);
		put(userId);
		writeBuffer();
	}

	/**
	 * �����˳�
	 */
	public void exit() {
		// TODO apply exit
		log.info("==============�˳�==============");
		String userId = in.nextLine();

		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);

		buffer.putInt(Config.REQUEST_EXIT);
		put(userId);
		writeBuffer();
	}

	/**
	 * д���ܵ�������
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
	 * ���ļ�ת���byte[]����
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
	 * ��buffer�з��ַ������ֽ����鳤���Լ����ֽ�����
	 * 
	 * @param string
	 */
	public void put(String string) {
		buffer.putInt(string.getBytes().length);
		buffer.put(string.getBytes());
	}

	/**
	 * �Խ�bufferд��socketchannel.
	 */
	public void writeBuffer() {
		buffer.flip();// ��buffer����ת��
		try {
			socketChannel.write(buffer);
			buffer.clear();
			log.info("д��ɹ�");
		} catch (Exception e) {
			log.info("д��ʧ��");
		}
	}

	public void lookOnline() {
		buffer = ByteBuffer.allocateDirect(4);
		buffer.putInt(9999);
	}

	/**
	 * �Ƿ�����������Ⱥ�ġ�������գ����������Ⱥ
	 * 
	 * @param intiveId
	 * @param intivedId
	 * @param crowdId
	 * @param time
	 */
	public void acceptToCrowd(String intiveId, String intivedId, int crowdId,
			String time) {
		log.info("============�Ƿ�ͬ���������" + crowdId + "(Y/N)=========");
		String y = in.nextLine();
		if (y.equals("Y")) {
			// ͬ�⡣
			log.info("ͬ��");
			sendApply(intiveId, crowdId, intiveId);
		} else {
			log.info("=======�Ѿ��ܾ�=========");
		}
	}

	/**
	 * �������Ⱥ��
	 */
	public void applyToCrowd() {
		log.info("===========�������Ⱥ=========");
		log.info("���Id:");
		String applyId = in.nextLine();
		log.info("ȺId:");
		int crowdId = in.nextInt();
		in.nextLine();
		log.info("���޽�����:");
		String intiveId = in.nextLine();
		sendApply(applyId, crowdId, intiveId);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param applyId
	 *            ������Id
	 * @param crowdId
	 *            Ⱥ���
	 * @param intiveId
	 *            ������id
	 */
	public void sendApply(String applyId, int crowdId, String intiveId) {
		// ����buffer��С
		buffer = ByteBuffer.allocateDirect(16 + applyId.getBytes().length
				+ intiveId.getBytes().length);
		buffer.putInt(Config.CROWD_APPLY);
		put(applyId);
		buffer.putInt(crowdId);
		put(intiveId);

		writeBuffer();
	}

	/**
	 * �Ƿ�ͬ���Ⱥ
	 * 
	 * @param applyId
	 *            ������ID
	 * @param crowdId
	 *            Ⱥ���
	 */
	public void agreeApply() {
		log.info("===========�Ƿ�ͬ���Ⱥ(Y/N)");

		log.info("���Id:");
		String userId = in.nextLine();
		log.info("������ID:");
		String applyId = in.nextLine();
		log.info("Ⱥ���:");
		int crowdId = in.nextInt();
		in.nextLine();
		buffer = ByteBuffer.allocateDirect(16 + applyId.getBytes().length
				+ userId.getBytes().length);

		log.info("-----------ͬ��");
		buffer.putInt(Config.SUCCESS);
		put(userId);
		put(applyId);
		buffer.putInt(crowdId);

		// д���ܵ�
		writeBuffer();
	}
	/**
	 * ���û�ǿ������
	 * @param userId
	 */
	public void delete(String userId){
		
		log.info("���� AdminWriteHandler--------delete");
		buffer = ByteBuffer.allocateDirect(8 + userId.getBytes().length);
		buffer.putInt(Config.REQUEST_EXIT);
		put(userId);
		log.info("userId");
		writeBuffer();
	}
	
	public void move(String userId,int number){
		log.info("�ƶ��û������ReadSelector");
		
		buffer = ByteBuffer.allocateDirect(12+userId.getBytes().length);
		buffer.putInt(Config.USER_MOVE);
		put(userId);
		buffer.putInt(number);
		
		writeBuffer();
	}
}
