package org.wooddog.servlets.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.dao.ChannelService;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Channel;
import org.wooddog.servlets.PageAction;
import org.wooddog.servlets.PageActionFactory;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-06-11
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
public class DeleteChannelActionTest implements ChannelService {
    private DeleteChannelAction subject;
    private int deletedChannelId;

    @Before
    public void setup() {
        subject = new DeleteChannelAction();
        subject.setChannelService(this);
    }

    @Test
    public void testDeleteChannel() throws Exception {
        Map<String, String[]> parameters;

        parameters = new HashMap<String, String[]>();
        parameters.put("id", new String[]{"1"});

        subject.setParameters(parameters);
        subject.execute();

        Assert.assertEquals(1, deletedChannelId);
    }

    @Override
    public void storeChannel(Channel channel) {

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
        deletedChannelId = id;
    }
}
