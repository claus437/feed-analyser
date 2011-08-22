package org.wooddog.job.channel.fetcher;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 22-08-11
 * Time: 08:33
 * To change this template use File | Settings | File Templates.
 */
public class RssChannelFetcher implements ChannelFetcher {
    private static final Logger LOGGER = Logger.getLogger(RssChannelFetcher.class);
    private static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

    @Override
    public List<Article> fetch(Channel channel, Date from) {
        List<Article> articleList;
        Document feed;

        feed = read(channel.getUrl());
        articleList = parseFeed(channel, from, feed);

        return articleList;
    }

    @SuppressWarnings("unchecked")
    private List<Article> parseFeed(Channel channel, Date from, Document feed) {
        List<Article> articleList;
        Article article;
        List<Element> items;
        DateFormat format;
        Date published;


        format = new SimpleDateFormat(DATE_FORMAT, Locale.UK);

        articleList = new ArrayList<Article>();
        items = feed.selectNodes("rss/channel/item");

        for (Element item : items) {
            try {
                published = format.parse(item.element("pubDate").getText());

                if (published.after(from)) {
                    article = new Article();
                    article.setTitle(getText(item, "title"));
                    article.setDescription(getText(item, "description"));
                    article.setLink(getText(item, "link"));
                    article.setPublished(published);
                    article.setSource(channel.getUrl().toExternalForm());
                    article.setPublished(published);

                    articleList.add(article);
                }
            } catch (ParseException x) {
                LOGGER.error("failed parsing date in article #" + item.asXML() + " from " + channel.getUrl().toExternalForm());
            }

        }

        return articleList;
    }


    private Document read(URL url) {
        SAXReader reader;
        Document document;

        reader = new SAXReader();
        try {
            document = reader.read(url);
        } catch (DocumentException x) {
            throw new RuntimeException(x);
        }

        return document;
    }

    private String getText(Element element, String elementName) {
        Element item;

        item = element.element(elementName);
        return item == null ? null : item.getText();
    }
}
