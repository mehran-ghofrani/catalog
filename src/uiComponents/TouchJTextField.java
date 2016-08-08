package uiComponents;

import uiComponents.pages.MainFrame;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TouchJTextField extends JTextField
{
    private GhostText ghostText;


    private boolean isSensetiveToTouch;
    private MainFrame parent;
    private String text;

    public TouchJTextField(String text, String ghostText, MainFrame parent)
    {
        super(text);
        this.parent = parent;

        initComponent(text, ghostText);
    }

    private void initComponent(String text, String ghostText)
    {
        isSensetiveToTouch = true;

        this.ghostText = new GhostText(this, ghostText);


        addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                parent.showKeyBoard(TouchJTextField.this);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                parent.hideKeyBoard();
            }
        });

    }

    public String getValidText()
    {
        return ghostText == null || ghostText.isEmpty() ? "" : super.getText();
    }

    public GhostText getGhostText()
    {
        return ghostText;
    }

    public boolean isSensetiveToTouch()
    {
        return isSensetiveToTouch;
    }
}
