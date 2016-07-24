package com.atm.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationUtil {
	public static ApplicationContext getApplicationContext(){
		return new ClassPathXmlApplicationContext("applicationContext.xml");
	}
}
