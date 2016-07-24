package com.atm.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface Application {
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("applicationContext.xml");
}
