package com.atm.util.mail;

/**
 * ������ͬ����ģ��Ĺ�����
 * @author ye
 *
 */
public class EmailFactory {
	
	public static EmailContent getRegisterEmail(){
		return new RegisterEmail();
	}
	public static EmailContent getFindUserIdEmail(){
		return new FindUserIdEmail();
	}
	public static EmailContent getChangePasswordEmail(){
		return new ChangePasswordEmail();
	}
}
