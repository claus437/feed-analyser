package org.wooddog.servlets.model;

import org.wooddog.DateUtil;
import org.wooddog.dao.CompanyService;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.StockService;
import org.wooddog.dao.service.CompanyServiceDao;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.dao.service.StockServiceDao;
import org.wooddog.domain.Company;
import org.wooddog.domain.Graph;
import org.wooddog.domain.History;
import org.wooddog.domain.Scoring;
import org.wooddog.domain.Stock;
import org.wooddog.graph.LineGraph;
import org.wooddog.servlets.ConfigServlet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.wooddog.domain.History.Field.SCORE;
import static org.wooddog.domain.History.Field.SCORE_CHANGED;
import static org.wooddog.domain.History.Field.SHARE;
import static org.wooddog.domain.History.Field.SHARE_CHANGED;

public class CompanyController {
    private static int FILE_COUNT;
    private File base;
    private CompanyModel2 model;
    private ScoringService scoringService;
    private CompanyService companyService;
    private StockService stockService;

    public CompanyController(CompanyModel2 model) {
        this.model = model;
        this.base = new File(ConfigServlet.getRealPath("img"));

        scoringService = ScoringServiceDao.getInstance();
        companyService = CompanyServiceDao.getInstance();
        stockService = StockServiceDao.getInstance();
    }

    public List<History> loadHistory(int count) {
        List<History> historyList;

        historyList = new ArrayList<History>();
        for (int i = 0; i < count; i++) {
            historyList.add(new History());
        }

        loadDates(historyList);
        loadScores(historyList);
        loadStocks(historyList);

        return historyList;
    }

    public Graph createGraph(History.Field field, List<History> historyList, BufferedImage image) {
        LineGraph lineGraph;
        Graph graph;
        double[] data;
        File file;
        Color color;

        color = model.getGraphColor(field);

        data = History.get(field, historyList);
        file = generateFileName("png");

        lineGraph = new LineGraph();
        lineGraph.setColor(color);
        lineGraph.setData(data);
        lineGraph.setWidth(image.getWidth());
        lineGraph.setHeight(image.getHeight());
        lineGraph.draw(image.createGraphics());

        writeImage(image, "png", file);

        graph = new Graph();
        graph.setColor(color);
        graph.setImage(file.getName());
        graph.setMaxValue(lineGraph.getHighestValue());
        graph.setMidValue(lineGraph.getMiddleValue());
        graph.setMinValue(lineGraph.getLowestValue());

        return graph;
    }

    public BufferedImage createImage(int width, int height) {
        BufferedImage image;
        Graphics2D graphics;

        image = new BufferedImage(width, height,  BufferedImage.TYPE_INT_ARGB);

        graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(AlphaComposite.SrcOver);

        return image;
    }

    private void loadDates(List<History> historyList) {
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.setTime(model.getDate());

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
        calendar.setTime(model.getDate());
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

        stockList = stockService.getStockHistory(model.getCompanyName(), model.getDate(), historyList.size());

        if (!stockList.isEmpty()) {
            if (DateUtil.sameDate(stockList.get(0).getDate(), model.getDate())) {
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

    private int getScore(Date date) {
        List<Scoring> scoreList;
        Date from;
        Date to;
        int score;

        from = getLowerDate(date);
        to = getUpperDate(date);
        score = 0;

        scoreList = scoringService.getScoringsInPeriodForCompany(model.getCompanyId(), from, to);
        for (Scoring scoring : scoreList) {
            score += scoring.getScore();
        }

        return score;
    }

    private synchronized File generateFileName(String suffix) {
        FILE_COUNT ++;
        return new File(base,  System.currentTimeMillis() + "-" + "GRAPH@" + FILE_COUNT + ".png");
    }

    private void writeImage(BufferedImage image, String type, File file) {
        try {
            System.out.println("writing image " + file.getCanonicalPath());
            ImageIO.write(image, type, file);
        } catch (IOException x) {
            throw new RuntimeException("unable to store file " + file.getAbsolutePath());
        }
    }

    public Company loadCompany() {
        return companyService.getCompany(model.getCompanyId());
    }
}
