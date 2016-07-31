package com.atm.chat.nio.server.util;

/**
 * 规划整理Config.更加系统地枚举每一个可能事件 {聊天消息类型(1001-1110)} {基本类型操作(100-999)}
 * {群操作(1200-1399)} {用户操作(1400-1599)}{成功:1,失败:0}
 * 
 * @version 2.0
 * @author ye
 * @2015.8.18
 */
public interface Config {

	// * 有*字样表示名字有改动
	/**
	 * 聊天消息类型
	 */
	// 私聊
	public static final int MESSAGE_TO = 1001;// 发送私聊消息
	public static final int MESSAGE_FROM = 1002;// 接收私聊消息
	public static final int MESSAGE_TEXT = 10003;// *私聊文本消息
	public static final int MESSAGE_IMG = 1004;// *私聊图片消息
	public static final int MESSAGE_SOUND = 1005;// * 群聊语音消息(未开发，敬请期待)
	public static final int MESSAGE_AUDIO = 1006;// *群聊视屏消息(未开发，敬请期待)
	public static final int MESSAGE_SUCCESS = 1007;// 消息发送成功
	public static final int MESSAGE_FAILED = 1008; // 消息发送失败
	public static final int MESSAGE_OFFLINE = 1009;// 获取离线消息

	// 群聊天
	public static final int CROWD_MESSAGE_TO = 1101; // 发送群聊消息
	public static final int CROWD_MESSAGE_FROM = 1102;// 接收群聊消息
	public static final int CROWD_MESSAGE_TEXT = 1103; // 群聊文本消息
	public static final int CROWD_MESSAGE_IMG = 1104;// 群聊图片消息
	public static final int CROWD_MESSAGE_SOUND = 1105;// 群聊语音消息(未开发，敬请期待)
	public static final int CROWD_MESSAGE_AUDIO = 1106;// 群聊视屏消息(未开发，敬请期待)
	public static final int CROWD_MESSAGE_SUCCESS = 1107;// 群聊消息发送成功
	public static final int CROWD_MESSAGE_FAILED = 1108; // 群聊消息发送失败

	/**
	 * 我的消息--离线
	 */
	public static final int MY_OFF_MESSAGE = 1151;

	public static final int MY_RESULT_MESSAGE = 1152; // 群聊消息发送失败
	
	/**
	 * 收到我的消息
	 */
	public static final int MY_MESSAGE = 1153; // 我的消息

	/** 基本的请求类型 **/
	public static final int REQUEST_LOGIN = 100;// 登录
	public static final int REQUEST_REGIST = 101;// 注册
	public static final int REQUEST_EXIT = 102;
	public static final int REQUEST_BE_OFF = 103;

	public static final int REQUEST_UI = 99; // 更新UI视图 *敦豪的

	public static final int RESULT_LOGIN = 200;
	public static final int RESULT_REGIST = 201;
	public static final int RESULT_UPDATE_HEAD = 202;

	public static final int IMG_NEED_UPDATE = 600;
	public static final int IMG_NO_UPDATE = 601;

	/**
	 * 群的操作
	 */
	public static final int CROWD_CREATE = 1201;// 创建群聊
	public static final int CROWD_MY = 1202;// 查找我的群
	public static final int CROWD_FIND = 1203;// 查找群
	public static final int CROWD_INTIVE = 1204;// 邀请进群
	public static final int CROWD_APPLY = 1205;// 申请进群
	public static final int CROWD_GET_INFO = 1206;// 群信息
	public static final int CROWD_GET_HEAD = 1207;// 获取群头像
	public static final int CROWD_SET_INFO = 1208;// 群员已经在群中
	public static final int CROWD_SET_HEAD = 1209;// 设置群头像
	public static final int CROWD_FOUND = 1210;// found群 可能感兴趣的群或者热门群

	/**
	 * 群操作的回复
	 */
	public static final int CROWD_RESULT_CREATE = 1301; // 创建群聊结果
	public static final int CROWD_RESULT_MY = 1302;// 查找我的群的结果
	public static final int CROWD_RESULT_FIND = 1303;// 查找群的结果
	public static final int CROWD_RESULT_INTIVE = 1304; // 邀请结果
	public static final int CROWD_RESULT_APPLY = 1305;// 申请进群结果
	public static final int CROWD_RESULT_GETINFO = 1306;// 群消息
	public static final int CROWD_RESULT_GETHEAD = 1307;// 申请进群结果
	public static final int CROWD_RESULT_SETINFO = 1308;// 申请进群结果
	public static final int CROWD_RESULT_SETHEAD = 1309;// 申请进群结果
	public static final int CROWD_RESULT_FOUND = 1310;// 申请进群结果

	public static final int CROWD_LIST = 1321; // 群列表
	public static final int CROWD_USER_ALREADY = 1322; // 群员已经在群中
	public static final int CROWD_FULL = 1323; // 群满人
	public static final int CROWD_NO_EXIST = 1324;// 群不存在
	/**
	 * 用户操作
	 */
	public static final int USER_LIST = 1401;// 用户列表
	public static final int USER_GET_ATTENT = 1402;// 我关注的
	public static final int USER_GET_ATTENTED = 1403;// 关注我的
	public static final int USER_GET_INFO = 1404;// 用户信息
	public static final int USER_GET_HEAD = 1405;// 用户头像
	public static final int USER_ADD_ATTENT = 1406;// 添加关注
	public static final int USER_CANCEL_ATTENT = 1407;// 取消关注
	public static final int USER_SET_INFO = 1408;// 修改资料
	public static final int USER_SET_HEAD = 1409;// 修改头像
	public static final int USER_FIND = 1410;// 查找用户
	public static final int USER_FOUND = 1411;// 查找可能感兴趣的群
	public static final int USER_OTHER_ATTENT = 1412;// 别人关注的
	public static final int USER_OTHER_FANS = 1413;// 别人的粉丝

	// 用户操作结果
	public static final int USER_RESULT_LIST = 1501;// 用户列表
	public static final int USER_RESULT_GETATTENT = 1502;// 我关注的
	public static final int USER_RESULT_GETATTENTED = 1503;// 关注我的
	public static final int USER_RESULT_GETINFO = 1504;// 用户信息
	public static final int USER_RESULT_GETHEAD = 1505;// 用户头像
	public static final int USER_RESULT_ADDATTENT = 1506;// 添加关注
	public static final int USER_RESULT_CANCELATTENT = 1507;// 取消关注
	public static final int USER_RESULT_SETINFO = 1508;// 修改资料
	public static final int USER_RESULT_SETHEAD = 1509;// 修改头像
	public static final int USER_RESULT_FIND = 1510;// 查找用户
	public static final int USER_RESULT_FOUND = 1511;// 查找感兴趣的群的结果
	public static final int USER_RESULT_OATTENT = 1512;// 获取别人的关注的结果
	public static final int USER_RESULT_OFANS = 1513;// 获取别人的粉丝的结果

	// 其他
	public static final int USER_NO_FOUND = 1520;// 用户没找到
	public static final int USER_LOGIN_ALREADY = 1521;// 用户已登录
	public static final int USER_NO_ONLINE = 1522; // 用户不在线

	public static final int RELATIONSHIP_ATTENTION = 2002;// 关注标志
	public static final int RELATIONSHIP_FOLLOER = 2003;// 粉丝标志
	public static final int RELATIONSHIP_NO_MATTER = 2004;// 没有关系的标志

	// other
	public static final int USER_MOVE = 9998;// 移动用户
	public static final int MAP_INFO = 9999;
	/**
	 * 对许多操作的同意回复
	 */
	public static final int USED = 6;
	public static final int SUCCESS = 1;
	public static final int FAILED = 0;
	public static final int NOT_FOUND = 2;// 对搜索的操作的回复--没有找到
}
