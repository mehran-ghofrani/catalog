package mains;

import uiComponents.pages.CatalogEmailSendingPage;
import uiComponents.pages.EntrancePage;
import uiComponents.pages.ImageCapturingPage;
import uiComponents.pages.MainFrame;

public class MailMain
{
    public static void main(String[] args)

    {
//        DBManager.getMyInstance();
//        ImageCapturingPage.getInstance();

        EntrancePage.getInstance();
        ImageCapturingPage.getInstance();
        CatalogEmailSendingPage.getInstance();
        MainFrame.getInstance().showPanel(EntrancePage.getInstance().getPanelIndex());
//        MainFrame.getInstance().showPanel(CatalogEmailSendingPage.getInstance().getPanelIndex());
//        MainFrame.getInstance().showPanel(EntrancePage.getInstance().getPanelIndex());

    }

}
