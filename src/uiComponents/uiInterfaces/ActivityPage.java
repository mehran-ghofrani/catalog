package uiComponents.uiInterfaces;

/**
 * Created by online on 8/13/2016.
 */
public interface ActivityPage
{
    int getPanelIndex();
    void beforeShow();
    void afterShow();
    void beforeDispose();
    void afterDispose();
    void beforeKeyboardShow();
    void afterKeyboardDispose();
}
