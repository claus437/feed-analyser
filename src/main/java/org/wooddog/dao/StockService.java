package org.wooddog.dao;

import org.wooddog.domain.Stock;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public interface StockService {
    void storeStocks(List<Stock> stockList);
    List<Stock> getStockHistory(String company, Date from, int count);
    List<Stock> getStocksByDate(Date date);
}
