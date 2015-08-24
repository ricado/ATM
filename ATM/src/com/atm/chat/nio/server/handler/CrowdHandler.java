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
		case Config.CROWD_CREATE:// 创建群聊
			createCrowd();
			break;
		case Config.CROWD_APPLY:// 申请进群
			applyToCrowd();
			break;
		case Config.CROWD_RESULT_APPLY:// 管理员回复的管理结果
			applyResult();
			break;
		case Config.CROWD_INTIVE:// 邀请进群
			intiveToCrowd();
			break;
		case Config.CROWD_FOUND:// 查找感兴趣的群或者热门群
			foundCrowd();
			break;
		case Config.CROWD_FIND:// 搜索群
			findCrowd();
			break;
		case Config.CROWD_MY:// 我的群
			findCrowdByUserId();
			break;
		case Config.CROWD_GET_INFO:// 获取群聊消息
			findCrowdMenber();
			break;
		default:
			break;
		}
	}

	/**
	 * 查找某一个人的群
	 * 
	 * @throws Exception
	 */
	public void findCrowdByUserId() throws Exception {
		String userId = getString();
		log.info("userId:" + userId);
		// TODO
		log.info("-------查找用户所属的所有群-------------");
		List<CrowdList> crowdLists = crowdService.findUsersCrowd(userId,0,10);
		log.info("-------发送对应的群列表内容---------");
		sendCrowdList(crowdLists,Config.CROWD_RESULT_MY);
	}

	/**
	 * 查找群
	 * 
	 * @throws Exception
	 */
	public void findCrowd() throws Exception {
		String userId = getString();
		String content = getString();
		// int max = getInt();
		// TODO查找群
		log.info(userId + "请求查找-" + content + "-相关的群");
		List<CrowdInfo> crowdInfos = crowdService.fingCrowdByNameOrLabel(
				content, 0, 20);
		log.info("===将crowdInfos转换成crowdList");
		List<CrowdList> crowdLists = makeCrowdInfoToCrowdList(crowdInfos);
		sendCrowdList(crowdLists,Config.CROWD_RESULT_FIND);
	}

	/**
	 * 将CrowdInfo类转换成CrowdList
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
	 * 查找可能感兴趣的群，或者热门群
	 * 
	 * @throws Exception
	 * 
	 */
	public void foundCrowd() throws Exception {
		log.info("---查找群可能感兴趣的或者热门群---");
		String userId = getString();
		//int first = getInt();
		//int max = getInt();
		int number = (int) (Math.random() * 10);
		List<CrowdList> crowdLists;// 群列表的列表
		if (number > 5) {
			log.info("--------可能感兴趣");
			crowdLists = crowdService.foundInterestingCrowd(userId, 0, 10);
		} else {
			log.info("--------热门");
			crowdLists = crowdService.foundHotCrowd(0, 10);
		}
		sendCrowdList(crowdLists,Config.CROWD_RESULT_FOUND);
	}

	/**
	 * 发送群列表的信息。
	 * @param crowdLists
	 * @throws IOException
	 */
	private void sendCrowdList(List<CrowdList> crowdLists,int type) throws IOException {
		if (crowdLists.size() == 0) {
			log.info("没有找到");
			buffer = ByteBuffer.allocateDirect(8);
			log.info("buffer的容量:" + buffer.limit());
			buffer.putInt(type);
			buffer.putInt(Config.NOT_FOUND);
			writeBuffer();
			return;
		}	
		int count = crowdLists.size();// 列表的数目
		log.info("count:" + count);
		CrowdList crowdList = null;
		//人数
		String[] limitStr = new String[count];
		//头像文件名
		String[] filenames = new String[count];
		int length = 0;// buffer的长度
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
		log.info("开始发送群列表的信息");
		//开始发送图像
		buffer = ByteBuffer.allocateDirect(length + 12);
		log.info("buffer的容量:" + buffer.limit());
		log.info("buffer的容量:" + (length + 12));
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
		log.info("-------传输完毕----");
	}

	/**
	 * 创建群聊
	 * 
	 * @throws Exception
	 */
	public void createCrowd() throws Exception {
		/*************** 获取对应信息 *************/
		String userId = getString();// 群主Id
		String crowdName = getString();// 群名称
		String describe = getString();// 群描述
		String crowdLabel = getString();// 群label
		int isHidden = getInt();// 是否隐藏
		String filename = getString();// 头像名
		log.info("...................filename:" + filename);
		int length = getInt();
		byte[] headByte = FileUtil.getFileBytes(socketChannel, length);// 获取头像文件字节数组
		String time = TimeUtil.getFileTimeFormat();
		log.info("length:" + length);
		log.info("bytes.size:" + headByte.length);

		log.info("time:" + time);
		String[] strings = filename.split("\\.");
		String extension = strings[1];
		log.info("===============创建群聊================");
		log.info("userId:" + userId);
		log.info("crowdName:" + crowdName);
		log.info("describe:" + describe);
		log.info("crowdLabel:" + crowdLabel);
		log.info("isHidden:" + isHidden);
		log.info("filename" + filename);
		log.info("========================================");

		// 封装成一个CrowdChat
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

		// 创建群聊，成功返回crowdId 失败返回-100
		// crowdListInfo crowdListInfo = CrowdService.saveCrowdChat(userId,
		// chat);
		int crowdId = crowdService.saveCrowdChat(userId, chat);
		// 保存头像到服务器中，并放回存放路径
		new ImageHandler(extension, headByte, 1, crowdId + "").start();
		;
		// 发送创建群聊信息
		sendCrowdResultCreate(crowdId);
	}

	/**
	 * 发送创建群的结果
	 * 
	 * @param crowdId
	 */
	public void sendCrowdResultCreate(int crowdId) {
		if (crowdId != -100) { // 创建成功
			buffer = ByteBuffer.allocateDirect(12);
			buffer.putInt(Config.CROWD_RESULT_CREATE);
			buffer.putInt(Config.SUCCESS);
			buffer.putInt(crowdId);
			// sendCrowdListInfo(crowdListInfo);// 发送新的群列表
		} else { // 创建失败
			buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(Config.CROWD_RESULT_CREATE);
			buffer.putInt(Config.FAILED);
		}
		writeBuffer();
	}

	/**
	 * 发送群聊列表的消息
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

		buffer.putInt(Config.CROWD_LIST);// 群列表类型
		buffer.putInt(crowdListInfo.getCrowdId());// 群ID
		put(crowdListInfo.getCrowdName());// 群名称
		put(filename);// 文件名
		buffer.putInt(bytes.length);// 文件大小
		buffer.put(bytes);// 文件字节数组

		writeBuffer();
	}

	/**
	 * 获取群聊信息
	 * 
	 * @throws IOException
	 */
	// TODO 获取群聊消息
	public void getCrowdInfo() throws IOException {
		int crowdId = getInt();
		CrowdInfo crowdInfo = crowdService.getCrowdInfo(crowdId);
		if (crowdInfo != null) {// 群存在，发送该群聊的消息
			sendCrowdInfo(crowdInfo);
		}
	}

	/**
	 * 发送群聊消息
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
	 * 查找群成员
	 * 
	 * @throws IOException
	 */
	public void findCrowdMenber() throws IOException {
		// TODO 查找群成员
		int crowdId = getInt();// 群编号
		log.info("群" + crowdId + "的群成员ID");
		try {
			List<MenberList> menbers = menberService
					.findAllMenber(crowdId);
			sendMenberList(menbers, crowdId);
		} catch (Exception e) {
			log.info("===========搜索失败==========");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.CROWD_RESULT_GETINFO);
			buffer.putInt(Config.FAILED);
			// buffer.putInt(crowdId);
			writeBuffer();
		}
	}

	/**
	 * 发送群资料的群成员
	 * 
	 * @param menberLists
	 *            menberLists的列表消息
	 * @param crowdId
	 *            群Id
	 * @throws IOException
	 */
	public void sendMenberList(List<MenberList> menberLists, int crowdId)
			throws IOException {
		// 判断是否找到群成员
		if (menberLists == null || menberLists.size() == 0) {
			log.info("===========没有搜索到==========");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.CROWD_RESULT_GETINFO);
			buffer.putInt(Config.NOT_FOUND);
			// buffer.putInt(crowdId);
			writeBuffer();
			return;
		}
		int count = menberLists.size();
		log.info("搜索成功,群人数为:" + count);
		/**
		 * 设置长度
		 */
		MenberList menberList = null;// 存放每个MenberList
		byte[][] images = new byte[count][];
		int length = 12;
		for (int i = 0; i < count; i++) {
			menberList = menberLists.get(i);
			images[i] = FileUtil.makeFileToByte(menberList.getHeadImagePath());
			length += 16
					+ menberList.getUserId().getBytes().length // userID字节数组的长度
					+ menberList.getNickname().getBytes().length
					+ images[i].length;
		}

		log.info("buffer的大小 :" + length);
		// 设定buffer大小
		buffer = ByteBuffer.allocateDirect(length);
		buffer.putInt(Config.CROWD_RESULT_GETINFO);// 群资料结果
		buffer.putInt(Config.SUCCESS);// 成功
		// buffer.putInt(crowdId);//群ID
		log.info("=========开始发送============");
		buffer.putInt(count);// 群成员数量
		for (int i = 0; i < count; i++) {
			menberList = menberLists.get(i);
			put(menberList.getUserId());// userID
			put(menberList.getNickname());// 昵称
			buffer.putInt(menberList.getRoleId());// 角色
			buffer.putInt(images[i].length);// 图片长度
			buffer.put(images[i]);// 图片
		}
		log.info("=========结束============");
		writeBuffer();
		log.info("============传送成功==========");
	}

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
		int isfull = menberService.isFull(crowdID);
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
				menberService.intiveToCrowd(intiveID, intivedID, crowdID);
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
		int i = menberService.isFull(crowdID);
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
		int isfull = menberService.isFull(crowdId);
		if (isfull == 1) {
			// 1.1 满人，告知申请人已满
			sendCrowdFull(crowdId, Config.CROWD_RESULT_APPLY);
		} else if (isfull == 0 && menberService.isManager(userId, crowdId)) {
			// 邀请人为管理员直接加入群
			menberService.saveCrowdMenber(applyId, crowdId, 3);
			buffer = ByteBuffer.allocateDirect(12);
			buffer.putInt(Config.CROWD_RESULT_APPLY);
			buffer.putInt(Config.SUCCESS);
			buffer.putInt(crowdId);
			writeBuffer();
		} else if (isfull == 0) {
			log.info("============群未满============");
			// 1.2 查找出该群的管理员们
			List<String> managers = menberService.getCrowdManagers(crowdId);
			// 管理员管道
			SocketChannel channel = null;
			menberService.applyToCrowd(applyId, crowdId);
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
		int isfull = menberService.isFull(crowdId);
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
		if (menberService.isCrowder(applyId, crowdId)) {
			// 1.1 已经在群中
			buffer = ByteBuffer.allocateDirect(4);
			buffer.putInt(Config.CROWD_USER_ALREADY);
			put(applyId);
			buffer.putInt(crowdId);
			writeBuffer();
		} else {
			// 1.2 不在群中 ，判断群是否满人
			int isfull = menberService.isFull(crowdId);
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
				menberService.saveCrowdMenber(applyId, crowdId, 3);
			} else {
				log.info("群不存在");
				menberService.deleteApply(applyId, crowdId);
				return;
			}
		}
		// 删除申请
		menberService.deleteApply(applyId, crowdId);
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
}