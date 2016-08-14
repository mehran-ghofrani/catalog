package uiComponents.pages;

import uiComponents.TouchJTextField;
import uiComponents.uiInterfaces.ActivityPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static utilities.Fonts.bodyFont;
import static utilities.Fonts.headingFont;
import static utilities.ImageUtilities.framifyImage;

/**
 * Created by Mactabi on 14/08/2016.
 */
public class RetryPage extends JPanel  implements ActivityPage {

    private JButton submitBtn;
    private JLabel statusLabel;
    private TouchJTextField emailInputField;
    private JLabel emailLabel;





    private Image frameImage;
    private Image logoImage;
    private Image userImg;
    private int currentIndex;
    private MainFrame parent;
    private JLabel infoLable;
    static RetryPage instance;
    public static RetryPage getInstance(){
        if (instance==null)
            instance=new RetryPage();
        return instance;
    }
    private RetryPage(){

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




        currentIndex = parent.addPanel(this);
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
    void initComponents(){
        setSize(parent.getSize());
        setLocation(0, 0);
        setBackground(Color.WHITE);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        initializeLayout(this);
        GridBagConstraints c = new GridBagConstraints();


        c.ipady = 20;
        c.insets = new Insets(10, 0, 30, 0);
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.CENTER;

        String infoMsg = "در صورت تمایل، ایمیل خود را برای دریافت عکس سلفی وارد نمایید";
        infoLable = new JLabel(infoMsg);
        infoLable.setFont(headingFont);
        add(infoLable, c);

        c.insets = new Insets(10, 0, 0, 20);
        c.ipadx = 0;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        emailLabel = new JLabel("ایمیل:");
        emailLabel.setFont(bodyFont);
        add(emailLabel, c);

        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        emailInputField = new TouchJTextField("", "example@host.com", parent);
        add(emailInputField, c);


        submitBtn = new JButton("ارسال");
        submitBtn.setFont(bodyFont);
        c.ipadx = 100;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.CENTER;
        add(submitBtn, c);

        c.insets = new Insets(40, 0, 0, 0);
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 4;
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

        emailInputField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {

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
    public int getPanelIndex() {
        return currentIndex;
    }

    @Override
    public void beforeShow() {

    }

    @Override
    public void afterShow() {

    }

    @Override
    public void beforeDispose() {

    }

    @Override
    public void afterDispose() {

    }
}
