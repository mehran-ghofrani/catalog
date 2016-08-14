package uiComponents.pages;

import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import com.jogamp.opengl.awt.GLJPanel;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

import java.io.IOException;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import uiComponents.uiInterfaces.ActivityPage;

/**
 * Created by online on 8/9/2016.
 */
public class EntrancePage extends GLJPanel implements ActivityPage
{
    private int currentIndex;
    private static EntrancePage instance;

    public static EntrancePage getInstance(){
        if(instance==null)
            instance= new EntrancePage();
        return instance;



    }

    private EntrancePage(){
        super( new GLCapabilities( GLProfile.getDefault() ) );
        init();


    }
    public void init(){

        Dimension size = MainFrame.getInstance().getMainPanelSize();
        setSize(size);
        setLocation(0, 0);
        String pictureAddress="C:\\Users\\Mactabi\\Desktop\\1.jpg";

        setLayout(null);
        setBackground(Color.BLUE);
        JButton btn=new JButton();
        //add(btn);
        btn.setLocation((int)size.getWidth()/4,(int)size.getHeight()/4);
        btn.setSize((int)size.getWidth()/2,(int)size.getHeight()/2);

        btn.setVisible(true);
        addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                MainFrame.getInstance().showPanel(ImageCapturingPage.getInstance().getPanelIndex());
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





        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 22));




        label.setSize(label.getText().length()*12,30);
        label.setLocation(((int)size.getWidth()-label.getWidth())/2,(int)size.getHeight()-60);

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
                if(OneTriangle.threadExists==false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                OneTriangle.deg += 0.05;
                                if (OneTriangle.deg > 359) OneTriangle.deg = 0;
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                EntrancePage.getInstance().repaint();
                            }
                        }
                    }).start();
                    OneTriangle.threadExists = true;
                }

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

    @Override
    public void beforeShow()
    {

    }

    @Override
    public void afterShow()
    {

    }

    @Override
    public void beforeDispose()
    {

    }

    @Override
    public void afterDispose()
    {

    }
}


class OneTriangle {
    public static float deg=0;
    public static boolean threadExists=false;
    protected static void setup( GL2 gl2, int width, int height ) {




        Texture text;

        try {
            text = TextureIO.newTexture(new File("icons\\1.jpg"), true);
            text.enable(gl2);
            text.bind(gl2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gl2.glMatrixMode( GL2.GL_PROJECTION );
        gl2.glLoadIdentity();
        gl2.glClearColor(1,1,1,1);



        gl2.glMatrixMode( GL2.GL_MODELVIEW );
        gl2.glLoadIdentity();

    }

    protected static void render( GL2 gl2, int width, int height ) {
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT );

        gl2.glViewport(0,0,width,height);
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();


        // draw a triangle filling the window
        GLU glu=new GLU();
        GLUT glut=new GLUT();

        glu.gluPerspective(90f,1f,0.1f ,10f);
        glu.gluLookAt(0,0,0,0,0,-2,0,1,0);




        gl2.glTranslated(0,0,-1);
        gl2.glDisable(GL.GL_CULL_FACE);
        gl2.glRotatef(deg,0,1,0);









        gl2.glBegin(GL2.GL_QUADS);
        gl2.glNormal3f(0,0,1);
        gl2.glTexCoord2d(0.0, 0.0);
        gl2.glVertex2d(-1.0, -1.0);
        gl2.glTexCoord2d(1.0, 0.0);
        gl2.glVertex2d(1, -1.0);
        gl2.glTexCoord2d(1.0, 1.0);
        gl2.glVertex2d(1, 1);
        gl2.glTexCoord2d(0.0, 1.0);
        gl2.glVertex2d(-1.0, 1);
        gl2.glEnd();


    }
}
