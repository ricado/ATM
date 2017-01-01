/**
 * 
 */
package com.atm.service.bbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.dao.PeopleAttentionAssociationDAO;
import com.atm.dao.bbs.ClickGoodDAO;
import com.atm.dao.bbs.CollectEssayDAO;
import com.atm.dao.bbs.EssayDAO;
import com.atm.dao.bbs.EssayPhotoDAO;
import com.atm.dao.bbs.LabelAttentionAssociationDAO;
import com.atm.dao.user.DepartmentDAO;
import com.atm.daoDefined.bbs.ClickGoodViewDAO;
import com.atm.daoDefined.bbs.CollectEssayViewDAO;
import com.atm.daoDefined.bbs.EssayDetailViewDAO;
import com.atm.daoDefined.bbs.EssayOuterDAO;
import com.atm.daoDefined.bbs.LabelViewDAO;
import com.atm.model.PeopleAttentionAssociation;
import com.atm.model.bbs.ClickGood;
import com.atm.model.bbs.ClickGoodId;
import com.atm.model.bbs.CollectEssay;
import com.atm.model.bbs.CollectEssayId;
import com.atm.model.bbs.EssayPhoto;
import com.atm.model.bbs.LabelAttentionAssociation;
import com.atm.model.define.bbs.ClickGoodView;
import com.atm.model.define.bbs.CollectEssayView;
import com.atm.model.define.bbs.EssayDetailView;
import com.atm.model.define.bbs.EssayOuter;
import com.atm.model.define.bbs.LabelView;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO：帖子逻辑业务处理
 * @fileName : com.atm.service.EssayDeal.java
 * date | author | version |   
 * 2015年7月30日 | Jiong | 1.0 |
 */
public class EssayDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	private JSONArray sendArray;
	
	//初始化
	private void init(){
		sendArray = new JSONArray();
	}
	
	
	//TODO***********************
	/*
	 * 获取主页面显示的帖子(热门，最新)
	 */
	public JSONArray getMainEssay() throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		
		//获取热门贴
		List<EssayOuter> list = essayOuterDao.getSomeEssay(0,5,"replyNum");
		if(list.size()==0){//取不到值
			return null;
		}
		list.get(0).setFlag("hot");
		
		
		String hotEssay = jsonUtil.objectToArray(list,false).toString();
		
		//获取最新贴
		list = essayOuterDao.getSomeEssay(0,10,"publishTime");;
		list.get(0).setFlag("new");
		String currentEssay = jsonUtil.objectToArray(list,false).toString();
		//合并两个array
		String essay = hotEssay+currentEssay;
		essay = essay.replace("][", ",");
		return new JSONArray(essay);
	}
	
	public JSONArray getHotEssay(int index) throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		
		//获取热门贴
		List<EssayOuter> list = essayOuterDao.getSomeEssay(index,10,"replyNum");
		if(list.size()==0){//取不到值
			return null;
		}
		return jsonUtil.objectToArray(list);
		
	}
	
	//TODO 获取十条从用户刷新处(index)算起的最新贴
	public JSONArray getTenEssay(HttpServletRequest request,int index) throws IOException, JSONException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		if(index<0){
			index = 0;
		}
		List list = essayOuterDao.getCurrentEssay(index);//从第index处取十条最新帖子
		if(list.size()==0){ //获取不到帖子了，返回null
			return null;
		}
		return jsonUtil.objectToArray(list);
	}
	
	
	//TODO 获取系别贴
	public JSONArray getDeptEssay(HttpServletRequest request,String dNo,int index) throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		DepartmentDAO deptDao = 
				context.getBean("DepartmentDAOImpl",DepartmentDAO.class);
		
		/*List deptList = (List) deptDao.findById(dNo);
		if(deptList.size()==0){
			return new JSONArray("[{'tip':'找不到该系别'}]");
		}
		*/
		//String dNo = ((Department) deptList.get(0)).getDno();
		if(index<0){
			index = 0;
		}
		
		List list = essayOuterDao.getDeptEssay(index,dNo);//从第first处取十条最新帖子
		return jsonUtil.objectToArray(list,false);
	}
	
	
	//TODO 获取关注人和标签的贴
	public JSONArray getAttendEssay(HttpServletRequest request,String userId,int index) throws JSONException, IOException{
		PeopleAttentionAssociationDAO attendDao = 
				context.getBean("PeopleAttentionAssociationDAOImpl",PeopleAttentionAssociationDAO.class);
		EssayOuterDAO essayDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		LabelAttentionAssociationDAO labelAttDao = 
				context.getBean("LabelAttentionAssociationDAOImpl",LabelAttentionAssociationDAO.class);
		boolean haveSomething = false;
		
		log.debug("获取关注者账号集合"+userId);
		List userList = attendDao.findByAttendUserId(userId);
		List<String> ids = new ArrayList<String>();
		if(userList.size()>0){
			haveSomething = true;	
		}
		//取出关注关系中的被关注者
		for(int i=0;i<userList.size();i++){
			String aId = ((PeopleAttentionAssociation)userList.get(i)).getUserAttendedId();
			ids.add(aId);
		}
		log.debug("被关注者查询条件"+ids.toString());
		
		log.debug("获取关注的标签编号集合");
		List labelList = labelAttDao.findByUserId(userId);
		if(labelList.size()>0){
			haveSomething = true;
		}
		if(!haveSomething){
			return new JSONArray("[{'tip':'未关注他人或标签'}]");
		}
		//将获取的标签关系取出标签ID来凑成sql查询的条件
		String labelCondition = "";
		for(int i=0;i<labelList.size();i++){
			labelCondition += "FIND_IN_SET('"+((LabelAttentionAssociation)labelList.get(i)).getLabId()+"',labId)";
			if(i<labelList.size()-1){
				labelCondition += " or ";
			}
		}
		log.debug("标签查询条件："+labelCondition);
		
		if(index<0){
			index = 0;
		}
		log.debug("获取关注人和标签的帖子");
		List list = essayDao.getAttendEssay(index, ids,labelCondition);
		if(list.size()==0){
			return null;
		}
		//删除不需要发给客户端的属性
		for(int i=0;i<list.size();i++){
			((EssayOuter) list.get(i)).setFullContent(null);
			((EssayOuter) list.get(i)).setDepartment(null);
			((EssayOuter) list.get(i)).setdNo(null);
			((EssayOuter) list.get(i)).setLabId(null);
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//TODO 获取标签帖子
	public JSONArray tagEssay(String tagName,int index) throws JSONException, IOException{
		EssayOuterDAO essayDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		LabelViewDAO labelDao = 
				(LabelViewDAO) context.getBean("LabelViewDAO");
		List labelList = labelDao.findByLabName(tagName);
		if(labelList.size()==0){
			return null;
		}
		LabelView label = (LabelView) labelList.get(0);
		String labelCondition = 
				"FIND_IN_SET('"+label.getLabId()+"',labId)";
		List list = essayDao.getAttendEssay(index, null,labelCondition);
		//删除不需要发给客户端的属性
		for(int i=0;i<list.size();i++){
			((EssayOuter) list.get(i)).setFullContent(null);
			((EssayOuter) list.get(i)).setDepartment(null);
			((EssayOuter) list.get(i)).setdNo(null);
			((EssayOuter) list.get(i)).setLabId(null);
		}
		return jsonUtil.objectToArray(list,false);
		
	}
	
	//TODO 获取用户发布的和收藏的一条帖子
	public JSONArray getUserEssay(String userId) throws JSONException, IOException{
		init();
		CollectEssayViewDAO collectEssayDao =
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		
		List list1 = collectEssayDao.findByProperty("userId",userId, 0, 1);
		int collectNum = collectEssayDao.getCollectEssayNum(userId);
		List list2 = essayOuterDao.getPublishedEssay(userId,0,1);
		int publishNum = essayOuterDao.getPublishedEssayNum(userId);
		
		if(list1.size()!=0){//0处存放收藏贴
			sendArray.put(0,jsonUtil.objectToJson(list1.get(0),false).put("num",collectNum));
		}
		if(list2.size()!=0){//1处存放发布贴
			sendArray.put(1,jsonUtil.objectToJson(list2.get(0),false).put("num", publishNum));
		}
		
		return sendArray;
		
	}
	
	//TODO 获取用户最新收藏帖子及帖子数
	public JSONObject getLastCollectedEssay(String userId) throws JSONException, IOException{
		CollectEssayViewDAO collectEssayDao =
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		java.util.Map<String,String> map = collectEssayDao.getLastCollectedEssay(userId);
		if(map==null){
			JSONObject obj = new JSONObject();
			obj.put("collectNum","0");
			return obj;
		}
		return jsonUtil.objectToJson(map);
	}
	//TODO 获取用户最新发布的帖子及帖子数
	public JSONObject getLastPublishedEssay(String userId) throws IOException, JSONException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		java.util.Map<String,String> map = essayOuterDao.getLastPublishedEssay(userId);
		
		if(map==null){
			JSONObject obj = new JSONObject();
			obj.put("publishNum","0");
			return obj;
		}
		return jsonUtil.objectToJson(map);
	}
	
	//TODO  获取用户（userId)收藏的帖子
	public JSONArray getCollectedEssay(HttpServletRequest request,String userId,int index) throws IOException, JSONException {
		init();
		CollectEssayViewDAO collectEssayDao =
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		List list = collectEssayDao.findByUserId(userId,index);
		
		if(list.size()==0){ //获取不到帖子了，返回null
			return null;
		}
		
		
		return jsonUtil.objectToArray(list,false);
	}
	
	/*
	 //TODO  获取用户发布的帖子
	 * 传入参数：essayId：帖子编号--------userId:用户账号
	 */
	public JSONArray getPublishedEssay(HttpServletRequest request,String userId,int index) throws IOException, JSONException{
		init();
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		List list = essayOuterDao.getPublishedEssay(userId, index,10);
		
		if(list.size()==0){ //获取不到帖子了，返回null
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//TODO 用户评论过的帖子
	public JSONArray getRepliedEssay(String userId,int page,int rows) throws JSONException, IOException{
		init();
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		List<String> list = essayOuterDao.getRepliedEssay(userId, rows, page);
		if(list.size()==0){ //获取不到帖子了，返回null
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//TODO　客户端进入帖子详情后需要参数
	public JSONObject getContent(HttpServletRequest request,int essayId,String userId) throws JSONException, IOException{
		EssayDetailViewDAO essayDetailDao =
				context.getBean("EssayDetailViewDAO",EssayDetailViewDAO.class);
		ClickGoodViewDAO clickGoodDao = 
				context.getBean("ClickGoodViewDAO",ClickGoodViewDAO.class);
		CollectEssayViewDAO collectEssayDao = 
				context.getBean("CollectEssayViewDAO",CollectEssayViewDAO.class);
		log.debug("获取帖子："+essayId);
		EssayDetailView essayDetail = essayDetailDao.findSomeValue(essayId);
		if(essayDetail==null){//获取不到帖子
			return null;
		}
		log.debug(">>>"+jsonUtil.objectToJson(essayDetail));
		//查询是否已点赞
		boolean isClickGood = false;
		//查询是否已收藏
		boolean isCollect = false;
		if(userId!=null){
			log.debug("验证用户与本帖关系");
			ClickGoodView clickGood = clickGoodDao.findById(userId+"_"+essayId);
			if(clickGood!=null){
				isClickGood = true;
			}	
			CollectEssayView collectEssay= collectEssayDao.findById(userId+"_"+essayId);
			if(collectEssay!=null){
				isCollect = true;
			}
		}
		essayDetail.setClickGood(isClickGood);
		essayDetail.setCollect(isCollect);
		System.out.println(">>>>>>>>>>"+jsonUtil.objectToJson(essayDetail, false));
		return jsonUtil.objectToJson(essayDetail, false);
	}
	/*
	 //TODO 进入帖子详情
	 * 传入参数：essayId：帖子编号
	 * read-only = false;
	 */
	public String essayDetail(HttpServletRequest request,int essayId) throws IOException, JSONException{

		EssayDetailViewDAO essayDetailDao =
				context.getBean("EssayDetailViewDAO",EssayDetailViewDAO.class);	
		//获取帖子详细内容
		EssayDetailView essayDetail = essayDetailDao.findById(essayId);
		if(essayDetail==null){//获取不到帖子
			return null;
		}
		
		EssayPhotoDAO dao = 
				(EssayPhotoDAO) context.getBean("EssayPhotoDAOImpl");
		
	
		List pathList  = dao.findByProperty("essayId", essayId);
		log.debug("文件路径数量:"+pathList.size());
		//List temp = new ArrayList();
		String photoPath = "";
		if(pathList.size()>0){
			//将实体中的路径属性取出，取代实体
			for(int i=0;i<pathList.size();i++){
				EssayPhoto p = (EssayPhoto) pathList.get(i);
				if(i==0){
					photoPath = p.getPhotoPath();
				}else{
					photoPath = photoPath+"*#"+p.getPhotoPath();
				}
				//pathList.remove(i);
				//pathList.add(i,p.getPhotoPath());
			}
			log.debug("保存图片路径到request");
			request.setAttribute("photoPath", photoPath.replaceAll("\\\\", "/"));
		}
		
		log.debug("保存帖子信息到request");
		request.setAttribute("essayBean", essayDetail);
		
		log.debug("EssayDeal方法结束");
		
		return "success";
	}
 
	/*
	 * 获取帖子点赞者
	 */
	public String getClickGoodPeople(HttpServletRequest request,int essayId,int first){
		ClickGoodViewDAO clickGoodDao = 
				context.getBean("ClickGoodViewDAO",ClickGoodViewDAO.class);
		List list = clickGoodDao.findByEssayId(essayId,first);
		request.setAttribute("clickGoodPeople", list);
		return "success";
	}
	/*
	 * 更多点赞者
	 */
	public JSONArray getMorePeople(int essayId,int first) throws JSONException, IOException{
		ClickGoodViewDAO clickGoodDao = 
				context.getBean("ClickGoodViewDAO",ClickGoodViewDAO.class);
		List list = clickGoodDao.findByEssayId(essayId,first);
		return jsonUtil.objectToArray(list);
	}
	//TODO 点赞和取消赞操作
	public String saveOrDeleteClickGood(HttpServletRequest request,int essayId,boolean isClickGood,String userId){
		ClickGoodDAO clickGoodDao = 
				context.getBean("ClickGoodDAOImpl",ClickGoodDAO.class);
		ClickGood clickGood = new ClickGood();
		ClickGoodId clickGoodId = new ClickGoodId();
		clickGoodId.setEssayId(essayId);
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
		return "success";
	}
	//TODO 收藏和取消收藏操作
		public String saveOrDeleteCollect(HttpServletRequest request,int essayId,boolean isCollect,String userId){
			CollectEssayDAO collectDao = 
					context.getBean("CollectEssayDAOImpl",CollectEssayDAO.class);
			CollectEssay collectEssay = new CollectEssay();
			CollectEssayId collectEssayId = new CollectEssayId();
			collectEssayId.setEssayId(essayId);
			collectEssayId.setUserId(userId);
			collectEssay.setId(collectEssayId);
			//已收藏，则执行取消收藏操作
			if(isCollect){
				log.debug("取消收藏操作");
				collectDao.delete(collectEssay);
			}else{
				log.debug("增加收藏记录");
				collectDao.attachDirty(collectEssay);
			}
			return "success";
		}
}

