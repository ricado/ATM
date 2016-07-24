package com.atm.service.bbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;

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
					context.getBean("EssayClickDAOImpl",EssayClickDAO.class);
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
	public String saveAEssay(UserInfo user,String essayType,String labNames,String title,String department,String content,ArrayList imagePath) throws IOException, JSONException{
		String mess = "����ʧ��";
		log.debug("���������뷢������");
		String userId = user.getUserId();
		String scNo = user.getScNo();
		
		//****************���ݲ�������**********************
		EssayDAO essayDao = context.getBean("EssayDAOImpl",EssayDAO.class);
		EssayTypeDAO typeDao = context.getBean("EssayTypeDAOImpl",EssayTypeDAO.class);
		LabelDAO labelDao = context.getBean("LabelDAOImpl",LabelDAO.class);
		DepartmentDAO deptDao = context.getBean("DepartmentDAOImpl",DepartmentDAO.class);
		if(labNames==null||labNames.replace(" ","").length()==0){
			labNames = "δ���ñ�ǩ";
		}
		String[] labName = labNames.split("\\*#");//��ǩ1*#��ǩ2
		
		//TODO************��ȡ��ӦID
		int typeId ;
		String labId="" ;
		String dNo;
		
		List typeList = typeDao.findByEssayType(essayType);
		List deptList = deptDao.findByDNameAndScNo(department, scNo);
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
		//TODO���˷�������Ӧɾ��
		if(deptList.size()==0){//ϵ�𲻴���ʱ������ֱ�������ݿ�������һ����¼��һ�㲻������������
			return "ϵ��"+department+"������";
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
			newDept.setDno((Integer.valueOf(dn)+1)+"");//����assigned����
			deptDao.save(newDept);
			deptList = deptDao.findByDname(department);*/
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
		
		if(imagePath.size()>0){
			log.debug("����ͼƬ·��");
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
		return mess="success";
	}
	//TODO��ɾ������
		public String deleteEssay(String userId,int essayId){
			EssayDAO essayDao = context.getBean("EssayDAOImpl",EssayDAO.class);
			Essay essay = essayDao.findById(essayId);
			if(essay==null){
				return "���Ӳ�����";
			}
			if(!essay.getPublisherId().equals(userId)){
				return "��Ȩɾ��������";
			}
			essayDao.delete(essay);
			log.debug("ɾ�������ӣ�"+essayId);
			return "success";
		}
}

