package org.wooddog;

import org.junit.Test;
import org.wooddog.job.stock.StockFetcher;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class BorsenC20FetcherTest {

    @Test
    public void testFetch() {
        StockFetcher fetcher;

        fetcher = new StockFetcher();
        fetcher.getStocks();

    }
}
