package org.wooddog;

import org.apache.log4j.Logger;
import org.wooddog.dao.ArticleService;
import org.wooddog.dao.ChannelService;
import org.wooddog.dao.Service;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
public class ChannelThread {
    private static final Logger LOGGER = Logger.getLogger(ChannelThread.class);
    private Worker worker;

    public ChannelThread(ChannelFetcher channelFetcher) {
        worker = new Worker(channelFetcher);
    }

    public void start() {
        worker.start();
    }

    public void stop() {
        worker.kill();
    }

    public boolean isAlive() {
        return worker.isAlive();
    }

    public Channel getChannel() {
        return worker.channelFetcher.getChannel();
    }

    private class Worker extends Thread {
        private boolean signal;
        private ChannelFetcher channelFetcher;

        Worker(ChannelFetcher channelFetcher) {
            this.channelFetcher = channelFetcher;
        }

        @Override
        public void run() {
            List<Article> articles;
            Service service;

            service = Service.getInstance();

            while (!signal) {
                try {
                    System.out.println("-- FETCHING " + channelFetcher.getChannel().getUrl().toExternalForm());
                    channelFetcher.fetch();
                    articles = channelFetcher.getArticles();
                    LOGGER.info("fetched " + articles.size());

                    if (!articles.isEmpty()) {
                        ArticleService.getInstance().storeArticles(articles);
                    }

                    channelFetcher.getChannel().setFetched(new Date());
                    ChannelService.getInstance().setChannelFetched(channelFetcher.getChannel().getId(), channelFetcher.getChannel().getFetched());
                    System.out.println("-- FETCHING UPDATED " + channelFetcher.getChannel().getUrl().toExternalForm());

                } catch (Throwable x) {
                    System.out.println(x.getMessage());
                }
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException x) {
                    signal = true;
                }
            }
        }

        public void kill() {
            signal = true;
        }
    }

}
