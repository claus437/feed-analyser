package org.wooddog.job.channel;

import org.wooddog.job.channel.fetcher.AtomChannelFetcher;
import org.wooddog.job.channel.fetcher.ChannelFetcher;
import org.wooddog.job.channel.fetcher.RssChannelFetcher;

import java.util.HashMap;
import java.util.Map;

public class ChannelFetcherFactory {
    private static final Map<String, ChannelFetcher> FETCHERS = new HashMap<String, ChannelFetcher>();

    static {
        FETCHERS.put("RSS", new RssChannelFetcher());
        FETCHERS.put("ATOM", new AtomChannelFetcher());
    }

    public static ChannelFetcher getFetcher(String type) {
        ChannelFetcher fetcher;

        fetcher = FETCHERS.get(type);
        if (fetcher == null) {
            throw new RuntimeException("unknown channel fetcher type " + type);
        }

        return fetcher;
    }

    private ChannelFetcherFactory() {
    }

}
