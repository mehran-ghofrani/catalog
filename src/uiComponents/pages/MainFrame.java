package uiComponents.pages;

import uiComponents.KeyBoard;
import uiComponents.NavigationBar;
import uiComponents.TouchJTextField;
import uiComponents.uiInterfaces.ActivityPage;
import uiComponents.uiInterfaces.EnterActionPerformListener;
import uiComponents.uiInterfaces.TouchKeyboardHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import static java.lang.Math.abs;

public class MainFrame extends JFrame implements TouchKeyboardHandler
{
    private static MainFrame instance = null;

    private JPanel mainPanel, keyboardPanel;
    private KeyBoard keyboard;
    private NavigationBar navBar;

    private Vector<JPanel> panels;
    private boolean listenToKeyboardShow;
    private int currentPanelIndex;
    private boolean isFirstTimeToShow;

    private MainFrame()
    {
        listLookAndFeels();

        String nameSnippet = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel".toLowerCase();
        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : plafs)
        {
            if (info.getClassName().toLowerCase().contains(nameSnippet))
            {
                try
                {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                } catch (InstantiationException e)
                {
                    e.printStackTrace();
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e)
                {
                    e.printStackTrace();
                }
            }
        }

        listenToKeyboardShow = true;
        isFirstTimeToShow = true;
        currentPanelIndex = -1;
        panels = new Vector<>();
        initComponents();

    }

    public static MainFrame getInstance()
    {
        if (instance == null)
            instance = new MainFrame();
        return instance;
    }

    private void listLookAndFeels()
    {
        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : plafs)
        {
            System.out.println(info.getClassName());
        }
    }

    private void initComponents()
    {
        setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        setLocationRelativeTo(null);
        setUndecorated(true);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {

            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                for(JPanel page : panels)
                {
                    ((ActivityPage) page).beforeDispose();
                    ((ActivityPage) page).afterDispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e)
            {

            }

            @Override
            public void windowIconified(WindowEvent e)
            {

            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {

            }

            @Override
            public void windowActivated(WindowEvent e)
            {

            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {

            }
        });

        JLayeredPane lp = getLayeredPane();

        navBar = new NavigationBar(this);
        navBar.setLocation(0, getHeight()-navBar.getHeight());

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setSize(getSize());
        mainPanel.setLocation(0, 0);

        keyboardPanel = new JPanel();
        keyboardPanel.setLayout(null);
        keyboardPanel.setSize(getWidth(), getHeight() / 3);
        keyboardPanel.setLocation(0, 2 * getHeight() / 3 - navBar.getHeight());
        keyboardPanel.setBackground(Color.BLUE);
        keyboardPanel.setVisible(false);

        keyboard = new KeyBoard(this);
        keyboard.setLocation(0, 0);
        keyboardPanel.add(keyboard);


        lp.add(mainPanel, new Integer(1));
        lp.add(keyboardPanel, new Integer(2));
        lp.add(navBar, new Integer(3));
    }

    @Override
    public void showKeyBoard(TouchJTextField textField)
    {
        if (listenToKeyboardShow)
        {
            ((ActivityPage) panels.elementAt(currentPanelIndex)).beforeKeyboardShow();

            getKeyboard().setEnterActionPerformListener((EnterActionPerformListener) panels.elementAt(currentPanelIndex));
            getKeyboard().setTextField(textField);

            keyboardPanel.setLocation(0, 2 * getHeight() / 3 - (navBar.isVisible() ? navBar.getHeight() : 0));
            keyboardPanel.setVisible(true);
            int freeSpace = (getHeight() - keyboardPanel.getHeight() - (navBar.isVisible() ? navBar.getHeight() : 0));
            int offset = freeSpace / 2 - textField.getY();
            if (offset > 0) // it goes down
            {
                mainPanel.setLocation(0, 0);
            } else if (abs(offset) + freeSpace > mainPanel.getHeight())
            {
                mainPanel.setLocation(0, freeSpace - mainPanel.getHeight());
            } else
                mainPanel.setLocation(0, offset);
        }
    }

    @Override
    public void hideKeyBoard()
    {
        if (listenToKeyboardShow)
        {
            keyboardPanel.setVisible(false);
            mainPanel.setLocation(0, 0);
            ((ActivityPage) panels.elementAt(currentPanelIndex)).afterKeyboardDispose();
        }
    }

    public int addPanel(JPanel panel)
    {
        if (panel != null)
        {
            panels.add(panel);
            return panels.size() - 1;
        }
        return -1;
    }

    public void showPanel(int index)
    {
        if (currentPanelIndex != index)
        {
            if (currentPanelIndex != -1)
            {

                ((ActivityPage) (panels.elementAt(currentPanelIndex))).beforeDispose();
                mainPanel.remove(panels.elementAt(currentPanelIndex));
                panels.elementAt(currentPanelIndex).setVisible(false);
                ((ActivityPage) (panels.elementAt(currentPanelIndex))).afterDispose();
            }
            mainPanel.add(panels.elementAt(index));
            currentPanelIndex = index;
            ((ActivityPage) (panels.elementAt(currentPanelIndex))).beforeShow();
            if (isFirstTimeToShow)
            {
                isFirstTimeToShow = false;
                setVisible(true);
            }
            repaint();
            panels.elementAt(index).setVisible(true);
            panels.elementAt(index).requestFocusInWindow();
            panels.elementAt(index).updateUI();
            ((ActivityPage) (panels.elementAt(currentPanelIndex))).afterShow();
        }

    }

    public boolean isListenToKeyboardShow()
    {
        return listenToKeyboardShow;
    }

    public void setListenToKeyboardShow(boolean listenToKeyboardShow)
    {
        this.listenToKeyboardShow = listenToKeyboardShow;
    }

    public JPanel getKeyboardPanel()
    {
        return keyboardPanel;
    }

    public KeyBoard getKeyboard()
    {
        return keyboard;
    }

    public Dimension getMainPanelSize()
    {
        return mainPanel.getSize();
    }

    public void goPreviousPage()
    {
        if (currentPanelIndex > 0)
            showPanel(currentPanelIndex - 1);
    }

    public void goHomePage()
    {
        showPanel(0);
    }

    public void hideNavbar()
    {
        mainPanel.setSize(getSize());
        navBar.setVisible(false);
    }

    public void showNavbar()
    {
        mainPanel.setSize(getWidth(), getHeight() - navBar.getHeight());
        navBar.setVisible(true);
    }
}
