package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Api_mail {
    public void mail(String mail_em, String mdps, String mail_dest) throws Exception {
        String host = "smtp.gmail.com";
        String username = mail_em;
        String password = mdps;

        // Set mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Create a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        // Create a new message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mail_em));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mail_dest));
        msg.setSubject(" Email");
        msg.setText("y'a une réponse à votre question");

        // Send the message
        Transport.send(msg);
        System.out.println("Message sent successfully!");
    }
}