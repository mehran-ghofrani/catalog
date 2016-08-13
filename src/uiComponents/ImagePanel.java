package uiComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by online on 8/13/2016.
 */
public class ImagePanel extends JPanel
{
    private final Image img;

    public ImagePanel(Image img)
    {
        this.img = img;
        setSize(img.getWidth(null), img.getHeight(null));
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(img, 0, 0, null);
    }
}
