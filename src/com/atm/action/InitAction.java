package com.atm.action;

import java.io.IOException;

import com.atm.chat.nio.server.NIOServer;

public class InitAction {
	public String runServer() throws IOException{
		new NIOServer();
		return null;
	}
}
