package org.wooddog.stock;

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
    private StockService stockService = StockServiceDao.getInstance();
    private StockFetcher fetcher = new StockFetcher();

    public void update() {
        List<Stock> currentStockList;
        List<Stock> latestStockList;
        Date now;

        now = new Date();

        currentStockList = stockService.getStocksByDate(now);
        latestStockList = fetcher.getStocks();

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
