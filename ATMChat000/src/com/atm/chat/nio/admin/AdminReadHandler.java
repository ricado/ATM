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
				Set selectedKeys = selector.selectedKeys(); // ����ͨ�����������֪������ͨ���ļ���
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
			/*case Config.LOGIN_SUCCESS:// ��¼
				loginSuccess();
				break;
			case Config.LOGIN_FAILED:
				loginFail();
				break;
			case Config.LOGIN_ALREADY:
				loginAlready();*/
				//break;
			case Config.MESSAGE_FROM:// ������Ϣ
				receiveMessage();
				break;
			case Config.CROWD_MESSAGE_FROM: // ����Ⱥ����Ϣ
				receiveCrowdMessage();
				break;
			/*case Config.FIND_MY_CROWD: // �ҵ�Ⱥ
				printMyCrowd();
			case Config.FIND_CROWD:// ������Ⱥ
				printfindCrowd();
				break;
			case Config.FIND_USER:// �������û�
				printfindUser();*/
			case Config.CROWD_APPLY://�������Ⱥ
				applyToCrowd();
				break;
			case Config.CROWD_FULL: //Ⱥ����
				crowdFull();
				break;
			case Config.CROWD_INTIVE://���յ������Ⱥ
				receiveToCrowd();
				break;
			/*case Config.CROWD_AGREE://ͬ���Ⱥ
				agreeToCrowd();
				break;
			case Config.CROWD_NOAGREE://���ܾ���Ⱥװ��
				noAgreeToCrowd();
				break;*/
			case Config.USER_GET_ATTENT://�ҹ�ע��
				attentMe();
				break;
			case Config.USER_GET_ATTENTED://��ע�ҵ�
				myAttent();
				break;
			default:
				break;
			}
			sk.interestOps(SelectionKey.OP_READ);
		}
	}

	// TODO ��¼�ɹ�--ʧ��---�ѵ�¼
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
	 * �յ�˽�� ������ʾ����
	 */
	// TODO receiveMessage
	public void receiveMessage() {
		try {
			String userId = getString();
			String friendId = getString();
			String content = getString();
			String time = getString();

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
	 */
	public void printfindCrowd() {
		String crowds = jsonObject.getString("crowds");
		JSONArray jsonArray = new JSONArray().fromObject(crowds);
		log.info("=============����Ⱥ===============");
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
	 * @throws IOException
	 */
	public void receiveCrowdListInfo() throws IOException{
		int crowdId = getInt();//ȺID
		String crowdName = getString();
		String filename = getString();
		int fileLength = getInt();
		byte[] bytes = getFileBytes();
		String path = saveFile(filename, crowdId+"", bytes);
		
		//��ʾ����
		log.info("=============Ⱥ�б�==============");
		log.info("crowdId:" + crowdId);
		log.info("crowdName: " + crowdName);
		log.info("filename:" + filename);
		log.info("fileLength:" + fileLength);
		log.info("bytesLength:" + bytes.length);
		log.info("---------------------------------");
	}
	
	/**
	 * ���յ�����Ⱥ����
	 * @throws IOException
	 */
	public void receiveToCrowd() throws IOException{
		String intiveId = getString();
		String intivedId = getString();
		String time = getString();
		int crowdId = getInt();
		log.info("=========���յ�" + intiveId + "�Ľ�Ⱥ����");
		log.info("ʱ��:" + time);
		log.info("crowdId:" + crowdId);
		log.info("�Ƿ�ͬ���Ⱥ(1303---1304)");
		//clientSend.acceptToCrowd(intiveId, intivedId, crowdId, time);
	}
	
	/**
	 * ���û��������ĳȺ
	 * @throws IOException
	 */
	public void applyToCrowd() throws IOException{
		String applyId = getString();
		int crowdId = getInt();
		String time = getString();
		log.info("=======�����Ⱥ=======");
		log.info("time:" + time);
		log.info("userId:" + applyId);
		log.info("crowdId:" + crowdId);
		log.info("�Ƿ�����Ⱥ(1306---1307)");
		//log.info(">>>>>>>>>>���� ClientSend.applyToCrowd()");
	}
	//�����ռӽ�Ⱥ
	public void agreeToCrowd() throws IOException{
		int corwdId = getInt();
		log.info("ͬ���Ⱥ" + corwdId);
	}
	//���ܾ�
	public void noAgreeToCrowd() throws IOException{
		int corwdId = getInt();
		log.info("���ܾ�����Ⱥ" + getInt());
	}
	public void crowdFull() throws IOException{
		int crowdId = getInt();
		log.info(crowdId+ "Ⱥ����");
	}
	
	/****************************************��ע***************************************************/
	
	
	/**
	 * ��ע�ҵ�
	 * @throws IOException 
	 */
	public void attentMe() throws IOException{
		int length = getInt();
		log.info("---------------��ע�ҵ�--------------");
		for(int i=0;i<length;i++){
			log.info("user--" + i + ":" + getString());
		}
		log.info("-----------------end----------------");
	}
	
	/**
	 * �ҹ�ע��
	 * @throws IOException 
	 */
	public void myAttent() throws IOException{
		int length = getInt();
		log.info("---------------�ҹ�ע��--------------");
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
	 * �����ļ���Ϣ
	 * 
	 * @param filename
	 * @param userId
	 * @return filepath
	 * @throws IOException
	 */
	// TODO �����ļ���Ϣ
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
