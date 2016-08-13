package uiComponents.pages;

import com.sun.deploy.config.Config;
import sun.rmi.runtime.NewThreadAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import com.jogamp.opengl.awt.GLJPanel;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * Created by online on 8/9/2016.
 */
public class EntrancePage extends GLJPanel implements MainPanel
{
    private int currentIndex;

    public EntrancePage(){
        super( new GLCapabilities( GLProfile.getDefault() ) );
        init();


    }
    public void init(){

        Dimension size = MainFrame.getInstance().getSize();
        setSize(size);
        setLocation(0, 0);
        String pictureAddress="C:\\Users\\Mactabi\\Desktop\\1.jpg";

        setLayout(null);
        JButton btn=new JButton();
        add(btn);
        btn.setLocation((int)size.getWidth()/4,(int)size.getHeight()/4);
        btn.setSize((int)size.getWidth()/2,(int)size.getHeight()/2);

        btn.setVisible(true);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().showPanel(ImageCapturingPage.getInstance().getPanelIndex());

            }
        });

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

        JLabel label=new JLabel("برای انداختن عکس سلفی صفحه را لمس کنید");
        label.setSize(label.getText().length()*6,50);
        label.setLocation(((int)size.getWidth()-label.getWidth())/2,0);

        label.setVisible(true);
        add(label);





        setBackground(Color.white);

        currentIndex = MainFrame.getInstance().addPanel(this);











        addGLEventListener( new GLEventListener() {

            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
            }

            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            }

            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }

            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
            }
        });

//        final JFrame jframe = new JFrame( "One Triangle Swing GLJPanel" );
//        jframe.addWindowListener( new WindowAdapter() {
//            public void windowClosing( WindowEvent windowevent ) {
//                jframe.dispose();
//                System.exit( 0 );
//            }
//        });

//        add( gljpanel );
//        gljpanel.setLocation(0,0);
//        jframe.setSize( 640, 480 );
//        jframe.setVisible( true );
    }














    @Override
    public int getPanelIndex(){
        return currentIndex;
    }
}


class OneTriangle {
    protected static void setup( GL2 gl2, int width, int height ) {
        gl2.glMatrixMode( GL2.GL_PROJECTION );
        gl2.glLoadIdentity();

        // coordinate system origin at lower left with width and height same as the window
        GLU glu = new GLU();
        glu.gluOrtho2D( 0.0f, width, 0.0f, height );

        gl2.glMatrixMode( GL2.GL_MODELVIEW );
        gl2.glLoadIdentity();

        gl2.glViewport( 0, 0, width, height );
    }

    protected static void render( GL2 gl2, int width, int height ) {
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT );

        // draw a triangle filling the window
        gl2.glLoadIdentity();
        gl2.glBegin( GL.GL_TRIANGLES );
        gl2.glColor3f( 1, 0, 0 );
        gl2.glVertex2f( 0, 0 );
        gl2.glColor3f( 0, 1, 0 );
        gl2.glVertex2f( width, 0 );
        gl2.glColor3f( 0, 0, 1 );
        gl2.glVertex2f( width / 2, height );
        gl2.glEnd();
    }
}
