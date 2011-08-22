package org.wooddog.job.channel;

import org.wooddog.job.channel.fetcher.AtomChannelFetcher;
import org.wooddog.job.channel.fetcher.ChannelFetcher;
import org.wooddog.job.channel.fetcher.RssChannelFetcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-08-11
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
public class ChannelFetcherFactory {
    private static final Map<String, ChannelFetcher> FETCHERS = new HashMap<String, ChannelFetcher>();

    private ChannelFetcherFactory() {
        FETCHERS.put("RSS", new RssChannelFetcher());
        FETCHERS.put("ATOM", new AtomChannelFetcher());
    }

    public static ChannelFetcher getFetcher(String type) {
        ChannelFetcher fetcher;

        fetcher = FETCHERS.get(type);
        if (fetcher== null) {
            throw new RuntimeException("unknown channel fetcher type " + type);
        }

        return fetcher;
    }


}
