package com.atm.util.mail;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;


/**
 * 简单邮件发送器
 * @author ye
 *
 */
public class MailSender {
	/**
	 * 发送邮件的props文件
	 */
	 private final transient Properties props = System.getProperties();
	 /**
	  * 邮件服务器登录验证
	  */
	 private transient MyAuthenticator authenticator;
	 /**
	  * 邮箱Session	
	  */
	 private transient Session session;
	 
	 /**
	  * 初始化发送服务器
	  * @param smtpHostName smtp主机地址
	  * @param username
	  * @param password
	  */
	 public MailSender(){
		 init("1169833934@qq.com",Password.p,"smtp.qq.com"); 
	 }
	 
	 /**
	  * 初始化发送服务器
	  * @param smtpHostName smtp主机地址
	  * @param username
	  * @param password
	  */
	 public MailSender(final String smtpHostName,final String username,
			 final String password){
		 init(username,password,smtpHostName); 
	 }
	 /**
	  * 初始化发送服务器
	  * @param username
	  * @param password
	  */
	 public MailSender(String username,String password){
		final String smtpHostName = "smtp." + username.split("@")[1]; 
	    init(username, password, smtpHostName);
	 }
	 /**
	  * 
	  * 初始化
	  * @param username
	  * @param password
	  * @param smtpHostName
	  */
	 public void init(String username,String password,String smtpHostName){
		 //初始化props
		 props.put("mail.smtp.auth", true);
		 props.put("mail.smtp.host", smtpHostName);
		 //验证
		 authenticator = new MyAuthenticator();
		 //创建Session
		 session = Session.getInstance(props, authenticator);
		 
	 }
	 /**
	  * 发送邮件
	  * @param recipient 收件人邮箱地址
	  * @param subject   邮箱主题
	  * @param content   邮件内容
	  * @throws AddressException
	  * @throws MessagingException
	  */
	 public void send(String recipient,String subject,Object content)
			 throws AddressException, MessagingException{
		//创建mime类型邮件
		 final MimeMessage message = new MimeMessage(session);
		 //设置发信人
		 message.setFrom(new InternetAddress(authenticator.getUsername()));
		 //设置收件人
		 message.setRecipient(RecipientType.TO,
				 new InternetAddress(recipient));
		 //设置主题
		 message.setSubject(subject);
		//设置时间
		message.setSentDate(Calendar.getInstance().getTime());
		 //设置邮件内容
		 message.setContent((Multipart) content);
		 //发送
		 Transport.send(message);
	 }
	 /**
	  * 群发邮件
	  * @param recipient
	  * @param subject
	  * @param content
	  * @throws AddressException
	  * @throws MessagingException
	  */
	 public void send(List<String> recipient,String subject,Multipart content) 
			 throws AddressException, MessagingException{
		//创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		//设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		//设置收件人们
		final int num = recipient.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipient.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		//设置主题
		message.setSubject(subject);
		//设置时间
		message.setSentDate(Calendar.getInstance().getTime());
		//设置邮件内容
		message.setContent(content);
		//发送
		Transport.send(message);
	 }
	 //
	 public void send(MyAuthenticator authenticator) throws MessagingException{
		//创建mime类型邮件
		 final MimeMessage message = new MimeMessage(session);
		 //设置发信人
		 message.setFrom(new InternetAddress(authenticator.getUsername()));
		 //设置收件人
		 message.setRecipient(RecipientType.TO,
				 new InternetAddress(authenticator.getRecipient()));
		 //设置主题
		 message.setSubject(authenticator.getSubject());
		//设置时间
		message.setSentDate(Calendar.getInstance().getTime());
		 //设置邮件内容
		Multipart content = makeFile(authenticator.getFilePaths(),authenticator.getContent());
		 message.setContent(content);
		 //发送
		 Transport.send(message); 
	 }
	 public Multipart makeFile(String[] filePaths,String content) throws MessagingException{
		//MimeMutltipart是一个容器类，包含Min   eBodyPart类型
			Multipart multipart = new MimeMultipart(); 
			//创建一个包含附件内容的MimeBodyPart
			MimeBodyPart bodyPart = new MimeBodyPart();
			//设置Html内容
			bodyPart.setContent(content, "text/html;charset=utf-8");
			multipart.addBodyPart(bodyPart);
			if(filePaths != null && filePaths.length>0){
				for(String filePath:filePaths){
					bodyPart = new MimeBodyPart();
					File file = new File(filePath);
					System.out.println("=====检查--"+filePath+"========");
					if(file.exists()){
						System.out.println("=====检查--success========");
						//得到数据源
						DataSource fileDataSource = new FileDataSource(file);
						//得到附件身并至如bodyPart
						bodyPart.setDataHandler(new DataHandler(fileDataSource));
						//得到文件名置入bodyPart
						bodyPart.setFileName(file.getName());
						System.out.println("========="+file.getName()+"========");
						multipart.addBodyPart(bodyPart);
					}
				}
			}
			return multipart;
	 }
	 
	public MyAuthenticator getAuthenticator() {
		return authenticator;
	}
	public void setAuthenticator(MyAuthenticator authenticator) {
		this.authenticator = authenticator;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Properties getProps() {
		return props;
	}
}
