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
	 * 发送修改密码的验证吗
	 * @param email
	 * @return
	 */
	public String sendChangePasswordEmail(String email){
		EmailContent content = EmailFactory.getChangePasswordEmail();
		content.setHello("Hey," + email);
		System.out.println(content.getContent());
		setAuthenticator(email,content.getContent(),"ATM修改密码邮箱验证");
		send();
		return content.getCaptchas();
	}
	
	/**
	 * 发送注册的邮箱验证码
	 * @param email
	 * @return
	 */
	public String sendRegisterEmail(String email){
		EmailContent content = EmailFactory.getRegisterEmail();
		content.setHello("Hey," + email);
		System.out.println(content.getContent());
		setAuthenticator(email,content.getContent(),"ATM注册邮箱验证");
		send();
		return content.getCaptchas();
	}
	
	/**
	 * 发送找回账号的邮件
	 * @param email
	 * @param userId
	 * @return
	 */
	public String sendFindUserIdEmail(String email,String userId){
		EmailContent content = EmailFactory.getFindUserIdEmail();
		content.setHello("Hey," + email);
		content.setCaptchas(userId);
		System.out.println(content.getContent());
		setAuthenticator(email,content.getContent(),"ATM找回账号邮箱验证");
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
