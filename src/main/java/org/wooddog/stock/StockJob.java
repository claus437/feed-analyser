package org.wooddog.stock;

import org.apache.log4j.Logger;
import org.wooddog.IOUtil;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class StockJob extends Thread {
    private static final Logger LOGGER = Logger.getLogger(StockJob.class);
    private static final StockJob INSTANCE = new StockJob();
    private boolean signal;
    private StockUpdater updater = new StockUpdater();

    public StockJob() {
    }

    public static StockJob getInstance() {
        return INSTANCE;
    }

    @Override
    public void run() {
        long nextRun;

        LOGGER.info("stock job started");

        nextRun = System.currentTimeMillis() - 1;

        do {
            if (System.currentTimeMillis() > nextRun) {
                updater.update();
                nextRun = getNextRun();

                LOGGER.info("next run " + new Date(nextRun).toGMTString());
            } else {
                IOUtil.sleep(1000 * 60);
            }
        } while (!signal);

        LOGGER.info("stock job terminated");
    }

    public void kill() {
        signal = true;
    }

    private long getNextRun() {
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }
}
