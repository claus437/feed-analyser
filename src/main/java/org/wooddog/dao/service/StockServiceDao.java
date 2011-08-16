package org.wooddog.dao.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.Service;
import org.wooddog.dao.StockService;
import org.wooddog.dao.mapper.ScoringMapper;
import org.wooddog.dao.mapper.StockMapper;
import org.wooddog.domain.Scoring;
import org.wooddog.domain.Stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public class StockServiceDao implements StockService {
    private static final StockServiceDao INSTANCE = new StockServiceDao();
    private SqlSessionFactory factory;

    private StockServiceDao() {
        factory = Service.getFactory();
    }

    public static StockService getInstance() {
        return INSTANCE;
    }


    @Override
    public void storeStocks(List<Stock> stockList) {
        SqlSession session;
        StockMapper mapper;

        session = factory.openSession();
        mapper = session.getMapper(StockMapper.class);

        try {
            for (Stock stock : stockList) {
                if (stock.getId() == 0) {
                    System.out.println("inserting " + stock.getCompany());
                    mapper.insert(stock);
                } else {
                    mapper.update(stock);
                }
            }
            session.commit();
        } finally {
            session.close();
        }
    }


    @Override
    public List<Stock> getStockHistory(String company, Date from, int count) {
        SqlSession session;
        StockMapper mapper;
        List<Stock> historyList;
        Map map;

        map = new HashMap<String, Object>();
        map.put("company", company);
        map.put("from", raiseDate(from));
        map.put("count", count);

        session = factory.openSession();
        mapper = session.getMapper(StockMapper.class);

        historyList = new ArrayList<Stock>();

        try {
            historyList = mapper.selectStockHistory(map);
            session.commit();
        } finally {
            session.close();
        }

        return historyList;
    }

    @Override
    public List<Stock> getStocksByDate(Date date) {
        SqlSession session;
        StockMapper mapper;
        List<Stock> stockList;

        session = factory.openSession();
        mapper = session.getMapper(StockMapper.class);

        stockList = new ArrayList<Stock>();

        try {
            stockList = mapper.selectByDate(flattenDate(date));
            session.commit();
        } finally {
            session.close();
        }

        return stockList;
    }

    private Date flattenDate(Date date) {
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    private Date raiseDate(Date date) {
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

}
