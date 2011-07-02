package org.wooddog.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.wooddog.dao.mapper.ChannelMapper;
import org.wooddog.domain.Channel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 26-06-11
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class ChannelService {
    private static final ChannelService INSTANCE = new ChannelService();
    private SqlSessionFactory factory;

    private ChannelService() {
        factory = Service.getFactory();
    }

    public static ChannelService getInstance() {
        return INSTANCE;
    }

    public void storeChannel(Channel channel) {
        SqlSession session;

        session = factory.openSession();
        try {
            session.getMapper(ChannelMapper.class).insert(channel);
            session.commit();
        } finally {
            session.close();
        }
    }

    public List<Channel> getChannels() {
        SqlSession session;
        List<Channel> channels;

        session = factory.openSession();
        try {
            channels = session.getMapper(ChannelMapper.class).select();
        } finally {
            session.close();
        }

        return channels;
    }

    public void setChannelFetched(int id, Date date) {
        SqlSession session;
        int updated;
        Map parameters;

        parameters = Service.createParameterMap(id, date);

        session = factory.openSession();
        try {
            updated = session.getMapper(ChannelMapper.class).updateFetched(parameters);
            session.commit();
        } finally {
            session.close();
        }
    }

    public org.wooddog.domain.Channel getChannelById(int id) {
        SqlSession session;
        org.wooddog.domain.Channel channel;

        session = factory.openSession();
        try {
            channel = session.getMapper(ChannelMapper.class).selectById(id);
        } finally {
            session.close();
        }

        return channel;
    }

    public void deleteChannels() {
        SqlSession session;

        session = factory.openSession();
        try {
            session.getMapper(ChannelMapper.class).delete();
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteChannel(int id) {
        SqlSession session;

        session = factory.openSession();
        try {
            session.getMapper(ChannelMapper.class).deleteById(id);
            session.commit();
        } finally {
            session.close();
        }
    }

}
