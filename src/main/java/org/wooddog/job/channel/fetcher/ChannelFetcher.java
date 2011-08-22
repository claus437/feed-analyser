package org.wooddog.job.channel.fetcher;

import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-08-11
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelFetcher {
    List<Article> fetch(Channel channel, Date from);
}
