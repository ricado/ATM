package com.atm.test;

import com.atm.service.crowd.CrowdMenberService;

public class MenberTest {
	private CrowdMenberService service = new CrowdMenberService();
	public static void main(String[] args) {
		MenberTest test = new MenberTest();
		//test.findAllMenber();
		test.addManager();
		test.removeManager();
	}
	public MenberTest() {
		// TODO Auto-generated constructor stub
	}
	public void findAllMenber(){
		service.findAllMenber(10001);
	}
	/**
	 * 设置管理员
	 */
	public void addManager(){
		service.addManager("10005", 10003);
	}
	/**
	 * 取消管理员
	 */
	public void removeManager(){
		service.cancelManager("10005", 10002);
	}
}
