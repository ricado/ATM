package com.atm.chat.nio.server.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.util.Config;
import com.atm.model.define.user.UserBasicInfo;
import com.atm.model.define.user.UserList;
import com.atm.service.user.UserService;
import com.atm.util.FileUtil;

/**
 * �����й��û���������Ϣ��������ӣ�ȡ����ע����ȡ��ע�ˣ���˿�б��Լ��ʱ���ϸ����
 * 
 * @author ye
 * @time 2015.8.17
 */
public class UserHandler extends BufferHandler {
	private static final Logger log = LoggerFactory
			.getLogger(UserHandler.class);
	UserService userService = new UserService();

	public UserHandler() {
	}

	/**
	 * ��������תվ
	 * 
	 * @param type
	 * @throws Exception
	 */
	public void operate(int type) throws Exception {
		switch (type) {
		case Config.USER_ADD_ATTENT:// ��ӹ�ע
			addAttent();
			break;
		case Config.USER_GET_ATTENT:// ��ȡ�ҹ�ע��
			myAttented();
			break;
		case Config.USER_GET_ATTENTED:// ��ȡ�ҵ�
			attentMe();
			break;
		case Config.USER_OTHER_ATTENT:
			// TODO ���˹�ע���û�
			otherAttent();
			break;
		case Config.USER_OTHER_FANS:
			// TODO ���˵ķ�˿
			otherFans();
			break;
		case Config.USER_CANCEL_ATTENT:// ȡ����ע
			cancelAttent();
			break;
		case Config.USER_FIND: // Ѱ����keyWordƥ����û�
			findUser();
			break;
		case Config.USER_FOUND:// ���ҿ��ܸ���Ȥ���û�
			findInterestingUser();
			break;
		case Config.USER_LIST: // Ѱ�ҿ��ܸ���Ȥ����
			findInterestingUser();
			break;
		case Config.USER_GET_INFO:// ��ȡ�û��Ļ�������
			findUserBasicInfo();
			break;
		case Config.USER_GET_HEAD:// ��ȡ�û�ͷ��
			userHead();
			break;
		default:
			break;
		}
	}

	/**
	 * ���ݻ�ȡ����keyWord������ص��û���
	 * 
	 * @throws Exception
	 */
	public void findUser() throws Exception {
		String userId = getString();
		String keyWord = getString();
		try {
			log.info(userId + "����������" + keyWord + "���й��û�");
			List<UserList> userLists = userService.findUser(keyWord, 0, 20);
			sendUserList(userLists, Config.USER_RESULT_FIND);
		} catch (Exception e) {
			log.info("����ʧ��");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_FIND);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}
	}

	/**
	 * ���ҿ��ܸ���Ȥ���û�
	 * 
	 * @throws Exception
	 */
	public void findInterestingUser() throws Exception {
		String userId = getString();// ����userID
		try {
			log.info("���뵽���ҿ��ܸ���Ȥ���û�");
			List<UserList> userLists = userService.findInterestingUser(userId,
					0, 10);
			sendUserList(userLists, Config.USER_RESULT_FIND);
		} catch (Exception e) {
			e.printStackTrace();
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_LIST);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}
	}

	/**
	 * �����û��б�
	 * 
	 * @param userLists
	 *            �û��б�
	 * @param type
	 *            ���͵Ľ������
	 * @throws IOException
	 */
	public void sendUserList(List<UserList> userLists, int type)
			throws IOException {
		int count = userLists.size();
		log.info("count:" + count);
		// �ж�userLists�Ƿ�Ϊ��.Ϊ����Ϊû���ҵ�
		if (userLists.size() == 0) {
			log.info("û���ҵ�");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(type);
			buffer.putInt(Config.NOT_FOUND);
			writeBuffer();
			return;
		}
		log.info("�ҵ���¼-------");
		int length = 0;// buffer ������
		byte[][] images = new byte[count][];
		try {
			for (int i = 0; i < count; i++) {
				UserList userList = userLists.get(i);
				log.info("i:" + i);
				String path = this.getClass().getResource("/").getPath()
						.substring(1).replaceFirst("WEB-INF/classes/", "");
				log.info(path);
				// ��ȡ�û�ͷ������ͼ��ͷ��
				images[i] = FileUtil.makeFileToByte(path
						+ userList.getHeadImagePath());
				log.info("iamges" + i + ":" + images[i].length);
				length += 24 + userList.getUserId().getBytes().length
						+ userList.getNickname().getBytes().length
						+ userList.getdName().getBytes().length
						+ userList.getSex().getBytes().length
						+ images[i].length;
				log.info("length" + i + ":" + length);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		length += 12;
		log.info("��ʼ�����û��б�");
		buffer = ByteBuffer.allocateDirect(length);
		log.info("buffer��������" + length);
		buffer.putInt(type);
		buffer.putInt(Config.SUCCESS);
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			UserList userList = userLists.get(i);
			put(userList.getUserId());
			put(userList.getNickname());
			put(userList.getdName());
			put(userList.getSex());
			buffer.putInt(userList.getFlag());
			buffer.putInt(images[i].length);
			buffer.put(images[i]);
		}
		writeBuffer();
		log.info("-----------���ͽ���-------------");
	}

	/**
	 * �����û��б�
	 * 
	 * @param userLists
	 *            �û��б�
	 * @param type
	 *            ���͵Ľ������
	 * @throws IOException
	 */
	public void sendUserList(List<UserList> userLists, int type, String userId,
			String otherUserId) throws IOException {
		int count = userLists.size();
		log.info("count:" + count);
		// �ж�userLists�Ƿ�Ϊ��.Ϊ����Ϊû���ҵ�
		if (userLists.size() == 0) {
			log.info("û���ҵ�");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(type);
			buffer.putInt(Config.NOT_FOUND);
			writeBuffer();
			return;
		}
		log.info("�ҵ���¼-------");
		int length = 0;// buffer ������
		byte[][] images = new byte[count][];
		try {
			for (int i = 0; i < count; i++) {
				UserList userList = userLists.get(i);
				log.info("i:" + i);
				String path = this.getClass().getResource("/").getPath()
						.substring(1).replaceFirst("WEB-INF/classes/", "");
				log.info(path);
				// ��ȡ�û�ͷ������ͼ��ͷ��
				images[i] = FileUtil.makeFileToByte(path
						+ userList.getHeadImagePath());
				log.info("iamges" + i + ":" + images[i].length);
				length += 24
						// ��ǰ�û���id
						+ userList.getUserId().getBytes().length
						+ userList.getNickname().getBytes().length
						+ userList.getdName().getBytes().length
						+ userList.getSex().getBytes().length
						+ images[i].length;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		length += userId.getBytes().length + 4
		// ��ǰother�û���id
				+ otherUserId.getBytes().length + 4 + 12;
		log.info("��ʼ�����û��б�");
		buffer = ByteBuffer.allocateDirect(length);
		log.info("buffer��������" + length);
		buffer.putInt(type);
		buffer.putInt(Config.SUCCESS);
		// ��ǰ�û�
		put(userId);
		// ��ǰother�û���id
		put(otherUserId);
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			UserList userList = userLists.get(i);
			put(userList.getUserId());
			put(userList.getNickname());
			put(userList.getdName());
			put(userList.getSex());
			// �Ƿ��й�ע
			buffer.putInt(userList.getFlag());
			buffer.putInt(images[i].length);
			buffer.put(images[i]);
		}
		writeBuffer();
		log.info("-----------���ͽ���-------------");
	}

	/****************************** ��ע ***********************/
	/**
	 * ��ӹ�ע
	 * 
	 * @throws Exception
	 */
	public void addAttent() throws Exception {
		log.info("-----------��ӹ�ע");
		String userAttentId = getString();
		String userAttentedId = getString();
		try {
			log.info(userAttentId + " attend " + userAttentedId);
			userService.addAttent(userAttentId, userAttentedId);
			log.info("��ӳɹ�");
			sendResultAttent(Config.USER_RESULT_ADDATTENT, Config.SUCCESS);
		} catch (Exception e) {
			log.info("��ӹ�עʧ��");
			sendResultAttent(Config.USER_RESULT_ADDATTENT, Config.FAILED);
		}
	}

	/**
	 * �õ����еĹ�ע�ҵ���
	 * 
	 * @throws Exception
	 */
	public void attentMe() throws Exception {
		log.info("-----------��ע�ҵ�");
		String userAttentedId = getString();
		try {
			List<UserList> userLists = userService
					.findAttentedMe(userAttentedId);
			sendUserList(userLists, Config.USER_GET_ATTENTED);
		} catch (Exception e) {
			log.info("ʧ��");
		}
	}

	/**
	 * �õ������ҹ�ע��
	 * 
	 * @throws Exception
	 */
	public void myAttented() throws Exception {
		log.info("-----------�ҹ�ע��");
		String userAttentId = getString();
		try {
			List<UserList> userLists = userService.findMyAttent(userAttentId);
			sendUserList(userLists, Config.USER_RESULT_GETATTENT);
		} catch (Exception e) {
			log.info("ʧ��");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_GETATTENT);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}

	}

	/**
	 * ȡ����ע
	 * 
	 * @throws Exception
	 */
	public void cancelAttent() throws Exception {
		log.info("---------ȡ����ע------------");
		String userId = getString();// ��ע��
		String attentedId = getString();// ����ע��
		try {
			userService.cancelAttent(userId, attentedId);
			log.info("ȡ����ע�ɹ�");
			sendResultAttent(Config.USER_RESULT_CANCELATTENT, Config.SUCCESS);
		} catch (Exception e) {
			log.info("ȡ����עʧ��");
			sendResultAttent(Config.USER_RESULT_CANCELATTENT, Config.FAILED);
		}
	}

	/**
	 * ��ȡ�����û���ע���б�
	 * 
	 * @throws Exception
	 */
	public void otherAttent() throws Exception {
		log.info("--------��ȡ�����û���ע���б�----------");
		// ��ǰ�û�
		String userId = getString();
		// �����û�
		String otherUserId = getString();
		try {
			List<UserList> userLists = userService.findOtherAttented(
					otherUserId, userId);
			log.info("��ȡ�����û��Ĺ�ע�б�");
			sendUserList(userLists, Config.USER_RESULT_OATTENT, userId,
					otherUserId);
		} catch (Exception e) {
			log.info("ʧ��");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_OATTENT);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}
	}

	/**
	 * ��ȡ�����û��ķ�˿�б�
	 * 
	 * @throws Exception
	 */
	public void otherFans() throws Exception {
		log.info("-------��ȡ�����û��ķ�˿�б�---------");
		// ��ǰ�û�
		String userId = getString();
		// �����û�
		String otherUserId = getString();
		try {
			List<UserList> userLists = userService.findOtherFans(otherUserId,
					userId);
			log.info("��ȡ�����û��Ĺ�ע�б�");
			sendUserList(userLists, Config.USER_RESULT_OFANS, userId,
					otherUserId);
		} catch (Exception e) {
			log.info("ʧ��");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_OFANS);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}
	}

	public void sendResultAttent(int type, int result) {
		buffer = ByteBuffer.allocateDirect(8);
		buffer.putInt(type);
		buffer.putInt(result);
		writeBuffer();
	}

	/**
	 * ��ȡ�û��Ļ���������Ϣ
	 * 
	 * @throws Exception
	 */
	public void findUserBasicInfo() throws Exception {
		String userId = getString();
		String otherUserId = getString();
		int relationShip = 0;
		log.info("====" + userId + "��ȡ" + otherUserId + "�Ļ�������======");
		try {
			UserBasicInfo basicInfo = userService.getUserBasicInfo(otherUserId);
			relationShip = userService.getRelationShip(userId, otherUserId);
			if (basicInfo != null) {
				sendUserBasicInfo(basicInfo, relationShip);
				return;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("��ȡ��������ʧ��");
		}
		buffer = ByteBuffer.allocateDirect(8);
		buffer.putInt(Config.USER_RESULT_GETINFO);
		buffer.putInt(Config.FAILED);
		writeBuffer();
	}

	/**
	 * ��ȡ�û�ͷ��Ķ�ȡ����,�����û���������userId,Ѱ��userId��ͷ��
	 * 
	 * @throws Exception
	 */
	public void userHead() throws Exception {
		log.info("��ȡ�û�ͷ��");
		// ��ȡ�û�userId
		String userId = getString();
		try {
			// ��ȡ�û�ͷ���·��
			String path = userService.getUserInfo(userId).getHeadImagePath();
			// ��ȡ��Ŀ·��
			String basicPath = this.getClass().getResource("/").getPath()
					.substring(1).replaceFirst("WEB-INF/classes/", "");
			log.info("path:" + basicPath + path);
			// ��ȡ�û�ͷ����ֽ�����
			byte[] bs = FileUtil.makeFileToByte(basicPath + path);
			// ����
			buffer = ByteBuffer.allocateDirect(8 + bs.length + 4 + 4
					+ userId.getBytes().length);
			buffer.putInt(Config.USER_RESULT_GETHEAD);
			buffer.putInt(Config.SUCCESS);
			put(userId);
			buffer.putInt(bs.length);
			buffer.put(bs);
			writeBuffer();
		} catch (Exception e) {
			buffer.clear();
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_GETHEAD);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}

	}

	/**
	 * ��ȡ�û����ϳɹ��������û�����������Ϣ
	 * ����userId,nickname,sign,dname,scname,focusNum,funsNum,image
	 * 
	 * @param userBasicInfo
	 * @throws IOException
	 */
	public void sendUserBasicInfo(UserBasicInfo userBasicInfo, int relationship)
			throws IOException {
		// ��ע������
		String focusNum = userBasicInfo.getFocusNum() + "";
		// ��˿��
		String fansNum = userBasicInfo.getFansNum() + "";
		String path = this.getClass().getResource("/").getPath().substring(1)
				.replaceFirst("WEB-INF/classes/", "");
		log.info(path);
		// ��ȡ�û�ͷ����ֽ�����
		byte[] image = FileUtil.makeFileToByte(path
				+ userBasicInfo.getHeadImagePath());
		String publishNum = "24";
		String publishTitle = "[�й��ñ��]�����ֶغ���ʦΪ���˼������";
		String publishContent = "�ñ��ӭ�����,���ѧ�ӱ������������λ��ʦ...";
		String collectNum = "28";
		String collectTitle = "[צ������]�غ��ų����ԣ��ҵ�ѧ������bat";
		String collectContent = "���գ��غ���ʦ˵����ѧ����н������λ��,����˵���ڷ�ƨ...";

		int length = 8 + 4 // ��ϵ
				// id
				+ userBasicInfo.getUserId().getBytes().length + 4
				// �ǳ�
				+ userBasicInfo.getNickname().getBytes().length + 4
				// �Ա�
				+ userBasicInfo.getSex().getBytes().length + 4
				// focusNum
				+ 4 + focusNum.getBytes().length
				// fansNum
				+ 4 + fansNum.getBytes().length
				// ǩ��
				+ userBasicInfo.getSign().getBytes().length + 4
				// ѧУ
				+ userBasicInfo.getScName().getBytes().length + 4
				// ϵ��
				+ userBasicInfo.getDname().getBytes().length + 4
				// ��������
				+ publishNum.getBytes().length + 4
				// ��������
				+ publishTitle.getBytes().length + 4
				// ��������
				+ publishContent.getBytes().length + 4
				// �ղ�������
				+ collectNum.getBytes().length + 4
				// �ղ�������
				+ collectTitle.getBytes().length + 4
				// �ղ�������
				+ collectContent.getBytes().length + 4
				// ͷ��
				+ image.length + 4;

		// �趨buffer�ĳ���
		buffer = ByteBuffer.allocateDirect(length);

		// ����
		buffer.putInt(Config.USER_RESULT_GETINFO);
		// �ɹ�
		buffer.putInt(Config.SUCCESS);

		// 1 �û�id
		put(userBasicInfo.getUserId());
		// 2 �û��ǳ�
		put(userBasicInfo.getNickname());
		// 3 �Ա�
		put(userBasicInfo.getSex());
		// 4 �û���ע������
		put(focusNum + "");
		// 5 �û��ķ�˿
		put(fansNum + "");
		// 6 �û�ǩ��
		put(userBasicInfo.getSign());
		// 7 �û�ѧУ
		put(userBasicInfo.getScName());
		// 8 �û�ϵ��
		put(userBasicInfo.getDname());
		// �û���ϵ
		buffer.putInt(relationship);
		// 9��������
		put(publishNum);
		// 10��������
		put(publishTitle);
		// 11��������
		put(publishContent);
		// 12�ղ�������
		put(collectNum);
		// 13�ղ�������
		put(collectTitle);
		// 14�ղ�������
		put(collectContent);
		// 15 ͷ���ֽ�����Ĵ�С
		buffer.putInt(image.length);
		// 16 ͷ���ֽ�����
		buffer.put(image);
		// д���ܵ�

		writeBuffer();
	}

	/**
	 * �޸��û���������
	 */
	public void changeUserBasicInfo() {
		// TODO �޸ĸ�������
	}
}
