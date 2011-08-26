package org.wooddog.servlets.model;

import org.wooddog.dao.ArticleService;
import org.wooddog.dao.service.ArticleServiceDao;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.domain.Article;
import org.wooddog.domain.Scoring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 25-08-11
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
public class ArticleModel {
    private ArticleService articleService;
    private ScoringServiceDao scoringService;
    private int count;
    private int companyId;
    private List<Article> articleList;
    private Map<Integer, Scoring> articleScoreMap;


    public ArticleModel() {
        articleService = ArticleServiceDao.getInstance();
        scoringService = ScoringServiceDao.getInstance();

        articleList = new ArrayList<Article>();
        articleScoreMap = new HashMap<Integer, Scoring>();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCompanyId(int id) {
        this.companyId = id;

    }

    public void loadScoredArticlesOlderThan(Date date) {
        List<Scoring> scoringList;
        Article article;


        articleList.clear();
        articleScoreMap.clear();

        scoringList = scoringService.getScoringsOlderThan(companyId, date, count);

        for (Scoring score : scoringList) {
            article = articleService.getArticle(score.getArticleId());

            articleList.add(article);
            articleScoreMap.put(article.getId(), score);
        }
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public Map<Integer, Scoring> getArticleScoreMap() {
        return articleScoreMap;
    }

}
