package com.atm.action;

/**
 * 修改：2015、9、19
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.bbs.RecuitDeal;
import com.atm.util.bbs.ObjectInterface;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 招聘
 * @author ye
 * @2015.08.03
 */
public class RecuitAction extends ActionSupport implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	public HttpServletRequest request = getRequest();
	public HttpServletResponse response = getResponse();
	
	//招聘帖参数
	private Integer reInfoId;//帖子ID
	private String reTypeName; //招聘类型
	private String woTypeName;//工作类型
	private String workAddress;//工作地点
	private String salary;//薪资
	private String telephone;//联系电话
	private String reContent;//招聘内容
	private String publisherId;//发布者账号
	
	//客户端传入相关参数
	private Integer page;//查询帖子起始位置
	private String tip; //查询条件
	//***注：tip为recuit时做发布招聘帖，为apply做发布求职贴
	
	RecuitDeal deal = (RecuitDeal) context.getBean("RecuitDeal");
	//mess:给用户的提示信息-----sendJson:装载mess和result------resultArray:装载帖子
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
				}catch(JSONException e){
					
				}
				mess = tip;
			}
		}
		//发送结果
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
	
	//***********************跳转****************************
		//TODO 发布帖子
	public void publish(){
		log.debug("发布招聘贴请求。。。");
		init();
		try {
			//获取用户登陆信息
			UserInfo user = (UserInfo) getRequest().getSession().getAttribute("user");
			if(user==null){
				mess = "未登录";
				send("recuit");
				return;
			}
			publisherId = user.getUserId();
			if(reInfoId == null){
				reInfoId = 0;
			}
			mess = deal.saveRecuit(reInfoId,reTypeName, woTypeName, workAddress, salary, telephone, reContent, publisherId);
		} catch(Exception e){
			mess = "发生错误";
			log.error(mess, e);
		}
		send("recuit");
	}
	
	//TODO 获取帖子
	public void getRecuit(){
		log.debug("获取招聘贴请求。。。");
		init();
		try{
			if(page==null){
				log.debug("默认page0");
				page = 0;
			}
			/*if(tip!=null)
				tip = jsonUtil.changeString(tip);*/
			log.debug(tip+":"+page+"....");
			resultArray = deal.recuitList(page,tip);
			check();
		}catch(Exception e){
			mess = "获取帖子发生错误";
			log.error(mess, e);
		}
		send("recuit");
	}
	//TODO　删除帖子
	public void delete(){
		log.debug("获取删除帖子请求。。");
		init();
		try{
			//获取用户登陆信息
			UserInfo user = (UserInfo) getRequest().getSession().getAttribute("user");
			if(user==null){
				mess = "未登录";
				send("recuit");
				return;
			}
			mess = deal.deleteRecuit(user.getUserId(), reInfoId);
		}catch(Exception e){
			mess = "获取帖子发生错误";
			log.error(mess, e);
		}
		send("recuit");
	}
	
	//TODO 进入帖子
	public String detail(){
		log.debug("进入招聘帖"+reInfoId);
		String mess = deal.recuitDetail(request, reInfoId);
		if(mess.equals("success")){
			log.debug("阅读+1");
			deal.saveClick(reInfoId);
		}
		return mess;
	}
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public int getReInfoId() {
		return reInfoId;
	}
	public void setReInfoId(Integer reInfoId) {
		this.reInfoId = reInfoId;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getReContent() {
		return reContent;
	}
	public void setReContent(String reContent) {
		this.reContent = reContent;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherID) {
		this.publisherId = publisherID;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getReTypeName() {
		return reTypeName;
	}
	public void setReTypeName(String reTypeName) {
		this.reTypeName = reTypeName;
	}
	public String getWoTypeName() {
		return woTypeName;
	}
	public void setWoTypeName(String woTypeName) {
		this.woTypeName = woTypeName;
	}
	public Integer getPage() {
		
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	

	
	
}
