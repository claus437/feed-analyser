package org.wooddog.dao;

import org.apache.ibatis.session.SqlSession;
import org.wooddog.dao.mapper.ArticleMapper;
import org.wooddog.domain.Article;

import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public interface ArticleService {
    void deleteArticles();
    void storeArticles(List<Article> articles);
    void storeArticle(Article article);
    List<Article> getArticlesFromId(int id);
    Article getLastScoredArticle();
    Date getLatestPublishDate(String source);
    Article getArticle(int id);
}
