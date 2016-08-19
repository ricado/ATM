package com.atm.service.bbs;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.atm.chat.nio.server.handler.MyMessageHandler;
import com.atm.dao.bbs.EssayClickDAO;
import com.atm.dao.bbs.EssayDAO;
import com.atm.dao.bbs.EssayPhotoDAO;
import com.atm.dao.bbs.EssayTypeDAO;
import com.atm.dao.bbs.LabelDAO;
import com.atm.dao.bbs.ReplyDAO;
import com.atm.dao.user.DepartmentDAO;
import com.atm.model.bbs.Essay;
import com.atm.model.bbs.EssayClick;
import com.atm.model.bbs.EssayPhoto;
import com.atm.model.bbs.EssayType;
import com.atm.model.bbs.Label;
import com.atm.model.bbs.Reply;
import com.atm.model.user.Department;
import com.atm.model.user.School;
import com.atm.model.user.UserInfo;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO：负责修改帖子，包括：发帖，删帖，点赞
 * @fileName : com.atm.service.EssayChangeDeal.java
 * date | author | version |   
 * 2015年8月6日 | Jiong | 1.0 |
 */
public class EssayChangeDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	
	
	
	//增加阅读数
	public void updateClickNum(int essayId){
		EssayClickDAO essayClickDao = 
					context.getBean("EssayClickDAOImpl",EssayClickDAO.class);
		EssayClick essayClick = new EssayClick();
		essayClick.setEssayId(essayId);
		essayClickDao.attachDirty(essayClick);
	}
	
	//保存一个帖子
	/*
	 * 先将以ID形式存储的字段从数据库中根据获取的名字取出对应ID
	 * 再将ID存入帖子表的pojo对象中
	 * 持久化此对象
	 */
	public String saveAEssay(UserInfo user,String essayType,String labNames,String title,String department,String content,ArrayList imagePath,String aiteID) throws IOException, JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		String mess = "发布失败";
		log.debug("》》》进入发帖方法");
		String userId = user.getUserId();
		String scNo = user.getScNo();
		
		//****************数据操作对象**********************
		EssayDAO essayDao = context.getBean("EssayDAOImpl",EssayDAO.class);
		EssayTypeDAO typeDao = context.getBean("EssayTypeDAOImpl",EssayTypeDAO.class);
		LabelDAO labelDao = context.getBean("LabelDAOImpl",LabelDAO.class);
		DepartmentDAO deptDao = context.getBean("DepartmentDAOImpl",DepartmentDAO.class);
		if(labNames==null||labNames.replace(" ","").length()==0){
			labNames = "未设置标签";
		}
		String[] labName = labNames.split("\\*#");//标签1*#标签2
		
		//TODO************获取对应ID
		int typeId ;
		String labId="" ;
		String dNo;
		
		List typeList = typeDao.findByEssayType(essayType);
		List deptList = deptDao.findByDNameAndScNo(department, scNo);
		//TODO 判断该记录在数据库中是否存在，若不存在则增加一条记录
		if(typeList.size()==0){
			EssayType newType = new EssayType();
			newType.setEssayType(essayType);
			typeDao.save(newType);
			//获取刚刚插入的记录
			typeList = typeDao.findByEssayType(essayType);
		}
		EssayType type = (EssayType) typeList.get(0);//从数组中取出pojo
		
		//多个标签名需要循环
		for(int i=0;i<labName.length; i++){
			List labelList = labelDao.findByLabName(labName[i]);
			if(labelList.size()==0){
				Label newLabel = new Label();
				newLabel.setLabName(labName[i]);
				labelDao.save(newLabel);
				labelList = labelDao.findByLabName(labName[i]);
				log.debug(labName.length+">>>>>>>>>>>>>>>>>>>"+labName[i]);
			}
			Label label = (Label) labelList.get(0);
			if(labId.length()==0){
				labId = labId+label.getLabId();
			}else{	
				labId = labId+","+label.getLabId();
			}
			
		}
		//TODO　此方法最终应删掉
		if(deptList.size()==0){//系别不存在时，考虑直接在数据库中增加一条记录，一般不会出现这种情况
			return "系别："+department+"不存在";
			/*	
			Department newDept = new Department();
			newDept.setDname(department);
			School sch = new School();
			sch.setScNo("10000");
			newDept.setSchool(sch);
			String dn = "";
			try{
			 dn = deptDao.findMaxNo("10000").toString();
			}catch(NullPointerException e){
				dn = "00001";
			}
			newDept.setDno((Integer.valueOf(dn)+1)+"");//主键assigned问题
			deptDao.save(newDept);
			deptList = deptDao.findByDname(department);*/
		}
		Department dept = (Department) deptList.get(0);
		
		//TODO 取出对应字段的ID
		typeId = type.getTypeId();
		dNo = dept.getDno();
		
		//TODO*******************获取并完善essay的pojo对象
		Essay essay = new Essay();
		essay.setTypeId(typeId);
		essay.setLabId(labId);
		essay.setDno(dNo);
		essay.setTitle(title);
		essay.setContent(content);
		essay.setPublisherId(userId);
		
		log.debug(">>>>>>>>>>保存essay");
		Serializable id = essayDao.save(essay);
		
		if(imagePath.size()>0){
			log.debug("保存图片路径");
			EssayPhotoDAO dao = 
					(EssayPhotoDAO) context.getBean("EssayPhotoDAOImpl");
			for(int i=0;i<imagePath.size();i++){
				String path = imagePath.get(i).toString();
				path = path.substring(path.indexOf("WebRoot"));
				EssayPhoto e  = new EssayPhoto();
				e.setEssayId(essay.getEssayId());
				e.setPhotoPath(path);
				dao.save(e);
			}
		}
		
		//到“我的消息”
		String[] ids = aiteID.split("//*#");
		Method m = MyMessageHandler.class.getMethod("sendMyMessage", String.class, int.class,String.class);
		for(String oneId:ids){
			JSONObject msgJsonStr = new JSONObject();
			msgJsonStr.put("essayId",id);
			msgJsonStr.put("nickname", user.getNickname());
			msgJsonStr.put("essayTitle", title);
			msgJsonStr.put("userId",userId);
			msgJsonStr.put("avatar",user.getHeadImagePath());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			msgJsonStr.put("createTime",df.format(new Date()));	
			m.invoke(MyMessageHandler.class.newInstance(),oneId,0,msgJsonStr.toString());
		}
		
		return mess="success";
	}
	//TODO　删除评论
		public String deleteEssay(String userId,int essayId){
			EssayDAO essayDao = context.getBean("EssayDAOImpl",EssayDAO.class);
			Essay essay = essayDao.findById(essayId);
			if(essay==null){
				return "帖子不存在";
			}
			if(!essay.getPublisherId().equals(userId)){
				return "无权删除此帖子";
			}
			essayDao.delete(essay);
			log.debug("删除了帖子："+essayId);
			return "success";
		}
}

