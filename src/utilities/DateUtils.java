package utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by online on 8/1/2016.
 */
public class DateUtils
{
    public static String convertToDateFormat(Timestamp time)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(time);
    }

    public static Timestamp getCurrentDate()
    {
        java.util.Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }
}
