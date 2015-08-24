package com.atm.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static Timestamp getTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	public static String getDateFormat(Timestamp timestamp){
		String format = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(timestamp.getTime()));
		return format;
	}
	
	public static String getCurrentDateFormat(){
		String format = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		return format;
	}
	
	public static String getFileTimeFormat(){
		String format = 
				new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		return format;
	}
	
	public static String getDateFormat(){
		String format = 
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return format;
	}
	
}
