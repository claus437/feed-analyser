package support.service;

import org.wooddog.dao.ArticleService;
import org.wooddog.domain.Article;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 25-08-11
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class ArticleServiceStub implements ArticleService {
    @Override
    public void deleteArticles() {
    }

    @Override
    public void storeArticles(List<Article> articles) {
    }

    @Override
    public void storeArticle(Article article) {
    }

    @Override
    public List<Article> getArticlesFromId(int id) {
        return null;
    }

    @Override
    public Article getLastScoredArticle() {
        return null;
    }

    @Override
    public Date getLatestPublishDate(String source) {
        return null;
    }

    @Override
    public Article getArticle(int id) {
        return null;
    }
}
