/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiComponents.pages;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import utilities.Fonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * @author Mactabi
 */
public class Camera extends JPanel
{

    private static Camera instance;
    private final int currentIndex;
    private Image currentImg;
    private Integer timer;

    private Camera()
    {
        System.load(new File("").getAbsolutePath() + "\\libs\\OpenCV\\" + Core.NATIVE_LIBRARY_NAME + ".dll");
        timer = 10;

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
                        } else
                        {
                            camera.release();
                            break;
                        }
                    }
                }
            });
            updater.start();
        }

        addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                saveImage();
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

        setSize(MainFrame.getInstance().getSize());
        setLocation(0, 0);
        setBackground(Color.BLUE);

        currentIndex = MainFrame.getInstance().addPanel(this);
        MainFrame.getInstance().showPanel(currentIndex);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!isShowing())
                {
                    try
                    {
                        Thread.sleep(10);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        while (getTimer() > 0)
                        {
                            System.out.println(getTimer());
                            decreaseTimer();
                            try
                            {
                                Thread.sleep(1000);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        saveImage();
                    }
                }).start();
            }
        }).start();
    }

    private void saveImage()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                synchronized (currentImg)
                {
                    File outputfile = new File("image.jpg");
                    if (outputfile.exists())
                        outputfile.delete();
                    BufferedImage bufferedImage = new BufferedImage(currentImg.getWidth(null), currentImg.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D bGr = bufferedImage.createGraphics();
                    bGr.drawImage(currentImg, 0, 0, null);
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
        }).start();
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
        double aspectRatio = ((double) currentImg.getWidth(null)) / currentImg.getHeight(null);
        int width, height = getHeight() - 50;
        width = (int) (height * aspectRatio);
        g.drawImage(currentImg.getScaledInstance(width, height, Image.SCALE_FAST)
                , (getWidth() - width) / 2, (getHeight() - height) / 2, this);
        g.setFont(Fonts.englishTimerFont);
        g.setColor(Color.white);
        synchronized (timer)
        {
            g.drawString(timer.toString(), getWidth() / 2, getHeight() / 2);
        }
    }

    void setImage(Image img)
    {
        this.currentImg = img;

    }

    public Integer getTimer()
    {
        synchronized (timer)
        {
            return timer;
        }
    }

    private void decreaseTimer()
    {
        synchronized (timer)
        {
            timer--;
        }
    }
}

