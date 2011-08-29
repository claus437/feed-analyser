package org.wooddog.servlets.model;

import org.wooddog.DateUtil;
import org.wooddog.dao.ArticleService;
import org.wooddog.dao.CompanyService;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.StockService;
import org.wooddog.dao.service.CompanyServiceDao;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.dao.service.StockServiceDao;
import org.wooddog.domain.Company;
import org.wooddog.domain.History;
import org.wooddog.domain.Scoring;
import org.wooddog.domain.Stock;
import org.wooddog.graph.Graph;
import org.wooddog.graph.LineGraph;
import org.wooddog.servlets.ConfigServlet;
import org.wooddog.servlets.PageAction;
import org.wooddog.servlets.actions.FindCompanyAction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyModelOld {
    /*
    public enum GraphElement {SCORE, SHARE, RECOMMENDATION}

    private Map<GraphElement, Color> graphColors;
    private Map<GraphElement, String> graphImages;
    private Map<GraphElement, Double> graphMinValues;
    private Map<GraphElement, Double> graphMidValues;
    private Map<GraphElement, Double> graphMaxValues;

    private ScoringService scoringService;
    private StockService stockService;
    private CompanyService companyService;
    private ArticleService articleService;

    private List<History> summaryList;
    private List<Company> foundCompanies;

    private File base;
    private Date date;
    private String companyName;
    private int companyId;
    private int summaryCount;
    private int graphCount;
    private int width;
    private int height;



    private ArticleModel articleModel;

    public CompanyModelOld() {
        base = new File(ConfigServlet.getRealPath("img"));

        graphColors = new HashMap<GraphElement, Color>();
        graphImages = new HashMap<GraphElement, String>();

        graphMinValues = new HashMap<GraphElement, Double>();
        graphMidValues = new HashMap<GraphElement, Double>();
        graphMaxValues = new HashMap<GraphElement, Double>();

        scoringService = ScoringServiceDao.getInstance();
        stockService = StockServiceDao.getInstance();
        companyService = CompanyServiceDao.getInstance();

        articleModel = new ArticleModel();
    }


    public void notifyHandlers(PageAction action) {
        if (action instanceof FindCompanyAction) {
            action.notifyHandlers();
            foundCompanies = ((FindCompanyAction) action).getResult();
        }
    }


    public String getImage(GraphElement element) {
        return "img/" + graphImages.get(element);
    }

    public void setColor(GraphElement element, String hexColor) {
        Color color;

        color = new Color(
                Integer.parseInt(hexColor.substring(1, 3), 16),
                Integer.parseInt(hexColor.substring(3, 5), 16),
                Integer.parseInt(hexColor.substring(5, 7), 16)
        );

        graphColors.put(element, color);
    }

    public String getColor(GraphElement element) {
        Color color;
        StringBuffer hex;


        color = graphColors.get(element);

        hex = new StringBuffer("#");
        hex.append(Integer.toHexString(color.getRed()));
        hex.append(Integer.toHexString(color.getGreen()));
        hex.append(Integer.toHexString(color.getBlue()));

        return hex.toString();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getSummaryCount() {
        return summaryCount;
    }

    public void setSummaryCount(int summaryCount) {
        this.summaryCount = summaryCount;
    }

    public int getGraphCount() {
        return graphCount;
    }

    public void setGraphCount(int graphCount) {
        this.graphCount = graphCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<History> getSummaryList() {
        return summaryList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getGraphMaxValue(GraphElement element) {
        return Double.toString(graphMaxValues.get(element));
    }

    public String getGraphMidValue(GraphElement element) {
        return Double.toString(graphMidValues.get(element));
    }

    public String getGraphMinValue(GraphElement element) {
        return Double.toString(graphMinValues.get(element));
    }

    public ArticleModel getArticleModel() {
        return articleModel;
    }

    public void load() {
        List<History> historyList;
        LineGraph lineGraph;
        String imageName;
        int count;

        if (summaryCount > graphCount) {
            count = summaryCount;
        } else {
            count = graphCount;
        }

        companyName = companyService.getCompany(companyId).getName();

        historyList = new ArrayList<History>();
        for (int i = 0; i < count; i++) {
            historyList.add(new History());
        }

        loadDates(historyList);
        loadScores(historyList);
        loadStocks(historyList);

        this.summaryList = new ArrayList<History>();
        this.summaryList.addAll(historyList.subList(0, summaryCount));


        for (GraphElement element : GraphElement.values()) {
            lineGraph = new LineGraph();
            lineGraph.setColor(graphColors.get(element));
            lineGraph.setData(getData(element, historyList, graphCount));

            imageName = element.name() + "_" + System.currentTimeMillis() + ".png";
            graphImages.put(element, imageName);

            createGraph(lineGraph, new File(base, imageName));

            graphMaxValues.put(element, (double) lineGraph.getHighestValue());
            graphMidValues.put(element, (double) lineGraph.getMiddleValue());
            graphMinValues.put(element, (double) lineGraph.getLowestValue());
        }
    }



    private void createGraph(LineGraph lineGraph, File imageName) {
        BufferedImage image;
        Graphics2D graphics;
        Graph graph;

        image = new BufferedImage(width, height,  BufferedImage.TYPE_INT_ARGB);

        graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(AlphaComposite.SrcOver);

        graph = new Graph();
        graph.setWidth(width);
        graph.setHeight(height);
        graph.setColumns(50);
        graph.setRows(10);
        graph.addGraph(lineGraph);

        graph.draw(graphics);

        try {
            ImageIO.write(image, "png", imageName);
        } catch (IOException x) {
            throw new RuntimeException("unable to store file " + imageName.getAbsolutePath());
        }
    }

    private void loadDates(List<History> historyList) {
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (History history : historyList) {
            history.date = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }

    private void loadScores(List<History> historyList) {
        Calendar calendar;
        double currentScore;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, historyList.size() * -1);

        currentScore = 0;

        for (History history : historyList) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            history.score = getScore(companyId, calendar.getTime());
            history.scoreChanged = history.score - currentScore;

            currentScore = history.score;
        }
    }

    private void loadStocks(List<History> historyList) {
        Company company;
        List<Stock> stockList;
        int index;
        History history;
        Stock stock;

        company = companyService.getCompany(companyId);
        stockList = stockService.getStockHistory(company.getName(), date, historyList.size());

        if (!stockList.isEmpty()) {
            if (DateUtil.sameDate(stockList.get(0).getDate(), date)) {
                stockList.set(0, stockList.get(0));
            }
        }

        for (index = 0; index < stockList.size(); index++) {
            history = historyList.get(index);
            stock = stockList.get(index);

            history.share = Double.parseDouble(stock.getValue().replaceAll(",", "")) / 100;
            history.shareChanged = Double.parseDouble(stock.getDiff().replaceAll(",", "")) / 100;
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

    private int getScore(int companyId, Date date) {
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

    private double[] getData(GraphElement graph, List<History> historyList, int count) {
        double[] values;

        values = new double[count];
        for (int i = 0; i < count; i++) {
            switch (graph) {
                case SCORE:
                    values[i] = historyList.get(i).score;
                    break;

                case SHARE:
                    values[i] = historyList.get(i).share;
                    break;

                case RECOMMENDATION:
                    values[i] = historyList.get(i).recommendation;
                    break;
            }
        }

        return values;
    }
      */
}
