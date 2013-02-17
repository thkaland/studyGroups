
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SMTPAuthenticator extends javax.mail.Authenticator {

    String gmail = "studygroupsproject@gmail.com",
            password = "SG123456";

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(gmail, password);
    }

    public static void sendMail(String hr, String subject, String msg) {


        String d_email = "studygroupsproject@gmail.com",
                d_host = "smtp.gmail.com",
                d_port = "465",
                m_to = hr,
                m_subject = subject,
                m_text = msg;


        Properties props = new Properties();
        props.put("mail.smtp.user", d_email);
        props.put("mail.smtp.host", d_host);
        props.put("mail.smtp.port", d_port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", d_port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();


        try {

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            //session.setDebug(true);

            MimeMessage message = new MimeMessage(session);
            message.setText(m_text);
            message.setSubject(m_subject);
            message.setFrom(new InternetAddress(d_email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
            Transport.send(message);

        } catch (Exception mex) {
            System.out.println("Mail Exception: " + mex.toString());
        }

    }
}
