package org.wooddog.dao;

import org.wooddog.domain.Scoring;

import java.util.Date;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 12-08-11
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public interface ScoringService {
    void store(Scoring score);
    List<Scoring> getScorings();
    Integer getLastScoredArticleIdForCompany(int companyId);
    List<Scoring> getScoringsInPeriodForCompany(int companyId, Date from, Date to);
    List<String> getKeyWords(int id);
    List<Scoring> getScoringsOlderThan(int companyId, Date date, int count);
}
