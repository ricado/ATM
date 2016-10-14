package com.atm.action;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.ServerThread;

public class ListenAction implements ServletContextListener {
	private static final Logger log = LoggerFactory
			.getLogger(ListenAction.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		log.debug("开始执行初始化方法....");
		Thread thread = new ServerThread();
		thread.start();
		log.debug("开启成功。。。。");
	}

}
