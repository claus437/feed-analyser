package org.wooddog.job.score;

import org.wooddog.domain.Article;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class ScoreFinder {
    private List<String> keywords;

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int rate(String text) {
        String content;

        content = text.toLowerCase();

        for (String keyword : keywords) {
            if (content.indexOf(keyword.toLowerCase()) != -1) {
                return 1;
            }
        }

        return 0;
    }
}
