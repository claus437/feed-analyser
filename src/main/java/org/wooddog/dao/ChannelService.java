package org.wooddog.dao;

import org.wooddog.domain.Channel;
import java.util.Date;
import java.util.List;

public interface ChannelService {
    void storeChannel(Channel channel);
    List<Channel> getChannels();
    void setChannelFetched(int id, Date date);
    Channel getChannelById(int id);
    void deleteChannels();
    void deleteChannel(int id);
}
