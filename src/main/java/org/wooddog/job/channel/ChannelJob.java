package org.wooddog.job.channel;

import org.wooddog.Progress;
import org.wooddog.dao.ArticleService;
import org.wooddog.dao.service.ArticleServiceDao;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;
import org.wooddog.job.Job;
import org.wooddog.job.channel.fetcher.ChannelFetcher;

import java.util.Date;
import java.util.List;

public class ChannelJob implements Job {
    private ChannelServiceDao channelService = ChannelServiceDao.getInstance();
    private ArticleService articleService = ArticleServiceDao.getInstance();
    private Progress progress = new Progress();
    private boolean terminate;



    @Override
    public String getName() {
        return "channel fetcher";
    }

    @Override
    public void execute() {
        List<Channel> channelList;
        List<Article> articles;
        ChannelFetcher fetcher;
        Date latest;

        progress.reset();

        channelList = channelService.getChannels();
        if (channelList.isEmpty()) {
            progress.done();
        }

        progress.setNumberOfUnits(channelList.size());

        for (Channel channel : channelList) {

            latest = articleService.getLatestPublishDate(channel.getUrl().toExternalForm());

            fetcher = ChannelFetcherFactory.getFetcher(channel.getType());
            articles = fetcher.fetch(channel, latest);

            articleService.storeArticles(articles);

            if (terminate) {
                break;
            }

            progress.step();
        }
    }

    @Override
    public void terminate() {
        terminate = true;
    }

    @Override
    public int progress() {
        return progress.getPercentDone();
    }
}
