/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiComponents.pages;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 * @author Mactabi
 */
public class Camera extends JPanel
{

    private static Camera instance;
    private final int currentIndex;
    private Image currentImg;

    private Camera()
    {
        System.load( new File("").getAbsolutePath() + "\\libs\\OpenCV\\" + Core.NATIVE_LIBRARY_NAME + ".dll");
        VideoCapture camera = new VideoCapture(0);
        Mat frame = new Mat();
        if (!camera.isOpened())
        {
            System.out.println("Error");
        } else
        {
            Thread updater = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        if (camera.read(frame))
                        {
                            Mat flippedFrame = new Mat();
                            Core.flip(frame, flippedFrame, 1);
                            BufferedImage image = MatToBufferedImage(flippedFrame);
                            setImage(image);
                            repaint();
                        }
                        else
                        {
                            camera.release();
                            break;
                        }
                    }
                }
            });
            updater.start();
        }

        setSize(MainFrame.getInstance().getSize());
        setLocation(0, 0);
        setBackground(Color.BLUE);

        currentIndex = MainFrame.getInstance().addPanel(this);
        MainFrame.getInstance().showPanel(currentIndex);
    }

    public static Camera getInstance()
    {

        if (instance == null)
            instance = new Camera();
        return instance;
    }

    public static void main(String args[])
    {
        JFrame j = new JFrame();
        j.setVisible(true);
        j.setSize(400, 400);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLayout(null);
        Camera cam = Camera.getInstance();
        j.getContentPane().add(cam);
        cam.setVisible(true);
        cam.setSize(400, 400);
        System.out.println("done");

    }

    public BufferedImage MatToBufferedImage(Mat frame)
    {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1)
        {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else if (frame.channels() == 3)
        {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

    public BufferedImage grayscale(BufferedImage img)
    {
        for (int i = 0; i < img.getHeight(); i++)
        {
            for (int j = 0; j < img.getWidth(); j++)
            {
                Color c = new Color(img.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor =
                        new Color(
                                red + green + blue,
                                red + green + blue,
                                red + green + blue);

                img.setRGB(j, i, newColor.getRGB());
            }
        }

        return img;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paintComponents(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(currentImg.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, this);

    }

    void setImage(Image img)
    {
        this.currentImg = img;

    }

}

