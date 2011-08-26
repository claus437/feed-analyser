package org.wooddog.dao.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.ArticleService;
import org.wooddog.dao.Service;
import org.wooddog.dao.mapper.ArticleMapper;
import org.wooddog.domain.Article;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
public class ArticleServiceDao implements ArticleService {
    private static final ArticleServiceDao INSTANCE = new ArticleServiceDao();
    private SqlSessionFactory factory;

    private ArticleServiceDao() {
        factory = Service.getFactory();
    }

    public static ArticleServiceDao getInstance() {
        return INSTANCE;
    }

    public void deleteArticles() {
        SqlSession session;

        session = factory.openSession();
        try {
            session.getMapper(ArticleMapper.class).delete();
            session.commit();
        } finally {
            session.close();
        }
    }

    public void storeArticles(List<Article> articles) {
        SqlSession session;
        ArticleMapper mapper;

        session = factory.openSession();
        try {
            mapper = session.getMapper(ArticleMapper.class);

            for (Article article : articles) {
                mapper.store(article);
            }

            session.commit();
        } finally {
            session.close();
        }
    }

    public void storeArticle(Article article) {
        SqlSession session;
        ArticleMapper mapper;

        session = factory.openSession();
        try {
            mapper = session.getMapper(ArticleMapper.class);
            mapper.store(article);
            session.commit();
        } finally {
            session.close();
        }
    }

    public List<Article> getArticlesFromId(int id) {
        SqlSession session;
        ArticleMapper mapper;
        List<Article> articles;

        session = factory.openSession();
        try {
            mapper = session.getMapper(ArticleMapper.class);

            articles = mapper.selectFromId(id);
        } finally {
            session.close();
        }

        return articles;
    }

    public Article getLastScoredArticle() {
        SqlSession session;
        List<Article> articles;
        Article article;

        session = factory.openSession();
        try {
            articles = session.getMapper(ArticleMapper.class).getLastScoredArticle();
            if (articles.isEmpty()) {
                article = null;
            } else {
                article = articles.get(0);
            }
        } finally {
            session.close();
        }

        return article;
    }

    @Override
    public Date getLatestPublishDate(String source) {
        SqlSession session;
        Date published;

        session = factory.openSession();
        try {
            published = session.getMapper(ArticleMapper.class).getLatestPublishDate(source);
            if (published == null) {
                published = new Date(0);
            }
        } finally {
            session.close();
        }

        return published;
    }

    @Override
    public Article getArticle(int id) {
        SqlSession session;
        ArticleMapper mapper;
        Article article;

        session = factory.openSession();
        try {
            mapper = session.getMapper(ArticleMapper.class);

            article = mapper.select(id);
        } finally {
            session.close();
        }

        return article;
    }
}