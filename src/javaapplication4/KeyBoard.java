/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import java.awt.Point;
import java.text.Normalizer;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Mactabi
 */
public class KeyBoard {
    
    
    public KeyBoard(JFrame frm){
    
    int height=frm.getHeight();
    int width=frm.getWidth();
    final int keysHeight=60;
    final int keysWidth=50;
    
    Point currentLocation=new Point(frm.getHeight()-keysHeight,0);
    String[][] charactericKeys = {
        {"`","1","2","3","4","5","6","7","8","9","0","-","=","\\"},
        {"Q","W","E","R","T","Y","U","I","O","P","[","]"},
        {"A","S","D","F","G","H","J","K","L",";","'"},
        {"Z","X","C","C","V","B","N","M",",",".","/"},
    };
    
   
    
    for(int i=0;i<=charactericKeys.length-1;i++){
        currentLocation.setLocation(i*keysWidth/3,currentLocation.getY()+keysHeight);
        for(int j=0;j<=charactericKeys[i].length-1;j++){
            JButton btn=new JButton();
            btn.setText(charactericKeys[i][j]);
            btn.setSize(keysWidth,keysHeight);
            btn.setLocation(currentLocation);
            currentLocation.setLocation(keysWidth+currentLocation.getX(),currentLocation.getY());
            frm.add(btn);
            
        }
        
        
        
        
        
    }
    
//    spaceKey=new
    
    
    
    }

   
};
