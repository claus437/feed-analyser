package org.wooddog.job.channel.fetcher;

import junit.framework.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 22-08-11
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class AtomChannelFetcherTest {
    private AtomChannelFetcher subject;
    private Channel channel;

    @Before
    public void setup() throws Exception {
        File file;

        file = new File("src/test/resources/org/wooddog/job/channel/atom-feed.xml");

        channel = new Channel();
        channel.setUrl(file.toURI().toURL());
        channel.setType("ATOM");

        subject = new AtomChannelFetcher();
    }

    @Test
    public void testMapping() throws Exception {
        List<Article> articleList;
        Article article;

        articleList = subject.fetch(channel, new Date(0));

        junit.framework.Assert.assertEquals(false, articleList.isEmpty());

        article = articleList.get(0);

        junit.framework.Assert.assertEquals("title", article.getTitle());
        junit.framework.Assert.assertEquals("description", article.getDescription());
        junit.framework.Assert.assertEquals("http://link", article.getLink());
        junit.framework.Assert.assertEquals(new GregorianCalendar(2011, 7, 22, 10, 32, 26).getTime(), article.getPublished());
        junit.framework.Assert.assertEquals(channel.getUrl().toURI().toURL().toExternalForm(), article.getSource());
    }

    @Test
    public void testFetchLatest() {
        Date from;

        from = new GregorianCalendar(2011, 7, 22, 7, 0 ,0).getTime();
        junit.framework.Assert.assertEquals(16, subject.fetch(channel, from).size());

        from = new GregorianCalendar(2011, 7, 21, 12, 50 ,0).getTime();
        junit.framework.Assert.assertEquals(18, subject.fetch(channel, from).size());
    }

}
