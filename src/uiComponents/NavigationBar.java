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
    private JButton homeBtn, backbtn;
    private MainFrame parent;
    private Image homeImg, backImg;

    public NavigationBar(MainFrame parent)
    {
        this.parent = parent;
        initComponents();
    }

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
        Color backColor = new Color(255, 255, 255);
        setBackground(backColor);
        setSize(parent.getWidth(), parent.getHeight()/30);

        homeBtn = new JButton(new ImageIcon(homeImg.getScaledInstance(getHeight(), getHeight(), 0)));
        backbtn = new JButton(new ImageIcon(backImg.getScaledInstance(getHeight(), getHeight(), 0)));

        homeBtn.setSize(getHeight(), getHeight());
        backbtn.setSize(getHeight(), getHeight());

        homeBtn.setFocusable(false);
        backbtn.setFocusable(false);

        homeBtn.setBackground(backColor);
        backbtn.setBackground(backColor);

        homeBtn.setLocation(getWidth()/2 + 10, 0);
        backbtn.setLocation(getWidth()/2 - homeBtn.getWidth() - 10, 0);

        homeBtn.setBorderPainted(false);
        backbtn.setBorderPainted(false);

        homeBtn.setOpaque(false);
        backbtn.setOpaque(false);

        homeBtn.setContentAreaFilled(false);
        backbtn.setContentAreaFilled(false);

        homeBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("home");
                parent.goHomePage();
            }
        });

        backbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("back");
                parent.goPreviousPage();
            }
        });


        add(homeBtn);
        add(backbtn);
    }
}
