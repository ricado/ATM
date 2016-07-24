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
 * ���ʼ�������
 * @author ye
 *
 */
public class MailSender {
	/**
	 * �����ʼ���props�ļ�
	 */
	 private final transient Properties props = System.getProperties();
	 /**
	  * �ʼ���������¼��֤
	  */
	 private transient MyAuthenticator authenticator;
	 /**
	  * ����Session	
	  */
	 private transient Session session;
	 
	 /**
	  * ��ʼ�����ͷ�����
	  * @param smtpHostName smtp������ַ
	  * @param username
	  * @param password
	  */
	 public MailSender(){
		 init("1169833934@qq.com",Password.p,"smtp.qq.com"); 
	 }
	 
	 /**
	  * ��ʼ�����ͷ�����
	  * @param smtpHostName smtp������ַ
	  * @param username
	  * @param password
	  */
	 public MailSender(final String smtpHostName,final String username,
			 final String password){
		 init(username,password,smtpHostName); 
	 }
	 /**
	  * ��ʼ�����ͷ�����
	  * @param username
	  * @param password
	  */
	 public MailSender(String username,String password){
		final String smtpHostName = "smtp." + username.split("@")[1]; 
	    init(username, password, smtpHostName);
	 }
	 /**
	  * 
	  * ��ʼ��
	  * @param username
	  * @param password
	  * @param smtpHostName
	  */
	 public void init(String username,String password,String smtpHostName){
		 //��ʼ��props
		 props.put("mail.smtp.auth", true);
		 props.put("mail.smtp.host", smtpHostName);
		 //��֤
		 authenticator = new MyAuthenticator();
		 //����Session
		 session = Session.getInstance(props, authenticator);
		 
	 }
	 /**
	  * �����ʼ�
	  * @param recipient �ռ��������ַ
	  * @param subject   ��������
	  * @param content   �ʼ�����
	  * @throws AddressException
	  * @throws MessagingException
	  */
	 public void send(String recipient,String subject,Object content)
			 throws AddressException, MessagingException{
		//����mime�����ʼ�
		 final MimeMessage message = new MimeMessage(session);
		 //���÷�����
		 message.setFrom(new InternetAddress(authenticator.getUsername()));
		 //�����ռ���
		 message.setRecipient(RecipientType.TO,
				 new InternetAddress(recipient));
		 //��������
		 message.setSubject(subject);
		//����ʱ��
		message.setSentDate(Calendar.getInstance().getTime());
		 //�����ʼ�����
		 message.setContent((Multipart) content);
		 //����
		 Transport.send(message);
	 }
	 /**
	  * Ⱥ���ʼ�
	  * @param recipient
	  * @param subject
	  * @param content
	  * @throws AddressException
	  * @throws MessagingException
	  */
	 public void send(List<String> recipient,String subject,Multipart content) 
			 throws AddressException, MessagingException{
		//����mime�����ʼ�
		final MimeMessage message = new MimeMessage(session);
		//���÷�����
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		//�����ռ�����
		final int num = recipient.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipient.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		//��������
		message.setSubject(subject);
		//����ʱ��
		message.setSentDate(Calendar.getInstance().getTime());
		//�����ʼ�����
		message.setContent(content);
		//����
		Transport.send(message);
	 }
	 //
	 public void send(MyAuthenticator authenticator) throws MessagingException{
		//����mime�����ʼ�
		 final MimeMessage message = new MimeMessage(session);
		 //���÷�����
		 message.setFrom(new InternetAddress(authenticator.getUsername()));
		 //�����ռ���
		 message.setRecipient(RecipientType.TO,
				 new InternetAddress(authenticator.getRecipient()));
		 //��������
		 message.setSubject(authenticator.getSubject());
		//����ʱ��
		message.setSentDate(Calendar.getInstance().getTime());
		 //�����ʼ�����
		Multipart content = makeFile(authenticator.getFilePaths(),authenticator.getContent());
		 message.setContent(content);
		 //����
		 Transport.send(message); 
	 }
	 public Multipart makeFile(String[] filePaths,String content) throws MessagingException{
		//MimeMutltipart��һ�������࣬����Min   eBodyPart����
			Multipart multipart = new MimeMultipart(); 
			//����һ�������������ݵ�MimeBodyPart
			MimeBodyPart bodyPart = new MimeBodyPart();
			//����Html����
			bodyPart.setContent(content, "text/html;charset=utf-8");
			multipart.addBodyPart(bodyPart);
			if(filePaths != null && filePaths.length>0){
				for(String filePath:filePaths){
					bodyPart = new MimeBodyPart();
					File file = new File(filePath);
					System.out.println("=====���--"+filePath+"========");
					if(file.exists()){
						System.out.println("=====���--success========");
						//�õ�����Դ
						DataSource fileDataSource = new FileDataSource(file);
						//�õ�����������bodyPart
						bodyPart.setDataHandler(new DataHandler(fileDataSource));
						//�õ��ļ�������bodyPart
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
