package org.wooddog.dao;

import org.apache.ibatis.session.SqlSession;
import org.wooddog.dao.mapper.ArticleMapper;
import org.wooddog.domain.Article;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public interface ArticleService {
    public void deleteArticles();
    public void storeArticles(List<Article> articles);
    public void storeArticle(Article article);
    public List<Article> getArticlesFromId(int id);
    public Article getLastScoredArticle();
}
