package com.atm.chat.nio.server.handler;


public class SendHandler extends BufferHandler{
	
	public SendHandler(){}
	
	public void sendOffLineMessage(String userId) throws Exception{
		MessageHandler handler = new MessageHandler();
		handler.setSocketChannel(socketChannel);
		handler.sendOffLineMessage(userId,socketChannel);
	}
}
