package org.wooddog.servlets.actions;

import org.wooddog.ChannelInspector;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Channel;
import org.wooddog.servlets.PageAction;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class AddChannelAction implements PageAction {
    public void execute(Map<String, String[]> parameters) {
        URL url;
        Channel channel;
        String type;

        try {
            url = new URL(parameters.get("url")[0]);
        } catch (MalformedURLException x) {
            throw new RuntimeException("url " + parameters.get("url")[0] + " is not well formed", x);
        }

        type = ChannelInspector.getType(url);
        if (type == null) {
            throw new RuntimeException("no suitable fetcher found for url " + url.toExternalForm());
        }

        channel = new Channel();
        channel.setUrl(url);
        channel.setType(type);

        ChannelServiceDao.getInstance().storeChannel(channel);
    }
}
