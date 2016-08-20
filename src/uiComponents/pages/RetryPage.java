package uiComponents.pages;

import uiComponents.TouchJTextField;
import uiComponents.uiInterfaces.ActivityPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import static utilities.Fonts.bodyFont;
import static utilities.Fonts.headingFont;
import static utilities.ImageUtilities.framifyImage;

public class RetryPage extends JPanel implements ActivityPage
{

    private JButton submitBtn;
    private JLabel statusLabel;
    private TouchJTextField emailInputField;
    private JLabel emailLabel;
    private JLabel imagePanel;


    private Image frameImage;
    private Image logoImage;
    private Image userImg;
    private int currentIndex;
    private MainFrame parent;
    private JLabel infoLable;
    static RetryPage instance;

    public static RetryPage getInstance()
    {
        if (instance == null)
            instance = new RetryPage();
        return instance;
    }

    private RetryPage()
    {

        this.parent = MainFrame.getInstance();

        try
        {
            frameImage = ImageIO.read(new File("icons\\ImageFrame3.png"));
            logoImage = ImageIO.read(new File("icons\\logo.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        initComponents();
    }

    public void setImage(Image img)
    {
        userImg = img;

        double imgAspectRatio = userImg.getWidth(null) / ((double) userImg.getHeight(null));
        int finalHeight = (getHeight()) / 3, finalWidth = (int) (finalHeight * imgAspectRatio);
        ImageIcon imgIcon = framifyImage(userImg, logoImage, frameImage, finalWidth, finalHeight);


        if (imagePanel == null)
        {
            GridBagConstraints c = new GridBagConstraints();
            c.ipady = 20;
            c.insets = new Insets(10, 0, 30, 0);
            c.weighty = 0.0;
            c.weightx = 1.0;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.NONE;
            imagePanel = new JLabel(imgIcon);
            add(imagePanel, c);
        } else imagePanel.setIcon(imgIcon);
        repaint();
    }

    void initComponents()
    {
        setSize(parent.getSize());
        setLocation(0, 0);
        setBackground(Color.WHITE);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();


        c.ipady = 20;
        c.insets = new Insets(10, 0, 30, 0);
        c.weighty = 0.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;


        String infoMsg = "<html><font color='green'>آیا عکس مورد تایید میباشد؟</font></html>";
        infoLable = new JLabel(infoMsg);
        infoLable.setFont(headingFont.deriveFont(Font.BOLD));
        c.anchor=c.PAGE_END;
        add(infoLable, c);


        c.insets = new Insets(10, 0, 10, 0);
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(new ImageIcon("icons//accept.png").getImage().getScaledInstance(50, 50, 0));
        submitBtn = new JButton(imageIcon);
        submitBtn.setFont(bodyFont);
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.fill = GridBagConstraints.NONE;
        submitBtn.setOpaque(false);
        submitBtn.setContentAreaFilled(false);
        submitBtn.setBorderPainted(false);
        add(submitBtn, c);


        imageIcon = new ImageIcon();
        imageIcon.setImage(new ImageIcon("icons//deny.png").getImage().getScaledInstance(50, 50, 0));
        JButton retryBtn = new JButton(imageIcon);
        retryBtn.setFont(bodyFont);
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.NONE;
        retryBtn.setOpaque(false);
        retryBtn.setContentAreaFilled(false);
        retryBtn.setBorderPainted(false);
        add(retryBtn, c);


        retryBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainFrame.getInstance().goPreviousPage();
            }
        });

        submitBtn.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                CatalogEmailSendingPage.getInstance().setImage(userImg);
                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException e1)
                {
                    e1.printStackTrace();
                }
                MainFrame.getInstance().showPanel(CatalogEmailSendingPage.getInstance().getPanelIndex());

            }

            @Override
            public void mousePressed(MouseEvent e)
            {

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {

            }

            @Override
            public void mouseExited(MouseEvent e)
            {

            }
        });
        submitBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });


        submitBtn.requestFocus();

        setFocusTraversalPolicy(new FocusTraversalPolicy()
        {
            @Override
            public Component getComponentAfter(Container aContainer, Component aComponent)
            {
                if (aComponent.equals(emailInputField))
                    return submitBtn;
                return emailInputField;
            }

            @Override
            public Component getComponentBefore(Container aContainer, Component aComponent)
            {
                if (aComponent.equals(emailInputField))
                    return submitBtn;
                return emailInputField;
            }

            @Override
            public Component getFirstComponent(Container aContainer)
            {
                return submitBtn;
            }

            @Override
            public Component getLastComponent(Container aContainer)
            {
                return emailInputField;
            }

            @Override
            public Component getDefaultComponent(Container aContainer)
            {
                return submitBtn;
            }
        });

        currentIndex = parent.addPanel(this);


    }

    private static void initializeLayout(JPanel panel)
    {/////////////////////////////////////////////////////////////////////

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        for (int i = 0; i < 5; i++)
        {
            c.gridx = i;
            JLabel temp = new JLabel("");
            panel.add(temp, c);
        }

        c.gridx = 0;
        for (int i = 0; i < 3; i++)
        {
            c.gridy = i;
            JLabel temp = new JLabel("");
            panel.add(temp, c);
        }
    }


    @Override
    public int getPanelIndex()
    {
        return currentIndex;
    }

    @Override
    public void beforeShow()
    {

    }

    @Override
    public void afterShow()
    {
        MainFrame.getInstance().showNavbar();
    }

    @Override
    public void beforeDispose()
    {

    }

    @Override
    public void afterDispose()
    {

    }
}
