package com.atm.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LogText {
	private static Logger logger = LoggerFactory.getLogger(LogText.class);
	public static void main(String[] args) {
		LogText test = new LogText();
		test.log();
	}
	public void log(){
		logger.debug("this is a debug");
		logger.info("this is a info");
		logger.error("this is a error");	
	}
}
