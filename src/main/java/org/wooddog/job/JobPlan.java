package org.wooddog.job;

import java.util.Calendar;
import java.util.Date;

public class JobPlan {
    public static enum Frequency {
        /**
         * Runs every time the field is equally dividable by the value, eg "every 4 minute" will run every 4th minute,
         * based from 0. if the value is not dividable by the maximum value of the field, the last time frame within the
         * field will be shorter than the given amount. Eg. "every 25 minute" will yell at 25, 50 and 0 minute witand again when the
         * minute field goes to 0 even though only 4 minutes has pasted.
         */
        EVERY,

        /**
         * Runs only when the field matches the value, eg. "only 1 hour" will only run at 1 am.
         */
        ONCE
    }

    private Frequency hourlyFrequency;
    private Frequency minutelyFrequency;
    private Frequency secondFrequency;
    private int hour;
    private int minute;
    private int second;


    public JobPlan() {
        this.hourlyFrequency = Frequency.EVERY;
        this.minutelyFrequency = Frequency.EVERY;
        this.secondFrequency = Frequency.EVERY;
    }

    public Date getNextRun(Date time) {
        Calendar next;

        next = Calendar.getInstance();
        next.setTime(time);

        set(Calendar.HOUR_OF_DAY, next);
        set(Calendar.MINUTE, next);
        set(Calendar.SECOND, next);

        return next.getTime();
    }


    public void setHour(Frequency frequency, int hour) {
        this.hourlyFrequency = frequency;
        this.hour = hour;
    }

    public void setMinute(Frequency frequency, int minute) {
        this.minutelyFrequency = frequency;
        this.minute = minute;
    }

    public void setSecond(Frequency frequency, int second) {
        this.secondFrequency = frequency;
        this.second = second;
    }

    private void set(int field, Calendar time) {
        Date temp;
        int value;
        Frequency frequency;
        int actual;

        switch (field) {
            case Calendar.HOUR_OF_DAY:
                value = hour;
                frequency = hourlyFrequency;
                break;

            case Calendar.MINUTE:
                value = minute;
                frequency = minutelyFrequency;
                break;

            case Calendar.SECOND:
                value = second;
                frequency = secondFrequency;
                break;

            default:
                throw new IllegalArgumentException("unknown calendar field " + field);
        }

        switch (frequency) {
            case EVERY:
                if (value != 0) {
                    actual = time.get(field);
                    time.set(field, actual - (actual % value) + value);
                }

                break;

            case ONCE:
                temp = time.getTime();
                time.set(field, value);

                if (temp.compareTo(time.getTime()) >= 0) {
                    time.add(field, time.getMaximum(field) + 1);
                }
                break;
        }
    }
}
