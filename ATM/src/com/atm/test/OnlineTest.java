package com.atm.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.atm.service.OnlineService;

public class OnlineTest {
	public OnlineService service = new OnlineService();

	public static void main(String[] args) {
		OnlineTest test = new OnlineTest();
		test.getLoginList();
		// test.delete();

		// test.getUserList();
		
		//test.getLoginMaxRecord();
		
		test.setTest();
	}

	public OnlineTest() {
	}

	/*
	 * public void getLoginList(){ OnlineService service = new OnlineService();
	 * String repon = service.getLoginList(); System.out.println(repon); }
	 */

	public void getUserList() {
		OnlineService service = new OnlineService();
		service.getUserList(0, 20);
	}

	public void getLoginList() {
		OnlineService service = new OnlineService();
		service.getLoginList(0, 20);
	}

	public void login() {
		OnlineService.saveLogin("10001", 1);
		OnlineService.saveLogin("10002", 1);
		OnlineService.saveLogin("10003", 1);
	}

	public void delete() {
		OnlineService.deleteLogin("10001", 1);
		OnlineService.deleteLogin("10002", 1);
		OnlineService.deleteLogin("10003", 1);
	}

	public void update() {
		OnlineService.updateLogin("10001", 1);
		OnlineService.updateLogin("10002", 1);
		OnlineService.updateLogin("10003", 1);
	}

	public void getLoginMaxRecord() {
		int max = OnlineService.getLoginMaxRecord();
		System.out.println(max + "");
	}

	public void setTest(){
		Set<String> str = new HashSet<String>();
		str.add("aaa");
		str.add("bbb");
		str.add("ccc");
		if(str.remove("aaa")){
			System.out.println("remove aaa");
		}else{
			System.out.println("remove error");
		}
		if(str.remove("aaa")){
			System.out.println("remove aaa");
		}else{
			System.out.println("remove error");
		}			
	}
}
