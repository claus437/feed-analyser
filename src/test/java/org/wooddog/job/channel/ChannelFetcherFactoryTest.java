package org.wooddog.job.channel;

import org.junit.Test;
import org.junit.Assert;
import org.wooddog.job.channel.fetcher.AtomChannelFetcher;
import org.wooddog.job.channel.fetcher.RssChannelFetcher;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 22-08-11
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class ChannelFetcherFactoryTest {

    @Test
    public void testGetRssFetcher() {
        Assert.assertEquals(RssChannelFetcher.class, ChannelFetcherFactory.getFetcher("RSS").getClass());
    }

    @Test
    public void testGetAtomFetcher() {
        Assert.assertEquals(AtomChannelFetcher.class, ChannelFetcherFactory.getFetcher("ATOM").getClass());
    }

    @Test (expected = RuntimeException.class)
    public void testUnknownFetcher() {
        ChannelFetcherFactory.getFetcher("UNKNOWN");
    }

}
