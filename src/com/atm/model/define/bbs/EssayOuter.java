package com.atm.model.define.bbs;

import com.atm.util.bbs.ObjectInterface;



/**
 * @������ʾ ���������б��У��������ӵĴ�����Ϣ����ʾ������ģ�
 * @author Jiong
 * @�޸�ʱ�� 2015��07��29�� 19:48:59
 */
public class EssayOuter implements java.io.Serializable,ObjectInterface {
	
	private Integer essayId;   //���ӱ�ţ���Ϊ���ȥ��ȡȫ�����ݵı�ʶ��
	private String essayType;  //�������� ��20��
	private String title;      //���ӱ��� ��100��
	private String labName;    //��ǩ����100��  ʾ������ǩ1*#��ǩ2 ���ָ�����*#��
	private String labColor;	//��ǩ��ɫ��100��ʾ������ɫ1*��ɫ2���ָ�����*��
	private String someContent;//�����������ݣ�50��
	private String nickname;   //�������ǳƣ�15��
	private String publishTime; //����ʱ��
	private Integer clickGoodNum; //������
	private Integer replyNum;  //�ظ���
	private String publisherId; //�������˺�
	private String department; //ϵ�����ƣ�20���������ⲻ��ʾ�������͸���,��ɾ����
	private String dNo;
	private String headImagePath; //ͷ��·��
	private String labId;
	private String fullContent;
	
	//�������ԣ����Ż����µı�־
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
	 * ȥ��department
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
