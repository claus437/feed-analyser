package org.wooddog.job.channel;

import org.wooddog.dao.ArticleService;
import org.wooddog.dao.service.ArticleServiceDao;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;
import org.wooddog.job.Job;
import org.wooddog.job.channel.fetcher.ChannelFetcher;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-08-11
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class ChannelJob implements Job {
    private ChannelServiceDao channelService = ChannelServiceDao.getInstance();
    private ArticleService articleService = ArticleServiceDao.getInstance();
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

        channelList = channelService.getChannels();

        for (Channel channel : channelList) {

            latest = articleService.getLatestPublishDate(channel.getUrl().toExternalForm());

            fetcher = ChannelFetcherFactory.getFetcher(channel.getType());
            articles = fetcher.fetch(channel, latest);

            articleService.storeArticles(articles);

            if (terminate) {
                break;
            }
        }
    }

    @Override
    public void terminate() {
        terminate = true;
    }
}
