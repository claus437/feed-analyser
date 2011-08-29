package org.wooddog.servlets.model;

import org.wooddog.domain.Article;
import org.wooddog.domain.Company;
import org.wooddog.domain.Graph;
import org.wooddog.domain.History;
import org.wooddog.domain.Scoring;
import org.wooddog.servlets.PageAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.wooddog.domain.History.Field.RECOMMENDATION;
import static org.wooddog.domain.History.Field.SCORE;
import static org.wooddog.domain.History.Field.SHARE;


public class CompanyModel2 implements EventHandler {
    private EventQueue eventQueue;
    private CompanyControllerOld controller;
    private String companyName;
    private List<History> summaryList;
    private List<Article> articleList;
    private int companyId;
    private Date date;
    private Map<String, Graph> graphs;
    private int summaryCount = 10;
    private int graphCount = 50;
    private int graphWidth = 550;
    private int graphHeight = 140;
    private Map<String, String> graphColors;

    public CompanyModel2() {
        this.controller = new CompanyControllerOld(this);
        this.eventQueue = new EventQueue(this);

        articleList = new ArrayList<Article>();
        graphs = new HashMap<String, Graph>();
        graphColors = new HashMap<String, String>();

        eventQueue.addEvent(false, true, "COMPANY");
    }

    public int getSummaryCount() {
        return summaryCount;
    }

    public void setSummaryCount(int summaryCount) {
        eventQueue.addEvent(this.summaryCount, summaryCount, "HISTORY");
        this.summaryCount = summaryCount;
    }

    public int getGraphCount() {
        return graphCount;
    }

    public void setGraphCount(int graphCount) {
        eventQueue.addEvent(this.graphCount, graphCount, "HISTORY");
        this.graphCount = graphCount;
    }

    public int getGraphWidth() {
        return graphWidth;
    }

    public void setGraphWidth(int graphWidth) {
        eventQueue.addEvent(this.graphWidth, graphWidth, "GRAPH");
        this.graphWidth = graphWidth;
    }

    public int getGraphHeight() {
        return graphHeight;
    }

    public void setGraphColor(String name, String color) {
        eventQueue.addEvent(graphColors.get(name), color, "GRAPH");
        graphColors.put(name, color);
    }

    public String getGraphColor(String name) {
        return graphColors.get(name);
    }

    public void setGraphHeight(int graphHeight) {
        eventQueue.addEvent(this.graphHeight, graphHeight, "GRAPH");
        this.graphHeight = graphHeight;
    }


    public void setDate(Date date) {
        eventQueue.addEvent(this.date, date, "HISTORY");
        this.date = date;
    }

    public void setCompanyId(int companyId) {
        eventQueue.addEvent(this.companyId, companyId, "COMPANY");
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Date getDate() {
        return date;
    }

    public String getCompanyName() {
        eventQueue.update();
        return companyName;
    }

    public List<History> getSummaryList() {
        eventQueue.update();
        return summaryList;
    }

    public List<Article> getArticleList() {
        eventQueue.update();
        return articleList;
    }

    public Map<String, Graph> getGraphs() {
        eventQueue.update();
        return graphs;
    }

    public Map<Integer, Scoring> getArticleScore() {
        eventQueue.update();
        return new HashMap<Integer, Scoring>();
    }

    public void execute(PageAction action) {
        System.out.println(action);
    }

    private void updateCompany() {
        Company company;

        //company = controller.loadCompany();
        //this.companyName = company.getName();
    }

    private void updateHistory() {
        List<History> historyList;
        List<History> graphList;
        Graph graph;
        int count;

        count = maxOf(summaryCount, graphCount);
        historyList = null; //controller.loadHistory(count);

        summaryList = historyList.subList(0, summaryCount);

        graphList = historyList.subList(0, graphCount);

        graphs.clear();

        graph = controller.createGraph(SCORE, graphList, controller.createImage(graphWidth, graphHeight));
        graphs.put(SCORE.name(), graph);

        graph = controller.createGraph(SHARE, graphList, controller.createImage(graphWidth, graphHeight));
        graphs.put(SHARE.name(), graph);

        graph = controller.createGraph(RECOMMENDATION, graphList, controller.createImage(graphWidth, graphHeight));
        graphs.put(RECOMMENDATION.name(), graph);
    }

    private void updateGraph() {

    }


    private int maxOf(int a, int b) {
        return a > b ? a : b;
    }


    @Override
    public void update(Set<String> events) {
        boolean update;

        update = false;

        if (events.contains("COMPANY") || update) {
            updateCompany();
            update = true;
        }

        if (events.contains("HISTORY") || update) {
            updateHistory();
            update = true;
        }

        if (events.contains("GRAPH") || update) {
            updateGraph();
            update = true;
        }
    }
}
