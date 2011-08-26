package org.wooddog.dao.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.Service;
import org.wooddog.dao.mapper.ScoringMapper;
import org.wooddog.domain.Scoring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public class ScoringServiceDao implements ScoringService {
    private static final ScoringServiceDao INSTANCE = new ScoringServiceDao();
    private SqlSessionFactory factory;

    private ScoringServiceDao() {
        factory = Service.getFactory();
    }

    public static ScoringServiceDao getInstance() {
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

    public List<Scoring> getScorings() {
        SqlSession session;
        List<Scoring> scorings;

        session = factory.openSession();
        try {
            scorings = session.getMapper(ScoringMapper.class).getScorings();
        } finally {
            session.close();
        }

        return scorings;
    }

    public Integer getLastScoredArticleIdForCompany(int companyId) {
        SqlSession session;
        Integer articleId;

        session = factory.openSession();
        try {
            articleId = session.getMapper(ScoringMapper.class).getLastScoredArticleIdForCompanyId(companyId);
        } finally {
            session.close();
        }

        return articleId;
    }

    public List<Scoring> getScoringsInPeriodForCompany(int companyId, Date from, Date to) {
        SqlSession session;
        List<Scoring> scorings;
        Map<Integer, Object> parameters;

        parameters = Service.createParameterMap(companyId, from, to);

        session = factory.openSession();
        try {
            scorings = session.getMapper(ScoringMapper.class).getScoringsInPeriodForCompany(parameters);
        } finally {
            session.close();
        }

        return scorings;
    }

    @Override
    public List<String> getKeyWords(int companyId) {
        SqlSession session;
        List<String> keywords;

        session = factory.openSession();
        try {
            keywords = session.getMapper(ScoringMapper.class).getKeyWordsByCompanyId(companyId);
        } finally {
            session.close();
        }

        return keywords;
    }

    @Override
    public List<Scoring> getScoringsOlderThan(int companyId, Date date, int count) {
        SqlSession session;
        List<Scoring> scorings;
        Map<String, Object> parameters;

        parameters = new HashMap<String, Object>();
        parameters.put("companyId", companyId);
        parameters.put("date", date);
        parameters.put("count", count);

        session = factory.openSession();
        try {
            scorings = session.getMapper(ScoringMapper.class).getScoringsOlderThan(parameters);
        } finally {
            session.close();
        }

        return scorings;
    }
}
