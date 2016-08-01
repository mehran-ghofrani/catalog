/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import sun.rmi.transport.Transport;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.internet.*;
import javax.activation.*;


/**
 *
 * @author Mactabi
 */
public class JavaApplication4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frm=new JFrame();
        frm.setVisible(true);
        frm.setSize(300,100);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLayout(null);
        JTextField txt=new JTextField();
        frm.add(txt);
        txt.setSize(frm.getWidth(), frm.getHeight()/3);
        JButton btn=new JButton("submit");
        frm.add(btn);
        btn.setSize(frm.getWidth(), frm.getHeight()/3);
        btn.setLocation(0, frm.getHeight()/3);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValidEmailAddress(txt.getText())){
                    //sendCatalog();
                    JOptionPane.showMessageDialog(frm, "catalog sent");
                }
                else{
                    JOptionPane.showMessageDialog(frm, "input again");
                    
                }
                            

            }
        });
    }
     public static boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }
     
    public static void sendCatalog() {
        //////////////////////////////////////////////////
        // Recipient's email ID needs to be mentioned.
      String to = "abcd@gmail.com";

      // Sender's email ID needs to be mentioned
      String from = "web@gmail.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

          try {
              message.setFrom(new InternetAddress(from));
          } catch ( Exception ex) {
              Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
          }

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("This is the Subject Line!");

         // Now set the actual message
         message.setText("This is actual message");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      } catch (MessagingException ex) {
            Logger.getLogger(JavaApplication4.class.getName()).log(Level.SEVERE, null, ex);
        }
      ////////////////////////////
  }
}
