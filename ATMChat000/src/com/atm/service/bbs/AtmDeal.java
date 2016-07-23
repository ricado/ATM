package com.atm.service.bbs;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.atm.dao.user.DepartmentDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.daoDefined.bbs.EssayOuterDAO;
import com.atm.daoDefined.bbs.LabelViewDAO;
import com.atm.util.bbs.ObjectInterface;

/**
 * @TODO������
 * @fileName : com.atm.service.AtmDeal.java
 * date | author | version |   
 * 2015��8��14�� | Jiong | 1.0 |
 */
public class AtmDeal implements ObjectInterface{
	Logger log = Logger.getLogger(getClass()); 
	public JSONArray getDeptList(String scNo) throws JSONException, IOException{
		DepartmentDAO deptDao = context.getBean("DepartmentDAOImpl",DepartmentDAO.class);
		List list = deptDao.findDeptList(scNo);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	//��ȡ���ű�ǩ
	public String getHotLabel() throws JSONException, IOException{
		LabelViewDAO labelDao = 
				context.getBean("LabelViewDAO",LabelViewDAO.class);
		List list = labelDao.findHotLabel();
		return list.toString();
	}
	
	//�����û�
	public JSONArray searchPeople(String userName) throws JSONException, IOException{
		UserInfoDAO userInfoDao = 
				context.getBean("UserInfoDAO",UserInfoDAO.class);
		List list = userInfoDao.searchPeople(userName);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
	
	//����ĳ���ؼ��ʵ�����
	public JSONArray searchEssayByKey(String key,int index) throws JSONException, IOException{
		EssayOuterDAO essayOuterDao = 
				context.getBean("EssayOuterDAO",EssayOuterDAO.class);
		List list = essayOuterDao.searchPeople(key,index);
		if(list.size()==0){
			return null;
		}
		return jsonUtil.objectToArray(list,false);
	}
}

