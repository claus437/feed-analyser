package org.wooddog.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.dao.service.ArticleServiceDao;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public class ServiceTest {

    @Test
    public void testStoreChannel() throws Exception {
        Channel channel;

        channel = new Channel();
        channel.setFetched(new Date());
        channel.setUrl(new URL("http://www.amiga.dk"));
        ChannelServiceDao.getInstance().storeChannel(channel);

        Assert.assertNotSame(0, channel.getId());
    }

    @Test
    public void testSelectChannels() throws Exception {
        List<Channel> channels;
        Channel channel;

        channel = new Channel();
        channel.setFetched(new Date());
        channel.setUrl(new URL("http://www.amiga.dk"));
        ChannelServiceDao.getInstance().storeChannel(channel);

        channel.setFetched(new Date());
        channel.setUrl(new URL("http://www.amiga2.dk"));
        ChannelServiceDao.getInstance().storeChannel(channel);

        channels = ChannelServiceDao.getInstance().getChannels();

        Assert.assertEquals(2, channels.size());
    }

    @Test
    public void testGetArticlesFromId() {
        List<Article> actuals;
        Article article;
        int boundaryId;

        article = new Article();
        article.setDescription("test1");
        article.setLink("link1");
        article.setPublished(new Date());
        article.setSource("source1");
        article.setTitle("title1");
        ArticleServiceDao.getInstance().storeArticle(article);

        boundaryId = article.getId();

        article = new Article();
        article.setDescription("test2");
        article.setLink("link2");
        article.setPublished(new Date());
        article.setSource("source2");
        article.setTitle("title2");
        ArticleServiceDao.getInstance().storeArticle(article);


        actuals = ArticleServiceDao.getInstance().getArticlesFromId(boundaryId);
        Assert.assertEquals(1, actuals.size());
        Assert.assertEquals(article.getId(), actuals.get(0).getId());
    }

    @Before
    public void clean() {
        ChannelServiceDao.getInstance().deleteChannels();
        ArticleServiceDao.getInstance().deleteArticles();
    }
}
