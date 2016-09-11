package mains;

import db.DBManager;
import uiComponents.pages.CatalogEmailSendingPage;
import uiComponents.pages.EntrancePage;
import uiComponents.pages.ImageCapturingPage;
import uiComponents.pages.MainFrame;
import uiComponents.pages.RetryPage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MailMain
{
    public static void main(String[] args)

    {
        DBManager.getMyInstance();

        EntrancePage.getInstance();
        ImageCapturingPage.getInstance();
        RetryPage.getInstance();
        CatalogEmailSendingPage.getInstance();

        MainFrame.getInstance().showPanel(RetryPage.getInstance().getPanelIndex());

    }

}
