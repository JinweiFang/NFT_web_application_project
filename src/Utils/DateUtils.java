package Utils;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class DateUtils {
    public static String formatDate(long dateInMillis) {
        Date date = new Date(dateInMillis);
        return DateFormat.getDateInstance().format(date);
    }

    public static String formateDateTime(long dateInMillis) {
        Date date = new Date(dateInMillis);
        return DateFormat.getDateTimeInstance().format(date);
    }

    public static long generateUnixTimestamp(){
        return generateUnixTimestamp(0);
    }

    public static long generateUnixTimestamp(long addMinutes) {
        long minutes = addMinutes * 60;
        long ut = System.currentTimeMillis() / 1000L;
        return ut + minutes;
    }

    public static Date covertUnixTimestampToDate(long timestamp){
        Date date = Date.from(Instant.ofEpochSecond(timestamp));
        return date;
    }
}
