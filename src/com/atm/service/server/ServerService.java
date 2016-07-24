package com.atm.service.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.NIOServer;

/**
 * 用与tomcat开启调用ListenAction。开启socke服务器
 * 
 * @author ye 2015.8.25
 */
public class ServerService {
	private static final Logger log = LoggerFactory
			.getLogger(ServerService.class);

	public void openServer() throws IOException {
		log.debug("开启使用server.....");
		new NIOServer();
	}
}
