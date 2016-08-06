/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import java.awt.AWTException;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author Mactabi
 */
public class KeyBoard {
    
    
    public KeyBoard(JFrame frm,final JTextField txt){
    
    
    final int keysHeight=60;
    final int keysWidth=50;
   
    
    Point currentLocation=new Point(0,frm.getHeight()-keysHeight*5-10);
    final char[][] upperKeys = {
        {'~','!','@','#','$','%','^','&','*','(',')','_','+','|'},
        {'Q','W','E','R','T','Y','U','I','O','P','{','}'},
        {'A','S','D','F','G','H','J','K','L',':','"'},
        {'Z','X','C','V','B','N','M','<','>','?'},
    };
    final char[][] lowerKeys = {
        {'`','1','2','3','4','5','6','7','8','9','0','-','=','\\'},
        {'Q','W','E','R','T','Y','U','I','O','P','[',']'},
        {'A','S','D','F','G','H','J','K','L',';','\''},
        {'Z','X','C','V','B','N','M',',','.','/'},
    };
    MButton[][] btns=new MButton[4][14];
    JButton shift=new JButton("Shift");
    JButton backSpace=new JButton("Delete");
    JButton enter=new JButton("Enter");
    
    for(int i=0;i<=lowerKeys.length-1;i++){
        currentLocation.setLocation(13*keysWidth+i*keysWidth/3,currentLocation.getY()+keysHeight);
        for(int j=0;j<=lowerKeys[i].length-1;j++){
            MButton btn=btns[i][j]=new MButton(i,j);
            String s=lowerKeys[i][j]+"";
            btn.setText(s);
            btn.setSize(keysWidth,keysHeight);
            btn.setLocation(currentLocation);
            currentLocation.setLocation(keysWidth+currentLocation.getX(),currentLocation.getY());
            frm.add(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   txt.requestFocusInWindow();
                    MButton b=((MButton)e.getSource());
//                    char a;
//                    char[] b=new char[1];
//                    ((JButton)e.getSource()).getText().getChars(0, 1, b, 0);
//                    a=b[0];
                    
                    try {
                        Robot robot = new Robot();
                        
                        KeyStroke awtKS = KeyStroke.getKeyStroke(lowerKeys[b.i][b.j], 0);
                        int key_code = awtKS.getKeyCode();
                        if(b.i==2&&b.j==10){
                            robot.keyPress(KeyEvent.VK_QUOTE);
                            robot.keyRelease(KeyEvent.VK_QUOTE);
                        }
                        else
                        if(b.i==0&&b.j==0){
                            robot.keyPress(KeyEvent.VK_BACK_QUOTE);
                            robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
                        }
                        else{
                            robot.keyPress(key_code);
                            robot.keyRelease(key_code);
                        }
                        

    
                    } catch (AWTException ex) {
                        Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            });
            
        }

        
        
        
        
        
    }
    
    frm.add(backSpace);
    currentLocation.setLocation(currentLocation.getX()+keysWidth*3/2, currentLocation.getY()-keysHeight*2/3);
    backSpace.setLocation(currentLocation);
    backSpace.setSize(keysWidth*3/2,keysHeight/2);
    frm.add(shift);
    currentLocation.setLocation(currentLocation.getX(), currentLocation.getY()+keysHeight);
    shift.setLocation(currentLocation);
    shift.setSize(keysWidth*3/2,keysHeight/2);
    frm.add(enter);
    currentLocation.setLocation(currentLocation.getX(), currentLocation.getY()-2*keysHeight);
    enter.setLocation(currentLocation);
    enter.setSize(keysWidth*3/2,keysHeight/2);
    
    
    frm.repaint();
    
    shift.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
                    if (((JButton)e.getSource()).getBackground()==Color.red){
                        ((JButton)e.getSource()).setBackground(null);
                        for(int i=0;i<=3;i++)
                            for(int j=0;j<=13;j++)
                                if(btns[i][j]!=null)btns[i][j].setText(lowerKeys[i][j]+"");
                    }
                    else{
                        ((JButton)e.getSource()).setBackground(Color.red);
                        for(int i=0;i<=3;i++)
                            for(int j=0;j<=13;j++)
                                if(btns[i][j]!=null)btns[i][j].setText(upperKeys[i][j]+"");
                    }
                    //((JButton)e.getSource()).setBackground(Color.red);
                    ((JButton)e.getSource()).repaint();
                    
                    try {
                        Robot robot = new Robot();
                        
                        if(((JButton)e.getSource()).getBackground()==Color.red)
                            robot.keyPress(KeyEvent.VK_SHIFT);
                        else
                            robot.keyRelease(KeyEvent.VK_SHIFT);

    
                    } catch (AWTException ex) {
                        Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
        }
    });
    
    backSpace.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            txt.requestFocusInWindow();
            try {
                        Robot robot = new Robot();
                        
                        KeyStroke awtKS = KeyStroke.getKeyStroke(8, 0);
                        robot.keyPress(awtKS.getKeyCode());
                        robot.keyRelease(awtKS.getKeyCode());
                        
                        
                        
                    } catch (AWTException ex) {
                        Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }
    });
    
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.

    }
    
    

   
};
class MButton extends JButton {
    int i;
    int j;
    MButton(int i,int j){
        this.i=i;
        this.j=j;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}