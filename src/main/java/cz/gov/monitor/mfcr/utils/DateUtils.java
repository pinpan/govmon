package cz.gov.monitor.mfcr.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    public static Date getDateAsDateOnly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTwoDaysLater(Date date) {
        return getFewDaysLater(date, 2);
    }

    public static Date getFewDaysLater(Date date, int numDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numDays);
        return calendar.getTime();
    }
}
