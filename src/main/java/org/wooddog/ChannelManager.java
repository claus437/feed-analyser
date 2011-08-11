package org.wooddog;

import org.wooddog.dao.ChannelService;
import org.wooddog.domain.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class ChannelManager {
    private static final ChannelManager INSTANCE = new ChannelManager();
    private List<ChannelThread> channelFetchers;

    private ChannelManager() {
        List<Channel> channels;
        channelFetchers = new ArrayList<ChannelThread>();

        channels = ChannelService.getInstance().getChannels();
        for (Channel channel : channels) {
            addChannel(channel);
        }
    }

    public static ChannelManager getInstance() {
        return INSTANCE;
    }

    public void addChannel(Channel channel) {
        ChannelThread fetcher;

        fetcher = new ChannelThread(new RssChannelFetcher(channel));
        fetcher.start();
        channelFetchers.add(fetcher);
    }

    public void removeChannel(Channel channel) {
        for (ChannelThread fetcher : channelFetchers) {
            if (channel.equals(fetcher.getChannel())) {
                fetcher.stop();
                channelFetchers.remove(fetcher);
            }
        }
    }

    public void start() {
        for (ChannelThread channel : channelFetchers) {
            channel.start();
        }
    }

    public void stop() {
        for (ChannelThread channel : channelFetchers) {
            channel.stop();
        }

        for (ChannelThread channel : channelFetchers) {
            while (channel.isAlive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException x) {
                    System.err.println("failed shutting down thread, " + x.getMessage());
                }
            }
        }
    }
}
