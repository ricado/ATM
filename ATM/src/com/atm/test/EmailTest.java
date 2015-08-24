package com.atm.test;

import com.atm.util.mail.SendDemo;

public class EmailTest {
	 public static void main(String[] args) {
		EmailTest test = new EmailTest();
		try{
			SendDemo demo = new SendDemo();
			//String captchas = demo.sendChangePasswordEmail("15622797401@163.com");
			//System.out.println("changePassword:" + captchas);
			/*SendDemo demo1 = new SendDemo();*/
			String captchas = demo.sendRegisterEmail("15622797401@163.com");
			System.out.println("register:" + captchas);
			/*SendDemo demo2 = new SendDemo();*/
			//captchas = demo.sendFindUserIdEmail("15622797401@163.com","100001");
			//System.out.println(captchas);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
