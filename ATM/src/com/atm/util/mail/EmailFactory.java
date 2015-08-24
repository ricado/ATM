package com.atm.util.mail;

/**
 * 产生不同邮箱模版的工厂类
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
