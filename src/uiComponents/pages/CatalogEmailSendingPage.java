package uiComponents.pages;

import db.DBManager;
import uiComponents.TouchJTextField;
import uiComponents.uiInterfaces.EnterActionPerformListener;
import uiComponents.uiInterfaces.ActivityPage;
import utilities.EmailUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static utilities.EmailUtils.isValidEmailAddress;
import static utilities.Fonts.bodyFont;
import static utilities.Fonts.headingFont;
import static utilities.ImageUtilities.framifyImage;

public class CatalogEmailSendingPage extends JPanel implements EnterActionPerformListener, ActivityPage
{
    private static CatalogEmailSendingPage instance = null;
    private Image userImg;
    private JLabel infoLable;
    private JButton submitBtn;
    private JLabel statusLabel;
    private TouchJTextField emailInputField;
    private JLabel emailLabel;

    private MainFrame parent;
    private int currentIndex;
    private Image frameImage;
    private Image logoImage;


    private CatalogEmailSendingPage()
    {
        this.parent = MainFrame.getInstance();
        try
        {
            frameImage = ImageIO.read(new File("icons\\ImageFrame3.png"));
            logoImage = ImageIO.read(new File("icons\\logo.jpg"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        initComponents();
    }

    public static CatalogEmailSendingPage getInstance()
    {
        if (instance == null)
            instance = new CatalogEmailSendingPage();
        return instance;
    }


    private static void initializeLayout(JPanel panel)
    {
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

    private void initComponents()
    {
        setSize(parent.getMainPanelSize());
        setLocation(0, 0);
        setBackground(Color.WHITE);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        initializeLayout(this);
        GridBagConstraints c = new GridBagConstraints();


        int i = 1;

        c.ipady = 20;
        c.insets = new Insets(10, 0, 30, 0);
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = i++;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.CENTER;

        String infoMsg = "در صورت تمایل، ایمیل خود را برای دریافت عکس سلفی وارد نمایید";
        infoLable = new JLabel(infoMsg);
        infoLable.setFont(headingFont);
        add(infoLable, c);

        c.insets = new Insets(10, 0, 0, 20);
        c.ipadx = 0;
        c.gridx = 2;
        c.gridy = i;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        emailLabel = new JLabel("ایمیل:");
        emailLabel.setFont(bodyFont);
        add(emailLabel, c);

        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = i++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        emailInputField = new TouchJTextField("", "example@host.com", parent);
        add(emailInputField, c);


        submitBtn = new JButton("ارسال");
        submitBtn.setFont(bodyFont);
        c.ipadx = 100;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = i++;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.CENTER;
        add(submitBtn, c);

        c.insets = new Insets(40, 0, 0, 0);
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = i++;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.CENTER;
        statusLabel = new JLabel(" ");
        statusLabel.setFont(bodyFont);
        add(statusLabel, c);

        submitBtn.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                EnterActionPerform();
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
                EnterActionPerform();
            }
        });

        emailInputField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                resetStatusLabel();
            }

            @Override
            public void focusLost(FocusEvent e)
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

    private void resetStatusLabel()
    {
        statusLabel.setText(" ");
    }

    private void setVisibleAll(boolean b)
    {
        infoLable.setVisible(b);
        emailInputField.setVisible(b);
        emailLabel.setVisible(b);
        submitBtn.setVisible(b);
    }

    @Override
    public void EnterActionPerform()
    {
        if (isValidEmailAddress(emailInputField.getValidText()))
        {
            String tempEmail = emailInputField.getValidText();
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    DBManager.getMyInstance().addEmail(tempEmail);
                    EmailUtils.send(tempEmail, "extraData//Desert.jpg", "Catalog");
                }
            }).start();
            setVisibleAll(false);
            statusLabel.setFont(headingFont.deriveFont(22.0f));
            statusLabel.setText("<html>کاتالوگ به آدرس " + tempEmail + " <font color='green'>ارسال شد</font></html>");
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(3000);
                    } catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                    resetStatusLabel();
                    emailInputField.setText("");
                    setVisibleAll(true);
                    emailInputField.requestFocus();
                    submitBtn.requestFocus();
                }
            }).start();
        } else
        {
            statusLabel.setFont(bodyFont);
            statusLabel.setText("<html>ایمیل <font color='red'>اشتباه</font> وارد شده است</html>");
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

    }

    @Override
    public void beforeDispose()
    {

    }

    @Override
    public void afterDispose()
    {

    }

    public void setImage(String path)
    {
        try
        {
            userImg = ImageIO.read(new File(path));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        double imgAspectRatio = userImg.getWidth(null) / ((double) userImg.getHeight(null));
        int finalHeight = (getHeight() - 50) / 4, finalWidth = (int) (finalHeight * imgAspectRatio);
        ImageIcon imgPanel = framifyImage(userImg, logoImage, frameImage, finalWidth, finalHeight);

        GridBagConstraints c = new GridBagConstraints();


        c.ipady = 20;
        c.insets = new Insets(10, 0, 30, 0);
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        add(new JLabel(imgPanel), c);

    }
}
