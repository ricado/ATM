package com.atm.util.mail;

import java.sql.Date;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * 服务器邮箱验证
 * @author ye
 *
 */
public class MyAuthenticator extends Authenticator {

	private String username = "1169833934@qq.com";//发件人
	private String password = Password.p;//邮箱密码
	private String recipient;//收件人
	private String smtpHostName = "smtp.qq.com";//smtp
	private String subject;//主题
	//private Date datetime;//时间
	private String content;//内容
	private String[] filePaths;//附件路径
	/**
	 * 初始化
	 * @param username
	 * @param password
	 */
	public MyAuthenticator(){
		
	}
	
	@Override
	 protected PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication(username, password);
	    }
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSmtpHostName() {
		return smtpHostName;
	}

	public void setSmtpHostName(String smtpHostName) {
		this.smtpHostName = smtpHostName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(String[] filePaths) {
		this.filePaths = filePaths;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/*public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}*/

}
