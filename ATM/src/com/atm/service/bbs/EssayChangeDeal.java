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
 * @TODO�������޸����ӣ�������������ɾ��������
 * @fileName : com.atm.service.EssayChangeDeal.java
 * date | author | version |   
 * 2015��8��6�� | Jiong | 1.0 |
 */
public class EssayChangeDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass());
	
	
	
	//�����Ķ���
	public void updateClickNum(int essayId){
		EssayClickDAO essayClickDao = 
					context.getBean("EssayClickDAO",EssayClickDAO.class);
		EssayClick essayClick = new EssayClick();
		essayClick.setEssayId(essayId);
		essayClickDao.attachDirty(essayClick);
	}
	
	//����һ������
	/*
	 * �Ƚ���ID��ʽ�洢���ֶδ����ݿ��и��ݻ�ȡ������ȡ����ӦID
	 * �ٽ�ID�������ӱ��pojo������
	 * �־û��˶���
	 */
	public String saveAEssay(String userId,String essayType,String labNames,String title,String department,String content) throws IOException, JSONException{
		String mess = "����ʧ��";
		log.debug("���������뷢������");
		
		
		//****************���ݲ�������**********************
		EssayDAO essayDao = context.getBean("EssayDAO",EssayDAO.class);
		EssayTypeDAO typeDao = context.getBean("EssayTypeDAO",EssayTypeDAO.class);
		LabelDAO labelDao = context.getBean("LabelDAO",LabelDAO.class);
		DepartmentDAO deptDao = context.getBean("DepartmentDAO",DepartmentDAO.class);
		
		String[] labName = labNames.split("\\*#");//��ǩ1*#��ǩ2
		
		//TODO************��ȡ��ӦID
		int typeId ;
		String labId="" ;
		String dNo;
		
		List typeList = typeDao.findByEssayType(essayType);
		List deptList = deptDao.findByDname(department);
		//TODO �жϸü�¼�����ݿ����Ƿ���ڣ���������������һ����¼
		if(typeList.size()==0){
			EssayType newType = new EssayType();
			newType.setEssayType(essayType);
			typeDao.save(newType);
			//��ȡ�ող���ļ�¼
			typeList = typeDao.findByEssayType(essayType);
		}
		EssayType type = (EssayType) typeList.get(0);//��������ȡ��pojo
		
		//�����ǩ����Ҫѭ��
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
		if(deptList.size()==0){//ϵ�𲻴���ʱ������ֱ�������ݿ�������һ����¼��һ�㲻������������
			Department newDept = new Department();
			newDept.setDname(department);
			newDept.setDno("11111");//����assigned����,�ɴ������ı�
			deptDao.save(newDept);
			deptList = deptDao.findByDname(department);
		}
		Department dept = (Department) deptList.get(0);
		
		//TODO ȡ����Ӧ�ֶε�ID
		typeId = type.getTypeId();
		dNo = dept.getDno();
		
		//TODO*******************��ȡ������essay��pojo����
		Essay essay = new Essay();
		essay.setTypeId(typeId);
		essay.setLabId(labId);
		essay.setDno(dNo);
		essay.setTitle(title);
		essay.setContent(content);
		essay.setPublisherId(userId);
		
		log.debug(">>>>>>>>>>����essay");
		essayDao.save(essay);
		return mess="�ɹ�";
	}
}

