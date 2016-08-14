package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtilities
{
    public static ImageIcon framifyImage(Image mainImage, Image logoImage, Image frameImage, int finalWidth, int finalHeight)
    {
        double logoAspectRatio = logoImage.getWidth(null) / ((double) logoImage.getHeight(null));
        int logoHeight = mainImage.getHeight(null) / 20, logoWidth = (int) (logoHeight * logoAspectRatio);

        Graphics graphics1 = mainImage.getGraphics();
        graphics1.drawImage(logoImage, 30, 30, logoWidth, logoHeight, null);

        double frameMarginHeightRatio = 170.0 / frameImage.getHeight(null), frameMarginWidthRatio = 180.0 / frameImage.getWidth(null);
        int imageHeight = (int) (finalHeight / (1 - 2 * frameMarginHeightRatio)), imageWidth = (int) (finalWidth / (1 - 2 * frameMarginWidthRatio));
        int extraHeight = (int) ((imageHeight - finalHeight) / 2), extraWidth = (int) ((imageWidth - finalWidth) / 2);

        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = bi.createGraphics();
        graphics.drawImage(mainImage, extraWidth, extraHeight, (int) finalWidth, (int) finalHeight, null);
        graphics.drawImage(frameImage, 0, 0, bi.getWidth(null), bi.getHeight(null), null);


        return new ImageIcon(bi);
    }
}
