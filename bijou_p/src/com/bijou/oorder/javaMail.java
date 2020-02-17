package com.bijou.oorder;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class javaMail {
	final String user = "bijou_team@naver.com";
	final String password = "bijou1234!";
	
	
	public void javaMail() {
		System.out.println("===== mail send... =====");
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.naver.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
				
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			// 수신자 메일 주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("bijou_team@naver.com"));
			
			// 메일 제목
			message.setSubject("New 주문");
			// 메일 내용
			message.setText("새로운 주문이 발생했습니다.\n"
					+ "http://localhost:8090/bijou/member/login_go.do");
			
			// 메일 전송
			Transport.send(message);
			System.out.println("===== Success Mail Send =====");
			
		} catch (Exception e) {
			System.out.println("Java Mail 전송 오류");
		}	
			
	}		
					
	
}
