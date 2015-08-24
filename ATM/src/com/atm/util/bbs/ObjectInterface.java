package com.atm.util.bbs;
/**
 * ���峣�ö���
 * @author Jiong
 * @���� 2015��7��26
 * 
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface ObjectInterface {
	public static final ApplicationContext context = 
			new ClassPathXmlApplicationContext("applicationContext.xml");
	public static final JsonUtil jsonUtil= (JsonUtil) context.getBean("JsonUtil");
	public static final SendUtil sendUtil = (SendUtil) context.getBean("SendUtil");
	public static final CommonUtil commonUtil = (CommonUtil) context.getBean("CommonUtil");
	
	public static String errorJson = "{'tip':'δ֪����'}";
	public static String errorArray = "[{'tip':'δ֪����'}]";
}
