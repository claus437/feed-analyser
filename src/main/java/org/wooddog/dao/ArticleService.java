package org.wooddog.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.mapper.ArticleMapper;
import org.wooddog.domain.Article;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
public class ArticleService {
    private static final ArticleService INSTANCE = new ArticleService();
    private SqlSessionFactory factory;

    private  ArticleService() {
        factory = Service.getFactory();
    }

    public static ArticleService getInstance() {
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

}