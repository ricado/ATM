package com.atm.service.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.chat.nio.server.NIOServer;

/**
 * ����tomcat��������ListenAction������socke������
 * 
 * @author ye 2015.8.25
 */
public class ServerService {
	private static final Logger log = LoggerFactory
			.getLogger(ServerService.class);

	public void openServer() throws IOException {
		log.debug("����ʹ��server.....");
		new NIOServer();
	}
}
