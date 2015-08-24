package com.atm.service.bbs;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @TODO：
 * @fileName : com.atm.service.ReplyDeal.java
 * date | author | version |   
 * 2015年8月11日 | Jiong | 1.0 |
 */
public class ReplyDeal   implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	
	private JSONArray sendArray;
	
	//初始化
	private void init(){
		sendArray = new JSONArray();
	}
	
	//TODO 获取楼层，first 为-1时获取一条热门评论，其他时则为普通起始位置查询
	public JSONArray getReply(HttpServletRequest request,int essayId,int first) throws JSONException, IOException{
		init();
		ReplyViewDAO replyDao = context.getBean("ReplyViewDAO",ReplyViewDAO.class);
		List list;
		//根据起始位置决定怎么获取结果集
		if(first==-1){
			list = replyDao.findHotReply(essayId);
		}else{
			list = replyDao.findByEssayId(essayId,first);
		}
		//获取不到结果，返回评论总数
		if(list.size()==0){
			return new JSONArray().put(new JSONObject().put("num", replyDao.getReplyNum(essayId)));
		}
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		//循环为每一个对象设置“是否点赞”属性值
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
	
	//TODO 获取楼中楼
	public JSONArray getInnerReply(int essayId,int floorId,int index) throws JSONException, IOException{
		init();
		ReplyViewDAO replyDao = context.getBean("ReplyViewDAO",ReplyViewDAO.class);
		List list = replyDao.findInnerReply(essayId, floorId, index);
		sendArray = jsonUtil.objectToArray(list,false);
		return sendArray;
	}
	
	//TODO 点赞和取消赞操作
	public String saveOrDeleteClickGood(HttpServletRequest request,int replyId,boolean isClickGood,String userId){
		ReplyClickGoodDAO clickGoodDao = 
				context.getBean("ReplyClickGoodDAO",ReplyClickGoodDAO.class);
		ReplyClickGood clickGood = new ReplyClickGood();
		ReplyClickGoodId clickGoodId = new ReplyClickGoodId();
		clickGoodId.setReplyId(replyId);
		clickGoodId.setUserId(userId);
		clickGood.setId(clickGoodId);
		//已点赞，则执行取消点赞操作
		if(isClickGood){
			log.debug("取消点赞操作");
			clickGoodDao.delete(clickGood);
		}else{
			log.debug("增加点赞记录");
			clickGoodDao.attachDirty(clickGood);
		}
		return "成功";
	}
	//判断楼层是否存在，直接写在save方法中会发生更新出错
	public boolean haveFloor(int floorId){
		ReplyViewDAO viewDao = context.getBean("ReplyViewDAO",ReplyViewDAO.class);
		return viewDao.haveFloor(floorId);
	}
	//TODO　发布一条新的评论
	public String saveAReply(HttpServletRequest request,int essayId,String userId,String repliedUserId,String repContent,int floorId,boolean boo){
		ReplyDAO replyDao = context.getBean("ReplyDAO",ReplyDAO.class);
		if(!boo){
			return "楼层"+floorId+"不存在";
		}
		Reply reply = new Reply();
		//被回复者账号为#号键,说明是发帖者
		if(repliedUserId.equals("#")){
			EssayOuterDAO essayDao = context.getBean("EssayOuterDAO",EssayOuterDAO.class);
			repliedUserId = essayDao.getPublisher(essayId);
		}
		reply.setEssayId(essayId);
		reply.setFloorId(floorId);
		reply.setRepContent(jsonUtil.changeString(repContent));
		reply.setRepliedUserId(repliedUserId);
		reply.setUserId(userId);
	
		replyDao.save(reply);
		return "success";
	}
	
	//TODO　删除评论
	public String deleteAReply(String userId,int replyId,int position){
		ReplyDAO replyDao = context.getBean("ReplyDAO",ReplyDAO.class);
		Reply reply = replyDao.findById(replyId);
		if(reply==null){
			return "该评论不存在";
		}
		if(!reply.getUserId().equals(userId)){
			return "无权删除此评论";
		}
		if(position==0){
			log.debug("删除一整个楼层");
			replyDao.deleteAFloor(reply.getFloorId(),reply.getEssayId());
		}else{
			replyDao.delete(reply);
		}
		return "success";
	}
	
}

