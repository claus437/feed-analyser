package org.wooddog;

import org.junit.Test;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.domain.Scoring;
import org.wooddog.servlets.model.TrentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 12-08-11
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
public class TrentModelTest {



    @Test
    public void testScore() {
        TrentModel model;
        List<String> scores;
        Calendar date;

        date = Calendar.getInstance();
        date.set(2011, 8, 12, 14, 30, 10);

        model =  new TrentModel(date.getTime(), 4);
        model.setScoringService(new MockScoringService(new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {2, 2, 2}}));

        scores = model.getScores(1);
        for (String score : scores) {
            System.out.println(score);
        }
    }

    class MockScoringService implements ScoringService {
        private int[][] scores;
        private int index;

        MockScoringService(int[]... scores) {
            this.scores = scores;
        }

        @Override
        public void store(Scoring score) {
        }

        @Override
        public List<Scoring> getScorings() {
            return null;
        }

        @Override
        public Integer getLastScoredArticleIdForCompany(int companyId) {
            return null;
        }

        @Override
        public List<Scoring> getScoringsInPeriodForCompany(int companyId, Date from, Date to) {
            List<Scoring> scoringList;
            Scoring scoring;

            scoringList = new ArrayList<Scoring>();
            for (int i = 0; i < scores[index].length; i++) {
                scoring = new Scoring();
                scoring.setScore(scores[index][i]);
                scoringList.add(scoring);
            }

            index ++;
            return scoringList;
        }
    }
}


