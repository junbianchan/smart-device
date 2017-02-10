package co.darma.services.impl;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import co.darma.common.EncryptionHelper;
import co.darma.services.EmailService;
import play.Logger;

public class EmailServiceImpl implements EmailService {


    /**
     * int corePoolSize,
     * int maximumPoolSize,
     * long keepAliveTime,
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue
     */
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 3 * 60,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));


    public static EmailService createInstance() {
        return new EmailServiceImpl();
    }

    private static String SMTP_USER = "noreply@darma.co";
    private static String SMTP_PASSWORD = "!Q@W3e4r";
    private static String EMAIL_FROM = "noreply@darma.co";

    private static String PASSWORD_RESET_PATH = "http://app.darma.co/password/displayreset/";

    private static String PASSWORD_RESET_PATH_FOR_TEST = "http://54.86.204.32/password/displayreset/";

    // private static String PASSWORD_RESET_PATH =
    // "http://localhost:9000/password/displayreset/";..


    private MimeMessage createPasswordResetMessage(Session session,
                                                   Long memberId, String email) throws AddressException,
            MessagingException {
        String token = EncryptionHelper.generatePasswordResetToken(memberId);
        String resetUrl = PASSWORD_RESET_PATH + token;


        String content = "Hi there,<br><br>" +
                "Click here to reset your Darma password:<br>"
                + "<a href=\"" + resetUrl + "\">" + resetUrl + "</a> <br><br> " +

                "Please copy it to your browser if it does not work.<br><br>" +

                "If you have other questions, feel free to contact us at: hello@darma.co<br><br>" +

                "Best,<br>" +
                "Your Darma Team";


        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                email));
        message.setSubject("Reset your Darma password");
        message.setContent(content, "text/html");
        return message;
    }

    private Properties createSendingProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private Session createSendingSession(Properties props) {
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });
    }

    @Override
    public void sendPasswordResetEmail(Long memberId, String email) {
        try {
            Properties props = createSendingProperties();
            Session session = createSendingSession(props);
            final MimeMessage message = createPasswordResetMessage(session, memberId,
                    email);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.error("Exception when sending message ,error :" + e.getLocalizedMessage());
                    }
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}