package org.wooddog.servlets.actions;

import org.apache.log4j.Logger;
import org.wooddog.ChannelManager;
import org.wooddog.dao.service.ChannelServiceDao;
import org.wooddog.domain.Channel;
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
    private static final Logger LOGGER = Logger.getLogger(DeleteChannelAction.class);

    public void execute(Map<String, String[]> parameters) {
        Channel channel;
        int id;

        id = Integer.parseInt(parameters.get("id")[0]);

        channel = ChannelServiceDao.getInstance().getChannelById(id);

        LOGGER.info("loaded channel" + channel.getId() + " " + channel.getUrl());

        ChannelServiceDao.getInstance().deleteChannel(id);
        ChannelManager.getInstance().removeChannel(channel);
    }
}
