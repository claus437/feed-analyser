package org.wooddog.job;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 17-08-11
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleTest {
    private JobPlan subject = new JobPlan();
    private Calendar time = new GregorianCalendar(2011, 1, 1, 0, 0, 0);
    private int timeField;
    private int timeStep;


    @Test
    public void testRunEveryHour() {
        timeField = Calendar.HOUR_OF_DAY;
        timeStep = 6;

        subject.setHour(JobPlan.Frequency.EVERY, timeStep);

        // actual     00 01    06 01    12 01    18 01
        run("HH dd", "06 01", "12 01", "18 01", "00 02");
    }

    @Test
    public void testRunEveryMinute() {
        timeField = Calendar.MINUTE;
        timeStep = 15;

        subject.setMinute(JobPlan.Frequency.EVERY, 15);

        // actual     00:00    00:15    00:30    00:45
        run("HH:mm", "00:15", "00:30", "00:45", "01:00");
    }

    @Test
    public void testRunEverySecond() {
        timeField = Calendar.SECOND;
        timeStep = 15;

        subject.setSecond(JobPlan.Frequency.EVERY, 15);

        // actual     00:00    00:15    00:30    00:45
        run("mm:ss", "00:15", "00:30", "00:45", "01:00");
    }


    @Test
    public void testRunOnceHour() {
        timeField = Calendar.HOUR_OF_DAY;
        timeStep = 6;

        subject.setHour(JobPlan.Frequency.ONCE, 13);

        // actual     00 01    06 01    12 01    18 01
        run("HH dd", "13 01", "13 01", "13 01", "13 02");
    }

    @Test
    public void testRunOnceMinute() {
        timeField = Calendar.MINUTE;
        timeStep = 15;

        subject.setMinute(JobPlan.Frequency.ONCE, 30);

        // actual     00:00    00:15    00:30    00:45
        run("HH:mm", "00:30", "00:30", "01:30", "01:30");
    }

    @Test
    public void testRunOnceSecond() {
        timeField = Calendar.SECOND;
        timeStep = 15;

        subject.setSecond(JobPlan.Frequency.ONCE, 30);

        // actual     00:00    00:15    00:30    00:45
        run("mm:ss", "00:30", "00:30", "01:30", "01:30");
    }


    private void run(String format, String... expectations) {
        SimpleDateFormat dateFormat;
        Date result;

        dateFormat = new SimpleDateFormat(format);
        for (int i = 0; i < expectations.length; i++) {
            result = subject.getNextRun(time.getTime());
            System.out.println(time.getTime() + " " + result);
            Assert.assertEquals("#" + i + " " + time.getTime(), expectations[i], dateFormat.format(result));
            time.add(timeField, timeStep);
        }
    }

    public void testSome() {
        int every;
        int next;
        int current;


        every = 7;
        current = 0;

        current = (int) System.currentTimeMillis();

        for (; current < 60; current++) {
            next = current - current % every + every;
            System.out.println(current + " " + next);
        }
    }
}
