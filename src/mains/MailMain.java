package mains;

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
//        DBManager.getMyInstance();
//        ImageCapturingPage.getInstance();

        EntrancePage.getInstance();
        ImageCapturingPage.getInstance();
        RetryPage.getInstance();
        CatalogEmailSendingPage.getInstance();

        try {
            RetryPage.getInstance().setImage(ImageIO.read(new File("C:\\Users\\Mactabi\\Desktop\\1.bmp")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainFrame.getInstance().showPanel(RetryPage.getInstance().getPanelIndex());
//        MainFrame.getInstance().showPanel(CatalogEmailSendingPage.getInstance().getPanelIndex());
//        MainFrame.getInstance().showPanel(RetryPage.getInstance().getPanelIndex());

    }

}
