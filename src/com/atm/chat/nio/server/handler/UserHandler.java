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
 * 处理有关用户的请求消息，包括添加，取消关注。获取关注人，粉丝列表以及资本详细资料
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
	 * 操作的中转站
	 * 
	 * @param type
	 * @throws Exception
	 */
	public void operate(int type) throws Exception {
		switch (type) {
		case Config.USER_ADD_ATTENT:// 添加关注
			addAttent();
			break;
		case Config.USER_GET_ATTENT:// 获取我关注的
			myAttented();
			break;
		case Config.USER_GET_ATTENTED:// 获取我的
			attentMe();
			break;
		case Config.USER_OTHER_ATTENT:
			// TODO 别人关注的用户
			otherAttent();
			break;
		case Config.USER_OTHER_FANS:
			// TODO 别人的粉丝
			otherFans();
			break;
		case Config.USER_CANCEL_ATTENT:// 取消关注
			cancelAttent();
			break;
		case Config.USER_FIND: // 寻找与keyWord匹配的用户
			findUser();
			break;
		case Config.USER_FOUND:// 查找可能感兴趣的用户
			findInterestingUser();
			break;
		case Config.USER_LIST: // 寻找可能感兴趣的人
			findInterestingUser();
			break;
		case Config.USER_GET_INFO:// 获取用户的基本资料
			findUserBasicInfo();
			break;
		case Config.USER_GET_HEAD:// 获取用户头像
			userHead();
			break;
		default:
			break;
		}
	}

	/**
	 * 根据获取到的keyWord搜索相关的用户。
	 * 
	 * @throws Exception
	 */
	public void findUser() throws Exception {
		String userId = getString();
		String keyWord = getString();
		try {
			log.info(userId + "请求搜索’" + keyWord + "‘有关用户");
			List<UserList> userLists = userService.findUser(keyWord, 0, 20);
			sendUserList(userLists, Config.USER_RESULT_FIND);
		} catch (Exception e) {
			log.info("查找失败");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_FIND);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}
	}

	/**
	 * 查找可能感兴趣的用户
	 * 
	 * @throws Exception
	 */
	public void findInterestingUser() throws Exception {
		String userId = getString();// 接收userID
		try {
			log.info("进入到查找可能感兴趣的用户");
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
	 * 发送用户列表
	 * 
	 * @param userLists
	 *            用户列表
	 * @param type
	 *            发送的结果类型
	 * @throws IOException
	 */
	public void sendUserList(List<UserList> userLists, int type)
			throws IOException {
		int count = userLists.size();
		log.info("count:" + count);
		// 判断userLists是否为空.为空则为没有找到
		if (userLists.size() == 0) {
			log.info("没有找到");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(type);
			buffer.putInt(Config.NOT_FOUND);
			writeBuffer();
			return;
		}
		log.info("找到记录-------");
		int length = 0;// buffer 的容量
		byte[][] images = new byte[count][];
		try {
			for (int i = 0; i < count; i++) {
				UserList userList = userLists.get(i);
				log.info("i:" + i);
				String path = this.getClass().getResource("/").getPath()
						.substring(1).replaceFirst("WEB-INF/classes/", "");
				log.info(path);
				// 获取用户头像缩略图的头像
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
		log.info("开始发送用户列表");
		buffer = ByteBuffer.allocateDirect(length);
		log.info("buffer的容量：" + length);
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
		log.info("-----------发送结束-------------");
	}

	/**
	 * 发送用户列表
	 * 
	 * @param userLists
	 *            用户列表
	 * @param type
	 *            发送的结果类型
	 * @throws IOException
	 */
	public void sendUserList(List<UserList> userLists, int type, String userId,
			String otherUserId) throws IOException {
		int count = userLists.size();
		log.info("count:" + count);
		// 判断userLists是否为空.为空则为没有找到
		if (userLists.size() == 0) {
			log.info("没有找到");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(type);
			buffer.putInt(Config.NOT_FOUND);
			writeBuffer();
			return;
		}
		log.info("找到记录-------");
		int length = 0;// buffer 的容量
		byte[][] images = new byte[count][];
		try {
			for (int i = 0; i < count; i++) {
				UserList userList = userLists.get(i);
				log.info("i:" + i);
				String path = this.getClass().getResource("/").getPath()
						.substring(1).replaceFirst("WEB-INF/classes/", "");
				log.info(path);
				// 获取用户头像缩略图的头像
				images[i] = FileUtil.makeFileToByte(path
						+ userList.getHeadImagePath());
				log.info("iamges" + i + ":" + images[i].length);
				length += 24
						// 当前用户的id
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
		// 当前other用户的id
				+ otherUserId.getBytes().length + 4 + 12;
		log.info("开始发送用户列表");
		buffer = ByteBuffer.allocateDirect(length);
		log.info("buffer的容量：" + length);
		buffer.putInt(type);
		buffer.putInt(Config.SUCCESS);
		// 当前用户
		put(userId);
		// 当前other用户的id
		put(otherUserId);
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			UserList userList = userLists.get(i);
			put(userList.getUserId());
			put(userList.getNickname());
			put(userList.getdName());
			put(userList.getSex());
			// 是否有关注
			buffer.putInt(userList.getFlag());
			buffer.putInt(images[i].length);
			buffer.put(images[i]);
		}
		writeBuffer();
		log.info("-----------发送结束-------------");
	}

	/****************************** 关注 ***********************/
	/**
	 * 添加关注
	 * 
	 * @throws Exception
	 */
	public void addAttent() throws Exception {
		log.info("-----------添加关注");
		String userAttentId = getString();
		String userAttentedId = getString();
		try {
			log.info(userAttentId + " attend " + userAttentedId);
			userService.addAttent(userAttentId, userAttentedId);
			log.info("添加成功");
			sendResultAttent(Config.USER_RESULT_ADDATTENT, Config.SUCCESS);
		} catch (Exception e) {
			log.info("添加关注失败");
			sendResultAttent(Config.USER_RESULT_ADDATTENT, Config.FAILED);
		}
	}

	/**
	 * 得到所有的关注我的人
	 * 
	 * @throws Exception
	 */
	public void attentMe() throws Exception {
		log.info("-----------关注我的");
		String userAttentedId = getString();
		try {
			List<UserList> userLists = userService
					.findAttentedMe(userAttentedId);
			sendUserList(userLists, Config.USER_GET_ATTENTED);
		} catch (Exception e) {
			log.info("失败");
		}
	}

	/**
	 * 得到所有我关注的
	 * 
	 * @throws Exception
	 */
	public void myAttented() throws Exception {
		log.info("-----------我关注的");
		String userAttentId = getString();
		try {
			List<UserList> userLists = userService.findMyAttent(userAttentId);
			sendUserList(userLists, Config.USER_RESULT_GETATTENT);
		} catch (Exception e) {
			log.info("失败");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_GETATTENT);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}

	}

	/**
	 * 取消关注
	 * 
	 * @throws Exception
	 */
	public void cancelAttent() throws Exception {
		log.info("---------取消关注------------");
		String userId = getString();// 关注人
		String attentedId = getString();// 被关注人
		try {
			userService.cancelAttent(userId, attentedId);
			log.info("取消关注成功");
			sendResultAttent(Config.USER_RESULT_CANCELATTENT, Config.SUCCESS);
		} catch (Exception e) {
			log.info("取消关注失败");
			sendResultAttent(Config.USER_RESULT_CANCELATTENT, Config.FAILED);
		}
	}

	/**
	 * 获取其他用户关注的列表
	 * 
	 * @throws Exception
	 */
	public void otherAttent() throws Exception {
		log.info("--------获取其他用户关注的列表----------");
		// 当前用户
		String userId = getString();
		// 其他用户
		String otherUserId = getString();
		try {
			List<UserList> userLists = userService.findOtherAttented(
					otherUserId, userId);
			log.info("获取其他用户的关注列表");
			sendUserList(userLists, Config.USER_RESULT_OATTENT, userId,
					otherUserId);
		} catch (Exception e) {
			log.info("失败");
			buffer = ByteBuffer.allocateDirect(8);
			buffer.putInt(Config.USER_RESULT_OATTENT);
			buffer.putInt(Config.FAILED);
			writeBuffer();
		}
	}

	/**
	 * 获取其他用户的粉丝列表
	 * 
	 * @throws Exception
	 */
	public void otherFans() throws Exception {
		log.info("-------获取其他用户的粉丝列表---------");
		// 当前用户
		String userId = getString();
		// 其他用户
		String otherUserId = getString();
		try {
			List<UserList> userLists = userService.findOtherFans(otherUserId,
					userId);
			log.info("获取其他用户的关注列表");
			sendUserList(userLists, Config.USER_RESULT_OFANS, userId,
					otherUserId);
		} catch (Exception e) {
			log.info("失败");
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
	 * 获取用户的基本资料信息
	 * 
	 * @throws Exception
	 */
	public void findUserBasicInfo() throws Exception {
		String userId = getString();
		String otherUserId = getString();
		int relationShip = 0;
		log.info("====" + userId + "获取" + otherUserId + "的基本资料======");
		try {
			UserBasicInfo basicInfo = userService.getUserBasicInfo(otherUserId);
			relationShip = userService.getRelationShip(userId, otherUserId);
			if (basicInfo != null) {
				sendUserBasicInfo(basicInfo, relationShip);
				return;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("获取基本资料失败");
		}
		buffer = ByteBuffer.allocateDirect(8);
		buffer.putInt(Config.USER_RESULT_GETINFO);
		buffer.putInt(Config.FAILED);
		writeBuffer();
	}

	/**
	 * 获取用户头像的读取方法,接收用户发过来的userId,寻找userId的头像
	 * 
	 * @throws Exception
	 */
	public void userHead() throws Exception {
		log.info("获取用户头像");
		// 获取用户userId
		String userId = getString();
		try {
			// 获取用户头像的路径
			String path = userService.getUserInfo(userId).getHeadImagePath();
			// 获取项目路径
			String basicPath = this.getClass().getResource("/").getPath()
					.substring(1).replaceFirst("WEB-INF/classes/", "");
			log.info("path:" + basicPath + path);
			// 获取用户头像的字节数组
			byte[] bs = FileUtil.makeFileToByte(basicPath + path);
			// 发送
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
	 * 获取用户资料成功。发送用户基本资料信息
	 * 包括userId,nickname,sign,dname,scname,focusNum,funsNum,image
	 * 
	 * @param userBasicInfo
	 * @throws IOException
	 */
	public void sendUserBasicInfo(UserBasicInfo userBasicInfo, int relationship)
			throws IOException {
		// 关注的人数
		String focusNum = userBasicInfo.getFocusNum() + "";
		// 粉丝数
		String fansNum = userBasicInfo.getFansNum() + "";
		String path = this.getClass().getResource("/").getPath().substring(1)
				.replaceFirst("WEB-INF/classes/", "");
		log.info(path);
		// 获取用户头像的字节数组
		byte[] image = FileUtil.makeFileToByte(path
				+ userBasicInfo.getHeadImagePath());
		String publishNum = "24";
		String publishTitle = "[中国好编程]第三轮敦豪老师为抢人祭出凤姐";
		String publishContent = "好编程迎来最火爆,广金学子编程艺术惊呆四位导师...";
		String collectNum = "28";
		String collectTitle = "[爪哇新闻]敦豪放出豪言，我的学生至少bat";
		String collectContent = "今日，敦豪老师说他的学生年薪至少六位数,寒冬说他在放屁...";

		int length = 8 + 4 // 关系
				// id
				+ userBasicInfo.getUserId().getBytes().length + 4
				// 昵称
				+ userBasicInfo.getNickname().getBytes().length + 4
				// 性别
				+ userBasicInfo.getSex().getBytes().length + 4
				// focusNum
				+ 4 + focusNum.getBytes().length
				// fansNum
				+ 4 + fansNum.getBytes().length
				// 签名
				+ userBasicInfo.getSign().getBytes().length + 4
				// 学校
				+ userBasicInfo.getScName().getBytes().length + 4
				// 系别
				+ userBasicInfo.getDname().getBytes().length + 4
				// 发帖数量
				+ publishNum.getBytes().length + 4
				// 发帖标题
				+ publishTitle.getBytes().length + 4
				// 发帖内容
				+ publishContent.getBytes().length + 4
				// 收藏贴数量
				+ collectNum.getBytes().length + 4
				// 收藏贴标题
				+ collectTitle.getBytes().length + 4
				// 收藏贴内容
				+ collectContent.getBytes().length + 4
				// 头像
				+ image.length + 4;

		// 设定buffer的长度
		buffer = ByteBuffer.allocateDirect(length);

		// 类型
		buffer.putInt(Config.USER_RESULT_GETINFO);
		// 成功
		buffer.putInt(Config.SUCCESS);

		// 1 用户id
		put(userBasicInfo.getUserId());
		// 2 用户昵称
		put(userBasicInfo.getNickname());
		// 3 性别
		put(userBasicInfo.getSex());
		// 4 用户关注的人数
		put(focusNum + "");
		// 5 用户的粉丝
		put(fansNum + "");
		// 6 用户签名
		put(userBasicInfo.getSign());
		// 7 用户学校
		put(userBasicInfo.getScName());
		// 8 用户系别
		put(userBasicInfo.getDname());
		// 用户关系
		buffer.putInt(relationship);
		// 9发帖数量
		put(publishNum);
		// 10发帖标题
		put(publishTitle);
		// 11发帖内容
		put(publishContent);
		// 12收藏贴数量
		put(collectNum);
		// 13收藏贴标题
		put(collectTitle);
		// 14收藏贴内容
		put(collectContent);
		// 15 头像字节数组的大小
		buffer.putInt(image.length);
		// 16 头像字节数组
		buffer.put(image);
		// 写进管道

		writeBuffer();
	}

	/**
	 * 修改用户基本资料
	 */
	public void changeUserBasicInfo() {
		// TODO 修改个人资料
	}
}
