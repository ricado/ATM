package com.atm.util.mail;

public interface EmailContent {
	public abstract String getContent();
	public abstract void setCaptchas(String captchas);
	public abstract void setHello(String hello);
	public abstract String getCaptchas();
}
