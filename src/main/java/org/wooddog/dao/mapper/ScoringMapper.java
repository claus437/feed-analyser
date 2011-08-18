package org.wooddog.dao.mapper;

import org.wooddog.domain.Scoring;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public interface ScoringMapper {
    void store(Scoring scoring);
    List<Scoring> getScorings();
    Integer getLastScoredArticleIdForCompanyId(int companyId);
    List<Scoring> getScoringsInPeriodForCompany(Map<Integer, Object> parameters);

    List<String> getKeyWordsByCompanyId(int companyId);
}
