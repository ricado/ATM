package com.atm.util.mail;

/**
 * 忘记密码的邮件HTML模版
 * @author ye
 * @2015.8.4
 */
public class ChangePasswordEmail implements EmailContent{
	public String theme = "ATM修改密码邮箱验证";//主题表示发邮件的原因
	public String hello = "Hey,";//友好问候语句
	public String message = " 修改密码邮箱验证，验证码为:";//邮件的核心内容
	public String tip = "请尽快在手机上填写该验证码，该验证码20分钟内有效";	//温馨提示
	public String captchas ="";//如果有验证码。则可以用来放验证码。验证码将加粗
	
	public ChangePasswordEmail() {
		// TODO Auto-generated constructor stub
		captchas = Captchas.getCaptchas(); 
	}
	
	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return "<html>"
				+ "<head></head>"
				+ "<body>"
				+ "<h2>" + theme + "</h2>"
			    + "<p>"
		    	+ "<font size='6PX'>&nbsp;&nbsp;"+hello+"</font></h3><br>"
		    	+ "&nbsp;&nbsp;" + message + "<b>"+ captchas + "</b>;<br>"
		    	+ "&nbsp;&nbsp;" +tip
		    	+"</p>"
				+ "</body>"
				+"</html>";
	}

	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getHello() {
		return hello;
	}
	
	@Override
	public void setHello(String hello) {
		this.hello = hello;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
	public String getCaptchas() {
		return captchas;
	}

	@Override
	public void setCaptchas(String captchas) {
		this.captchas = captchas;
	}
}
