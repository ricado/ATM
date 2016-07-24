package com.atm.model.define.bbs;

import com.atm.util.bbs.ObjectInterface;



/**
 * @帖子显示 用在帖子列表中，包含帖子的大致信息（显示在外面的）
 * @author Jiong
 * @修改时间 2015年07月29日 19:48:59
 */
public class EssayOuter implements java.io.Serializable,ObjectInterface {
	
	private Integer essayId;   //帖子编号（作为点进去获取全部内容的标识）
	private String essayType;  //帖子类型 （20）
	private String title;      //帖子标题 （100）
	private String labName;    //标签名（100）  示例：标签1*#标签2 （分隔符：*#）
	private String labColor;	//标签颜色（100）示例：颜色1*颜色2（分隔符：*）
	private String someContent;//部分帖子内容（50）
	private String nickname;   //发布者昵称（15）
	private String publishTime; //发布时间
	private Integer clickGoodNum; //点赞数
	private Integer replyNum;  //回复数
	private String publisherId; //发布者账号
	private String department; //系别名称（20）（帖子外不显示，不发送给你,可删除）
	private String dNo;
	private String headImagePath; //头像路径
	private String labId;
	private String fullContent;
	
	//额外属性，热门或最新的标志
	private String flag;
	/**
	 * 
	 */
	public EssayOuter() {
	}
	
	/**
	 * @param essayId
	 * @param essayType
	 * @param title
	 * @param labName
	 * @param someContent
	 * @param nickname
	 * @param publishTime
	 * @param clickGoodNum
	 * @param replyNum
	 */
	public EssayOuter(Integer essayId, String essayType, String title,String department,
			String labName,String labColor, String someContent, String nickName,String publisherId,
			String publishTime, Integer clickGoodNum, Integer collectNum,String headImagePath,String labId) {
		this.essayId = essayId;
		this.essayType = essayType;
		this.title = title;
		this.department = department;
		this.labName = labName;
		this.labColor = labColor;
		this.someContent = someContent;
		this.nickname = nickName;
		this.publishTime = publishTime;
		this.clickGoodNum = clickGoodNum;
		this.replyNum = collectNum;
		this.publisherId = publisherId;
		this.headImagePath = headImagePath;
		this.labId = labId;
	}
	/**
	 * 去掉department
	 * @param essayId
	 * @param essayType
	 * @param title
	 * @param labName
	 * @param someContent
	 * @param nickname
	 * @param publishTime
	 * @param clickGoodNum
	 * @param replyNum
	 * @param publisherId
	 * @param headImagePath
	 */
	public EssayOuter(Integer essayId, String essayType, String title,
			String labName, String labColor,String someContent, String nickname,
			String publishTime, Integer clickGoodNum, Integer replyNum,
			String publisherId, String headImagePath) {
		super();
		this.essayId = essayId;
		this.essayType = essayType;
		this.title = title;
		this.labName = labName;
		this.someContent = someContent;
		this.nickname = nickname;
		this.publishTime = publishTime;
		this.publishTime = commonUtil.subTime(publishTime);
		this.clickGoodNum = clickGoodNum;
		this.replyNum = replyNum;
		this.publisherId = publisherId;
		this.headImagePath = headImagePath;
		this.labColor = labColor;
	}

	public Integer getEssayId() {
		return essayId;
	}
	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}
	public String getEssayType() {
		return essayType;
	}
	public void setEssayType(String essayType) {
		this.essayType = essayType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getSomeContent() {
		return someContent;
	}
	public void setSomeContent(String someContent) {
		this.someContent = someContent;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickName) {
		this.nickname = nickName;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
		this.publishTime = commonUtil.subTime(publishTime);
	}
	public Integer getClickGoodNum() {
		return clickGoodNum;
	}
	public void setClickGoodNum(Integer clickGoodNum) {
		this.clickGoodNum = clickGoodNum;
	}
	public Integer getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(Integer collectNum) {
		this.replyNum = collectNum;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getHeadImagePath() {
		return headImagePath;
	}

	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}

	public String getLabColor() {
		return labColor;
	}

	public void setLabColor(String labColor) {
		this.labColor = labColor;
	}

	public String getdNo() {
		return dNo;
	}

	public void setdNo(String dNo) {
		this.dNo = dNo;
	}

	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	public String getFullContent() {
		return fullContent;
	}

	public void setFullContent(String fullContent) {
		this.fullContent = fullContent;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
	
}
