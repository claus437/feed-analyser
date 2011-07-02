package org.wooddog.dao.mapper;

import org.wooddog.domain.Article;
import org.wooddog.domain.Scoring;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public interface ScoringMapper {
    void store(Scoring scoring);
    Article getLastScoredArticle();
}
