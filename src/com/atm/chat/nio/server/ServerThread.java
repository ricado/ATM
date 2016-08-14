package com.atm.chat.nio.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerThread extends Thread {
	private static Logger log = LoggerFactory.getLogger(ServerThread.class);
	private NIOServer server = null;

	public ServerThread() {
	}

	public void run() {
		try {
			log.info("开启serverThread --run");
			server = new NIOServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("开启失败");
			e.printStackTrace();
		}
	}

}
