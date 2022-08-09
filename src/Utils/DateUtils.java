package Utils;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {
    public static String formatDate(long dateInMillis) {
        Date date = new Date(dateInMillis);
        return DateFormat.getDateInstance().format(date);
    }

    public static  String formateDateTime(long dateInMillis) {
        Date date = new Date(dateInMillis);
        return DateFormat.getDateTimeInstance().format(date);
    }
}
