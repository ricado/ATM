package com.atm.action.BBS;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.EssayChangeDeal;
import com.atm.service.bbs.EssayDeal;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

public class EssayAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,ObjectInterface {
	Logger log = Logger.getLogger(getClass());
	
	private String ids; //批量id
	private int essayId;
	private int first = 0;//起始位置
	private int page = 0;//帖子列表已显示帖子数
	private String id;
	private int rows = 0;
	
	private boolean clickGood;//是否点赞
	private boolean collect;//是否收藏
	
	private String deptName;//查找系别帖子
	
	private String tag; //标签帖子
	
	//发帖参数
	private String type;
	private String title;
	private String content;
	private String label;
	private String department;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	EssayDeal deal = //只读相关操作
			(EssayDeal) context.getBean("EssayDeal");
	EssayChangeDeal changeDeal = //更新保存相关操作
			context.getBean("EssayChangeDeal",EssayChangeDeal.class);
	
	//mess:给用户的提示信息-----sendJson:装载mess------resultArray:装载帖子
	String mess;
	JSONObject sendJson;
	JSONArray resultArray;
	
	//初始化
	private void init(){
		mess = "获取失败";
		sendJson  = new JSONObject();
		resultArray = new JSONArray();
	}
	//检查有没有获取到帖子
	private void check(){
		if(resultArray == null){
			mess = "没有帖子";
			resultArray = new JSONArray();//重新初始化
		}else{
			String tip="success";
			try{
				tip = (String) resultArray.getJSONObject(0).get("tip");
				resultArray = new JSONArray();
			}catch(JSONException e){
				
			}
			mess = tip;
		}
	}
	//发送结果
	private void send(){
		send("bbs");
	}
	private void send(String name){
		try {
			sendJson.put("tip", mess);
			sendJson.put(name,resultArray);
		} catch (JSONException e) {
			sendUtil.writeToClient(response, errorJson);
			log.error(e);
		}
		sendUtil.writeToClient(response,sendJson);
	}
	
	//TODO*****************跳转****************************
	
	int hotNum = 5;
	//跳转获取主界面帖子,及上拉刷新
	public String mainEssay(){
		log.debug("获取主界面帖子请求");
		/*try{
			Cookie[] cookies = request.getCookies();
			for(int i=0;i<cookies.length;i++){
				System.out.println("cookis:"+cookies[i].getName());
				System.out.println("comm"+cookies[i].getComment());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}*/
		init();
		try {
			
			int index = getPage();//取出客户端传过来的帖子位置
			log.debug(">>>>>>>>>>>>>第"+index+"条");
			if(index==0){
				resultArray = deal.getMainEssay();
				int length = resultArray.length();
				if(length>=10){
					hotNum = 5;
				}else{
					hotNum = length/2;
				}
			}else{
				index = index - hotNum;//索引减去3，因为获取了3条热门帖
				if(index<0){
					index = 0;
				}
				resultArray = deal.getTenEssay(request,index);
			}
			check();
		} catch(Exception e){
			mess = "获取帖子发生错误";
			log.error(mess, e);
		}
		send();
		return null;
	}
	
	public String deptEssay(){
		log.debug("获取院系帖子请求");
		init();
		//获取用户登陆信息
		/*UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "用户未登陆";
			send();
			return null;
		}*/
		int index = getPage();//取出客户端传过来的帖子位置
		try {
			log.debug(">>>>>>>>>>>>>>>>>>>>获取系别帖子"+getId());
			resultArray = deal.getDeptEssay(request, getId(), index);
			check();
		} catch(Exception e){
			mess = "获取帖子发生错误";
			log.error(mess, e);
		}
		send();
		return null;
	}
	
	//获取关注贴（标签，人）
	public String attendEssay(){
		log.debug("获取关注相关的帖子请求");
		init();
		//获取用户登陆信息
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "未登录";
			send();
			return null;
		}
		String userId = user.getUserId();
		try {
			int index = getPage();//取出客户端传过来的帖子位置
			log.debug(">>>>>>>>>>>>>>>>>>>>获取关注帖子"+userId);
			resultArray = deal.getAttendEssay(request, userId, index);
			check();
		} catch(Exception e){
			mess = "获取帖子发生错误";
			log.error(mess, e);
		}
		send();
		return null;
	}
	
	//TODO 获取某一标签的帖子
	public void tagEssay(){
		init();
		try {
			log.debug(tag+":"+page);
			resultArray = deal.tagEssay(tag, page);
			if(resultArray==null){
				mess = "标签:"+tag+" 不存在";
			}else{
				mess = "success";
			}
		} catch(Exception e){
			mess = "获取帖子发生错误";
			log.error(mess, e);
		}
		send();
	}
	
	//跳转获取十条最新贴
	/*
	 * 需要客户端传输参数：index:用户刷新处位置
	 */
	/*public String tenEssay(){
		init();
		try {
			resultArray = deal.getTenEssay(request);
			check();
		} catch (Exception e) {
			mess = "获取发生错误";
			log.error(e);
		} 
		send();
		return null;
	}*/
	
	
	public void userEssay(){
		log.debug("获取用户的一条发过及收藏的帖子");
		init();
		//获取用户登陆信息
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "未登录";
			send();
			return;
		}
		try{
			resultArray = deal.getUserEssay(user.getUserId());
			check();
		}catch(Exception e){
			mess = "获取发生错误";
			log.error(e);
		}
		send();
		log.debug("用户帖子方法结束");
	}
	//跳转获取用户收藏的帖子
	/*
	 * 需要客户端传入参数：page:查询位置
	 */
	public String collectedEssay(){
		log.debug("获取收藏的帖子请求page:"+getPage());
		init();
		//获取用户登陆信息
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess = "未登录";
			send();
			return null;
		}
		try{
			int index = getPage();//取出客户端传过来的帖子位置
			resultArray = deal.getCollectedEssay(request,user.getUserId(),index);
			check();
		}catch(Exception e){
			mess = "获取发生错误";
			log.error(e);
		}
		send();
		log.debug("收藏方法结束");
		return null;
	}
	
	//跳转获取用户评论的帖子
		/*
		 * 需要客户端传入参数：page,rows
		 */
		public String repliedEssay(){
			log.debug("获取评论的帖子请求page:"+getPage()+":rows:"+getRows());
			init();
			//获取用户登陆信息
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if(user==null){
				mess = "未登录";
				send();
				return null;
			}
			try{
				resultArray = deal.getRepliedEssay(user.getUserId(),page,rows);
				check();
			}catch(Exception e){
				mess = "获取发生错误";
				log.error(e);
			}
			send();
			log.debug("方法结束");
			return null;
		}
	
	//跳转用户发布的帖子
	/*
	 * 需要客户端传入参数：index:查询位置
	 */
	public String publishedEssay(){
		log.debug("获取发布的帖子请求");
		init();
		String userId = id;
		//若id为null，则是获取用户自己发布的帖子，否则是获取账号为id的用户帖子
		log.debug("用户帖子:"+id);
		if(id==null||id.length()==0){
			//获取用户登陆信息
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if(user==null){
				mess = "用户未登陆";
				send();
				return null;
			}
			userId = user.getUserId();
		}
		try{
			int index = getPage();//取出客户端传过来的帖子位置
			resultArray = deal.getPublishedEssay(request,userId,index);
			check();
			
		}catch(Exception e){
			mess = "获取发生错误";
			log.error(e);
		}
		send();
		return null;
	}
	//客户端帖子详情相关操作(获取安卓控件显示的属性）
	public String content(){
		log.debug("获取帖子详情控件参数请求");
		String mess = "失败";
		try{
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			String userId = null;
			if(user!=null){
				userId = user.getUserId();
			}
			JSONObject sendObject = deal.getContent(request,essayId,userId);
			sendUtil.writeToClient(response, sendObject);
		}catch(Exception e){
			log.debug("获取参数错误",e);
			sendUtil.writeToClient(response, errorJson);
		}
			return null;
	}
	//跳转进入帖子详情(网页）
	/*
	 * 需要传入参数：essayId:帖子编号
	 */
	public String detail(){
		log.debug("获取帖子详情页面请求");
		try{
			log.debug("进入detail");
			String boo = deal.essayDetail(request,getEssayId());
			if(boo==null){
				return ERROR;
			}else if(boo.equals("success")){
				log.debug("获取帖子成功，进行跳转"+getEssayId());
				log.debug("阅读量增加1");
				changeDeal.updateClickNum(getEssayId());
				return "essaySuccess";
			}else{
				return ERROR;
			}
		}catch(Exception e){
			log.error(e);
			return ERROR;
		}
	}
	
	//跳转到点赞者列表页面
	public String clickGoodPeople(){
		log.debug("获取点赞者列表请求");
		try{
			log.debug("获取点赞者,index:"+getFirst());
			deal.getClickGoodPeople(request, getEssayId(),0);
			return "clickGoodPeople";
		}catch(Exception e){
			log.error("获取点赞者错误",e);
			return ERROR;
		}
	}
	//获取更多点赞者
	public void moreClickGoodPeople(){
	try{
		log.debug("获取更多点赞者"+getEssayId()+":"+getFirst());
		JSONArray resultArray = deal.getMorePeople(getEssayId(), getFirst());
		sendUtil.writeToClient(response, resultArray);	
		}catch(Exception e){
			log.error("获取更多点赞者错误", e);
			sendUtil.writeToClient(response,"[]");
		}
	}
	
	//TODO 点赞方法
	public void clickGood(){
		log.debug("获取点赞请求");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "未登录";
			send();
		}else{
			try {
				mess = deal.saveOrDeleteClickGood(request, getEssayId(), isClickGood(),user.getUserId());
				send();
			} catch (Exception e) {
				mess = "获取错误";
				log.debug(mess, e);
				send();
			} 
		}
	}
	//TODO 收藏方法
	public void collectEssay(){
		log.debug("获取收藏请求");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "未登录";
			send();
		}else{
			try {
				mess = deal.saveOrDeleteCollect(request, getEssayId(), isCollect(),user.getUserId());
				send();
			} catch (Exception e) {
				mess = "获取错误";
				log.debug(mess, e);
				send();
			} 
		}
	}
	
	//TODO 批量收藏方法
	public void collectBatch(){
		log.debug("获取批量收藏请求");
		init();
		UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
		if(user==null){
			mess = "未登录";
			send();
			return;
		}
		try {
			if(ids==null){
				mess = "未选择收藏的帖子";
				send("delEssay");
				return;
			}
			String[] idList = ids.split(",");
			String tempMess = null;
			for(String oneId:idList){
				mess = deal.saveOrDeleteCollect(request, Integer.valueOf(oneId.trim()), isCollect(),user.getUserId());
				tempMess = mess.equals("success")?tempMess:mess;
			}
			mess = tempMess==null?mess:tempMess;
			send();
		} catch (Exception e) {
			mess = "获取错误："+e.getMessage();
			log.debug(mess, e);
			send();
		} 
		
	}
	
	//TODO***********************修改相关的操作
	
	//发布帖子
	public String publishEssay(){
		log.debug("获取发布帖子请求");
		init();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		if(user==null){
			mess="未登录";
			send();
			return null;
		}
		try{
			mess = changeDeal.saveAEssay(user, type, label, title, department, content,new ArrayList());
		}catch(Exception e){
			mess = "未知错误,发布失败";
			log.error(mess, e);
		}
		send();
		return null;
	}
	//TODO 删除一条帖子
		public void deleteEssay(){
			log.debug("获取批量删除帖子请求"+essayId);
			init();
			UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
			if(user==null){
				mess = "未登录";	
				send("delEssay");
				return;
			}
			try {
				mess = changeDeal.deleteEssay(user.getUserId(), essayId);
				log.debug("删除结束"+mess);
			} catch (Exception e) {
				mess = "获取错误";
				log.debug(mess, e);
			} 
			send("delEssay");
		}
		
		//TODO 批量删除帖子
		public void deleteBatch(){
			log.debug("获取删除帖子请求"+ids);
			init();
			UserInfo user = (UserInfo) request.getSession(true).getAttribute("user");
			if(user==null){
				mess = "未登录";	
				send("delEssay");
				return;
			}
			try {
				if(ids==null){
					mess = "未选择删除的帖子";
					send("delEssay");
					return;
				}
				String[] idList = ids.split(",");
				String errorMess = null;
				for(String oneId:idList){
					mess = changeDeal.deleteEssay(user.getUserId(), Integer.valueOf(oneId.trim()));
					log.debug("----------删除一条帖子（"+oneId+"："+mess+")");
					if(!mess.equals("success")){
						errorMess = mess;
					}
				}
				mess = errorMess==null?mess:errorMess;
				log.debug("删除结束"+mess);
			} catch (Exception e) {
				mess = "获取错误"+e.getMessage();
				log.debug(mess, e);
			} 
			send("delEssay");
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isClickGood() {
		return clickGood;
	}
	public void setClickGood(boolean clickGood) {
		this.clickGood = clickGood;
	}
	public boolean isCollect() {
		return collect;
	}
	public void setCollect(boolean collect) {
		this.collect = collect;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	

}
