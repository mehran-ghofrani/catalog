package uiComponents.pages;

import com.sun.deploy.config.Config;
import sun.rmi.runtime.NewThreadAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by online on 8/9/2016.
 */
public class EntrancePage extends JPanel implements MainPanel
{
    private int currentIndex;

    public EntrancePage(){
        init();


    }
    public void init(){

        Dimension size = MainFrame.getInstance().getSize();
        setSize(size);
        setLocation(0, 0);
        String pictureAddress="C:\\Users\\Mactabi\\Desktop\\1.jpg";

        setLayout(null);
        JButton btn=new JButton();
        add(btn);
        btn.setLocation((int)size.getWidth()/4,(int)size.getHeight()/4);
        btn.setSize((int)size.getWidth()/2,(int)size.getHeight()/2);

        btn.setVisible(true);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().showPanel(ImageCapturingPage.getInstance().getPanelIndex());

            }
        });

        Image img;
        File f;
        f=new File(pictureAddress);
        try{
            img=ImageIO.read(f);
            btn.setIcon(new ImageIcon(img.getScaledInstance(btn.getWidth(),btn.getHeight(),Image.SCALE_DEFAULT)));
        }
        catch(Exception ex)
        {

        }

        JLabel label=new JLabel("برای انداختن عکس سلفی صفحه را لمس کنید");
        label.setSize(label.getText().length()*6,50);
        label.setLocation(((int)size.getWidth()-label.getWidth())/2,0);

        label.setVisible(true);
        add(label);





        setBackground(Color.white);

        currentIndex = MainFrame.getInstance().addPanel(this);






    }


    @Override
    public int getPanelIndex(){
        return currentIndex;
    }
}
