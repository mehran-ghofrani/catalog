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
import utilities.Fonts;

/**
 * Created by online on 8/9/2016.
 */
public class EntrancePage extends GLJPanel implements ActivityPage
{
    private int currentIndex;
    public static boolean finished=false;
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
        String pictureAddress="icons\\1.jpg";

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
                MainFrame.getInstance().showPanel(ImageCapturingPage.getInstance().getPanelIndex());

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
            ex.printStackTrace();
        }

        String firstLine = "لطفا برای انداختن عکس سلفی صفحه را لمس کنید",
                secondLine = "عکس شما پس از ارسال به ایمیل پاک خواهد شد";
        JLabel label=new JLabel("<html><div style='text-align: center;'><font color='rgb(18,66,95)'>" + firstLine + "<br>" + secondLine + "</font></div></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(Fonts.headingFont.deriveFont(Font.BOLD, 30));




        label.setSize(label.getText().length()/2*15,60);
        label.setLocation(((int)size.getWidth()-label.getWidth())/2,(int)size.getHeight()-90);

        label.setVisible(true);
//        add(label);





        setBackground(Color.white);

        currentIndex = MainFrame.getInstance().addPanel(this);











        addGLEventListener( new GLEventListener() {

            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
                glautodrawable.getGL().getGL2().glViewport(0,0,width,height);
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

    @Override
    public void beforeShow()
    {
        MainFrame.getInstance().hideNavbar();
        EntrancePage.finished=false;


            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (EntrancePage.finished==false) {
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






    }

    @Override
    public void afterShow()
    {
        MainFrame.getInstance().showLogo();
    }

    @Override
    public void beforeDispose()
    {
        EntrancePage.finished=true;
    }

    @Override
    public void afterDispose()
    {

    }

    @Override
    public void beforeKeyboardShow() {

    }

    @Override
    public void afterKeyboardDispose() {

    }
}


class OneTriangle {
    public static float deg=0;
    static Texture text2;
    static Texture text;

    protected static void setup( GL2 gl2, int width, int height ) {








        try {
            text = TextureIO.newTexture(new File("icons\\1.png"), true);

        } catch (IOException e) {
            e.printStackTrace();
        }





        try {
            text2 = TextureIO.newTexture(new File("icons\\welcomeText.png"), true);

        } catch (IOException e) {
            e.printStackTrace();
        }


        gl2.glMatrixMode( GL2.GL_PROJECTION );
        gl2.glLoadIdentity();
        gl2.glClearColor(1,1,1,1);



        gl2.glMatrixMode( GL2.GL_MODELVIEW );
        gl2.glLoadIdentity();



        gl2.glClear( GL.GL_COLOR_BUFFER_BIT );

        gl2.glBlendFunc (gl2.GL_SRC_ALPHA, gl2.GL_ONE_MINUS_SRC_ALPHA);
        gl2.glEnable(gl2.GL_BLEND);















    }

    protected static void render( GL2 gl2, int width, int height ) {




        gl2.glClear( GL.GL_COLOR_BUFFER_BIT );


        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glLoadIdentity();


        // draw a triangle filling the window
        GLU glu=new GLU();
        GLUT glut=new GLUT();

        glu.gluPerspective(90f,1f,0.1f ,10f);
//        glu.gluOrtho2D(-1,1,-1,1);
        glu.gluLookAt(0,0,0,0,0,-2,0,1,0);



        gl2.glTranslated(0,0,-1);
//        gl2.glTranslated(0,0,0);
        gl2.glDisable(GL.GL_CULL_FACE);
        gl2.glRotatef(deg,0,1,0);







        text.enable(gl2);
        text.bind(gl2);


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



        gl2.glLoadIdentity();
        gl2.glTranslated(0,0,-1);

        text2.enable(gl2);
        text2.bind(gl2);


        double x=-0.8;
        double y=-0.8;
        double w=1.8;
        double h=0.2;

        gl2.glLogicOp(gl2.GL_XOR);
        gl2.glEnable(gl2.GL_COLOR_LOGIC_OP );



        gl2.glBegin(GL2.GL_QUADS);
        gl2.glNormal3f(0,0,1);
        gl2.glTexCoord2d(0.0, 0.0);
        gl2.glVertex2d(x, y);
        gl2.glTexCoord2d(1.0, 0.0);
        gl2.glVertex2d(x+w, y);
        gl2.glTexCoord2d(1.0, 1.0);
        gl2.glVertex2d(x+w, y+h);
        gl2.glTexCoord2d(0.0, 1);
        gl2.glVertex2d(x, y+h);
        gl2.glEnd();
        gl2.glDisable(gl2.GL_COLOR_LOGIC_OP );


        /////



        gl2.glLoadIdentity();
        gl2.glTranslated(0,0,-1);






        gl2.glLogicOp(gl2.GL_XOR);
        gl2.glEnable(gl2.GL_COLOR_LOGIC_OP );

        gl2.glColor3f(1,1,1);



        gl2.glBegin(GL2.GL_QUADS);
        gl2.glNormal3f(0,0,1);
        gl2.glVertex2d(x, y);
        gl2.glVertex2d(x+w, y);
        gl2.glVertex2d(x+w, y+h);
        gl2.glVertex2d(x, y+h);
        gl2.glEnd();
        gl2.glDisable(gl2.GL_COLOR_LOGIC_OP );



    }
}
