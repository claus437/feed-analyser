package org.wooddog.servlets.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.dao.ChannelService;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Channel;
import org.wooddog.servlets.PageAction;
import org.wooddog.servlets.PageActionFactory;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-06-11
 * Time: 09:23
 * To change this template use File | Settings | File Templates.
 */
public class AddChannelActionTest implements ChannelService {
    private AddChannelAction subject;
    private URL url;

    @Before
    public void setup() throws Exception {
        subject = new AddChannelAction();
        subject.setChannelService(this);

        url = new File("src/test/resources/org/wooddog/job/channel/rss-feed-2.0.xml").toURI().toURL();
    }

    @Test
    public void testAddChannel() throws Exception {
        Map<String, String[]> parameters;

        parameters = new HashMap<String, String[]>();
        parameters.put("url", new String[]{url.toExternalForm()});

        subject.execute(parameters);
    }


    @Override
    public void storeChannel(Channel channel) {
        Assert.assertEquals(url, channel.getUrl());
        Assert.assertEquals("RSS", channel.getType());
    }

    @Override
    public List<Channel> getChannels() {
        return null;
    }

    @Override
    public void setChannelFetched(int id, Date date) {
    }

    @Override
    public Channel getChannelById(int id) {
        return null;
    }

    @Override
    public void deleteChannels() {
    }

    @Override
    public void deleteChannel(int id) {
    }
}
