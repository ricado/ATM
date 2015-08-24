package com.atm.util.mail;

public class Captchas {
	public static String getCaptchas(){
		int num = 100000 + (int)(Math.random()*1000000);
		return num+"";
	}
	public static void main(String[] args) {
		System.out.println(getCaptchas());
	}
}
