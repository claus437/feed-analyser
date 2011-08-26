package org.wooddog.servlets.model;

import org.wooddog.domain.Article;
import org.wooddog.domain.Company;
import org.wooddog.domain.Graph;
import org.wooddog.domain.History;
import org.wooddog.domain.Scoring;
import org.wooddog.servlets.PageAction;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.wooddog.domain.History.Field.RECOMMENDATION;
import static org.wooddog.domain.History.Field.SCORE;
import static org.wooddog.domain.History.Field.SHARE;


public class CompanyModel2 {
    private ModelProperties properties;
    private CompanyController controller;
    private String companyName;
    private List<History> summaryList;
    private List<Article> articleList;
    private int companyId;
    private Date date;
    private Map<String, Graph> graphs;

    public static ModelProperties create() {
        return new ModelProperties();
    }

    private CompanyModel2(ModelProperties properties) {
        this.properties = properties;
        this.controller = new CompanyController(this);

        articleList = new ArrayList<Article>();
        graphs = new HashMap<String, Graph>();
    }

    public void loadHistory(Date date, int companyId) {
        this.date = date;
        this.companyId = companyId;

        updateCompany();
        updateHistory();
    }

    public int getCompanyId() {
        return companyId;
    }

    public Date getDate() {
        return date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<History> getSummaryList() {
        return summaryList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public Map<String, Graph> getGraphs() {
        return graphs;
    }

    public Map<Integer, Scoring> getArticleScore() {
        return new HashMap<Integer, Scoring>();
    }

    public void execute(PageAction action) {
        System.out.println(action);
    }

    private void updateCompany() {
        Company company;

        company = controller.loadCompany();
        this.companyName = company.getName();
    }

    private void updateHistory() {
        List<History> historyList;
        List<History> graphList;
        Graph graph;
        int count;

        count = maxOf(properties.summaryCount, properties.graphCount);
        historyList = controller.loadHistory(count);

        summaryList = historyList.subList(0, properties.summaryCount);

        graphList = historyList.subList(0, properties.graphCount);

        graphs.clear();

        graph = controller.createGraph(SCORE, graphList, controller.createImage(properties.graphWidth, properties.graphHeight));
        graphs.put(SCORE.name(), graph);

        graph = controller.createGraph(SHARE, graphList, controller.createImage(properties.graphWidth, properties.graphHeight));
        graphs.put(SHARE.name(), graph);

        graph = controller.createGraph(RECOMMENDATION, graphList, controller.createImage(properties.graphWidth, properties.graphHeight));
        graphs.put(RECOMMENDATION.name(), graph);
    }


    private int maxOf(int a, int b) {
        return a > b ? a : b;
    }

    Color getGraphColor(History.Field field) {
        StringBuffer hexColor;
        int[] tones;

        tones = new int[3];
        hexColor = new StringBuffer(properties.graphColors.get(field.name()));

        hexColor.delete(0, 1);

        for (int i = 0; i < tones.length; i++) {
            tones[i] = Integer.parseInt(hexColor.substring(0, 2), 16);
            hexColor.delete(0, 2);
        }

        return new Color(tones[0], tones[1], tones[2]);
    }

    public static class ModelProperties {
        public int summaryCount = 10;
        public int graphCount = 50;
        public int graphWidth = 550;
        public int graphHeight = 140;
        private Map<String, String> graphColors = new HashMap<String, String>();

        public void setGraphColor(String graph, String color) {
            graphColors.put(graph, color);
        }

        public CompanyModel2 initialize() {
            return new CompanyModel2(this);
        }
    }
}
