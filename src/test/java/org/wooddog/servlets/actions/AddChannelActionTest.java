package org.wooddog.servlets.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.dao.ChannelService;
import org.wooddog.dao.Service;
import org.wooddog.domain.Channel;
import org.wooddog.servlets.PageAction;
import org.wooddog.servlets.PageActionFactory;

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
public class AddChannelActionTest {

    @Before
    public void clean() {
        ChannelService.getInstance().deleteChannels();
    }

    @Test
    public void testAddChannel() {
        PageAction action;
        Map<String, String[]> parameters;
        List<Channel> channels;

        parameters = new HashMap<String, String[]>();
        parameters.put("url", new String[]{"http://www.google.com"});

        action = PageActionFactory.getInstance().getAction("AddChannel");
        action.execute(parameters);

        channels = ChannelService.getInstance().getChannels();
        for (Channel channel : channels) {
            if (channel.getUrl() == null) {
                continue;
            }

            if ("http://www.google.com".equals(channel.getUrl().toExternalForm())) {
                return;
            }
        }

        Assert.fail();
    }
}
