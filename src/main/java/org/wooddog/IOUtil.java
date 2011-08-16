package org.wooddog;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.omg.CORBA.portable.Streamable;
import org.wooddog.stock.StockJob;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.text.html.parser.Entity;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class IOUtil {
    private static final Logger LOGGER = Logger.getLogger(IOUtil.class);

    public static Document readDocument(InputStream source) {
        SAXReader reader;
        Document document;

        reader = new SAXReader();
        reader.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                System.out.println(publicId + " " + systemId);
                return new InputSource(getClass().getClassLoader().getResourceAsStream("xhtml1-strict.dtd"));
            }
        });

        try {
            document = reader.read(source);
        } catch (DocumentException x) {
            throw new RuntimeException("unable to read xml document, " + x.getMessage(), x);
        }

        return document;
    }

    public static byte[] read(InputStream source) {
        ByteArrayOutputStream stream;

        stream = new ByteArrayOutputStream();
        copy(source, stream);

        return stream.toByteArray();
    }

    public static void copy(InputStream source, OutputStream target) {
        byte[] data;
        int length;

        data = new byte[4096];

        try {
            while ((length = source.read(data)) != -1) {
                target.write(data, 0, length);
            }
        } catch (IOException x) {
            throw new RuntimeException("failed coping data");
        }
    }

    public static void close(String name, Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException x) {
                LOGGER.error("failed closing stream " + name + ", " + x.getMessage(), x);
            }
        }
    }

    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException x) {
            throw new RuntimeException(x);
        }
    }

    public static void wait(Thread job) {
        while (job.isAlive()) {
            sleep(100);
        }
    }
}
