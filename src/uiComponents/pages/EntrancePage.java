package uiComponents.pages;

import com.sun.deploy.config.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
        String pictureAddress="C:\\Users\\Mactabi\\Desktop\\1.jpg";

        setLayout(null);
        JButton btn=new JButton();
        add(btn);
        btn.setLocation((int)size.getWidth()/4,(int)size.getHeight()/4);
        btn.setSize((int)size.getWidth()/2,(int)size.getHeight()/2);
        btn.setVisible(true);

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

        currentIndex = MainFrame.getInstance().addPanel(this);






    }


    @Override
    public int getPanelIndex() {
        return currentIndex;
    }
}
