package org.wooddog;

import org.dom4j.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ChannelInspector {

    public static String getType(URL url) {
        URLConnection connection;
        InputStream stream;
        byte[] content;

        try {
            connection = url.openConnection();
            stream = connection.getInputStream();
        } catch (IOException x) {
            throw new RuntimeException("unable to open url " + url.toExternalForm());
        }

        try {
            content = IOUtil.read(stream);
        } catch (RuntimeException x) {
            throw new RuntimeException("error reading content from " + url.toExternalForm());
        } finally {
            IOUtil.close(url.toExternalForm(), stream);
        }

        return determineType(content);
    }

    private static String determineType(byte[] bytes) {
        Document document;

        try {
            document = IOUtil.readDocument(new ByteArrayInputStream(bytes));
        } catch (RuntimeException x) {
            return null;
        }

        if ("rss".equals(document.getRootElement().getName().toLowerCase())) {
            return "RSS";
        }

        if ("feed".equals(document.getRootElement().getName().toLowerCase())) {
            return "ATOM";
        }

        return null;
    }
}
