import Utilities.WordExporter;
import db.DBManager;

/**
 * Created by online on 8/1/2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        DBManager dbManager = new DBManager();
//        for(int i = 0; i < 5; i++)
//        {
//            dbManager.addEmail("d_alihosseiny" + i + "@yahoo.com");
//            try
//            {
//                Thread.sleep(2000);
//            } catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }

        dbManager.listEmails();
        WordExporter.exportToWord(dbManager.getEmailEntities());
    }
}
