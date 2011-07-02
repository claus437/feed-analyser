package org.wooddog;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public class FeadReaderTest {

    @Test
    public void testReadFeed() throws Exception {
        RssChannelFetcher reader;
        List<Article> articles;
        Channel channel;

        channel = new Channel();
        channel.setUrl(new URL("http://www.dr.dk/nyheder/service/feeds/penge"));

        reader = new RssChannelFetcher(channel);
        reader.fetch();

        articles = reader.getArticles();
        Assert.assertNotSame(0, articles.size());

        // TODO works only if the feed did not publish any articles since last fetch, which is unlikely but not imposible
        reader.fetch();
        articles = reader.getArticles();
        Assert.assertEquals(0, articles.size());
    }

    @Test
    public void testDateFormat() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.UK);
        String date = "Sat, 18 Jun 2011 06:52:00 +0200";

        System.out.print(format.parse(date).toLocaleString());

    }
}
