package org.wooddog.dao.mapper;

import org.wooddog.domain.Article;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public interface ArticleMapper {
    void delete();
    void store(Article article);
    List<Article> selectFromId(int id);
    List<Article> getLastScoredArticle();
    Date getLatestPublishDate(String source);
    Article select(int id);
}
