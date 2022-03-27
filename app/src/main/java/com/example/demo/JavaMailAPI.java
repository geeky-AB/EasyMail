package com.example.demo;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void,Void,Void>{
        private Context context;

        private Session session;
        private String email,subject, content, senderEmail, pass;
        public JavaMailAPI(Context context, String email, String subject, String content, String senderEmail, String pass) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.senderEmail = senderEmail;
        this.pass = pass;
    }

        @Override
        protected Void doInBackground(Void... voids) {
                Properties properties = new Properties();

                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.port", "465");

                session = javax.mail.Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication(){
                                return new PasswordAuthentication(senderEmail,pass);
                        }
                });

                Message message = new MimeMessage(session);

                try {
                        message.setFrom(new InternetAddress(senderEmail));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                        message.setSubject(subject);
                        message.setText(content);
                        Transport.send(message);
                } catch (MessagingException e) {
                        e.printStackTrace();
                }
                return null;
        }
}
