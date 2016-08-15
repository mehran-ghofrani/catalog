package utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtilities
{
    public static ImageIcon framifyImage(Image mainImage, Image logoImage, Image frameImage, int finalWidth, int finalHeight)
    {
        mainImage = logolizeImage(mainImage, logoImage);
        double frameMarginHeightRatio = 170.0 / frameImage.getHeight(null), frameMarginWidthRatio = 180.0 / frameImage.getWidth(null);
        int imageHeight = (int) (finalHeight / (1 - 2 * frameMarginHeightRatio)), imageWidth = (int) (finalWidth / (1 - 2 * frameMarginWidthRatio));
        int extraHeight = (int) ((imageHeight - finalHeight) / 2), extraWidth = (int) ((imageWidth - finalWidth) / 2);

        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = bi.createGraphics();
        graphics.drawImage(mainImage, extraWidth, extraHeight, (int) finalWidth, (int) finalHeight, null);
        graphics.drawImage(frameImage, 0, 0, bi.getWidth(null), bi.getHeight(null), null);


        return new ImageIcon(bi);
    }

    public static Image logolizeImage(Image mainImage, Image logoImage)
    {
        double logoAspectRatio = logoImage.getWidth(null) / ((double) logoImage.getHeight(null));
        double logoHeight = mainImage.getHeight(null) / 11.0, logoWidth = logoHeight * logoAspectRatio;

        Graphics graphics1 = mainImage.getGraphics();
        graphics1.drawImage(logoImage, 20, 20, (int) logoWidth, (int) logoHeight, null);

        return mainImage;
    }

    public static void saveImage(Image img, String path)
    {
        File outputfile = new File(path);
        if (outputfile.exists())
            outputfile.delete();
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        try
        {
            ImageIO.write(bufferedImage, "jpg", outputfile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
