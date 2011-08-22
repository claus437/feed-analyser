package org.wooddog;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 22-08-11
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class ChannelInspectorTest {

    @Test
    public void testAtomType() throws Exception {
        File file;
        String actual;

        file = new File("src/test/resources/org/wooddog/job/channel/atom-feed.xml");
        actual = ChannelInspector.getType(file.toURI().toURL());

        Assert.assertEquals("ATOM", actual);
    }

    @Test
    public void testRssType() throws Exception {
        File file;
        String actual;

        file = new File("src/test/resources/org/wooddog/job/channel/rss-feed-2.0.xml");
        actual = ChannelInspector.getType(file.toURI().toURL());

        Assert.assertEquals("RSS", actual);
    }

    @Test
    public void testUnknownType() throws Exception {
        File file;
        String actual;

        file = new File("src/test/resources/org/wooddog/job/channel/webcontent.xml");
        actual = ChannelInspector.getType(file.toURI().toURL());

        Assert.assertEquals(null, actual);
    }


}
