package org.wooddog;

import org.wooddog.dao.ArticleService;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.Service;
import org.wooddog.domain.Article;
import org.wooddog.domain.Company;
import org.wooddog.domain.Scoring;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class SimpleScoreRator extends Thread {
    private Company company;
    private boolean signal;
    private int lastRated;

    public SimpleScoreRator(Company company) {
        this.company = company;
    }

    private void init() {
        Article article;
        ScoringService service;

        service = ScoringService.getInstance();

        article = service.getLastScoredArticle();
        lastRated = article == null ? 0 : article.getId();
    }

    public void run() {
        List<Article> articles;
        Scoring scoring;
        ScoringService scoringService;
        ArticleService articleService;

        articleService = ArticleService.getInstance();
        scoringService = ScoringService.getInstance();

        while (!signal) {
            articles = articleService.getArticlesFromId(lastRated);
            for (int i = 0; i <articles.size() && !signal; i++) {
                scoring = rate(articles.get(i));
                if (scoring != null) {
                    scoringService.store(scoring);
                }
                lastRated = articles.get(i).getId();
            }
        }
    }


    private Scoring rate(Article article) {
        Scoring scoring;

        scoring = null;

        if (article.getDescription().indexOf(company.getName()) != -1) {
            scoring = new Scoring();
            scoring.setArticleId(article.getId());
            scoring.setDate(new Date());
            scoring.setScore(0);
            scoring.setCompanyId(company.getId());
        }

        return scoring;
    }
}
