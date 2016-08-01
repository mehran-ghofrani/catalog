/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import Utilities.WordExporter;
import com.sun.deploy.panel.JavaPanel;
import db.DBManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;


/**
 * @author Mactabi
 */
public class JavaApplication4
{


    public static void main(String[] args)
    {


        final DBManager dbManager;
        dbManager = new DBManager();
        JFrame frm = new JFrame();
        frm.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        frm.setLocationRelativeTo(null);
        frm.setUndecorated(true);
        frm.setLayout(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setSize(frm.getSize());
        mainPanel.setLocation(0,0);


        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        c.ipadx = 500;
        c.ipady = 20;
        c.insets = new Insets(10,0,0,0);
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;

        JTextField txt = new JTextField();
        txt.setText("d_alihosseiny@yahoo.com");
        mainPanel.add(txt, c);
//        KeyBoard kb = new KeyBoard(frm);

        JButton btn = new JButton("submit");
        c.ipadx = 100;
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(btn, c);

        JButton reportBtn = new JButton("Get report");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(reportBtn, c);
        btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (EmailSend.isValidEmailAddress(txt.getText()))
                {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            dbManager.addEmail(txt.getText());
                            EmailSend.send(txt.getText());
                        }
                    }).start();
                    JOptionPane.showMessageDialog(frm, "catalog sent");
                } else
                {
                    JOptionPane.showMessageDialog(frm, "input again");

                }


            }
        });
        reportBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WordExporter.exportToWord(dbManager.getEmailEntities());
            }
        });
        frm.add(mainPanel);
        frm.setVisible(true);
    }


}
