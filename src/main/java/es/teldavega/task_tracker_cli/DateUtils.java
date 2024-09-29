package es.teldavega.task_tracker_cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    private DateUtils() {

    }

    public static Date fromString(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.parse(dateString);
    }

    public static String fromDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.getDefault());
        return formatter.format(date);
    }
}
