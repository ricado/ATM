package com.atm.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.model.user.UserInfo;
import com.atm.service.ApplyService;
import com.atm.util.bbs.ObjectInterface;

public class ApplyAction implements ObjectInterface{
	private static final Logger log = Logger.getLogger(ApplyAction.class);
	private HttpServletRequest request = getRequest();
	private HttpServletResponse response = getResponse();
	
	 private Integer apInfoId;
     private Integer workId;
     private String publisherId;
     private String telephone;
     private String personalInfo;
     
     private String expectSalary;
     private String reTypeName;
     private String woTypeName;
   //客户端传入相关参数
 	private Integer page;//查询帖子起始位置
 	private String tip; //查询条件
	
	
	private ApplyService applyService = (ApplyService) context.getBean("ApplyService");
	
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
	
	public String save(){
		//json = applyService.saveApplay(json);
		//setJson();
		return null;
	}
	//TODO 发布帖子
	public void publish(){
		log.debug("发布求职贴请求。。。");
		init();
		try {
			//获取用户登陆信息
			UserInfo user = (UserInfo) getRequest().getSession().getAttribute("user");
			if(user==null){
				mess = "未登录";
				send("apply");
				return;
			}
			publisherId = user.getUserId();
			if(apInfoId == null){
				apInfoId = 0;
			}
			if(apInfoId>0){
				log.debug("修改操作");
			}
			mess = applyService.saveApply(apInfoId,reTypeName, woTypeName,expectSalary, telephone, personalInfo, publisherId);
		} catch(Exception e){
			mess = "发生错误";
			log.error(mess, e);
		}
		send("apply");
	}
	
	public String update(){
		//json = applyService.updateApply(json);
		//setJson();
		return null;
	}
	
	public String delete(){
		//json = applyService.deleteApply(json);
		//setJson();
		return null;
	}
	
	public String getApply(){
		init();
		if(page==null){
			log.debug("没有接收page,默认0");
			page = 0;
		}
	/*	if(tip!=null)
			tip = jsonUtil.changeString(tip);*/
		try {
			log.debug("求职贴条件:"+tip);
			resultArray = applyService.findList(tip,page);
		} catch (Exception e1) {
			mess = "获取异常";
		} 
		mess = "success";
		send("apply");
		return null;
	}
	//TODO 进入帖子
	public String detail(){
		log.debug("进入求职帖"+apInfoId);
		String mess = applyService.applyDetail(request,apInfoId);
		if(mess.equals("success")){
			log.debug("阅读+1");
			applyService.saveClick(apInfoId);
		}
		return mess;
	}
	public String findApply(){
		//json = applyService.findByApplyId(json);
		//setJson();
		return null;
	}
	

	public Integer getApInfoId() {
		return apInfoId;
	}

	public void setApInfoId(Integer apInfoId) {
		this.apInfoId = apInfoId;
	}


	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getExpectSalary() {
		return expectSalary;
	}

	public void setExpectSalary(String expectSalary) {
		this.expectSalary = expectSalary;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(String personalInfo) {
		this.personalInfo = personalInfo;
	}

	public ApplyService getApplyService() {
		return applyService;
	}

	public void setApplyService(ApplyService applyService) {
		this.applyService = applyService;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
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
}
