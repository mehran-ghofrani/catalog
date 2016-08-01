/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



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
     
    
}
