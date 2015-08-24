package com.atm.util.mail;

public class RegisterEmail implements EmailContent{
	public String theme = "ATM注册邮箱验证";//主题表示发邮件的原因
	public String hello = "Hey,";//友好问候语句
	public String message = " 你正在申请注册ATM的账号;验证码是:";//邮件的核心内容
	public String tip = "请尽快在手机上填写该验证码，该验证码20分钟内有效";	//温馨提示
	public String captchas ="";//如果有验证码。则可以用来放验证码。验证码将加粗
	
	public RegisterEmail() {
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
