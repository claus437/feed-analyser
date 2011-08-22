package org.wooddog;

import org.junit.Test;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 12-08-11
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class RssChannelFetcherTest {

    @Test
    public void testRssFeed() throws Exception {
        RssChannelFetcher fetcher;
        Channel channel;
        List<Article> articles;

        channel = new Channel();
        channel.setUrl(new URL("file://http://www.dr.dk/nyheder/service/feeds/allenyheder"));

        fetcher = new RssChannelFetcher(channel);
        fetcher.fetch();

        articles = fetcher.getArticles();
        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }

    @Test
    public void testAtomFeed() throws Exception {
        RssChannelFetcher fetcher;
        Channel channel;
        List<Article> articles;

        channel = new Channel();
        channel.setUrl(new URL("http://cia.ascore.denmark.nordic.x/hudson/rssAll"));

        fetcher = new RssChannelFetcher(channel);
        fetcher.fetch();

        articles = fetcher.getArticles();
        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }
}
