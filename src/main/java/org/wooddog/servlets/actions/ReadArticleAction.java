package org.wooddog.servlets.actions;

import org.wooddog.dao.ArticleService;
import org.wooddog.dao.service.ArticleServiceDao;
import org.wooddog.domain.Article;
import org.wooddog.servlets.PageAction;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 25-08-11
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class ReadArticleAction implements PageAction {
    public static String PARAM_ARTICLE_ID = "articleId";
    private ArticleService articleService;
    private Article article;
    private Map<String, String[]> parameters;

    public ReadArticleAction() {
        articleService = ArticleServiceDao.getInstance();
    }

    @Override
    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void execute() {
        int articleId;

        if (!parameters.containsKey(PARAM_ARTICLE_ID)) {
            throw new RuntimeException("required parameter \"" + PARAM_ARTICLE_ID + "\" is missing");
        }

        try {
            articleId = Integer.parseInt(parameters.get(PARAM_ARTICLE_ID)[0]);
        } catch (NumberFormatException x) {
            throw new RuntimeException("value for parameter \"" + PARAM_ARTICLE_ID + "\" is not a valid integer ", x);
        }

        article = articleService.getArticle(articleId);

        if (article == null) {
            throw new RuntimeException("found no articles with id " + articleId);
        }
    }

    public Article getArticle() {
        return article;
    }
}
