package org.wooddog.servlets.actions;

import org.wooddog.ChannelManager;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Channel;
import org.wooddog.servlets.PageAction;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-06-11
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class AddChannelAction implements PageAction {
    public void execute(Map<String, String[]> parameters) {
        URL url;
        Channel channel;

        try {
            url = new URL(parameters.get("url")[0]);
        } catch (MalformedURLException x) {
            throw new RuntimeException("url " + parameters.get("url")[0] + " is not well formed", x);
        }

        channel = new Channel();
        channel.setUrl(url);

        ChannelServiceDao.getInstance().storeChannel(channel);
        ChannelManager.getInstance().addChannel(channel);
    }
}
