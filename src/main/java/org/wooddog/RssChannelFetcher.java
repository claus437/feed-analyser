package org.wooddog;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class RssChannelFetcher implements ChannelFetcher {
    private static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
    private Document feed;
    private Channel channel;
    private Date updated;
    private List<Article> articles;

    public RssChannelFetcher(Channel channel) {
        this.channel = channel;
        articles = new ArrayList<Article>();
        updated = new Date(0);
    }

    public void fetch() throws IOException, DocumentException, ParseException {
        URLConnection connection;

        connection = channel.getUrl().openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);

        feed = read(connection.getInputStream());
        connection.getInputStream().close();

        createFeedItems();
    }

    public Channel getChannel() {
        return channel;
    }

    public List<Article> getArticles() {
        return articles;
    }

    private Document read(InputStream stream) throws DocumentException {
        SAXReader reader;
        Document document;

        reader = new SAXReader();
        document = reader.read(stream);

        return document;
    }

    private void createFeedItems() throws ParseException {
        Article article;
        SimpleDateFormat format;
        List<Element> items;
        Date publishDate;
        Date latest;

        latest = updated;
        format = new SimpleDateFormat(DATE_FORMAT, Locale.UK);

        articles.clear();

        items = feed.selectNodes("rss/channel/item");
        for (Element item : items) {
            publishDate = format.parse(item.element("pubDate").getText());

            if (publishDate.after(latest)) {
                article = new Article();
                article.setTitle(item.element("title").getText());
                article.setDescription(item.element("description").getText());
                article.setLink(item.element("link").getText());
                article.setPublished(publishDate);
                articles.add(article);
            }

            if (publishDate.after(updated)) {
                updated = publishDate;
            }
        }
    }
}
