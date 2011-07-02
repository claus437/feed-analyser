package org.wooddog.dao.mapper;

import org.wooddog.domain.Channel;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelMapper {
    List<Channel> select();
    Channel selectById(int id);
    void insert(Channel channel);
    void delete();
    void deleteById(int id);
    int updateFetched(Map map);
}
