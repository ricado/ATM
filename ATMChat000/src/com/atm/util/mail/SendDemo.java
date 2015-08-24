package com.atm.util.mail;



public class SendDemo {
	public MyAuthenticator authenticator = new MyAuthenticator();
	//public String email = "550260496@qq.com";
	public String email = "15622797401@163.com";
	public SendDemo(){}
	
	public String send(){
		MailSender sender = new MailSender();
		System.out.println("send.................");
		try{
			sender.send(authenticator);
			System.out.println("sucess===============");
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}	
	}
	
	/**
	 * �����޸��������֤��
	 * @param email
	 * @return
	 */
	public String sendChangePasswordEmail(String email){
		EmailContent content = EmailFactory.getChangePasswordEmail();
		content.setHello("Hey," + email);
		System.out.println(content.getContent());
		setAuthenticator(email,content.getContent(),"ATM�޸�����������֤");
		send();
		return content.getCaptchas();
	}
	
	/**
	 * ����ע���������֤��
	 * @param email
	 * @return
	 */
	public String sendRegisterEmail(String email){
		EmailContent content = EmailFactory.getRegisterEmail();
		content.setHello("Hey," + email);
		System.out.println(content.getContent());
		setAuthenticator(email,content.getContent(),"ATMע��������֤");
		send();
		return content.getCaptchas();
	}
	
	/**
	 * �����һ��˺ŵ��ʼ�
	 * @param email
	 * @param userId
	 * @return
	 */
	public String sendFindUserIdEmail(String email,String userId){
		EmailContent content = EmailFactory.getFindUserIdEmail();
		content.setHello("Hey," + email);
		content.setCaptchas(userId);
		System.out.println(content.getContent());
		setAuthenticator(email,content.getContent(),"ATM�һ��˺�������֤");
		send();
		return null;
	}
	
	
	public void setAuthenticator(String email,String content,String subject){
		authenticator.setRecipient(email);
		authenticator.setContent(content);
		authenticator.setFilePaths(null);
		authenticator.setSubject(subject);
	}
	/*public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}*/
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
