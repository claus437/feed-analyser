package org.wooddog.stock;

import org.apache.log4j.Logger;
import org.wooddog.dao.StockService;
import org.wooddog.dao.service.StockServiceDao;
import org.wooddog.domain.Stock;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 16-08-11
 * Time: 08:42
 * To change this template use File | Settings | File Templates.
 */
public class StockUpdater {
    private static final Logger LOGGER = Logger.getLogger(StockUpdater.class);
    private StockService stockService = StockServiceDao.getInstance();
    private StockFetcher fetcher = new StockFetcher();

    public void update() {
        List<Stock> currentStockList;
        List<Stock> latestStockList;
        Date now;

        now = new Date();

        currentStockList = stockService.getStocksByDate(now);
        LOGGER.info("current stocks for " + now.toGMTString() + ": " + currentStockList.size() + " ");
        latestStockList = fetcher.getStocks();
        LOGGER.info("found stocks for " + now.toGMTString() + ": " + latestStockList.size() + " ");

        copyIds(currentStockList, latestStockList);

        stockService.storeStocks(latestStockList);
    }

    private void copyIds(List<Stock> source, List<Stock> target) {
        Stock stock;

        for (Stock idLessStock : target) {
            stock = findStock(source, idLessStock.getCompany());
            if (stock != null) {
                idLessStock.setId(stock.getId());
            }
        }
    }

    private Stock findStock(List<Stock> stockList, String company) {
        for (Stock stock : stockList) {
            if (company.equals(stock.getCompany())) {
                return stock;
            }
        }

        return null;
    }
}
