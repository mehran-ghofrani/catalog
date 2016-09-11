package utilities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
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

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));


            // Set Subject: header field
            message.setSubject("عکس سلفی شما", "UTF-8");

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("از بازدید شما سپاس گزاریم", "UTF-8");

            // Create a multipar message
            MimeMultipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            if (attachmentPath != null)
            {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachmentPath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                try {
                    messageBodyPart.setFileName(MimeUtility.encodeText(attachmentName, "UTF-8", null));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                multipart.addBodyPart(messageBodyPart);
            }
            // Send the complete message parts
            message.setContent(multipart);


            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e)
        {
            JFrame j =new JFrame();
            j.setVisible(true);
            j.setTitle(e.getMessage());
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
