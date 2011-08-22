package org.wooddog.servlets.actions;

import org.apache.log4j.Logger;
import org.wooddog.dao.ChannelService;
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
    private ChannelService channelService = ChannelServiceDao.getInstance();

    public void execute(Map<String, String[]> parameters) {
        int id;

        id = Integer.parseInt(parameters.get("id")[0]);
        channelService.deleteChannel(id);
    }

    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }
}
