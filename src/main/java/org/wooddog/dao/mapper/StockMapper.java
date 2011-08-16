package org.wooddog.dao.mapper;

import org.wooddog.domain.Stock;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public interface StockMapper {
    void insert(Stock stock);
    void update(Stock stock);
    List<Stock> selectByDate(Date date);
    List<Stock> selectStockHistory(Map map);
}
