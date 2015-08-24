package com.atm.chat.nio.server.handler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.atm.chat.nio.server.util.Config;
import com.atm.model.chat.CrowdChat;
import com.atm.model.define.chat.CrowdInfo;
import com.atm.model.define.chat.CrowdList;
import com.atm.model.define.chat.CrowdListInfo;
import com.atm.model.define.chat.MenberList;
import com.atm.service.crowd.CrowdMenberService;
import com.atm.service.crowd.CrowdService;
import com.atm.util.FileUtil;
import com.atm.util.TimeUtil;

public class CrowdHandler extends MenberHandler {
	CrowdService crowdService = new CrowdService();
	CrowdMenberService menberService = new CrowdMenberService();
	private Object count;

	public CrowdHandler() {
	}

	public static void main(String[] args) {
		String filename = "a.jpg";
		String[] strings = filename.split("\\.");
		System.out.println(strings[0] + "   " + strings[1]);
	}

	public void operate(int type) throws Exception {
		switch (type) {
		case Config.CROWD_CREATE:// ����Ⱥ��
			createCrowd();
			break;
		case Config.CROWD_APPLY:// �����Ⱥ
			applyToCrowd();
			break;
		case Config.CROWD_RESULT_APPLY:// ����Ա�ظ��Ĺ�����
			applyResult();
			break;
		case Config.CROWD_INTIVE:// �����Ⱥ
			intiveToCrowd();
			break;
		case Config.CROWD_FOUND:// ���Ҹ���Ȥ��Ⱥ��������Ⱥ
			foundCrowd();
			break;
		case Config.CROWD_FIND:// ����Ⱥ
			findCrowd();
			break;
		case Config.CROWD_MY:// �ҵ�Ⱥ
			findCrowdByUserId();
			break;
		case Config.CROWD_GET_INFO:// ��ȡȺ����Ϣ
			findCrowdMenber();
			break;
		default:
			break;
		}
	}

	/**
	 * ����ĳһ���˵�Ⱥ
	 * 
	 * @throws Exception
	 */
	public void findCrowdByUserId() throws Exception {
		String userId = getString();
		log.info("userId:" + userId);
		// TODO
		log.info("-------�����û�����������Ⱥ-------------");
		List<CrowdList> crowdLists = crowdService.findUsersCrowd(userId,0,10);
		log.info("-------���Ͷ�Ӧ��Ⱥ�б�����---------");
		sendCrowdList(crowdLists,Config.CROWD_RESULT_MY);
	}

	/**
	 * ����Ⱥ
	 * 
	 * @throws Exception
	 */
	public void findCrowd() throws Exception {
		String userId = getString();
		String content = getString();
		// int max = getInt();
		// TODO����Ⱥ
		log.info(userId + "�������-" + content + "-��ص�Ⱥ");
		List<CrowdInfo> crowdInfos = crowdService.fingCrowdByNameOrLabel(
				content, 0, 20);
		log.info("===��crowdInfosת����crowdList");
		List<CrowdList> crowdLists = makeCrowdInfoToCrowdList(crowdInfos);
		sendCrowdList(crowdLists,Config.CROWD_RESULT_FIND);
	}

	/**
	 * ��CrowdInfo��ת����CrowdList
	 * @param crowdInfos
	 * @return
	 */
	public List<CrowdList> makeCrowdInfoToCrowdList(List<CrowdInfo> crowdInfos) {
		CrowdList crowdList = new CrowdList();
		List<CrowdList> crowdLists = new ArrayList<CrowdList>();
		for (int i = 0; i < crowdInfos.size(); i++) {
			CrowdInfo chat = (CrowdInfo) crowdInfos.get(i);
			crowdList = new CrowdList(chat.getCrowdId(),
					chat.getCrowdHeadImage(), chat.getCrowdName(),
					chat.getNumLimit(), chat.getCrowdNum());
			crowdLists.add(crowdList);
		}
		return crowdLists;
	}

	/**
	 * ���ҿ��ܸ���Ȥ��Ⱥ����������Ⱥ
	 * 
	 * @throws Exception
	 * 
	 */
	public void foundCrowd() throws Exception {
		log.info("---����Ⱥ���ܸ���Ȥ�Ļ�������Ⱥ---");
		String userId = getString();
		//int first = getInt();
		//int max = getInt();
		int number = (int) (Math.random() * 10);
		List<CrowdList> crowdLists;// Ⱥ�б���б�
		if (number > 5) {
			log.info("--------���ܸ���Ȥ");
			crowdLists = crowdService.foundInterestingCrowd(userId, 0, 10);
		} else {
			log.info("--------����");
			crowdLists = crowdService.foundHotCrowd(0, 10);
		}
		sendCrowdList(crowdLists,Config.CROWD_RESULT_FOUND);
	}

	/**
	 * ����Ⱥ�б����Ϣ��
	 * @param crowdLists
	 * @throws IOException
	 */
	private void sendCrowdList(List<CrowdList> crowdLists,int type) throws IOException {
		if (crowdLists.size() == 0) {
			log.info("û���ҵ�");
			buffer = ByteBuffer.allocateDirect(8);
			log.info("buffer������:" + buffer.limit());
			buffer.putInt(type);
			buffer.putInt(Config.NOT_FOUND);
			writeBuffer();
			return;
		}	
		int count = crowdLists.size();// �б����Ŀ
		log.info("count:" + count);
		CrowdList crowdList = null;
		//����
		String[] limitStr = new String[count];
		//ͷ���ļ���
		String[] filenames = new String[count];
		int length = 0;// buffer�ĳ���
		byte[][] images = new byte[count][];
		for (int i = 0; i < count; i++) {
			crowdList = crowdLists.get(i);
			limitStr[i] = crowdList.getCrowdNum() + "/"
					+ crowdList.getNumLimit();
			filenames[i] =
			FileUtil.getSmallCrowdHead(crowdList.getCrowdHeadImage());
			//filenames[i] = new File(crowdList.getCrowdHeadImage()).getName();
			log.info("-----filename:" + i + ":" + filenames[i]);
			log.info("-----filenamepath:" + crowdList.getCrowdHeadImage());
			 images[i] = FileUtil.makeFileToByte(crowdList.getCrowdHeadImage()
			 + "/" + filenames[i]);
			//images[i] = FileUtil.makeFileToByte(crowdList.getCrowdHeadImage());
			log.info("-----images[i]:" + images[i].length);
			length += 16 
					+ crowdList.getCrowdName().getBytes().length
			// + filenames[i].getBytes().length
					+ limitStr[i].getBytes().length 
					+ images[i].length;
		}
		log.info("��ʼ����Ⱥ�б����Ϣ");
		//��ʼ����ͼ��
		buffer = ByteBuffer.allocateDirect(length + 12);
		log.info("buffer������:" + buffer.limit());
		log.info("buffer������:" + (length + 12));
		buffer.putInt(type);
		buffer.putInt(Config.SUCCESS);
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			log.info("crowdId" + i + ":" + crowdLists.get(i).getCrowdId());
			buffer.putInt(crowdLists.get(i).getCrowdId());
			put(crowdLists.get(i).getCrowdName());
			put(limitStr[i]);
			// put(filenames[i]);
			buffer.putInt(images[i].length);
			buffer.put(images[i]);
		}
		log.info("position:" + buffer.position());
		writeBuffer();
		log.info("-------�������----");
	}

	/**
	 * ����Ⱥ��
	 * 
	 * @throws Exception
	 */
	public void createCrowd() throws Exception {
		/*************** ��ȡ��Ӧ��Ϣ *************/
		String userId = getString();// Ⱥ��Id
		String crowdName = getString();// Ⱥ����
		String describe = getString();// Ⱥ����
		String crowdLabel = getString();// Ⱥlabel
		int isHidden = getInt();// �Ƿ�����
		String filename = getString();// ͷ����
		log.info("...................filename:" + filename);
		int length = getInt();
		byte[] headByte = FileUtil.getFileBytes(socketChannel, length);// ��ȡͷ���ļ��ֽ�����
		String time = TimeUtil.getFileTimeFormat();
		log.info("length:" + length);
		log.info("bytes.size:" + headByte.length);

		log.info("time:" + time);
		String[] strings = filename.split("\\.");
		String extension = strings[1];
		log.info("===============����Ⱥ��================");
		log.info("userId:" + userId);
		log.info("crowdName:" + crowdName);
		log.info("describe:" + describe);
		log.info("crowdLabel:" + crowdLabel);
		log.info("isHidden:" + isHidden);
		log.info("filename" + filename);
		log.info("========================================");

		// ��װ��һ��CrowdChat
		CrowdChat chat = new CrowdChat();
		chat.setCrowdDescription(describe);
		// chat.setCrowdHeadImage(headImagePath);
		chat.setCrowdLabel(crowdLabel);
		chat.setCrowdName(crowdName);
		if (isHidden == 1) {
			chat.setIsHidden(false);
		} else if (isHidden == 0) {
			chat.setIsHidden(true);
		}
		chat.setNumLimit(200);
		chat.setVerifyMode("1");

		// ����Ⱥ�ģ��ɹ�����crowdId ʧ�ܷ���-100
		// crowdListInfo crowdListInfo = CrowdService.saveCrowdChat(userId,
		// chat);
		int crowdId = crowdService.saveCrowdChat(userId, chat);
		// ����ͷ�񵽷������У����Żش��·��
		new ImageHandler(extension, headByte, 1, crowdId + "").start();
		;
		// ���ʹ���Ⱥ����Ϣ
		sendCrowdResultCreate(crowdId);
	}

	/**
	 * ���ʹ���Ⱥ�Ľ��
	 * 
	 * @param crowdId
	 */
	public void sendCrowdResultCreate(int crowdId) {
		if (crowdId != -100) { // �����ɹ�
			buffer = ByteBuffer.allocateDirect(12);
			buffer.putInt(Config.CROWD_RESULT_CREATE);
			buffer.putInt(Config.SUCCESS);
			buffer.putInt(crowdId);
			// sendCrowdListInfo(crowdListInfo);// �����µ�Ⱥ�б�
		} else { // ����ʧ��
			buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(Config.CROWD_RESULT_CREATE);
			buffer.putInt(Config.FAILED);
		}
		writeBuffer();
	}

	/**
	 * ����Ⱥ���б����Ϣ
	 * 
	 * @throws IOException
	 */
	public void sendCrowdListInfo(CrowdListInfo crowdListInfo)
			throws IOException {
		byte[] bytes = FileUtil.makeFileToByte(crowdListInfo
				.getCrowdHeadImage());
		String filename = new File(crowdListInfo.getCrowdHeadImage()).getName();
		buffer = ByteBuffer.allocateDirect(20
				+ crowdListInfo.getCrowdName().getBytes().length
				+ filename.getBytes().length + bytes.length);

		buffer.putInt(Config.CROWD_LIST);// Ⱥ�б�����
		buffer.putInt(crowdListInfo.getCrowdId());// ȺID
		put(crowdListInfo.getCrowdName());// Ⱥ����
		put(filename);// �ļ���
		buffer.putInt(bytes.length);// �ļ���С
		buffer.put(bytes);// �ļ��ֽ�����

		writeBuffer();
	}

	/**
	 * ��ȡȺ����Ϣ
	 * 
	 * @throws IOException
	 */
	// TODO ��ȡȺ����Ϣ
	public void getCrowdInfo() throws IOException {
		int crowdId = getInt();
		CrowdInfo crowdInfo = crowdService.getCrowdInfo(crowdId);
		if (crowdInfo != null) {// Ⱥ���ڣ����͸�Ⱥ�ĵ���Ϣ
			sendCrowdInfo(crowdInfo);
		}
	}

	/**
	 * ����Ⱥ����Ϣ
	 * 
	 * @param crowdInfo
	 * @throws IOException
	 */
	public void sendCrowdInfo(CrowdInfo crowdInfo) throws IOException {
		String filename = new File(crowdInfo.getCrowdHeadImage()).getName();
		byte[] headImage = FileUtil.makeFileToByte(crowdInfo
				.getCrowdHeadImage());
		buffer = ByteBuffer.allocateDirect(4
				+ crowdInfo.getCrowdName().getBytes().length
				+ crowdInfo.getCrowdDescription().getBytes().length
				+ crowdInfo.getCrowdLabel().getBytes().length
				+ filename.getBytes().length + headImage.length);
		buffer.putInt(Config.CROWD_RESULT_GETINFO);
		buffer.putInt(crowdInfo.getCrowdId());
		put(crowdInfo.getCrowdName());
		put(crowdInfo.getCrowdDescription());
		put(crowdInfo.getCrowdLabel());
		buffer.putInt(crowdInfo.getCrowdNum());
		put(filename);
		buffer.putInt(headImage.length);
		buffer.put(headImage);
		writeBuffer();
	}

	/**
	 * ����Ⱥ��Ա
	 * 
	 * @throws IOException
	 */
	public void findCrowdMenber() throws IOException {
		// TODO ����Ⱥ��Ա
		int crowdId = getInt();// Ⱥ���
		log.info("Ⱥ" + crowdId + "��Ⱥ��ԱID");
		try {
			List<MenberList> menbers = menberService
					.findAllMenber(crowdId);
			sendMenberList(menbers, crowdId);
		} catch (Exception e) {
			log.info("===========����ʧ��==========");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.CROWD_RESULT_GETINFO);
			buffer.putInt(Config.FAILED);
			// buffer.putInt(crowdId);
			writeBuffer();
		}
	}

	/**
	 * ����Ⱥ���ϵ�Ⱥ��Ա
	 * 
	 * @param menberLists
	 *            menberLists���б���Ϣ
	 * @param crowdId
	 *            ȺId
	 * @throws IOException
	 */
	public void sendMenberList(List<MenberList> menberLists, int crowdId)
			throws IOException {
		// �ж��Ƿ��ҵ�Ⱥ��Ա
		if (menberLists == null || menberLists.size() == 0) {
			log.info("===========û��������==========");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.CROWD_RESULT_GETINFO);
			buffer.putInt(Config.NOT_FOUND);
			// buffer.putInt(crowdId);
			writeBuffer();
			return;
		}
		int count = menberLists.size();
		log.info("�����ɹ�,Ⱥ����Ϊ:" + count);
		/**
		 * ���ó���
		 */
		MenberList menberList = null;// ���ÿ��MenberList
		byte[][] images = new byte[count][];
		int length = 12;
		for (int i = 0; i < count; i++) {
			menberList = menberLists.get(i);
			images[i] = FileUtil.makeFileToByte(menberList.getHeadImagePath());
			length += 16
					+ menberList.getUserId().getBytes().length // userID�ֽ�����ĳ���
					+ menberList.getNickname().getBytes().length
					+ images[i].length;
		}

		log.info("buffer�Ĵ�С :" + length);
		// �趨buffer��С
		buffer = ByteBuffer.allocateDirect(length);
		buffer.putInt(Config.CROWD_RESULT_GETINFO);// Ⱥ���Ͻ��
		buffer.putInt(Config.SUCCESS);// �ɹ�
		// buffer.putInt(crowdId);//ȺID
		log.info("=========��ʼ����============");
		buffer.putInt(count);// Ⱥ��Ա����
		for (int i = 0; i < count; i++) {
			menberList = menberLists.get(i);
			put(menberList.getUserId());// userID
			put(menberList.getNickname());// �ǳ�
			buffer.putInt(menberList.getRoleId());// ��ɫ
			buffer.putInt(images[i].length);// ͼƬ����
			buffer.put(images[i]);// ͼƬ
		}
		log.info("=========����============");
		writeBuffer();
		log.info("============���ͳɹ�==========");
	}

	/**
	 * �����Ⱥ �ж�Ⱥ�Ƿ��Ѿ����������֪������; ���������û������ߣ�������ݿ⣬�����ߣ�����
	 * 
	 * @throws Exception
	 */
	public void intiveToCrowd() throws Exception {
		String intiveID = getString();
		String intivedID = getString();
		int crowdID = getInt();
		log.info("intiveId:" + intiveID + " intivedID:" + intivedID
				+ " crowdID:" + crowdID);
		// 1. �ж�Ⱥ�Ƿ��Ѿ����ˡ����˸�֪������Ⱥ������
		int isfull = menberService.isFull(crowdID);
		log.info("--------�ж�--------");
		if (isfull == 1) {
			// 1.1���ˣ�֪ͨ����������
			log.info("Ⱥ����");
			sendCrowdFull(crowdID, Config.CROWD_RESULT_INTIVE);
		} else if (isfull == 0) {
			// 1.2 δ���ˡ��ж��û��Ƿ����߲����߼��������ݿ⡣���߼�����
			SocketChannel channel = map.get(intivedID);
			if (channel == null) {
				log.info("�û�������");
				// 1.2.1 �û�������,�������ݿ�
				menberService.intiveToCrowd(intiveID, intivedID, crowdID);
			} else {
				log.info("==============�û�����");
				// 1.2.2 �û����ߣ���������
				String time = TimeUtil.getCurrentDateFormat();
				sendIntive(intiveID, intivedID, time, crowdID, channel);
			}
		} else {
			log.info("Ⱥ������");
		}
	}

	/**
	 * ��֪��ǰȺ������
	 */
	public void sendCrowdFull(int crowdId, int type) {
		buffer = ByteBuffer.allocateDirect(12);
		buffer.putInt(type);
		buffer.putInt(Config.CROWD_FULL);
		buffer.putInt(crowdId);
		writeBuffer();
	}

	/**
	 * ��֪Ⱥ������
	 */
	public void sendCrowdFull(int crowdId, int type, SocketChannel channel) {
		buffer = ByteBuffer.allocateDirect(12);
		buffer.putInt(type);
		buffer.putInt(Config.CROWD_FULL);
		buffer.putInt(crowdId);
		writeBuffer(channel);
	}

	/**
	 * ���������Ⱥ��Ϣ
	 * 
	 * @param intiveID
	 *            ������
	 * @param intivedID
	 *            ������
	 * @param time
	 *            ʱ��
	 * @param crowdID
	 *            Ⱥ���
	 * @param channel
	 *            ������socketChannel
	 * @throws UnsupportedEncodingException 
	 */
	public void sendIntive(String intiveID, String intivedID, String time,
			int crowdID, SocketChannel channel) throws UnsupportedEncodingException {
		// 1. �ж�Ⱥ�Ƿ��Ѿ����ˡ����˸�֪������Ⱥ������
		int i = menberService.isFull(crowdID);
		if (i == 1) {
			// 1.1���ˣ�֪ͨ����������
			sendCrowdFull(crowdID, Config.CROWD_RESULT_INTIVE);
		} else if (i == 0) {
			buffer = ByteBuffer.allocateDirect(20 + intiveID.getBytes().length
					+ intivedID.getBytes().length + time.getBytes().length);
			buffer.putInt(Config.CROWD_INTIVE);
			put(intiveID);// ������Id
			put(intivedID);// ������Id
			put(time);// ʱ��
			buffer.putInt(crowdID);
			writeBuffer(channel);
		} else {
			log.info("Ⱥ������");
		}
	}

	/**
	 * �����Ⱥ
	 * 
	 * @throws Exception
	 */
	public void applyToCrowd() throws Exception {
		String applyId = getString();
		int crowdId = getInt();
		String userId = getString();
		log.info("=====" + applyId + "�����ȥȺ===========");
		log.info("crowdId:" + crowdId);
		log.info("�����ˣ�" + userId);
		// 1.�ж�Ⱥ�Ƿ�����
		int isfull = menberService.isFull(crowdId);
		if (isfull == 1) {
			// 1.1 ���ˣ���֪����������
			sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
		} else if (isfull == 0 && menberService.isManager(userId, crowdId)) {
			// ������Ϊ����Աֱ�Ӽ���Ⱥ
			menberService.saveCrowdMenber(applyId, crowdId, 3);
			buffer = ByteBuffer.allocateDirect(12);
			buffer.putInt(Config.CROWD_RESULT_APPLY);
			buffer.putInt(Config.SUCCESS);
			buffer.putInt(crowdId);
			writeBuffer();
		} else if (isfull == 0) {
			log.info("============Ⱥδ��============");
			// 1.2 ���ҳ���Ⱥ�Ĺ���Ա��
			List<String> managers = menberService.getCrowdManagers(crowdId);
			// ����Ա�ܵ�
			SocketChannel channel = null;
			menberService.applyToCrowd(applyId, crowdId);
			// 1.2.1 ��������Ա��Ⱥ��������Ϣ
			for (int i = 0; i < managers.size(); i++) {
				channel = map.get(managers.get(i));
				if (channel == null) {
					log.info(managers.get(i) + "������");
				} else {
					// 1.2.1.2Ⱥ��Ա���ߣ�����������Ϣ
					// ��ȡ��ǰʱ��
					String time = TimeUtil.getCurrentDateFormat();
					sendApply(applyId, crowdId, time, channel);
				}
			}
		} else {
			log.info("Ⱥ������");
		}
	}

	/**
	 * ��������
	 * 
	 * @param applyId
	 *            ������
	 * @param crowdId
	 *            Ⱥ���
	 * @param time
	 *            ʱ��
	 * @param channel
	 *            ����Ա�ܵ�
	 * @throws UnsupportedEncodingException 
	 */
	public void sendApply(String applyId, int crowdId, String time,
			SocketChannel channel) throws UnsupportedEncodingException {
		// 1.�ж�Ⱥ�Ƿ�����
		int isfull = menberService.isFull(crowdId);
		if (isfull == 1) {
			log.info("qȺ����");
			// 1.1 ���ˣ���֪����������
			sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
			writeBuffer();
		} else if (isfull == 0) {
			log.info("============snedApply======Ⱥδ��===========");
			buffer = ByteBuffer.allocateDirect(54);
			buffer.putInt(Config.CROWD_APPLY);
			put(applyId);
			buffer.putInt(crowdId);
			put(time);
			writeBuffer(channel);
		} else {
			log.info("Ⱥ������");
		}
	}

	/**
	 * �������Ⱥ�ĵĽ������
	 * 
	 * @throws Exception
	 */
	public void applyResult() throws Exception {
		int type = getInt();
		String userId = getString();
		String applyId = getString();
		int crowdId = getInt();
		if (type == Config.SUCCESS) {
			log.info(userId + "ͬ��" + applyId + "����" + crowdId + "Ⱥ��");
			agreeToCrowd(applyId, crowdId);
		} else {
			log.info(userId + "�ܾ�" + applyId + "����" + crowdId + "Ⱥ��");
			sendApplyResult(applyId, crowdId, Config.FAILED);
		}
	}

	/**
	 * ͬ����룬�ж��Ƿ��Ѿ���Ⱥ�С�Ⱥ�Ƿ��Ѿ�����
	 * 
	 * @throws IOException
	 * 
	 */
	private void agreeToCrowd(String applyId, int crowdId) throws IOException {
		// 1.�Ƿ��Ѿ���Ⱥ��
		if (menberService.isCrowder(applyId, crowdId)) {
			// 1.1 �Ѿ���Ⱥ��
			buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(Config.CROWD_USER_ALREADY);
			put(applyId);
			buffer.putInt(crowdId);
			writeBuffer();
		} else {
			// 1.2 ����Ⱥ�� ���ж�Ⱥ�Ƿ�����
			int isfull = menberService.isFull(crowdId);
			if (isfull == 1) {
				// 1.2.1 ���ˡ���֪����Ա�Լ�������
				// 1.2.1.1��֪����Ա
				sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
				// 1.2.1.2 ��֪������
				sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY,
						map.get(applyId));
			} else if (isfull == 0) {
				// 1.2.2 ������
				sendApplyResult(applyId, crowdId, Config.SUCCESS);
				menberService.saveCrowdMenber(applyId, crowdId, 3);
			} else {
				log.info("Ⱥ������");
				menberService.deleteApply(applyId, crowdId);
				return;
			}
		}
		// ɾ������
		menberService.deleteApply(applyId, crowdId);
	}

	/**
	 * ��������Ⱥ�Ļظ�
	 * 
	 * @param applyId
	 * @param crowdId
	 * @param type
	 */
	public void sendApplyResult(String applyId, int crowdId, int type) {
		buffer = ByteBuffer.allocateDirect(12);
		buffer.putInt(Config.CROWD_RESULT_APPLY);
		buffer.putInt(type);
		buffer.putInt(crowdId);
		writeBuffer(map.get(applyId));
	}
}