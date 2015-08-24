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
	 * 邀请进群 判断群是否已经满，满则告知邀请人; 不满，若用户不在线，存进数据库，若在线，则发送
	 * 
	 * @throws Exception
	 */
	public void intiveToCrowd() throws Exception {
		String intiveID = getString();
		String intivedID = getString();
		int crowdID = getInt();
		log.info("intiveId:" + intiveID + " intivedID:" + intivedID
				+ " crowdID:" + crowdID);
		// 1. 判断群是否已经满人。满人告知邀请人群已满人
		int isfull = crowdMenberService.isFull(crowdID);
		log.info("--------判断--------");
		if (isfull == 1) {
			// 1.1满人，通知邀请人满人
			log.info("群满人");
			sendCrowdFull(crowdID, Config.CROWD_RESULT_INTIVE);
		} else if (isfull == 0) {
			// 1.2 未满人。判断用户是否在线不在线即存入数据库。在线即发送
			SocketChannel channel = map.get(intivedID);
			if (channel == null) {
				log.info("用户不在线");
				// 1.2.1 用户不再线,存入数据库
				crowdMenberService.intiveToCrowd(intiveID, intivedID, crowdID);
			} else {
				log.info("==============用户在线");
				// 1.2.2 用户在线，发送邀请
				String time = TimeUtil.getCurrentDateFormat();
				sendIntive(intiveID, intivedID, time, crowdID, channel);
			}
		} else {
			log.info("群不存在");
		}
	}

	/**
	 * 告知当前群聊已满
	 */
	public void sendCrowdFull(int crowdId, int type) {
		buffer = ByteBuffer.allocateDirect(12);
		buffer.putInt(type);
		buffer.putInt(Config.CROWD_FULL);
		buffer.putInt(crowdId);
		writeBuffer();
	}

	/**
	 * 告知群聊已满
	 */
	public void sendCrowdFull(int crowdId, int type, SocketChannel channel) {
		buffer = ByteBuffer.allocateDirect(12);
		buffer.putInt(type);
		buffer.putInt(Config.CROWD_FULL);
		buffer.putInt(crowdId);
		writeBuffer(channel);
	}

	/**
	 * 发送邀请进群消息
	 * 
	 * @param intiveID
	 *            邀请人
	 * @param intivedID
	 *            受邀人
	 * @param time
	 *            时间
	 * @param crowdID
	 *            群编号
	 * @param channel
	 *            受邀人socketChannel
	 * @throws UnsupportedEncodingException 
	 */
	public void sendIntive(String intiveID, String intivedID, String time,
			int crowdID, SocketChannel channel) throws UnsupportedEncodingException {
		// 1. 判断群是否已经满人。满人告知邀请人群已满人
		int i = crowdMenberService.isFull(crowdID);
		if (i == 1) {
			// 1.1满人，通知邀请人满人
			sendCrowdFull(crowdID, Config.CROWD_RESULT_INTIVE);
		} else if (i == 0) {
			buffer = ByteBuffer.allocateDirect(20 + intiveID.getBytes().length
					+ intivedID.getBytes().length + time.getBytes().length);
			buffer.putInt(Config.CROWD_INTIVE);
			put(intiveID);// 邀请人Id
			put(intivedID);// 受邀人Id
			put(time);// 时间
			buffer.putInt(crowdID);
			writeBuffer(channel);
		} else {
			log.info("群不存在");
		}
	}

	/**
	 * 申请进群
	 * 
	 * @throws Exception
	 */
	public void applyToCrowd() throws Exception {
		String applyId = getString();
		int crowdId = getInt();
		String userId = getString();
		log.info("=====" + applyId + "申请进去群===========");
		log.info("crowdId:" + crowdId);
		log.info("邀请人：" + userId);
		// 1.判断群是否满人
		int isfull = crowdMenberService.isFull(crowdId);
		if (isfull == 1) {
			// 1.1 满人，告知申请人已满
			sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
		} else if (isfull == 0 && crowdMenberService.isManager(userId, crowdId)) {
			// 邀请人为管理员直接加入群
			crowdMenberService.saveCrowdMenber(applyId, crowdId, 3);
			buffer = ByteBuffer.allocateDirect(12);
			buffer.putInt(Config.CROWD_RESULT_APPLY);
			buffer.putInt(Config.SUCCESS);
			buffer.putInt(crowdId);
			writeBuffer();
		} else if (isfull == 0) {
			log.info("============群未满============");
			// 1.2 查找出该群的管理员们
			List<String> managers = crowdMenberService.getCrowdManagers(crowdId);
			// 管理员管道
			SocketChannel channel = null;
			crowdMenberService.applyToCrowd(applyId, crowdId);
			// 1.2.1 遍历管理员，群发申请消息
			for (int i = 0; i < managers.size(); i++) {
				channel = map.get(managers.get(i));
				if (channel == null) {
					log.info(managers.get(i) + "不在线");
				} else {
					// 1.2.1.2群成员在线，发送申请消息
					// 获取当前时间
					String time = TimeUtil.getCurrentDateFormat();
					sendApply(applyId, crowdId, time, channel);
				}
			}
		} else {
			log.info("群不存在");
		}
	}

	/**
	 * 发送申请
	 * 
	 * @param applyId
	 *            申请人
	 * @param crowdId
	 *            群编号
	 * @param time
	 *            时间
	 * @param channel
	 *            管理员管道
	 * @throws UnsupportedEncodingException 
	 */
	public void sendApply(String applyId, int crowdId, String time,
			SocketChannel channel) throws UnsupportedEncodingException {
		// 1.判断群是否满人
		int isfull = crowdMenberService.isFull(crowdId);
		if (isfull == 1) {
			log.info("q群满人");
			// 1.1 满人，告知申请人已满
			sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
			writeBuffer();
		} else if (isfull == 0) {
			log.info("============snedApply======群未满===========");
			buffer = ByteBuffer.allocateDirect(54);
			buffer.putInt(Config.CROWD_APPLY);
			put(applyId);
			buffer.putInt(crowdId);
			put(time);
			writeBuffer(channel);
		} else {
			log.info("群不存在");
		}
	}

	/**
	 * 申请进入群聊的结果处理
	 * 
	 * @throws Exception
	 */
	public void applyResult() throws Exception {
		int type = getInt();
		String userId = getString();
		String applyId = getString();
		int crowdId = getInt();
		if (type == Config.SUCCESS) {
			log.info(userId + "同意" + applyId + "进入" + crowdId + "群聊");
			agreeToCrowd(applyId, crowdId);
		} else {
			log.info(userId + "拒绝" + applyId + "进入" + crowdId + "群聊");
			sendApplyResult(applyId, crowdId, Config.FAILED);
		}
	}

	/**
	 * 同意进入，判断是否已经在群中。群是否已经满了
	 * 
	 * @throws IOException
	 * 
	 */
	private void agreeToCrowd(String applyId, int crowdId) throws IOException {
		// 1.是否已经在群中
		if (crowdMenberService.isCrowder(applyId, crowdId)) {
			// 1.1 已经在群中
			buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(Config.CROWD_USER_ALREADY);
			put(applyId);
			buffer.putInt(crowdId);
			writeBuffer();
		} else {
			// 1.2 不在群中 ，判断群是否满人
			int isfull = crowdMenberService.isFull(crowdId);
			if (isfull == 1) {
				// 1.2.1 满人。告知管理员以及申请人
				// 1.2.1.1告知管理员
				sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
				// 1.2.1.2 告知申请人
				sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY,
						map.get(applyId));
			} else if (isfull == 0) {
				// 1.2.2 不满人
				sendApplyResult(applyId, crowdId, Config.SUCCESS);
				crowdMenberService.saveCrowdMenber(applyId, crowdId, 3);
			} else {
				log.info("群不存在");
				crowdMenberService.deleteApply(applyId, crowdId);
				return;
			}
		}
		// 删除申请
		crowdMenberService.deleteApply(applyId, crowdId);
	}

	/**
	 * 发送申请群的回复
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
	 * 设置管理员
	 */
	public void setManager() throws Exception{
		String userId = getString();
		int crowdId = getInt();
		log.info("将" + userId + "设置成为群" + crowdId + "的管理员");
	}
	
	/**
	 * 撤销管理员
	 * @throws Exception
	 */
	public void cancelManager() throws Exception{
		String userId = getString();
		int crowdId = getInt();
		log.info("撤销" + userId + "为群" + crowdId + "的管理员");
	}
}
