package com.atm.service;

import org.apache.struts2.ServletActionContext;

import com.atm.dao.impl.user.StudentDAOImpl;
import com.atm.dao.impl.user.TeacherDAOImpl;
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
/**
 * ��������ʱ��ͨ�������˺Ż������䲢����֤����ȷ��
 * @author ye
 * @2015.07.29
 */
public class ConfirmUser {
	//��֤�˺�
	public boolean findById(String userId){
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		if(dao.findById(userId)!=null){
			return true;
		}
		return false;
	}
	//��֤����
	public boolean findByEmail(String email){
		StudentDAO dao = StudentDAOImpl.getFromApplicationContext();
		if(dao.findByEmail(email).size()==1){
			return true;
		}else{
			TeacherDAO teacherDao = TeacherDAOImpl.getFromApplicationContext();
			if(teacherDao.findByEmail(email).size()==1){
				return true;
			}
		}
		return false;
	}
}