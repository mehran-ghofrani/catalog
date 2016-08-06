package javaapplication4;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by online on 8/6/2016.
 */
public class TouchJTextField extends JTextField
{
    private boolean isSensetiveToTouch;
    private JavaApplication4 parent;

    public TouchJTextField(JavaApplication4 parent)
    {
        this.parent = parent;
        initComponent();
    }

    public TouchJTextField(String text, JavaApplication4 parent)
    {
        super(text);
        this.parent = parent;
        initComponent();
    }

    private void initComponent()
    {
        isSensetiveToTouch = true;

        addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                parent.showKeyBoard();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                parent.hideKeyBoard();
            }
        });
    }
}
