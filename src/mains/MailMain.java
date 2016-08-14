package mains;

import uiComponents.pages.CatalogEmailSendingPage;
import uiComponents.pages.ImageCapturingPage;
import uiComponents.pages.MainFrame;

public class MailMain
{
    public static void main(String[] args)

    {
//        DBManager.getMyInstance();
//        ImageCapturingPage.getInstance();
//        MainFrame.getInstance().showPanel(new EntrancePage(MainFrame.getInstance().getSize()).getPanelIndex());
//        MainFrame.getInstance().showPanel(EntrancePage.getInstance().getPanelIndex());

        CatalogEmailSendingPage.getInstance();
        ImageCapturingPage.getInstance();
        MainFrame.getInstance().showPanel(ImageCapturingPage.getInstance().getPanelIndex());
//        MainFrame.getInstance().showPanel(CatalogEmailSendingPage.getInstance().getPanelIndex());

    }

}
