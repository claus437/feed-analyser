package org.wooddog.dao;

import org.apache.bcel.generic.INSTANCEOF;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.mapper.ChannelMapper;
import org.wooddog.dao.mapper.ScoringMapper;
import org.wooddog.domain.Article;
import org.wooddog.domain.Scoring;

import javax.mail.internet.NewsAddress;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public class ScoringService {
    private static final ScoringService INSTANCE = new ScoringService();
    private SqlSessionFactory factory;

    private ScoringService() {
        factory = Service.getFactory();
    }

    public static ScoringService getInstance() {
        return INSTANCE;
    }

    public void store(Scoring score) {
        SqlSession session;

        session = factory.openSession();
        try {
            session.getMapper(ScoringMapper.class).store(score);
            session.commit();
        } finally {
            session.close();
        }
    }

    public Article getLastScoredArticle() {
        SqlSession session;
        Article article;

        session = factory.openSession();
        try {
            article = session.getMapper(ScoringMapper.class).getLastScoredArticle();
        } finally {
            session.close();
        }

        return article;
    }
}
