package org.wooddog.servlets.model;

import org.junit.Before;
import org.junit.Test;
import org.wooddog.Config;
import org.wooddog.domain.History;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 28-08-11
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
public class CompanyPageControllerTest {

    @Before
    public void setup() {
        Config.load("org/wooddog/config/mysql.properties");
    }

    @Test
    public void testModel() {
        CompanyPageController subject;
        List<History> historyList;

        subject = new CompanyPageController();
        subject.setCompanyId(13);
        System.out.println(subject.getCompany().getName());

        subject.setDate(new Date());
        subject.setGraphCount(50);
        subject.setSummaryCount(10);
        subject.setGraphHeight(100);
        subject.setGraphWidth(200);
        subject.setGraphColor("SCORE", "#FF0000");
        subject.setGraphColor("SHARE", "#0000FF");
        subject.setGraphColor("RECOMMENDATION", "#00FF00");



        System.out.println(subject.getGraphs());

        historyList = subject.getSummaryList();

        for (History history : historyList) {
            System.out.println(history.getDate());
        }

    }
}
