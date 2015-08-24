package com.atm.chat.nio.server.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

import com.atm.chat.nio.server.util.Config;
import com.atm.service.crowd.CrowdMenberService;
import com.atm.util.TimeUtil;

public class MenberHandler extends BufferHandler{
	
	private CrowdMenberService crowdMenberService = new CrowdMenberService();
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
		int isfull = crowdMenberService.isFull(crowdID);
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
				crowdMenberService.intiveToCrowd(intiveID, intivedID, crowdID);
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
		int i = crowdMenberService.isFull(crowdID);
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
		int isfull = crowdMenberService.isFull(crowdId);
		if (isfull == 1) {
			// 1.1 ���ˣ���֪����������
			sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
		} else if (isfull == 0 && crowdMenberService.isManager(userId, crowdId)) {
			// ������Ϊ����Աֱ�Ӽ���Ⱥ
			crowdMenberService.saveCrowdMenber(applyId, crowdId, 3);
			buffer = ByteBuffer.allocateDirect(12);
			buffer.putInt(Config.CROWD_RESULT_APPLY);
			buffer.putInt(Config.SUCCESS);
			buffer.putInt(crowdId);
			writeBuffer();
		} else if (isfull == 0) {
			log.info("============Ⱥδ��============");
			// 1.2 ���ҳ���Ⱥ�Ĺ���Ա��
			List<String> managers = crowdMenberService.getCrowdManagers(crowdId);
			// ����Ա�ܵ�
			SocketChannel channel = null;
			crowdMenberService.applyToCrowd(applyId, crowdId);
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
		int isfull = crowdMenberService.isFull(crowdId);
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
		if (crowdMenberService.isCrowder(applyId, crowdId)) {
			// 1.1 �Ѿ���Ⱥ��
			buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(Config.CROWD_USER_ALREADY);
			put(applyId);
			buffer.putInt(crowdId);
			writeBuffer();
		} else {
			// 1.2 ����Ⱥ�� ���ж�Ⱥ�Ƿ�����
			int isfull = crowdMenberService.isFull(crowdId);
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
				crowdMenberService.saveCrowdMenber(applyId, crowdId, 3);
			} else {
				log.info("Ⱥ������");
				crowdMenberService.deleteApply(applyId, crowdId);
				return;
			}
		}
		// ɾ������
		crowdMenberService.deleteApply(applyId, crowdId);
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
	
	/**
	 * ���ù���Ա
	 */
	public void setManager() throws Exception{
		String userId = getString();
		int crowdId = getInt();
		log.info("��" + userId + "���ó�ΪȺ" + crowdId + "�Ĺ���Ա");
	}
	
	/**
	 * ��������Ա
	 * @throws Exception
	 */
	public void cancelManager() throws Exception{
		String userId = getString();
		int crowdId = getInt();
		log.info("����" + userId + "ΪȺ" + crowdId + "�Ĺ���Ա");
	}
}
