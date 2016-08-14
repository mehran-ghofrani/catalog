package uiComponents;

import uiComponents.pages.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by online on 8/14/2016.
 */
public class NavigationBar extends JPanel
{
//    private static NavigationBar instance = null;
    private JButton homeBtn, backbtn;
    private MainFrame parent;
    private Image homeImg, backImg;

    public NavigationBar(MainFrame parent)
    {
        this.parent = parent;
        initComponents();
    }

//    public static NavigationBar getInstance()
//    {
//        if(instance == null)
//            instance = new NavigationBar();
//        return instance;
//    }

    private void initComponents()
    {
        try
        {
            homeImg = ImageIO.read(new File("icons//home.png"));
            backImg = ImageIO.read(new File("icons//back.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        setLayout(null);
        setBackground(Color.WHITE);
        setSize(parent.getWidth(), parent.getHeight()/30);

        homeBtn = new JButton(new ImageIcon(homeImg.getScaledInstance(getHeight(), getHeight(), 0)));
        backbtn = new JButton(new ImageIcon(backImg.getScaledInstance(getHeight(), getHeight(), 0)));

        homeBtn.setSize(getHeight(), getHeight());
        backbtn.setSize(getHeight(), getHeight());

        homeBtn.setFocusable(false);
        backbtn.setFocusable(false);

        homeBtn.setBackground(Color.WHITE);
        backbtn.setBackground(Color.WHITE);

        homeBtn.setLocation(getWidth()/2, 0);
        backbtn.setLocation(getWidth()/2 - homeBtn.getWidth(), 0);

        homeBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.goHomePage();
            }
        });

        backbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.goPreviousPage();
            }
        });


        add(homeBtn);
        add(backbtn);
    }
}
