package org.wooddog;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wooddog.dao.ChannelService;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.io.IOException;
import java.io.InputStream;
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
    private static final String RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
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

    @SuppressWarnings("unchecked")
    private void createFeedItems() throws ParseException {
        Article article;
        SimpleDateFormat format;
        List<Element> items;
        Date publishDate;
        Date latest;
        String pubName;

        latest = updated;


        articles.clear();

        if ("rss".equals(feed.getRootElement().getName())) {
            items = feed.selectNodes("rss/channel/item");
            format = new SimpleDateFormat(DATE_FORMAT, Locale.UK);
            pubName = "pubDate";
        } else if ("feed".equals(feed.getRootElement().getName())) {
            items = feed.selectNodes("/feed/*[local-name() = 'entry']");
            format = new SimpleDateFormat(RFC3339_FORMAT, Locale.UK);
            pubName = "published";
        } else {
            return;
        }

        for (Element item : items) {

            publishDate = format.parse(item.element(pubName).getText());

            if (publishDate.after(latest)) {
                article = new Article();
                article.setTitle(getText(item, "title"));
                article.setDescription(getText(item, "summary"));
                article.setLink(getText(item, "link", "href"));
                article.setPublished(publishDate);
                articles.add(article);
            }

            if (publishDate.after(updated)) {
                updated = publishDate;
            }
        }
    }

    private String getText(Element element, String elementName) {
        Element item;

        item = element.element(elementName);
        return item == null ? null : item.getText();
    }

    private String getText(Element element, String elementName, String attribute) {
        Element item;

        item = element.element(elementName);
        return item == null ? null : item.attributeValue(attribute);
    }

}
