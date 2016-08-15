package utilities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by online on 8/6/2016.
 */
public class EmailUtils
{
    public synchronized static void send(String to, String attachmentPath, String attachmentName)
    {
        System.out.println("sending email to: " + to);
        final String username = "sales@protouch.ir";
        final String password = "salesPROTOUCH1395@@";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.protouch.ir");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try
        {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));


            // Set Subject: header field
            message.setSubject("عکس سلفی شما");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("از بازدید شما سپاس گزاریم");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            if (attachmentPath != null)
            {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachmentPath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(attachmentName);
                multipart.addBodyPart(messageBodyPart);
            }
            // Send the complete message parts
            message.setContent(multipart);


            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e)
        {
            throw new RuntimeException(e);
        }

        ////////////////////////////
    }

    public static boolean isValidEmailAddress(String email)
    {
        if (email.trim().equalsIgnoreCase(""))
            return false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
