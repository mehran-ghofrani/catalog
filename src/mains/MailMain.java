/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mains;

import uiComponents.pages.CatalogEmailSendingPage;
import uiComponents.pages.ImageCapturingPage;
import uiComponents.pages.MainFrame;

public class MailMain
{
    public static void main(String[] args)
    {
//        DBManager.getMyInstance();
        CatalogEmailSendingPage.getInstance();
        ImageCapturingPage.getInstance();
        MainFrame.getInstance().showPanel(ImageCapturingPage.getInstance().getPanelIndex());
//        MainFrame.getInstance().showPanel(CatalogEmailSendingPage.getInstance().getPanelIndex());

    }

}
