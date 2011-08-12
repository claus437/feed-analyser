package org.wooddog;

import org.apache.log4j.Logger;
import org.wooddog.dao.ArticleService;
import org.wooddog.dao.CompanyService;
import org.wooddog.dao.ScoringService;
import org.wooddog.domain.Article;
import org.wooddog.domain.Company;
import org.wooddog.domain.Scoring;

import java.util.Date;
import java.util.List;

public class ScoreRunner extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ScoreRunner.class);
    private static final ScoreRunner INSTANCE = new ScoreRunner();
    private ScoringService scoreService = ScoringService.getInstance();
    private ArticleService articleService = ArticleService.getInstance();
    private boolean signal;
    private ScoreRator rator;

    ScoreRunner() {
    }

    public static ScoreRunner getInstance() {
        return INSTANCE;
    }

    public void run() {
        List<Company> companies;

        while (!signal) {
            companies = CompanyService.getInstance().getCompanies();
            rator = ScoreRatorFactory.getScoreRator();

            for (int i = 0; !signal && i < companies.size(); i++) {
                LOGGER.debug("rating " + companies.get(i));
                rate(companies.get(i));
            }

            sleep();
        }
    }

    public void kill() {
        signal = true;
    }

    private void sleep() {
        long end;

        end = System.currentTimeMillis() + (5 * 1000);
        while (System.currentTimeMillis() < end && !signal) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException x) {
                throw new RuntimeException(x);
            }
        }
    }

    private void rate(Company company) {
        List<Article> articles;
        Integer lastScoredArticleId;
        Scoring score;
        Article article;

        lastScoredArticleId = scoreService.getLastScoredArticleIdForCompany(company.getId());
        if (lastScoredArticleId == null) {
            lastScoredArticleId = 0;
        }

        articles =  articleService.getArticlesFromId(lastScoredArticleId);
        for (int i = 0; !signal && i < articles.size(); i++) {
            article = articles.get(i);

            score = new Scoring();
            score.setCompanyId(company.getId());
            score.setArticleId(article.getId());
            score.setDate(new Date());
            score.setScore(rator.rate(company.getName(), article.getTitle() + " " + article.getDescription()));

            LOGGER.info("rated article " + article.getId() + " " + article.getPublished() + " for company " + company.getName() + " scored: " + score.getScore());
            scoreService.store(score);
        }
    }
}
