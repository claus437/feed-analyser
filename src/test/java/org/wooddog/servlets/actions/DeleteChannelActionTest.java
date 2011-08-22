package org.wooddog.servlets.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Channel;
import org.wooddog.servlets.PageAction;
import org.wooddog.servlets.PageActionFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-06-11
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
public class DeleteChannelActionTest {

    @Before
    public void clean() {
        ChannelServiceDao.getInstance().deleteChannels();
    }

    @Test
    public void testDeleteChannel() throws Exception {
        PageAction action;
        Map<String, String[]> parameters;
        Channel channel;

        channel = new Channel();
        channel.setUrl(new URL("http://www.google.com"));
        ChannelServiceDao.getInstance().storeChannel(channel);
        System.out.println("stored " + channel.getId() + " " + channel.getUrl());


        parameters = new HashMap<String, String[]>();
        parameters.put("id", new String[]{Integer.toString(channel.getId())});

        action = PageActionFactory.getInstance().getAction("DeleteChannel");
        action.execute(parameters);

        Assert.assertTrue(ChannelServiceDao.getInstance().getChannels().isEmpty());
    }
}
