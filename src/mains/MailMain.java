/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mains;

import db.DBManager;
import uiComponents.pages.Camera;
import uiComponents.pages.CatalogEmailSendingPage;

public class MailMain
{
    public static void main(String[] args)
    {
//        DBManager.getMyInstance();
//        new CatalogEmailSendingPage();
        Camera.getInstance();
    }

}
