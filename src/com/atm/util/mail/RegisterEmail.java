package com.atm.util.mail;

public class RegisterEmail implements EmailContent{
	public String theme = "ATMע��������֤";//�����ʾ���ʼ���ԭ��
	public String hello = "Hey,";//�Ѻ��ʺ����
	public String message = " ����������ע��ATM���˺�;��֤����:";//�ʼ��ĺ�������
	public String tip = "�뾡�����ֻ�����д����֤�룬����֤��20��������Ч";	//��ܰ��ʾ
	public String captchas ="";//�������֤�롣�������������֤�롣��֤�뽫�Ӵ�
	
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
