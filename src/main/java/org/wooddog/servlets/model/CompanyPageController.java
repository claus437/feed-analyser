package org.wooddog.servlets.model;

import org.wooddog.domain.Article;
import org.wooddog.domain.Company;
import org.wooddog.domain.History;
import org.wooddog.domain.Graph;
import org.wooddog.servlets.PageAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 27-08-11
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class CompanyPageController implements ModelChangeListener {
    private boolean graphRefreshHistory;

    private int summaryCount;
    private int graphCount;
    private CompanyModel companyModel;
    private HistoryModel historyModel;

    private GraphModel graphModel;

    public CompanyPageController() {
        companyModel = new CompanyModel();
        historyModel = new HistoryModel();
        graphModel = new GraphModel();

        historyModel.addChangeListener(this);
    }

    public int getSummaryCount() {
        return this.summaryCount;
    }

    public void setSummaryCount(int count) {
        this.summaryCount = count;
        historyModel.setCount(max(this.summaryCount, this.graphCount));
    }

    public int getGraphCount() {
        return this.graphCount;
    }

    public void setGraphCount(int count) {
        this.graphCount = count;
        historyModel.setCount(max(this.summaryCount, this.graphCount));
    }

    public int getCompanyId() {
        return companyModel.getCompanyId();
    }

    public void setCompanyId(int companyId) {
        companyModel.setCompanyId(companyId);
        historyModel.setCompanyId(companyId);
    }

    public Company getCompany() {
        return companyModel.getCompany();
    }

    public Date getDate() {
        return historyModel.getDate();
    }

    public void setDate(Date date) {
        historyModel.setDate(date);
    }

    public int getGraphWidth() {
        return graphModel.getWidth();
    }

    public void setGraphWidth(int graphWidth) {
        graphModel.setWidth(graphWidth);
    }

    public int getGraphHeight() {
        return graphModel.getHeight();
    }

    public void setGraphHeight(int graphHeight) {
        graphModel.setHeight(graphHeight);
    }

    public void setGraphColor(String graph, String color) {
        graphModel.setGraphColor(graph, color);
    }


    public List<History> getSummaryList() {
        return historyModel.getHistoryList().subList(0, summaryCount);
    }

    public Map<String, Graph> getGraphs() {
        if (graphRefreshHistory) {
            graphModel.setHistoryList(historyModel.getHistoryList());
            graphRefreshHistory = false;
        }

        return graphModel.getGraphs();
    }

    public List<Article> getArticleList() {
        return new ArrayList<Article>();
    }

    public void execute(PageAction action) {

    }


    @Override
    public void onChange(Object src, Object oldValue, Object newValue) {
        if (src == historyModel) {
            graphRefreshHistory = true;
        }
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

}
