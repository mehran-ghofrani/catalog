/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;        
import org.opencv.highgui.VideoCapture;        

/**
 *
 * @author Mactabi
 */
public class camera extends JPanel {
    
    private Image currentImg;
    private static camera instance;
    
    public BufferedImage MatToBufferedImage(Mat frame) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }
    public BufferedImage grayscale(BufferedImage img) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
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
    public static camera getInstance(){
        
        if(instance==null)
            instance = new camera();
        return instance;
    }
    private camera(){
        System.load("C:\\Users\\Mactabi\\Desktop\\opencv\\build\\java\\x64\\"+ Core.NATIVE_LIBRARY_NAME + ".dll");
        VideoCapture camera = new VideoCapture(0);
        Mat frame = new Mat();
        if(!camera.isOpened()){
            System.out.println("Error");
        }
        else{
            Thread updater=new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        if(camera.read(frame)){
                            Mat flippedFrame=new Mat();
                            Core.flip(frame,flippedFrame,1);
                            BufferedImage image = MatToBufferedImage(flippedFrame);
                            setImage(image);
                            repaint();
//                            break;
                        }
                    }
                }
            }); 
            updater.start();
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g); //To change body of generated methods, choose Tools | Templates.
                g.drawImage(currentImg, 0, 0,  this);

    }
    void setImage(Image img){
        this.currentImg=img;
        
    }
    public void release(){
        getInstance().release();
    }
    
    
    
    
    public static void main(String args[]){
        JFrame j =new JFrame();
        j.setVisible(true);
        j.setSize(400,400);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLayout(null);
        camera cam=camera.getInstance();
        j.getContentPane().add(cam);
        cam.setVisible(true);
        cam.setSize(400,400);
        System.out.println("done");
        
    }
    
}

