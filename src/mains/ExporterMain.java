package mains;

import db.DBManager;
import utilities.WordExporter;

/**
 * Created by online on 8/1/2016.
 */
public class ExporterMain
{
    public static void main(String[] args)
    {
        DBManager dbManager = DBManager.getMyInstance();
//        dbManager.listEmails();
        WordExporter.exportToWord(dbManager.getEmailEntities());
    }
}
