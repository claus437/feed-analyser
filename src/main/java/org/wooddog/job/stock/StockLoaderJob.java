package org.wooddog.job.stock;

import org.wooddog.job.Job;
import org.wooddog.stock.StockUpdater;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class StockLoaderJob implements Job {
    private StockUpdater updater;

    @Override
    public String getName() {
        return "stock loader";
    }

    @Override
    public void execute() {
        updater.update();
    }

    @Override
    public void terminate() {

    }
}
