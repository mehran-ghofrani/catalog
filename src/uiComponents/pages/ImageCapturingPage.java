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
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageCapturingPage extends JPanel implements MainPanel
{

    private static ImageCapturingPage instance;
    private final Thread timerThread;
    private Image currentImg;
    private Integer timer;
    private Boolean showCamera;
    private boolean showCapture;
    private double captureBorderThickness;
    private int currentIndex = 0;
    private VideoCapture camera;

    private ImageCapturingPage()
    {
        System.load(new File("").getAbsolutePath() + "\\libs\\OpenCV\\" + Core.NATIVE_LIBRARY_NAME + ".dll");
        timer = 8;
        showCamera = true;
        showCapture = false;

        camera = new VideoCapture(0);
        Mat frame = new Mat();
        waitUntilCameraIsConnected();
        while (!camera.isOpened())
        {
            JOptionPane.showConfirmDialog(this, "خطا در اتصال به دوربین");
            camera.open(0);
        }
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    if (getCamera().read(frame))
                    {
                        if (showCamera == false)
                            break;
                        Mat flippedFrame = new Mat();
                        Core.flip(frame, flippedFrame, 1);
                        BufferedImage image = MatToBufferedImage(flippedFrame);
                        setImage(image);
                        repaint();
                    } else
                    {
                        if(timerThread != null)
                        {
                            System.out.println("suspend");
                            if(timerThread.isAlive())
                                timerThread.suspend();
                        }
                        waitUntilCameraIsConnected();
                        waitUntilImageIsReady();
                        if(timerThread != null)
                        {
                            System.out.println("resume");
                            timerThread.resume();
                        }
                    }
                }
                getCamera().release();
                System.out.println("camera released");
            }
        }).start();

        setSize(MainFrame.getInstance().getSize());
        setLocation(0, 0);

        currentIndex = MainFrame.getInstance().addPanel(this);

        timerThread = new Thread(new Runnable()
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
                showCamera = false;
                showCapturingEffect();
                System.out.println("thread timer ended");
                CatalogEmailSendingPage.getInstance().setImage("image.jpg");
                MainFrame.getInstance().showPanel(CatalogEmailSendingPage.getInstance().getPanelIndex());
            }
        });
        timerThread.start();
    }

    private void waitUntilCameraIsConnected()
    {
        while (!camera.isOpened())
        {
            camera.release();
            JOptionPane.showConfirmDialog(this, "خطا در اتصال به دوربین");
            camera.open(0);
        }
    }

    private void waitUntilImageIsReady()
    {
        Mat frame = new Mat();
        while (!camera.read(frame))
        {
            camera.release();
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            camera.open(0);
            System.out.println("try to read image");
        }
    }

    private void showCapturingEffect()
    {
//        setSize(getParent().getWidth() * 90 / 100, getParent().getHeight() * 90 / 100);
//        setLocation(getParent().getWidth() * 5 / 100, getParent().getHeight() * 5 / 100);
//        try
//        {
//            Thread.sleep(100);
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//        setLocation(0, 0);
//        setSize(getParent().getWidth(), getParent().getHeight());
//        repaint();
        showCapture = true;
        captureBorderThickness = 0.1;
        while (captureBorderThickness < 1)
        {
            captureBorderThickness += 0.1;
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        captureBorderThickness = 1;
        try
        {
            Thread.sleep(10);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        while (captureBorderThickness > 0)
        {
            captureBorderThickness -= 0.1;
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        showCapture = false;
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

    public static ImageCapturingPage getInstance()
    {

        if (instance == null)
            instance = new ImageCapturingPage();
        return instance;
    }

    public static void main(String args[])
    {
        JFrame j = new JFrame();
        j.setVisible(true);
        j.setSize(400, 400);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLayout(null);
        ImageCapturingPage cam = ImageCapturingPage.getInstance();
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
        super.paintComponents(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (currentImg != null)
        {
            double aspectRatio = ((double) currentImg.getWidth(null)) / currentImg.getHeight(null);
            int width, height = getHeight() - 50;
            width = (int) (height * aspectRatio);
            g.drawImage(currentImg.getScaledInstance(width, height, Image.SCALE_FAST)
                    , (getWidth() - width) / 2, (getHeight() - height) / 2, this);
            g.setFont(Fonts.englishTimerFont);
            if (timer >= 6) g.setColor(Color.green);
            else if (timer >= 4) g.setColor(Color.yellow);
            else g.setColor(Color.red);
            if (timer > 0)
                g.drawString(timer.toString(),  (getWidth() / 2) - 140, (getHeight() / 2) + 100);
            if (showCapture)
            {
                g.setColor(Color.white);
                float thickness = 10;
                Graphics2D g2 = (Graphics2D) g;
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke((float) (captureBorderThickness > 0 ? (captureBorderThickness * thickness) : 0.0)));
                g2.drawRect((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                g2.setStroke(oldStroke);
            }
        }
    }

    private void setImage(Image img)
    {
        this.currentImg = img;

    }

    public Integer getTimer()
    {
        return timer;
    }

    private void decreaseTimer()
    {
        timer--;
    }

    @Override
    public int getPanelIndex()
    {
        return currentIndex;
    }

    public VideoCapture getCamera()
    {
        return camera;
    }

    public void setCamera(VideoCapture camera)
    {
        this.camera = camera;
    }
}
