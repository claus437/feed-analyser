package org.wooddog.domain;

import java.io.StreamCorruptedException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by IntelliJ IDEA.
* User: DENCBR
* Date: 25-08-11
* Time: 20:48
* To change this template use File | Settings | File Templates.
*/
public class History {
    public enum Field{SCORE, SCORE_CHANGED, SHARE, SHARE_CHANGED, RECOMMENDATION, RECOMMENDATION_CHANGED}
    private double[] values;

    private Date date;


    public History() {
        values = new double[Field.values().length];
    }

    public double get(Field field) {
        return values[field.ordinal()];
    }

    public void set(Field field, double value) {
        values[field.ordinal()] = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getScoreChanged() {
        return values[Field.SCORE_CHANGED.ordinal()];
    }

    public double getShare() {
        return values[Field.SHARE.ordinal()];
    }

    public double getShareChanged() {
        return values[Field.SHARE_CHANGED.ordinal()];
    }

    public double getRecommendation() {
        return values[Field.RECOMMENDATION.ordinal()];
    }

    public double getRecommendationChanged() {
        return values[Field.RECOMMENDATION_CHANGED.ordinal()];
    }

    public double getScore() {
        return values[Field.SCORE.ordinal()];
    }

    public static double[] get(Field field, List<History> historyList) {
        double values[];

        values = new double[historyList.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = historyList.get(i).get(field);
        }

        return values;
    }
}
