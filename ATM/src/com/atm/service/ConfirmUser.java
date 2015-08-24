package com.atm.service;

import org.apache.struts2.ServletActionContext;

import com.atm.dao.impl.user.StudentDAOImpl;
import com.atm.dao.impl.user.TeacherDAOImpl;
import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
/**
 * 忘记密码时，通过输入账号或者邮箱并且验证其正确性
 * @author ye
 * @2015.07.29
 */
public class ConfirmUser {
	//验证账号
	public boolean findById(String userId){
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		if(dao.findById(userId)!=null){
			return true;
		}
		return false;
	}
	//验证邮箱
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