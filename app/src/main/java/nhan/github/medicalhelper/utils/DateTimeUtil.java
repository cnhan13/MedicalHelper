package nhan.github.medicalhelper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeUtil {

    public static final String DISPLAY_DATE_FORMAT = "dd/MM/yyyy";

    public static final int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
    public static final int DAYS_PER_WEEK = 7;

    public static Calendar displayDateToCalendar(String s) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.ENGLISH);
        try {
            cal.setTime(fmt.parse(s));
            clearTimeInDay(cal);
        } catch (ParseException e) {
            cal = null;
        }
        return cal;
    }

    public static String calendarToDisplayDate(Calendar cal) {
        if (cal == null) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.ENGLISH);
        return fmt.format(cal.getTime());
    }

    public static long millisToDaysCeil(long millis) {
        return (long) Math.ceil(millis / MILLISECONDS_PER_DAY);
    }

    public static long daysToWeeksQuotient(long days) {
        return (long) Math.floor(days / DAYS_PER_WEEK);
    }

    public static long daysToWeeksRemainder(long days) {
        return days % DAYS_PER_WEEK;
    }

    public static long millisToWeeksQuotient(long millis) {
        return daysToWeeksQuotient(millisToDaysCeil(millis));
    }

    public static long millisToWeeksDaysRemainder(long millis) {
        return daysToWeeksRemainder(millisToDaysCeil(millis));
    }

    public static void clearTimeInDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
