package com.atm.test;

import com.atm.service.MessageService;

public class MessageTest {
	public static void main(String[] args) {
		//getCrowdOffRecord();
		getPrivate();
	}
	public static void getCrowdOffRecord(){
		MessageService.getOffLineCrowdMessage("10001");
	}
	
	public static void getPrivate(){
		MessageService.getOffLineMessage("10001");
	}
}
