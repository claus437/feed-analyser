package org.wooddog.domain;

import java.net.URL;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class Channel {
    private int id;
    private URL url;
    private Date fetched;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Date getFetched() {
        return fetched;
    }

    public void setFetched(Date fetched) {
        this.fetched = fetched;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        Channel channel;

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        channel = (Channel) o;

        if (id != channel.id) {
            return false;
        }

        if (fetched != null ? !fetched.equals(channel.fetched) : channel.fetched != null) {
            return false;
        }

        if (url != null ? !url.equals(channel.url) : channel.url != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (fetched != null ? fetched.hashCode() : 0);
        return result;
    }
}
