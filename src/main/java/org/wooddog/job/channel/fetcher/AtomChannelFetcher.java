package org.wooddog.job.channel.fetcher;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import javax.xml.xpath.XPath;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AtomChannelFetcher implements ChannelFetcher {
    private static final Logger LOGGER = Logger.getLogger(AtomChannelFetcher.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

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
        Map<String, String> nameSpace;
        List<Article> articleList;
        Article article;
        List<Element> items;
        DateFormat format;
        Date published;


        format = new SimpleDateFormat(DATE_FORMAT, Locale.UK);

        articleList = new ArrayList<Article>();
        items = feed.selectNodes("/feed/*[local-name() = 'entry']");

        for (Element item : items) {
            try {
                published = format.parse(item.element("published").getText());

                if (published.after(from)) {
                    article = new Article();
                    article.setTitle(getText(item, "title"));
                    article.setDescription(getText(item, "summary"));
                    article.setLink(getText(item, "link", "href"));
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

     private String getText(Element element, String elementName, String attribute) {
        Element item;

        item = element.element(elementName);
        return item == null ? null : item.attributeValue(attribute);
    }
}
