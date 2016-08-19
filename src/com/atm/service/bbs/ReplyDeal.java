package com.atm.service.bbs;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.chat.nio.server.handler.MyMessageHandler;
import com.atm.dao.bbs.EssayDAO;
import com.atm.dao.bbs.ReplyDAO;
import com.atm.daoDefined.bbs.EssayOuterDAO;
import com.atm.daoDefined.bbs.IsReplyClickGoodViewDAO;
import com.atm.daoDefined.bbs.ReplyClickGoodDAO;
import com.atm.daoDefined.bbs.ReplyViewDAO;
import com.atm.model.bbs.Reply;
import com.atm.model.define.bbs.IsReplyClickGoodView;
import com.atm.model.define.bbs.ReplyClickGood;
import com.atm.model.define.bbs.ReplyClickGoodId;
import com.atm.model.define.bbs.ReplyView;
import com.atm.model.user.UserInfo;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO��
 * @fileName : com.atm.service.ReplyDeal.java
 * date | author | version |   
 * 2015��8��11�� | Jiong | 1.0 |
 */
public class ReplyDeal   implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	
	private JSONArray sendArray;
	
	//��ʼ��
	private void init(){
		sendArray = new JSONArray();
	}
	
	//TODO ��ȡ¥�㣬first Ϊ-1ʱ��ȡһ���������ۣ�����ʱ��Ϊ��ͨ��ʼλ�ò�ѯ
	public JSONArray getReply(HttpServletRequest request,int essayId,int first) throws JSONException, IOException{
		init();
		ReplyViewDAO replyDao = context.getBean("ReplyViewDAO",ReplyViewDAO.class);
		List list;
		//������ʼλ�þ�����ô��ȡ�����
		if(first==-1){
			list = replyDao.findHotReply(essayId);
		}else{
			list = replyDao.findByEssayId(essayId,first);
		}
		//��ȡ���������������������
		if(list.size()==0){
			return new JSONArray().put(new JSONObject().put("num", replyDao.getReplyNum(essayId)));
		}
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		//ѭ��Ϊÿһ���������á��Ƿ���ޡ�����ֵ
		for(int i=0; i<list.size(); i++){
			ReplyView reply = (ReplyView) list.get(i);
			boolean isClickGood = false;
			if(user==null){
				isClickGood = false;
			}else{
				IsReplyClickGoodViewDAO isClickGoodDao = 
						(IsReplyClickGoodViewDAO) context.getBean("IsReplyClickGoodViewDAO");
				IsReplyClickGoodView isClick = 
						isClickGoodDao.findById(user.getUserId()+"_"+reply.getReplyId());
				if(isClick==null){
					isClickGood = false;
				}else{
					isClickGood = true;
				}
			}
			reply.setClickGood(isClickGood);
			sendArray.put(jsonUtil.objectToJson(reply));
		}
		return sendArray;
	}
	
	//TODO ��ȡ¥��¥
	public JSONArray getInnerReply(int essayId,int floorId,int index) throws JSONException, IOException{
		init();
		ReplyViewDAO replyDao = context.getBean("ReplyViewDAO",ReplyViewDAO.class);
		List list = replyDao.findInnerReply(essayId, floorId, index);
		sendArray = jsonUtil.objectToArray(list,false);
		return sendArray;
	}
	
	//TODO ���޺�ȡ���޲���
	public String saveOrDeleteClickGood(HttpServletRequest request,int replyId,boolean isClickGood,String userId){
		ReplyClickGoodDAO clickGoodDao = 
				context.getBean("ReplyClickGoodDAO",ReplyClickGoodDAO.class);
		ReplyClickGood clickGood = new ReplyClickGood();
		ReplyClickGoodId clickGoodId = new ReplyClickGoodId();
		clickGoodId.setReplyId(replyId);
		clickGoodId.setUserId(userId);
		clickGood.setId(clickGoodId);
		//�ѵ��ޣ���ִ��ȡ�����޲���
		if(isClickGood){
			log.debug("ȡ�����޲���");
			clickGoodDao.delete(clickGood);
		}else{
			log.debug("���ӵ��޼�¼");
			clickGoodDao.attachDirty(clickGood);
		}
		return "�ɹ�";
	}
	//�ж�¥���Ƿ���ڣ�ֱ��д��save�����лᷢ�����³���
	public boolean haveFloor(int floorId){
		ReplyViewDAO viewDao = context.getBean("ReplyViewDAO",ReplyViewDAO.class);
		return viewDao.haveFloor(floorId);
	}
	//TODO������һ���µ�����
	public String saveAReply(HttpServletRequest request,int essayId,UserInfo user,String repliedUserId,String repContent,int floorId,boolean boo) throws NoSuchMethodException, SecurityException, JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		ReplyDAO replyDao = context.getBean("ReplyDAOImpl",ReplyDAO.class);
		String userId = user.getUserId();
		if(!boo){
			return "¥��"+floorId+"������";
		}
		Reply reply = new Reply();
		//���ظ����˺�Ϊ#�ż�,˵���Ƿ�����
		if(repliedUserId.equals("#")){
			EssayOuterDAO essayDao = context.getBean("EssayOuterDAO",EssayOuterDAO.class);
			repliedUserId = essayDao.getPublisher(essayId);
		}
		reply.setEssayId(essayId);
		reply.setFloorId(floorId);
		reply.setRepContent(repContent);
		reply.setRepliedUserId(repliedUserId);
		reply.setUserId(userId);
		log.debug("��������");
		Serializable id =  replyDao.save(reply);
		
		EssayDAO essayDao = context.getBean("EssayDAOImpl",EssayDAO.class);
		JSONObject msgJsonStr = new JSONObject();
		msgJsonStr.put("replyId",id);
		msgJsonStr.put("nickname", user.getNickname());
		msgJsonStr.put("floorId", floorId);
		msgJsonStr.put("essayId", essayId);
		msgJsonStr.put("essayTitle", essayDao.findById(essayId).getTitle());
		msgJsonStr.put("userId",userId);
		msgJsonStr.put("avatar",user.getHeadImagePath());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		msgJsonStr.put("replyTime",df.format(new Date()));
		Method m = MyMessageHandler.class.getMethod("sendMyMessage", String.class, int.class,String.class);
		m.invoke(MyMessageHandler.class.newInstance(),repliedUserId,1,msgJsonStr.toString());
		
		return "success";
	}
	
	//TODO��ɾ������
	public String deleteAReply(String userId,int replyId,int position){
		ReplyDAO replyDao = context.getBean("ReplyDAOImpl",ReplyDAO.class);
		Reply reply = replyDao.findById(replyId);
		if(reply==null){
			return "�����۲�����";
		}
		if(!reply.getUserId().equals(userId)){
			return "��Ȩɾ��������";
		}
		if(position==0){
			log.debug("ɾ��һ����¥��");
			replyDao.deleteAFloor(reply.getFloorId(),reply.getEssayId());
		}else{
			replyDao.delete(reply);
		}
		return "success";
	}
	
}

