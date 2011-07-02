package org.wooddog.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 20:59
 * To change this template use File | Settings | File Templates.
 */
public class Scoring {
    private int id;
    private Date date;
    private int score;
    private int articleId;
    private int companyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
