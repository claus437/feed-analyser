package org.wooddog.job.channel.fetcher;


import junit.framework.Assert;
import org.junit.Before;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;
import org.wooddog.job.channel.fetcher.RssChannelFetcher;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 22-08-11
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
public class RssChannelFetcherTest {
    private RssChannelFetcher subject;
    private Channel channel;

    @Before
    public void setup() throws Exception {
        File file;

        file = new File("src/test/resources/org/wooddog/job/channel/rss-feed-2.0.xml");

        channel = new Channel();
        channel.setUrl(file.toURI().toURL());
        channel.setType("RSS");

        subject = new RssChannelFetcher();
    }

    @Test
    public void testMapping() throws Exception {
        List<Article> articleList;
        Article article;

        articleList = subject.fetch(channel, new Date(0));

        Assert.assertEquals(false, articleList.isEmpty());

        article = articleList.get(0);

        Assert.assertEquals("title", article.getTitle());
        Assert.assertEquals("description", article.getDescription());
        Assert.assertEquals("http://link", article.getLink());
        Assert.assertEquals(new GregorianCalendar(2011, 7, 22, 8, 48, 0).getTime(), article.getPublished());
        Assert.assertEquals(channel.getUrl().toURI().toURL().toExternalForm(), article.getSource());
    }

    @Test
    public void testFetchLatest() {
        Date from;

        from = new GregorianCalendar(2011, 7, 22, 7, 0 ,0).getTime();
        Assert.assertEquals(6, subject.fetch(channel, from).size());

        from = new GregorianCalendar(2011, 7, 22, 4, 0 ,0).getTime();
        Assert.assertEquals(18, subject.fetch(channel, from).size());
    }
}
