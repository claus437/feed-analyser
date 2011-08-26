package org.wooddog.job.score;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.dao.ArticleService;
import org.wooddog.dao.CompanyService;
import org.wooddog.dao.ScoringService;
import org.wooddog.domain.Article;
import org.wooddog.domain.Company;
import org.wooddog.domain.Scoring;
import support.service.ArticleServiceStub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public class ScoreJobTest {
    private List<String> assertions;
    private ScoreJob subject;

    @Before
    public void setup() {
        assertions = new ArrayList<String>();

        subject = new ScoreJob();
        subject.setScoringService(new ScoringServiceMock());
        subject.setArticleService(new ArticleServiceMock());
        subject.setCompanyService(new CompanyServiceMock());
        subject.setScoreFinder(new ScoreFinderMock());
    }

    @Test
    public void testExecute() {
        String[] expected;

        subject.execute();

        System.out.println(assertions);

        expected = new String[] {
                "ScoringService.getLastScoredArticleIdForCompany(1)",
                "ArticleService.getArticles(10)",
                "ScoringService.getKeyWords(1)",
                "ScoreFinder.setKeywords([key_1_1, key_2_1])",
                "ScoreFinder.setKeywords(title description)",
                "ScoringService.store(aid:3, cId:1, score:100)",
                "ScoringService.getLastScoredArticleIdForCompany(2)",
                "ArticleService.getArticles(20)",
                "ScoringService.getKeyWords(2)",
                "ScoreFinder.setKeywords([key_1_2, key_2_2])",
                "ScoreFinder.setKeywords(title description)",
                "ScoringService.store(aid:3, cId:2, score:100)"
        };

        Assert.assertArrayEquals(expected, assertions.toArray());
    }

    class ScoringServiceMock implements ScoringService {
        @Override
        public void store(Scoring score) {
            assertions.add("ScoringService.store(aid:" + score.getArticleId() + ", cId:" + score.getCompanyId() + ", score:" + score.getScore() + ")");
        }

        @Override
        public List<Scoring> getScorings() {
            return null;
        }

        @Override
        public Integer getLastScoredArticleIdForCompany(int companyId) {
            assertions.add("ScoringService.getLastScoredArticleIdForCompany(" + companyId + ")");
            return companyId * 10;
        }

        @Override
        public List<Scoring> getScoringsInPeriodForCompany(int companyId, Date from, Date to) {
            return null;
        }

        @Override
        public List<String> getKeyWords(int id) {
            assertions.add("ScoringService.getKeyWords(" + id + ")");
            return Arrays.asList("key_1_" + id, "key_2_" + id);
        }

        @Override
        public List<Scoring> getScoringsOlderThan(int companyId, Date date, int count) {
            return null;
        }
    }

    class ArticleServiceMock extends ArticleServiceStub {

        @Override
        public List<Article> getArticlesFromId(int id) {
            Article article;

            assertions.add("ArticleService.getArticles(" + id + ")");

            article = new Article();
            article.setId(3);
            article.setTitle("title");
            article.setDescription("description");

            return Arrays.asList(article);
        }
    }

    class CompanyServiceMock implements CompanyService {
        @Override
        public List<Company> getCompanies() {
            List<Company> companies;
            Company company;

            companies = new ArrayList<Company>();

            company = new Company();
            company.setName("cmp 1");
            company.setId(1);
            companies.add(company);

            company = new Company();
            company.setName("cmp 2");
            company.setId(2);
            companies.add(company);

            return companies;
        }

        @Override
        public void storeCompany(Company company) {
        }

        @Override
        public Company getCompany(int companyId) {
            return null;
        }
    }

    class ScoreFinderMock extends ScoreFinder {
        @Override
        public void setKeywords(List<String> keywords) {
            assertions.add("ScoreFinder.setKeywords(" + keywords + ")");
        }

        @Override
        public int rate(String text) {
            assertions.add("ScoreFinder.setKeywords(" + text + ")");
            return 100;
        }
    }
}
