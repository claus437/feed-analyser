package org.wooddog.servlets.model;

import org.wooddog.DateUtil;
import org.wooddog.dao.CompanyService;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.StockService;
import org.wooddog.dao.service.CompanyServiceDao;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.dao.service.StockServiceDao;
import org.wooddog.domain.History;
import org.wooddog.domain.Scoring;
import org.wooddog.domain.Stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.wooddog.domain.History.Field.*;

public class HistoryModel {
    private ModelChangeNotifier notifier;
    private List<History> historyList;
    private ScoringService scoringService;
    private CompanyService companyService;
    private StockService stockService;
    private int companyId;
    private Date date;
    private int count;
    private boolean refresh;

    public HistoryModel() {
        notifier = new ModelChangeNotifier(this);
        scoringService = ScoringServiceDao.getInstance();
        companyService = CompanyServiceDao.getInstance();
        stockService = StockServiceDao.getInstance();
    }

    public void addChangeListener(ModelChangeListener listener) {
        notifier.addModelChangeListener(listener);
    }


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        if (notifier.notifyListeners(this.companyId, companyId)) {
            refresh = true;
        }

        this.companyId = companyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (notifier.notifyListeners(this.date, date)) {
            refresh = true;
        }

        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (notifier.notifyListeners(this.count, count)) {
            refresh = true;
        }

        this.count = count;
    }

    public List<History> getHistoryList() {
        if (refresh) {
            refresh();
            refresh = false;
        }

        return historyList;
    }

    public void refresh() {
        historyList = new ArrayList<History>();

        for (int i = 0; i < count; i++) {
            historyList.add(new History());
        }

        loadDates(historyList);
        loadScores(historyList);
        loadStocks(historyList);
    }

    private void loadDates(List<History> historyList) {
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (History history : historyList) {
            history.setDate(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }

    private void loadScores(List<History> historyList) {
        Calendar calendar;
        double currentScore;
        double score;
        double changed;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, historyList.size() * -1);

        currentScore = 0;

        for (History history : historyList) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            score = getScore(calendar.getTime());
            changed = score - currentScore;

            currentScore = score;

            history.set(SCORE, getScore(calendar.getTime()));
            history.set(SCORE_CHANGED, changed);
        }
    }

    private void loadStocks(List<History> historyList) {
        List<Stock> stockList;
        int index;
        History history;
        Stock stock;
        double share;
        double changed;
        String companyName;

        companyName = companyService.getCompany(companyId).getName();

        stockList = stockService.getStockHistory(companyName, date, historyList.size());

        if (!stockList.isEmpty()) {
            if (DateUtil.sameDate(stockList.get(0).getDate(), date)) {
                stockList.set(0, stockList.get(0));
            }
        }

        for (index = 0; index < stockList.size(); index++) {
            history = historyList.get(index);
            stock = stockList.get(index);

            // TODO: make nicer
            share = Double.parseDouble(stock.getValue().replaceAll(",", "")) / 100;
            changed = Double.parseDouble(stock.getDiff().replaceAll(",", "")) / 100;

            history.set(SHARE, share);
            history.set(SHARE_CHANGED, changed);
        }
    }

    private int getScore(Date date) {
        List<Scoring> scoreList;
        Date from;
        Date to;
        int score;

        from = getLowerDate(date);
        to = getUpperDate(date);
        score = 0;

        scoreList = scoringService.getScoringsInPeriodForCompany(companyId, from, to);
        for (Scoring scoring : scoreList) {
            score += scoring.getScore();
        }

        return score;
    }

    private Date getLowerDate(Date date) {
        Calendar lower;

        lower = Calendar.getInstance();
        lower.setTime(date);
        lower.set(Calendar.HOUR_OF_DAY, 0);
        lower.set(Calendar.MINUTE, 0);
        lower.set(Calendar.SECOND, 0);
        lower.set(Calendar.MILLISECOND, 0);

        return lower.getTime();
    }

    private Date getUpperDate(Date date) {
        Calendar upper;

        upper = Calendar.getInstance();
        upper.setTime(date);
        upper.set(Calendar.HOUR_OF_DAY, 23);
        upper.set(Calendar.MINUTE, 59);
        upper.set(Calendar.SECOND, 59);
        upper.set(Calendar.MILLISECOND, 999);

        return upper.getTime();
    }
}
