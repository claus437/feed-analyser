package org.wooddog.servlets;

import org.wooddog.dao.CompanyService;
import org.wooddog.dao.ScoringService;
import org.wooddog.domain.Company;
import org.wooddog.domain.Scoring;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 03-07-11
 * Time: 19:32
 * To change this template use File | Settings | File Templates.
 */
public class TrendModel {
    private List<Company> companyList;

    public TrendModel() {
        companyList = CompanyService.getInstance().getCompanies();
    }

    public List<Company> getCompanies() {
        return companyList;
    }

    public Scoring getScore(int companyId, int dayOffset) {
        List<Scoring> scoringList;
        Calendar from;
        Calendar to;
        int score;

        from = Calendar.getInstance();
        from.set(Calendar.DAY_OF_MONTH, dayOffset);
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        from.set(Calendar.MILLISECOND, 0);

        to = Calendar.getInstance();
        to.set(Calendar.DAY_OF_MONTH, dayOffset);
        to.set(Calendar.HOUR_OF_DAY, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);
        to.set(Calendar.MILLISECOND, 999);

        scoringList = ScoringService.getInstance().getScoringsInPeriodForCompany(companyId, from.getTime(), to.getTime());
        for (Scoring scoring : scoringList) {


        }

        return null;
    }

}