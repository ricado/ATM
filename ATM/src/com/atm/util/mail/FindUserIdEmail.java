package com.atm.util.mail;

public class FindUserIdEmail implements EmailContent{
	public String theme = "ATM�һ��˺�";//�����ʾ���ʼ���ԭ��
	public String hello = "Hey,";//�Ѻ��ʺ����
	public String message = "����������Ӧ���˺���:";//�ʼ��ĺ�������
	public String tip = "�����μ�ס�˺�Ŷ�㲻Ҫ������";	//��ܰ��ʾ
	public String captchas ="";//�������֤�롣�������������֤�롣��֤�뽫�Ӵ�
	
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
