package org.wooddog.servlets.actions;

import org.wooddog.dao.ChannelService;
import org.wooddog.domain.Channel;
import org.wooddog.ChannelManager;
import org.wooddog.dao.Service;
import org.wooddog.servlets.PageAction;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-06-11
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class DeleteChannelAction implements PageAction {
    public void execute(Map<String, String[]> parameters) {
        Channel channel;
        int id;

        id = Integer.parseInt(parameters.get("id")[0]);

        channel = ChannelService.getInstance().getChannelById(id);

        System.out.println("loaded channel" + channel.getId() + " " + channel.getUrl());

        ChannelService.getInstance().deleteChannel(id);
        System.out.println("after delete" + channel.getId() + " " + channel.getUrl());

        ChannelManager.getInstance().removeChannel(channel);
    }
}
