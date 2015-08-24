package com.atm.service.bbs;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.atm.dao.bbs.EssayClickDAO;
import com.atm.dao.bbs.EssayDAO;
import com.atm.dao.bbs.EssayTypeDAO;
import com.atm.dao.bbs.LabelDAO;
import com.atm.dao.user.DepartmentDAO;
import com.atm.model.bbs.Essay;
import com.atm.model.bbs.EssayClick;
import com.atm.model.bbs.EssayType;
import com.atm.model.bbs.Label;
import com.atm.model.user.Department;
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
					context.getBean("EssayClickDAO",EssayClickDAO.class);
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
	public String saveAEssay(String userId,String essayType,String labNames,String title,String department,String content) throws IOException, JSONException{
		String mess = "发布失败";
		log.debug("》》》进入发帖方法");
		
		
		//****************数据操作对象**********************
		EssayDAO essayDao = context.getBean("EssayDAO",EssayDAO.class);
		EssayTypeDAO typeDao = context.getBean("EssayTypeDAO",EssayTypeDAO.class);
		LabelDAO labelDao = context.getBean("LabelDAO",LabelDAO.class);
		DepartmentDAO deptDao = context.getBean("DepartmentDAO",DepartmentDAO.class);
		
		String[] labName = labNames.split("\\*#");//标签1*#标签2
		
		//TODO************获取对应ID
		int typeId ;
		String labId="" ;
		String dNo;
		
		List typeList = typeDao.findByEssayType(essayType);
		List deptList = deptDao.findByDname(department);
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
		if(deptList.size()==0){//系别不存在时，考虑直接在数据库中增加一条记录，一般不会出现这种情况
			Department newDept = new Department();
			newDept.setDname(department);
			newDept.setDno("11111");//主键assigned问题,由触发器改变
			deptDao.save(newDept);
			deptList = deptDao.findByDname(department);
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
		essayDao.save(essay);
		return mess="成功";
	}
}

