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
				Set selectedKeys = selector.selectedKeys(); // ����ͨ�����������֪������ͨ���ļ���
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
	 * ������������͹�������Ϣ��ת����
	 * 
	 * @param sk
	 * @throws IOException
	 */
	// TODO ��Ϣ��תվ
	private void dealWithSelectionKey(SelectionKey sk) throws IOException {

		if (sk.isReadable()) {
			// ʹ�� NIO ��ȡ Channel�е����ݣ������ȫ�ֱ���sc��һ���ģ���Ϊֻע����һ��SocketChannel
			// sc����дҲ�ܶ�������Ƕ�
			socketChannel = (SocketChannel) sk.channel();
			int type = getInt();
			log.info("type:" + type);
			// ��Ϣ��ת
			switch (type) {
			case Config.RESULT_LOGIN:// ��¼
				loginResult();
				break;
			case Config.MESSAGE_FROM:// ������Ϣ
				receiveMessage();
				break;
			case Config.CROWD_MESSAGE_FROM: // ����Ⱥ����Ϣ
				receiveCrowdMessage();
				break;
			case Config.CROWD_MY: // �ҵ�Ⱥ
				printMyCrowd();
			case Config.CROWD_FIND:// ������Ⱥ
				printfindCrowd();
				break;
			case Config.CROWD_LIST:
				printfindCrowd();
				break;
			case Config.USER_FIND:// �������û�
				printfindUser();
			case Config.CROWD_APPLY:// �������Ⱥ
				applyToCrowd();
				break;
			case Config.CROWD_INTIVE:// ���յ������Ⱥ
				receiveToCrowd();
				break;
			case Config.CROWD_RESULT_APPLY:// ͬ���Ⱥ
				applyResult();
				break;
			case Config.USER_GET_ATTENT:// �ҹ�ע��
				attentMe();
				break;
			case Config.USER_GET_ATTENTED:// ��ע�ҵ�
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
	 * ��¼���
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
	 * �յ�˽�� ������ʾ����
	 */
	// TODO receiveMessage
	public void receiveMessage() {
		try {
			int type = getInt();
			String userId = getString();
			String friendId = getString();
			String time = getString();
			String content = getString();

			log.info("-------------����" + userId + "��˽��---------------");
			log.info(">>>>>>>>>>>time:" + time);
			log.info("content:" + content);
			log.info("-------------end---------------");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("receiveMessage error");
		}
	}

	/**
	 * ��ӡȺ����Ϣ
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

			log.info("-----------Ⱥ��" + crowdId + "------------------");
			log.info(">>>>>>>>>>>time:" + time);
			log.info(">>>>>>>>>>>userId:" + userId);

			if (messageType == Config.CROWD_MESSAGE_TEXT) {
				content = getString();
				log.info("content:" + content);
			} else if (messageType == Config.CROWD_MESSAGE_IMG) {
				// TODO Ⱥ��ͼƬͼƬ�Ĵ���
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
	 * �ҵ�Ⱥ
	 * 
	 * @param json
	 */
	public void printMyCrowd() {
		String menbersList = jsonObject.getString("menbersList");
		JSONArray jsonArray = new JSONArray().fromObject(menbersList);
		log.info("=============�ҵ�Ⱥ===============");
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
	 * ��ӡ���Ҳ��ҵ�Ⱥ
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
	 * ��ӡ���Ҳ��ҵ��û�
	 */
	public void printfindUser() {
		String users = jsonObject.getString("users");
		JSONArray jsonArray = new JSONArray().fromObject(users);
		log.info("=============�����û�===============");
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
	 * ����Ⱥ�б����Ϣ��
	 * 
	 * @throws IOException
	 */
	public void receiveCrowdListInfo() throws IOException {
		int crowdId = getInt();// ȺID
		String crowdName = getString();
		String filename = getString();
		int fileLength = getInt();
		byte[] bytes = getFileBytes();
		String path = saveFile(filename, crowdId + "", bytes);

		// ��ʾ����
		log.info("=============Ⱥ�б�==============");
		log.info("crowdId:" + crowdId);
		log.info("crowdName: " + crowdName);
		log.info("filename:" + filename);
		log.info("fileLength:" + fileLength);
		log.info("bytesLength:" + bytes.length);
		log.info("---------------------------------");
	}

	public void createCrowdSuccess() throws IOException {
		log.info("�����ɹ���Ⱥ��Ϊ:" + getString());
	}

	public void createCrowdFail() {
		log.info("����ʧ�ܣ�so sad");
	}

	/**
	 * ���յ�����Ⱥ����
	 * 
	 * @throws IOException
	 */
	public void receiveToCrowd() throws IOException {
		String intiveId = getString();
		String intivedId = getString();
		String time = getString();
		int crowdId = getInt();
		log.info("=========���յ�" + intiveId + "�Ľ�Ⱥ����");
		log.info("ʱ��:" + time);
		log.info("crowdId:" + crowdId);
		log.info("�Ƿ�ͬ���Ⱥ(1303---1304)");
		// clientSend.acceptToCrowd(intiveId, intivedId, crowdId, time);
	}

	/**
	 * ���û��������ĳȺ
	 * 
	 * @throws IOException
	 */
	public void applyToCrowd() throws IOException {
		String applyId = getString();
		int crowdId = getInt();
		String time = getString();
		log.info("=======�����Ⱥ=======");
		log.info("time:" + time);
		log.info("userId:" + applyId);
		log.info("crowdId:" + crowdId);
		log.info("�Ƿ�����Ⱥ(1306---1307)");
		// log.info(">>>>>>>>>>���� ClientSend.applyToCrowd()");
	}

	public void applyResult() throws IOException {
		int result = getInt();
		int crowdId = getInt();
		if (result == Config.SUCCESS) {
			log.info("ͬ���Ⱥ" + crowdId);
		} else if (result == Config.FAILED) {
			log.info("���ܾ�����Ⱥ" + crowdId);
		} else {
			log.info(crowdId + "Ⱥ����");
		}
	}

	/**************************************** ��ע ***************************************************/

	/**
	 * ��ע�ҵ�
	 * 
	 * @throws IOException
	 */
	public void attentMe() throws IOException {
		int length = getInt();
		log.info("---------------��ע�ҵ�--------------");
		for (int i = 0; i < length; i++) {
			log.info("user--" + i + ":" + getString());
		}
		log.info("-----------------end----------------");
	}

	/**
	 * �ҹ�ע��
	 * 
	 * @throws IOException
	 */
	public void myAttent() throws IOException {
		int length = getInt();
		log.info("---------------�ҹ�ע��--------------");
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
	 * ��ȡ�ļ���byte����
	 * 
	 * @return byte[]
	 * @throws IOException
	 */
	public byte[] getFileBytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// �ļ����ܻ�ܴ�
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
	 * ��ȡ�ļ���byte����
	 * 
	 * @return byte[]
	 * @throws IOException
	 */
	public byte[] getFileBytes(int length) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// �ļ����ܻ�ܴ�
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
	 * �����ļ���Ϣ
	 * 
	 * @param filename
	 * @param userId
	 * @return filepath
	 * @throws IOException
	 */
	// TODO �����ļ���Ϣ
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
