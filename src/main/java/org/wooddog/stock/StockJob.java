package org.wooddog.stock;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import org.wooddog.IOUtil;
import org.wooddog.dao.StockService;
import org.wooddog.dao.service.StockServiceDao;
import org.wooddog.domain.Stock;

import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class StockJob extends Thread {
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

        nextRun = System.currentTimeMillis() - 1;

        do {
            if (System.currentTimeMillis() > nextRun) {
                updater.update();
                nextRun = getNextRun();
            } else {
                IOUtil.sleep(1000 * 60);
            }
        } while (!signal);
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
