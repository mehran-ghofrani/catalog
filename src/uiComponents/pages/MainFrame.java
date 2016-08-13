package uiComponents.pages;

import uiComponents.KeyBoard;
import uiComponents.TouchJTextField;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static java.lang.Math.abs;

public class MainFrame extends JFrame implements TouchKeyboardHandler
{
    private static MainFrame instance = null;

    private boolean listenToKeyboardShow;
    private JPanel mainPanel, keyboardPanel;
    private Vector<JPanel> panels;
    private int currentPanelIndex;
    private boolean isFirstTimeToShow;
    private KeyBoard keyboard;

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
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane lp = getLayeredPane();

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setSize(getSize());
        mainPanel.setLocation(0, 0);

        keyboardPanel = new JPanel();
        keyboardPanel.setLayout(null);
        keyboardPanel.setSize(mainPanel.getWidth(), mainPanel.getHeight() / 3);
        keyboardPanel.setLocation(0, 2 * mainPanel.getHeight() / 3);
        keyboardPanel.setBackground(Color.BLUE);
        keyboardPanel.setVisible(false);

        keyboard = new KeyBoard(this);
        keyboard.setLocation(0, 0);
        keyboardPanel.add(keyboard);

        lp.add(mainPanel, new Integer(1));
        lp.add(keyboardPanel, new Integer(2));
    }

    @Override
    public void showKeyBoard(TouchJTextField textField)
    {
        if (listenToKeyboardShow)
        {
            getKeyboard().setEnterActionPerformListener((EnterActionPerformListener) panels.elementAt(currentPanelIndex));
            getKeyboard().setTextField(textField);

            keyboardPanel.setVisible(true);
            int freeSpace = (mainPanel.getHeight() - keyboardPanel.getHeight());
            int offset = freeSpace / 2 - textField.getY();
//            System.out.println("offset: " + offset);
            if (offset > 0)
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
                mainPanel.remove(panels.elementAt(currentPanelIndex));
            mainPanel.add(panels.elementAt(index));
            currentPanelIndex = index;
            if (isFirstTimeToShow)
            {
                isFirstTimeToShow = false;
                setVisible(true);
            }
            repaint();
            panels.elementAt(index).requestFocusInWindow();
            panels.elementAt(index).updateUI();
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
}
