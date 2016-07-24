package com.atm.action.BBS;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.ReplyDeal;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @TODO��
 * @fileName : com.atm.action.ReplyAction.java
 * date | author | version |   
 * 2015��8��11�� | Jiong | 1.0 |
 */
public class ReplyAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,ObjectInterface{
Logger log = Logger.getLogger(getClass());
	
	private int essayId;
	private int first = 0;
	private int floorId;
	private int replyIndex;//���ҵ���ʼλ�ã���Ϊ-1���ǻ�ȡ���ŵ�һ������
	private int replyId;
	private boolean clickGood;
	
	private String repliedUserId;//���ظ����˵��˺�
	private String repContent;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	ReplyDeal deal = context.getBean("ReplyDeal",ReplyDeal.class);
	
	String mess;
	JSONObject sendJson;
	JSONArray sendArray;
	//��ʼ��
	private void init(){
		mess = "��ȡʧ��";
		sendJson  = new JSONObject();
		sendArray = new JSONArray();
	}
	
	private void sendTip(){
		sendTip(true);
	}
	//������ʾ��Ϣ:trueΪ����JSONArray ,falseΪ����JSONObject
	private void sendTip(boolean method){
		try {
			sendJson.put("tip",mess);
			sendArray.put(0,sendJson);
			if(method)
				sendUtil.writeToClient(response, sendArray);
			else
				sendUtil.writeToClient(response, sendJson);
		} catch (JSONException e) {
			if(method)
				sendUtil.writeToClient(response, errorArray);
			else
				sendUtil.writeToClient(response, errorJson);
			log.error(e);
		}
	}
	public void replyList(){
		log.debug("��ȡ�����б�����");
		init();
		try{
			log.debug(">>>>>>>>>���ڻ�ȡ���۵�����:"+getEssayId());
			sendArray = deal.getReply(request,getEssayId(),getReplyIndex());
			sendUtil.writeToClient(response, sendArray);
		}catch (Exception e) {
			mess = "��ȡ����";
			log.debug(mess, e);
			sendTip();
		}
	}
	
	
	public void replyInnerList(){
		log.debug("��ȡ����¥��¥����");
		init();
		try {
			log.debug(">>>>>>>>>���ڻ�ȡ���۵�����:"+getEssayId());
			sendArray = deal.getInnerReply(getEssayId(),getFloorId(),getFirst());
			sendUtil.writeToClient(response, sendArray);
		} catch (Exception e) {
			mess = "��ȡ����";
			log.debug(mess, e);
			sendTip();
		} 
		
	}
	
	//TODO ���޷���
	public void clickGood(){
		log.debug("��ȡ��������");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			sendTip();
		}else{
			try {
				mess = deal.saveOrDeleteClickGood(request, getReplyId(), isClickGood(),user.getUserId());
				sendTip();
			} catch (Exception e) {
				mess = "��ȡ����";
				log.debug(mess, e);
				sendTip();
			} 
		}
	}
	
	//TODO ����һ������
	public void publishReply(){
		log.debug("��ȡ������������");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			sendTip(false);
			return;
		}
		try {
			String userId = user.getUserId();
			boolean boo = true;
			if(floorId!=0){//¥��Ϊ��Ϊ0˵���ظ�����¥��¥�����жϻظ���¥���Ƿ����
			  boo = deal.haveFloor(floorId);
			}
			mess = deal.saveAReply(request, essayId, userId, repliedUserId, repContent, floorId,boo);
			sendTip(false);
		} catch (Exception e) {
			mess = "��ȡ����";
			log.debug(mess, e);
			sendTip(false);
		} 
		
		return;
	}
	
	//TODO ɾ��һ������
	public String deleteReply(){
		log.debug("��ȡɾ����������"+getReplyId());
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "δ��¼";
			sendTip(false);
			return null;
		}
		try {
			int position = getFirst();//ɾ���������ڵ�λ��
			mess = deal.deleteAReply(user.getUserId(),getReplyId(),position);
			log.debug("ɾ������"+mess);
			sendTip(false);
		} catch (Exception e) {
			mess = "��ȡ����";
			log.debug(mess, e);
			sendTip(false);
		} 
		
		return null;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request = arg0;
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response = arg0;	
	}
	public int getEssayId() {
		return essayId;
	}
	public void setEssayId(int essayId) {
		this.essayId = essayId;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getFloorId() {
		return floorId;
	}
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	public boolean isClickGood() {
		return clickGood;
	}
	public void setClickGood(boolean clickGood) {
		this.clickGood = clickGood;
	}
	public int getReplyIndex() {
		return replyIndex;
	}
	public void setReplyIndex(int replyIndex) {
		this.replyIndex = replyIndex;
	}

	public String getRepliedUserId() {
		return repliedUserId;
	}

	public void setRepliedUserId(String repliedUserId) {
		this.repliedUserId = repliedUserId;
	}

	public String getRepContent() {
		return repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
	}
}

