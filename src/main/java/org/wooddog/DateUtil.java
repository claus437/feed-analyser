package org.wooddog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 16-08-11
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {
    public static final int YEAR = 7;
    public static final int MONTH = 6;
    public static final int DAY = 5;
    public static final int HOUR = 4;
    public static final int MINUTE = 3;
    public static final int SECOND = 2;
    public static final int MILLISECOND = 1;


    public static boolean sameDate(Date a, Date b) {
        Calendar c1;
        Calendar c2;

        c1 = Calendar.getInstance();
        c1.setTime(a);

        c2 = Calendar.getInstance();
        c2.setTime(b);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
            && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
            && c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }

    public static Date flatten(Date date, int field) {
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (field >= YEAR) {
            calendar.set(Calendar.YEAR, 0);
        }

        if (field >= MONTH) {
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
        }

        if (field >= DAY) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        if (field >= HOUR) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }

        if (field >= MINUTE) {
            calendar.set(Calendar.MINUTE, 0);
        }

        if (field >= SECOND) {
            calendar.set(Calendar.SECOND, 0);
        }

        if (field >= MILLISECOND) {
            calendar.set(Calendar.MILLISECOND, 0);
        }

        return date;
    }
}
