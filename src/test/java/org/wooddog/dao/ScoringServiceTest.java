package org.wooddog.dao;


import junit.framework.Assert;
import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.domain.Scoring;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScoringServiceTest extends DaoTestCase {
    private static final Date JAN132011_101213 = new Date(1307952733000L);
    private ScoringServiceDao service = ScoringServiceDao.getInstance();


    @Test
    public void testGetScorings() {
        List<Scoring> scorings;
        Scoring scoring;

        execute(DatabaseOperation.CLEAN_INSERT, "GetScorings");

        scorings = service.getScorings();
        Assert.assertEquals(2, scorings.size());

        scoring = scorings.get(0);
        Assert.assertEquals(1, scoring.getId());
        Assert.assertEquals(2, scoring.getScore());
        Assert.assertEquals(3, scoring.getArticleId());
        Assert.assertEquals(4, scoring.getCompanyId());
        Assert.assertEquals(JAN132011_101213, scoring.getDate());
    }

    @Test
    public void storeScoring() throws Exception {
        Scoring scoring;
        ITable actual;
        ITable expected;

        resetPrimaryKey("SCORINGS");
        execute(DatabaseOperation.CLEAN_INSERT, "StoreScoring");

        scoring = new Scoring();
        scoring.setScore(8);
        scoring.setArticleId(9);
        scoring.setCompanyId(10);
        scoring.setDate(JAN132011_101213);
        service.store(scoring);

        actual = connection.createDataSet().getTable("SCORINGS");
        expected = getDataSet("StoreScoring.expected").getTable("SCORINGS");

        Assertion.assertEquals(expected, actual);
    }

    @Test
    public void lastScoredArticle() throws Exception {
        Integer articleId;

        execute(DatabaseOperation.CLEAN_INSERT, "LastScoredArticle");
        articleId = service.getLastScoredArticleIdForCompany(7);
        Assert.assertNotNull(articleId);
        Assert.assertEquals(6, articleId.intValue());
    }

    @Test
    public void testScoringsInPeriod() throws Exception {
        List<Scoring> scorings;
        Calendar from;
        Calendar to;

        execute(DatabaseOperation.CLEAN_INSERT, "ScoringsInPeriod");

        from  = Calendar.getInstance();
        from.set(2011, Calendar.JANUARY, 1, 0, 0, 0);
        from.set(Calendar.MILLISECOND, 0);

        to = Calendar.getInstance();
        to.set(2011, Calendar.JANUARY, 1, 23, 59, 59);
        to.set(Calendar.MILLISECOND, 59);

        scorings = service.getScoringsInPeriodForCompany(1, from.getTime(), to.getTime());
        Assert.assertEquals(2, scorings.size());

        for (Scoring scoring : scorings) {
            Assert.assertEquals(1, scoring.getCompanyId());
            Assert.assertTrue("scoring after given period " + scoring.getDate(), scoring.getDate().getTime() >= from.getTimeInMillis());
            Assert.assertTrue("scoring before given period", scoring.getDate().before(to.getTime()));
        }
    }



}
