package org.wooddog;

import org.dom4j.DocumentException;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelFetcher {
    void fetch() throws IOException, DocumentException, ParseException;
    List<Article> getArticles();
    Channel getChannel();
}