package org.wooddog.servlets.model;

import org.wooddog.dao.ScoringService;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.domain.Scoring;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 12-08-11
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class TrentModel {
    private int historyCount;
    private Date date;
    private ScoringService scoringService;

    public TrentModel(Date date, int historyCount) {
        this.date = date;
        this.historyCount = historyCount;
        this.scoringService = ScoringServiceDao.getInstance();
    }

    public ScoringService getScoringService() {
        return scoringService;
    }

    public void setScoringService(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    public List<String> getMonths() {
        List<String> months;
        Calendar calendar;
        SimpleDateFormat format;

        format = new SimpleDateFormat("MMM dd", Locale.UK);
        calendar = Calendar.getInstance();
        calendar.setTime(date);

        months = new ArrayList<String>();
        for (int i = 0; i < historyCount; i++) {
            months.add(format.format(calendar.getTime()).toLowerCase());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        return months;
    }

    public List<String> getScores(int companyId) {
        List<String> scores;
        int score;
        Calendar calendar;
        int currentScore;
        DecimalFormat format;
        String direction;

        format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        scores = new ArrayList<String>();
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, historyCount * -1);

        currentScore = 0;

        for (int i = 0; i < historyCount; i++) {
            score = getScore(companyId, calendar.getTime());
            direction = score > currentScore ? "+" : "-";
            scores.add(direction + " / " + score);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            currentScore = score;
        }

        Collections.reverse(scores);
        return scores;
    }

    private double getDifference(int oldScore, int newScore) {
        double diff;

        diff = newScore - oldScore;
        return diff / oldScore * 100;
    }

    public List<String> getStocks(int companyId) {
        List<String> stocks;

        stocks = new ArrayList<String>();
        for (int i = 0; i < historyCount; i++) {
            stocks.add("n/a");
        }

        return stocks;
    }

    public List<String> getRecommendations(int companyId) {
        List<String> recommendations;

        recommendations = new ArrayList<String>();
        for (int i = 0; i < historyCount; i++) {
            recommendations.add("n/a");
        }

        return recommendations;
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


}
