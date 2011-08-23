package org.wooddog.job.score;


import org.wooddog.Progress;
import org.wooddog.dao.ArticleService;
import org.wooddog.dao.service.ArticleServiceDao;
import org.wooddog.dao.CompanyService;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.service.CompanyServiceDao;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.domain.Article;
import org.wooddog.domain.Company;
import org.wooddog.domain.Scoring;
import org.wooddog.job.Job;
import java.util.Date;
import java.util.List;


public class ScoreJob implements Job {
    private CompanyService companyService = CompanyServiceDao.getInstance();
    private ScoringService scoringService = ScoringServiceDao.getInstance();
    private ArticleService articleService = ArticleServiceDao.getInstance();
    private ScoreFinder scoreFinder = new ScoreFinder();
    private Progress progress = new Progress();
    private boolean terminate;


    @Override
    public String getName() {
        return "keyword score";
    }

    @Override
    public void execute() {
        List<Company> companyList;

        progress.reset();

        companyList = companyService.getCompanies();
        progress.setNumberOfUnits(companyList.size());

        rateCompanies(companyList);
    }

    @Override
    public void terminate() {
        terminate = true;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setScoringService(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setScoreFinder(ScoreFinder scoreFinder) {
        this.scoreFinder = scoreFinder;
    }

    @Override
    public int progress() {
        return progress.getPercentDone();
    }

    private void rateCompanies(List<Company> companyList) {
        List<Article> articleList;


        for (Company company : companyList) {
            articleList = getArticlesToScore(company.getId());
            if (articleList.isEmpty()) {
                progress.setStepsPerUnit(companyList.size());
                progress.step();
            } else {
                progress.setStepsPerUnit(articleList.size());
                rateArticles(company, articleList);
            }

            if (terminate) {
                return;
            }
        }
    }

    private void rateArticles(Company company, List<Article> articleList) {
        List<String> keywords;
        Scoring score;
        int points;


        keywords = scoringService.getKeyWords(company.getId());
        scoreFinder.setKeywords(keywords);

        for (Article article : articleList) {
            points = scoreFinder.rate(article.getTitle() + " " + article.getDescription());

            score = new Scoring();
            score.setCompanyId(company.getId());
            score.setDate(new Date());
            score.setArticleId(article.getId());
            score.setScore(points);

            scoringService.store(score);
            if (terminate) {
                return;
            }

            progress.step();
        }
    }

    private List<Article> getArticlesToScore(int companyId) {
        Integer lastScored;
        List<Article> articleList;

        lastScored = scoringService.getLastScoredArticleIdForCompany(companyId);
        if (lastScored == null) {
            lastScored = 0;
        }

        articleList = articleService.getArticlesFromId(lastScored);

        return articleList;
    }
}
